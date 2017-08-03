package alginterface;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import general.DatasetModelServer;
import input.algorithm.Control;
import input.algorithm.InvalidImplementationException;
import input.algorithm.WrongContolException;
import input.datasets.Common;
import utils.SystemUtilities;
import utils.TextUtilities;

public class GuiLauncher {
	
	

	
	
	public static Control buidGuiControl(ControlPanel control_panel, AdvancedPanel advanced_panel, String workspacePath,
			String experimentName) throws WrongContolException, InvalidImplementationException {
		
		Properties guiProp = new Properties (SystemUtilities.getAlgorithmProperties());
		
		String auxOut = TextUtilities.appendToPath(workspacePath, experimentName); 
		
		String auxDataset = control_panel.getDataset_combo();
		String auxN =control_panel.getN_value() + "";
		String auxG = control_panel.getG_value()+ "";
		String auxI = control_panel.getI_value() + "";
		String auxAle = control_panel.getAle_value() + "";
		String auxSel = control_panel.getSel_value() + "";
		String auxMut = control_panel.getMut_value() + "";
		String auxWf = control_panel.getWf_value() + "";
		String auxWg = control_panel.getWg_value() + "";
		String auxWc = control_panel.getWc_value() + "";
		String auxWt = control_panel.getWt_value() + "";
		String auxWog = control_panel.getWog_value() + "";
		String auxWoc = control_panel.getWoc_value() + "";
		String auxWot = control_panel.getWot_value() + "";
		
		String auxMinG = advanced_panel.getMinG_v() + "";
		String auxMinC = advanced_panel.getMinC_v() + "";
		String auxMinT = advanced_panel.getMinT_v() + "";
		String auxMaxG = advanced_panel.getMaxG_v() + "";
		String auxMaxC = advanced_panel.getMaxC_v() + "";
		String auxMaxT = advanced_panel.getMaxT_v() + "";
		String auxThreads = advanced_panel.getThreads_v() + "";
		
		guiProp.setProperty("dataset", auxDataset);
		guiProp.setProperty("N", auxN);
		guiProp.setProperty("G", auxG);
		guiProp.setProperty("I", auxI);
		guiProp.setProperty("Ale", auxAle);
		guiProp.setProperty("Sel", auxSel);
		guiProp.setProperty("Mut", auxMut);
		guiProp.setProperty("Wf", auxWf);
		guiProp.setProperty("Wg", auxWg);
		guiProp.setProperty("Wc", auxWc);
		guiProp.setProperty("Wt", auxWt);
		guiProp.setProperty("WOg", auxWog);
		guiProp.setProperty("WOc", auxWoc);
		guiProp.setProperty("WOt", auxWot);
		guiProp.setProperty("minG", auxMinG);
		guiProp.setProperty("minC", auxMinC);
		guiProp.setProperty("minT", auxMinT);
		guiProp.setProperty("maxG", auxMaxG);
		guiProp.setProperty("maxC", auxMaxC);
		guiProp.setProperty("maxT", auxMaxT);
		guiProp.setProperty("threads", auxThreads);
				
				
		guiProp.setProperty("individual", advanced_panel.getIndividual_v());
		guiProp.setProperty("fitness", control_panel.getFitness_combo());
		guiProp.setProperty("datahierarchy", advanced_panel.getHierarchy_v());
		guiProp.setProperty("stoppingcriterion", advanced_panel.getStop_v());
		guiProp.setProperty("solutioncriterion", advanced_panel.getSolution_v());
		guiProp.setProperty("initialpop", advanced_panel.getInitialpop_v());
		guiProp.setProperty("selection", advanced_panel.getSelection_v());
		guiProp.setProperty("crossover", advanced_panel.getCrossover_v());
		guiProp.setProperty("mutation", advanced_panel.getMutation_v());
	
		
		guiProp.setProperty("out", auxOut);
		
		
		Common dataset = (DatasetModelServer.getInstance()).getDatasetByName(guiProp.getProperty("dataset"));
		
		
		Control guiControl = new Control (dataset, guiProp);
	
		return guiControl;
		
	}
	
	
	public static void saveControl (ControlPanel control_panel, AdvancedPanel advanced_panel, String workspacePath,
			String experimentName, String destinationPath) throws WrongContolException, InvalidImplementationException, IOException {
		
		Properties guiProp = new Properties (SystemUtilities.getAlgorithmProperties());
		
		String auxOut = TextUtilities.appendToPath(workspacePath, experimentName); 
		
		String auxDataset = control_panel.getDataset_combo();
		String auxN =control_panel.getN_value() + "";
		String auxG = control_panel.getG_value()+ "";
		String auxI = control_panel.getI_value() + "";
		String auxAle = control_panel.getAle_value() + "";
		String auxSel = control_panel.getSel_value() + "";
		String auxMut = control_panel.getMut_value() + "";
		String auxWf = control_panel.getWf_value() + "";
		String auxWg = control_panel.getWg_value() + "";
		String auxWc = control_panel.getWc_value() + "";
		String auxWt = control_panel.getWt_value() + "";
		String auxWog = control_panel.getWog_value() + "";
		String auxWoc = control_panel.getWoc_value() + "";
		String auxWot = control_panel.getWot_value() + "";
		
		String auxMinG = advanced_panel.getMinG_v() + "";
		String auxMinC = advanced_panel.getMinC_v() + "";
		String auxMinT = advanced_panel.getMinT_v() + "";
		String auxMaxG = advanced_panel.getMaxG_v() + "";
		String auxMaxC = advanced_panel.getMaxC_v() + "";
		String auxMaxT = advanced_panel.getMaxT_v() + "";
		String auxThreads = advanced_panel.getThreads_v() + "";
		
		guiProp.setProperty("dataset", auxDataset);
		guiProp.setProperty("N", auxN);
		guiProp.setProperty("G", auxG);
		guiProp.setProperty("I", auxI);
		guiProp.setProperty("Ale", auxAle);
		guiProp.setProperty("Sel", auxSel);
		guiProp.setProperty("Mut", auxMut);
		guiProp.setProperty("Wf", auxWf);
		guiProp.setProperty("Wg", auxWg);
		guiProp.setProperty("Wc", auxWc);
		guiProp.setProperty("Wt", auxWt);
		guiProp.setProperty("WOg", auxWog);
		guiProp.setProperty("WOc", auxWoc);
		guiProp.setProperty("WOt", auxWot);
		guiProp.setProperty("minG", auxMinG);
		guiProp.setProperty("minC", auxMinC);
		guiProp.setProperty("minT", auxMinT);
		guiProp.setProperty("maxG", auxMaxG);
		guiProp.setProperty("maxC", auxMaxC);
		guiProp.setProperty("maxT", auxMaxT);
		guiProp.setProperty("threads", auxThreads);
				
				
		guiProp.setProperty("individual", advanced_panel.getIndividual_v());
		guiProp.setProperty("fitness", control_panel.getFitness_combo());
		guiProp.setProperty("datahierarchy", advanced_panel.getHierarchy_v());
		guiProp.setProperty("stoppingcriterion", advanced_panel.getStop_v());
		guiProp.setProperty("solutioncriterion", advanced_panel.getSolution_v());
		guiProp.setProperty("initialpop", advanced_panel.getInitialpop_v());
		guiProp.setProperty("selection", advanced_panel.getSelection_v());
		guiProp.setProperty("crossover", advanced_panel.getCrossover_v());
		guiProp.setProperty("mutation", advanced_panel.getMutation_v());
	
		
		guiProp.setProperty("out", auxOut);

		PrintStream ps = new PrintStream(destinationPath);
		guiProp.store(ps, "");
	

	
		
		
	}
	
	
	
	

}
