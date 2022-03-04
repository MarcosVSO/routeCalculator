package base;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import com.google.gson.Gson;
public class routeGenerator {
	float maxLat = (float) -16.6296;
	float minLat = (float) -16.7638;
	float maxLong = (float) -49.2194;
	float minLong = (float) -49.3334;
	int qtdRotas;
	
	public ArrayList<String[]> generateRoutes(int qtdRotas, int qtdPontosEntrega) throws IOException, InterruptedException{
		Gson gson = new Gson();
		var client = HttpClient.newHttpClient();
		Random rand = new Random();
		int index;
		
		String[] randomPoints = new String[qtdRotas];
		ArrayList<String[]> rotas = new ArrayList<String[]>();
		String centroDeDistribuicao = "-49.2576,-16.6800";
				
		for (int i = 0; i < qtdRotas; i++) {
			String coordinate = "";
			coordinate += Math.random()*(maxLong - minLong) + minLong;
			coordinate += ",";
			coordinate += Math.random()*(maxLat - minLat) + minLat;
			
			randomPoints[i] =  String.valueOf(coordinate);
		}
		
		//System.out.println("Ponto Inicial: "+centroDeDistribuicao);
		//System.out.println("Ponto Final: "+randomPoints[0]);
		
		for (int i = 0; i < qtdRotas; i++) {
			HttpRequest request = HttpRequest.newBuilder()
			          .uri(URI.create("http://0.0.0.0:5000/route/v1/driving/"+centroDeDistribuicao+";"+randomPoints[i]+"?geometries=geojson"))
			          .header("Accept", "application/json")
			          .build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			Route rotaAux = gson.fromJson(response.body(),Route.class);
			
			ArrayList<String[]> coordenadas = rotaAux.getCoordenadasRota();
			
			ArrayList<Integer> numbers = new ArrayList<Integer>();
			Random randomGenerator = new Random();
			while (numbers.size() < qtdPontosEntrega) {
				 int random = randomGenerator .nextInt(coordenadas.size() - 1) + 1;
				 if (!numbers.contains(random)) {
				        numbers.add(random);
			     }
			}
			
			int[] sortedIndexList = sortIndex(numbers);
			
			String[] rotaCalculada = new String[qtdPontosEntrega + 2];
			rotaCalculada[0] = centroDeDistribuicao;
			for (int k = 1; k < qtdPontosEntrega + 1 ; k++) {
				rotaCalculada[k] = coordenadas.get(sortedIndexList[k - 1])[0]+","+coordenadas.get(sortedIndexList[k - 1])[1];
			}
			rotaCalculada[qtdPontosEntrega + 1] = randomPoints[i];
			
			String rotaString = "";
			for (String coord : rotaCalculada) {
				rotaString+=coord +  "|"; 
			}
			System.out.println(rotaString);
			
			rotas.add(rotaCalculada);
		}
		
		return rotas;
	}
	
	public int[] sortIndex(ArrayList<Integer> numbers){
		int[] listaAsc = new int[numbers.size()];
		int aux;
		
		for (int i = 0; i < numbers.size(); i++) {
			listaAsc[i] = numbers.get(i);
		}
		
		for (int i = 0; i < numbers.size() - 1 ; i++) {
			if (listaAsc[i] > listaAsc[i+1]) {
				aux = listaAsc[i];
				listaAsc[i] = listaAsc[i+1];
				listaAsc[i+1] = aux;
			}
		}
		return listaAsc;
	}
}
