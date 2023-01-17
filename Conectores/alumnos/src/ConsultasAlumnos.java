
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Scanner;

import javax.swing.plaf.ColorUIResource;

public class ConsultasAlumnos {

    public ConectarDB cdb;
    private PreparedStatement preparedStatement = null;

    /**
     * Se obtienen los alumnos que contengan el parámetro
     * 
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
     * 
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
     * 
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
     * 
     * @param codigoAlumno clave primaria identificadora del alumno
     * @apiNote corregido
     */
    public int borraAlumno(int codigoAlumno) {
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
            return elemBorrado;
        } catch (SQLException e) {
            e.getSQLState();
            e.getErrorCode();
            e.getLocalizedMessage();
            return -1;
        }
    }

    /**
     * Borra la asignatura de la base de datos
     * 
     * @param asignatura nombre de la asignatura
     * @apiNote corregido
     */
    public int borraAsignatura(String asignatura) {
        try (Statement sentencia = this.cdb.conexion.createStatement()) {

            String query = "Select COD from asignaturas where NOMBRE ='" + asignatura + "'";
            ResultSet rs = sentencia.executeQuery(query);
            int resultados = 0;
            if (rs.next()) {
                int codigo = rs.getInt("COD");
                query = "delete from notas where asignatura =" + codigo;
                sentencia.executeUpdate(query);
                query = "delete from asignaturas where NOMBRE = '" + asignatura + "'";
                resultados = sentencia.executeUpdate(query);
                System.out.println("asignaturas borradas: " + resultados);
            } else {
                System.out.println("no se encontró la asignatura");
            }
            return resultados;

        } catch (SQLException e) {
            e.getSQLState();
            e.getErrorCode();
            e.getLocalizedMessage();
            return -1;
        }
    }

    /**
     * Muestra el nombre de las aulas que tienen alumnos
     * 
     * @apiNote corregido
     */
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

    /**
     * Muestra el nombre de los alumnos, de las asignaturas y las notas de las notas
     * aprobadas
     * 
     * @apiNote corregido
     */
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

    /**
     * Muestra el nombre de las asignaturas sin alumnos
     * 
     * @apiNote corregido
     */
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

    /**
     * Muestra el nombre de los alumnos que cumplan los requisitos (sin sentencia
     * preparada)
     * 
     * @param patron parte del nombre de los alumnos
     * @param altura altura minima de los alumnos
     * @apiNote corregido
     */
    public void buscaPorPatron(String patron, int altura) {
        try (Statement sentencia = this.cdb.conexion.createStatement()) {
            String query = "Select nombre from alumnos where nombre like '%" + patron + "%' and altura>" + altura;

            ResultSet rs = sentencia.executeQuery(query);
            while (rs.next()) {
                System.out.println(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            e.getSQLState();
            e.getErrorCode();
            e.getLocalizedMessage();
        }
    }

    /**
     * Muestra el nombre de los alumnos que cumplan los requisitos con sentencia
     * preparada
     * 
     * @param patron parte del nombre de los alumnos
     * @param altura altura minima de los alumnos
     * @apiNote corregido
     */
    public void buscaPorPatronSentenciaPreparada(String patron, int altura) {
        try {
            String query = "SELECT nombre from alumnos where nombre like ? and altura>?";
            if (this.preparedStatement == null) {
                this.preparedStatement = this.cdb.conexion.prepareStatement(query);
            }
            preparedStatement.setString(1, "%" + patron + "%");
            preparedStatement.setInt(2, altura);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("nombre"));
            }

        } catch (SQLException e) {
            e.getSQLState();
            e.getErrorCode();
            e.getLocalizedMessage();
        }
    }

    /**
     * Añade una columna a una tabla
     * 
     * @param colum objeto columna que contiene la información de qué columna
     *              insertar en qué tabla y el tipo de dato que almacena
     * @apiNote corregido
     */
    public int engadirTabla(Columna colum) {
        try (Statement sentencia = this.cdb.conexion.createStatement()) {
            String query = String.format("ALTER TABLE %s ADD %s %s;", colum.tabla, colum.nombreColumna, colum.dataType);
            if (colum.propiedades != null) {
                query += " " + colum.propiedades;
            }
            System.out.println(query);
            int res = sentencia.executeUpdate(query);
            System.out.printf("Se han añadido %d columna(s)", res);
            return res;

        } catch (SQLException e) {
            e.getSQLState();
            e.getErrorCode();
            e.getLocalizedMessage();
            return -1;
        }
    }

    /**
     * Muestra la metadata asociada al driver, la conexion, el usuario y el SGBD
     * empleado
     * 
     * @apiNote corregido
     */
    public void driverConnectionUserDBPInfo() {
        DatabaseMetaData dbMetaData;
        try {
            dbMetaData = this.cdb.conexion.getMetaData();
            System.out.printf("Nombre del driver %s "
                    + "\nVersión del driver %s"
                    + "\nUrl de la conexion %s"
                    + "\nUsuario %s"
                    + "\nNombre del SGBD %s"
                    + "\nVersión del SGBD %s", dbMetaData.getDriverName(), dbMetaData.getDriverVersion(),
                    dbMetaData.getURL(),
                    dbMetaData.getUserName(), dbMetaData.getDatabaseProductName(),
                    dbMetaData.getDatabaseProductVersion());

        } catch (SQLException e) {
            e.getSQLState();
            e.getErrorCode();
            e.getLocalizedMessage();
        }
    }

    /**
     * Muestra todas las bases de datos del SGBD al que estamos conectados
     * 
     * @apiNote corregido
     */
    public void getDatabases() {
        DatabaseMetaData dbmd;
        ResultSet databases;
        try {
            dbmd = this.cdb.conexion.getMetaData();
            databases = dbmd.getCatalogs();
            while (databases.next()) {
                System.out.println(databases.getString("TABLE_CAT"));
            }
        } catch (SQLException e) {
            e.getSQLState();
            e.getErrorCode();
            e.getLocalizedMessage();
        }
    }

    /**
     * Muestra el nombre de las tablas de la base de datos a la que estamos
     * conectados
     * 
     * @implNote el ejercicio pide la info de la base de datos ADD
     * @apiNote corregido
     */
    public void getTablesInfo() {
        DatabaseMetaData dbmd;
        ResultSet tables;
        try {
            dbmd = this.cdb.conexion.getMetaData();
            // para poner las tablas de ADD cambiariamos el parámetro abajo
            tables = dbmd.getTables(this.cdb.database, null, null, null);
            while (tables.next()) {
                System.out.printf("Nombre tabla: %s"
                        + "\tTipo de tabla: %s\n", tables.getString("TABLE_NAME"), tables.getString("TABLE_TYPE"));
            }
            tables.first();
            System.out.println("\nAHORA SOLO LAS VISTAS");
            while (tables.next()) {
                // igual hay una mejor forma de comprobar esto?-> EFECTIVAMENTE, usando el get
                // tables y modificando el parametro types(arriba está null)
                if (tables.getString("TABLE_TYPE").equals("VIEW")) {
                    System.out.println(tables.getString("TABLE_NAME"));
                }
            }
        } catch (SQLException e) {
            e.getSQLState();
            e.getErrorCode();
            e.getLocalizedMessage();
        }
    }

    /**
     * Muestra todas las tablas de todas las bases de datos de nuestra conexión al
     * SGBD
     * 
     * @apiNote corregido
     */
    public void getAllTablesFromAllDatabases() {
        DatabaseMetaData dbmd;
        ResultSet tablas, databases;
        try {
            dbmd = this.cdb.conexion.getMetaData();
            databases = dbmd.getCatalogs();
            while (databases.next()) {
                tablas = dbmd.getTables(databases.getString("TABLE_CAT"), null, null, null);
                System.out.println("DATABASE: " + databases.getString("TABLE_CAT"));
                while (tablas.next()) {
                    System.out.printf("\tNombre tabla: %s"
                            + "\tTipo de tabla: %s\n", tablas.getString("TABLE_NAME"), tablas.getString("TABLE_TYPE"));
                }
            }
        } catch (SQLException e) {
            e.getSQLState();
            e.getErrorCode();
            e.getLocalizedMessage();
        }
    }

    /**
     * Muestra los procesos que se están llevando a cabo en la base de datos en la
     * que estamos conectados
     * 
     * @apiNote corregido
     */
    public void getProcedures() {
        DatabaseMetaData dbmd;
        ResultSet procedures;
        try {
            dbmd = this.cdb.conexion.getMetaData();
            procedures = dbmd.getProcedures(this.cdb.database, null, null);
            while (procedures.next()) {
                System.out.println(procedures.getString("PROCEDURE_NAME"));
            }
        } catch (SQLException e) {
            e.getSQLState();
            e.getErrorCode();
            e.getLocalizedMessage();
        }
    }

    /**
     * Muestra de las tablas que cumplan el patrón los siguientes datos de sus
     * columnas: posición, base de datos, tabla, tipo de dato, tamaño de la columna,
     * permite nulos, se autoincrementa
     * 
     * @param tableNamePattern         parte del nombre de la tabla, o nombre exacto
     *                                 si los parámetros son false
     * @param permiteMatchALaIzquierda permite tablas que cumplan el patrón del
     *                                 nombre, sin importar que no lo cumpla a la
     *                                 izquierda de este
     * @param permiteMatchALaDerecha   permite tablas que cumplan el patrón del
     *                                 nombre, sin importar que no lo cumpla a la
     *                                 derecha de este
     */
    public void getDatosDeLasTablas(String tableNamePattern, boolean permiteMatchALaIzquierda,
            boolean permiteMatchALaDerecha) {
        DatabaseMetaData dbmd;
        ResultSet databases, columnas;

        if (permiteMatchALaIzquierda) {
            tableNamePattern = "%" + tableNamePattern;
        }
        if (permiteMatchALaDerecha) {
            tableNamePattern += "%";
        }
        try {
            dbmd = this.cdb.conexion.getMetaData();
            databases = dbmd.getCatalogs();
            while (databases.next()) {
                columnas = dbmd.getColumns(databases.getString("TABLE_CAT"), null, tableNamePattern, null);
                while (columnas.next()) {
                    System.out.printf("Posicion %d"
                            + "\nBase de datos\t %s"
                            + "\nTabla \t %s"
                            + "\nTipo de dato \t %s"
                            + "\nTamaño de la columna\t %d"
                            + "\nPermite nulos?\t %s"
                            + "\nSe autoincrementa sola?\t %s\n\n",
                            columnas.getRow(),
                            columnas.getString("TABLE_CAT"),
                            columnas.getString("TABLE_NAME"),
                            columnas.getString("TYPE_NAME"),
                            columnas.getInt("COLUMN_SIZE"),
                            columnas.getInt("NULLABLE") == 1 ? "Sí" : "No", // tecnicamente esto tiene un tercer valor,
                                                                            // cuando no se sabe si se puede nular o no
                                                                            // pero...//TAMBIEN acabo de ver que existe
                                                                            // el getString("IS_NULLABLE")
                            columnas.getString("IS_AUTOINCREMENT"));
                }
            }

        } catch (SQLException e) {
            e.getSQLState();
            e.getErrorCode();
            e.getLocalizedMessage();
        }
    }

    /**
     * Muestra las claves primarias y foráneas de las tablas de la db
     */
    public void getPrimaryAndForeignKeys() {
        DatabaseMetaData dbmd;
        ResultSet clavesPrimarias, clavesForaneas;
        try {
            dbmd = this.cdb.conexion.getMetaData();
            clavesPrimarias = dbmd.getPrimaryKeys(this.cdb.database, null, null);
            clavesForaneas = dbmd.getExportedKeys(this.cdb.database, null, null);
            while (clavesPrimarias.next()) {
                System.out.println("Clave primaria de: " + clavesPrimarias.getString("TABLE_NAME") + "\t"
                        + clavesPrimarias.getString("COLUMN_NAME"));
            }

            while (clavesForaneas.next()) {
                System.out.println("Clave foránea de: " + clavesForaneas.getString("FKTABLE_NAME") + "\t"
                        + clavesForaneas.getString("FKCOLUMN_NAME"));
            }
        } catch (SQLException e) {
            e.getSQLState();
            e.getErrorCode();
            e.getLocalizedMessage();
        }
    }

    /**
     * Realiza una sentencia y obtiene metadata de los resultados de la consulta
     */
    public void datosDeLasColumnasDeLaSentencia() {
        ResultSetMetaData rsmd;
        ResultSet res;
        try (Statement sentencia = this.cdb.conexion.createStatement()) {
            String query = "select *, nombre as nom from alumnos";
            res = sentencia.executeQuery(query);
            rsmd = res.getMetaData();
            System.err.println(rsmd.getColumnCount());
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                // tiene que empezar en 1..................
                System.out.printf("Nombre columna \t %s"
                        + "\nAlias de la columna \t %s"
                        + "\nTipo de dato: \t %s"
                        + "\nEs autoincrementado? \t %s"
                        + "\nPermite nulos? \t %s\n\n",
                        rsmd.getColumnName(i),
                        rsmd.getColumnLabel(i),
                        rsmd.getColumnTypeName(i),
                        rsmd.isAutoIncrement(i) ? "sí" : "no",
                        rsmd.isNullable(i) == 1 ? "sí" : "no");

            }
        } catch (SQLException e) {
            e.getSQLState();
            e.getErrorCode();
            e.getLocalizedMessage();
        }
    }

    public void consigueListaDeDrivers() {
        // DriverManager driverManager;
        try {
            // no sé si se refiere a esto...?
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                System.out.println(drivers.nextElement().toString());
            }
        } catch (Exception e) {
            // e.getSQLState();
            // e.getErrorCode();
            e.getLocalizedMessage();
        }
    }

    public int insertaAlumnos(Alumno[] alumnos) {

        try (Statement sentencia = this.cdb.conexion.createStatement()) {
            this.cdb.conexion.setAutoCommit(false);
            for (Alumno alumno : alumnos) {
                //TODO pregunta: ya que NO es autoincrementado y seleccionar el codigo máximo es una "jaimitada"...cuál es la mejor forma de hacerlo?
                String query = String.format("INSERT INTO alumnos(codigo,nombre,apellidos,altura,aula) VALUES (%d,%s,%s,%d,%d)");
                //TODO terminar el metodo
            }
        } catch (SQLException e) {
            try{
                if(this.cdb.conexion!=null){
                    this.cdb.conexion.rollback();
                }
            }catch(SQLException eo){
                eo.getSQLState();
                eo.getErrorCode();
                eo.getLocalizedMessage();
            }
        }                   
        
        return 0;
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
        // consu.buscaPorPatron("fr", 180);
        // }
        // tPosterior = System.currentTimeMillis();
        // System.out.println(tPosterior-tActual);
        // tActual=System.currentTimeMillis();
        // for (int i = 0; i < 1000000; i++) {
        // consu.buscaPorPatronSentenciaPreparada("fr", 180);
        // }
        // tPosterior=System.currentTimeMillis();
        // System.out.println(tPosterior-tActual);
        // Columna col = new Columna("suspensos", "asignaturas", "int", null);
        // consu.engadirTabla(col);
        // consu.getDatosDeLasTablas("a", false, true);
        consu.consigueListaDeDrivers();
        consu.cdb.CerrarConexion();
    }
}

/*
 * try(Statement sentencia = this.cdb.conexion.createStatement()){
 * 
 * 
 * 
 * } catch (SQLException e) {
 * e.getSQLState();
 * e.getErrorCode();
 * e.getLocalizedMessage();
 * }
 */
