package strcrossover;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algcore.TriGen;
import algutils.AlgorithmRandomUtilities;
import algutils.TriclusterUtilities;
import crossovers.CrossoverStrategy;

public class GridStrategy implements CrossoverStrategy {
	
	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(GridStrategy.class);

	public AlgorithmIndividual[] cross(AlgorithmIndividual father, AlgorithmIndividual mother, String individualClassName) {
		
		AlgorithmIndividual[] r = new AlgorithmIndividual[2];
		father.addEntry("father ["+((TriGen.getInstance()).getOngoingGenerationIndex()+1)+"]");		
		mother.addEntry("mother ["+((TriGen.getInstance()).getOngoingGenerationIndex()+1)+"]");
		
//		LOG.debug("");
//		LOG.debug(">>>> Cruce");
//		
//		LOG.debug("--> [F] X{"+father.getSampleSize()+"} = "+father.getSamples()+
//				" , Y{"+father.getGeneSize()+"} = "+father.getGenes());
//		LOG.debug("--> [M] X{"+mother.getSampleSize()+"} = "+mother.getSamples()+
//				" , Y{"+mother.getGeneSize()+"} = "+mother.getGenes());
//		
		
		Collection<Integer> child1_X = new LinkedList<Integer>();
		Collection<Integer> child2_X = new LinkedList<Integer>();
		Collection<Integer> child1_Y = new LinkedList<Integer>();
		Collection<Integer> child2_Y = new LinkedList<Integer>();
		
		Collection<Integer> [] child_times = TriclusterUtilities.getInstance().
				buildOnePointComponents(father.getTimes(),mother.getTimes(), 
						AlgorithmConfiguration.getInstance().getMinT(), 
						AlgorithmConfiguration.getInstance().getMaxT(), 
						AlgorithmConfiguration.getInstance().getData().getTimeSize());
		
		Collection<Integer> child1_T = child_times[0];
		Collection<Integer> child2_T = child_times[1];
		
		
		Collection<Integer> fX = father.getSamples();
		Set<Integer> fsX = new HashSet<Integer>(fX);
		
		Collection<Integer> mX = mother.getSamples();
		Set<Integer> msX = new HashSet<Integer>(mX);
		
		Set<Integer> intersectionX = new HashSet<Integer>(fsX);
		intersectionX.retainAll(msX);
				
		Collection<Integer> fY = father.getGenes();
		Set<Integer> fsY = new HashSet<Integer>(fY);
				
		Collection<Integer> mY = mother.getGenes();
		Set<Integer> msY = new HashSet<Integer>(mY);
		
		Set<Integer> intersectionY = new HashSet<Integer>(fsY);
		intersectionY.retainAll(msY);
		
		if(intersectionX.isEmpty()&&intersectionY.isEmpty()) {
			// Sin solapamiento
			
//			LOG.debug("Sin solapamiento");
			
			Set<Integer> auxBag = AlgorithmConfiguration.getInstance().getData().getSamplesBag();
			auxBag.removeAll(father.getSamples());
			auxBag.removeAll(mother.getSamples());
			int c1_center_X = 0;
			int c2_center_X = 0;
			if (auxBag.size()>1) {
				AlgorithmRandomUtilities.getInstance().newBag();
				AlgorithmRandomUtilities.getInstance().putMarbles(auxBag);
				c1_center_X = AlgorithmRandomUtilities.getInstance().extractAmarble();
				c2_center_X = AlgorithmRandomUtilities.getInstance().extractAmarble();	
			}
			else {
				
				c1_center_X = AlgorithmRandomUtilities.getInstance().
						getFromInterval(0, AlgorithmConfiguration.getInstance().getData().getSampleSize() - 1);
				
				c2_center_X = AlgorithmRandomUtilities.getInstance().
						getFromInterval(0, AlgorithmConfiguration.getInstance().getData().getSampleSize() - 1);
			}
				
			
			auxBag = AlgorithmConfiguration.getInstance().getData().getGenesBag();
			auxBag.removeAll(father.getGenes());
			auxBag.removeAll(mother.getGenes());
			int c1_center_Y = 0;
			int c2_center_Y = 0;
			if (auxBag.size()>1) {
				AlgorithmRandomUtilities.getInstance().newBag();
				AlgorithmRandomUtilities.getInstance().putMarbles(auxBag);
				c1_center_Y = AlgorithmRandomUtilities.getInstance().extractAmarble();
				c2_center_Y = AlgorithmRandomUtilities.getInstance().extractAmarble();	
			}
			else {
				
				c1_center_Y = AlgorithmRandomUtilities.getInstance().
						getFromInterval(0, AlgorithmConfiguration.getInstance().getData().getGenSize() - 1);
				
				c2_center_Y = AlgorithmRandomUtilities.getInstance().
						getFromInterval(0, AlgorithmConfiguration.getInstance().getData().getGenSize() - 1);
			}
			
			buid_coordinates(child1_X, child1_Y, child2_X, child2_Y, c1_center_X, c1_center_Y, c2_center_X, c2_center_Y);
			
		}
		else if (intersectionX.isEmpty()&&!intersectionY.isEmpty()) {
			
			// Solapamiento en Y
			
//			LOG.debug("Solapamiento en Y");
			
			int c1_center_Y = ((Integer) intersectionY.toArray()[0]).intValue();
			int c2_center_Y = c1_center_Y;
						
			if (intersectionY.size()>1) {
				AlgorithmRandomUtilities.getInstance().newBag();
				AlgorithmRandomUtilities.getInstance().putMarbles(intersectionY);
				c1_center_Y = AlgorithmRandomUtilities.getInstance().extractAmarble();
				c2_center_Y = AlgorithmRandomUtilities.getInstance().extractAmarble();
			}
			
			
			Set<Integer> auxBag = AlgorithmConfiguration.getInstance().getData().getSamplesBag();
			auxBag.removeAll(father.getSamples());
			auxBag.removeAll(mother.getSamples());
			int c1_center_X = 0;
			int c2_center_X = 0;
			if (auxBag.size()>1) {
				AlgorithmRandomUtilities.getInstance().newBag();
				AlgorithmRandomUtilities.getInstance().putMarbles(auxBag);
				c1_center_X = AlgorithmRandomUtilities.getInstance().extractAmarble();
				c2_center_X = AlgorithmRandomUtilities.getInstance().extractAmarble();	
			}
			else {
				
				c1_center_X = AlgorithmRandomUtilities.getInstance().
						getFromInterval(0, AlgorithmConfiguration.getInstance().getData().getSampleSize() - 1);
				
				c2_center_X = AlgorithmRandomUtilities.getInstance().
						getFromInterval(0, AlgorithmConfiguration.getInstance().getData().getSampleSize() - 1);
			}
			
			buid_coordinates(child1_X, child1_Y, child2_X, child2_Y, c1_center_X, c1_center_Y, c2_center_X, c2_center_Y);
			
			
		}
		else if (!intersectionX.isEmpty()&&intersectionY.isEmpty()) {
			
			// Solapamiento en X
			
//			LOG.debug("Solapamiento en X");
			
			int c1_center_X = ((Integer) intersectionX.toArray()[0]).intValue();
			int c2_center_X = c1_center_X;
						
			if (intersectionX.size()>1) {
			AlgorithmRandomUtilities.getInstance().newBag();
			AlgorithmRandomUtilities.getInstance().putMarbles(intersectionX);
			 c1_center_X = AlgorithmRandomUtilities.getInstance().extractAmarble();
			 c2_center_X = AlgorithmRandomUtilities.getInstance().extractAmarble();
			}
			
			
			Set<Integer> auxBag = AlgorithmConfiguration.getInstance().getData().getGenesBag();
			auxBag.removeAll(father.getGenes());
			auxBag.removeAll(mother.getGenes());
			int c1_center_Y = 0;
			int c2_center_Y = 0;
			if (auxBag.size()>1) {
				AlgorithmRandomUtilities.getInstance().newBag();
				AlgorithmRandomUtilities.getInstance().putMarbles(auxBag);
				c1_center_Y = AlgorithmRandomUtilities.getInstance().extractAmarble();
				c2_center_Y = AlgorithmRandomUtilities.getInstance().extractAmarble();	
			}
			else {
				
				c1_center_Y = AlgorithmRandomUtilities.getInstance().
						getFromInterval(0, AlgorithmConfiguration.getInstance().getData().getGenSize() - 1);
				
				c2_center_Y = AlgorithmRandomUtilities.getInstance().
						getFromInterval(0, AlgorithmConfiguration.getInstance().getData().getGenSize() - 1);
			}
			
			buid_coordinates(child1_X, child1_Y, child2_X, child2_Y, c1_center_X, c1_center_Y, c2_center_X, c2_center_Y);			
			
			
		}
		else {
			// Solapamiento en las dos dimensiones	
			
//			LOG.debug("Solapamiento en las dos dimensiones");
			
			int c1_center_X = ((Integer) intersectionX.toArray()[0]).intValue();
			int c2_center_X = c1_center_X;
						
			if (intersectionX.size()>1) {
				AlgorithmRandomUtilities.getInstance().newBag();
				AlgorithmRandomUtilities.getInstance().putMarbles(intersectionX);
				c1_center_X = AlgorithmRandomUtilities.getInstance().extractAmarble();
				c2_center_X = AlgorithmRandomUtilities.getInstance().extractAmarble();
			}
			
			int c1_center_Y = ((Integer) intersectionY.toArray()[0]).intValue();
			int c2_center_Y = c1_center_Y;
						
			if (intersectionY.size()>1) {
				AlgorithmRandomUtilities.getInstance().newBag();
				AlgorithmRandomUtilities.getInstance().putMarbles(intersectionY);
				c1_center_Y = AlgorithmRandomUtilities.getInstance().extractAmarble();
				c2_center_Y = AlgorithmRandomUtilities.getInstance().extractAmarble();
			}
		
			buid_coordinates(child1_X, child1_Y, child2_X, child2_Y, c1_center_X, c1_center_Y, c2_center_X, c2_center_Y);				
			
		}
		
		r[0] = TriclusterUtilities.getInstance().buildIndividual(child1_Y,child1_X,child1_T,individualClassName,
				"from crossover ["+((TriGen.getInstance()).getOngoingGenerationIndex()+1)+"]");
		
		
		r[1] = TriclusterUtilities.getInstance().buildIndividual(child2_Y,child2_X,child2_T,individualClassName,
				"from crossover ["+((TriGen.getInstance()).getOngoingGenerationIndex()+1)+"]");
			
	
//		LOG.debug("--> [C1] X{"+r[0].getSampleSize()+"} = "+r[0].getSamples()+
//				" , Y{"+r[0].getGeneSize()+"} = "+r[0].getGenes());
//		LOG.debug("--> [C2] X{"+r[1].getSampleSize()+"} = "+r[1].getSamples()+
//				" , Y{"+r[1].getGeneSize()+"} = "+r[1].getGenes());

				
		return r;

	}
	
	
	private void buid_coordinates(Collection<Integer> child1_X, Collection<Integer> child1_Y, 
			Collection<Integer> child2_X, Collection<Integer> child2_Y,
			int c1_center_X,int c1_center_Y,
			int c2_center_X,int c2_center_Y) {
		
		
		int c1_tamX = AlgorithmRandomUtilities.getInstance().
				getFromInterval(AlgorithmConfiguration.getInstance().getMinC(), 
						AlgorithmConfiguration.getInstance().getMaxC());
		
		int c1_tamY = AlgorithmRandomUtilities.getInstance().
				getFromInterval(AlgorithmConfiguration.getInstance().getMinG(), 
						AlgorithmConfiguration.getInstance().getMaxG());
				
		
		int [] child1_borders = TriclusterUtilities.getInstance().
				getTensor2DLimits(c1_center_Y,c1_center_X,c1_tamY,c1_tamX,
						AlgorithmConfiguration.getInstance().getData().getGenSize(),
						AlgorithmConfiguration.getInstance().getData().getSampleSize());
		
		int c2_tamX = AlgorithmRandomUtilities.getInstance().
				getFromInterval(AlgorithmConfiguration.getInstance().getMinC(), 
						AlgorithmConfiguration.getInstance().getMaxC());
		
		int c2_tamY = AlgorithmRandomUtilities.getInstance().
				getFromInterval(AlgorithmConfiguration.getInstance().getMinG(), 
						AlgorithmConfiguration.getInstance().getMaxG());
				
		
		int [] child2_borders = TriclusterUtilities.getInstance().
				getTensor2DLimits(c2_center_Y,c2_center_X,c2_tamY,c2_tamX,
						AlgorithmConfiguration.getInstance().getData().getGenSize(),
						AlgorithmConfiguration.getInstance().getData().getSampleSize());
	
		for (int y = child1_borders[0]; y <= child1_borders[1]; y++)
			child1_Y.add(new Integer(y));
		
		for (int x = child1_borders[2]; x <= child1_borders[3]; x++)
			child1_X.add(new Integer(x));
		
		for (int y = child2_borders[0]; y <= child2_borders[1]; y++)
			child2_Y.add(new Integer(y));
		
		for (int x = child2_borders[2]; x <= child2_borders[3]; x++)
			child2_X.add(new Integer(x));		
		
	}

}