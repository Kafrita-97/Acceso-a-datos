package proyectoAAD;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProyectoAadApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoAadApplication.class, args);
		
		xmlTreatment file_1 = new xmlTreatment("src/main/resources/contratos-adjudicados-jun-22.xml");
		file_1.readXML();
	}

}
