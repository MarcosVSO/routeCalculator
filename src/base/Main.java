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
		ArrayList<String> coletas = new ArrayList<String>();
		ArrayList<String[]> restricoes = new ArrayList<String[]>();
		ArrayList<String[]> entregas = new ArrayList<String[]>();
		ArrayList<String[]> melhoresRotasComColetas = new ArrayList<String[]>();
		
		/*String[] entrega1 = {"-49.2576,-16.6800", "-49.260251,-16.681666", "-49.283726,-16.673946", "-49.32147,-16.668214"}; // https://www.openstreetmap.org/directions?engine=fossgis_osrm_car&route=-16.6798%2C-49.2576%3B-16.6682%2C-49.3215#map=14/-16.6743/-49.2895&layers=N
		String[] entrega2 = {"-49.2576,-16.6800", "-49.25817,-16.681058", "-49.271576,-16.687007","-49.3215,-16.6682"};      // https://www.openstreetmap.org/directions?engine=fossgis_osrm_car&route=-16.6798%2C-49.2576%3B-16.7032%2C-49.3075#map=15/-16.6916/-49.2827&layers=N
		String[] entrega3 = {"-49.2576,-16.6800", "-49.257295,-16.682307", "-49.264462,-16.695994", "-49.2742,-16.7134"};    // https://www.openstreetmap.org/directions?engine=fossgis_osrm_car&route=-16.6798%2C-49.2576%3B-16.7134%2C-49.2742#map=14/-16.6970/-49.2656&layers=N
		
		entregas.add(entrega1);
		entregas.add(entrega2);
		entregas.add(entrega3);*/
		
		routeGenerator generator = new routeGenerator();
		entregas = generator.generateRoutes(4,1);
		
		/*for (String[] entrega: entregas) {
			System.out.println("\nRota");
			for (String coord : entrega) {
				System.out.println(coord);
			}
		}*/
		
		String coleta1 = "-49.2833,-16.6871"; // https://www.openstreetmap.org/note/new?lat=-16.6871&lon=-49.2833#map=14/-16.6973/-49.2696&layers=N
		String coleta2 = "-49.2848,-16.7142"; // https://www.openstreetmap.org/note/new?lat=-16.7142&lon=-49.2848#map=14/-16.7076/-49.2757&layers=N 
		
		coletas.add(coleta1);
		coletas.add(coleta2);
		
		String[] restricao1 = {"distância","2","10000"};
		String[] restricao2 = {"duração","10","3600"};
		String[] restricao3 = {"desvio","5","12000"};
		
		restricoes.add(restricao1);
		restricoes.add(restricao2);
		restricoes.add(restricao3);
		
		
		
		/*routeShortestRouteCalculator calculator = new routeShortestRouteCalculator();
		for (String[] entrega : entregas) {
			melhoresRotasComColetas.add(calculator.addColetaToEntrega(coletas,entrega));
		}
		
		System.out.println("Desvio total da Rota");
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
			
			System.out.println("Rota "+i+" : Distância total com coleta: "+distanciaComColeta[i]+" - Distância sem realizar coleta: "+distanciaSemColeta[i]+" - Diferença: "+diferencaDistanciaComESemColeta[i]);
			System.out.println("Rota "+i+" : Tempo total com coleta:"+tempoComColeta[i]+" - Tempo sem realizar coleta: "+tempoSemColeta[i]+" - "+" - Diferença: "+diferencaTempoComESemColeta[i]);
		}
		
		System.out.println("\n");*/
		
		Float[][] menorDistancia = new Float[coletas.size()][entregas.size()];
		Float[][] menorDuracao = new Float[coletas.size()][entregas.size()];
		routeShortestRouteCalculator shortestRouteCalculator = new routeShortestRouteCalculator();
		
				
		for (int i = 0; i < entregas.size(); i++) {
			
			for (int j = 0; j < coletas.size(); j++) {
				menorDistancia[j][i] = shortestRouteCalculator.menorDistanciaPonto(entregas.get(i), coletas.get(j));
				menorDuracao[j][i] = shortestRouteCalculator.menorDuracaoPonto(entregas.get(i), coletas.get(j));				

			}
		}
		
		System.out.println("Distância até a coleta");
		for (int i=0; i < coletas.size(); i++) {
			for (int j=0; j < entregas.size(); j++) {
				System.out.println("Coleta: "+i+" Rota: "+j+": "+menorDistancia[i][j]);
			}
		}
		
		System.out.println("\nTempo até a coleta");
		for (int i=0; i < coletas.size(); i++) {
			for (int j=0; j < entregas.size(); j++) {
				System.out.println("Coleta: "+i+" Rota: "+j+": "+menorDuracao[i][j]);
			}
		}
		
		Float[][] desvioColetas = new Float[coletas.size()][entregas.size()];
		for (int i=0; i < coletas.size(); i++) {
			for (int j = 0; j < entregas.size(); j++) {
				desvioColetas[i][j] = shortestRouteCalculator.desvioColeta(entregas.get(j),coletas.get(i));
			}
		}
		
		System.out.println("\nDesvios");
		for (int i=0; i < coletas.size(); i++) {
			for (int j = 0; j < entregas.size(); j++) {
				System.out.println("Coleta "+i+" - Rota "+j+" -> Desvio: "+desvioColetas[i][j]);
			}
		}
		
		utilityFunctionCalculator utilityCalculator = new utilityFunctionCalculator();
		Float[][] result = new Float[coletas.size()][entregas.size()];
		for (int i=0; i < coletas.size(); i++) {
			for (int j=0; j < entregas.size(); j++) {
				result[i][j] = utilityCalculator.calculateUtility(menorDistancia[i][j],menorDuracao[i][j],desvioColetas[i][j],restricoes);
			}
		}
		
		System.out.println("\nUtilidades Finais");
		for (int i=0; i < coletas.size(); i++) {
			for (int j=0; j < entregas.size(); j++) {
				System.out.println("Coleta: "+i+" Rota: "+j+": "+result[i][j]);
			}
		}
		
		var resultadoNormalizado = utilityCalculator.normalizeUtility(result,coletas.size(),entregas.size());
		System.out.println("\nUtilidades Normalizadas");
		for (int i=0; i < coletas.size(); i++) {
			for (int j=0; j < entregas.size(); j++) {
				System.out.println("Coleta: "+i+" Rota: "+j+": "+resultadoNormalizado[i][j]);
			}
		}
		 
	}
}
