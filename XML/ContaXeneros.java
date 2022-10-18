package XML;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ContaXeneros {
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

    public static void Contar(Document doc){
        Map<String,Integer> listaXeneros = new HashMap<String,Integer>();
        NodeList peliculas = doc.getElementsByTagName("pelicula");
        for (int i = 0; i < peliculas.getLength(); i++) {
            if(!listaXeneros.containsKey(((Element)peliculas.item(i)).getAttribute("genero"))){
                listaXeneros.put(((Element)peliculas.item(i)).getAttribute("genero"), i);
            }
        }
        System.out.println("hai estos xéneros: "+listaXeneros.size());
    }
   
    

    public static void main(String[] args) {
        String ruta = "XML\\peliculas.xml";
        Document doc = CreaArbore(ruta);
        Contar(doc);
    }  
}

