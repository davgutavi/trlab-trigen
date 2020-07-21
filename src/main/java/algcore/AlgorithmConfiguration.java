package algcore;

/**
 *  
 * Configuration for <b>TriGen</b> algorithm.
 *  
 * Singleton class that provides all required parameters for a properly <b>TriGen</b>'s run.
 * 
 * 
 * 
 * @author David Gutiérrez-Avilés
 *
 */

public class AlgorithmConfiguration {

	/**
	 * singleton field.
	 */
	static private AlgorithmConfiguration singleton = new AlgorithmConfiguration();

	/**
	 * concurrency flag field.
	 */
	private boolean concurrency = false;

	/**
	 * number of threads field.
	 */
	private int threads;
		
	/**
	 * report string field.
	 */
	private String reportString;

	/**
	 * dataset field.
	 */
	private AlgorithmDataset data;

	/**
	 * data hierarchy field.
	 */
	private DataHierarchy dataHierarchy;

	/**
	 * number of solutions field.
	 */
	private int n;

	/**
	 * number of generations field.
	 */
	private int g;

	/**
	 * number of individuals field.
	 */
	private int i;

	/**
	 * randomness rate field.
	 */
	private float ale;

	/**
	 * selection rate field.
	 */
	private float sel;

	/**
	 * mutation probability field.
	 */
	private float mut;

	/**
	 * fitness weight field.
	 */
	private double wf;

	/**
	 * gene size weight field.
	 */
	private float wg;

	/**
	 * conditions or samples size weight field.
	 */
	private float wc;

	/**
	 * times size weight field.
	 */
	private float wt;

	/**
	 * gene overlapping weight field.
	 */
	private float wog;

	/**
	 * samples or conditions overlapping weight field.
	 */
	private float woc;

	/**
	 * times overlapping weight field.
	 */
	private float wot;

	/**
	 * minimum number of genes field. 
	 */
	private int minG;

	/**
	 * minimum number of conditions or samples field. 
	 */
	private int minC;

	/**
	 * minimum number of times field.
	 */
	private int minT;
		
	/**
	 * maximum number of genes field.
	 */
	private int maxG;

	/**
	 * maximum number of conditions or samples field.
	 */
	private int maxC;

	/**
	 * maximum number of times field.
	 */
	private int maxT;
	
	/**
	 * Empty constructor for singleton.
	 */
	private AlgorithmConfiguration() {
	}

	/**
	 * Provides an unique object of this class according to Singleton design pattern.
	 * 
	 * @return the singleton object of this class
	 */
	public static AlgorithmConfiguration getInstance() {
		return singleton;
	}

	/**
	 * Checks the concurrence mode of <b>TriGen</b> algorithm settled by user.
	 * 
	 * @return <code>true</code> is user claims concurrence mode otherwise <code>false</code>
	 */
	public boolean isConcurrency() {
		return concurrency;
	}

	/**
	 * Gets the number of threads of <b>TriGen</b> algorithm settled by user.  
	 * 
	 * @return the number of threads for concurrence execution
	 */
	public int getThreads() {
		return threads;
	}

	/**
	 * Gets a string with a report of all fields in order to check the state of this class.
	 * 
	 * @return report with all field values and algorithm configuration
	 */
	public String getReportString() {
		return reportString;
	}

	/**
	 * Gets the input {@link AlgorithmDataset} object settled by user in order to perform an experiment. 
	 * 
	 * @return the input dataset for current run
	 * @see AlgorithmDataset
	 */
	public AlgorithmDataset getData() {
		return data;
	}

	/**
	 * Gets the {@link DataHierarchy} object built for current <b>TriGen</b>'s run.
	 * 
	 * @return the data hierarchy for current run
	 * @see DataHierarchy
	 */
	public DataHierarchy getDataHierarchy (){
		return dataHierarchy;
	}

	/**
	 * Gets the number of solutions settled by user in order to perform an experiment.
	 * 
	 * <b>N</b> control parameter of <b>TriGen</b> algorithm.
	 * 
	 * @return the number of solutions
	 */
	public int getN() {
		return n;
	}

	/**
	 * Gets the number of generations settled by user in order to perform an experiment.
	 * 
	 * <b>G</b> control parameter of <b>TriGen</b> algorithm.
	 * 
	 * @return the number of generations
	 */
	public int getG() {
		return g;
	}
	
	/**
	 * Gets the number of individuals settled by user in order to perform an experiment.
	 * 
	 * <b>I</b> control parameter of <b>TriGen</b> algorithm.
	 * 
	 * @return the number of individuals
	 */
	public int getI() {
		return i;
	}

	/**
	 * Gets the randomness ratio settled by user in order to perform an experiment.
	 * 
	 * <b>Ale</b> control parameter of <b>TriGen</b> algorithm.
	 * 
	 * @return the randomness ratio
	 */
	public float getAle() {
		return ale;
	}

