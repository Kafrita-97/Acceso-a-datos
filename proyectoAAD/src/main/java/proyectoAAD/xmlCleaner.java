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

/* 
 * Esta clase cumple con la unica función de limpiar el archivo xml a tratar,
 * dándole un formato mas adecuado eliminando nodos prescindibles e informacion
 * que no vamos a tratar, asi como nombrar las etiquetas correctamente. Esta 
 * clase se ha hecho a medida para el xml que hay que tratar. Se incluye en el 
 * proyecto igualmente aunque no se vaya a ejecutar junto a este.
 */

public class xmlCleaner {

    private static String inFilePath = "/Users/juanma/Library/Mobile Documents/com~apple~CloudDocs/V/Estudios/DAM/2º/Acceso a datos/Trabajo voluntario - Acceso a datos/Proyecto/proyectoAAD/src/main/resources/contratos-adjudicados-jun-22.xml";
    private static String outFilePath = "/Users/juanma/Library/Mobile Documents/com~apple~CloudDocs/V/Estudios/DAM/2º/Acceso a datos/Trabajo voluntario - Acceso a datos/Proyecto/proyectoAAD/src/main/resources/contratos-adjudicados-jun-22.xml";
    
    public xmlCleaner (String _inFilePath, String _outFilePath){
        inFilePath = _inFilePath;
        outFilePath = _outFilePath;
        clean();
    }

    private void clean() {
        
        try {
           
            Document docXmlInput = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(inFilePath));
            docXmlInput.getDocumentElement().normalize();

            Document docXmlOutout = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            
            // definimos el elemento raíz del documento
            Element xmlOutputRootElement = docXmlOutout.createElement("Root");
            docXmlOutout.appendChild(xmlOutputRootElement);
            
            NodeList rowNodeList = docXmlInput.getElementsByTagName("Row");
            int valueId = 1;

            //Recorre cada row, es decir, cada instancia a guardar
            for (int i = 0; i < rowNodeList.getLength(); i++) {

                Element xmlOutElementContrato = docXmlOutout.createElement("Contrato");
                xmlOutputRootElement.appendChild(xmlOutElementContrato);
                
                Attr xmlOutAttrId = docXmlOutout.createAttribute("id");
                xmlOutAttrId.setValue(Integer.toString(valueId));
                xmlOutElementContrato.setAttributeNode(xmlOutAttrId);

                //Aqui se guardan todos los hijos de cada row
                NodeList rowChildsNodeList = rowNodeList.item(i).getChildNodes();
                //Se recorre cada hijo del row
                int flag = 1;
                for (int j = 0; j < rowChildsNodeList.getLength(); j++) {

                    //Aqui se guarda cada hijo del row en un nodo simple
                    Node dataNode = rowChildsNodeList.item(j).getChildNodes().item(1);

                    //Descartamos los nulos
                    if (dataNode != null && dataNode.getNodeType() == Node.ELEMENT_NODE) {

                        //Se cambia la etiqueta para poder identificar mejor los datos a la hora de introducirlo en la BD
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

            // clases necesarias finalizar la creación del archivo XML
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(docXmlOutout);
            StreamResult result = new StreamResult(new File(outFilePath));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

