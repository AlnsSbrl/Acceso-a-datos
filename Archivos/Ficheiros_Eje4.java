import java.io.File;
import java.io.FileReader;
import java.io.IOException;
//import java.util.Collections;
import java.util.HashMap;

public class Ficheiros_Eje4 {
    
    public static void caracterMaisEmpregado(File arquivo) throws IOException{
        HashMap<Integer, Integer> caracteres = new HashMap<>();
    
        try (FileReader ficheiro = new FileReader(arquivo)) {
            int caracter=0;
            while(caracter!=-1){
                caracter=ficheiro.read();
                if ((char)(caracter)!=' '){
                    if (caracteres.containsKey(caracter)){
                        caracteres.put(caracter, caracteres.get(caracter)+1);
                    } else{
                        caracteres.put(caracter,1);
                    }    
                }
            }
            
        } catch (Exception e) {
            
        } finally{
            
            int mayorOcurrencia=0;
            int letraRepetida=0;
            for (Integer letra : caracteres.keySet()) {
                if(caracteres.get(letra)>mayorOcurrencia){
                    mayorOcurrencia=caracteres.get(letra);
                    letraRepetida=letra;
                }
                
            }

            //VER LA FORMA MAS OPTIMA DE HACERLO
            //int max2 = Collections.max(caracteres.values());
            
            

            System.out.printf("A letra que máis se repite é:\"%s\"con %d apariciones",(char)(letraRepetida),mayorOcurrencia);
        }
        
        
    }
    public static void main(String[] args) throws IOException {
        String path="C:\\Users\\Pablo\\Downloads\\quijote.txt";
        File quijote = new File(path);
        caracterMaisEmpregado(quijote);
    }
}
