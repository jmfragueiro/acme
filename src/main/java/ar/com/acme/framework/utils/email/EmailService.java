package ar.gov.posadas.mbe.framework.utils.email;

import ar.gov.posadas.mbe.framework.common.Constantes;
import ar.gov.posadas.mbe.framework.common.ETipoContacto;
import ar.gov.posadas.mbe.framework.common.Propiedades;
import ar.gov.posadas.mbe.framework.common.Response;
import ar.gov.posadas.mbe.framework.core.logging.IPersistentLogService;
import ar.gov.posadas.mbe.ports.email.IEmailConfigService;
import ar.gov.posadas.mbe.ports.email.IEmailReadNotificacionClient;
import ar.gov.posadas.mbe.ports.email.IEmailReadNotificacionServer;
import ar.gov.posadas.mbe.ports.email.IEmailTemplateService;
import ar.gov.posadas.mbe.ports.email.IEmailUnreachService;

import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.activation.DataHandler;
import jakarta.mail.BodyPart;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.internet.*;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class EmailService implements IEmailReadNotificacionServer {
    private final List<IEmailReadNotificacionClient> ernClients = new ArrayList<>();
    private final ResourceLoader resourceLoader;
    private final Propiedades systemprops;
    private final IEmailTemplateService emailTemplateService;
    private final IEmailUnreachService emailUnreachService;
    private final IEmailConfigService emailCfgService;
    private final IPersistentLogService logService;
    private final JavaMailSenderImpl sender = new JavaMailSenderImpl();

    /**
     * Metodo para aplicar el observer y agregar un cliente al servicio de reademail
     */
    @Override
    public void addClient(IEmailReadNotificacionClient client) {
        if (client != null) {
            ernClients.add(client);
        }
    }

    /**
     * Este metodo se encarga de conducir el envio de un correo electronico
     *
     * @param emailBody Cuerpo del Correo
     * @param direccionenvioid ID de la Direccion de Envio
     * @param controllectura Indica si se requiere control de lectura
     * @param token Token a utilizar en el control de lectura
     *
     * @return
     */
    public Response sendEmail(EmailBody emailBody, Long direccionenvioid, boolean controllectura, String token) {
        if (emailCfgService != null) {
            emailCfgService.completeEmailPropertiesForSendDirId(direccionenvioid).applyTo(sender);
        }

        // Si no Estoy en PRODUCCION hago que todas las pruebas lleguen a una direccion de correo controlada
        if (!systemprops.getEntorno().toUpperCase().equals(Constantes.SYS_CAN_ENV_PROD)) {
            // emailBody.setDestinatarios(new String[]{"lschwegler@gmail.com", "lucasrodoni@gmail.com"});
            // emailBody.setDestinatarios(new String[]{"diegoale2390@gmail.com", "lschwegler@gmail.com", "lucasrodoni@gmail.com"});
            // emailBody.setDestinatarios(new String[]{"lucasrodoni@gmail.com"});
            // emailBody.setDestinatarios(new String[]{"diegoale2390@gmail.com", "lschwegler@gmail.com"});
            emailBody.setDestinatarios(new String[]{"lschwegler@gmail.com"});
            emailBody.setConCopiaA(new String[0]);
        }

        String[] destinatariosControlados = controlDestinatarios(emailBody.getDestinatarios());
        String[] concopiaAControlados = controlDestinatarios(emailBody.getConCopiaA());

        if (destinatariosControlados != null) {
            emailBody.setDestinatarios(destinatariosControlados);
            emailBody.setConCopiaA(concopiaAControlados);

            return sendEmailTool(emailBody, controllectura, token);
        } else {
            return Response.fail(Constantes.MSJ_MAIL_ERR_INVALIDTO);
        }
    }

    /**
     * @param destinatarios Listado de Destinatarios del Correo Electrónico
     * @return String[]
     * <p>
     * Se Realiza control de direcciones de mail que fueron rechazadas por "mal formadas"
     * con el objetivo que no se trate de enviar un correo electronico a esas direcciones
     * que se sabe van a generar error
     */
    private String[] controlDestinatarios(String[] destinatarios) {
        ArrayList<String> temporal = new ArrayList<String>();
        boolean esDireccionValida;

        // Recorre los destinatarios intentando agregar solo lo valido
        for (String destinatario : destinatarios) {
            try {
                // Verifico que el Mail sea valido segun la utilidad que brinda java
                esDireccionValida = false;
                InternetAddress internetAddress = new InternetAddress(destinatario);
                internetAddress.validate();
                esDireccionValida = true;

                // Verifico que el Mail no este ya entre los rechazados anteriormente
                if ((emailUnreachService != null
                        && emailUnreachService.existByTipoContacto(Long.valueOf(ETipoContacto.MAIL.ordinal()), destinatario))
                    || !esDireccionValida) {
                   continue;
                }

                // Si esta todo ok entonces se suma a la lista de destinatarios
                temporal.add(destinatario);
            } catch (AddressException ignored) {
                // Registro Rechazo del Contacto: aqui supongo que la direccion esta "mal formada"
                if (emailUnreachService != null) {
                    emailUnreachService.addByTipoContacto(
                            Long.valueOf(ETipoContacto.MAIL.ordinal()),
                            destinatario,
                            Constantes.MSJ_MAIL_ERR_BADTO
                    );
                }
            } catch (Exception ignored) {
                // Registro Rechazo del Contacto: aqui se admiten otros errores que puedan surgir
                if (emailUnreachService != null) {
                    emailUnreachService.addByTipoContacto(
                            Long.valueOf(ETipoContacto.MAIL.ordinal()),
                            destinatario,
                            Constantes.MSJ_MAIL_ERR_FATAL
                    );
                }
            }
        }

        return temporal.toArray(new String[temporal.size()]);
    }

    /**
     * Consulta para ver como enviar imagenes en el texto del mail
     * https://mumzee.medium.com/display-base64-encoded-images-in-email-using-java-878e23534f9c
     *
     * @param emailBody      Cuerpo del Correo
     * @param controllectura Indica si se requiere control de lectura
     * @param token          token a utilizar en el control de lectura
     * @return
     * @throws MessagingException Proceso que configura el contenido del Correo Electronico y Realiza el Envio definitivo
     */
    private Response sendEmailTool(EmailBody emailBody, boolean controllectura, String token) {
        Response resultado = Response.fail();
        String[] destinatario = emailBody.getDestinatarios();
        String[] concopia = (emailBody.getConCopiaA() != null ? emailBody.getConCopiaA() : new String[]{});
        Collection<EmailMultiPartDTO> multipartes = (emailBody.getMultipartimagenes() != null ? emailBody.getMultipartimagenes() : new ArrayList<>());
        String subject = emailBody.getAsunto();

        String textMessage =
                (emailTemplateService != null)
                    ? emailTemplateService.completeEmailText(emailBody.getContenido(), controllectura, token)
                    : emailBody.getContenido();

        String responder =
                    (emailBody.getResponderA() != null)
                        ? emailBody.getResponderA()
                        : Constantes.SYS_CAD_MAIL_NORESPONDER;

        try {
            MimeMessage message = this.sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            if (destinatario.length > 0) {
                if (!textMessage.isEmpty()) {
                    if (!subject.isEmpty()) {
                        // Defino los Destinatarios del Mail
                        helper.setTo(destinatario);
                        if (concopia.length > 0) {
                            helper.setCc(concopia);
                        }

                        // Defino la Direccion Predefinida para Responder el Mail
                        if (responder != null && !responder.isEmpty()) {
                            helper.setReplyTo(responder);
                        }

                        // Defino el Asunto del Mail
                        if (emailTemplateService != null) {
                            subject = emailTemplateService.completarAsunto(subject);
                        }
                        helper.setSubject(subject);

                        // Defino el Objeto Multipart que utilizo para definir el contenido y los adjuntos del mail
                        Multipart multiPart = new MimeMultipart("alternative");

                        // Defino el Contenido del Mail (Cuerpo Principal) y lo registro en multipart
                        MimeBodyPart textPart = new MimeBodyPart();
                        textPart.setContent(textMessage, "text/html; charset=UTF-8");
                        multiPart.addBodyPart(textPart);

                        // Proceso los Archivos que seran parte del Cuerpo definiendo los como Multipart
                        multipartes.forEach(mp -> {
                            try {
                                ByteArrayDataSource imageDataSource = new ByteArrayDataSource(mp.getArchivo(), mp.getMimetype());
                                BodyPart imagePart = new MimeBodyPart();
                                imagePart.setDataHandler(new DataHandler(imageDataSource));
                                imagePart.setHeader("Content-ID", "<" + mp.getReferencia() + ">");
                                imagePart.setFileName(mp.getNombre());

                                multiPart.addBodyPart(imagePart);
                                message.setContent(multiPart);
                            } catch (MessagingException e) {
                                if (logService != null) {
                                    logService.error(null, "ERROR", "ADJUNTAR ARCHIVO AL MAIL",
                                                    Constantes.MSJ_MAIL_ERR_SEND + ": " + e.getMessage(), destinatario[0]);
                                }
                            }
                        });
                        message.setContent(multiPart);

                        // Finalmente envio el mail
                        this.sender.send(message);
                        resultado = Response.ok(Constantes.MSJ_MAIL_SEND_OK);
                    } else {
                        resultado = Response.fail(Constantes.MSJ_MAIL_ERR_NOSUBJECT);
                    }
                } else {
                    resultado = Response.fail(Constantes.MSJ_MAIL_ERR_NOMESSAGE);
                }
            } else {
                resultado = Response.fail(Constantes.MSJ_MAIL_ERR_NOTO);
            }
        } catch (MessagingException e) {
            if (logService != null) {
                logService.error(null, "ERROR", "ENVIO MAIL",
                                Constantes.MSJ_MAIL_ERR_SEND + ": " + e.getMessage(), destinatario[0]);
            }
            resultado = Response.fail(Constantes.MSJ_MAIL_ERR_SEND + ": " + destinatario[0] + ". " + e.getMessage());
        }

        return resultado;
    }

    /**
     * @param token String
     * @return File Archivo png que contiene una imagen a utilizar en el mail
     * Este metodo tiene la funcion principal de registrar el Visto por parte del destinatario sobre un correo recibido.
     * El mail solicita una imagen (logo_oficial_mail.png) para incrustarlo en la cabecera, con este artificio logramos saber que
     * fue leido, por consiguiente registro tal situacion
     * @throws IOException
     */
    public byte[] logoInstitucional(String token, boolean metodonuevo) throws IOException {
        /* PASO 1: Obtengo el archivo a devolver */
        String path_base_imegen = resourceLoader.getResource("classpath:/imagen/").getURI().getPath();
        String origen = path_base_imegen + systemprops.getEmpresa().get("codigo") + (metodonuevo ? "/logo_oficial_mail.png" : "/pixel.png");
        File file = new File(origen);

        // Creating an object of FileInputStream to
        // read from a file
        FileInputStream fl = new FileInputStream(file);

        // Now creating byte array of same length as file
        byte[] fileinbyte = new byte[(int) file.length()];

        // Reading file content to byte array
        // using standard read() method
        fl.read(fileinbyte);

        // lastly closing an instance of file input stream
        // to avoid memory leakage
        fl.close();

        /* PASO 2: Realizo la registración del VISTO del mail, tarea principal del proceso */
        this.ernClients.stream().forEach(n -> n.registrarVisto(token));

        return fileinbyte;
    }
}