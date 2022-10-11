import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Ficheiros_Eje8 {
    
    public static void copiaSinBuffer(File arquivo, File arquivoSaida){
        
        try (FileInputStream FichIn = new FileInputStream(arquivo);
        FileOutputStream FichOut = new FileOutputStream(arquivoSaida, true)) {         
            int caracter; //sin buffer, este int indica QUÉ caracter es
            while((caracter=FichIn.read())!=-1){
                FichOut.write(caracter);
            }
        } catch (Exception e) {
            System.err.println("hola buenas Don Javier");
        }
    }

    public static void copiaConBuffer(File arquivo, File arquivoSaida, int numCaracteres){
        try (FileInputStream FichIn = new FileInputStream(arquivo);
        FileOutputStream FichOut = new FileOutputStream(arquivoSaida, true)) {
            int caracteres; //con buffer este int indica el NÚMERO de caracteres
            byte[] buffer = new byte[numCaracteres];
            while((caracteres=FichIn.read(buffer))!=-1){
                FichOut.write(buffer,0,caracteres);
            }
        } catch (Exception e) {
            System.err.println("Algo falla meeuuu");
        }
    }

    public static void main(String args[]){
        
    }
}
