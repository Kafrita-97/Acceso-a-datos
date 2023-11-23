package proyectoAAD;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal que inicia la aplicación Spring Boot.
 */
@SpringBootApplication
public class ProyectoAadApplication {

	// Credenciales de la base de datos H2 en memoria
	private final static String jdbcURL = "jdbc:h2:mem:testdb";
	private final static String userName = "sa";
	private final static String password = "";

	/**
     * Método principal que inicia la aplicación Spring Boot.
     * 
     * @param args Argumentos de línea de comandos proporcionados al iniciar la aplicación.
     */
	public static void main(String[] args) {
		SpringApplication.run(ProyectoAadApplication.class, args);

		// Inicia la conversión de XML a base de datos
		new XmlToDb(jdbcURL, userName, password);

		// Inicia la conversión de base de datos a XML
		new DbToXml(jdbcURL, userName, password);

		/*
         * Abre un navegador web predeterminado para acceder a la consola H2.
         * 
         * Este código está comentado ya que la clase OpenBrowser no está presente.
         * Si la clase está implementada, puedes descomentar esta sección para abrir el navegador.
         */
		 OpenBrowser op = new OpenBrowser();
		 try {
			op.abrirNavegadorPorDefecto("http://localhost:8080/h2-console/");
		 } catch (IOException e) {
			e.printStackTrace();
		 }
		
	}

}
