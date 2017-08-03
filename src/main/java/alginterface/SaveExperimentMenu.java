package alginterface;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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

import input.algorithm.InvalidImplementationException;
import input.algorithm.WrongContolException;
import utils.SystemUtilities;
import utils.TextUtilities;
import java.awt.Color;
import java.awt.SystemColor;

@SuppressWarnings("serial")
public class SaveExperimentMenu extends JDialog {

	private static final Logger LOG = LoggerFactory.getLogger(SaveExperimentMenu.class);

	private static final String EXT = ".tricfg";

	private final JPanel contentPanel = new JPanel();

	private JTextField selectedPath_f;

	
	
	
	
	private ControlPanel control_panel;
	
	private AdvancedPanel advanced_panel;
	
	private String expName;
	
	private String workspacePath;
	private JTextField textField;
	

	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SaveExperimentMenu dialog = new SaveExperimentMenu("test");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SaveExperimentMenu(String experimetName) {
		getContentPane().setFont(new Font("Arial", Font.PLAIN, 12));

		this.expName = experimetName;
		setBounds(100, 100, 845, 178);

		getContentPane().setLayout(null);

		contentPanel.setBounds(0, 0, 0, 0);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);

		contentPanel.setLayout(null);

		JLabel lblWorkspace = new JLabel(SystemUtilities.getLabelProperty("save_as_tag"));
		lblWorkspace.setFont(new Font("Arial", Font.PLAIN, 12));
		lblWorkspace.setBounds(17, 21, 142, 16);
		getContentPane().add(lblWorkspace);

		JPanel buttonPane = new JPanel();
		buttonPane.setBounds(67, 94, 450, 39);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane);
		
		JButton cancelButton = new JButton(SystemUtilities.getLabelProperty("cancel_button"));
		cancelButton.setFont(new Font("Arial", Font.PLAIN, 12));
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

		JButton saveButton = new JButton(SystemUtilities.getLabelProperty("save_button"));
		saveButton.setFont(new Font("Arial", Font.PLAIN, 12));
		saveButton.setActionCommand("OK");
		
		JButton okButton = new JButton("OK");
		okButton.setFont(new Font("Arial", Font.PLAIN, 12));
		buttonPane.add(okButton);
		okButton.setEnabled(false);

		buttonPane.add(saveButton);
		getRootPane().setDefaultButton(saveButton);

		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				LOG.debug("Saving control");
					try {
						GuiLauncher.saveControl(control_panel, advanced_panel, workspacePath, expName, selectedPath_f.getText());
					} catch (WrongContolException | InvalidImplementationException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			

				textField.setText(SystemUtilities.getLabelProperty("saved_text")+selectedPath_f.getText());
				cancelButton.setEnabled(false);
				okButton.setEnabled(true);
					
				LOG.debug("Selected path : " +  selectedPath_f.getText());

			}
		});

		
		
		
				
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				JButton button = (JButton) e.getSource();

				JRootPane root = button.getRootPane();

				JDialog dialog = (JDialog) root.getParent();

				dialog.dispose();
				
			}
		});

		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JButton button = (JButton) e.getSource();

				JRootPane root = button.getRootPane();

				JDialog dialog = (JDialog) root.getParent();

				dialog.dispose();

				LOG.debug("Selected path : " + selectedPath_f.getText());

			}
		});

		selectedPath_f = new JTextField();
		selectedPath_f.setFont(new Font("Arial", Font.PLAIN, 12));
		selectedPath_f.setBounds(115, 15, 584, 28);
		getContentPane().add(selectedPath_f);
		selectedPath_f.setColumns(10);

		String aux = TextUtilities.getCorrectPathFromHome(expName + EXT);

		selectedPath_f.setText(aux);

		JButton explore_button = new JButton(SystemUtilities.getLabelProperty("explore_button"));
		explore_button.setFont(new Font("Arial", Font.PLAIN, 12));
		explore_button.setBounds(711, 16, 117, 29);
		getContentPane().add(explore_button);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBackground(SystemColor.window);
		textField.setForeground(Color.BLUE);
		textField.setFont(new Font("Arial", Font.PLAIN, 12));
		textField.setBounds(125, 55, 574, 28);
		getContentPane().add(textField);
		textField.setColumns(10);
		textField.setBorder(null);

		explore_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home"));

				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

				int choice = fileChooser.showOpenDialog(explore_button);

				if (choice == JFileChooser.APPROVE_OPTION) {

					String auxRoot = fileChooser.getSelectedFile().getPath();

					String aux = TextUtilities.appendToPath(auxRoot, expName + EXT);

					selectedPath_f.setText(aux);

				}
			}
		});

	}
	
	public void resetText (){
		textField.setText("");
	}

	public void setExpName(String experimentName) {
		expName = experimentName;
		String aux = TextUtilities.getCorrectPathFromHome(expName + EXT);
		selectedPath_f.setText(aux);
	}

	public void setControl_panel(ControlPanel control_panel) {
		this.control_panel = control_panel;
	}

	public void setAdvanced_panel(AdvancedPanel advanced_panel) {
		this.advanced_panel = advanced_panel;
	}

	public void setWorkspacePath(String workspacePath) {
		this.workspacePath = workspacePath;
	}
}
