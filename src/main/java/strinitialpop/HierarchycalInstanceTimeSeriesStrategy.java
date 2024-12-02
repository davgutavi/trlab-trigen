package strinitialpop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algutils.AlgorithmRandomUtilities;
import algutils.TriclusterUtilities;
import initialpops.InitialPopStrategy;

public class HierarchycalInstanceTimeSeriesStrategy  implements InitialPopStrategy {

	public List<AlgorithmIndividual> generateIndividuals(int numberOfIndividuals, String individualClassName) {
		
		List<AlgorithmIndividual> l = new ArrayList<AlgorithmIndividual>(numberOfIndividuals);		
		
		ArrayList<ArrayList<Collection<Integer>>> jcoor = AlgorithmConfiguration.getInstance().
				getDataHierarchy().build_st_coorinates(numberOfIndividuals);

		for(ArrayList<Collection<Integer>> gst:jcoor) {

			Collection<Integer> lc = gst.get(0);
			Collection<Integer> lt = gst.get(1);
			
			int nGenes = AlgorithmRandomUtilities.getInstance().
					getFromInterval(AlgorithmConfiguration.getInstance().getMinG(), AlgorithmConfiguration.getInstance().getMaxG());
							
			int coordenadaGen = AlgorithmRandomUtilities.getInstance().getFromInterval(0, 
					AlgorithmConfiguration.getInstance().getData().getGenSize() - 1);
			
			Collection<Integer> lg = TriclusterUtilities.getInstance().getIntervalComponent(coordenadaGen,nGenes,
					AlgorithmConfiguration.getInstance().getData().getGenSize());

			AlgorithmIndividual newIndividual = TriclusterUtilities.getInstance().
					buildIndividual(lg,lc,lt,individualClassName,"from initial population: hierarchical instance time series");
			
			l.add(newIndividual);
		
		}

		return l;

	}	

}
