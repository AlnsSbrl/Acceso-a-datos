import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

//tenemos que generar el parser que lanza los eventos que queramos
//ESTE parser es lo que tenemos que configurar para cada ejercicio segun nos interese

public class ParserSAXLeePeliculas extends DefaultHandler {

    static int profundidad = 1;
    static boolean nodoAbierto = false;
    static boolean pasouTexto = false;
    static boolean cierresSeguidos=false;

    @Override
    public void startDocument() throws SAXException {
        System.out.println("Comienzo a leer");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        cierresSeguidos=false;
        for (int i = 0; i < profundidad; i++) {
            System.out.print("\t");
        }
        System.out.print("<" + qName + ">"+profundidad);
        profundidad++;

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        if(cierresSeguidos){
            profundidad--;
        }
        cierresSeguidos=true;
        for (int i = 0; i < profundidad && !pasouTexto; i++) {
            System.err.print("\t");
        }
        pasouTexto = false;
        System.err.print("</" + qName + ">"+profundidad);
        profundidad--;

    }

    @Override
    public void endDocument() throws SAXException {
        System.err.println(".............................");
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String valorNodoTexto = new String(ch, start, length);
        System.out.print(valorNodoTexto);
        pasouTexto = true;
    }
}