package algcore;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import alginterface.TriGenGuiTask;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import algutils.AlgorithmRandomUtilities;
import utils.TextUtilities;

/**
 * <b>TriGen</b> algorithm class. It provides methods and tools to make experiments.
 * 
 * @author David Gutiérrez-Avilés
 */
public class TriGen {
	
	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(TriGen.class);

	static private TriGen singleton = new TriGen();
	private TriGen() {}
	public static TriGen getInstance() {return singleton;}
	
	
	/**
	 * Individual class name field.
	 */
	private String individualClassName;

	/**
	 * Initial population operator field.
	 */
	private InitialPop initialPop;
	
	/**
	 * Fitness function operator field.
	 */
	private FitnessFunction fitnessFunction;
	
	/**
	 * Selection operator field.
	 */
	private Selection selection;
	
	/**
	 * Crossover operator field.
	 */
	private Crossover crossover;
		
	/**
	 * Mutation operator field.
	 */
	private Mutation mutation;
	
	/**
	 * Solution criterion field.
	 */
	private SolutionCriterion solutionCriterion;
	
	/**
	 * Stopping criterion field.
	 */
	private StoppingCriterion stoppingCriterion;
	
	/**
	 * Solutions field.
	 */
	private List<AlgorithmIndividual> solutions;

	/**
	 * Ongoing solution index field.
	 */
	private int ongoingSolutionIndex;
	
	/**
	 * Ongoing generation index field.
	 */
	private int ongoingGenerationIndex;
	
	/**
	 * Ongoing evaluated individual field.
	 */
	private int ongoingEvaluatedIndividualIndex;
	
//	private String populationReport;
	
	private TriGenGuiTask task;
	
	private boolean canceled;
	
	//c : console, i: gui
//	private char type;
//	
	

	/**
	 * Sets the {@link TriGen} algorithm state.
	 * 
	 * @param indClassName the individual class name
	 * @param initPop the initial population operator
	 * @param fitness the fitness function operator
	 * @param selection the selection operator
	 * @param crossover the crossover operator
	 * @param mutation the mutation operator
	 * @param solCriterion the solution criterion
	 * @param stopCriterion the stopping criterion
	 */
	public void set(String indClassName, InitialPop initPop, FitnessFunction fitness,
			Selection selection, Crossover crossover, Mutation mutation, SolutionCriterion solCriterion, StoppingCriterion stopCriterion) {

		this.individualClassName = indClassName;

		this.initialPop = initPop;
		this.fitnessFunction = fitness;
		this.selection = selection;
		this.crossover = crossover;
		this.mutation = mutation;
		this.solutionCriterion = solCriterion;
		
		this.stoppingCriterion = stopCriterion;

		this.solutions = new LinkedList<AlgorithmIndividual>();

		this.ongoingSolutionIndex = 0;
		
		this.ongoingGenerationIndex = 0;
		
//		this.populationReport = "";
		
	}

	/**
	 * Runs <b>TriGen</b> algorithm previously settled.
	 * 
	 * @return the algorithm's run solutions as a {@link List} of {@link AlgorithmIndividual}.
	 */

