import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.net.ssl.HttpsURLConnection;

public class DatosMetereoloxicosLocalidade {

    public JsonValue leeJSON(String ruta) {
        try {
            if (ruta.toLowerCase().startsWith("http://")) {
                return leerHttp(ruta);
            } else if (ruta.toLowerCase().startsWith("https://")) {
                return leerHttps(ruta);
            } else {
                return leerFichero(ruta);
            }
        } catch (IOException e) {
            System.out.println("Error procesando documento Json " +
                    e.getLocalizedMessage());
            return null;
        }
    }

    public JsonValue leerFichero(String ruta) throws FileNotFoundException {
        try (JsonReader reader = Json.createReader(new FileReader(ruta))) {
            return reader.read();
            /*
             * JsonStructure jsonSt = reader.read();
             * System.out.println(jsonSt.getValueType());
             * JsonObject jsonObj = reader.readObject();
             * System.out.println(jsonObj.getValueType());
             * JsonArray jsonArr = reader.readArray();
             * System.out.println(jsonArr.getValueType());
             */
        }
    }

    public JsonValue leerHttp(String direccion) throws IOException {
        URL url = new URL(direccion);
        try (InputStream is = url.openStream();
                JsonReader reader = Json.createReader(is)) {
            return reader.read();
        }
    }

    public JsonValue leerHttps(String direccion) throws IOException {
        URL url = new URL(direccion);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        try (InputStream is = conn.getInputStream();
                JsonReader reader = Json.createReader(is)) {
            return reader.read();
        } finally {
            conn.disconnect();
        }
    }

    public JsonObject getDatosMetereoloxicos(String localidad) {
        // como la consulta nos devuelve el object, lo pillamos como tal
        // (deberiamos pillarlo mas bien en modo generico, porque en unos sitios nos lo
        // daran como array)
        JsonObject json = leeJSON("http://api.openweathermap.org/data/2.5/weather?q=" + localidad + ",es&lang=es&APP" +
                "ID=a975f935caf274ab016f4308ffa23453").asJsonObject();
        System.out.println("http://api.openweathermap.org/data/2.5/weather?q=" + localidad + ",es&lang=es&APP" +
                "ID=a975f935caf274ab016f4308ffa23453");
        System.out.println(json.getString("name"));
        System.out.println(json.getJsonNumber("timezone"));

        //asi accedemos al array "weather", hijo de el arbol Json (que creamos arriba en la consulta)
        JsonArray tiempo = json.getJsonArray("weather");
        for (JsonValue valor : tiempo) {
            System.out.println(valor.asJsonObject().getString("description"));
            //y desde aqui obtenemos el hijo directo de weather
        }

        // json.getString("description")
        return json.asJsonObject();
    }

    public JsonObject getDatosMetereoloxicos(double latitude, double lonxitude) {
        JsonValue json = leeJSON("http://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + lonxitude
                + "&APPID=8f8dccaf02657071004202f05c1fdce0");
        return json.asJsonObject();
    }

    public static void main(String[] args) {
        DatosMetereoloxicosLocalidade datos = new DatosMetereoloxicosLocalidade();
        datos.getDatosMetereoloxicos("vigo");
    }
}
