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

			
		List<AlgorithmIndividual> l = null;

		if (TriGen.getInstance().getOngoingSolutionIndex() == 0) {

			int aleatorias = AlgorithmConfiguration.getInstance().getI()/2;			
			int cubos      = AlgorithmConfiguration.getInstance().getI()-aleatorias;
			l = aleatoria.generateIndividuals(aleatorias,TriGen.getInstance().getIndividualClassName());
			List<AlgorithmIndividual> aux = tensores.generateIndividuals(cubos,TriGen.getInstance().getIndividualClassName());
			l.addAll(aux);
			
		}

		else {

			int n_aleatorio = (int) (AlgorithmConfiguration.getInstance().getAle() * AlgorithmConfiguration.getInstance().getI());
			int aleatorias = n_aleatorio/2;
			int cubos      = n_aleatorio-aleatorias;
						
			int n_jerarquico = AlgorithmConfiguration.getInstance().getI() - n_aleatorio;
			l = aleatoria.generateIndividuals(aleatorias, TriGen.getInstance().getIndividualClassName());
			List<AlgorithmIndividual> aux = tensores.generateIndividuals(cubos,TriGen.getInstance().getIndividualClassName());
			l.addAll(aux);
			List<AlgorithmIndividual> aux1 = jerarquica.generateIndividuals(n_jerarquico, TriGen.getInstance().getIndividualClassName());
			l.addAll(aux1);

		}

		return l;

	}

}