	public List<AlgorithmIndividual> runAlgorithm() {
		
		AlgorithmConfiguration PARAM = AlgorithmConfiguration.getInstance();
		DataHierarchy JER = PARAM.getDataHierarchy(); 
				
		canceled = false;	
		double rop = 0;
		double top = PARAM.getN()*(4+(4*PARAM.getG()));
		double com = 0;
		checkTaskState(com);
		String scom = "";
		Format format = TextUtilities.getDecimalFormat('.', "0.0");
		
		List<AlgorithmIndividual> population = null;		
		List<AlgorithmIndividual> parents = null;
		List<AlgorithmIndividual> children = null;
		List<AlgorithmIndividual> mutatedChildren = null;
		
		ongoingSolutionIndex = 0;
		
		while (stoppingCriterion.checkCriterion() && !canceled) {	
			
			com = (rop/top)*100;			
			checkTaskState(com);			
			scom = format.format(com);			
			System.out.println("\n["+(ongoingSolutionIndex + 1)+"] "+scom+"% "+JER.getPercentage());
						
			ongoingGenerationIndex = 0;
			
			if (!canceled){
			System.out.println("--initial population");		
			population = produceInitialPopulation();
			}
			
			rop++;
			
			while (ongoingGenerationIndex < PARAM.getG() && !canceled) {
				
				com = (rop/top)*100;				
				checkTaskState(com);
				scom = format.format(com);
				System.out.print("["+(ongoingSolutionIndex+1)+","+(ongoingGenerationIndex+1)+"] "+scom+"% "+JER.getPercentage());
				
				if (!canceled){
				System.out.print("--evaluation");
				evaluate(population);
				}
				
				rop++;
				
				if (!canceled){
				System.out.print("--selection");
				parents = select(population);
				}			
				
				rop++;
				
				if (!canceled){
				System.out.print("--crossover");							
				children = crossover(population,parents);
				}
				
				rop++;
				
				if (!canceled){
				System.out.print("--mutation\n");
				mutatedChildren = mutate(children);
				}
				
				rop++;
								
				if (!canceled){
				population.clear();	
				population.addAll(parents);
				population.addAll(mutatedChildren);	
				}
							
												
				ongoingGenerationIndex++;
						
			}
			
			com = (rop/top)*100;			
			checkTaskState(com);
			scom = format.format(com);
			
			if (!canceled){
			System.out.println("["+(ongoingSolutionIndex + 1)+"] "+scom+"%: results evaluation");
			evaluate(population);
			}
			
			rop++;
						
			com = (rop/top)*100;			
			checkTaskState(com);
			scom = format.format(com);
			
			AlgorithmIndividual best = null;
			
			if (!canceled){
			System.out.println("["+(ongoingSolutionIndex + 1)+"] "+scom+"%: solution criterion");
			best = chooseTheBest(population);
			}
			
			rop++;
			
			if (!canceled) solutions.add(best);

			com = (rop/top)*100;			
			checkTaskState(com);			
			scom = format.format(com);
			
			if (!canceled){
			System.out.println("["+(ongoingSolutionIndex + 1)+"] "+scom+"%: update data hierarchy");
			JER.updateDataHierarchy(best);
			}
			
			rop++;
						
			ongoingSolutionIndex++;
		}
		
		com = 100.0;
		checkTaskState(com);
		
		if (!canceled){
			System.out.println(com+"%: completed");
		} else {
			System.out.println("\ncancelled");
		}
		
		return solutions;
	}

	
	/**
	 * Initial population method.
	 * 
	 * @return the initial population
	 */
	private List<AlgorithmIndividual> produceInitialPopulation() {

		return initialPop.produceInitialPop();

	}

	/**
	 * Fitness function method.
	 * 
	 * @param population the eveluated population
	 */
	private void evaluate(List<AlgorithmIndividual> population) {

		DecimalFormat f = TextUtilities.getDecimalFormat('.', "0.000");
		
		AlgorithmConfiguration c = AlgorithmConfiguration.getInstance();

		if (c.isConcurrency() == false) {

			ongoingEvaluatedIndividualIndex = 1;
					
			for (AlgorithmIndividual ind: population){
				
//				LOG.debug("\n"+ind.toString());
				
				if (ind.canSkipEvaluation()==true){
					
//					LOG.debug("skiped");
					
				}
				else{
					
//					double ffbefore = ind.getFitnessFunctionValue();
//					
//					boolean canSkip = ind.chekCanSkipEvaluation();
					
					//LOG.debug("\n BEFORE = "+ind.toString()+" --> can skip? = "+canSkip);
									
					
					fitnessFunction.evaluate(ind);
					ind.addEntry("evaluated FF = "+f.format(ind.getFitnessFunctionValue())+" ["+(ongoingGenerationIndex+1)+"]");
					ind.setEvaluated(true);
					ind.setAltered(false);
					
//					double ffafter = ind.getFitnessFunctionValue();
//					
//					boolean q1 = (ffbefore==ffafter);
//					
//					LOG.debug("\n BEFORE=AFTER? = "+q1+" ## can skip? = "+canSkip+" ## R = "+(canSkip==q1));
//					
//					if (canSkip!=q1) LOG.debug("\n\nERROR\n\n");
				
				}
				
				ongoingEvaluatedIndividualIndex++;			
			}

		}

		else {
			
			ExecutorService pool = Executors.newFixedThreadPool(c.getThreads());
		
			Collection<ConcurrentEvaluator> concurrentTask = new LinkedList<ConcurrentEvaluator>();

			int i = 0;
			
			for (AlgorithmIndividual ind: population){
				
				if (ind.canSkipEvaluation()==true){
				
					
				}
				else{
					concurrentTask.add(new ConcurrentEvaluator(fitnessFunction,ind, i));
					ind.addEntry("evaluated ["+(ongoingGenerationIndex+1)+"]");
					ind.setEvaluated(true);
					ind.setAltered(false);
					i++;
				}
			
			}
		
			try {
			
				pool.invokeAll(concurrentTask);

			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}

			pool.shutdown();

		}

	}

