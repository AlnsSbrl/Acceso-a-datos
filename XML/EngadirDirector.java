package XML;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class EngadirDirector {

    public static void AddDirector(Document doc, String titulo, String[] datosDirector) {
        NodeList peliculas = doc.getElementsByTagName("pelicula");
        for (int i = 0; i < peliculas.getLength(); i++) {
            Element pelicula = (Element) peliculas.item(i);
            NodeList tituloPelicula = pelicula.getElementsByTagName("titulo");
            // realmente tendría que comprobar que coincidiesen más datos,
            // el titulo de la peli no es identificador de la misma
            if (tituloPelicula.item(0).getFirstChild().getNodeValue().equals(titulo)) {
                Node nodoDirector = doc.createElement("director");
                nodoDirector.appendChild(doc.createTextNode("\n"));
                Node nodoNombre = doc.createElement("nombre");
                nodoNombre.appendChild(doc.createTextNode(datosDirector[0]));
                nodoDirector.appendChild(nodoNombre);
                nodoDirector.appendChild(doc.createTextNode("\n"));
                Node nodoApellido = doc.createElement("apellido");
                nodoApellido.appendChild(doc.createTextNode(datosDirector[1]));
                nodoDirector.appendChild(nodoApellido);
                nodoDirector.appendChild(doc.createTextNode("\n"));
                pelicula.appendChild(nodoDirector);
            }
        }
    }

    public static void main(String[] args) {
        String path = "XML\\peliculas.xml";
        Document doc = CrearArbore.CreaArbore(path);
        String[] datosDirector = new String[2];
        datosDirector[0] = "Alfredo";
        datosDirector[1] = "Landa";
        AddDirector(doc, "Dune", datosDirector);
        GardarArbore.grabarDOM(doc, path);
    }
}