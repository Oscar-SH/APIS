package OMDB;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

class ApiOMDBE {
	public static void main(String[] args) {
		String url = "http://www.omdbapi.com/?apikey=d92cf684&t=Game%20of%20Thrones&Season=1";
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

			String tituloserie = objetoPrincipal.get("Title").getAsString();
			String temporada = objetoPrincipal.get("Season").getAsString();

			System.out.println("Titulo de la serie: " + tituloserie);
			System.out.println("Temporada: " + temporada + "\n");

			// OBTENEMOS EL ARREGLO DE EPISODIOS DE LA TEMPORADA 1

			JsonArray arregloepisodios = objetoPrincipal.get("Episodes").getAsJsonArray();

			// RECORREMOS EL ARREGLO
			for (JsonElement itemsss : arregloepisodios) {
				JsonObject elementtag = itemsss.getAsJsonObject();
		
				Integer episodio = elementtag.get("Episode").getAsInt();
				String tituloe = elementtag.get("Title").getAsString();
				String fecha = elementtag.get("Released").getAsString();
				
				System.out.println("----------Episodio "+episodio+"-------");
				System.out.println("Titulo: " + tituloe);
				System.out.println("Fecha: " + fecha);
				System.out.println("----------------------------");
				
			}

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

