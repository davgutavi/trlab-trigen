package alginput;

/**@
 * 
 * @author David Gutiérrez-Avilés
 *
 */
import java.util.HashSet;
import java.util.Set;

import algcore.AlgorithmDataset;

public class Microarray implements AlgorithmDataset {

	//private static final Logger LOG = LoggerFactory.getLogger(Microarray.class);
	
	//Rows = Genes, Columns = Conditions (Samples) , Depth = Times, ==> GST, Gene Sample Time form 
		
	private double[][][] rawData;
	
	
	public Microarray (double[][][] rawData){
		
		this.rawData = rawData;
		
	}
	
	public int getGenSize() {
		return rawData.length;
	}

	public int getSampleSize() {
		return rawData[0].length;
	}

	public int getTimeSize() {
		return rawData[0][0].length;
	}


	public double getValue(int g, int c,int t) {
		
		return rawData[g][c][t];
	
	}

	public String toString() {

		String aux4 = "";
		for (int g = 0; g < getGenSize(); g++) {
			for (int t = 0; t < getTimeSize(); t++) {
				for (int c = 0; c < getSampleSize(); c++) {
					boolean cond1 = c == getSampleSize() - 1;
					boolean cond2 = t == getTimeSize() - 1;
					String cad = "" + rawData[g][c][t];
					if (cond1 == false && cond2 == false) {
						cad += " , ";
					} else if (cond1 == true && cond2 == true) {
						cad += "\n";
					} else if (cond1 == true && cond2 == false) {
						cad += " || ";
					} else {
						cad += " , ";
					}
					aux4 += cad;
				}
			}
		}

		return aux4;
	}

	@Override
	public Set<Integer> getGenesBag() {
		
		Set<Integer> res = new HashSet<Integer>();
		
		for (int i=0; i<getGenSize();i++){
			res.add(new Integer(i));
		}
			
		return res;
	}

	@Override
	public Set<Integer> getSamplesBag() {
		
		Set<Integer> res = new HashSet<Integer>();
		
		for (int i=0; i<getSampleSize();i++){
			res.add(new Integer(i));
		}
			
		return res;
	}

	@Override
	public Set<Integer> getTimesBag() {
		
		Set<Integer> res = new HashSet<Integer>();
		
		for (int i=0; i<getTimeSize();i++){
			res.add(new Integer(i));
		}
			
		return res;
	}

}