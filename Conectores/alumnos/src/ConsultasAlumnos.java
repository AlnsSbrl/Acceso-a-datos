
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConsultasAlumnos {
    
    public ConectarDB cdb;
    

    public void BuscaAlumno(String parteDelNombre){
        try(Statement sentencia = this.cdb.conexion.createStatement()){
            String query = "select nombre from alumnos where nombre like '%"+parteDelNombre+"%'";
            ResultSet rs = sentencia.executeQuery(query);
            while(rs.next()){
                System.out.println(rs.getString("nombre"));
            }
            query = "select count(nombre) as total from alumnos where nombre like '%"+parteDelNombre+"%'";
            rs = sentencia.executeQuery(query);
            while(rs.next()){
                System.out.println("Numero de resultados: "+rs.getInt("total"));
            }       
        } catch (SQLException e) {
            System.err.println(e.getSQLState());
            System.err.println(e.getErrorCode());
            System.err.println(e.getLocalizedMessage());
        }
    }

    public void InsertarAlumnos(Alumno[] alumnos){

        try(Statement sentencia = this.cdb.conexion.createStatement()){          
            String query = "SELECT max(codigo) as codigo from alumnos";
            ResultSet rs = sentencia.executeQuery(query);
            int codigo=0;
            while(rs.next()){
                codigo=rs.getInt("codigo");
            }
            String values ="";
            for (int i = 0; i < alumnos.length; i++) {
                Alumno al = alumnos[i];
                values+=String.format(" (%d,'%s','%s',%d,%d),",++codigo, al.nombre,al.apellidos,al.altura,al.aula);
            }
            query = String.format("INSERT INTO alumnos(codigo,nombre,apellidos,altura,aula) VALUES"+values.substring(0, values.length()-1));
            System.out.println(query);
            int filasAfectadas= sentencia.executeUpdate(query);
            System.out.println("Filas insertadas: "+filasAfectadas);
        }catch(SQLException e){
            System.err.println(e.getSQLState());
            System.err.println(e.getErrorCode());
            System.err.println(e.getLocalizedMessage());
        }
    }
    
    public void InsertarAsignaturas(String[] asignaturas){
        try (Statement sentencia = this.cdb.conexion.createStatement()) {
            String query = "SELECT max(COD) as codigo from asignaturas";
            ResultSet rs= sentencia.executeQuery(query);
            int codigo=0;
            while(rs.next()){
                codigo=rs.getInt("codigo");
            }
            query="INSERT INTO asignaturas VALUES";
            String values = "";
            for (int i = 0; i < asignaturas.length; i++) {
                values+=String.format(" (%d,'%s'),", ++codigo,asignaturas[i]);
            }
            query=query+values.substring(0,values.length()-1);
            int numFilas = sentencia.executeUpdate(query);
            System.out.println("Numero filas afectadas: "+numFilas);
        } catch (SQLException e) {
            System.err.println(e.getSQLState());
            System.err.println(e.getErrorCode());
            System.err.println(e.getLocalizedMessage());
        }
    }
    
    public void BorraAlumno(int codigoAlumno){
        
        try (Statement sentencia = this.cdb.conexion.createStatement()) {
            
            String query = "Select codigo, nombre from alumnos where codigo ="+codigoAlumno;
            ResultSet rs = sentencia.executeQuery(query);
            //boolean existeAlumno=false;
            //existeAlumno=rs.next();
            if(rs.next()){
                query = "delete from notas where alumno="+codigoAlumno;
                sentencia.executeUpdate(query);
                query = "delete from alumnos where codigo="+codigoAlumno;
                sentencia.executeUpdate(query);
                System.out.println("alumno borrado");
            }else{
                System.out.println("non existe o alumno");
            }
        } catch (SQLException e) {
            // TODO: handle exception
            e.getSQLState();
            e.getErrorCode();
            e.getLocalizedMessage();
        }
    }
    
    public void BorraAsignatura(String asignatura){
        try (Statement sentencia = this.cdb.conexion.createStatement()) {
            
            String query = "Select COD from asignaturas where NOMBRE ='"+asignatura+"'";
            ResultSet rs = sentencia.executeQuery(query);
            if(rs.next()){
                int codigo = rs.getInt("COD");
                query= "delete from notas where asignatura ="+codigo;
                sentencia.executeUpdate(query);
                query= "delete from asignaturas where NOMBRE = '"+asignatura+"'";
                int resultados = sentencia.executeUpdate(query);
                System.out.println("asignaturas borradas: "+resultados);
            }else{
                System.out.println("no se encontró la asignatura");
            }

        } catch (SQLException e) {
            e.getSQLState();
            e.getErrorCode();
            e.getLocalizedMessage();
        }
    }
    
    //nombre de las aulas con alumnos
    public void aulasConAlumnos(){
        try (Statement sentencia = this.cdb.conexion.createStatement()) {
            String query = "Select nombreAula from aulas where numero in (select distinct aula from alumnos)";
            ResultSet rs = sentencia.executeQuery(query);
            System.out.println("aulas con alumnos: ");
            while(rs.next()){
                System.out.println(rs.getString("nombreAula"));
            }
        } catch (SQLException e) {
            e.getSQLState();
            e.getErrorCode();
            e.getLocalizedMessage();
        }
    }
    //nombre de los alumnos, de las asignaturas y de aquellos alumnos que hayan aprobado alguna asignatura
    //TODO basicamente no termino de entender qué se busca o cómo mostrar los datos
    public void notasAlumnosEnAsignaturas(){
        try (Statement sentencia = this.cdb.conexion.createStatement()) {
            String query = "";
            ResultSet rs = sentencia.executeQuery(query);
            System.out.println("");
            while(rs.next()){
            }
        } catch (SQLException e) {
            e.getSQLState();
            e.getErrorCode();
            e.getLocalizedMessage();
        }
    }
    //nombre de las asignaturas sin alumnos
    public void asignaturasSinAlumnos(){
        try (Statement sentencia = this.cdb.conexion.createStatement()) {
            String query = "Select nombre from asignaturas where COD not in(select distinct asignatura from notas)";
            ResultSet rs = sentencia.executeQuery(query);
            System.out.println("asignaturas sin alumnos:");
            while(rs.next()){
                System.out.println(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            e.getSQLState();
            e.getErrorCode();
            e.getLocalizedMessage();
        }
    }

    //sin sentencias preparadas
    //TODO terminar el metodo
    public void buscaPorPatron(String patron){
        try (Statement sentencia = this.cdb.conexion.createStatement()) {
            String query = "Select nombre from alumnos where nombre like "+patron;
            ResultSet rs = sentencia.executeQuery(query);           
        } catch (SQLException e) {
            e.getSQLState();
            e.getErrorCode();
            e.getLocalizedMessage();
        }
    }

    //TODO buscar como hacer las sentencias preparadas (hay que modificar la conexion...hago dos conexiones diferentes?)
    public void buscaPorPatronSentenciaPreparada(String patron){

    }
    public static void main(String[] args) throws Exception {
        
        ConsultasAlumnos consu = new ConsultasAlumnos();
        consu.cdb = new ConectarDB();
        consu.cdb.conectar("conectores", "localhost", "root", "");       
        //consu.BuscaAlumno("fr");
        // Alumno[] alumnos = new Alumno[2];
        // alumnos[0] = new Alumno("Christopher", "Nolan", 180, 20);
        // alumnos[1] = new Alumno("Quentin", "Tarantino", 160, 11);
        // consu.InsertarAlumnos(alumnos);
        // String[] asignaturas ={"Acceso a datos","php","frontend","backend","Tekken 2"};
        // consu.InsertarAsignaturas(asignaturas);
        //consu.BorraAlumno(14);
        //consu.BorraAsignatura("Tekken 2");
        //consu.asignaturasSinAlumnos();
        String patron = "%k";
        consu.buscaPorPatron(patron);
        consu.aulasConAlumnos();
        consu.cdb.CerrarConexion();      
    }
}
