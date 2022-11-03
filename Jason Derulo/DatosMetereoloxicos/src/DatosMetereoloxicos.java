
import java.io.FileNotFoundException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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

    
    public void DatosDoTempo(JsonObject jsonRoot){
       

        JsonArray array = (JsonArray) Json.createArrayBuilder().add(Json.createObjectBuilder()
        .add("temperatura", jsonRoot.getJsonObject("main").getJsonNumber("temp"))
        .add("humidade",jsonRoot.getJsonObject("main").getJsonNumber("humidity"))
        .add("probabilidade nubes",jsonRoot.getJsonObject("clouds").getInt("all"))
        .add("velocidade do vento",jsonRoot.getJsonObject("wind").getJsonNumber("speed"))
        .add("data",unixTimeToString( jsonRoot.getInt("dt")))
        .add("pronostico do tempo", getDiagnostico(jsonRoot))) //me falta arreglar esto
        .build(); 
            
        
        System.out.println(array.toString());
    }

    public JsonValue getDiagnostico(JsonObject jsonRoot){
        return null;
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
        JsonObject jsonRoot= jsonn.leeJSON(consultaWeather).asJsonObject();
        System.out.println(datos.CoordenadasOpenWeather(jsonRoot).toString());
        datos.DatosDoTempo(jsonRoot);
    }
}
