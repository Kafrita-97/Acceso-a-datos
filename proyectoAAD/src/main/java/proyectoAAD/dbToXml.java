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

public class dbToXml {

    private String jdbcURL;
    private String userName;
    private String password;

    private final String outFilePath = "/Users/juanma/Library/Mobile Documents/com~apple~CloudDocs/V/Estudios/DAM/2º/Acceso a datos/Trabajo voluntario - Acceso a datos/modified-contratos-adjudicados-jun-22.xml";

    private final String tableName = "CONTRATOS";
    private final String query = "SELECT ID, NIF, ADJUDICATARIO,OBJETO_GENERICO, OBJETO, FECHA_ADJUDICACION, IMPORTE, PROVEEDORES_CONSULTADOS, OTRO FROM " + tableName + ";";

    public dbToXml(String _jdbcURL, String _userName, String _password) {
        
        jdbcURL = _jdbcURL;
        userName = _userName;
        password = _password;
        
        createNewXml();

    }

    private void createNewXml() {

        try (Connection connectionToDb = DriverManager.getConnection(jdbcURL, userName, password);
                PreparedStatement preparedStatement = connectionToDb.prepareStatement(query)) {

            Document newXml = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            extractDataToXml(newXml, preparedStatement);

            saveNewXml(newXml);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

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

                createXmlElement(newXml, elementNames[i], i + 2, resultSet, xmlOutElementContrato);

            }

        }

    }

    private void createXmlElement(Document newXml, String elementName, int rsIndex, ResultSet resultSet,
            Node xmlOutElementContrato) throws DOMException, SQLException {

        Element xmlOutElement = newXml.createElement(elementName);
        xmlOutElement.setTextContent(resultSet.getString(rsIndex));
        xmlOutElementContrato.appendChild(xmlOutElement);

    }

    private void saveNewXml(Document newXml) throws TransformerFactoryConfigurationError, TransformerException {

        newXml.normalizeDocument();

        // clases necesarias finalizar la creación del archivo XML
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        DOMSource source = new DOMSource(newXml);
        StreamResult result = new StreamResult(new File(outFilePath));
        transformer.transform(source, result);

    }

}
