package strmutation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algutils.AlgorithmRandomUtilities;
import mutations.MutationStrategy;

public class GridInsertPerimeterStrategy implements MutationStrategy {

	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(GridInsertPerimeterStrategy.class);

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
			
			
			int loops = 0;
			int chunks = 0;
			int insert = 0;
			
			if(xSize==ySize&&directions[0]&&directions[1]&&directions[2]&&directions[3]) {
				
				loops = xSize/2;
				chunks = xSize%2;
				
				if (loops==0) {
					insert = AlgorithmRandomUtilities.getInstance().getFromInterval(1, maxInserts);
				}
				else {
					
					int loopInsertion = AlgorithmRandomUtilities.getInstance().getFromInterval(1, loops);
					int chunkInsertion = 0;
					
					if (chunks!=0)
						chunkInsertion = AlgorithmRandomUtilities.getInstance().getFromInterval(1, chunks);
					
					insert = (loopInsertion*4)+chunkInsertion;
//					LOG.debug(loops+ " loops , "+chunks+" chunks");
					
				}	
				
				
					
			}
			else {
				insert = AlgorithmRandomUtilities.getInstance().getFromInterval(1, maxInserts);
			}
			
			if(insert>0)
				res = true;
			
			
//			LOG.debug("insert = "+insert);
			
			while(insert>0) {
				
				if (directions[0]&&insert>0) {
					individual.putSample(individualLeftX-1);
					individualLeftX--;
					insert--;
				}				
				if (directions[1]&&insert>0) {
					individual.putSample(individualRightX+1);
					individualRightX++;
					insert--;
				}				
				if (directions[2]&&insert>0) {
					individual.putGene(individualTopY-1);
					individualTopY--;
					insert--;
				}
				if (directions[3]&&insert>0) {
					individual.putGene(individualDownY+1);
					individualDownY++;
					insert--;
				}
							
				
			}
					
		}
	
		return res;

	}

}