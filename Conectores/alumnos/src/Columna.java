public class Columna {
    public String nombreColumna;
    public String tabla;
    public String dataType;
    public String propiedades;

    public Columna(String nombreColumna,String nombreTabla,String datatype,String propiedades){
        this.nombreColumna=nombreColumna;
        tabla=nombreTabla;
        this.dataType=datatype;
        this.propiedades=propiedades;
    }
}
