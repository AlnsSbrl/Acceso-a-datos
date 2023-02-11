import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConsultasLite {
    PreparedStatement ps = null;
    ConectaSQLite connSQLite = new ConectaSQLite();
    ConectarDB connMariaDB = new ConectarDB();

    /**
     * Indica el nombre de las aulas que tengan cierto número de alumnos como mínimo
     * @param minimo el valor mínimo de alumnos
     */
    public void aulasConMinimoXAlumnos(int minimo) {

        ResultSet rs;
        try (Statement sentencia = this.connSQLite.conexion.createStatement()) {
            String query = String.format("select nombreAula from aulas where puestos>=%d", minimo);
            rs = sentencia.executeQuery(query);
            while (rs.next()) {
                System.out.println(rs.getString("nombreAula"));
            }
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
            System.out.println(e.getSQLState());
            System.out.println(e.getLocalizedMessage());
        }
    }

    /**
     * Indica el nombre de las aulas que tengan cierto número de alumnos como mínimo, pero con una sentencia preparada
     * @param minimo valor mínimo de alumnos
     */
    public void aulasConXAlumnosPrepared(int minimo) {
        String query = "Select nombreAula from aulas where puestos >= ?";
        ResultSet rs;
        try {
            if (this.ps == null) {
                System.out.println(query);
                this.ps = this.connSQLite.conexion.prepareStatement(query);
            }
            ps.setInt(1, minimo);
            rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("nombreAula"));
            }
        } catch (SQLException e) {
            System.out.println(e.getSQLState());
            System.out.println(e.getErrorCode());
            System.out.println(e.getLocalizedMessage());
        }
    }

    /**
     * Inserta un aula en la base de datos embebida
     * @param aula contiene los valores del aula a insertar
     * @return el número de aulas insertadas o -1 si hubo algún problema
     */
    public int insertaAula(Aula aula) {

        try (Statement statement = this.connSQLite.conexion.createStatement()) {
            String query = String.format("insert into aulas values (%d,'%s',%d)", aula.numero, aula.nombreAula,
                    aula.puestos);
            int i = statement.executeUpdate(query);
            return i;
        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println(e.getErrorCode());
            System.out.println(e.getSQLState());
            System.out.println(e.getLocalizedMessage());
            return -1;
        }
    }

    /**
     * Inserta un aula en la base de datos embebida, incluso si ya existe el valor de la clave primaria del aula que se quiere insertar
     * @param aula valores del aula a insertar
     * @return el número de aulas insertadas o -1 si hubo algún problema
     */
    public int insertAulaBruteForce(Aula aula) {

        try (Statement statement = this.connSQLite.conexion.createStatement()) {
            String query = String.format("replace into aulas values (%d,'%s',%d)", aula.numero, aula.nombreAula,
                    aula.puestos);
            int i = statement.executeUpdate(query);
            return i;
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
            System.out.println(e.getSQLState());
            System.out.println(e.getLocalizedMessage());
            return -1;
        }
    }

    /**
     * Inserta los alumnos en las dos bases de datos (la embebida y en mariaDB)
     * @param alumnos valores de los alumnos a insertar
     * @return el número de alumnos que se han insertado o -1 si ha habido algún problema y deshace los cambios
     */
    public int insertaAlumnosConsistentemente(Alumno[] alumnos) {

        try (
                Statement statementSQLite = this.connSQLite.conexion.createStatement();
                Statement statementMaria = this.connMariaDB.conexion.createStatement()) {
            this.connMariaDB.conexion.setAutoCommit(false);
            this.connSQLite.conexion.setAutoCommit(false);
            String[] values = new String[alumnos.length];
            for (int i = 0; i < alumnos.length; i++) {
                Alumno al = alumnos[i];
                values[i] = String.format(" ('%s','%s',%d,%d)", al.nombre, al.apellidos, al.altura,
                        al.aula);
            }
            String query = "INSERT INTO alumnos(nombre,apellidos,altura,aula) VALUES" + String.join(",", values);
            int i = statementMaria.executeUpdate(query);
            statementSQLite.executeUpdate(query);
            this.connMariaDB.conexion.commit();
            this.connSQLite.conexion.commit();
            return i;
        } catch (SQLException e) {
            System.out.println("No se han podido introducir los alumnos");
            try {
                if (this.connSQLite.conexion != null && this.connMariaDB.conexion != null) {
                    this.connSQLite.conexion.rollback();
                    this.connMariaDB.conexion.rollback();
                    System.out.println("se deshacen los cambios");
                }
            } catch (SQLException eo) {
                eo.getSQLState();
                eo.getErrorCode();
                eo.getLocalizedMessage();
            }
            return -1;
        }
    }

    /**
     * Ejecuta la misma sentencia de búsqueda por patrón de un alumno en ambas bases de datos y compara el tiempo de ejecución de las consultas
     * @param pattern patrón de búsqueda del alumno
     */
    public void buscaAlumnoPorPatronAmbasDB(String pattern) {

        String query = "select * from alumnos where nombre like '%" + pattern + "%'";
        HiloSQLite hilo = new HiloSQLite();
        hilo.recibeDatos(query);
        hilo.run();
        ResultSet rs;
        try (Statement statement = this.connSQLite.conexion.createStatement()) {
            long i = System.currentTimeMillis();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                rs.getString("nombre");
            }
            System.out.println("sqlite tardó: " + (System.currentTimeMillis() - i) + " ms");

        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
            System.out.println(e.getSQLState());
            System.out.println(e.getLocalizedMessage());
        }
    }
    /**
     * Clase que ejecuta la sentencia select que se le pasa por parámetro en mariadb y te devuelve el tiempo que tardó en ejecutarse
     */
    class HiloSQLite extends Thread {
        String query;

        @Override
        public void run() {
            ResultSet rs;
            try (Statement statement = connMariaDB.conexion.createStatement()) {
                long i = System.currentTimeMillis();
                rs = statement.executeQuery(query);
                while (rs.next()) {
                    rs.getString("nombre");
                }
                System.out.println("mariaDB tardó: " + (System.currentTimeMillis() - i) + " ms");

            } catch (SQLException e) {
                System.out.println(e.getErrorCode());
                System.out.println(e.getSQLState());
                System.out.println(e.getLocalizedMessage());
            }
        }

        public void recibeDatos(String query) {
            this.query = query;
        }
    }


    /**
     * Inserta los alumnos(o las aulas) en las dos bases de datos (la embebida y en mariaDB)
     * 
     * @param elementosAInsertar valores de los alumnos(o aulas) a insertar
     * @return el número de alumnos(o aulas) que se han insertado o -1 si ha habido algún
     *         problema y deshace los cambios
     */
    public int insertaCosasConsistentemente(Object[] elementosAInsertar) {//lo malo de esto es que puedes meterme un array de varios objetos diferentes...y en teoria deberia cascar

        String query="";
        try (
                Statement statementSQLite = this.connSQLite.conexion.createStatement();
                Statement statementMaria = this.connMariaDB.conexion.createStatement()) {
            this.connMariaDB.conexion.setAutoCommit(false);
            this.connSQLite.conexion.setAutoCommit(false);
            String[] values = new String[elementosAInsertar.length];
            for (int i = 0; i < elementosAInsertar.length; i++) {
                if (elementosAInsertar[i] instanceof Alumno) {
                    Alumno al = (Alumno) elementosAInsertar[i];
                    values[i] = String.format(" ('%s','%s',%d,%d)", al.nombre, al.apellidos, al.altura,
                            al.aula);
                } else if (elementosAInsertar[i] instanceof Aula) {
                    Aula aula = (Aula) elementosAInsertar[i];
                    values[i] = String.format(" ('%s',%d,%d)", aula.nombreAula, aula.numero, aula.puestos);
                }
            }

            if(elementosAInsertar[0] instanceof Alumno){
                query = "INSERT INTO alumnos(nombre,apellidos,altura,aula) VALUES" + String.join(",", values);
            }else if(elementosAInsertar[0] instanceof Aula){
                query = "INSERT INTO aulas(nombreAula,numero,puestos) VALUES"+String.join(",", values);
            }

            int i = statementMaria.executeUpdate(query);
            statementSQLite.executeUpdate(query);
            this.connMariaDB.conexion.commit();
            this.connSQLite.conexion.commit();
            return i;
        } catch (SQLException e) {
            System.out.println("No se han podido introducir los datos");
            try {
                if (this.connSQLite.conexion != null && this.connMariaDB.conexion != null) {
                    this.connSQLite.conexion.rollback();
                    this.connMariaDB.conexion.rollback();
                    System.out.println("se deshacen los cambios");
                }
            } catch (SQLException eo) {
                eo.getSQLState();
                eo.getErrorCode();
                eo.getLocalizedMessage();
            }
            return -1;
        }
    }
   
    
    public static void main(String[] args) {
        String pathDB = "src\\baseDeDatos.db";
        ConsultasLite c = new ConsultasLite();
        c.connSQLite.conectar(pathDB);
        c.connMariaDB.conectar("conectores", "localhost", "root", "");

        // c.aulasConMinimoXAlumnos(30);
        // c.aulasConXAlumnosPrepared(30);
        // Aula aa = new Aula("Gilipollas", 21, 22);
        // c.insertAulaBruteForce(aa);
        //// int i = c.insertAulaBruteForce(new Aula("guapasss", 50, 22));
        // System.out.println(i);
        c.buscaAlumnoPorPatronAmbasDB("fr");
        c.connMariaDB.CerrarConexion();
        c.connSQLite.cerrarConexion();
    }
}
