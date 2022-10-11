package XML;
//Crea un método que lea 

//o ficheiro peliculas.xml e cree a árbore DOM.

import org.w3c.dom.Element;

//import java.io.Console;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
//import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ExerciciosPeliculasDOM {

    public static Document CreaArbore(String ruta) {
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

    public static void MostrarPeliculasSuboptimalForm(Document doc) {
        Node raiz, pelicula, nodoAux;
        NodeList hijosDeRaiz, datosPelicula;
        raiz = doc.getFirstChild(); // como es un document, esto ES la filmoteca
        System.err.printf("El nodo ess: %s \n", raiz.getNodeName());
        hijosDeRaiz = raiz.getChildNodes();
        // conseguimos todos los hijos de document, que
        // APARTE de peliculas, tiene nodos texto (\n, espacios entre etiquetas
        // <peliculas>...etc)
        // entonces tendremos que diferenciar

        for (int i = 0; i < hijosDeRaiz.getLength(); i++) {

            // vamos uno a uno con los elementos hijo
            pelicula = hijosDeRaiz.item(i);

            if (pelicula.getNodeType() == Node.ELEMENT_NODE) {
                // si es nodo, aka no es el texto, entramos en el bucle
                System.out.println("________________");
                // y conseguimos sus hijos, en el caso de que tenga
                datosPelicula = pelicula.getChildNodes();
                for (int j = 0; j < datosPelicula.getLength(); j++) {
                    nodoAux = datosPelicula.item(j);
                    // y aqui nos ayudamos de un nodo auxiliar para poder visualizar
                    // los datos
                    // claro, solo funciona con los nodos que tienen texto dentro,
                    // que no tienen OTROS nodos extra adentro

                    // ejemplo: <titulo>Modern Warfare 4<titulo>
                    // no funca con: <juego>
                    // <director>Shinichi Masuda<director>
                    // <año>2131<año>
                    // <juego>
                    if (nodoAux.getNodeType() == Node.ELEMENT_NODE) {

                        System.out.println(nodoAux.getNodeName() + " :" + nodoAux.getFirstChild().getNodeValue());
                        if (nodoAux.getLastChild() != nodoAux.getFirstChild()) {
                            NodeList illoMasCosasAquiIDK = nodoAux.getChildNodes();
                            for (int k = 0; k < illoMasCosasAquiIDK.getLength(); k++) {
                                Node nodoAux2Punto0 = illoMasCosasAquiIDK.item(k);
                                if (nodoAux2Punto0.getNodeType() == Node.ELEMENT_NODE) {
                                    System.out.println(nodoAux2Punto0.getNodeName() + ": "
                                            + nodoAux2Punto0.getFirstChild().getNodeValue());
                                }
                            }
                        }
                    }
                }
            }
        }

        // NodeList elementosDeLaLista = doc.getElementsByTagName("pelicula");

        // for (int i = 0; i < elementosDeLaLista.getLength(); i++) {
        // System.out.println(elementosDeLaLista.item(i).getFirstChild().getTextContent());
        // }

    }

    public void getDatosJaviMal(Document doc) {
        NodeList titulos = doc.getElementsByTagName("titulo");
        NodeList nombre = doc.getElementsByTagName("nombre");
        NodeList apellidos = doc.getElementsByTagName("apellidos");

        for (int i = 0; i < titulos.getLength(); i++) {
            System.out.println("_________");
            System.out.println(titulos.item(i).getFirstChild().getNodeValue());
            System.out.println(nombre.item(i).getFirstChild().getNodeValue());
            System.out.println(apellidos.item(i).getFirstChild().getNodeValue());
        }

    }

    // A ver, esto no está del todo completo, hacer más tarde y darle mas vueltas xd
    // mirar google docssssss
    public void getDatosJaviBienQuestion(Document doc) {
        NodeList pelis = doc.getElementsByTagName("pelicula");
        // NodeList nombre=doc.getElementsByTagName("nombre");
        // NodeList apellidos=doc.getElementsByTagName("apellido");

        for (int i = 0; i < pelis.getLength(); i++) {
            Element peli = (Element) pelis.item(i);
            System.out.println("____");
            NodeList titulos = peli.getElementsByTagName("titulo");

            if (titulos.getLength() > 0) {
                System.out.println(titulos.item(0).getFirstChild().getNodeValue());
                ;
            }
            NodeList director = peli.getElementsByTagName("director");

            for (int j = 0; j < director.getLength(); j++) {
                NodeList nombre = ((Element) director.item(j)).getElementsByTagName("nombre");
                NodeList apellido = ((Element) director.item(j)).getElementsByTagName("apellido");

                if (nombre.getLength() > 0)
                    System.out.println("nombre: " + nombre.item(0).getFirstChild().getNodeValue());
                if (apellido.getLength() > 0)
                    System.out.println(apellido.item(0).getFirstChild().getNodeValue());
            }
        }

    }

    public static void MostrarOsTitulosDasPeliculas(Document doc) {
        NodeList titulos = doc.getElementsByTagName("titulo");
        for (int i = 0; i < titulos.getLength(); i++) {
            System.out.println(titulos.item(i).getFirstChild().getNodeValue());
        }
    }

    public static void MostrarTituloDirectorXenero(Document doc,String genero) {
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

    public static String MostrarDatoDesexado(Node nodo, String datoDesexado){

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

    public static void main(String[] args) {
        String ruta = "XML\\peliculas.xml";
        Document doc = CreaArbore(ruta);
        // MostrarOsTitulosDasPeliculas(doc);
        //MostrarPeliculasSuboptimalForm(doc);
        MostrarTituloDirectorXenero(doc, "Thriller");
    }
}
/*
 * pasa string de la peli que quieres los datos
 * 
 * consigues la lista de titulos
 * 
 * recorre los valores hasta que encuentra que casa
 * guarda el elemento padre (LA PELICULA)
 * 
 * 
 * 
 * 
 * 
 */