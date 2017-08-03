package initialpops;

/**
 * Operador que genera la poblacion inicial con un porcentaje de individuos aleatorios recibido por parámetro.
 * El resto se construye usando la jerarquia de los datos de entrada.
 * @author DAVID GUTIERREZ AVILES
 */

import java.util.List;

import strinitialpop.HierarchycalStrategy;
import strinitialpop.RandomStrategy;
import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algcore.InitialPop;
import algcore.TriGen;

public class BasicInitialPop implements InitialPop {

	private InitialPopStrategy totalmente_aleatoria;
	private InitialPopStrategy jerarquica;

	public BasicInitialPop() {

		totalmente_aleatoria = new RandomStrategy();
		jerarquica = new HierarchycalStrategy();

	}

	public List<AlgorithmIndividual> produceInitialPop() {

		TriGen     trigen = TriGen.getInstance();
		
		AlgorithmConfiguration config  = AlgorithmConfiguration.getInstance();
		
		List<AlgorithmIndividual> l = null;

		if (trigen.getOngoingSolutionIndex() == 0) {
			
			l = totalmente_aleatoria.generateIndividuals(config.getI(),trigen.getIndividualClassName());
		}

		else {

			int n_aleatorio = (int) (config.getAle() * config.getI());

			int n_jerarquico = config.getI() - n_aleatorio;

			l = totalmente_aleatoria.generateIndividuals(n_aleatorio, trigen.getIndividualClassName());
			
			List<AlgorithmIndividual> aux = jerarquica.generateIndividuals(n_jerarquico, trigen.getIndividualClassName());

			l.addAll(aux);

		}

		return l;

	}

}