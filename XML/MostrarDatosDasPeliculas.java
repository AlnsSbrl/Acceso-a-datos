package XML;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MostrarDatosDasPeliculas {
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


    //cambiar esto, porque si no me da un bucle triple y queda un poco feo
    public static void MostraDatos(Document doc){
        NodeList peliculas = doc.getElementsByTagName("pelicula");
        for (int i = 0; i < peliculas.getLength(); i++) {
            NodeList directores = ((Element)peliculas.item(i)).getElementsByTagName("director");
            NodeList titulo = ((Element)peliculas.item(i)).getElementsByTagName("titulo");
            System.out.println("Título: \t"+titulo.item(0).getFirstChild().getNodeValue());
            System.out.println("\t Xénero: \t"+((Element)peliculas.item(i)).getAttribute("genero"));
            for (int j = 0; j < directores.getLength(); j++) {
                System.out.println("\t Nome director "+(int)(j+1)+":\t"+MostrarDatoDesexado(directores.item(j), "nombre"));
                System.out.println("\t Apelido director "+(int)(j+1)+":\t"+MostrarDatoDesexado(directores.item(j), "apellido"));
            }
        }
    }

    /**
     * Devolve unha cadea co valor(ou valores, en caso de teren varios apelidos) do nodo desexado
     * @param nodo nodo do cal queremos o dato
     * @param datoDesexado o dato (ou datos) desexados
     * @return unha cadea cos valores dos datos desexados
     */
    public static String MostrarDatoDesexado(Node nodo, String datoDesexado){
        Element elemento = (Element)nodo;
        NodeList datos = elemento.getElementsByTagName(datoDesexado);
        if(datos.getLength()>1){
            String datosMultiples="";
           
            for (int i = 0; i < datos.getLength(); i++) {
                if(datos.item(i).getNodeType()==Element.ELEMENT_NODE){
                    datosMultiples+=datos.item(i).getFirstChild().getNodeValue()+"\t";
                }    
            }
            return datosMultiples;
        }
        if(datos.getLength()>0)return datos.item(0).getFirstChild().getNodeValue();
        return null;
    }

    public static void main(String[] args) {
        String ruta = "XML\\peliculas.xml";
        Document doc = CreaArbore(ruta);
        MostraDatos(doc);
    }
}
