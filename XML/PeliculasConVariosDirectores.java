package XML;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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


    /**
     * Metodo que devolve os titulos das peliculas que teñen dous directores como minimo
     * @deprecated polo método {@link XML.PeliculasConVariosDirectores#MostraPeliculas(Document, int)} na version 2
     * @param doc arbore dom do documento xml
     */
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

    /**
     * Método que devolve os títulos das películas con n ou máis directores
     * @param doc arbore DOM do documento XML
     * @param numDirectores numero de directores
     */
    public static void MostraPeliculas(Document doc, int numDirectores){  
        NodeList peliculas = doc.getElementsByTagName("pelicula");
        System.out.printf("As películas con %d directores ou máis son:\n\n",numDirectores);
        for (int i = 0; i < peliculas.getLength(); i++) {
            Element pelicula = ((Element)peliculas.item(i));
            NodeList directores = pelicula.getElementsByTagName("director");       
            if(directores.getLength()>=numDirectores){
                NodeList titulos = pelicula.getElementsByTagName("titulo");
                System.out.println(titulos.item(0).getFirstChild().getNodeValue());
            }
        }
    }

    public static void main(String[] args) {
        String ruta = "XML\\peliculas.xml";
        Document doc = CreaArbore(ruta);
        MostraPeliculas(doc,2);
    }
}
