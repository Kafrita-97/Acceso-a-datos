import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
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

            /* for (int i = 0; i < XMLNodeList.getLength(); i++) {
                System.out.println((XMLNodeList.item(i)).getTextContent());
            } */
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
