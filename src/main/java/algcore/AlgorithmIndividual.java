package algcore;

import java.text.DecimalFormat;
import java.util.Collection;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;


import java.util.LinkedList;
import java.util.List;

import utils.TextUtilities;

/**
 * Individual abstract class of <b>TriGen</b> algorithm. It is represent an individual of evolutionary process. 
 * Provide a default implementation for common methods and prototypes for implementation-dependent methods.
 * Every new implementation of individual must extend this class.
 * Globally a <b>TriGen</b> individual is composed of three groups (genes, conditions and times) of coordinates such that a 
 * coordinate represents a gene, condition or time from input dataset. After any modification of those three groups their coordinates
 * must be ordered from lowest to highest.
 * 
 * @author David Gutiérrez-Avilés
 */
public abstract class AlgorithmIndividual implements Comparable<AlgorithmIndividual>{
	
//	private static final Logger LOG = LoggerFactory.getLogger(AlgorithmIndividual.class);
	
	/**
	 * genes field.
	 */
	protected Collection<Integer> genes;
	
	/**
	 * conditions or samples field.
	 */
	protected Collection<Integer> samples;
	
	/**
	 * times field.
	 */
	protected Collection<Integer> times;
	
	/**
	 * fitness field.
	 */
	protected double        fitness;
	
	/**
	 * overlapping field.
	 */
	protected double        overlapping;
	
	/**
	 * sizes field.
	 */
	protected double        sizes;
	
	/**
	 * fitness function field.
	 */
	protected double        fitnessFunction;
			
	/**
	 * evaluated flag field.
	 */
	protected boolean evaluated; 
	
	/**
	 * altered flag field.
	 */
	protected boolean altered;
	
	/**
	 * register field.
	 */
	protected List<String>   register;
	
	/**
	 * Initializes the genetic material. Set the genes, conditions and times with input {@link Collection}s.
	 * 
	 * @param genes the group of genes to set
	 * @param samples the group of conditions to set
	 * @param times the group of times to set
	 */
	public abstract void initialize(Collection<Integer> genes, Collection<Integer> samples, Collection<Integer> times);

	/**
	 * Gets the gene in the <code>geneIndex</code> position of genes group.
	 * 
	 * @param geneIndex the position of gene in genes group
	 * @return gene coordinate
	 */
	public abstract int getGene(int geneIndex);
	
	/**
	 * Gets the sample or condition in the <code>sampleIndex</code> position of conditions group.
	 * 
	 * @param sampleIndex the position of sample or condition in conditions group
	 * @return condition coordinate
	 */
	public abstract int getSample (int sampleIndex);
	
	/**
	 * Gets the time in the <code>timeIndex</code> position of times group.
	 * 
	 * @param timeIndex the position of time in times group
	 * @return time coordinate
	 */
	public abstract int getTime(int timeIndex);
	
	/**
	 * Puts the input <code>gene</code> coordinate in genes group.
	 * 
	 * After insertion, the coordinates of genes group must be ordered from lowest to highest.
	 *  
	 * @param gene the gene coordinate
	 */
	public abstract void putGene (int gene);
	
	/**
	 * Puts the input <code>sample</code> coordinate in conditions group.
	 * 
	 * After insertion, the coordinates of conditions group must be ordered from lowest to highest.
	 *  
	 * @param sample the sample coordinate
	 */
	public abstract void putSample (int sample);
	
	/**
	 * Puts the input <code>time</code> coordinate in times group.
	 * 
	 * After insertion, the coordinates of times group must be ordered from lowest to highest.
	 *  
	 * @param time the time coordinate
	 */
	public abstract void putTime (int time);
	
	/**
	 * Deletes the input <code>gene</code> coordinate in genes group.
	 * 
	 * After removal, the coordinates of genes group must be ordered from lowest to highest.
	 * 
	 * @param gene the gene coordinate
	 */
	public abstract void deleteGene (int gene);
	
	/**
	 * Deletes the input <code>sample</code> coordinate in conditions group.
	 * 
	 * After removal, the coordinates of conditions group must be ordered from lowest to highest.
	 *  
	 * @param sample the sample coordinate
	 */
	public abstract void deleteSample (int sample);
	
	/**
	 * Deletes the input <code>time</code> coordinate in times group.
	 * 
	 * After removal, the coordinates of times group must be ordered from lowest to highest.
	 *  
	 * @param time the time coordinate
	 */
	public abstract void deleteTime (int time);
	
	//**compare
	
	public int compareTo(AlgorithmIndividual o) {
		
		double fe1 = fitnessFunction;
		
		double fe2 = o.getFitnessFunctionValue();
	
		return Double.compare(fe1, fe2);
		
	}

	/**
	 * Indicates if this individual can skip the evaluation operator of the <b>TriGen</b> algorithm's evolutionary process.
	 * 
	 * This methods helps {@link FitnessFunction} to decide if a individual is already evaluated or not.
	 * 
	 * @return <code>true</code> if can skip evaluation, otherwise <code>false</code>
	 */
	public boolean canSkipEvaluation() {
	
		boolean res = false;
		
		if (evaluated&&!altered)
			res = true;
		
		return res;
	}
	
	
	/**
	 * Sets <code>evaluated</code> state, <code>altered</code> state, <code>fitness</code> value, <code>overlapping</code> value,
	 * <code>sizes</code> value, <code>fitnessFunction</code> value, <code>register</code> value to initial state.
	 */
	protected void initialState (){
				
		evaluated = false;
		
		altered = false;
		
		fitness         = 0.0;
		
		overlapping     = 0.0;
		
		sizes           = 0.0;
		
		fitnessFunction = 0.0;
		
		register        = new LinkedList<String>();
		
	}
		
