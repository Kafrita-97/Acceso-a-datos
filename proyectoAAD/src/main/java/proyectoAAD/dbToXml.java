package proyectoAAD;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class DbToXml {

    private String jdbcURL;
    private String userName;
    private String password;

    private final String outFilePath = "/Users/juanma/Library/Mobile Documents/com~apple~CloudDocs/V/Estudios/DAM/2º/Acceso a datos/Trabajo voluntario - Acceso a datos/modified-contratos-adjudicados-jun-22.xml";

    private final String tableName = "CONTRATOS";
    private final String query = "SELECT ID, NIF, ADJUDICATARIO,OBJETO_GENERICO, OBJETO, FECHA_ADJUDICACION, IMPORTE, PROVEEDORES_CONSULTADOS, OTRO FROM "
            + tableName + ";";

    /**
     * Constructor que acepta la URL de la base de datos, el nombre de usuario y la
     * contraseña.
     * 
     * @param _jdbcURL  La URL de la base de datos.
     * @param _userName El nombre de usuario para la conexión a la base de datos.
     * @param _password La contraseña para la conexión a la base de datos.
     */
    public DbToXml(String _jdbcURL, String _userName, String _password) {

        jdbcURL = _jdbcURL;
        userName = _userName;
        password = _password;

        createNewXml();

    }

    /**
     * Crea un nuevo documento XML a partir de los datos de la base de datos y lo
     * guarda en un archivo.
     */
    private void createNewXml() {

        try (Connection connectionToDb = DriverManager.getConnection(jdbcURL, userName, password);
                PreparedStatement preparedStatement = connectionToDb.prepareStatement(query)) {

            // Crea un nuevo documento XML
            Document newXml = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            // Extrae los datos de la base de datos y los agrega al documento XML
            extractDataToXml(newXml, preparedStatement);

            // Guarda el documento XML en un archivo
            saveNewXml(newXml);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Extrae los datos de la base de datos y los agrega al documento XML.
     * 
     * @param newXml            El documento XML al que se agregarán los datos.
     * @param preparedStatement La declaración preparada para la consulta SQL.
     * @throws SQLException Si hay un error al ejecutar la consulta SQL.
     */
    private void extractDataToXml(Document newXml, PreparedStatement preparedStatement) throws SQLException {

        Element xmlOutputRootElement = newXml.createElement("Root");
        newXml.appendChild(xmlOutputRootElement);

        ResultSet resultSet = null;
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

            Element xmlOutElementContrato = newXml.createElement("Contrato");
            xmlOutputRootElement.appendChild(xmlOutElementContrato);

            Attr xmlOutAttrId = newXml.createAttribute("id");
            xmlOutAttrId.setValue(resultSet.getString(1));
            xmlOutElementContrato.setAttributeNode(xmlOutAttrId);

            String[] elementNames = new String[] { "NIF", "ADJUDICATARIO", "OBJETO_GENERICO", "OBJETO",
                    "FECHA_ADJUDICACION", "IMPORTE", "PROVEEDORES_CONSULTADOS", "OTRO" };

            for (int i = 0; i < elementNames.length; i++) {

                // Crea elementos XML para cada dato de la base de datos
                createXmlElement(newXml, elementNames[i], i + 2, resultSet, xmlOutElementContrato);

            }

        }

    }

    /**
     * Crea un elemento XML y lo agrega al elemento padre.
     * 
     * @param newXml                El documento XML al que se agregará el elemento.
     * @param elementName           El nombre del elemento XML.
     * @param rsIndex               El índice del conjunto de resultados.
     * @param resultSet             El conjunto de resultados de la consulta SQL.
     * @param xmlOutElementContrato El elemento padre al que se agregará el nuevo
     *                              elemento.
     * @throws DOMException Si hay un error al crear el elemento XML.
     * @throws SQLException Si hay un error al obtener el valor del conjunto de
     *                      resultados.
     */
    private void createXmlElement(Document newXml, String elementName, int rsIndex, ResultSet resultSet,
            Node xmlOutElementContrato) throws DOMException, SQLException {

        Element xmlOutElement = newXml.createElement(elementName);
        xmlOutElement.setTextContent(resultSet.getString(rsIndex));
        xmlOutElementContrato.appendChild(xmlOutElement);

    }

    /**
     * Guarda el documento XML en un archivo.
     * 
     * @param newXml El documento XML que se guardará.
     * @throws TransformerException                 Si hay un error al transformar
     *                                              el documento XML.
     * @throws TransformerFactoryConfigurationError Si hay un error de configuración
     *                                              en la fábrica de
     *                                              transformadores.
     */
    private void saveNewXml(Document newXml) throws TransformerFactoryConfigurationError, TransformerException {

        newXml.normalizeDocument();

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        DOMSource source = new DOMSource(newXml);
        StreamResult result = new StreamResult(new File(outFilePath));
        transformer.transform(source, result);

    }

}
