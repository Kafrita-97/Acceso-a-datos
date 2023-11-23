package proyectoAAD;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProyectoAadApplication {
	private final static String jdbcURL = "jdbc:h2:mem:testdb";
    private final static String userName = "sa";
    private final static String password = "";
	public static void main(String[] args) {
		SpringApplication.run(ProyectoAadApplication.class, args);

		xmlToDb con1 = new xmlToDb(jdbcURL, userName, password);

		dbToXml con2 = new dbToXml(jdbcURL, userName, password);

		/*
		 * OpenBrowser op = new OpenBrowser();
		 * try {
		 * op.abrirNavegadorPorDefecto("http://localhost:8080/h2-console/");
		 * } catch (IOException e) {
		 * e.printStackTrace();
		 * }
		 */
	}

}
