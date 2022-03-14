package meaningcloud;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.opencensus.stats.Aggregation.Count;

class APIMeaningE {
	public static void main(String[] args) {
		String url = "https://api.meaningcloud.com/sentiment-2.1?key=7326ac377e9b605b2bb463bbe9ae3192&lang=es&txt=El%20hotel%20es%20muy%20bonito%20y%20limpio,%20sin%20embargo,%20es%20un%20poco%20caro";
		String respuesta = "";
		try {
			respuesta = peticionHttpGet(url);
			System.out.println("La respuesta es:\n" + respuesta);
			JsonParser parser = new JsonParser();
			
			//objeto principal
			JsonObject objetoPrincipal = parser.parse(respuesta).getAsJsonObject();
			String sentimientogeneralopinion = objetoPrincipal.get("score_tag").getAsString();
			String emocion = "";
			if (sentimientogeneralopinion.equals("P")) {
				emocion = "Positiva";
			}
			else if (sentimientogeneralopinion.equals("N")) {
				emocion = "Negativa";
			}else {
				emocion = "Neutro";
			}
			
			System.out.println("================================");
			System.out.println("La opinion general es: " + emocion);
			System.out.println("================================");
				
			// Entrar al arreglo de lista de oraciones "sentence_list"
			JsonArray sentence_list = objetoPrincipal.get("sentence_list").getAsJsonArray();

			// Recorremos los objetos del arreglo
			for (JsonElement sentence_lists : sentence_list) {
				//obtenemos cada objeto del arreglo
				JsonObject elementsentence = sentence_lists.getAsJsonObject();
				// Obtenemos los datos primirivos de texto "text" y sentimiento de la oración "score_tag"
				String text = elementsentence.get("text").getAsString();
				String sentir = elementsentence.get("score_tag").getAsString();
				String tex;
				
				if (sentir.equals("P") || sentir.equals("P+")) {
					tex = "Positivo";
				}
				else if (sentir.equals("N")) {
					tex = "Negativo";
				}else {
					tex = "Neutro";
				}
				
				// Se imprime en consola
				System.out.println("\n================================");
				System.out.println("Oracion: " + text);
				System.out.print("Sentimiento: " + tex + "\n");
				System.out.println("================================\n");
				
				JsonArray arreglodesglose = elementsentence.get("segment_list").getAsJsonArray();
				
			for(JsonElement itemsss : arreglodesglose) {
				JsonObject elementtag = itemsss.getAsJsonObject();
				 String oraciond = elementtag.get("text").getAsString();
				 String sentird = elementtag.get("score_tag").getAsString();
				 String texx = "";
				 
				if (sentird.equals("P") || sentird.equals("P+")){	 
					texx = "Positivo";
				}else if (sentird.equals("N")) {
					texx = "Negativo";
				}else {
					texx = "Neutro";
				}
				 
				System.out.println("================================");
				System.out.println("Oracion desglosada: "+oraciond);
				System.out.println("Sentimiento: "+texx);
				System.out.println("================================");
			}
				

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