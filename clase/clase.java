package clase;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

class clase {

	public static void main(String[] args) {
		String url = "http://api.weatherstack.com/current?access_key=31c5e5d97c1f5292d2b0ee53a5a0f941&query=Teziutlan&=";
		String respuesta = "";
		try {
			respuesta = peticionHttpGet(url);
			//Solo para evitar relleno
			//System.out.println("La respuesta es:\n" + respuesta);

			JsonParser parser = new JsonParser();

			// OBTENEMOS EL OBJETO PRINCIPAL

			JsonObject objetoPrincipal = parser.parse(respuesta).getAsJsonObject();
			//Solo para evitar relleno
			//System.out.println(objetoPrincipal);

			// OBTENEMOS LOS ATRIBUTOS PRIMARIOS DE ESE OBJETO ES DECIR TITULO Y EPISODIO

			JsonObject oblocalizacion = objetoPrincipal.get("location").getAsJsonObject();
			JsonObject obdatos = objetoPrincipal.get("current").getAsJsonObject();
			
				String ciudad = oblocalizacion.get("name").getAsString();
				String pais = oblocalizacion.get("country").getAsString();
				String temperatura = obdatos.get("temperature").getAsString();
				String humedad = obdatos.get("humidity").getAsString();
				
				System.out.println("----------------------------");
				System.out.println("Ciudad: "+ciudad);
				System.out.println("Pa�s: "+pais);
				System.out.println("----------------------------");
				System.out.println("\n"+"----------------------------");
				System.out.println("Temperatura = "+temperatura+"Centigrados.");
				System.out.println("Humedad = "+humedad+"%");
				System.out.println("----------------------------");
				

		} catch (Exception e) {
			// Manejar excepción
			e.printStackTrace();
		}
	}

	public static String peticionHttpGet(String urlParaVisitar) throws Exception {
		// Esto es lo que vamos a devolver
		StringBuilder resultado = new StringBuilder();
		// Crear un objeto de tipo URL
		URL url = new URL(urlParaVisitar);

		// Abrir la conexión e indicar que será de tipo GET
		HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
		conexion.setRequestMethod("GET");
		// Búferes para leer
		BufferedReader rd = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
		String linea;
		// Mientras el BufferedReader se pueda leer, agregar contenido a resultado
		while ((linea = rd.readLine()) != null) {
			resultado.append(linea);
		}
		// Cerrar el BufferedReader
		rd.close();
		// Regresar resultado, pero como cadena, no como StringBuilder
		return resultado.toString();
	}

}
