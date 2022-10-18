package XML;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class PeliculasConVariosDirectores {
    
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

    public static void MostraPeliculas(Document doc){
        NodeList directores = doc.getElementsByTagName("director");
        NodeList titulos = doc.getElementsByTagName("titulo");
        System.out.println("Peliculas con mais dun director:");
        for (int i = 0; i < directores.getLength()-1; i++) {
            if(directores.item(i).getParentNode()==directores.item(i+1).getParentNode()){
                for (int j = 0; j < titulos.getLength(); j++) {
                    if(directores.item(i).getParentNode()==titulos.item(j).getParentNode()){
                        System.out.println(titulos.item(j).getFirstChild().getNodeValue());
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        String ruta = "XML\\peliculas.xml";
        Document doc = CreaArbore(ruta);
        MostraPeliculas(doc);
    }
}
