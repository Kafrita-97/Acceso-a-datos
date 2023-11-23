package proyectoAAD;

import java.io.File;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlToDb {

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

    /**
     * Constructor que acepta la URL de la base de datos, el nombre de usuario y la
     * contraseña.
     * 
     * @param _jdbcURL  La URL de la base de datos.
     * @param _userName El nombre de usuario para la conexión a la base de datos.
     * @param _password La contraseña para la conexión a la base de datos.
     */
    public XmlToDb(String _jdbcURL, String _userName, String _password) {

        jdbcURL = _jdbcURL;
        userName = _userName;
        password = _password;

        insertDataToDB();

    }

    /**
     * Inserta datos en la base de datos a partir de un archivo XML.
     */
    private void insertDataToDB() {

        try (Connection connectionToDB = DriverManager.getConnection(jdbcURL, userName, password)) {

            // Crea la tabla en la base de datos
            createTable(connectionToDB);
            // Inserta datos en la tabla desde el archivo XML
            insertData(connectionToDB);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Crea la tabla en la base de datos.
     * 
     * @param connectionToDB La conexión a la base de datos.
     */
    private void createTable(Connection connectionToDB) {

        try (PreparedStatement preparedStatement = connectionToDB.prepareStatement(createTableSQL)) {

            // Ejecuta la consulta para crear la tabla
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Inserta datos en la base de datos desde un archivo XML.
     * 
     * @param connection La conexión a la base de datos.
     */
    private void insertData(Connection connection) {

        try {

            // Parsea el archivo XML
            Document docXml = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(FilePath));

            // Obtiene la lista de nodos "Contrato"
            NodeList contratoNodeList = docXml.getElementsByTagName("Contrato");

            // Itera sobre los nodos "Contrato" e inserta los datos en la base de datos
            for (int i = 0; i < contratoNodeList.getLength(); i++) {

                insertNodeData(connection, contratoNodeList.item(i));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Inserta los datos de un nodo en la base de datos.
     * 
     * @param connection    La conexión a la base de datos.
     * @param node          El nodo que contiene los datos a insertar.
     * @throws SQLException Si hay un error al ejecutar la consulta SQL.
     */
    private void insertNodeData(Connection connection, Node node) throws SQLException {

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);) {

            // Inicializa el campo "OTRO" como nulo; se cambiará en el switch si es
            // necesario
            preparedStatement.setNull(9, java.sql.Types.VARCHAR);

            // Obtiene la lista de nodos del contenido del nodo "Contrato"
            NodeList contratoContentNodeList = node.getChildNodes();

            // Itera sobre los nodos del contenido del "Contrato" e inserta los datos en la
            // base de datos
            for (int j = 0; j < contratoContentNodeList.getLength(); j++) {

                Node childNode = contratoContentNodeList.item(j);

                // Establece los parámetros en la consulta preparada
                setPreparedStatementParam(preparedStatement, childNode);

            }

            // Ejecuta la consulta para insertar los datos en la base de datos
            preparedStatement.executeUpdate();

        }

    }

    /**
     * Establece los parámetros de un PreparedStatement en función del nombre del nodo.
     *
     * @param preparedStatement         El PreparedStatement al que se le establecerán los parámetros.
     * @param node                      El nodo que contiene la información a ser asignada como parámetro.
     * @throws DOMException             Si ocurre una excepción relacionada con el modelo de objeto de documento (DOM).
     * @throws SQLException             Si hay un problema al establecer los parámetros en el PreparedStatement.
     */
    private void setPreparedStatementParam(PreparedStatement preparedStatement, Node node)
            throws DOMException, SQLException {

        Map<String, Integer> mapNodeNameIndex = Map.of(
            "NIF", 1,
            "ADJUDICATARIO", 2,
            "OBJETO_GENERICO", 3,
            "OBJETO", 4,
            "FECHA_ADJUDICACION", 5,
            "IMPORTE", 6,
            "PROVEEDORES_CONSULTADOS", 7,
            "TIPO_CONTRATO", 8,
            "OTRO", 9);

        if (mapNodeNameIndex.containsKey(node.getNodeName())) {

            preparedStatement.setString(mapNodeNameIndex.get(node.getNodeName()), node.getTextContent());

        }
    
    }

}
