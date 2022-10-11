import java.io.File;

public class Ficheiros_Eje1 {

    public static String metodoListaArquivos(String path){

        File ficheiro = new File(path);
        String resultado =" Directorios: ";
        if (ficheiro.isDirectory()){
            File[] files = ficheiro.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()){
                    resultado = resultado + String.format(" %s\n", files[i].getName());
                }
            }
            resultado = resultado +"\n Arquivos: ";
             for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()){
                    resultado = resultado + String.format(" %s \n ", files[i].getName());
                }
             }
            }
            return resultado;

    }

    public static void main(String[] args) {

        String path = "C:\\Users\\Pablo\\Downloads\\";
        System.out.println(metodoListaArquivos(path));

    }
}
