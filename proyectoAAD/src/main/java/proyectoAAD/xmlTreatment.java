package proyectoAAD;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class xmlTreatment {
    private String XMLPath;

    public xmlTreatment (String _xmlPath) {
        XMLPath = _xmlPath;
    }

    public void readXML() {

        File xmlFile = new File(XMLPath);

        try {
            Document xmlDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
            NodeList xmNodeList = xmlDoc.getElementsByTagName("Row");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

//Proyecto del modulo de acceso a datos de 2º de DAM, consiste en leer un archivo XML, almacenar su info en una BD y por último crear un nuevo XML modificando algún dato respecto al archivo original