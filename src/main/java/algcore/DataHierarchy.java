package algcore;

import java.util.List;
import java.util.Map;

/**
 * Data hierarchy interface. It provides methods to consult system to avoid overlapping. 
 * 
 * @author David Gutiérrez-Avilés
 */
public interface DataHierarchy {
	
	/**
	 * Gets a {@link List} of <code>geneSize</code> coordinates of less visited genes form input {@link AlgorithmDataset}.
	 * 
	 * @param geneSize the number of genes
	 * @return less visited genes
	 */
	public List<Integer> getHierarchicalListOfGenes       (int geneSize);
	
	/**
	 * Gets a {@link List} of <code>sampleSize</code> coordinates of less visited samples or conditions form input {@link AlgorithmDataset}.
	 * 
	 * @param geneSize the number of samples or conditions
	 * @return less visited samples or conditions
	 */
	public List<Integer> getHierarchicalListOfSamples    (int sampleSize);
	
	/**
	 * Gets a {@link List} of <code>timeSize</code> coordinates of less visited times form input {@link AlgorithmDataset}.
	 * 
	 * @param geneSize the number of times
	 * @return less visited times
	 */
	public List<Integer> getHierarchicalListOfTimes     (int timeSize);
		
	/**
	 * Updates this data hierarchy with a new discovered solution.
	 * 
	 * @param solution the solution to update
	 */
	public void 		 updateDataHierarchy 				 (AlgorithmIndividual solution);
	
	//public void 		 appendHierarchicalString			 (String hierarchicalString);
	
	//public String 		 getHierarchicalString					 ();
	
	/**
	 * Gets a {@link List} of <code>genSize</code> *  <code>sampleSize</code> coordinates of less 
	 * visited genes and samples form input {@link AlgorithmDataset}.
	 * 
	 * @param geneSize the number of genes
	 * @param sampleSize the number of samples
	 * @return less visited genes and samples
	 */
	public List<Integer> [] buildGenesAndSamples       (int genSize, int sampleSize);
	
	/**
	 * Gets a {@link List} of <code>n</code> coordinates of less 
	 * visited genes and samples form input {@link AlgorithmDataset}.
	 * 
	 * @param n the number of coordinates
	 * @return less visited genes and samples
	 */
	public List<Integer> [] buildGenesAndSamples       (int n);
	
//	public int getSize ();
	
	/**
	 * Gets if data hierarchy is available, that is, if there are coordinates to visit.
	 * 
	 * @return <code>true</code> if there are coordinates to visit otherwise <code>false</code>
	 */
	public boolean isAvailable ();
	
	/**
	 * Gets the number of not visited genes.
	 * 
	 * @return the number of not visited genes
	 */
	public int getAvailableGenes ();
	
	/**
	 * Gets the number of not visited samples or conditions.
	 * 
	 * @return the number of not visited samples or conditions
	 */
	public int getAvailableSamples ();
	
//	public AlgorithmIndividual getRest ();
	
//	public AlgorithmIndividual toIndividual ();
	
	/**
	 * Gets the percentage of visited coordinates.
	 * 
	 * @return the percentage of visited coordinates
	 */
	public String getPercentage ();
	
//	public String getHierarchicalReport ();
	
	/**
	 * Gets a {@link Map} of genes coordinates. The key is the gene coordinate and the value is the number of times visited.
	 * 
	 * @return the gene coordinates and corresponding number o times visited
	 */
	public Map<Integer, Integer> getGenHierarchy() ;

	/**
	 * Gets a {@link Map} of samples or conditions coordinates. The key is the sample or condition coordinate and the value 
	 * is the number of times visited.
	 * 
	 * @return the sample or condition coordinates and corresponding number o times visited
	 */
	public Map<Integer, Integer> getSampleHierarchy() ;
	
	/**
	 * Gets a {@link Map} of times coordinates. The key is the time coordinate and the value is the number of times visited.
	 * 
	 * @return the time coordinates and corresponding number o times visited
	 */
	public Map<Integer, Integer> getTimeHierarchy() ;
	
	/**
	 * Initializes the initial data hierarchy state. 
	 * 
	 * @param geneSize the number of genes
	 * @param sampleSize the number of samples or conditions
	 * @param timeSize the number of times
	 */
	public void  initializeDataHierarchy (int geneSize, int sampleSize, int timeSize) ;
	
}