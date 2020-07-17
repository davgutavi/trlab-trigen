package strmutation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algutils.AlgorithmRandomUtilities;
import mutations.MutationStrategy;

public class GridRemovePerimeterStrategy implements MutationStrategy {

	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(GridRemovePerimeterStrategy.class);

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
			
		
			
			int loops = 0;
			int chunks = 0;
			int removals = 0;
			
			if(xSize==ySize&&directions[0]&&directions[1]&&directions[2]&&directions[3]) {
				
				loops = xSize/2;
				chunks = xSize%2;
				
				if (loops==0) {
					removals = AlgorithmRandomUtilities.getInstance().getFromInterval(1, maxRemovals);
				}
				else {
					
					int loopInsertion = AlgorithmRandomUtilities.getInstance().getFromInterval(1, loops);
					int chunkInsertion = 0;
					
					if (chunks!=0)
						chunkInsertion = AlgorithmRandomUtilities.getInstance().getFromInterval(1, chunks);
					
					removals = (loopInsertion*4)+chunkInsertion;
//					LOG.debug(loops+ " loops , "+chunks+" chunks");
					
				}	
				
				
					
			}
			else {
				removals = AlgorithmRandomUtilities.getInstance().getFromInterval(1, maxRemovals);
			}
			
			if(removals>0)
				res = true;
			
			
//			LOG.debug("removals = "+removals);
						
			while(removals>0) {
				
				if (directions[0]&&removals>0) {
					individual.deleteSample(individualLeftX);
					individualLeftX++;
					removals--;
				}				
				if (directions[1]&&removals>0) {
					individual.deleteSample(individualRightX);
					individualRightX--;
					removals--;
				}				
				if (directions[2]&&removals>0) {
					individual.deleteGene(individualTopY);
					individualTopY++;
					removals--;
				}
				if (directions[3]&&removals>0) {
					individual.deleteGene(individualDownY);
					individualDownY--;
					removals--;
				}
							
				
			}
					
		}
	
		return res;

	}
	
}

