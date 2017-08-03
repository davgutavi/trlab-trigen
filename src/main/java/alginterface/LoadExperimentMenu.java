package alginterface;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.SystemUtilities;
import utils.TextUtilities;

import java.awt.Color;
import java.awt.SystemColor;

@SuppressWarnings("serial")
public class LoadExperimentMenu extends JDialog {

	private static final Logger LOG = LoggerFactory.getLogger(SetWorkspaceMenu.class);

	private final JPanel contentPanel = new JPanel();
	private JTextField selectedPath_f;

	private String selectedPath_v;
	private JTextField textField;
	
	
	
	private ControlPanel controlPanel;
	private AdvancedPanel advancedPanel;
	private SetWorkspaceMenu workspaceMenu;
	private JTextField expName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SetWorkspaceMenu dialog = new SetWorkspaceMenu();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	
	public String getSelectedPath() {
		return selectedPath_v;
	}
	
	
	public void resetText (){
		textField.setText("");
	}

	/**
	 * Create the dialog.
	 */
	public LoadExperimentMenu() {

		setBounds(100, 100, 852, 166);

		getContentPane().setLayout(null);

		contentPanel.setBounds(0, 0, 0, 0);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);

		contentPanel.setLayout(null);

		JLabel lblWorkspace = new JLabel(SystemUtilities.getLabelProperty("control_path_tag"));
		lblWorkspace.setFont(new Font("Arial", Font.PLAIN, 12));
		lblWorkspace.setBounds(17, 21, 111, 16);
		getContentPane().add(lblWorkspace);

		JPanel buttonPane = new JPanel();
		buttonPane.setBounds(69, 91, 450, 47);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane);

		JButton loadButton = new JButton(SystemUtilities.getLabelProperty("load_button"));
		loadButton.setFont(new Font("Arial", Font.PLAIN, 12));
		loadButton.setActionCommand("OK");

		buttonPane.add(loadButton);
		getRootPane().setDefaultButton(loadButton);

		loadButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				selectedPath_v = selectedPath_f.getText();
										
				String ext = TextUtilities.getFileExtension(selectedPath_v);
				
				if (!ext.equalsIgnoreCase(SystemUtilities.getSystemProperty("control_extension"))){
				
					textField.setText(SystemUtilities.getLabelProperty("wrong_extension_text"));
					
				}
				
				else {
					
					Properties defaults = SystemUtilities.getAlgorithmProperties();
					
					Properties user = new Properties(defaults);

					try {
						user.load(new FileInputStream(selectedPath_v));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					String userOut = user.getProperty("out");
					
					expName.setText(TextUtilities.getFileName(userOut));
					
					workspaceMenu.setPaths(TextUtilities.getRootPathWithSlash(userOut));
					
					advancedPanel.setValues(user);
					controlPanel.setValues(user);
					
					JButton button = (JButton) e.getSource();

					JRootPane root = button.getRootPane();

					JDialog dialog = (JDialog) root.getParent();

					dialog.dispose();					
					
				}
				
				LOG.debug("Selected path : " + selectedPath_v);

			}
		});

		JButton cancelButton = new JButton(SystemUtilities.getLabelProperty("cancel_button"));
		cancelButton.setFont(new Font("Arial", Font.PLAIN, 12));
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JButton button = (JButton) e.getSource();

				JRootPane root = button.getRootPane();

				JDialog dialog = (JDialog) root.getParent();

				dialog.dispose();

				LOG.debug("Selected path : " + selectedPath_v);

			}
		});

		selectedPath_f = new JTextField();
		selectedPath_f.setFont(new Font("Arial", Font.PLAIN, 12));
		selectedPath_f.setBounds(140, 15, 584, 28);
		getContentPane().add(selectedPath_f);
		selectedPath_f.setColumns(10);

		selectedPath_f.setText(System.getProperty("user.home"));
		selectedPath_v = selectedPath_f.getText();

		JButton explore_button = new JButton(SystemUtilities.getLabelProperty("explore_button"));
		explore_button.setFont(new Font("Arial", Font.PLAIN, 12));
		explore_button.setBounds(729, 16, 117, 29);
		getContentPane().add(explore_button);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBackground(SystemColor.window);
		textField.setForeground(Color.RED);
		textField.setFont(new Font("Arial", Font.PLAIN, 12));
		textField.setBounds(140, 51, 584, 28);
		getContentPane().add(textField);
		textField.setColumns(10);
		textField.setBorder(null);

		explore_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home"));

				int choice = fileChooser.showOpenDialog(explore_button);

				if (choice == JFileChooser.APPROVE_OPTION) {
				
					selectedPath_f.setText(fileChooser.getSelectedFile().getPath());
									
				}
							
				
			}
		});

	}




	public void setControlPanel(ControlPanel controlPanel) {
		this.controlPanel = controlPanel;
	}




	public void setAdvancedPanel(AdvancedPanel advancedPanel) {
		this.advancedPanel = advancedPanel;
	}




	public void setExpName(JTextField expName) {
		this.expName = expName;
	}




	public void setWorkspaceMenu(SetWorkspaceMenu workspaceMenu) {
		this.workspaceMenu = workspaceMenu;
	}
}
