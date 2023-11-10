import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XLMTreatment {
    private String XMLPath;

    public XLMTreatment (String _XMLPath) {
        XMLPath = _XMLPath;
    }

    public void readXLM() {
        File xmlFile = new File(XMLPath);
        try {
            Document XMLDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
            NodeList XMLNodeList = XMLDoc.getElementsByTagName("Row");

            for (int i = 0; i < XMLNodeList.getLength(); i++) {
                Node nodo = XMLNodeList.item(i);
                System.out.println(nodo.getTextContent());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
