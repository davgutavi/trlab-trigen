package strinitialpop;

/**
 * Estrategia para crear Individuos con la ayuda de la jerarquia de datos. 
 * @author David Gutiérrez-Avilés
 */

import initialpops.InitialPopStrategy;

import java.util.LinkedList;
import java.util.List;

import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algcore.DataHierarchy;
import algutils.AlgorithmRandomUtilities;

public class HierarchycalStrategy implements InitialPopStrategy {

	public List<AlgorithmIndividual> generateIndividuals(int numberOfIndividuals, String individualClassName) {
		
		AlgorithmRandomUtilities ALEATORIOS = AlgorithmRandomUtilities.getInstance();
		AlgorithmConfiguration PARAM      = AlgorithmConfiguration.getInstance();
		DataHierarchy  JER        = PARAM.getDataHierarchy();
		
		List<AlgorithmIndividual> l = new LinkedList<AlgorithmIndividual>();

		for (int n = 0; n < numberOfIndividuals; n++) {

			int numeroGenes       = ALEATORIOS.getFromInterval(PARAM.getMinG(), PARAM.getMaxG());
			int numeroCondiciones = ALEATORIOS.getFromInterval(PARAM.getMinC(), PARAM.getMaxC());
			int numeroTiempos     = ALEATORIOS.getFromInterval(PARAM.getMinT(), PARAM.getMaxT());

						
			List<Integer> g =  JER.getHierarchicalListOfGenes(numeroGenes);
			
			List<Integer> c =  JER.getHierarchicalListOfSamples(numeroCondiciones);
			
			List<Integer> t =  JER.getHierarchicalListOfTimes(numeroTiempos);

			AlgorithmIndividual nuevo = null;
			try {
				nuevo = (AlgorithmIndividual) (Class.forName(individualClassName)).newInstance();
				nuevo.initialize(g,c,t);
				nuevo.addEntry("from initial population: hierarchical");
				l.add(nuevo);
			}
			catch (InstantiationException e) {e.printStackTrace();}
			catch (IllegalAccessException e) {e.printStackTrace();}
			catch (ClassNotFoundException e) {e.printStackTrace();}
		
		}// for

		return l;

	}// procedimiento

	

}// clase
