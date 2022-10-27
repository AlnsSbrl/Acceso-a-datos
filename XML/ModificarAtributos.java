package XML;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.w3c.dom.Element;

public class ModificarAtributos {

    /**
     * Returns an XML document
     * 
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

    public static void EngadirAtributo(Document doc, String pelicula, String nomeAtributo, String valorAtributo) {
        NodeList peliculas = doc.getElementsByTagName("pelicula");
        boolean existePelicula = false;
        int indicePeli = 0;
        // daría problema de haber varias pelis con el mismo nombre
        for (int i = 0; i < peliculas.getLength(); i++) {
            NodeList titulo = ((Element) peliculas.item(i)).getElementsByTagName("titulo");
            if (titulo.item(0).getFirstChild().getNodeValue().contains(pelicula)) {
                existePelicula = true;
                indicePeli = i;
            }
        }
        if (existePelicula && !((Element) peliculas.item(indicePeli)).hasAttribute(nomeAtributo)) {
            ((Element) peliculas.item(indicePeli)).setAttribute(nomeAtributo, valorAtributo);
        }
    }

    public static void grabarDOM(Document document, String ficheroSalida)
            throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, FileNotFoundException {
        DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
        DOMImplementationLS ls = (DOMImplementationLS) registry.getDOMImplementation("XML 3.0 LS 3.0");
        // Se crea un destino vacio
        LSOutput output = ls.createLSOutput();
        output.setEncoding("UTF-8");
        // Se establece el flujo de salida
        output.setByteStream(new FileOutputStream(ficheroSalida));
        // output.setByteStream(System.out);
        // Permite escribir un documento DOM en XML
        LSSerializer serializer = ls.createLSSerializer();
        // Se establecen las propiedades del serializador
        serializer.setNewLine("\r\n");
        ;
        serializer.getDomConfig().setParameter("format-pretty-print", true);
        // Se escribe el documento ya sea en un fichero o en una cadena de texto
        serializer.write(document, output);
        // String xmlCad=serializer.writeToString(document);
    }

    public static void BorrarAtributo(Document doc, String pelicula, String atributo) {
        NodeList peliculas = doc.getElementsByTagName("pelicula");
        boolean existePelicula = false;
        int indicePeli = 0;

        for (int i = 0; i < peliculas.getLength(); i++) {
            NodeList titulo = ((Element) peliculas.item(i)).getElementsByTagName("titulo");
            if (titulo.item(0).getFirstChild().getNodeValue().contains(pelicula)) {
                existePelicula = true;
                indicePeli = i;
            }
        }
        if (existePelicula && ((Element) peliculas.item(indicePeli)).hasAttribute(atributo)) {
            ((Element) peliculas.item(indicePeli)).removeAttribute(atributo);
        }
    }

    public static void main(String[] args) {
        String ruta = "XML\\peliculas.xml";
        Document doc = CreaArbore(ruta);

        // borraAtributo(doc, "Me and Earl and the dying girl", ruta);
        EngadirAtributo(doc, "Dune", "estreno", "2021");
        BorrarAtributo(doc, "Dune", "genero");

        
        try {
            grabarDOM(doc, "XML\\peliculas.xml");
        } catch (ClassNotFoundException e){
            System.err.println("non se encontrou a clase");
        }catch (InstantiationException a){
            System.err.println("non se puido crear o arquivo");
        }catch(IllegalAccessException mm){
            System.err.println("non se puido acceder ó arquivo");
        } catch(FileNotFoundException e) {
            System.err.println("non se encontrou o arquivo");
        } catch (Exception m){
            System.err.println("algo inesperado aconteceu");
        }
    }
}
