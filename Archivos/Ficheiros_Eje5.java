import java.io.File;
import java.util.Scanner;

public class Ficheiros_Eje5 {
    
    public static void atopaLinea(File arquivo, String filtro){

        String seguintePalabra="";
        int linea=1;
        try (Scanner sc= new Scanner(arquivo)) {
            
            while(sc.hasNextLine()){
                seguintePalabra=""+sc.nextLine();
                if(seguintePalabra.contains(filtro)){
                    System.out.println("A palabra "+filtro+" atopouse na liña "+linea);
                }
    
                linea++;
            }
        } catch (Exception e) {
           
        }
        
    }
    public static void main(String[] args) {
        
        String path="C:\\Users\\Pablo\\Downloads\\quijote.txt";
        File quijote = new File(path);
        String filtro = "Dulcinea";
        atopaLinea(quijote, filtro);
        
    }
}
