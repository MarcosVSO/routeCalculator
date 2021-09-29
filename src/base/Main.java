package base;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class Main {
	
	@SuppressWarnings("null")
	public static void main(String[] args) throws InterruptedException, IOException {
		ArrayList<String[]> rotas = new ArrayList<String[]>();
		ArrayList<String> coletas = new ArrayList<String>();
		ArrayList<String[]> restricoes = new ArrayList<String[]>();
		ArrayList<Route> objectRoutes = new ArrayList<Route>();
		ArrayList<String[]> entregas = new ArrayList<String[]>();
		ArrayList<String[]> melhoresRotasComColetas = new ArrayList<String[]>();
		
		String[] rota1 = {"-49.2576,-16.6800","-49.3215,-16.6682"}; // https://www.openstreetmap.org/directions?engine=fossgis_osrm_car&route=-16.6798%2C-49.2576%3B-16.6682%2C-49.3215#map=14/-16.6743/-49.2895&layers=N
		String[] rota2 = {"-49.2576,-16.6800","-49.3073,-16.7021"}; // https://www.openstreetmap.org/directions?engine=fossgis_osrm_car&route=-16.6798%2C-49.2576%3B-16.7032%2C-49.3075#map=15/-16.6916/-49.2827&layers=N
		String[] rota3 = {"-49.2576,-16.6800","-49.2742,-16.7134"}; // https://www.openstreetmap.org/directions?engine=fossgis_osrm_car&route=-16.6798%2C-49.2576%3B-16.7134%2C-49.2742#map=14/-16.6970/-49.2656&layers=N
		
		rotas.add(rota1);
		rotas.add(rota2);
		rotas.add(rota3);
		
		String coleta1 = "-49.2833,-16.6871"; // https://www.openstreetmap.org/note/new?lat=-16.6871&lon=-49.2833#map=14/-16.6973/-49.2696&layers=N
		String coleta2 = "-49.2848,-16.7142"; // https://www.openstreetmap.org/note/new?lat=-16.7142&lon=-49.2848#map=14/-16.7076/-49.2757&layers=N 
		
		coletas.add(coleta1);
		coletas.add(coleta2);
		
		String[] restricao1 = {"distância","2","5000"};
		String[] restricao2 = {"duração","10","240"};
		
		restricoes.add(restricao1);
		restricoes.add(restricao2);
		
		String[] entrega1 = {"-49.2576,-16.6800", "-49.260251,-16.681666", "-49.283726,-16.673946", "-49.32147,-16.668214"};
		String[] entrega2 = {"-49.2576,-16.6800", "-49.25817,-16.681058", "-49.271576,-16.687007","-49.3215,-16.6682"};
		String[] entrega3 = {"-49.2576,-16.6800", "-49.257295,-16.682307", "-49.264462,-16.695994", "-49.2742,-16.7134"};
		
		entregas.add(entrega1);
		entregas.add(entrega2);
		entregas.add(entrega3);
		
		routeShortestRouteCalculator calculator = new routeShortestRouteCalculator();
		for (String[] entrega : entregas) {
			melhoresRotasComColetas.add(calculator.addColetaToEntrega(coletas,entrega));
		}
		
		
		float distanciaComColeta[] = new float[melhoresRotasComColetas.size()];
		float distanciaSemColeta[] = new float[melhoresRotasComColetas.size()];
		float tempoComColeta[] = new float[melhoresRotasComColetas.size()];
		float tempoSemColeta[] = new float[melhoresRotasComColetas.size()];
		float diferencaDistanciaComESemColeta[] = new float[melhoresRotasComColetas.size()];
		float diferencaTempoComESemColeta[] = new float[melhoresRotasComColetas.size()];
		for (int i = 0; i < melhoresRotasComColetas.size(); i++) {
			distanciaComColeta[i] = calculator.calculateDistance(melhoresRotasComColetas.get(i));
			distanciaSemColeta[i] = calculator.calculateDistance(entregas.get(i));
			
			tempoComColeta[i] = calculator.calculateDuration(melhoresRotasComColetas.get(i));
			tempoSemColeta[i] = calculator.calculateDuration(entregas.get(i));
			
			diferencaDistanciaComESemColeta[i] = distanciaComColeta[i] - distanciaSemColeta[i];
			diferencaTempoComESemColeta[i] = tempoComColeta[i] - tempoSemColeta[i];
			
			System.out.println("Rota "+i+" : "+distanciaComColeta[i]+" - "+distanciaSemColeta[i]+" - "+diferencaDistanciaComESemColeta[i]);
			System.out.println("Rota "+i+" : "+tempoComColeta[i]+" - "+tempoSemColeta[i]+" - "+" - "+diferencaTempoComESemColeta[i]);
		}
		
		
		
		/*
		  var client = HttpClient.newHttpClient();
		  Gson gson = new Gson();
		  
		  for (String[] rota : rotas) {
			HttpRequest request = HttpRequest.newBuilder()
			          .uri(URI.create("http://0.0.0.0:5000/route/v1/driving/"+rota[0]+";"+rota[1]+"?geometries=geojson"))
			          .header("Accept", "application/json")
			          .build();
			
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			Route rotaAux = gson.fromJson(response.body(),Route.class);
			objectRoutes.add(rotaAux);
		}
		
		
		Float[][] menorDistancia = new Float[coletas.size()][rotas.size()];
		Float[][] menorDuracao = new Float[coletas.size()][rotas.size()];
		ArrayList<String[]> coordenadasRota = new ArrayList<String[]>();
		routeShortestRouteCalculator shortestRouteCalculator = new routeShortestRouteCalculator();
		int routeIndex = 0;
		for (Route r : objectRoutes) {
			System.out.println("Distância: "+r.getRoutesDistance());
			System.out.println("Duração: "+r.getRoutesDuration());
			    
			System.out.println("Início:"+r.getWaypointsSaida());
			System.out.println("Fim:"+r.getWaypointsChegada());
			
			for (String[] latitudeLongitude : r.getCoordenadasRota()) {
			    System.out.println(latitudeLongitude[0]+" , "+latitudeLongitude[1]);
			}
			
			
			for (int i = 0; i < coletas.size(); i++) {
				menorDistancia[i][routeIndex] = shortestRouteCalculator.menorDistanciaPonto(r.getCoordenadasRota(), coletas.get(i));				
			}
			
			for (int i = 0; i < coletas.size(); i++) {
				menorDuracao[i][routeIndex] = shortestRouteCalculator.menorDuracaoPonto(r.getCoordenadasRota(), coletas.get(i));				
			}
			
			routeIndex++;
		}
		
		System.out.println("Distância até a coleta");
		for (int i=0; i < coletas.size(); i++) {
			for (int j=0; j < rotas.size(); j++) {
				System.out.println("Coleta: "+i+" Rota: "+j+": "+menorDistancia[i][j]);
			}
		}
		
		System.out.println("\nDurações até a coleta");
		for (int i=0; i < coletas.size(); i++) {
			for (int j=0; j < rotas.size(); j++) {
				System.out.println("Coleta: "+i+" Rota: "+j+": "+menorDuracao[i][j]);
			}
		}
		
		utilityFunctionCalculator utilityCalculator = new utilityFunctionCalculator();
		Float[][] result = new Float[coletas.size()][rotas.size()];
		for (int i=0; i < coletas.size(); i++) {
			for (int j=0; j < rotas.size(); j++) {
				result[i][j] = utilityCalculator.calculateUtility(menorDistancia[i][j],menorDuracao[i][j],restricoes);
			}
		}
		
		System.out.println("\nUtilidades Finais");
		for (int i=0; i < coletas.size(); i++) {
			for (int j=0; j < rotas.size(); j++) {
				System.out.println("Coleta: "+i+" Rota: "+j+": "+result[i][j]);
			}
		}*/
		
		    
	}
}
