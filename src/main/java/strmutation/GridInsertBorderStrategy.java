package strmutation;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algutils.AlgorithmRandomUtilities;
import mutations.MutationStrategy;

public class GridInsertBorderStrategy implements MutationStrategy {

	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(GridInsertBorderStrategy.class);

	public boolean alter(AlgorithmIndividual individual) {

		boolean res = false;
		
		int individualLeftX = individual.getSample(0);
		int individualRightX = individual.getSample(individual.getSampleSize()-1);
		
		int individualTopY = individual.getGene(0);
		int individualDownY = individual.getGene(individual.getGeneSize()-1);
		
//		LOG.debug("individualLeftX = "+individualLeftX+" , individualRightX = "+individualRightX+" , individualTopY = "+individualTopY+" , individualDownY = "+individualDownY);
		
		int xSize = AlgorithmConfiguration.getInstance().getMaxC()-individual.getSampleSize();
		int ySize = AlgorithmConfiguration.getInstance().getMaxG()-individual.getGeneSize();
		
//		LOG.debug("xSize = "+xSize+" , ySize = "+ySize);
		
		int leftBorderX = individualLeftX - xSize;
		int rightBorderX = individualRightX + xSize;
		int topBorderY = individualTopY - ySize;
		int downBorderY = individualDownY + ySize;
		
//		LOG.debug("leftBorderX = "+leftBorderX+" , rightBorderX = "+rightBorderX+" , topBorderY = "+topBorderY+" , downBorderY = "+downBorderY);
					
		
		boolean directions[] = {false,false,false,false};
		
		int maxInserts = Integer.MAX_VALUE;
		
		if (xSize>0) {
			
			if(leftBorderX>0) {
				directions[0]=true;
				if(xSize<maxInserts)
					maxInserts = xSize;
			}
			
			if(rightBorderX<(AlgorithmConfiguration.getInstance().getData().getSampleSize()-1)) {
				directions[1]=true;	
				if(xSize<maxInserts)
					maxInserts = xSize;
			}
			
		}
		
		if (ySize>0) {
			
			if(topBorderY>0) {
				directions[2]=true;
				if(ySize<maxInserts)
					maxInserts = ySize;
			}
			
			if(downBorderY<(AlgorithmConfiguration.getInstance().getData().getGenSize()-1)) {
				directions[3]=true;
				if(ySize<maxInserts)
					maxInserts = ySize;
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
			
			int insert = AlgorithmRandomUtilities.getInstance().getFromInterval(1, maxInserts);
			
//			LOG.debug("selectedDirection = "+selectedDirection+" , insert = "+insert);
			
			if(insert>0) {
				res = true;	
				
				if(selectedDirection==0) {
					for (int i=0;i<insert;i++) {
						individual.putSample(individualLeftX-1);
						individualLeftX--;
					}
				}
				else if(selectedDirection==1) {
					for (int i=0;i<insert;i++) {
						individual.putSample(individualRightX+1);
						individualRightX++;
					}
				}
				else if(selectedDirection==2) {
					for (int i=0;i<insert;i++) {
						individual.putGene(individualTopY-1);
						individualTopY--;
					}
				}
				else if(selectedDirection==3) {
					for (int i=0;i<insert;i++) {
						individual.putGene(individualDownY+1);
						individualDownY++;
					}					
				}		
				
			}			
								
		}
	
		return res;

	}

}