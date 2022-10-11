import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Ficheiros_Eje3 {
    
    public static void contaCaracter(File arquivo, char filtro) throws IOException{
        
        int cont=0;
        try (FileReader contido = new FileReader(arquivo)) {
            int caracter;
            do{
                caracter=contido.read();
                if( (char)caracter==filtro){
                    cont++;
                }
            } while (caracter!=-1);
        } catch (Exception e) {
            // TODO: handle exception
        }
        System.out.println("O caracter "+filtro+" aparece "+cont+"veces");

    }
    public static void main(String[] args) throws IOException {
        String path="C:\\Users\\Pablo\\Downloads\\quijote.txt";
        File quijote = new File(path);
        char filtro = 'a';
        contaCaracter(quijote,filtro);
    }
}
