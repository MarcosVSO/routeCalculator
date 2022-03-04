package base;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;

public class routeShortestRouteCalculator {
	
	public float menorDistanciaPonto(String[] coordenadas, String coletas) throws IOException, InterruptedException {
			//ArrayList<String[]> rotasColeta = new ArrayList<String[]>();
			//System.out.println(coletas[0]+" , "+coletas[1]);
			float minDistance = 1000000;
			Gson gson = new Gson();
			var client = HttpClient.newHttpClient();

			for (String coord : coordenadas) {
				//System.out.println(coord[0]+" , "+coord[1]);
				HttpRequest request = HttpRequest.newBuilder()
				          .uri(URI.create("http://0.0.0.0:5000/route/v1/driving/"+coord+";"+coletas+"?geometries=geojson"))
				          .header("Accept", "application/json")
				          .build();
				HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
				Route rotaAux = gson.fromJson(response.body(),Route.class);
				//System.out.println(rotaAux.getRoutesDistance());
				if (Float.parseFloat(rotaAux.getRoutesDistance()) < minDistance) {
					minDistance = Float.parseFloat(rotaAux.getRoutesDistance());
				}
			}
		//System.out.println(minDistance);
		return minDistance;
	}
	
	public float menorDuracaoPonto(String[] coordenadas, String coletas) throws IOException, InterruptedException {
		//ArrayList<String[]> rotasColeta = new ArrayList<String[]>();
		//System.out.println(coletas[0]+" , "+coletas[1]);
		float minDuration = 1000000;
		Gson gson = new Gson();
		var client = HttpClient.newHttpClient();

		for (String coord : coordenadas) {
			//System.out.println(coord[0]+" , "+coord[1]);
			HttpRequest request = HttpRequest.newBuilder()
			          .uri(URI.create("http://0.0.0.0:5000/route/v1/driving/"+coord+";"+coletas+"?geometries=geojson"))
			          .header("Accept", "application/json")
			          .build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			Route rotaAux = gson.fromJson(response.body(),Route.class);
			//System.out.println(rotaAux.getRoutesDistance());
			if (Float.parseFloat(rotaAux.getRoutesDuration()) < minDuration) {
				minDuration = Float.parseFloat(rotaAux.getRoutesDuration());
			}
		}
	//System.out.println(minDistance);
	return minDuration;
	}
	
	public String[] addColetaToEntrega(ArrayList<String> coletas, String[] entrega) throws IOException, InterruptedException {
		Gson gson = new Gson();
		var client = HttpClient.newHttpClient();
		ArrayList<String> rotaComColetas = new ArrayList<String>();
		int indexBestOption = 0;
		float menorDist = 99999;
		
		for(String coord: entrega) {
			rotaComColetas.add(coord);
		}
		
		for(String coleta: coletas) {
			//System.out.println(coleta);
			for (int i = 0; i < rotaComColetas.size() - 1; i++) {
				//System.out.println(ent);
				
				HttpRequest request1 = HttpRequest.newBuilder()
						.uri(URI.create("http://0.0.0.0:5000/route/v1/driving/"+rotaComColetas.get(i)+";"+coleta+"?geometries=geojson"))
						.header("Accept", "application/json")
						.build();
				HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
				Route rotaAux1 = gson.fromJson(response1.body(), Route.class);
				
				HttpRequest request2 = HttpRequest.newBuilder()
						.uri(URI.create("http://0.0.0.0:5000/route/v1/driving/"+coleta+";"+rotaComColetas.get(i+1)+"?geometries=geojson"))
						.header("Accept", "application/json")
						.build();
				HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
				Route rotaAux2 = gson.fromJson(response1.body(), Route.class);
				
				float somaDistanciais = Float.parseFloat(rotaAux1.getRoutesDistance()) + Float.parseFloat(rotaAux2.getRoutesDistance());
				String[] rotaD = {entrega[i], String.valueOf(somaDistanciais)};
				//System.out.println(rotaD[0]+" -  "+rotaD[1]);
				
				if (Float.parseFloat(rotaD[1]) < menorDist){
					menorDist = Float.parseFloat(rotaD[1]);
					indexBestOption = i;
				}
				
			}
			
			//System.out.println(indexBestOption);
			
			rotaComColetas.add(indexBestOption+1,coleta);
			
			//System.out.println("\n Com coletas");
			for (String r : rotaComColetas) {
				//System.out.println(r);
			}
			//System.out.println("\n");
			
			menorDist = 999999;
			indexBestOption = 0;
		}
		String[] returnRoute = new String[rotaComColetas.size()];
		for (int i=0; i < rotaComColetas.size(); i++) {
			returnRoute[i] = rotaComColetas.get(i);
		}
		return returnRoute;
	}
	
	public float calculateDistance(String[] coordenadas) throws IOException, InterruptedException {
		var client = HttpClient.newHttpClient();
		Gson gson = new Gson();
		float totalDistance = 0;
		for (int i = 0; i < coordenadas.length - 1 ; i++) {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create("http://0.0.0.0:5000/route/v1/driving/"+coordenadas[i]+";"+coordenadas[i+1]+"?geometries=geojson"))
					.header("Accept", "application/json")
					.build();
			HttpResponse<String> response1 = client.send(request, HttpResponse.BodyHandlers.ofString());
			Route rotaAux = gson.fromJson(response1.body(), Route.class);
			totalDistance += Float.parseFloat(rotaAux.getRoutesDistance());
		}
		
		return totalDistance;
	}
	
	public float calculateDuration(String[] coordenadas) throws IOException, InterruptedException {
		var client = HttpClient.newHttpClient();
		Gson gson = new Gson();
		float totalDuration = 0;
		for (int i = 0; i < coordenadas.length - 1 ; i++) {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create("http://0.0.0.0:5000/route/v1/driving/"+coordenadas[i]+";"+coordenadas[i+1]+"?geometries=geojson"))
					.header("Accept", "application/json")
					.build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			Route rotaAux = gson.fromJson(response.body(), Route.class);
			totalDuration += Float.parseFloat(rotaAux.getRoutesDuration());
		}
		return totalDuration;
	}
	
	public float desvioColeta(String[] entrega, String coleta) throws IOException, InterruptedException {
		Gson gson = new Gson();
		var client = HttpClient.newHttpClient();
		Float[] desvios = new Float[entrega.length - 1];
		//for (String e : entrega) {System.out.println(e);}
		//System.out.println(coleta);
		
		for (int i = 0; i < entrega.length - 1; i++) {
			//System.out.println(ent);
			
			HttpRequest request1 = HttpRequest.newBuilder()
					.uri(URI.create("http://0.0.0.0:5000/route/v1/driving/"+entrega[i]+";"+coleta+"?geometries=geojson"))
					.header("Accept", "application/json")
					.build();
			HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
			Route rotaAux1 = gson.fromJson(response1.body(), Route.class);
			
			HttpRequest request2 = HttpRequest.newBuilder()
					.uri(URI.create("http://0.0.0.0:5000/route/v1/driving/"+coleta+";"+entrega[i+1]+"?geometries=geojson"))
					.header("Accept", "application/json")
					.build();
			HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
			Route rotaAux2 = gson.fromJson(response1.body(), Route.class);
			
			float somaDistanciais = Float.parseFloat(rotaAux1.getRoutesDistance()) + Float.parseFloat(rotaAux2.getRoutesDistance());
			desvios[i] = somaDistanciais;
		}
		Arrays.sort(desvios);
		//for (Float d : desvios) {System.out.println(d);}
		//System.out.println("\n");
		return (Float) desvios[desvios.length -1];
	}
	
	
}
