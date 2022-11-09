import java.util.HashSet;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ParserSAXMaisDunDirector extends DefaultHandler {
    
    static String titulo="";
    static boolean esTitulo=false;
    static int numDirectores=0;
    static HashSet<String> peliculas = new HashSet<>();
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
       
        
        if(qName.equals("pelicula")){
            numDirectores=0;
           
        }
        if(qName.equals("director")){
            numDirectores++;
            if(numDirectores>1 && !peliculas.contains(titulo)){
                peliculas.add(titulo);
            }
        }
        if(qName.equals("titulo")){
            esTitulo=true;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if(esTitulo){
            titulo=new String(ch,start,length);
            esTitulo=false;
        }
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println(peliculas.toString());
    }
}
