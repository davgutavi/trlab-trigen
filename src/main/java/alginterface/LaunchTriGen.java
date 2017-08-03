package alginterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import algcore.TriGen;
import input.algorithm.Control;
import utils.SystemUtilities;
import java.awt.SystemColor;

@SuppressWarnings("serial")
public class LaunchTriGen extends JDialog implements PropertyChangeListener {
	
	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(LaunchTriGen.class);

	private final JPanel contentPanel = new JPanel();

	private JProgressBar progressBar;
	private JButton okButton;
	private JButton cancelButton;
	private JTextArea message;

	private Control currentControl;
	private TriGenGuiTask task;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			LaunchTriGen dialog = new LaunchTriGen();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public LaunchTriGen() {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setBounds(100, 100, 469, 144);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		cancelButton = new JButton(SystemUtilities.getLabelProperty("cancel_button"));
		cancelButton.setFont(new Font("Arial", Font.PLAIN, 12));
		contentPanel.add(cancelButton);

		okButton = new JButton(SystemUtilities.getLabelProperty("ok_button"));
		okButton.setFont(new Font("Arial", Font.PLAIN, 12));
		okButton.setActionCommand("start");
		contentPanel.add(okButton);

		progressBar = new JProgressBar(0, 100);
		progressBar.setFont(new Font("Arial", Font.BOLD, 12));
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		contentPanel.add(progressBar);

		getContentPane().add(contentPanel, BorderLayout.CENTER);

		message = new JTextArea(2, 35);
		message.setBackground(SystemColor.window);
		contentPanel.add(message);
		message.setForeground(Color.BLUE);
		message.setEditable(false);
		message.setFont(new Font("Arial", Font.ITALIC, 12));
		message.setMargin(new Insets(5, 5, 5, 5));
		contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		
		okButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();				
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				(TriGen.getInstance()).cancel();
			}

		});

		setVisible(false);
	}

	public void launch(Control control) {
		currentControl = control;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				launchAlgorithm();
			}
		});
	}

	public void launchAlgorithm() {
		okButton.setEnabled(false);
		message.setText("");
		cancelButton.setEnabled(true);
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		task = new TriGenGuiTask(currentControl);
		task.setPanel(contentPanel);
		task.setOkButton(okButton);
		task.setCancelButton(cancelButton);
		task.setMessage(message);
		task.addPropertyChangeListener(this);
		task.execute();
	}

	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
			int progress = (Integer) evt.getNewValue();
			progressBar.setValue(progress);
		}
	}

}
