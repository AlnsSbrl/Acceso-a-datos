package XML;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class BorraPelicula {
    
    public static void BorrarPelicula(Document doc, String pelicula){
        NodeList peliculas = doc.getElementsByTagName("pelicula");
        for (int i = 0; i < peliculas.getLength(); i++) {
            Node titulo = ((Element)peliculas.item(i)).getElementsByTagName("titulo").item(0);
            if (titulo.getFirstChild().getNodeValue().contains(pelicula)) {
                System.out.println("holaaa");
                titulo.getParentNode().getParentNode().removeChild(peliculas.item(i));           
            }
        }
       
    }
    public static void main(String[] args){

        String path = "XML\\peliculas.xml";
        Document doc = CrearArbore.CreaArbore(path);
        String[][] atributos =new String[2][2];
        atributos[0][0]="año";
        atributos[0][1]="1987";
        atributos[1][0]="idioma";
        atributos[1][1]="en";
        String[][] director = new String[1][2];
        director[0][0]="John";
        director[0][1]="Tiernan";
        EngadirPelicula.Engadir(doc, "Depredador", atributos, director);
        atributos[0][1]="2015";
        director[0][0]="Alfonso";
        director[0][1]="Gomez Rejon";
        EngadirPelicula.Engadir(doc, "Me and Earl and the dying girl", atributos, director);
        BorrarPelicula(doc, "Me and Earl and the dying girl");
        GardarArbore.grabarDOM(doc, path);
    }
}
