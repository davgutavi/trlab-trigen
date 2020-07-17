package strinitialpop;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algutils.AlgorithmRandomUtilities;
import algutils.TriclusterUtilities;
import initialpops.InitialPopStrategy;

public class Tensor2DStrategy  implements InitialPopStrategy {

	public List<AlgorithmIndividual> generateIndividuals(int numberOfIndividuals, String individualClassName) {

		List<AlgorithmIndividual> l = new LinkedList<AlgorithmIndividual>();

		for (int i = 0; i < numberOfIndividuals; i++) {

			int coordenadaGen = AlgorithmRandomUtilities.getInstance().
					getFromInterval(0, AlgorithmConfiguration.getInstance().getData().getGenSize() - 1);
			int coordenadaCondicion = AlgorithmRandomUtilities.getInstance().
					getFromInterval(0, AlgorithmConfiguration.getInstance().getData().getSampleSize() - 1);
			int coordenadaTiempo = AlgorithmRandomUtilities.getInstance().
					getFromInterval(0, AlgorithmConfiguration.getInstance().getData().getTimeSize() - 1);

			int tamG = AlgorithmRandomUtilities.getInstance().
					getFromInterval(AlgorithmConfiguration.getInstance().getMinG(), 
							AlgorithmConfiguration.getInstance().getMaxG());
			int tamC = AlgorithmRandomUtilities.getInstance().
					getFromInterval(AlgorithmConfiguration.getInstance().getMinC(), 
							AlgorithmConfiguration.getInstance().getMaxC());
			int tamT = AlgorithmRandomUtilities.getInstance().
					getFromInterval(AlgorithmConfiguration.getInstance().getMinT(), 
							AlgorithmConfiguration.getInstance().getMaxT());

			int[] radios = TriclusterUtilities.getInstance().getTensorLimits(coordenadaGen, coordenadaCondicion, coordenadaTiempo,
					tamG, tamC, tamT, AlgorithmConfiguration.getInstance().getData().getGenSize(), 
					AlgorithmConfiguration.getInstance().getData().getSampleSize(), 
					AlgorithmConfiguration.getInstance().getData().getTimeSize());
			
			Collection<Integer> genes = new LinkedList<Integer>();
			for (int g = radios[0]; g <= radios[1]; g++)
				genes.add(new Integer(g));

			Collection<Integer> condiciones = new LinkedList<Integer>();
			for (int c = radios[2]; c <= radios[3]; c++)
				condiciones.add(new Integer(c));

			int nTime = AlgorithmRandomUtilities.getInstance().
					getFromInterval(AlgorithmConfiguration.getInstance().getMinT(), AlgorithmConfiguration.getInstance().getMaxT());
				
			Collection<Integer> tiempos = TriclusterUtilities.getInstance().
					getDispersedRandomComponent(nTime,AlgorithmConfiguration.getInstance().getData().getTimesBag());

			AlgorithmIndividual newIndividual = TriclusterUtilities.getInstance().
					buildIndividual(genes,condiciones,tiempos,individualClassName,"from initial population: tensor 2D");
			
			l.add(newIndividual);

		}

		return l;

	}
	
}
