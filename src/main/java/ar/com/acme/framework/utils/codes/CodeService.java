package ar.com.acme.framework.utils.codes;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.oned.Code39Writer;
import com.google.zxing.qrcode.QRCodeWriter;

import ar.com.acme.framework.common.Constantes;
import lombok.RequiredArgsConstructor;

import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

@Service
@RequiredArgsConstructor
public class CodeService implements ICodeService {
    private final ResourceLoader resourceLoader;
    private final List<ICodeClass> codeClasses;

    /**
     * Este metodo gennera u codigo QR a partir de los datos pasados como argumento.
     *
     * @param text   el texto a representar en el codigo QR
     * @param width  ancho del codigo qr
     * @param height alto del codigo qr
     * @param incrustarLogo indica si se debe incrustar un logo en el QR
     * @param codigoEmpresa el codigo de la empresa para obtener el logo
     * @return la cadena de bytes que expresa la imagen del codigo QR generado
     */
    public byte[] getQRCODEImage(String text, int width, int height, boolean incrustarLogo, String codigoEmpresa) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);

        if (incrustarLogo) {
            /* Cargo la Nueva Imagen a Incrustar en el QR */
            /* La combinacion de imagenes lo consulte desde esta url https://chillyfacts.com/generate-qr-code-image-using-java/ */

            /* Version para obtener imagen desde disco de la pc */
            // String origen = "D:/logo_oficial.png";
            // File file = new File(origen);

            /* Version para obtener imagen desde una url */
            // String url = frontendhostbasico + "/assets/images/empresa/" + empresacodigo + "/" + "logo_oficial_qr.png";
            // URL file = new URL(url);

            /* Version para obtener imagen desde disco del server en carpeta resources */
            String path_base_imegen = resourceLoader.getResource("classpath:/imagen/").getURI().getPath();
            String origen = path_base_imegen + codigoEmpresa  + "/logo_oficial_qr.png";
            File file = new File(origen);

            /* Traigo a memoria la nueva imagen a incrustar */
            /* Defino algunas propiedades de la nueva imagen a incrustar y la obtengo en memoria*/
            int proporcionLogoImagen = 18;
            int width_NewImagen = width * proporcionLogoImagen / 100;
            int height_NewImagen = height * proporcionLogoImagen / 100;
            BufferedImage logoImage = ImageIO.read(file);
            logoImage = resize(logoImage, width_NewImagen, height_NewImagen);

            /* Calculo el delta height y width entre el QR y la nueva imagen */
            int deltaHeight = height - logoImage.getHeight();
            int deltaWidth = width - logoImage.getWidth();

            /* Inicializo una nueva imagen combinada */
            BufferedImage combined = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = (Graphics2D) combined.getGraphics();

            /* Escribo el QR Original en la nueva imagen en la posicion 0,0 */
            ByteArrayInputStream input = new ByteArrayInputStream(pngOutputStream.toByteArray());
            BufferedImage image = ImageIO.read(input);

            g.drawImage(image, 0, 0, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

            /*
            Escribo la nueva imagen combinada en las posiciones (deltaWidth / 2) and (deltaHeight / 2)
            Background: Left/Right and Top/Bottom must be the same space for the logo to be centered
            */
            g.drawImage(logoImage, (int) Math.round(deltaWidth / 2), (int) Math.round(deltaHeight / 2), null);

            // Version de Resultado final con imagen a disco - ImageIO.write(combined, "png", new File("D:\\QR.png"));
            // Escribo la nueva imagen combinada con un PNG to OutputStream
            ByteArrayOutputStream pngOutputStream_New = new ByteArrayOutputStream();
            ImageIO.write(combined, "png", pngOutputStream_New);

            return pngOutputStream_New.toByteArray();
        } else {
            return pngOutputStream.toByteArray();
        }
    }

    /**
     * Este metodo gennera un codigo de barra "3 de 9" a partir de los datos pasados como argumento.
     *
     * @param text   el texto a representar en el codigo de barra
     * @param width  ancho del codigo de barra
     * @param height alto del codigo de barra
     * @return la cadena de bytes que expresa la imagen del codigo de barra 3 de 9 generado
     */
    public byte[] getBARCODE39Image(String text, int width, int height) throws WriterException, IOException {
        BitMatrix bitMatrix = new Code39Writer().encode(text, BarcodeFormat.CODE_39, width, height, null);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();

        return pngData;
    }

    /**
     * Este metodo gennera un codigo de barra "128" a partir de los datos pasados como argumento.
     *
     * @param text   el texto a representar en el codigo de barra
     * @param width  ancho del codigo de barra
     * @param height alto del codigo de barra
     * @return la cadena de bytes que expresa la imagen del codigo de barra 128 generado
     */
    public byte[] getBARCODE128Image(String text, int width, int height) throws WriterException, IOException {
        BitMatrix bitMatrix = new Code128Writer().encode(text, BarcodeFormat.CODE_128, width, height, null);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();

        return pngData;
    }

    @Override
    public byte[] getQRImageByToken(String token) throws IOException, WriterException {
        return this.codeClasses.stream()
                               .filter(c -> c.isThatCodeClass(token))
                               .findFirst()
                               .map(c -> c.findQRImageByToken(token))
                               .orElseThrow(() -> new IllegalArgumentException(Constantes.MSJ_ERR_ONIMAGEGEN));
    }

    /**
     * Este método se utiliza para redimensionar una imagen.
     *
     * @param bufferedImage la imagen original
     * @param newW         el nuevo ancho de la imagen
     * @param newH         el nuevo alto de la imagen
     * @return la imagen redimensionada
     */
    private BufferedImage resize(BufferedImage bufferedImage, int newW, int newH) {
        int w = bufferedImage.getWidth();
        int h = bufferedImage.getHeight();
        BufferedImage bufim = new BufferedImage(newW, newH, bufferedImage.getType());
        Graphics2D g = bufim.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(bufferedImage, 0, 0, newW, newH, 0, 0, w, h, null);
        g.dispose();

        return bufim;
    }
}
