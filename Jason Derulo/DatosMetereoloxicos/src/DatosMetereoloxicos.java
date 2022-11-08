
import java.io.FileNotFoundException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonString;
import javax.json.JsonValue;

public class DatosMetereoloxicos {
    
    Jsonn jsonn = new Jsonn();

    public class prediccion{
        JsonArray prediccions;

        public prediccion(JsonValue[] prediccions){
           
        }
    }

    public JsonObject DatosPorNomeLocalidade(String localidade){
        JsonObject ficheiroJson;
        try {
            ficheiroJson= jsonn.leeJSON("http://api.openweathermap.org/data/2.5/weather?q="+localidade+
            ",es&lang=es&APPID=a975f935caf274ab016f4308ffa23453").asJsonObject();
        } catch (Exception e) {
            System.err.println("erro co nome da localidade");
            return null;
        }
        return ficheiroJson;
    }

    public JsonObject DatosPorLocalizacion(double latitude, double lonxitude){
        JsonObject ficheiroJson;
        try {
            ficheiroJson=jsonn.leeJSON("http://api.openweathermap.org/data/2.5/weather?lat="+latitude+"&lon="+lonxitude+"&APPID=8f8dccaf02657071004202f05c1fdce0").asJsonObject();
        } catch (Exception e) {
            System.err.println("erro cos datos de lonxitude e latitude");
            return null;
        }
        return ficheiroJson;
    }

    public JsonObject ProximasPrediccionsPorLocalizacion(double lat, double lonx, int predicions){
        JsonObject ficheiroJson;
        try {
            ficheiroJson = jsonn.leeJSON("http://api.openweathermap.org/data/2.5/find?lat="+lat+"&lon="+lonx+"&cnt="+predicions+"&APPID=8f8dccaf02657071004202f05c1fdce0").asJsonObject();
        } catch (Exception e) {
            System.err.println("hola perdon");
            return null;
        }
        return ficheiroJson;
    }


    /**
     * Devolve o ID de calquer JSON obtido de OpenWeatherMap
     * @param jsonRoot arquivo JSON lido
     * @return o valor do ID
     */
    public int IDdunhaCidadeOpenWeather(JsonObject jsonRoot){
        return jsonRoot.getInt("id");
    }

    /**
     * Devolve a cidade de calquer JSON obtido de OpenWeatherMap
     * @param jsonRoot arquivo JSON lido
     * @return o nome da cidade
     */
    public String NomeDunhaCidadeOpenWeather(JsonObject jsonRoot){
        return jsonRoot.getString("name");
    }

    /**
     * Devolve as coordenadas de calquer JSON obtido de OpenWeatherMap
     * @param jsonRoot arquivo JSON lido
     * @return o valor das coordenadas
     */
    public JsonObject CoordenadasOpenWeather(JsonObject jsonRoot){
        return jsonRoot.getJsonObject("coord");
    }

    /**
     * Devolve a temperatura, humidade, probabilidade de nubes, velocidade do vento, a data e os distintos pronosticos do tempo
     * @param jsonRoot nodo raiz do arquivo Json de OpenWeatherMaps
     * @return os datos nunha collecion
     */
    public ArrayList<Object> getDatosDoTempo(JsonObject jsonRoot){

            ArrayList<Object> datos = new ArrayList<>();
            datos.add("temperatura");
            datos.add(jsonRoot.getJsonObject("main").getJsonNumber("temp"));
            datos.add("humidade");
            datos.add(jsonRoot.getJsonObject("main").getJsonNumber("humidity"));
            datos.add("probabilidade de nubes");
            datos.add(jsonRoot.getJsonObject("clouds").getJsonNumber("all"));
            datos.add("velocidade do vento");
            datos.add(jsonRoot.getJsonObject("wind").getJsonNumber("speed"));
            datos.add("data");
            datos.add(unixTimeToString(jsonRoot.getInt("dt")));
            datos.add("pronosticos do tempo ");
            datos.add(getPronosticos(jsonRoot.getJsonArray("weather")));

            System.out.println(datos.toString());
            
        return datos;
    }
    public ArrayList<Object> getPronosticos(JsonArray jsonRoot){

        ArrayList<Object> pronosticos = new ArrayList<>();
        for (int i = 0; i < jsonRoot.size(); i++) {
            pronosticos.add("Pronostico "+(i+1));
            pronosticos.add(jsonRoot.getJsonObject(i).getString("description"));
        }
        return pronosticos;
    }
    
    public ArrayList<Object> getDatosDoTempoMultiplesCidades(JsonObject jsonRoot){

        //por algun motivo esto de aqui me peta el visual studio code
        ArrayList<Object> datosCidades = new ArrayList<>();

        JsonArray listaDeJsonObj = jsonRoot.getJsonArray("list");
        System.err.println(listaDeJsonObj.size());
        for (int i = 0; i < listaDeJsonObj.size(); i++) {
            datosCidades.add(listaDeJsonObj.getJsonObject(i).getString("name"));
            datosCidades.add(getDatosDoTempo(listaDeJsonObj.getJsonObject(i)));
        }
        System.out.println(datosCidades.toString());
        return datosCidades;
    }
    public String unixTimeToString (int unixTime){
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return Instant.ofEpochSecond(unixTime).atZone(ZoneId.of("GMT+1")).format(formatter);
    }
    public static void main(String[] args) {
        
        DatosMetereoloxicos datos = new DatosMetereoloxicos();
        //System.out.println(datos.DatosPorNomeLocalidade("vigo").toString());
        Jsonn jsonn = new Jsonn();
        String consultaWeather="http://api.openweathermap.org/data/2.5/weather?lat=42.232819&lon=-8.72264&units=metric&APPID=8f8dccaf02657071004202f05c1fdce0";
        String consultaWeatherCidadesProximas = "http://api.openweathermap.org/data/2.5/find?lat=42.232819&lon=-8.72264&cnt=5&APPID=8f8dccaf02657071004202f05c1fdce0";
        JsonObject jsonRoot= jsonn.leeJSON(consultaWeather).asJsonObject();
        //System.out.println(datos.CoordenadasOpenWeather(jsonRoot).toString());
        //datos.getDatosDoTempo(jsonRoot);
        JsonObject jsonRootMultiples = jsonn.leeJSON(consultaWeatherCidadesProximas).asJsonObject();
        datos.getDatosDoTempoMultiplesCidades(jsonRootMultiples);
        //System.out.println(jsonRoot.toString());
    }
}
