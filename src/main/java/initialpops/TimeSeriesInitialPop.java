package initialpops;

import java.util.List;

import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algcore.InitialPop;
import algcore.TriGen;
import strinitialpop.HierarchycalTimeSeriesStrategy;
import strinitialpop.RandomTimeSeriesStrategy;
import strinitialpop.TensorStrategy;

public class TimeSeriesInitialPop implements InitialPop {

	private InitialPopStrategy aleatoria;
	private InitialPopStrategy jerarquica;
	private InitialPopStrategy tensores;

	public TimeSeriesInitialPop() {

		aleatoria = new RandomTimeSeriesStrategy();
		jerarquica = new HierarchycalTimeSeriesStrategy();
		tensores = new TensorStrategy ();

	}

	public List<AlgorithmIndividual> produceInitialPop() {

		TriGen     TRIGEN = TriGen.getInstance();
		AlgorithmConfiguration PARAM  = AlgorithmConfiguration.getInstance();
		
		List<AlgorithmIndividual> l = null;

		if (TRIGEN.getOngoingSolutionIndex() == 0) {

			int aleatorias = PARAM.getI()/2;			
			int cubos      = PARAM.getI()-aleatorias;
			l = aleatoria.generateIndividuals(aleatorias,TRIGEN.getIndividualClassName());
			List<AlgorithmIndividual> aux = tensores.generateIndividuals(cubos,TRIGEN.getIndividualClassName());
			l.addAll(aux);
			
		}

		else {

			int n_aleatorio = (int) (PARAM.getAle() * PARAM.getI());
			int aleatorias = n_aleatorio/2;
			int cubos      = n_aleatorio-aleatorias;
						
			int n_jerarquico = PARAM.getI() - n_aleatorio;
			l = aleatoria.generateIndividuals(aleatorias, TRIGEN.getIndividualClassName());
			List<AlgorithmIndividual> aux = tensores.generateIndividuals(cubos,TRIGEN.getIndividualClassName());
			l.addAll(aux);
			List<AlgorithmIndividual> aux1 = jerarquica.generateIndividuals(n_jerarquico, TRIGEN.getIndividualClassName());
			l.addAll(aux1);

		}

		return l;

	}

}
