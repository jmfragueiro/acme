package ar.com.acme.framework.utils.email;

import java.util.Collection;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailBody {
    private String[] destinatarios;
    private String[] conCopiaA;
    private String responderA;
    private String asunto;
    private String contenido;
    private Collection<EmailMultiPartDTO> multipartimagenes;
}