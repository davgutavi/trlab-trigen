package alginterface;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import input.algorithm.Control;
import input.algorithm.InvalidImplementationException;
import input.algorithm.WrongContolException;
import utils.SystemUtilities;

public class AlgApp {

	

	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(AlgApp.class);
	
	public  JFrame frame;
	
	private ControlPanel control_panel;
	private AdvancedPanel advanced_panel;
	private SetWorkspaceMenu setWorkspace ;
	private SaveExperimentMenu savExperiment ;
	private LoadExperimentMenu loadExperiment ;
	private JTextField expName_v;
	
	private JComboBox<String> dataset_combo;
	
	private LaunchTriGen launchTrigen;
	
	
	
	
	
	

	/**
	 * Create the application.
	 */
	public AlgApp() {				
		initialize();
	}


	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
			
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Arial", Font.PLAIN, 12));
		frame.setBounds(100, 100, 848, 700);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		expName_v = new JTextField();
		expName_v.setBounds(251, 601, 152, 28);
		frame.getContentPane().add(expName_v);
		expName_v.setColumns(10);		
		expName_v.setText(SystemUtilities.getLabelProperty("exp_name_default"));
		
		setWorkspace = new SetWorkspaceMenu ();
		setWorkspace.setVisible(false);
		
		savExperiment = new SaveExperimentMenu (expName_v.getText());
		savExperiment.setVisible(false);
		
		loadExperiment = new LoadExperimentMenu();
		loadExperiment.setVisible(false);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 90, 842, 487);
		frame.getContentPane().add(tabbedPane);
		
		control_panel = new ControlPanel();
		tabbedPane.addTab(SystemUtilities.getLabelProperty("control_tab_tag"), null, control_panel, null);
		
		dataset_combo = control_panel.getDatasetCombo();
				
		advanced_panel = new AdvancedPanel(dataset_combo);
		tabbedPane.addTab(SystemUtilities.getLabelProperty("advanced_tab_tag"), null, advanced_panel, null);
		
				
		JMenuBar menu_bar = new JMenuBar();
		menu_bar.setBorder(new LineBorder(SystemColor.BLACK, 1, true));
		menu_bar.setBounds(0, 0, 174, 22);
		
		frame.getContentPane().add(menu_bar);
		
		JMenu load_menu = new JMenu(SystemUtilities.getLabelProperty("load_menu_tag"));
		load_menu.setFont(new Font("Arial", Font.BOLD, 14));
		menu_bar.add(load_menu);
		
		JMenuItem mntmLoadExperiment = new JMenuItem(SystemUtilities.getLabelProperty("load_experiment_menu_tag"));
		mntmLoadExperiment.setFont(new Font("Arial", Font.BOLD, 14));
		load_menu.add(mntmLoadExperiment);
		
		mntmLoadExperiment.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				loadExperiment.resetText();
				loadExperiment.setExpName(expName_v);
				loadExperiment.setWorkspaceMenu(setWorkspace);
				loadExperiment.setControlPanel(control_panel);
				loadExperiment.setAdvancedPanel(advanced_panel);
				loadExperiment.setVisible(true);
				
			}
		});
		
		
		JMenu mnSave = new JMenu(SystemUtilities.getLabelProperty("save_menu_tag"));
		mnSave.setFont(new Font("Arial", Font.BOLD, 14));
		menu_bar.add(mnSave);
		
		JMenuItem mntmNewMenuItem = new JMenuItem(SystemUtilities.getLabelProperty("save_experiment_menu_tag"));
		mntmNewMenuItem.setFont(new Font("Arial", Font.BOLD, 14));
		mnSave.add(mntmNewMenuItem);
		
		mntmNewMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
							
				savExperiment.resetText();
				savExperiment.setExpName(expName_v.getText());				
				savExperiment.setControl_panel(control_panel);
				savExperiment.setAdvanced_panel(advanced_panel);
				savExperiment.setWorkspacePath(setWorkspace.getSelectedPath());
				savExperiment.setVisible(true);
			
			}
		});
		
		JMenu mnSettings = new JMenu(SystemUtilities.getLabelProperty("settings_menu_tag"));
		mnSettings.setFont(new Font("Arial", Font.BOLD, 14));
		menu_bar.add(mnSettings);
		
		JMenuItem mntmSetWorkspace = new JMenuItem(SystemUtilities.getLabelProperty("set_workspace_menu_tag"));
		mntmSetWorkspace.setFont(new Font("Arial", Font.BOLD, 14));
		mnSettings.add(mntmSetWorkspace);
		mntmSetWorkspace.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setWorkspace.setVisible(true);
			}
		});
		
		menu_bar.validate();
		
		JButton launch_button = new JButton(SystemUtilities.getLabelProperty("launch_button"));
		launch_button.setBounds(529, 602, 117, 29);
		frame.getContentPane().add(launch_button);
		
		JLabel expName_l = new JLabel(SystemUtilities.getLabelProperty("experimet_name_tag"));
		expName_l.setBounds(48, 607, 198, 16);
		frame.getContentPane().add(expName_l);
		
		JTextPane txtpnTrlabAlgapp = new JTextPane();
		txtpnTrlabAlgapp.setText("TrLab - TriGen");
		txtpnTrlabAlgapp.setPreferredSize(new Dimension (100,100));
		
		txtpnTrlabAlgapp.setBorder(null);
		txtpnTrlabAlgapp.setForeground(SystemColor.controlHighlight);
		txtpnTrlabAlgapp.setBackground(SystemColor.window);
		txtpnTrlabAlgapp.setEditable(false);
		txtpnTrlabAlgapp.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 30));
		
		txtpnTrlabAlgapp.setBounds(310, 20, 225, 40);
		frame.getContentPane().add(txtpnTrlabAlgapp);
		
	
		
		
		
		
//		trigenProgress = new TriGenProgress ();
		
		launchTrigen = new LaunchTriGen();

		launch_button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
												
		
				Control control = null;
				try {
					control = GuiLauncher.buidGuiControl(control_panel, advanced_panel, setWorkspace.getSelectedPath(), expName_v.getText());
				} catch (WrongContolException | InvalidImplementationException e1) {
					e1.printStackTrace();
				}
					
//				trigenProgress.show();
//				trigenProgress.launch(control);
				
				launchTrigen.setVisible(true);
				
				launchTrigen.launch(control);

			
			}
		});
		
		
		
		
	
	}
}



