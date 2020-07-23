package individuals;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import algcore.AlgorithmIndividual;

public class CoorTricluster extends AlgorithmIndividual {
	
	
	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(CoorTricluster.class);
	
	private List<Integer> lgenes;
	private List<Integer> lsamples;
	private List<Integer> ltimes;
	
	
	@Override
	public void initialize(Collection<Integer> genes, Collection<Integer> samples, Collection<Integer> times) {
		
		
		
//		LOG.debug("genes = "+genes.toString());
		this.genes    = fromCollectionToList(genes);
		this.samples  = fromCollectionToList(samples);
		this.times    = fromCollectionToList(times);
		
		lgenes = (List<Integer>)this.genes;
		lsamples = (List<Integer>)this.samples;
		ltimes= (List<Integer>)this.times;
				
		initialState ();
		

	
	}
	
	

	private List<Integer> fromCollectionToList (Collection<Integer> component){
		
		List<Integer> r = new LinkedList<Integer>();
		
		for (Integer coor:component){
			r.add(new Integer(coor.intValue()));
		}
		
		Collections.sort(r);
		
		return r;
		
	}
	
	@Override
	public int getGene(int geneIndex) {
		return (lgenes.get(geneIndex)).intValue();
	}

	@Override
	public int getSample(int sampleIndex) {
		return (lsamples.get(sampleIndex)).intValue();
	}
	
	@Override
	public int getTime(int timeIndex) {
		return (ltimes.get(timeIndex)).intValue();
	}
	
	@Override
	public void putGene (int gene){
		lgenes.add(new Integer(gene));
		Collections.sort(lgenes);
	}
	
	@Override
	public void putSample (int sample){
		lsamples.add(new Integer(sample));
		Collections.sort(lsamples);
	}
	
	@Override
	public void putTime (int time){
		ltimes.add(new Integer(time));
		Collections.sort(ltimes);
	}
	
	@Override
	public void deleteGene (int gene){ 
		lgenes.remove(new Integer(gene));
	}
	
	@Override
	public void deleteSample (int sample){
		lsamples.remove(new Integer(sample));
	}
	
	@Override
	public void deleteTime (int time){
		ltimes.remove(new Integer(time));
	}
	
	public String toString() {

		String r = "";
		
		r = "["+genes.size()+","+samples.size()+","+times.size()+"]";
		
//		DecimalFormat f = TextUtilities.getDecimalFormat('.', "0.000");
//		
//		r = "["+genes.size()+","+samples.size()+","+times.size()+"] "
//				+"("+lgenes.get(0)+"-"+lgenes.get(lgenes.size()-1)+") "
//				+"("+lsamples.get(0)+"-"+lsamples.get(lsamples.size()-1)+") "
//				+"("+ltimes.get(0)+"-"+ltimes.get(ltimes.size()-1)+") "
//				+ " evaluated?="+evaluated+ " altered?="+altered+
//				" , FF = "+f.format(fitnessFunction)+" ["+f.format(fitness)+","+f.format(sizes)+","+f.format(overlapping)+"]";
			
		return r;
	}
	
	
	
}