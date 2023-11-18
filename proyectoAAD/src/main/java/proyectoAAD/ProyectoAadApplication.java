package proyectoAAD;

import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProyectoAadApplication {
	public static void main(String[] args) {
		
		xmlCleaner file1 = new xmlCleaner("", "/Users/juanma/Library/Mobile Documents/com~apple~CloudDocs/V/Estudios/DAM/2º/Acceso a datos/Trabajo voluntario - Acceso a datos/Proyecto/proyectoAAD/src/main/resources/contratos-adjudicados-jun-22.xml");
		
		SpringApplication.run(ProyectoAadApplication.class, args);
		/* OpenBrowser op = new OpenBrowser();
		try {
			op.abrirNavegadorPorDefecto("http://localhost:8080/h2-console/");
		} catch (IOException e) {
			e.printStackTrace();
		} */
	}
	
}

