package proyectoAAD;

import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProyectoAadApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProyectoAadApplication.class, args);
		xmlTreatment file_1 = new xmlTreatment("src/main/resources/contratos-adjudicados-jun-22.xml");
		file_1.readXML();
		
		
		OpenBrowser op = new OpenBrowser();
		try {
			op.abrirNavegadorPorDefecto("http://localhost:8080/h2-console/");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

