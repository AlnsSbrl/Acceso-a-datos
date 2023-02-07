import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectaSQLite {

    public Connection conexion;

    public void conectar(String srcDB) {
        try {
            String url = "jdbc:sqlite:" + srcDB;
            conexion = DriverManager.getConnection(url);
            if (this.conexion != null) {
                System.out.println("conectao a " + srcDB);
            } else {
                System.out.println("no sirve meu");
            }
        } catch (SQLException e) {
            System.out.println("mi pana kapasao");
        }
    }

    public void cerrarConexion() {
        try {
            this.conexion.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getLocalizedMessage());
        }
    }
}
