package ar.com.acme.sistema.seguridad.usuario;

import java.time.LocalDate;

import lombok.Value;

@Value
public class NotificacionusuarioDTO {
    Long usuarioid;
    String username;
    String razonsocial;
    LocalDate desde;
    LocalDate hasta;
    String hora;
    String titulo;
    String asunto;
    String mensaje;
    String icono;
    String gravedad;
}
