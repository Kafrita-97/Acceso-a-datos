package proyectoAAD;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Esta clase se encarga de limpiar un archivo XML específico dándole un formato
 * más adecuado.
 * Elimina nodos prescindibles e información no necesaria y renombra las
 * etiquetas según las necesidades del proyecto.
 * Aunque no se ejecute junto con el proyecto principal, se incluye para
 * referencia y documentación.
 */
public class XmlCleaner {

    private static String inFilePath = "/Users/juanma/Library/Mobile Documents/com~apple~CloudDocs/V/Estudios/DAM/2º/Acceso a datos/Trabajo voluntario - Acceso a datos/Proyecto/proyectoAAD/src/main/resources/contratos-adjudicados-jun-22.xml";
    private static String outFilePath = "/Users/juanma/Library/Mobile Documents/com~apple~CloudDocs/V/Estudios/DAM/2º/Acceso a datos/Trabajo voluntario - Acceso a datos/Proyecto/proyectoAAD/src/main/resources/contratos-adjudicados-jun-22.xml";

    /**
     * Constructor que acepta las rutas de entrada y salida del archivo XML.
     * 
     * @param _inFilePath  La ruta del archivo XML de entrada.
     * @param _outFilePath La ruta del archivo XML de salida después de la limpieza.
     */
    public XmlCleaner(String _inFilePath, String _outFilePath) {
        inFilePath = _inFilePath;
        outFilePath = _outFilePath;
        clean();
    }

    /**
     * Realiza la limpieza del archivo XML especificado.
     */
    private void clean() {

        try {

            // Parsea el archivo de entrada
            Document docXmlInput = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(new File(inFilePath));
            docXmlInput.getDocumentElement().normalize();

            // Crea un nuevo documento XML de salida
            Document docXmlOutout = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            // Define el elemento raíz del documento
            Element xmlOutputRootElement = docXmlOutout.createElement("Root");
            docXmlOutout.appendChild(xmlOutputRootElement);

            // Obtiene la lista de nodos "Row" del documento de entrada
            NodeList rowNodeList = docXmlInput.getElementsByTagName("Row");
            int valueId = 1;

            // Recorre cada nodo "Row" (cada instancia a guardar)
            for (int i = 0; i < rowNodeList.getLength(); i++) {

                // Crea un nuevo elemento "Contrato" en el documento de salida
                Element xmlOutElementContrato = docXmlOutout.createElement("Contrato");
                xmlOutputRootElement.appendChild(xmlOutElementContrato);

                // Añade un atributo "id" al elemento "Contrato"
                Attr xmlOutAttrId = docXmlOutout.createAttribute("id");
                xmlOutAttrId.setValue(Integer.toString(valueId));
                xmlOutElementContrato.setAttributeNode(xmlOutAttrId);

                // Obtiene los hijos de cada nodo "Row"
                NodeList rowChildsNodeList = rowNodeList.item(i).getChildNodes();

                // Recorre cada hijo del nodo "Row"
                int flag = 1;
                for (int j = 0; j < rowChildsNodeList.getLength(); j++) {

                    // Obtiene el nodo de datos (segundo hijo) de cada hijo del nodo "Row"
                    Node dataNode = rowChildsNodeList.item(j).getChildNodes().item(1);

                    // Descartamos los nulos
                    if (dataNode != null && dataNode.getNodeType() == Node.ELEMENT_NODE) {

                        // Cambia la etiqueta para identificar mejor los datos al introducirlos en la BD
                        switch (flag) {
                            case 1:
                                Element xmlOutElementNIF = docXmlOutout.createElement("NIF");
                                xmlOutElementNIF.setTextContent(dataNode.getTextContent().toUpperCase());
                                xmlOutElementContrato.appendChild(xmlOutElementNIF);
                                flag++;

                                break;

                            case 2:
                                Element xmlOutElementAdjud = docXmlOutout.createElement("ADJUDICATARIO");
                                xmlOutElementAdjud.setTextContent(dataNode.getTextContent().toUpperCase());
                                xmlOutElementContrato.appendChild(xmlOutElementAdjud);
                                flag++;

                                break;

                            case 3:
                                Element xmlOutElementObj_Gen = docXmlOutout.createElement("OBJETO_GENERICO");
                                xmlOutElementObj_Gen.setTextContent(dataNode.getTextContent().toUpperCase());
                                xmlOutElementContrato.appendChild(xmlOutElementObj_Gen);
                                flag++;

                                break;

                            case 4:
                                Element xmlOutElementObj = docXmlOutout.createElement("OBJETO");
                                xmlOutElementObj.setTextContent(dataNode.getTextContent().toUpperCase());
                                xmlOutElementContrato.appendChild(xmlOutElementObj);
                                flag++;

                                break;

                            case 5:
                                Element xmlOutElementFecha_adj = docXmlOutout.createElement("FECHA_ADJUDICACION");
                                xmlOutElementFecha_adj.setTextContent(dataNode.getTextContent().toUpperCase());
                                xmlOutElementContrato.appendChild(xmlOutElementFecha_adj);
                                flag++;

                                break;

                            case 6:
                                Element xmlOutElementImporte = docXmlOutout.createElement("IMPORTE");
                                xmlOutElementImporte.setTextContent(dataNode.getTextContent().toUpperCase());
                                xmlOutElementContrato.appendChild(xmlOutElementImporte);
                                flag++;

                                break;

                            case 7:
                                Element xmlOutElementProv_Con = docXmlOutout.createElement("PROVEEDORES_CONSULTADOS");
                                xmlOutElementProv_Con.setTextContent(dataNode.getTextContent().toUpperCase());
                                xmlOutElementContrato.appendChild(xmlOutElementProv_Con);
                                flag++;

                                break;

                            case 8:
                                Element xmlOutElementTipo_Cont = docXmlOutout.createElement("TIPO_CONTRATO");
                                xmlOutElementTipo_Cont.setTextContent(dataNode.getTextContent().toUpperCase());
                                xmlOutElementContrato.appendChild(xmlOutElementTipo_Cont);
                                flag++;

                                break;

                            case 9:
                                Element xmlOutElementOtros = docXmlOutout.createElement("OTRO");
                                xmlOutElementOtros.setTextContent(dataNode.getTextContent().toUpperCase());
                                xmlOutElementContrato.appendChild(xmlOutElementOtros);
                                flag++;

                                break;

                            default:
                                break;
                        }

                    }

                }

                valueId++;

            }

            docXmlOutout.normalizeDocument();

            // Clases necesarias para finalizar la creación del archivo XML
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(docXmlOutout);
            StreamResult result = new StreamResult(new File(outFilePath));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
