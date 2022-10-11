import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Scanner;

import javax.swing.text.AbstractDocument.LeafElement;

public class Ficheiros_Eje7 {
    public static ArrayList<String> lerTodasAsLineas(File arquivo) {
        ArrayList<String> coleccionDeLineas = new ArrayList<>();
        try (Scanner sc = new Scanner(arquivo)) {
            while (sc.hasNext()) {
                coleccionDeLineas.add(sc.nextLine());
            }
        } catch (Exception e) {
            System.err.println("non se pudo ler o arquivo");
        }
        return coleccionDeLineas;
    }

    public static void crearOsArquivos(ArrayList<String> lineas, String arquivoSaida, boolean ascendente) {
        int numLineas = lineas.size();
        
        ListIterator orden =lineas.listIterator(lineas.size());
        orden.previous();
        int i = 0;

        if (true) {

            for (String linea : lineas) {
                try (PrintWriter novoArquivo = new PrintWriter(
                        String.format(arquivoSaida + "(%d)", i))) {

                    novoArquivo.write(linea);
                    i++;
                }

                catch (Exception e) {
                    System.err.println("Erro na escritura das liñas");
                }
            }
        } //else {

            // for (int j = lineas.size(); j > 0; j--) {
            //     try (PrintWriter novoArquivo = new PrintWriter(String.format(arquivoSaida + "(%d)", i))) {
            //         novoArquivo.write(lineas.get(j));
            //     } catch (Exception e) {
            //         System.err.println("Erro coa escritura...again");
            //     }
            // }
        //}

        // novoArquivo.write(lineas.get(numLineas-i));
        // for (int j = i-1; j >=0; j--) {
        // try (PrintWriter novoArquivo = new
        // PrintWriter(String.format("LineaDescCaseSens(%d)",i-j))) {
        // novoArquivo.write(coleccionDeLineas.get(j));
        // } catch (Exception e) {

        // }
        // }

    }

    public static void operaciones(File arquivo, char operacion, String arquivoSaida) {

        switch (operacion) {
            case 'n':
                int cantidadeLineas = 0;
                try (Scanner arquivoLido = new Scanner(arquivo)) {
                    while (arquivoLido.hasNext()) {
                        cantidadeLineas++;
                        arquivoLido.nextLine();
                    }
                } catch (Exception e) {

                } finally {
                    System.out.println(cantidadeLineas);

                }
                break;

            case 'A':
                // Ordenar las lineaas por orden alfabet y CASE SENSITIVE
                ArrayList<String> lineas = new ArrayList<>();
                lineas = lerTodasAsLineas(arquivo);
                lineas.sort(null);
                crearOsArquivos(lineas, arquivoSaida, true);
                break;

            case 'D':
                // Ordenar de forma desc y case sensitive
                lineas = lerTodasAsLineas(arquivo);
                lineas.sort(null);
                crearOsArquivos((ArrayList<String>)(lineas.subList(lineas.size()-1, 0)), arquivoSaida, false);
                break;

            case 'a':
                // Ordenar de forma asc y NOT case sens
                lineas = lerTodasAsLineas(arquivo);
                lineas.sort(String.CASE_INSENSITIVE_ORDER);
                crearOsArquivos(lineas, arquivoSaida, true);

                break;

            case 'd':
                // Ordenar forma desc y NOT case sens
                lineas = lerTodasAsLineas(arquivo);
                lineas.sort(String.CASE_INSENSITIVE_ORDER);
                lineas.listIterator(lineas.size());
                //lineas.subList(lineas.size()-1, 0);
                crearOsArquivos(lineas, arquivoSaida, false);

                break;
        }
    }

    public static void main(String args[]) {

        File arquivo = new File("C:\\Users\\Pablo\\Desktop\\Programas Java\\prueba.txt");
        String arquivoSaida = "C:\\Users\\Pablo\\Desktop\\Programas Java\\";
        operaciones(arquivo, 'n', arquivoSaida);
        // operaciones(arquivo, 'A', arquivoSaida);
        // operaciones(arquivo, 'a', arquivoSaida);
        operaciones(arquivo, 'D', arquivoSaida);
        // operaciones(arquivo, 'd', arquivoSaida);

    }
}
