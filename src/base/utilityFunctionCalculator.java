package base;

import java.util.ArrayList;
import java.util.Arrays;

public class utilityFunctionCalculator {
	public float calculateUtility(Float distancia,Float duracao,Float desvio,ArrayList<String[]> restricoes) {
		
		if (distancia > Float.parseFloat(restricoes.get(0)[2]) ||  duracao > Float.parseFloat(restricoes.get(1)[2]) || desvio > Float.parseFloat(restricoes.get(2)[2])) {
			return 99999;
		}else {
			return (Float.parseFloat(restricoes.get(0)[1]) * distancia + Float.parseFloat(restricoes.get(1)[1]) * duracao + Float.parseFloat(restricoes.get(2)[1]) * desvio);
		}
		
	}
	
	public Float[][] normalizeUtility(Float[][] result, int qtdColetas, int qtdEntregas){
		Float[][] resultNormalizada = new Float[qtdColetas][qtdEntregas];
		float minValue = result[0][0];
		float maxValue = result[0][0];
		float utilidadeNormalizadaZeroUm;
		float utilidadeNormalizadaInvertida;
		for (int i=0; i < qtdColetas; i++) {
			for (int j=0; j < qtdEntregas; j++) {
				//System.out.println("Coleta: "+i+" Rota: "+j+": "+result[i][j]);
				if (result[i][j] < minValue) {
					minValue = result[i][j];
				}
				if (result[i][j] > maxValue) {
					maxValue = result[i][j];
				}
			}
		}
		//System.out.println("\nMin Value: "+minValue);
		//System.out.println("Max Value: "+maxValue);
		//System.out.println("Normalizados: ");
		for (int i=0; i < qtdColetas; i++) {
			for (int j=0; j < qtdEntregas; j++) {
				utilidadeNormalizadaZeroUm = (result[i][j] - minValue)/(maxValue-minValue);
				//System.out.println("Coleta: "+i+" Rota: "+j+": "+ utilidadeNormalizadaZeroUm);
				utilidadeNormalizadaInvertida = 1 - utilidadeNormalizadaZeroUm;
				resultNormalizada[i][j] = utilidadeNormalizadaInvertida;
			}
		}
		return resultNormalizada;
	}
}
