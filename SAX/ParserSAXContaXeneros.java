import java.util.HashSet;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ParserSAXContaXeneros extends DefaultHandler {
    
    static HashSet<String> generos = new HashSet<>();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if(qName.equals("pelicula")&&!generos.contains(attributes.getValue("genero"))&&attributes.getValue("genero")!=null){
            generos.add(attributes.getValue("genero"));
        }
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println(generos.toString());
    }
}
