package XML;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CambiaNome {
    
    public static void CambiarNome(Document doc, String[][] director){
        //esto es para cambiar el nombre en _todas_ las peliculas en las que ha trabajado
        NodeList peliculas = doc.getElementsByTagName("pelicula");
        for (int i = 0; i < peliculas.getLength(); i++) {
            Element pelicula =(Element) peliculas.item(i);
            NodeList directores = pelicula.getElementsByTagName("director");
            for (int j = 0; j < directores.getLength(); j++) {
                Node nombre = ((Element)directores.item(j)).getElementsByTagName("nombre").item(0);
                Node apellido = ((Element)directores.item(j)).getElementsByTagName("apellido").item(0);
                if(nombre.getFirstChild().getNodeValue().equals(director[0][0])&&apellido.getFirstChild().getNodeValue().equals(director[0][1])){
                    nombre.replaceChild( doc.createTextNode(director[1][0]),nombre.getFirstChild());
                    apellido.replaceChild( doc.createTextNode(director[1][1]),apellido.getFirstChild());
                }
            }
        }
    }
    public static void main(String[] args) {
        String path = "XML\\peliculas.xml";
        Document doc = CrearArbore.CreaArbore(path);
        String[][] director = new String[2][2];

        director[0][0]="Larry";
        director[0][1]="Wachowski";
        director[1][0]="Lana";
        director[1][1]="Wachowski";
        CambiarNome(doc, director);
        GardarArbore.grabarDOM(doc, path);
    }
}
