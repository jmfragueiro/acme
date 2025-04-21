package ar.gov.posadas.mbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
//@EnableAsync(proxyTargetClass = true)
public class Aplicacion extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(Aplicacion.class, args);
	}
}
