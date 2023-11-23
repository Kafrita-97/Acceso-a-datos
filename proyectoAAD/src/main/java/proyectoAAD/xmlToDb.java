package proyectoAAD;

import java.io.File;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class xmlToDb {

    private String jdbcURL;
    private String userName;
    private String password;

    private final String FilePath = "/Users/juanma/Library/Mobile Documents/com~apple~CloudDocs/V/Estudios/DAM/2º/Acceso a datos/Trabajo voluntario - Acceso a datos/Proyecto/proyectoAAD/src/main/resources/contratos-adjudicados-jun-22.xml";

    private final String tableName = "CONTRATOS";
    private final String createTableSQL = "CREATE TABLE " + tableName
            + " (ID INT AUTO_INCREMENT PRIMARY KEY,NIF VARCHAR(10), ADJUDICATARIO VARCHAR (200),OBJETO_GENERICO VARCHAR (200), OBJETO VARCHAR (200), FECHA_ADJUDICACION VARCHAR(200), IMPORTE VARCHAR(200), PROVEEDORES_CONSULTADOS VARCHAR(200), TIPO_CONTRATO VARCHAR(200), OTRO VARCHAR(200));";
    private final String insertQuery = "INSERT INTO " + tableName +
            "(NIF, ADJUDICATARIO, OBJETO_GENERICO, OBJETO, FECHA_ADJUDICACION, IMPORTE, PROVEEDORES_CONSULTADOS, TIPO_CONTRATO, OTRO) "
            +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public xmlToDb(String _jdbcURL, String _userName, String _password) {
        
        jdbcURL = _jdbcURL;
        userName = _userName;
        password = _password;

        insertDataToDB();

    }

    private void insertDataToDB() {

        try (Connection connectionToDB = DriverManager.getConnection(jdbcURL, userName, password)) {

            createTable(connectionToDB);
            insertData(connectionToDB);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void createTable(Connection connectionToDB) {

        try (PreparedStatement preparedStatement = connectionToDB.prepareStatement(createTableSQL)) {

            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void insertData(Connection connection) {

        try {

            Document docXml = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(FilePath));

            // en este nodelist se guardan cada uno de los contratos
            NodeList contratoNodeList = docXml.getElementsByTagName("Contrato");

            // Se recorre cada contrato del nodelist
            for (int i = 0; i < contratoNodeList.getLength(); i++) {

                insertNodeData(connection, contratoNodeList.item(i));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void insertNodeData(Connection connection, Node node) throws SQLException {

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);) {

            // al haber contratos que no tienen el campo "otro", primero lo establezco a
            // null y en el casi de que sí lo tenga, se cambiará en el switch
            preparedStatement.setNull(9, java.sql.Types.VARCHAR);

            NodeList contratoContentNodeList = node.getChildNodes();

            for (int j = 0; j < contratoContentNodeList.getLength(); j++) {

                Node nodeFinal = contratoContentNodeList.item(j);

                setPreparedStatementParam(preparedStatement, nodeFinal);

            }

            preparedStatement.executeUpdate();

        }
        
    }

    private void setPreparedStatementParam(PreparedStatement preparedStatement, Node node)
            throws DOMException, SQLException {

        switch (node.getNodeName()) {

            case "NIF":
                preparedStatement.setString(1, node.getTextContent());
                break;

            case "ADJUDICATARIO":
                preparedStatement.setString(2, node.getTextContent());
                break;

            case "OBJETO_GENERICO":
                preparedStatement.setString(3, node.getTextContent());
                break;

            case "OBJETO":
                preparedStatement.setString(4, node.getTextContent());
                break;

            case "FECHA_ADJUDICACION":
                preparedStatement.setString(5, node.getTextContent());
                break;

            case "IMPORTE":
                preparedStatement.setString(6, node.getTextContent());
                break;

            case "PROVEEDORES_CONSULTADOS":
                preparedStatement.setString(7, node.getTextContent());
                break;

            case "TIPO_CONTRATO":
                preparedStatement.setString(8, node.getTextContent());
                break;

            case "OTRO":
                preparedStatement.setString(9, node.getTextContent());
                break;

            default:
                break;

        }

    }

}
