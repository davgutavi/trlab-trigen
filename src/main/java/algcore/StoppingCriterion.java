package algcore;

/**
 * Stopping criterion interface. The {@link TriGen} algorithm uses this criterion in order to stop the evolutionary processes. 
 * 
 * @author David Gutiérrez-Avilés
 */
public interface StoppingCriterion {
	
	
	/**
	 * Checks the stopping criterion.
	 * 
	 * @return <code>true</code> if the criterion is satisfied, otherwise <code>false</code>
	 */
	public boolean checkCriterion ();

}
