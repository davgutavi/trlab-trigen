package alginterface;

import java.awt.Cursor;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import algcore.AlgorithmConfiguration;
import algcore.AlgorithmIndividual;
import algcore.DataHierarchy;
import algcore.TriGen;
import algentrypoint.FootBridge;
import algutils.TriGenBuilder;
import general.Parser;
import input.algorithm.Control;
import input.laboratory.AnalysisResources;
import input.laboratory.WrongOptionsException;
import labentrypoint.Facade;
import utils.SystemUtilities;
import utils.TextUtilities;

public class TriGenGuiTask extends SwingWorker<List<AlgorithmIndividual>, List<AlgorithmIndividual>> {

	private static final Logger LOG = LoggerFactory.getLogger(TriGenGuiTask.class);

	private Control control;

	private long t1;
	private long t2;

	private JPanel panel;
	private JButton okButton;
	private JButton cancelButton;
	private JTextArea message;

	public TriGenGuiTask(Control control) {

		this.control = control;

		(TriGen.getInstance()).setTask(this);
		

	}

	@Override
	protected List<AlgorithmIndividual> doInBackground() throws Exception {

		FootBridge.buildParameters(control);
		AlgorithmConfiguration config = AlgorithmConfiguration.getInstance();
		
		LOG.debug("ID = "+Thread.currentThread().getId());
		LOG.debug("Name = "+Thread.currentThread().getName());
		LOG.info("Run configuration:\n" + config.getReportString());
		LOG.info("Implementation:\n" + control.getImplementation() + "\n");

		TriGen trigen = TriGen.getInstance();
		TriGenBuilder.buildTriGen(control.getImplementation());
		Calendar now1 = Calendar.getInstance();

		LOG.info("Executing algoritm:");

		t1 = now1.getTimeInMillis();

		List<AlgorithmIndividual> r = trigen.runAlgorithm();

		return r;
	}

	protected void done() {

		
		if ((TriGen.getInstance()).isCancelled()){
			
			Toolkit.getDefaultToolkit().beep();
	        okButton.setEnabled(true);
	        cancelButton.setEnabled(false);
	        panel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	        message.setText(SystemUtilities.getLabelProperty("cancelled_message"));
			
		}
		
		else{
		
		Calendar now2 = Calendar.getInstance();
		t2 = now2.getTimeInMillis();

		AlgorithmConfiguration config = AlgorithmConfiguration.getInstance();

		String timeString = TextUtilities.getTimeString(t1, t2);

		List<AlgorithmIndividual> r = null;
		
		File solDir = null;

		try {
			
			r = this.get();
			
			DataHierarchy hierarchy = config.getDataHierarchy();

			AnalysisResources lr = new AnalysisResources(control, t2 - t1);

			lr.loadLegacyDataHierarchy(hierarchy.getGenHierarchy(), hierarchy.getSampleHierarchy(),
					hierarchy.getTimeHierarchy());

			for (AlgorithmIndividual tri : r) {

				lr.loadOneSolution(tri.getGenes(), tri.getSamples(), tri.getTimes());

				lr.loadOneSlog(tri.getRegisterReport(";"));

			}

			LOG.info("");

			LOG.info(timeString);

			LOG.info("Building the solution file");

			solDir = new File(control.getOutFolder() + SystemUtilities.getFileSep() + control.getOutName());

			solDir.mkdirs();

			Parser.buildSolutionFile(lr, solDir.getAbsolutePath());

			Facade.buildCompleteResultsFiles(lr, solDir.getAbsolutePath());
			
//			LOG.debug(Thread.currentThread().toString());

			LOG.info("DONE!");
			
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WrongOptionsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Toolkit.getDefaultToolkit().beep();
        okButton.setEnabled(true);
        cancelButton.setEnabled(false);
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        message.setText(SystemUtilities.getLabelProperty("completed_task"));
        message.append("\n"+SystemUtilities.getLabelProperty("results_message") + solDir.getAbsolutePath());
		}

	}

	public void setProgress(double i) {
		this.setProgress((int) i);
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	public void setOkButton(JButton button) {
		this.okButton = button;
	}
	
	public void setCancelButton(JButton button) {
		this.cancelButton = button;
	}
	
	public void setMessage(JTextArea message) {
		this.message = message;
	}

}
