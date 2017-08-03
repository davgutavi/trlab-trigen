package individuals;

import java.util.Collection;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import algcore.AlgorithmIndividual;

public class SetTricluster extends AlgorithmIndividual {



	public void initialize(Collection<Integer> genes, Collection<Integer> samples, Collection<Integer> times) {
		
		this.genes        = fromCollectionToSet(genes);
		this.samples     =   fromCollectionToSet(samples);
		this.times      = fromCollectionToSet(times);
		
		initialState ();
		
	}
	
	
	private SortedSet<Integer> fromCollectionToSet (Collection<Integer> component){
		
		SortedSet<Integer> r = new TreeSet<Integer>();
		
		for (Integer coor:component){
			r.add(coor);
		}
		
		return r;
		
	}
	
	

	@Override
	public int getGene(int geneIndex) {
		Integer[] aux1 = genes.toArray(new Integer[0]);
		Integer aux2 = aux1[geneIndex];
		return aux2.intValue();
	}

	@Override
	public int getSample(int sampleIndex) {
		Integer[] aux1 = samples.toArray(new Integer[0]);
		Integer aux2 = aux1[sampleIndex];
		return aux2.intValue();
	}
	
	@Override
	public int getTime(int timeIndex) {
		Integer[] aux1 = times.toArray(new Integer[0]);
		Integer aux2 = aux1[timeIndex];
		return aux2.intValue();
	}

	
public void putGene (int gen){
	((Set<Integer>)genes).add(new Integer(gen));
}

public void putSample (int condicion){
	((Set<Integer>)samples).add(new Integer(condicion));
}

public void putTime (int tiempo){
	((Set<Integer>)times).add(new Integer(tiempo));
}

public void deleteGene (int gen){ 
	((Set<Integer>)genes).remove(new Integer(gen));
}

public void deleteSample (int condicion){
	((Set<Integer>)samples).remove(new Integer(condicion));
}

public void deleteTime (int tiempo){
	((Set<Integer>)times).remove(new Integer(tiempo));
}

}