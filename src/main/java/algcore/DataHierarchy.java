package algcore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


/**
 * Data hierarchy interface. It provides methods to consult system to avoid overlapping. 
 * 
 * @author David Gutiérrez-Avilés
 */
public interface DataHierarchy {
	
	public ArrayList<ArrayList<Collection<Integer>>> build_gst_coorinates (int n);
	
	public ArrayList<ArrayList<Collection<Integer>>> build_gs_coorinates (int n);
	
	public ArrayList<Collection<Integer>> build_i_coorinates (int n, char type);
	
	/**
	 * Initializes the initial data hierarchy state. 
	 * 
	 * @param geneSize the number of genes
	 * @param sampleSize the number of samples or conditions
	 * @param timeSize the number of times
	 */
	public void initialize (int geneSize, int sampleSize, int timeSize);
	
		/**
	 * Updates this data hierarchy with a new discovered solution.
	 * 
	 * @param solution the solution to update
	 */
	public void update (AlgorithmIndividual solution);
	
	/**
	 * Gets if data hierarchy is available, that is, if there are coordinates to visit.
	 * 
	 * @return <code>true</code> if there are coordinates to visit otherwise <code>false</code>
	 */
	public boolean isAvailable ();
	
	/**
	 * Gets the percentage of visited coordinates.
	 * 
	 * @return the percentage of visited coordinates
	 */
	public String getPercentage ();
	
	// Legacy
	public Map<Integer, Integer> getGenHierarchy();
	public Map<Integer, Integer> getSampleHierarchy();
	public Map<Integer, Integer> getTimeHierarchy();

	
}