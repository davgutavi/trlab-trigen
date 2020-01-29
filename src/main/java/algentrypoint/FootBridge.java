package algentrypoint;

import input.algorithm.Control;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import algcore.AlgorithmConfiguration;
import algcore.DataHierarchy;
import alginput.Microarray;
import algutils.LegacyWrongInput;
import algutils.TriGenBuilder;

/**
 * A bridge between input parameters and algorithm configuration.
 * 
 * 
 * @author David Gutiérrez-Avilés
 *
 */
public class FootBridge {

	/**
	 * Builds the {@link AlgorithmConfiguration}.
	 * 
	 * @param control the control parameters
	 * @throws IOException
	 * @throws LegacyWrongInput
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static void buildParameters(Control control) throws IOException,
			LegacyWrongInput, NoSuchMethodException, SecurityException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {

		AlgorithmConfiguration PARAM = AlgorithmConfiguration.getInstance();

		PARAM.setData(new Microarray(control.getDataset().getDataset()));
		PARAM.setN(control.getN());
		PARAM.setG(control.getG());
		PARAM.setI(control.getI());
		PARAM.setSel((float) control.getSel());
		PARAM.setAle((float) control.getAle());
		PARAM.setMut((float) control.getMut());
		PARAM.setWf(control.getWf());
		PARAM.setWg((float) control.getWg());
		PARAM.setWc((float) control.getWc());
		PARAM.setWt((float) control.getWt());
		PARAM.setWog((float) control.getWog());
		PARAM.setWoc((float) control.getWoc());
		PARAM.setWot((float) control.getWot());
		PARAM.setMinG(control.getMinG());
		PARAM.setMinC(control.getMinC());
		PARAM.setMinT(control.getMinT());
		PARAM.setMaxG(control.getMaxG());
		PARAM.setMaxC(control.getMaxC());
		PARAM.setMaxT(control.getMaxT());
		int nt = control.getThreads();
		boolean con = false;
		if (nt > 0)
			con = true;
		PARAM.setConcurrency(con);
		PARAM.setThreads(nt);

		DataHierarchy jerarquia = TriGenBuilder.getInstance()
				.buildDataHierarchyV2(control.getImplementation()
						.getDataHierarchy(),
						control.getDataset().getGeneSize(), control
								.getDataset().getSampleSize(), control
								.getDataset().getTimeSize());

		PARAM.setDataHierarchy(jerarquia);

		String aux = "";

		aux += "dataset = " + control.getDataset().getDatasetName() + "\n";
		aux += "concurrence = " + PARAM.isConcurrency() + "\n";
		aux += "threads = " + PARAM.getThreads() + "\n";
		aux += "N   = " + PARAM.getN() + "\n";
		aux += "G   = " + PARAM.getG() + "\n";
		aux += "I   = " + PARAM.getI() + "\n";
		aux += "Sel = " + PARAM.getSel() + "\n";
		aux += "Mut = " + PARAM.getMut() + "\n";
		aux += "Ale = " + PARAM.getAle() + "\n";
		aux += "Wf  = " + PARAM.getWf() + "\n";
		aux += "Wg  = " + PARAM.getWg() + "\n";
		aux += "Wc  = " + PARAM.getWc() + "\n";
		aux += "Wt  = " + PARAM.getWt() + "\n";
		aux += "WOg = " + PARAM.getWog() + "\n";
		aux += "WOc = " + PARAM.getWoc() + "\n";
		aux += "WOt = " + PARAM.getWot() + "\n";
		aux += "minG = " + PARAM.getMinG() + "\n";
		aux += "minC = " + PARAM.getMinC() + "\n";
		aux += "minT = " + PARAM.getMinT() + "\n";
		aux += "maxG = " + PARAM.getMaxG() + "\n";
		aux += "maxC = " + PARAM.getMaxC() + "\n";
		aux += "maxT = " + PARAM.getMaxT() + "\n";
		aux += "data        = " + ((PARAM.getData()).getClass()).getName()
				+ "\n";
		aux += "hierarchy   = "
				+ ((PARAM.getDataHierarchy()).getClass()).getName() + "\n";

		PARAM.setReportString(aux);

	}
}
