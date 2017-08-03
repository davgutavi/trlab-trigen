package algcore;

import java.util.Set;


/**
 * Dataset interface which is been used by <b>TriGen</b> algorithm.
 * 
 * @author David Gutiérrez-Avilés
 */

public interface AlgorithmDataset {

	/**
	 * Gets the number of genes of this dataset.
	 * 
	 * @return the number of genes
	 * 
	 */
	public int getGenSize();

	/**
	 * Gets the number of conditions of this dataset.
	 * 
	 * @return the number of conditions
	 */
	public int getSampleSize();
	
	/**
	 * Gets the number of times of this dataset.
	 * 
	 * @return the number of times
	 */
	public int getTimeSize();

	/**
	 * Gets the expression level of a given gene-sample-time coordinate.
	 * 
	 * @param g gene coordinate
	 * 
	 * @param s sample or condition coordinate
	 * 
	 * @param t time coordinate
	 * 
	 * @return the expression level of gene-sample-time coordinate
	 */
	public double getValue(int g, int s, int t);
	
	/**
	 * Gets a {@link Set} with all gene coordinates of this dataset.
	 * 
	 * @return all gene coordinates
	 */
	public Set<Integer> getGenesBag ();
	
	/**
	 * Gets a {@link Set} with all sample or condition coordinates of this dataset.
	 * 
	 * @return all condition coordinates
	 */
	public Set<Integer> getSamplesBag ();
	
	/**
	 * Gets a {@link Set} with all time coordinates of this dataset.
	 * 
	 * @return all time	coordinates
	 */
	public Set<Integer> getTimesBag ();

	
}
