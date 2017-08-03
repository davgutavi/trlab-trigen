package strinitialpop;

import initialpops.InitialPopStrategy;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algcore.AlgorithmDataset;
import algcore.DataHierarchy;

public class EarthQuakeStrategy implements InitialPopStrategy {

	public List<AlgorithmIndividual> generateIndividuals(int numberOfIndividuals, String individualClassName) {

		AlgorithmConfiguration PARAM = AlgorithmConfiguration.getInstance();
		DataHierarchy JER = PARAM.getDataHierarchy();
		AlgorithmDataset DATA = PARAM.getData();
				
		List<AlgorithmIndividual> l = new LinkedList<AlgorithmIndividual>();
		
		Random r = new Random();
		r.setSeed(new Date().getTime());
		
		

		for (int n = 0; n < numberOfIndividuals; n++) {
			
				List<Integer> lt = JER.getHierarchicalListOfTimes(DATA.getTimeSize());
				List<Integer> lg = null;
				List<Integer> lc = null;
				
				int[] ng = aleatorios();
						
				List<Integer>[] gc = JER.buildGenesAndSamples(ng[0],ng[1]);
				
				lg = gc[0];
				lc = gc[1];
				
				Collections.sort(lt);
				Collections.sort(lg);
				Collections.sort(lc);
					
				//System.out.println("lg = "+lg+" lc = "+lc);
					
				AlgorithmIndividual nuevo = null;
					
				try {
				 nuevo = (AlgorithmIndividual) (Class.forName(individualClassName)).newInstance();
				 //from nuevo.initialize(lt,lg,lc) to nuevo.initialize(lg,lc,lt);
				 nuevo.initialize(lg,lc,lt);
				 nuevo.addEntry("from initial population: earthquake");
				 l.add(nuevo);
				} catch (InstantiationException e) {
						e.printStackTrace();
				} catch (IllegalAccessException e) {
						e.printStackTrace();
				} catch (ClassNotFoundException e) {
						e.printStackTrace();
				}
		
		}// for

		return l;

	}// procedimiento

	
	
	private int[] aleatorios(){
		
		AlgorithmConfiguration PARAM = AlgorithmConfiguration.getInstance();
		DataHierarchy JER = PARAM.getDataHierarchy();
		
		int[] res = new int[2];
		
		Random r = new Random();
		r.setSeed(new Date().getTime());

		int maxGenes = PARAM.getMaxG();
		int genesJ = JER.getAvailableGenes();
		int maxG = 0;
		
		if (maxGenes<=genesJ){
			maxG = maxGenes; 
		}
		else{
			maxG = genesJ;
		}
		
		int maxCondiciones = PARAM.getMaxC();
		int condicionesJ = JER.getAvailableSamples();
		int maxC = 0;
		
		if (maxCondiciones<=condicionesJ){
			maxC = maxCondiciones; 
		}
		else{
			maxC = condicionesJ;
		}
		
		int ng = r.nextInt(maxG + 1);
		
		while (ng < PARAM.getMinG()) {
			ng = r.nextInt(maxG + 1);
		}

		int nc = r.nextInt(maxC + 1);
		
		while (nc < PARAM.getMinC()) {
			nc = r.nextInt(maxC + 1);
		}
		
		res[0] = ng;
		res[1] = nc;
		
		return res;
	}
	

}// clase
