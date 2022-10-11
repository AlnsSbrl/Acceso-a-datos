package XML;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class TitulosPeliculas {
    
    public Document CreaArbore(String ruta) {
        Document doc = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(ruta);
        } catch (Exception ee) {
            System.err.println("Bullet For My Valentine");
        }
        return doc;
    }

    public void conseguirTitulosPeliculas(Document doc) {
        NodeList titulos = doc.getElementsByTagName("titulo");
        for (int i = 0; i < titulos.getLength(); i++) {
            System.out.println(titulos.item(i).getFirstChild().getNodeValue());
        }
        
    }

    public static void main(String[] args){
        String ruta = "XML\\peliculas.xml";
        TitulosPeliculas programa = new TitulosPeliculas();
        Document doc = programa.CreaArbore(ruta);
        programa.conseguirTitulosPeliculas(doc);
    }
}
