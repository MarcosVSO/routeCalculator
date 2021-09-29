package base;

import java.util.ArrayList;

public class utilityFunctionCalculator {
	public float calculateUtility(Float distancia,Float duracao,ArrayList<String[]> restricoes) {
		
		if (distancia > Float.parseFloat(restricoes.get(0)[2]) ||  duracao > Float.parseFloat(restricoes.get(1)[2])) {
			return 999999;
		}else {
			return Float.parseFloat(restricoes.get(0)[1])*distancia + Float.parseFloat(restricoes.get(1)[1])*duracao;
		}
		
	}
}
