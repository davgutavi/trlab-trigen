package strinitialpop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algutils.TriclusterUtilities;

/**
 * Estrategia para crear Individuos con la ayuda de la jerarquia de datos. 
 * @author David Gutiérrez-Avilés
 */

import initialpops.InitialPopStrategy;

public class HierarchycalStrategy implements InitialPopStrategy {

	public List<AlgorithmIndividual> generateIndividuals(int numberOfIndividuals, String individualClassName) {
		
		List<AlgorithmIndividual> l = new ArrayList<AlgorithmIndividual>(numberOfIndividuals);		
		
		ArrayList<ArrayList<Collection<Integer>>> jcoor = AlgorithmConfiguration.getInstance().
				getDataHierarchy().build_gst_coorinates(numberOfIndividuals);
		
		for(ArrayList<Collection<Integer>> gst:jcoor) {
			
			Collection<Integer> lg = gst.get(0);
			Collection<Integer> lc = gst.get(1);
			Collection<Integer> lt = gst.get(2);					
			
			AlgorithmIndividual newIndividual = TriclusterUtilities.getInstance().
					buildIndividual(lg,lc,lt,individualClassName,"from initial population: hierarchical");
			
			l.add(newIndividual);
			
		}

		return l;

	}

}
