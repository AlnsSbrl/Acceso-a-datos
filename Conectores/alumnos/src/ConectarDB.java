import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConectarDB {

    public Connection conexion;


    public void conectar(String db, String server, String user, String password) {
        try {
            String url = String.format("jdbc:mariadb://%s:3306/%s?useServerPrepStmts=true", server, db);
            this.conexion = DriverManager.getConnection(url, user, password);

            if (this.conexion != null) {
                System.out.printf("conectado a %s en %s \n", db, server);
            } else {
                System.out.printf("no conectado a %s en %s \n", db, server);
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getLocalizedMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("Código de error: " + e.getErrorCode());
        }
    }

    public void CerrarConexion(){
        try {
            this.conexion.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexion: "+e.getLocalizedMessage());
        }
    }
}
