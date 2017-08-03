package initialpops;

/**
 * Operador que genera la poblacion inicial con un porcentaje de individuos aleatorios recibido por parámetro.
 * El resto se construye usando la jerarquia de los datos de entrada.
 * @author DAVID GUTIERREZ AVILES
 */

import java.util.List;

import strinitialpop.EarthQuakeStrategy;
import strinitialpop.RandomStrategy;
import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algcore.DataHierarchy;
import algcore.InitialPop;
import algcore.TriGen;

public class EarthQuakeInitialPop implements InitialPop {

	private InitialPopStrategy jerarquicaTerremoto;
	
	private InitialPopStrategy totalmente_aleatoria;

	public EarthQuakeInitialPop() {
		
		jerarquicaTerremoto = new EarthQuakeStrategy();
		totalmente_aleatoria = new RandomStrategy();

	}

	public List<AlgorithmIndividual> produceInitialPop() {

		
		AlgorithmConfiguration PARAM = AlgorithmConfiguration.getInstance();
		TriGen TRI = TriGen.getInstance();
		DataHierarchy JER = PARAM.getDataHierarchy();
		
		float pAleatorio = PARAM.getAle(); 
		
		List<AlgorithmIndividual> l = null;
			
		int totales = PARAM.getI();
		
		boolean disponible = JER.isAvailable();
		
		if (disponible){
			
			int aleatorio = (int)(pAleatorio*totales);
			int jerarquicos = totales - aleatorio;
		
			l = jerarquicaTerremoto.generateIndividuals(jerarquicos,TRI.getIndividualClassName());
			
			List<AlgorithmIndividual> aux = totalmente_aleatoria.generateIndividuals(aleatorio, TRI.getIndividualClassName());
			
			l.addAll(aux);
					
		}
		else{
			l = totalmente_aleatoria.generateIndividuals(totales, TRI.getIndividualClassName());
		}
		
		return l;

	}

}
