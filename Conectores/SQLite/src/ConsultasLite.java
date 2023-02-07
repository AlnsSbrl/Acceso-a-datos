import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ConsultasLite {
    PreparedStatement ps=null;
    ConectaSQLite connSQLite = new ConectaSQLite();

    public void aulasConMinimoXAlumnos(int minimo){

        ResultSet rs;
        try(Statement sentencia = this.connSQLite.conexion.createStatement()) {
            String query = String.format("select nombreAula from aulas where puestos>=%d",minimo);
            rs= sentencia.executeQuery(query);
            while(rs.next()){
                System.out.println(rs.getString("nombreAula"));
            }    
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
            System.out.println(e.getSQLState());
            System.out.println(e.getLocalizedMessage());
        }
    }
   
    public void aulasConXAlumnosPrepared(int minimo){
        String query = "Select nombreAula from aulas where puestos >= ?";
        ResultSet rs;
        try {
            if(this.ps==null){
                System.out.println(query);
                this.ps= this.connSQLite.conexion.prepareStatement(query);
            }
            ps.setInt(1,minimo);
            rs=ps.executeQuery();
            while(rs.next()){
                System.out.println(rs.getString("nombreAula"));  
            }       
        } catch (SQLException e) {
            System.out.println(e.getSQLState());
            System.out.println(e.getErrorCode());
            System.out.println(e.getLocalizedMessage());
        }
    }
    
    public int insertaAula(Aula aula){
       
        try (Statement statement = this.connSQLite.conexion.createStatement()) {
            String query = String.format("insert into aulas values (%d,'%s',%d)",aula.numero,aula.nombreAula,aula.puestos);
            int i= statement.executeUpdate(query);
            return i;
        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println(e.getErrorCode());
            System.out.println(e.getSQLState());
            System.out.println(e.getLocalizedMessage());
            return -1;
        }
    }
    
    public int insertAulaBruteForce(Aula aula){
        
        //mayb hago un delete primero?
        //hay un insert into on duplicate primary key update......pero claro, es sin UPDATE
        
        return -1;
    }
    public static void main(String[] args) {
        String pathDB = "src\\baseDeDatos.db";
        ConsultasLite c= new ConsultasLite();
        c.connSQLite.conectar(pathDB);


        //c.aulasConMinimoXAlumnos(30);
        c.aulasConXAlumnosPrepared(30);
        c.connSQLite.cerrarConexion();
    }
}
