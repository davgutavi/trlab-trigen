package alginterface;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import java.awt.Font;

@SuppressWarnings("serial")
public class SetWorkspaceMenu extends JDialog {

	
	private static final Logger LOG = LoggerFactory.getLogger(SetWorkspaceMenu.class);
	
	
	private final JPanel contentPanel = new JPanel();
	private JTextField selectedPath_f;
	
	private String selectedPath_v;

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

	public void setPaths (String nPath){
		selectedPath_f.setText(nPath);
		selectedPath_v = nPath;
	}
	
	public String getSelectedPath (){
		return selectedPath_v;
	}
	
	
	/**
	 * Create the dialog.
	 */
	public SetWorkspaceMenu() {
				
		setBounds(100, 100, 852, 166);
		
		getContentPane().setLayout(null);
		
		contentPanel.setBounds(0, 0, 0, 0);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		
		contentPanel.setLayout(null);
		
			JLabel lblWorkspace = new JLabel(SystemUtilities.getLabelProperty("workspace_tag"));
			lblWorkspace.setFont(new Font("Arial", Font.PLAIN, 12));
			lblWorkspace.setBounds(17, 21, 68, 16);
			getContentPane().add(lblWorkspace);
		
		
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(67, 64, 450, 47);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			
				JButton okButton = new JButton(SystemUtilities.getLabelProperty("ok_button"));
				okButton.setFont(new Font("Arial", Font.PLAIN, 12));
				okButton.setActionCommand("OK");
				
				
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				
				
				
				
				okButton.addActionListener(new ActionListener() {
					
					
					@Override
					public void actionPerformed(ActionEvent e) {
						selectedPath_v = selectedPath_f.getText();
						
						JButton button = (JButton)e.getSource() ;
						
						JRootPane root = button.getRootPane();
						
						JDialog dialog = (JDialog)root.getParent();
						
						dialog.dispose();
						
						LOG.debug("Selected path : "+selectedPath_v);
						
					}
				});
			
			
				JButton cancelButton = new JButton(SystemUtilities.getLabelProperty("cancel_button"));
				cancelButton.setFont(new Font("Arial", Font.PLAIN, 12));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				
				
				cancelButton.addActionListener(new ActionListener() {
					
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						JButton button = (JButton)e.getSource() ;
						
						JRootPane root = button.getRootPane();
						
						JDialog dialog = (JDialog)root.getParent();
						
						dialog.dispose();
						
						LOG.debug("Selected path : "+selectedPath_v);
						
						
					}
				});
			
			
		
		
		selectedPath_f = new JTextField();
		selectedPath_f.setFont(new Font("Arial", Font.PLAIN, 12));
		selectedPath_f.setBounds(97, 15, 584, 28);
		getContentPane().add(selectedPath_f);
		selectedPath_f.setColumns(10);
		
		selectedPath_f.setText(System.getProperty("user.home"));
		selectedPath_v = selectedPath_f.getText();
		
		
		JButton explore_button = new JButton(SystemUtilities.getLabelProperty("explore_button"));
		explore_button.setFont(new Font("Arial", Font.PLAIN, 12));
		explore_button.setBounds(693, 16, 117, 29);
		getContentPane().add(explore_button);
		
		
		
		explore_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fileChooser =  new JFileChooser(System.getProperty("user.home"));
				
				
								
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							
				int choice = fileChooser.showOpenDialog(explore_button);
							
				if (choice == JFileChooser.APPROVE_OPTION) {
					selectedPath_f.setText(fileChooser.getSelectedFile().getPath());
                    
				}
			}
		});
		
	}





}