	/**
	 * Selection method.
	 * 
	 * @param population the population
	 * @return the selected population
	 */
	private List<AlgorithmIndividual> select(List<AlgorithmIndividual> population) {
		
		AlgorithmConfiguration config = AlgorithmConfiguration.getInstance();
		
		int numberOfSelections = (int) (config.getSel() * config.getI());
		
		return selection.select(numberOfSelections,population);
		
	}

	/**
	 * Crossover method.
	 * 
	 * @param basePopulation the population
	 * @param selectedPopulation the selected population
	 * @return the children
	 */
	private List<AlgorithmIndividual> crossover(List<AlgorithmIndividual> basePopulation,List<AlgorithmIndividual> selectedPopulation) {
		
		AlgorithmConfiguration config = AlgorithmConfiguration.getInstance();
		
		int numberOfChildren  = config.getI() - selectedPopulation.size();
				
		return crossover.cross(numberOfChildren, selectedPopulation);
	
	}

	/**
	 * Mutation method.
	 * 
	 * @param population the population
	 * @return the mutated population
	 */
	private List<AlgorithmIndividual> mutate(List<AlgorithmIndividual> population) {
		
		AlgorithmRandomUtilities randomSupport = AlgorithmRandomUtilities.getInstance();
		
		AlgorithmConfiguration config = AlgorithmConfiguration.getInstance();
		
		for (AlgorithmIndividual ind:population){
						
			double porcentaje = randomSupport.getPercentage();
			
			boolean mutateIndividual = porcentaje < config.getMut();
			
			if (mutateIndividual){
				
				mutation.mutate(ind);
				
				ind.setAltered(true);
				
				ind.addEntry("mutated ["+(ongoingGenerationIndex+1)+"]");
				
			}
			
		}
		
		return population;
	
	}

	/**
	 * Method to choose the best individual.
	 * 
	 * @param population the population
	 * @return the best individual
	 */
	private AlgorithmIndividual chooseTheBest(List<AlgorithmIndividual> population) {
		return solutionCriterion.chooseTheBest(population);
	}
	
	/**
	 * Gets the solutions of an algorithm run.
	 * 
	 * @return the solutions
	 */
	public List<AlgorithmIndividual> getSolutions() {
		return solutions;
	}

	/**
	 * Gets the individual class name.
	 * 
	 * @return the individual class name
	 */
	public String getIndividualClassName() {
		return individualClassName;
	}

	/**
	 * Gets the ongoing solution index.
	 * 
	 * @return the ongoing solution index
	 */
	public int getOngoingSolutionIndex() {
		return ongoingSolutionIndex;
	}

	/**
	 * Gets the ongoing generation index.
	 * 
	 * @return the ongoing generation index
	 */
	public int getOngoingGenerationIndex() {
		return ongoingGenerationIndex;
	}

	/**
	 * Gets the ongoing evaluated individual index.
	 * 
	 * @return the ongoing evaluated individual index
	 */
	public int getOngoingEvaluatedIndividualIndex() {
		return ongoingEvaluatedIndividualIndex;
	}

	/////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////OTHER METHODS//////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////
	
	
	public void setTask(TriGenGuiTask task) {
		this.task = task;
	}
	
	public boolean isCancelled (){
		return canceled;
	}
	
	public void cancel (){
		canceled = true;
	}
	
	private void checkTaskState (double v){
		
		if (task!=null){
			
			if (v>100.0) v = 100.0;
			
			task.setProgress(v);
			
			if (task.isCancelled()){
				
//				(Thread.currentThread()).stop();
//				(Thread.currentThread()).interrupt();
				canceled = true;
			}
			
		}
			
	}

	
	
	
	

}