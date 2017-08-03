package alginterface;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Properties;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import general.DatasetModelServer;
import utils.SystemUtilities;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel {
	
	
	private static final Logger LOG = LoggerFactory.getLogger(ControlPanel.class);

	private JSpinner n_value;
	private JSpinner g_value;
	private JSpinner i_value;
	private JSpinner ale_value;
	private JSpinner sel_value;
	private JSpinner mut_value;
	private JSpinner wf_value;
	private JSpinner wg_value;
	private JSpinner wc_value;
	private JSpinner wt_value;
	private JSpinner wog_value;
	private JSpinner woc_value;
	private JSpinner wot_value;	
	private JComboBox<String> fitness_combo;
	private JComboBox<String> dataset_combo;

	private String[] dataset_items;

	private String[] fit_items;

	public ControlPanel() {

		super();

		dataset_items = (DatasetModelServer.getInstance()).getDatasetItems();
	
		fit_items = (DatasetModelServer.getInstance()).getFitnessItems();
		
		this.setLayout(new FormLayout(
				new ColumnSpec[] { ColumnSpec.decode("max(7dlu;pref)"), FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(37dlu;pref)"),
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("max(79dlu;default)"), FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, },
				new RowSpec[] { FormSpecs.PREF_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.PREF_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, }));

		init_labels();
		
		init_values();

	}
	
	public JComboBox<String> getDatasetCombo (){
		return dataset_combo;
	}

	private void init_values() {

		Properties def = SystemUtilities.getAlgorithmProperties();

		Properties gui = SystemUtilities.getGuiProperties();

		SpinnerModel n_model = new SpinnerNumberModel(new Integer(def.getProperty("N")),
				new Integer(gui.getProperty("n_min")), new Integer(gui.getProperty("n_max")),
				new Integer(gui.getProperty("n_step")));
		n_value = new JSpinner(n_model);
		this.add(n_value, "5, 3, fill, fill");

		SpinnerModel g_model = new SpinnerNumberModel(new Integer(def.getProperty("G")),
				new Integer(gui.getProperty("g_min")), new Integer(gui.getProperty("g_max")),
				new Integer(gui.getProperty("g_step")));
		g_value = new JSpinner(g_model);
		this.add(g_value, "5, 5, fill, fill");

		SpinnerModel i_model = new SpinnerNumberModel(new Integer(def.getProperty("I")),
				new Integer(gui.getProperty("i_min")), new Integer(gui.getProperty("i_max")),
				new Integer(gui.getProperty("i_step")));
		i_value = new JSpinner(i_model);
		this.add(i_value, "5, 7, fill, fill");

		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');

		double ale_default = Double.parseDouble((def.getProperty("Ale")));

		SpinnerModel ale_model = new SpinnerNumberModel(new Double(ale_default), new Double(gui.getProperty("ale_min")),
				new Double(gui.getProperty("ale_max")), new Double(gui.getProperty("ale_step")));

		ale_value = new JSpinner(ale_model);

		JSpinner.NumberEditor ale_editor = (JSpinner.NumberEditor) ale_value.getEditor();
		DecimalFormat ale_format = ale_editor.getFormat();
		ale_format.setDecimalFormatSymbols(symbols);

		ale_value.setValue(-0.1);

		ale_value.setValue(ale_default);

		this.add(ale_value, "5, 9, fill, fill");

		double sel_default = Double.parseDouble((def.getProperty("Sel")));

		SpinnerModel sel_model = new SpinnerNumberModel(new Double(sel_default), new Double(gui.getProperty("sel_min")),
				new Double(gui.getProperty("sel_max")), new Double(gui.getProperty("sel_step")));

		sel_value = new JSpinner(sel_model);

		JSpinner.NumberEditor sel_editor = (JSpinner.NumberEditor) sel_value.getEditor();
		DecimalFormat sel_format = sel_editor.getFormat();
		sel_format.setDecimalFormatSymbols(symbols);

		sel_value.setValue(-0.1);

		sel_value.setValue(sel_default);

		this.add(sel_value, "5, 11, fill, fill");

		double mut_default = Double.parseDouble((def.getProperty("Mut")));

		SpinnerModel mut_model = new SpinnerNumberModel(new Double(mut_default), new Double(gui.getProperty("mut_min")),
				new Double(gui.getProperty("mut_max")), new Double(gui.getProperty("mut_step")));

		mut_value = new JSpinner(mut_model);

		JSpinner.NumberEditor mut_editor = (JSpinner.NumberEditor) mut_value.getEditor();
		DecimalFormat mut_format = mut_editor.getFormat();
		mut_format.setDecimalFormatSymbols(symbols);

		mut_value.setValue(-0.1);

		mut_value.setValue(mut_default);

		this.add(mut_value, "5, 13, fill, fill");

		double wf_default = Double.parseDouble((def.getProperty("Wf")));

		SpinnerModel wf_model = new SpinnerNumberModel(new Double(wf_default), new Double(gui.getProperty("wf_min")),
				new Double(gui.getProperty("wf_max")), new Double(gui.getProperty("wf_step")));

		wf_value = new JSpinner(wf_model);

		JSpinner.NumberEditor wf_editor = (JSpinner.NumberEditor) wf_value.getEditor();
		DecimalFormat wf_format = wf_editor.getFormat();
		wf_format.setDecimalFormatSymbols(symbols);

		wf_value.setValue(-0.1);

		wf_value.setValue(wf_default);

		this.add(wf_value, "5, 15, fill, fill");

		double wg_default = Double.parseDouble((def.getProperty("Wg")));

		SpinnerModel wg_model = new SpinnerNumberModel(new Double(wg_default), new Double(gui.getProperty("wg_min")),
				new Double(gui.getProperty("wg_max")), new Double(gui.getProperty("wg_step")));

		wg_value = new JSpinner(wg_model);

		JSpinner.NumberEditor wg_editor = (JSpinner.NumberEditor) wg_value.getEditor();
		DecimalFormat wg_format = wg_editor.getFormat();
		wg_format.setDecimalFormatSymbols(symbols);

		wg_value.setValue(-0.1);

		wg_value.setValue(wg_default);

		this.add(wg_value, "5, 17, fill, fill");

		double wc_default = Double.parseDouble((def.getProperty("Wc")));

		SpinnerModel wc_model = new SpinnerNumberModel(new Double(wc_default), new Double(gui.getProperty("wc_min")),
				new Double(gui.getProperty("wc_max")), new Double(gui.getProperty("wc_step")));

		wc_value = new JSpinner(wc_model);

		JSpinner.NumberEditor wc_editor = (JSpinner.NumberEditor) wc_value.getEditor();
		DecimalFormat wc_format = wc_editor.getFormat();
		wc_format.setDecimalFormatSymbols(symbols);

		wc_value.setValue(-0.1);

		wc_value.setValue(wc_default);

		this.add(wc_value, "5, 19, fill, fill");

		double wt_default = Double.parseDouble((def.getProperty("Wt")));

		SpinnerModel wt_model = new SpinnerNumberModel(new Double(wt_default), new Double(gui.getProperty("wt_min")),
				new Double(gui.getProperty("wt_max")), new Double(gui.getProperty("wt_step")));

		wt_value = new JSpinner(wt_model);

		JSpinner.NumberEditor wt_editor = (JSpinner.NumberEditor) wt_value.getEditor();
		DecimalFormat wt_format = wt_editor.getFormat();
		wt_format.setDecimalFormatSymbols(symbols);

		wt_value.setValue(-0.1);

		wt_value.setValue(wt_default);

		this.add(wt_value, "5, 21, fill, fill");

		double wog_default = Double.parseDouble((def.getProperty("WOg")));

		SpinnerModel wog_model = new SpinnerNumberModel(new Double(wog_default), new Double(gui.getProperty("wog_min")),
				new Double(gui.getProperty("wog_max")), new Double(gui.getProperty("wog_step")));

		wog_value = new JSpinner(wog_model);

		JSpinner.NumberEditor wog_editor = (JSpinner.NumberEditor) wog_value.getEditor();
		DecimalFormat wog_format = wog_editor.getFormat();
		wog_format.setDecimalFormatSymbols(symbols);

		wog_value.setValue(-0.1);

		wog_value.setValue(wog_default);

		this.add(wog_value, "5, 23, fill, fill");

		double woc_default = Double.parseDouble((def.getProperty("WOc")));

		SpinnerModel woc_model = new SpinnerNumberModel(new Double(woc_default), new Double(gui.getProperty("woc_min")),
				new Double(gui.getProperty("woc_max")), new Double(gui.getProperty("woc_step")));

		woc_value = new JSpinner(woc_model);

		JSpinner.NumberEditor woc_editor = (JSpinner.NumberEditor) woc_value.getEditor();
		DecimalFormat woc_format = woc_editor.getFormat();
		woc_format.setDecimalFormatSymbols(symbols);

		woc_value.setValue(-0.1);

		woc_value.setValue(woc_default);

		this.add(woc_value, "5, 25, fill, fill");

		double wot_default = Double.parseDouble((def.getProperty("WOc")));

		SpinnerModel wot_model = new SpinnerNumberModel(new Double(wot_default), new Double(gui.getProperty("wot_min")),
				new Double(gui.getProperty("wot_max")), new Double(gui.getProperty("wot_step")));

		wot_value = new JSpinner(wot_model);

		JSpinner.NumberEditor wot_editor = (JSpinner.NumberEditor) wot_value.getEditor();
		DecimalFormat wot_format = wot_editor.getFormat();
		wot_format.setDecimalFormatSymbols(symbols);

		wot_value.setValue(-0.1);

		wot_value.setValue(wot_default);

		this.add(wot_value, "5, 27, fill, fill");

		buildCombos(def);

	}
	
	public void updateDatasetCombo (){
		dataset_items = (DatasetModelServer.getInstance()).getDatasetItems();
		dataset_combo.setModel(new DefaultComboBoxModel<String>(dataset_items));
	}

	private void buildCombos(Properties def) {

		String dataset_default = def.getProperty("dataset");
		dataset_combo = new JComboBox<String>(dataset_items);
		dataset_combo.setSelectedItem(dataset_default);
		dataset_combo.setFont(new Font("Arial", Font.PLAIN, 12));
		this.add(dataset_combo, "11, 3, fill, fill");
		
		
		
		dataset_combo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				JComponent combo = (JComponent) e.getSource();
						
				JRootPane root = combo.getRootPane();
					
				JFrame parent = (JFrame) root.getParent();
				
				Container containter = parent.getContentPane();
							
				JTabbedPane tab = (JTabbedPane)containter.getComponent(1);
				
				AdvancedPanel adv_panel = (AdvancedPanel)tab.getComponent(1);
				
				adv_panel.updateMaxMin((String)dataset_combo.getSelectedItem());
				
				LOG.debug("name = "+adv_panel.toString());
				LOG.debug("name = "+adv_panel.getName());
				
			}
		});
		
		

		String fitness_default = def.getProperty("fitness");
		fitness_combo = new JComboBox<String>(fit_items);
		fitness_combo.setSelectedItem(fitness_default);
		fitness_combo.setFont(new Font("Arial", Font.PLAIN, 12));
		this.add(fitness_combo, "11, 5, fill, fill");

	}


	
	private void init_labels() {

		JLabel n_label = new JLabel("N");
		n_label.setFont(new Font("Arial", Font.PLAIN, 12));
		this.add(n_label, "3, 3, center, fill");

		JLabel g_label = new JLabel("G");
		g_label.setFont(new Font("Arial", Font.PLAIN, 12));
		this.add(g_label, "3, 5, center, fill");

		JLabel i_label = new JLabel("I");
		i_label.setFont(new Font("Arial", Font.PLAIN, 12));
		this.add(i_label, "3, 7, center, fill");

		JLabel ale_label = new JLabel("Ale");
		ale_label.setFont(new Font("Arial", Font.PLAIN, 12));
		this.add(ale_label, "3, 9, center, fill");

		JLabel sel_label = new JLabel("Sel");
		sel_label.setFont(new Font("Arial", Font.PLAIN, 12));
		this.add(sel_label, "3, 11, center, fill");

		JLabel mut_label = new JLabel("Mut");
		mut_label.setFont(new Font("Arial", Font.PLAIN, 12));
		this.add(mut_label, "3, 13, center, fill");

		JLabel wf_label = new JLabel("Wf");
		wf_label.setFont(new Font("Arial", Font.PLAIN, 12));
		this.add(wf_label, "3, 15, center, fill");

		JLabel wg_label = new JLabel("Wg");
		wg_label.setFont(new Font("Arial", Font.PLAIN, 12));
		this.add(wg_label, "3, 17, center, fill");

		JLabel wc_label = new JLabel("Wc");
		wc_label.setFont(new Font("Arial", Font.PLAIN, 12));
		this.add(wc_label, "3, 19, center, fill");

		JLabel wt_label = new JLabel("Wt");
		wt_label.setFont(new Font("Arial", Font.PLAIN, 12));
		this.add(wt_label, "3, 21, center, fill");

		JLabel wog_label = new JLabel("WOg");
		wog_label.setFont(new Font("Arial", Font.PLAIN, 12));
		this.add(wog_label, "3, 23, center, fill");

		JLabel woc_label = new JLabel("WOc");
		woc_label.setFont(new Font("Arial", Font.PLAIN, 12));
		this.add(woc_label, "3, 25, center, fill");

		JLabel wot_label = new JLabel("WOt");
		wot_label.setFont(new Font("Arial", Font.PLAIN, 12));
		this.add(wot_label, "3, 27, center, fill");

		JLabel dataset_label = new JLabel("Dataset");
		dataset_label.setFont(new Font("Arial", Font.PLAIN, 12));
		this.add(dataset_label, "9, 3, center, fill");

		JLabel fitnes_label = new JLabel("Fitness");
		fitnes_label.setFont(new Font("Arial", Font.PLAIN, 12));
		this.add(fitnes_label, "9, 5, center, fill");

	}

	public int getN_value() {
		return ((Integer)n_value.getValue()).intValue();
	}

	public int getG_value() {
		return ((Integer)g_value.getValue()).intValue();
	}

	public int getI_value() {
		return ((Integer)i_value.getValue()).intValue();
		
	}

	public double getAle_value() {
		return ((Double)ale_value.getValue()).doubleValue();
		
	}

	public double getSel_value() {
		return ((Double)sel_value.getValue()).doubleValue();
	}

	public double getMut_value() {
		return ((Double)mut_value.getValue()).doubleValue();
	}

	public double getWf_value() {
		return ((Double)wf_value.getValue()).doubleValue();
	}

	public double getWg_value() {
		return ((Double)wg_value.getValue()).doubleValue();
	}

	public double getWc_value() {
		return ((Double)wc_value.getValue()).doubleValue();
	}

	public double getWt_value() {
		return ((Double)wt_value.getValue()).doubleValue();
	}

	public double getWog_value() {
		return ((Double)wog_value.getValue()).doubleValue();
	}

	public double getWoc_value() {
		return ((Double)woc_value.getValue()).doubleValue();
	}

	public double getWot_value() {
		return ((Double)wot_value.getValue()).doubleValue();
	}

	public String getFitness_combo() {
		
		return (String)fitness_combo.getSelectedItem();
	}

	public String getDataset_combo() {
		return (String)dataset_combo.getSelectedItem();
	}
	
	
	public void setValues (Properties values){
		
		
		
		 n_value.setValue(new Integer (values.getProperty("N")));
		 g_value.setValue(new Integer (values.getProperty("G")));
		 i_value.setValue(new Integer (values.getProperty("I")));
		ale_value.setValue(new Double (values.getProperty("Ale")));
		 sel_value.setValue(new Double (values.getProperty("Sel")));
		 mut_value.setValue(new Double (values.getProperty("Mut")));
		 wf_value.setValue(new Double (values.getProperty("Wf")));
		wg_value.setValue(new Double (values.getProperty("Wg")));
		 wc_value.setValue(new Double (values.getProperty("Wc")));
		 wt_value.setValue(new Double (values.getProperty("Wt")));
		 wog_value.setValue(new Double (values.getProperty("WOg")));
		woc_value.setValue(new Double (values.getProperty("WOc")));
		 wot_value.setValue(new Double (values.getProperty("WOt")));
		 fitness_combo.setSelectedItem(values.getProperty("fitness"));
		 dataset_combo.setSelectedItem(values.getProperty("dataset"));
		
		
	}

	

}
