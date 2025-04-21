package ar.gov.posadas.mbe.framework.utils.email;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.gov.posadas.mbe.framework.common.Constantes;
import ar.gov.posadas.mbe.framework.common.Response;
import ar.gov.posadas.mbe.ports.control.CtrlResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping(value = Constantes.SYS_CAD_EMAIL_URL)
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping(path = "/enviar")
    public CtrlResponse<Response> EnviarMail(@RequestParam(required = true) String destinatario,
                                             @RequestParam(required = true) String asunto,
                                             @RequestParam(required = true) String cuerpo) {
        Collection<EmailMultiPartDTO> multiparts = new ArrayList<>();
        EmailBody emailBody = new EmailBody();
        String contenido = "<tr>" +
                           "<td>" +
                           "<table>" +
                           "<tr>" +
                           "<td>" +
                           cuerpo +
                           "</br>" +
                           "</td>" +
                           "</tr>" +
                           "</br>" +
                           "</br>" +
                           "</table>" +
                           "</td>" +
                           "</tr>";

        emailBody.setDestinatarios(new String[]{destinatario});
        emailBody.setConCopiaA(null);
        emailBody.setResponderA(null);
        emailBody.setAsunto(asunto);
        emailBody.setContenido(contenido);
        emailBody.setMultipartimagenes(multiparts);

        Response resultado = emailService.sendEmail(emailBody, 1L, false, "");

        return (resultado.success()) ? CtrlResponse.ok(resultado) : CtrlResponse.badRequest(resultado);
    }

    @GetMapping(path = "/logoinstitucional/{token}")
    public CtrlResponse<byte[]> logoInstitucional(@PathVariable String token) throws IOException {
        return CtrlResponse.of(
                    ResponseEntity.ok()
                                  .header("Content-Type", "image/png" + "; charset=UTF-8")
                                  .header("Content-Disposition", "attachment; filename=" + "logoinstitucional.png")
                                  .body(emailService.logoInstitucional(token, true)));
    }

    @GetMapping(path = "/visto/{token}")
    public CtrlResponse<byte[]> visto(@PathVariable String token) throws IOException {
        return CtrlResponse.of(
            ResponseEntity.ok()
                          .header("Content-Type", "image/png" + "; charset=UTF-8")
                          .header("Content-Disposition", "attachment; filename=" + "logoinstitucional.png")
                          .body(emailService.logoInstitucional(token, false)));
    }
}