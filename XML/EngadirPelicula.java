package XML;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public class EngadirPelicula {

    public static void Engadir(Document doc,String titulo,String[][] atributos,String[][] directores){
        Element nodoPelicula = doc.createElement("pelicula");
        for (int i = 0; i < atributos.length; i++) {
            nodoPelicula.setAttribute(atributos[i][0], atributos[i][1]);
        }
        nodoPelicula.appendChild(doc.createTextNode("\n"));
        Element nodoTitulo = doc.createElement("titulo");
        Text textNodoTitulo = doc.createTextNode(titulo);
        nodoTitulo.appendChild(textNodoTitulo);
        nodoPelicula.appendChild(nodoTitulo);
        for (int i = 0; i < directores.length; i++) {
            nodoPelicula.appendChild(doc.createTextNode("\n"));
            Element nodoDirector = doc.createElement("director");
            nodoDirector.appendChild(doc.createTextNode("\n"));
            Element nodoNombre = doc.createElement("nombre");
            Text textNodoNombre = doc.createTextNode(directores[i][0]);
            nodoNombre.appendChild(textNodoNombre);
            Element nodoApellido = doc.createElement("apellido");
            Text textNodoApellido = doc.createTextNode(directores[i][1]);
            nodoApellido.appendChild(textNodoApellido);
            nodoDirector.appendChild(nodoNombre);
            nodoDirector.appendChild(doc.createTextNode("\n"));
            nodoDirector.appendChild(nodoApellido);
            nodoDirector.appendChild(doc.createTextNode("\n"));
            nodoPelicula.appendChild(nodoDirector);
            nodoPelicula.appendChild(doc.createTextNode("\n"));
        }
        Node raiz = doc.getFirstChild();
        raiz.appendChild(nodoPelicula);
    }

    public static void main(String[] args) {
        String path="XML\\peliculas.xml";
        Document doc =CrearArbore.CreaArbore(path);
        String[][] atributos =new String[2][2];
        atributos[0][0]="año";
        atributos[0][1]="1987";
        atributos[1][0]="idioma";
        atributos[1][1]="en";
        String[][] director = new String[1][2];
        director[0][0]="John";
        director[0][1]="Tiernan";
        Engadir(doc, "Depredador", atributos, director);
        atributos[0][1]="2015";
        director[0][0]="Alfonso";
        director[0][1]="Gomez Rejon";
        Engadir(doc, "Me and Earl and the dying girl", atributos, director);
        GardarArbore.grabarDOM(doc, path);
    }
}
