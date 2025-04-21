package ar.gov.posadas.mbe.framework.utils.codes;

import java.io.IOException;

import com.google.zxing.WriterException;

public interface ICodeService {
    byte[] getQRImageByToken(String token) throws IOException, WriterException;

    byte[] getQRCODEImage(String text, int width, int height, boolean incrustarLogo, String codigoEmpresa) throws WriterException, IOException;

    byte[] getBARCODE39Image(String text, int width, int height) throws WriterException, IOException;

    byte[] getBARCODE128Image(String text, int width, int height) throws WriterException, IOException;
}
