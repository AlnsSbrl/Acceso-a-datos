package XML;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class CrearArbore {
    
    /**
     * Returns an XML document
     * @param ruta Path of the xml file
     * @return an XML to work with
     */
    public static Document CreaArbore(String ruta) {
        Document doc = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(ruta);
        } catch (Exception ee) {
            System.err.println("We couldn't make the xml document");
        }
        return doc;
    }

    public static void main(String[] args) {
        String ruta = "C:\\Users\\Pablo\\Desktop\\programas java\\XML\\peliculas.xml";
        Document doc = CreaArbore(ruta);
    }

}
