package ar.com.acme.framework.utils.email;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailMultiPartDTO {
    private String nombre;
    private String mimetype;
    private String referencia;
    private byte[] archivo;
}