	/**
	 * Gets the selection ratio settled by user in order to perform an experiment.
	 * 
	 * <b>Sel</b> control parameter of <b>TriGen</b> algorithm.
	 * 
	 * @return the selection ratio
	 */
	public float getSel() {
		return sel;
	}

	/**
	 * Gets the mutation probability settled by user in order to perform an experiment.
	 * 
	 * <b>Mut</b> control parameter of <b>TriGen</b> algorithm.
	 * 
	 * @return the mutation probability
	 */
	public float getMut() {
		return mut;
	}

	/**
	 * Gets the fitness weight settled by user in order to perform an experiment.
	 * 
	 * <b>Wf</b> control parameter of <b>TriGen</b> algorithm.
	 * 
	 * @return the fitness weight
	 */
	public double getWf(){
		return wf;
	}
	
	/**
	 * Gets the genes size weight settled by user in order to perform an experiment.
	 * 
	 * <b>Wg</b> control parameter of <b>TriGen</b> algorithm.
	 * 
	 * @return the genes size weight
	 */
	public float getWg() {
		return wg;
	}

	/**
	 * Gets the conditions size weight settled by user in order to perform an experiment.
	 * 
	 * <b>Wc</b> control parameter of <b>TriGen</b> algorithm.
	 * 
	 * @return the conditions size weight
	 */
	public float getWc() {
		return wc;
	}

	/**
	 * Gets the times size weight settled by user in order to perform an experiment.
	 * 
	 * <b>Wt</b> control parameter of <b>TriGen</b> algorithm.
	 * 
	 * @return the times size weight
	 */
	public float getWt() {
		return wt;
	}

	/**
	 * Gets the genes overlapping weight settled by user in order to perform an experiment.
	 * 
	 * <b>WOg</b> control parameter of <b>TriGen</b> algorithm.
	 * 
	 * @return the genes overlapping weight
	 */
	public float getWog() {
		return wog;
	}

	/**
	 * Gets the conditions overlapping weight settled by user in order to perform an experiment.
	 * 
	 * <b>WOc</b> control parameter of <b>TriGen</b> algorithm.
	 * 
	 * @return the conditions overlapping weight
	 */
	public float getWoc() {
		return woc;
	}

	/**
	 * Gets the times overlapping weight settled by user in order to perform an experiment.
	 * 
	 * <b>WOt</b> control parameter of <b>TriGen</b> algorithm.
	 * 
	 * @return the times overlapping weight
	 */
	public float getWot() {
		return wot;
	}

	/**
	 * Gets the minimum genes size available for an {@link AlgorithmIndividual} and {@link AlgorithmDataset}. 
	 * 
	 * @return the minimum genes size
	 */
	public int getMinG() {
		return minG;
	}

	/**
	 * Gets the minimum conditions size available for an {@link AlgorithmIndividual} and {@link AlgorithmDataset}. 
	 * 
	 * @return the minimum conditions size
	 */
	public int getMinC() {
		return minC;
	}

	/**
	 * Gets the minimum times size available for an {@link AlgorithmIndividual} and {@link AlgorithmDataset}. 
	 * 
	 * @return the minimum times size
	 */
	public int getMinT() {
		return minT;
	}

	/**
	 * Gets the maximum genes size available for an {@link AlgorithmIndividual} and {@link AlgorithmDataset}. 
	 * 
	 * @return the maximum genes size
	 */
	public int getMaxG() {
		return maxG;
	}

	/**
	 * Gets the maximum conditions size available for an {@link AlgorithmIndividual} and {@link AlgorithmDataset}. 
	 * 
	 * @return the maximum conditions size
	 */
	public int getMaxC() {
		return maxC;
	}

	/**
	 * Gets the maximum times size available for an {@link AlgorithmIndividual} and {@link AlgorithmDataset}. 
	 * 
	 * @return the maximum times size
	 */
	public int getMaxT() {
		return maxT;
	}

	/**
	 * Sets the concurrence mode of <b>TriGen</b> algorithm.
	 * 
	 * @param concurrency the new value
	 */
	public void setConcurrency(boolean concurrency) {
		this.concurrency = concurrency;
	}

	/**
	 * Sets the number of threads of <b>TriGen</b> algorithm. 
	 * 
	 * @param threads
	 */
	public void setThreads(int threads) {
		this.threads = threads;
	}

	/**
	 * Sets the string with a report of all fields.
	 * 
	 * @param reportString the new report string
	 */
	public void setReportString(String reportString) {
		this.reportString = reportString;
	}

	/**
	 * Sets the {@link AlgorithmDataset} object.
	 * 
	 * @param data the new dataset
	 * @see AlgorithmDataset
	 */
	public void setData (AlgorithmDataset data) {
		this.data = data;
	}

