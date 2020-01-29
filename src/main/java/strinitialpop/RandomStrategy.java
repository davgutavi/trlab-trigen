package strinitialpop;

/**
 * Estrategia que crea Individuos de forma totalmete aleatoria. 
 * @author DAVID GUTIERREZ AVILES
 */

import initialpops.InitialPopStrategy;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algcore.AlgorithmDataset;
import algutils.AlgorithmRandomUtilities;
import algutils.TriclusterUtilities;

public class RandomStrategy implements InitialPopStrategy {

	public List<AlgorithmIndividual> generateIndividuals(int numberOfIndividuals, String individualClassName) {

		AlgorithmRandomUtilities ALEATORIOS = AlgorithmRandomUtilities.getInstance();		
		AlgorithmConfiguration PARAM  = AlgorithmConfiguration.getInstance();		
		AlgorithmDataset DATOS = PARAM.getData();
				
		List<AlgorithmIndividual> l = new LinkedList<AlgorithmIndividual>();

		for (int n = 0; n < numberOfIndividuals; n++) {

			int numeroGenes       = ALEATORIOS.getFromInterval(PARAM.getMinG(), PARAM.getMaxG());
			int numeroCondiciones = ALEATORIOS.getFromInterval(PARAM.getMinC(), PARAM.getMaxC());
			int numeroTiempos     = ALEATORIOS.getFromInterval(PARAM.getMinT(), PARAM.getMaxT());
			
			Collection<Integer> g = TriclusterUtilities.getInstance().getDispersedRandomComponent(numeroGenes,DATOS.getGenesBag());
			Collection<Integer> c = TriclusterUtilities.getInstance().getDispersedRandomComponent(numeroCondiciones,DATOS.getSamplesBag());
			Collection<Integer> t = TriclusterUtilities.getInstance().getDispersedRandomComponent(numeroTiempos,DATOS.getTimesBag());
			
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

		}// for

		return l;

	}

}