	/**
	 * Sets the <code>evaluated</code> state. 
	 * 
	 * <code>true</code> implies that individual has already been evaluated otherwise <code>false</code>.
	 *  
	 * If an individual is created (by initial population or crossover) this flag must set to <code>false</code>.
	 * 
	 * After evaluation of an individual this flag must be set to <code>true</code>.
	 * 
	 * @param evaluated the evaluated to set
	 */
	public void setEvaluated(boolean evaluated) {
		this.evaluated = evaluated;
	}

	/**
	 * Sets the <code>altered</code> state. 
	 * 
	 * <code>true</code> implies that individual has been altered (mutated) otherwise <code>false</code>.
	 *  
	 * If an individual is altered (by mutation operator) this flag must set to <code>true</code>.
	 * 
	 * After evaluation of an individual this flag must be set to <code>false</code>.
	 * 
	 * @param altered the altered to set
	 */
	public void setAltered(boolean altered) {
		this.altered = altered;
	}

	/**
	 * Gets complete version of {@link toString}. All individual parameters are showed.
	 * 
	 * @return the {@link String} with all parameters
	 */
	public String completeToString () {
		
		String r = "";
		
		DecimalFormat f = TextUtilities.getDecimalFormat('.', "0.000");
				
		r = "["+genes.size()+","+samples.size()+","+times.size()+"] "
				+ " evaluated?="+evaluated+ " altered?="+altered+
				" , FF = "+f.format(fitnessFunction)+" ["+f.format(fitness)+","+f.format(sizes)+","+f.format(overlapping)+"]"+"\n"
				+"Genes: "+genes+"\n"+
				 "Samples: "+samples+"\n"+
				 "Times: "+times+"\n"+
				 getRegisterReport("\n");
			
		return r;
		
	}

	public String toString() {

		String r = "";
		
		DecimalFormat f = TextUtilities.getDecimalFormat('.', "0.000");
		
		r = "["+genes.size()+","+samples.size()+","+times.size()+"] "
				+ "{"+register.get(0)+"-"+register.get(register.size()-1)+"}"
				+ " evaluated?="+evaluated+ " altered?="+altered+
				" , FF = "+f.format(fitnessFunction)+" ["+f.format(fitness)+","+f.format(sizes)+","+f.format(overlapping)+"]";
			
		return r;
	}

	/**
	 * Gets the gene coordinates.
	 * 
	 * @return the gene coordinates
	 */
	public Collection<Integer> getGenes() {
		return genes;
	}
	
	/**
	 * Gets the sample or condition coordinates.
	 * 
	 * @return the sample or condition coordinates
	 */
	public Collection<Integer> getSamples() {
		return samples;
	}

	/**
	 * Gets the time coordinates.
	 * 
	 * @return the time coordinates
	 */
	public Collection<Integer> getTimes() {
		return times;
	}
	
	/**
	 * Gets the number of gene coordinates.
	 * 
	 * @return the number of gene coordinates
	 */
	public int getGeneSize() {
		return genes.size();
	}
	
	/**
	 * Gets the number of sample or condition coordinates.
	 * 
	 * @return the number of sample or condition coordinates
	 */
	public int getSampleSize() {
		return samples.size();
	}
	
	/**
	 * Gets the number of time coordinates.
	 * 
	 * @return the number of time coordinates
	 */
	public int getTimeSize() {
		return times.size();
	}
			
	/**
	 * Gets the complete fitness function value. 
	 * 
	 * @return the complete fitness function value
	 */
	public double getFitnessFunctionValue() {
		return fitnessFunction;
	}
	
	/**
	 * Gets the fitness value. The value are normalized.
	 * 
	 * @return the fitness value
	 */
	public double getFitnessValue() {
		return fitness;
	}
	
	/**
	 * Gets the overlapping value of fitness function. The value are normalized and weighted.
	 * 
	 * @return the overlapping value
	 */
	public double getOverlappingValue() {
		return overlapping;
	}
	
	/**
	 * Gets the size value of fitness function. The value are normalized, weighted and complemented.
	 * 
	 * @return the size value
	 */
	public double getSizeValue() {
		return sizes;
	}
	
	/**
	 * Gets the {@link List} of {@link String} of entries. Each entry is a event for this individual through the evolutionary process.
	 * 
	 * @return the register
	 */
	public List<String> getRegister() {
		return register;
	}
	
	/**
	 * Gets all entries of this individual in a sequence form. 
	 * 
	 * @param sep the separator between each entry
	 * 
	 * @return the report with all entries
	 */
	public String getRegisterReport(String sep) {
		String r = "";
		
		for (String s: register)
			r+=s+sep;
		
		return r;
	}
	
	/**
	 * Sets the complete fitness function value.
	 * 
	 * @param fitnessFunctionValue the value to set
	 */
	public void setFitnessFunctionValue(double fitnessFunctionValue) {
		this.fitnessFunction = fitnessFunctionValue;
	}
	
	/**
	 * Sets the fitness value.
	 * 
	 * @param fitnessValue the value to set
	 */
	public void setFitnessValue(double fitnessValue) {
		this.fitness = fitnessValue;
	}

	/**
	 * Sets the overlapping value.
	 * 
	 * @param overlappingValue the value to set
	 */
	public void setOverlappingValue(double overlappingValue) {
		this.overlapping = overlappingValue;
	}
	
	/**
	 * Sets the size value.
	 * 
	 * @param sizeValue the value to set
	 */
	public void setSizeValue(double sizeValue) {
		this.sizes = sizeValue;
	}
		
	/**
	 * Add a new entry to register.
	 * 
	 * @param entry the entry to add
	 */
	public void addEntry(String entry) {
		register.add(entry);
	}
	
}