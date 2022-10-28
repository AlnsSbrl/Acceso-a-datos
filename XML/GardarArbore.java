package XML;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.w3c.dom.Document;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

public class GardarArbore {

    public static void grabarDOM(Document document, String ficheroSalida) {

        try {

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
        } catch (ClassNotFoundException e) {
            System.err.println("non se atopou a clase");
        } catch (InstantiationException e) {
            System.err.println("non se puido crear");
        } catch (IllegalAccessException e) {
            System.err.println("non se tivo acceso");
        } catch (FileNotFoundException e) {
            System.err.println("non se atopou o arquivo");
        }
    }

}
