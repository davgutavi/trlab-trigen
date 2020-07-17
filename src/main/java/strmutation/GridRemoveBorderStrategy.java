package strmutation;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algutils.AlgorithmRandomUtilities;
import mutations.MutationStrategy;

public class GridRemoveBorderStrategy implements MutationStrategy {

	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(GridRemoveBorderStrategy.class);

	public boolean alter(AlgorithmIndividual individual) {

		boolean res = false;
		
		int individualLeftX = individual.getSample(0);
		int individualRightX = individual.getSample(individual.getSampleSize()-1);
		
		int individualTopY = individual.getGene(0);
		int individualDownY = individual.getGene(individual.getGeneSize()-1);
		
//		LOG.debug("individualLeftX = "+individualLeftX+" , individualRightX = "+individualRightX+" , individualTopY = "+individualTopY+" , individualDownY = "+individualDownY);
		
		int xSize = individual.getSampleSize()-AlgorithmConfiguration.getInstance().getMinC();
		int ySize = individual.getGeneSize()-AlgorithmConfiguration.getInstance().getMinG();
		
//		LOG.debug("xSize = "+xSize+" , ySize = "+ySize);
		
		int leftBorderX = individualLeftX + xSize;
		int rightBorderX = individualRightX - xSize;
		int topBorderY = individualTopY + ySize;
		int downBorderY = individualDownY - ySize;
		
//		LOG.debug("leftBorderX = "+leftBorderX+" , rightBorderX = "+rightBorderX+" , topBorderY = "+topBorderY+" , downBorderY = "+downBorderY);
					
		
		boolean directions[] = {false,false,false,false};
		
		int maxRemovals = Integer.MAX_VALUE;
		
		if (xSize>0) {
			
			if(leftBorderX>individualLeftX) {
				directions[0]=true;
				if(xSize<maxRemovals)
					maxRemovals = xSize;
			}
			
			if(rightBorderX<individualRightX) {
				directions[1]=true;	
				if(xSize<maxRemovals)
					maxRemovals = xSize;
			}
			
		}
		
		if (ySize>0) {
			
			if(topBorderY>individualTopY) {
				directions[2]=true;
				if(ySize<maxRemovals)
					maxRemovals = ySize;
			}
			
			if(downBorderY<individualDownY) {
				directions[3]=true;
				if(ySize<maxRemovals)
					maxRemovals = ySize;
			}
			
			
		}
		
		
		
//		LOG.debug("directions = "+Arrays.toString(directions));
		
		
		if(directions[0]||directions[1]||directions[2]||directions[3]) {
			
						
			AlgorithmRandomUtilities.getInstance().newBag();			
						
			Collection<Integer> marbles = new ArrayList<Integer>(4);
			
			for (int i=0;i<4;i++) 				
				if(directions[i]) 
					marbles.add(new Integer(i));
					
			AlgorithmRandomUtilities.getInstance().putMarbles(marbles);
			
			int selectedDirection = AlgorithmRandomUtilities.getInstance().extractAmarble();	
			
			int removals = AlgorithmRandomUtilities.getInstance().getFromInterval(1, maxRemovals);
			
//			LOG.debug("selectedDirection = "+selectedDirection+" , removals = "+removals);
			
			if(removals>0) {
				res = true;	
				
				if(selectedDirection==0) {
					for (int i=0;i<removals;i++) {
						individual.deleteSample(individualLeftX);
						individualLeftX++;
					}
				}
				else if(selectedDirection==1) {
					for (int i=0;i<removals;i++) {
						individual.deleteSample(individualRightX);
						individualRightX--;
					}
				}
				else if(selectedDirection==2) {
					for (int i=0;i<removals;i++) {
						individual.deleteGene(individualTopY);
						individualTopY++;
					}
				}
				else if(selectedDirection==3) {
					for (int i=0;i<removals;i++) {
						individual.deleteGene(individualDownY);
						individualDownY--;
					}					
				}		
				
			}			
								
		}
	
		return res;

	}

}