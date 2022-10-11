import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Ficheiros_Eje6 {
    
    /**
     * @param arquivo
     */
    public static void divide(File arquivo, int numCaracteres){
        try(FileReader arquivoLido = new FileReader(arquivo)){
            //este está mal, es porque me considera los \n como caracteres??
            //"los puede contar, sin problema"
            char[] buffer = new char[numCaracteres];
            int i=0;
            while(arquivoLido.read(buffer)!=-1) {
                try (FileWriter novoArquivo = new FileWriter(String.format("NovoArquivo%d", i))) {
                    novoArquivo.write(buffer);
                    i++; 
                } catch (Exception e) {
                    
                }
            }
            
        }catch(FileNotFoundException eoa){

        }catch(IOException aa){

        }
    }

    
    public static void divideLineas(File arquivo, int numLineas){
        try (Scanner arquivoLido = new Scanner(arquivo)) {
            int i=0;
            while(arquivoLido.hasNext()){
                try (PrintWriter novoArquivo = new PrintWriter(String.format("arquivoLinea%d", i))) {
                i++;
                for (int j = 0; j < numLineas && arquivoLido.hasNext(); j++) {
                    novoArquivo.println(arquivoLido.nextLine());
                }
                } catch (Exception e) {
                    
                }
            }                    
        } catch (Exception e) {
          
        }
    }

    public static void uneFicheiros(File...arquivos){
        try (PrintWriter novoArquivo = new PrintWriter("Arquivo_conxunto")) {

            for (File arquivo : arquivos) {
                try (Scanner sc = new Scanner(arquivo)) {
                    while(sc.hasNext()){
                        novoArquivo.append(sc.nextLine()+"\n");
                    }
                } catch (Exception e) {
                        // TODO: handle exception
                }
            }    
        } catch (Exception e) {
                // TODO: handle exception
            }
    }

    public static void main(String args[]) {

        File arquivo = new File("C:\\Users\\Pablo\\Desktop\\Programas Java\\prueba.txt");
        //EN GENERAL EL OUTPUT DEL ARCHIVO **TAMBIEN** SE PASA COMO PARAMETRO, no puedo
        //asumir o decidir yo dónde queda o cómo se llama el archivo
        //divideLineas(arquivo, 2);
        divide(arquivo, 5);
        String path="C:\\Users\\Pablo\\Desktop\\Programas Java\\arquivoLinea";

        //uneFicheiros(new File(path+"0"),new File(path+"1"),new File(path+"2"),new File(path+"3"));

    }
}
