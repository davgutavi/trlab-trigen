package strinitialpop;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import algcore.AlgorithmConfiguration;
import algcore.AlgorithmDataset;
import algcore.AlgorithmIndividual;
import algutils.AlgorithmRandomUtilities;
import algutils.TriclusterUtilities;
import initialpops.InitialPopStrategy;

public class RandomTimeSeriesStrategy implements InitialPopStrategy {

	public List<AlgorithmIndividual> generateIndividuals(int numberOfIndividuals, String individualClassName) {

		AlgorithmRandomUtilities ALEATORIOS = AlgorithmRandomUtilities.getInstance();
		AlgorithmConfiguration PARAM  = AlgorithmConfiguration.getInstance();
		AlgorithmDataset DATOS = PARAM.getData();
				
		List<AlgorithmIndividual> l = new LinkedList<AlgorithmIndividual>();

		for (int n = 0; n < numberOfIndividuals; n++) {

			int numeroGenes       = ALEATORIOS.getFromInterval(PARAM.getMinG(), PARAM.getMaxG());
			int numeroCondiciones = ALEATORIOS.getFromInterval(PARAM.getMinC(), PARAM.getMaxC());
			int numeroTiempos     = ALEATORIOS.getFromInterval(PARAM.getMinT(), PARAM.getMaxT());
			
			Collection<Integer> g = TriclusterUtilities.getDispersedRandomComponent(numeroGenes,DATOS.getGenesBag());
			Collection<Integer> c = TriclusterUtilities.getDispersedRandomComponent(numeroCondiciones,DATOS.getSamplesBag());

			int coordenadaTiempo = ALEATORIOS.getFromInterval(0, DATOS.getTimeSize() - 1);
			Collection<Integer> t = TriclusterUtilities.getIntervalComponent(coordenadaTiempo,numeroTiempos,DATOS.getTimeSize());
						
			AlgorithmIndividual nuevo = null;
			try {
				nuevo = (AlgorithmIndividual) (Class.forName(individualClassName)).newInstance();
				nuevo.initialize(g,c,t);
				nuevo.addEntry("from initial population: random");
				l.add(nuevo);
			} 
			catch (InstantiationException e) {e.printStackTrace();}
			catch (IllegalAccessException e) {e.printStackTrace();}
			catch (ClassNotFoundException e) {e.printStackTrace();}

		}

		return l;

	}

}
