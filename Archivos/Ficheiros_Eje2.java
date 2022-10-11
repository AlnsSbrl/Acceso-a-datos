import java.io.File;

public class Ficheiros_Eje2 {
    public static String metodoMostraDirectorio(File arquivo){

        //no funciona exactamente así, muestra también las CARPETAS
        File[] files = arquivo.listFiles();
        String listado ="";
        for (File file : files) {
           listado = listado +file.getAbsolutePath()+"\n";
            if (file.isDirectory()){
                
                listado = listado + metodoMostraDirectorio(file);
            } 
        }
        return listado;

    }

    public static void main(String[] args) {

        String path = "C:\\Users\\Pablo\\Desktop\\";
        File arquivo = new File(path);
        System.out.println(metodoMostraDirectorio(arquivo));
    }
}
