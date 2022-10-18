package XML;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*Crea un método que percorra de forma recursiva a árbore mostrando o nome dos
nodos de forma tabulada de forma similar á seguinte (sendo o número do
principio o tipo de nodo)
 */
public class ArbolDOMRecursivo {

    /**
     * Crea o documento da estructura xml para traballar con el
     * @param path ruta do arquivo
     * @return o arquivo coa estrutura xml
     */
    public static Document CreateDOMBuild(String path) {
        Document doc = null;
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.parse(path);
        } catch (Exception e) {
            System.err.println("There was a mistake making the document, error 404");
        }
        return doc;
    }

    /**
     * Mostra na xanela a estrutura xml seguindo o formato
     * @param doc arquivo coa estrutura xml
     * @param root nodo con fillos
     * @param treeDeepness a profundidade na que se encontra anidado un nodo
     */
    public static void ShowDOMElements(Document doc, Node root, int treeDeepness) {

        if (root == doc.getFirstChild()) {
            System.out.println(root.getNodeType() + " " + root.getNodeName());
        }
        NodeList rootChildren = root.getChildNodes();
        for (int i = 0; i < rootChildren.getLength(); i++) {
            System.out.print(" ");
            for (int j = 0; j < treeDeepness; j++) {
                System.out.print("\t");
            }
            System.out.println(rootChildren.item(i).getNodeType() + " " + rootChildren.item(i).getNodeName());
            if (rootChildren.item(i).getNodeType() == Node.ELEMENT_NODE) {
                ShowDOMElements(doc, rootChildren.item(i), treeDeepness + 1);
            }
        }
    }

    public static void main(String[] args) {
        String ruta = "C:\\Users\\Pablo\\Desktop\\programas java\\XML\\peliculas.xml";
        Document doc = CreateDOMBuild(ruta);
        Node root = doc.getFirstChild();
        ShowDOMElements(doc, root, 0);

    }

}
