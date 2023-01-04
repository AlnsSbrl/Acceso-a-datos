import java.util.ArrayList;
import java.util.Hashtable;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ParserSAXDirectoresDePelicula extends DefaultHandler {

    static boolean escribeTexto = false;
    static boolean esTitulo = false;
    static String nombreAtributo = "";
    static String tituloPelicula = "";
    static Hashtable<String, ArrayList<String>> peliculas = new Hashtable<>();
    static ArrayList<String> datosPeliculas;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if (qName.equals("pelicula")) {
            datosPeliculas=new ArrayList<>();
            if (attributes.getValue("genero") != null) {
                datosPeliculas.add("Genero: " + attributes.getValue("genero"));
            }
        } else if (qName.equals("titulo")) {
            esTitulo = true;
        } else if (qName.equals("nombre") || qName.equals("apellido")) {
            escribeTexto = true;
            nombreAtributo = qName;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("pelicula")) {
            peliculas.put(tituloPelicula, datosPeliculas);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (escribeTexto) {
            datosPeliculas.add(nombreAtributo + ": " + new String(ch, start, length));
            escribeTexto = false;
        } else if (esTitulo) {
            tituloPelicula = new String(ch, start, length);
            esTitulo = false;
        }
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println(peliculas.toString());
    }
}