	/**
	 *  Sets the {@link DataHierarchy} object.
	 * 
	 * @param dataHierarchy the new data hierarchy
	 * @see DataHierachy
	 */
	public void setDataHierarchy (DataHierarchy dataHierarchy){
		this.dataHierarchy = dataHierarchy;		
	}
		
	/**
	 * Sets the number of solutions.
	 * 
	 * <b>N</b> control parameter of <b>TriGen</b> algorithm.
	 *
	 * @param n the new number of solutions value
	 */
	public void setN(int n) {
		this.n = n;
	}

	/**
	 * Sets the number of generations.
	 * 
	 * <b>G</b> control parameter of <b>TriGen</b> algorithm.
	 *
	 * @param g the new number of generations value
	 */
	public void setG (int g) {
		this.g = g;
	}

	/**
	 * Sets the number of individuals.
	 * 
	 * <b>I</b> control parameter of <b>TriGen</b> algorithm.
	 * 
	 * @param i the new number of individuals value
	 */
	public void setI(int i) {
		this.i = i;
	}

	/**
	 * Sets the randomness ratio.
	 * 
	 * <b>Ale</b> control parameter of <b>TriGen</b> algorithm.
	 * 
	 * @param ale the new randomness ratio value
	 */
	public void setAle(float ale) {
		this.ale = ale;
	}

	/**
	 * Sets the selection ratio.
	 * 
	 * <b>Sel</b> control parameter of <b>TriGen</b> algorithm.
	 * 
	 * @param sel the new selection ratio value
	 */
	public void setSel(float sel) {
		this.sel = sel;
	}

	/**
	 * Sets the mutation probability.
	 * 
	 * <b>Mut</b> control parameter of <b>TriGen</b> algorithm.
	 *  
	 * @param mut the new mutation probability value
	 */
	public void setMut(float mut) {
		this.mut = mut;
	}

	/**
	 * Sets the fitness weight.
	 * 
	 * <b>Wf</b> control parameter of <b>TriGen</b> algorithm.
	 * 
	 * @param wf the new fitness weight value
	 */
	public void setWf(double wf){
		this.wf = wf;
	}

	/**
	 * Sets the genes size weight.
	 * 
	 * <b>Wg</b> control parameter of <b>TriGen</b> algorithm.
	 * 
	 * @param wg the new genes size weight value
	 */
	public void setWg(float wg) {
		this.wg = wg;
	}

	/**
	 * Sets the conditions size weight.
	 * 
	 * <b>Wc</b> control parameter of <b>TriGen</b> algorithm.
	 * 
	 * @param wc the new conditions size weight value
	 */
	public void setWc(float wc) {
		this.wc = wc;
	}
	
	/**
	 * Sets the times size weight.
	 * 
	 * <b>Wt</b> control parameter of <b>TriGen</b> algorithm.
	 * 
	 * @param wt the new times size weight value
	 */
	public void setWt(float wt) {
		this.wt = wt;
	}

	/**
	 * Sets the genes overlapping weight.
	 * 
	 * <b>WOg</b> control parameter of <b>TriGen</b> algorithm.
	 * 
	 * @param wog the new genes overlapping weight value
	 */
	public void setWog(float wog) {
		this.wog = wog;
	}

	/**
	 * Sets the conditions overlapping weight.
	 * 
	 * <b>WOc</b> control parameter of <b>TriGen</b> algorithm.
	 * 
	 * @param woc the new conditions overlapping weight value
	 */
	public void setWoc(float woc) {
		this.woc = woc;
	}


	/**
	 * Sets the times overlapping weight.
	 * 
	 * <b>WOt</b> control parameter of <b>TriGen</b> algorithm.
	 * 
	 * @param wot the new times overlapping weight value
	 */
	public void setWot(float wot) {
		this.wot = wot;
	}
	
	/**
	 * Sets the minimum genes size available. 
	 * 
	 * @param minG the new minimum genes size. 
	 */
	public void setMinG(int minG) {
		this.minG = minG;
	}

	/**
	 * Sets the minimum conditions size available. 
	 * 
	 * @param minC the new minimum conditions size. 
	 */
	public void setMinC(int minC) {
		this.minC = minC;
	}
	
	/**
	 * Sets the minimum times size available. 
	 * 
	 * @param minT the new minimum times size. 
	 */
	public void setMinT(int minT) {
		this.minT = minT;
	}

	/**
	 * Sets the maximum genes size available. 
	 * 
	 * @param maxG the new maximum genes size. 
	 */
	public void setMaxG(int maxG) {
		this.maxG = maxG;
	}

	/**
	 * Sets the maximum conditions size available. 
	 * 
	 * @param maxC the new maximum conditions size. 
	 */
	public void setMaxC(int maxC) {
		this.maxC = maxC;
	}
	
	/**
	 * Sets the maximum times size available. 
	 * 
	 * @param maxT the new maximum times size. 
	 */
	public void setMaxT(int maxT) {
		this.maxT = maxT;
	}
	
}