package strinitialpop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algutils.AlgorithmRandomUtilities;
import algutils.TriclusterUtilities;
import initialpops.InitialPopStrategy;

public class HierarchycalTimeSeriesStrategy  implements InitialPopStrategy {

	public List<AlgorithmIndividual> generateIndividuals(int numberOfIndividuals, String individualClassName) {
		
		List<AlgorithmIndividual> l = new ArrayList<AlgorithmIndividual>(numberOfIndividuals);		
		
		ArrayList<ArrayList<Collection<Integer>>> jcoor = AlgorithmConfiguration.getInstance().
				getDataHierarchy().build_gs_coorinates(numberOfIndividuals);

		for(ArrayList<Collection<Integer>> gst:jcoor) {

			Collection<Integer> lg = gst.get(0);
			Collection<Integer> lc = gst.get(1);
			
			int nTime = AlgorithmRandomUtilities.getInstance().
					getFromInterval(AlgorithmConfiguration.getInstance().getMinT(), AlgorithmConfiguration.getInstance().getMaxT());
							
			int coordenadaTiempo = AlgorithmRandomUtilities.getInstance().getFromInterval(0, 
					AlgorithmConfiguration.getInstance().getData().getTimeSize() - 1);
			
			Collection<Integer> lt = TriclusterUtilities.getInstance().getIntervalComponent(coordenadaTiempo,nTime,
					AlgorithmConfiguration.getInstance().getData().getTimeSize());

			AlgorithmIndividual newIndividual = TriclusterUtilities.getInstance().
					buildIndividual(lg,lc,lt,individualClassName,"from initial population: hierarchical time series");
			
			l.add(newIndividual);
		
		}

		return l;

	}	

}