package alginterface;

import java.awt.Font;
import java.util.List;
import java.util.Properties;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import general.DatasetModelServer;
import input.datasets.Common;
import utils.SystemUtilities;

@SuppressWarnings("serial")
public class AdvancedPanel extends JPanel {

	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(AdvancedPanel.class);
	
	
	//combobox from control panel
	private JComboBox<String> dataset_combo;
	
	private JSpinner threads_v;

	private JComboBox<String> individual_v;
	private JComboBox<String> hierarchy_v;
	private JComboBox<String> stop_v;
	private JComboBox<String> solution_v;
	private JComboBox<String> initialpop_v;
	private JComboBox<String> selection_v;
	private JComboBox<String> crossover_v;
	private JComboBox<String> mutation_v;

	private JSpinner minG_v;
	private JSpinner minC_v;
	private JSpinner minT_v;
	private JSpinner maxG_v;
	private JSpinner maxC_v;
	private JSpinner maxT_v;

	
	
	private String[] ind_items;
	private String[] hie_items;
	private String[] stp_items;
	private String[] sol_items;
	private String[] ini_items;
	private String[] sel_items;
	private String[] cro_items;
	private String[] mut_items;
	
	

	public AdvancedPanel(JComboBox<String> dataset_combo) {

		super();

		this.setName("advanced panel");
		
		this.dataset_combo = dataset_combo;

		loadModels();
		

		this.setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("max(54dlu;default)"), FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("max(57dlu;default)"), FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("max(40dlu;default)"), FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("max(45dlu;default)"), FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("max(39dlu;default)"), FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("max(39dlu;default)"), FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC, },
				new RowSpec[] { FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, }));

		init_labels();

		init_values();

	}

	private void init_values() {

		Properties def = SystemUtilities.getAlgorithmProperties();
		Properties gui = SystemUtilities.getGuiProperties();

		int threads_default = Integer.parseInt(def.getProperty("threads"));
		int threads_min = Integer.parseInt(gui.getProperty("threads_min"));
		int threads_max = Integer.parseInt(gui.getProperty("threads_max"));
		int threads_step = Integer.parseInt(gui.getProperty("threads_step"));

		SpinnerModel threads_model = new SpinnerNumberModel(new Integer(threads_default), new Integer(threads_min),
				new Integer(threads_max), new Integer(threads_step));

		threads_v = new JSpinner(threads_model);

		this.add(threads_v, "5, 3, fill, fill");

		buildAdvancedCombos(def);

		initMaxMin ((String)dataset_combo.getSelectedItem());

	}
	
	
	private void initMaxMin (String currentDataset){
		
		Properties gui = SystemUtilities.getGuiProperties();
		
		Common data = (DatasetModelServer.getInstance()).getDatasetByName(currentDataset);

		int minG_min = Integer.parseInt(gui.getProperty("ming_min"));
		int minG_step = Integer.parseInt(gui.getProperty("ming_step"));
		SpinnerModel minG_model = new SpinnerNumberModel(data.getDefMinG(), minG_min, data.getGeneSize(), minG_step);
		minG_v = new JSpinner(minG_model);
		this.add(minG_v, "5, 25, center, fill");

		int minC_min = Integer.parseInt(gui.getProperty("minc_min"));
		int minC_step = Integer.parseInt(gui.getProperty("minc_step"));
		SpinnerModel minC_model = new SpinnerNumberModel(data.getDefMinC(), minC_min, data.getSampleSize(), minC_step);
		minC_v = new JSpinner(minC_model);
		this.add(minC_v, "9, 25, fill, fill");

		int minT_min = Integer.parseInt(gui.getProperty("mint_min"));
		int minT_step = Integer.parseInt(gui.getProperty("mint_step"));
		SpinnerModel minT_model = new SpinnerNumberModel(data.getDefMinT(), minT_min, data.getTimeSize(), minT_step);
		minT_v = new JSpinner(minT_model);
		this.add(minT_v, "13, 25, fill, fill");

		int maxG_min = Integer.parseInt(gui.getProperty("maxg_min"));
		int maxG_step = Integer.parseInt(gui.getProperty("maxg_step"));
		SpinnerModel maxG_model = new SpinnerNumberModel(data.getDefMaxG(), maxG_min, data.getGeneSize(), maxG_step);
		maxG_v = new JSpinner(maxG_model);
		this.add(maxG_v, "5, 27, center, fill");

		int maxC_min = Integer.parseInt(gui.getProperty("maxc_min"));
		int maxC_step = Integer.parseInt(gui.getProperty("maxc_step"));
		SpinnerModel maxC_model = new SpinnerNumberModel(data.getDefMaxC(), maxC_min, data.getSampleSize(), maxC_step);
		maxC_v = new JSpinner(maxC_model);
		this.add(maxC_v, "9, 27, fill, fill");

		int maxT_min = Integer.parseInt(gui.getProperty("maxt_min"));
		int maxT_step = Integer.parseInt(gui.getProperty("maxt_step"));
		SpinnerModel maxT_model = new SpinnerNumberModel(data.getDefMaxT(), maxT_min, data.getTimeSize(), maxT_step);
		maxT_v = new JSpinner(maxT_model);
		this.add(maxT_v, "13, 27, fill, fill");
				
	}
	
	public void updateMaxMin (String currentDataset){
		
		Properties gui = SystemUtilities.getGuiProperties();
		
		Common data = (DatasetModelServer.getInstance()).getDatasetByName(currentDataset);

		int minG_min = Integer.parseInt(gui.getProperty("ming_min"));
		int minG_step = Integer.parseInt(gui.getProperty("ming_step"));
		SpinnerModel minG_model = new SpinnerNumberModel(data.getDefMinG(), minG_min, data.getGeneSize(), minG_step);
		minG_v.setModel(minG_model);

		int minC_min = Integer.parseInt(gui.getProperty("minc_min"));
		int minC_step = Integer.parseInt(gui.getProperty("minc_step"));
		SpinnerModel minC_model = new SpinnerNumberModel(data.getDefMinC(), minC_min, data.getSampleSize(), minC_step);
		minC_v.setModel(minC_model);

		int minT_min = Integer.parseInt(gui.getProperty("mint_min"));
		int minT_step = Integer.parseInt(gui.getProperty("mint_step"));
		SpinnerModel minT_model = new SpinnerNumberModel(data.getDefMinT(), minT_min, data.getTimeSize(), minT_step);
		minT_v.setModel(minT_model);

		int maxG_min = Integer.parseInt(gui.getProperty("maxg_min"));
		int maxG_step = Integer.parseInt(gui.getProperty("maxg_step"));
		SpinnerModel maxG_model = new SpinnerNumberModel(data.getDefMaxG(), maxG_min, data.getGeneSize(), maxG_step);
		maxG_v.setModel(maxG_model);

		int maxC_min = Integer.parseInt(gui.getProperty("maxc_min"));
		int maxC_step = Integer.parseInt(gui.getProperty("maxc_step"));
		SpinnerModel maxC_model = new SpinnerNumberModel(data.getDefMaxC(), maxC_min, data.getSampleSize(), maxC_step);
		maxC_v.setModel(maxC_model);

		int maxT_min = Integer.parseInt(gui.getProperty("maxt_min"));
		int maxT_step = Integer.parseInt(gui.getProperty("maxt_step"));
		SpinnerModel maxT_model = new SpinnerNumberModel(data.getDefMaxT(), maxT_min, data.getTimeSize(), maxT_step);
		maxT_v.setModel(maxT_model);
		
		
	}
	

	private void buildAdvancedCombos(Properties def) {

		String individual_default = def.getProperty("individual");
		individual_v = new JComboBox<String>(ind_items);
		individual_v.setSelectedItem(individual_default);
		this.add(individual_v, "5, 7, fill, fill");

		String hierarchy_default = def.getProperty("datahierarchy");
		hierarchy_v = new JComboBox<String>(hie_items);
		hierarchy_v.setSelectedItem(hierarchy_default);
		this.add(hierarchy_v, "5, 9, fill, fill");

		String stop_default = def.getProperty("stoppingcriterion");
		stop_v = new JComboBox<String>(stp_items);
		stop_v.setSelectedItem(stop_default);
		this.add(stop_v, "5, 11, fill, fill");

		String solution_default = def.getProperty("solutioncriterion");
		solution_v = new JComboBox<String>(sol_items);
		solution_v.setSelectedItem(solution_default);
		this.add(solution_v, "5, 13, fill, fill");

		String initialpop_default = def.getProperty("initialpop");
		initialpop_v = new JComboBox<String>(ini_items);
		initialpop_v.setSelectedItem(initialpop_default);
		this.add(initialpop_v, "5, 15, fill, fill");

		String selection_default = def.getProperty("selection");
		selection_v = new JComboBox<String>(sel_items);
		selection_v.setSelectedItem(selection_default);
		this.add(selection_v, "5, 17, fill, fill");

		String crossover_default = def.getProperty("crossover");
		crossover_v = new JComboBox<String>(cro_items);
		crossover_v.setSelectedItem(crossover_default);
		this.add(crossover_v, "5, 19, fill, fill");

		String mutation_default = def.getProperty("mutation");
		mutation_v = new JComboBox<String>(mut_items);
		mutation_v.setSelectedItem(mutation_default);
		this.add(mutation_v, "5, 21, fill, fill");

	}

	private void init_labels() {

		JLabel threads_l = new JLabel("Threads");
		threads_l.setFont(new Font("Arial", Font.PLAIN, 12));
		this.add(threads_l, "3, 3, center, fill");

		JLabel individual_l = new JLabel("Individual");
		individual_l.setFont(new Font("Arial", Font.PLAIN, 12));
		this.add(individual_l, "3, 7, center, fill");

		JLabel hierarchy_l = new JLabel("Data hierarchy");
		hierarchy_l.setFont(new Font("Arial", Font.PLAIN, 12));
		this.add(hierarchy_l, "3, 9, center, fill");

		JLabel stop_l = new JLabel("Stopping criterion");
		stop_l.setFont(new Font("Arial", Font.PLAIN, 12));
		this.add(stop_l, "3, 11, center, fill");

		JLabel solution_l = new JLabel("Solution criterion");
		solution_l.setFont(new Font("Arial", Font.PLAIN, 12));
		this.add(solution_l, "3, 13, center, fill");

		JLabel initialpop_l = new JLabel("Initial population");
		initialpop_l.setFont(new Font("Arial", Font.PLAIN, 12));
		this.add(initialpop_l, "3, 15, center, fill");

		JLabel selection_l = new JLabel("Selection");
		selection_l.setFont(new Font("Arial", Font.PLAIN, 12));
		this.add(selection_l, "3, 17, center, fill");

		JLabel crossover_l = new JLabel("Crossover");
		crossover_l.setFont(new Font("Arial", Font.PLAIN, 12));
		this.add(crossover_l, "3, 19, center, fill");

		JLabel mutation_l = new JLabel("Mutation");
		mutation_l.setFont(new Font("Arial", Font.PLAIN, 12));
		this.add(mutation_l, "3, 21, center, fill");

		JLabel minG_l = new JLabel("min G");
		minG_l.setFont(new Font("Arial", Font.PLAIN, 12));
		this.add(minG_l, "3, 25, center, fill");

		JLabel minC_l = new JLabel("min C");
		minC_l.setFont(new Font("Arial", Font.PLAIN, 12));
		this.add(minC_l, "7, 25, center, fill");

		JLabel minT_l = new JLabel("min T");
		minT_l.setFont(new Font("Arial", Font.PLAIN, 12));
		this.add(minT_l, "11, 25, center, fill");

		JLabel maxG_l = new JLabel("max G");
		maxG_l.setFont(new Font("Arial", Font.PLAIN, 12));
		this.add(maxG_l, "3, 27, center, fill");

		JLabel maxC_l = new JLabel("max C");
		maxC_l.setFont(new Font("Arial", Font.PLAIN, 12));
		this.add(maxC_l, "7, 27, center, fill");

		JLabel maxT_l = new JLabel("max T");
		maxT_l.setFont(new Font("Arial", Font.PLAIN, 12));
		this.add(maxT_l, "11, 27, center, fill");

	}
	
	private void loadModels (){
	
		
		List<String[]> aux =  (DatasetModelServer.getInstance()).getImplementationItems();
		
		
		ind_items = aux.get(1);
		hie_items = aux.get(2);
		 stp_items = aux.get(3);
		 sol_items = aux.get(4);
		 ini_items = aux.get(5);
		 sel_items = aux.get(6);
		 cro_items = aux.get(7);
		 mut_items = aux.get(8);
		
		
		
	}

	public int getThreads_v() {
		return ((Integer)threads_v.getValue()).intValue();
	}

	public String getIndividual_v() {
		return (String)individual_v.getSelectedItem();
	}

	public String getHierarchy_v() {
		return  (String)hierarchy_v.getSelectedItem();
	}

	public String getStop_v() {
		return  (String)stop_v.getSelectedItem();
	}

	public String getSolution_v() {
		return  (String)solution_v.getSelectedItem();
	}

	public String getInitialpop_v() {
		return  (String)initialpop_v.getSelectedItem();
	}

	public String getSelection_v() {
		return  (String)selection_v.getSelectedItem();
	}

	public String getCrossover_v() {
		return  (String)crossover_v.getSelectedItem();
	}

	public String getMutation_v() {
		return  (String)mutation_v.getSelectedItem();
	}

	public int getMinG_v() {
		return ((Integer)minG_v.getValue()).intValue();
	}

	public int getMinC_v() {
		return ((Integer)minC_v.getValue()).intValue();
	}

	public int getMinT_v() {
		return ((Integer)minT_v.getValue()).intValue();
	}

	public int getMaxG_v() {
		return ((Integer)maxG_v.getValue()).intValue();
	}

	public int getMaxC_v() {
		return ((Integer)maxC_v.getValue()).intValue();
	}

	public int getMaxT_v() {
		return ((Integer)maxT_v.getValue()).intValue();
	}
	
	
	public void setValues (Properties values){
		
		threads_v.setValue(new Integer (values.getProperty("threads")));
		individual_v.setSelectedItem(values.getProperty("individual"));
		hierarchy_v.setSelectedItem(values.getProperty("datahierarchy"));
		stop_v.setSelectedItem(values.getProperty("stoppingcriterion"));
		solution_v.setSelectedItem(values.getProperty("solutioncriterion"));
		initialpop_v.setSelectedItem(values.getProperty("initialpop"));
		 selection_v.setSelectedItem(values.getProperty("selection"));
		 crossover_v.setSelectedItem(values.getProperty("crossover"));
		 mutation_v.setSelectedItem(values.getProperty("mutation"));
		 minG_v.setValue(new Double (values.getProperty("minG")));
		 minC_v.setValue(new Double (values.getProperty("minC")));
		 minT_v.setValue(new Double (values.getProperty("minT")));
		 maxG_v.setValue(new Double (values.getProperty("maxG")));
		 maxC_v.setValue(new Double (values.getProperty("maxC")));
		 maxT_v.setValue(new Double (values.getProperty("maxT")));

		
	}

}
