
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.plaf.ColorUIResource;

public class ConsultasAlumnos {

    public ConectarDB cdb;
    private PreparedStatement preparedStatement=null;

    /**
     * Se obtienen los alumnos que contengan el parámetro
     * @param parteDelNombre parte del nombre que se quiere buscar
     * @apiNote corregido
     */
    public void buscaAlumnos(String parteDelNombre) {
        try (Statement sentencia = this.cdb.conexion.createStatement()) {
            String query = "select nombre from alumnos where nombre like '%" + parteDelNombre + "%'";
            ResultSet rs = sentencia.executeQuery(query);
            rs.last();
            System.out.println(+rs.getRow() + " ");// se mueve a la ultima fila y la cuento, luego vuelvo a la misma
            rs.first();
            while (rs.next()) {
                System.out.println(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            System.err.println(e.getSQLState());
            System.err.println(e.getErrorCode());
            System.err.println(e.getLocalizedMessage());
        }
    }

    /**
     * Añade alumnos a la base de datos
     * @param alumnos alumno a insertar
     * @apiNote corregido
     */
    public void insertarAlumnos(Alumno[] alumnos) {

        try (Statement sentencia = this.cdb.conexion.createStatement()) {
            String query = "SELECT max(codigo) as codigo from alumnos";
            ResultSet rs = sentencia.executeQuery(query);
            int codigo = 0;
            while (rs.next()) {
                codigo = rs.getInt("codigo");
            }
            String[] values = new String[alumnos.length];
            for (int i = 0; i < alumnos.length; i++) {
                Alumno al = alumnos[i];
                values[i] = String.format(" (%d,'%s','%s',%d,%d)", ++codigo, al.nombre, al.apellidos, al.altura,
                        al.aula);
            }
            query = "INSERT INTO alumnos(codigo,nombre,apellidos,altura,aula) VALUES" + String.join(",", values);
            System.out.println(query);
            int filasAfectadas = sentencia.executeUpdate(query);
            System.out.println("Filas insertadas: " + filasAfectadas);
        } catch (SQLException e) {
            System.err.println(e.getSQLState());
            System.err.println(e.getErrorCode());
            System.err.println(e.getLocalizedMessage());
        }
    }

    /**
     * Añade asignaturas a la base de datos
     * @param asignaturas asignatura a insertar
     * @apiNote corregido
     */
    public void insertarAsignaturas(String[] asignaturas) {
        try (Statement sentencia = this.cdb.conexion.createStatement()) {
            String query = "SELECT max(COD) as codigo from asignaturas";
            ResultSet rs = sentencia.executeQuery(query);
            int codigo = 0;
            while (rs.next()) {
                codigo = rs.getInt("codigo");
            }
            query = "INSERT INTO asignaturas VALUES";
            String[] values = new String[asignaturas.length];
            for (int i = 0; i < asignaturas.length; i++) {
                values[i] = String.format(" (%d,'%s')", ++codigo, asignaturas[i]);
            }
            query = query + String.join(",", values);
            int numFilas = sentencia.executeUpdate(query);
            System.out.println("Numero filas afectadas: " + numFilas);
        } catch (SQLException e) {
            System.err.println(e.getSQLState());
            System.err.println(e.getErrorCode());
            System.err.println(e.getLocalizedMessage());
        }
    }

    /**
     * Borra el alumno de la base de datos
     * @param codigoAlumno clave primaria identificadora del alumno
     * @apiNote corregido
     */
    public void borraAlumno(int codigoAlumno) {
        int elemBorrado = 0;
        try (Statement sentencia = this.cdb.conexion.createStatement()) {
            String query = "delete from notas where alumno=" + codigoAlumno;
            sentencia.executeUpdate(query);
            query = "delete from alumnos where codigo=" + codigoAlumno;
            elemBorrado = sentencia.executeUpdate(query);
            if (elemBorrado > 0) {
                System.out.println("alumno borrado");
            } else {
                System.out.println("non existe o alumno");
            }
        } catch (SQLException e) {
            e.getSQLState();
            e.getErrorCode();
            e.getLocalizedMessage();
        }
    }

    /**
     * Borra la asignatura de la base de datos
     * @param asignatura nombre de la asignatura
     * @apiNote similar a la de borrar alumno, pero como busca por nombre en vez de clave primaria es necesario dos consultas
     */
    public void borraAsignatura(String asignatura) {
        try (Statement sentencia = this.cdb.conexion.createStatement()) {

            String query = "Select COD from asignaturas where NOMBRE ='" + asignatura + "'";
            ResultSet rs = sentencia.executeQuery(query);
            if (rs.next()) {
                int codigo = rs.getInt("COD");
                query = "delete from notas where asignatura =" + codigo;
                sentencia.executeUpdate(query);
                query = "delete from asignaturas where NOMBRE = '" + asignatura + "'";
                int resultados = sentencia.executeUpdate(query);
                System.out.println("asignaturas borradas: " + resultados);
            } else {
                System.out.println("no se encontró la asignatura");
            }

        } catch (SQLException e) {
            e.getSQLState();
            e.getErrorCode();
            e.getLocalizedMessage();
        }
    }

    // nombre de las aulas con alumnos
    public void aulasConAlumnos() {
        try (Statement sentencia = this.cdb.conexion.createStatement()) {
            String query = "Select nombreAula from aulas where numero in (select distinct aula from alumnos)";
            ResultSet rs = sentencia.executeQuery(query);
            System.out.println("aulas con alumnos: ");
            while (rs.next()) {
                System.out.println(rs.getString("nombreAula"));
            }
        } catch (SQLException e) {
            e.getSQLState();
            e.getErrorCode();
            e.getLocalizedMessage();
        }
    }

    // nombre de los alumnos, de las asignaturas y de aquellos alumnos que hayan
    // aprobado alguna asignatura
    public void notasAlumnosEnAsignaturas() {
        try (Statement sentencia = this.cdb.conexion.createStatement()) {
            String query = "SELECT alumnos.nombre as alumno, asignaturas.nombre as asignatura, nota FROM ((notas INNER JOIN alumnos ON notas.alumno=alumnos.codigo) INNER JOIN asignaturas ON notas.asignatura=asignaturas.cod) WHERE nota>=5";
            ResultSet rs = sentencia.executeQuery(query);
            System.out.println("");
            while (rs.next()) {
                System.out
                        .println(rs.getString("alumno") + "\t" + rs.getString("asignatura") + "\t" + rs.getInt("nota"));
            }
        } catch (SQLException e) {
            e.getSQLState();
            e.getErrorCode();
            e.getLocalizedMessage();
        }
    }

    // nombre de las asignaturas sin alumnos
    public void asignaturasSinAlumnos() {
        try (Statement sentencia = this.cdb.conexion.createStatement()) {
            String query = "Select nombre from asignaturas where COD not in(select distinct asignatura from notas)";
            ResultSet rs = sentencia.executeQuery(query);
            System.out.println("asignaturas sin alumnos:");
            while (rs.next()) {
                System.out.println(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            e.getSQLState();
            e.getErrorCode();
            e.getLocalizedMessage();
        }
    }

    // sin sentencias preparadas
    public void buscaPorPatron(String patron, int altura) {
        try (Statement sentencia = this.cdb.conexion.createStatement()) {
            String query = "Select nombre from alumnos where nombre like '%" + patron+"%' and altura>"+altura;
            
            ResultSet rs = sentencia.executeQuery(query);
            while(rs.next()){
                System.out.println(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            e.getSQLState();
            e.getErrorCode();
            e.getLocalizedMessage();
        }
    }

    // con sentencia preparada
    public void buscaPorPatronSentenciaPreparada(String patron, int altura) {
        try {
            String query = "SELECT nombre from alumnos where nombre like ? and altura>?";
            if(this.preparedStatement==null){
                this.preparedStatement=this.cdb.conexion.prepareStatement(query);
            }
            preparedStatement.setString(1,"%"+patron+"%");
            preparedStatement.setInt(2, altura);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                System.out.println(rs.getString("nombre"));
            }
            
        } catch (SQLException e) {
            e.getSQLState();
            e.getErrorCode();
            e.getLocalizedMessage();
        }
    }

    public void engadirTabla(Columna colum){
        try(Statement sentencia = this.cdb.conexion.createStatement()){
            String query = String.format("ALTER TABLE %s ADD %s %s %s;", colum.tabla,colum.nombreColumna, colum.dataType,colum.propiedades);
            
            
        } catch (SQLException e) {
            e.getSQLState();
            e.getErrorCode();
            e.getLocalizedMessage();
        }
    }

    public static void main(String[] args) throws Exception {

        ConsultasAlumnos consu = new ConsultasAlumnos();
        consu.cdb = new ConectarDB();
         consu.cdb.conectar("conectores", "localhost", "root", "");
        // consu.buscaAlumnos("fr");
        // Alumno[] alumnos = new Alumno[1];
        // alumnos[0] = new Alumno("que onda", "cor por", 180, 20);
        // // alumnos[1] = new Alumno("curro", "f", 160, 11);
        // consu.insertarAlumnos(alumnos);
        // // String[] asignaturas ={"Acceso a datos","php","frontend","backend","Tekken
        // // 2"};
        // // consu.InsertarAsignaturas(asignaturas);
        // // consu.BorraAlumno(14);
        // // consu.BorraAsignatura("Tekken 2");
        // // consu.asignaturasSinAlumnos();
        // String patron = "%k";
        // //consu.notasAlumnosEnAsignaturas();
        // consu.buscaPorPatron(patron);
        // consu.aulasConAlumnos();
        // long tPosterior;
        // long tActual = System.currentTimeMillis();
        // for (int i = 0; i < 1000000; i++) {
        //     consu.buscaPorPatron("fr", 180);
        // }
        // tPosterior = System.currentTimeMillis();
        // System.out.println(tPosterior-tActual);
        // tActual=System.currentTimeMillis();
        // for (int i = 0; i < 1000000; i++) {
        //     consu.buscaPorPatronSentenciaPreparada("fr", 180);
        // }
        // tPosterior=System.currentTimeMillis();
        // System.out.println(tPosterior-tActual);
        consu.cdb.CerrarConexion();
    }
}

/* 
  try(Statement sentencia = this.cdb.conexion.createStatement()){

        
            
        } catch (SQLException e) {
            e.getSQLState();
            e.getErrorCode();
            e.getLocalizedMessage();
        }
*/
