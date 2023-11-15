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

