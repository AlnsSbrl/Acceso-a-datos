import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class Ficheiros_Eje9 {
    
    public static void crearNovoAlumno(File arquivo, ArrayList<Alumno> datos){
        try (//FileInputStream listaDeAlumnos = new FileInputStream(arquivo);
        FileOutputStream listaDeAlimnosActualizada = new FileOutputStream(arquivo, true);
        DataOutputStream out = new DataOutputStream(listaDeAlimnosActualizada);) {
            for (Alumno alumno : datos) {
                out.writeInt(alumno.getCodigo());
                out.writeUTF(alumno.getNome());
                out.writeDouble(alumno.getAltura());
            }
        } catch (Exception e) {
            System.err.println("Ola, Don Conde, non funciono :'((");
        }
    }

    public static void consultarAlumno(){

    }

    public static void modificarAlumno(File arquivo){

    }

    public static void borrarAlumno(File arquivo){

    }

    public static void main(String args[]){
        File arquivo = new File("C:\\Users\\Pablo\\Downloads\\alumnos.dat");
        ArrayList alumno = new ArrayList();
        
        crearNovoAlumno(arquivo,alumno);
        //datos: cod (int), nombre, altura(double)
        //dar de alta nuevos alumnos -> incluir en el fichero con append
        //consultar alumnos -> recorrer alumnos y si coincide: sysout
        //modificar alumnos ->recorrer alumn, copy paste menos el que se modifica
        //borrar alumn -> copy paste menos el que se borra
    }
}
