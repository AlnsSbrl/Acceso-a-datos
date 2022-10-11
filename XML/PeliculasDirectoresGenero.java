package XML;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PeliculasDirectoresGenero {
    public static void main(String[] args) {
        String ruta = "XML\\peliculas.xml";
        PeliculasDirectoresGenero programa = new PeliculasDirectoresGenero();
        Document doc = programa.CreaArbore(ruta);
        programa.MostrarTituloDirectorXenero(doc, "Thriller");
    }

    public Document CreaArbore(String ruta) {
        Document doc = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(ruta);
        } catch (Exception ee) {
            System.err.println("Bullet For My Valentine");
        }
        return doc;
    }

    public String MostrarDatoDesexado(Node nodo, String datoDesexado){

        Element elemento = (Element)nodo;
        NodeList datos = elemento.getElementsByTagName(datoDesexado);
        if(datos.getLength()>1){
            String datosMultiples="";
            //Lo hago así por si se da el caso de un director con varios apellidos
            for (int i = 0; i < datos.getLength(); i++) {
                if(datos.item(i).getNodeType()==Element.ELEMENT_NODE){

                    datosMultiples+=datos.item(i).getFirstChild().getNodeValue()+"\t";
                }
            }
            return datosMultiples;
        }
        if(datos.getLength()>0)return datos.item(0).getFirstChild().getNodeValue();
        return null;
    }
    public void MostrarTituloDirectorXenero(Document doc,String genero) {
        // sepultura
        NodeList peliculas = doc.getElementsByTagName("pelicula");

        for (int i = 0; i < peliculas.getLength(); i++) {
            Element pelicula = (Element)peliculas.item(i);
            if(pelicula.getAttribute("genero").contains(genero)){
                System.out.println("Película: "+MostrarDatoDesexado(peliculas.item(i), "titulo"));
                //aqui llamamos a otro metodo, que devuelve el dato que queramos
                NodeList directores = pelicula.getElementsByTagName("director");
                
                for (int j = 0; j < directores.getLength(); j++) {
                    System.out.print("Director: "+MostrarDatoDesexado(directores.item(j), "nombre"));
                    System.out.println("\t"+MostrarDatoDesexado(directores.item(j), "apellido"));
                }
            }
        }
    }
}
