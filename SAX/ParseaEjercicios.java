import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ParseaEjercicios {
    
    public static void main(String[] args) {
        ParserSAXLeePeliculas parserSAX = new ParserSAXLeePeliculas();
        //ParserSAXDirectoresDePelicula parserSAX = new ParserSAXDirectoresDePelicula();
        //ParserSAXMaisDunDirector parserSAX = new ParserSAXMaisDunDirector();
        //ParserSAXContaXeneros parserSAX = new ParserSAXContaXeneros();
        ParseaEjercicios parse = new ParseaEjercicios();
        String pathXML = "peliculas.xml";
        parse.GetSaxGenerico(pathXML, parserSAX);  
    }

    
    // este metodo sirve para procesar cada parserSAX que creemos
    public void GetSaxGenerico(String entradaXML, DefaultHandler parserSAX){
        try{
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            parser.parse(entradaXML, parserSAX);
        }catch(SAXException aa){
            System.err.println("Erro no SAX");
        }catch(ParserConfigurationException o){
            System.err.println("non está dispoñible");
        }catch(IOException as){
            System.err.println("non se puido acceder");
        }
    }
}
