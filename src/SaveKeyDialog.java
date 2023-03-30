import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.crypto.SecretKey;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.JTextField;

public class SaveKeyDialog extends JDialog { 

	private final JPanel contentPanel = new JPanel();
	JLabel keyLabel;
	JButton okButton;
	private JTextField passwordTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SaveKeyDialog dialog = new SaveKeyDialog(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog for storing a SecretKey.
	 */
	public SaveKeyDialog(SecretKey key) {
		setTitle("Save Key\r\n");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		//Button panel at the bottom of the JDialog
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("Save");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						JFileChooser fileChooser = new JFileChooser();
						fileChooser.setDialogTitle("Save Secret Key");
						 
					    fileChooser.showSaveDialog(null);
					    //User selects file location
					    File filepath = fileChooser.getSelectedFile();
					    //User selects password
					    String userPassword = passwordTextField.getText();
					    
					    if(filepath != null) {
						    try {
								SymmetricEncryption.saveKeyToStorage(key, userPassword, filepath.toString());
								JOptionPane.showMessageDialog(null, "Key Successfully Stored: " + filepath.toString());
							} catch (Exception e) {
								JOptionPane.showMessageDialog(null, "Error Storing Secret Key: " + e.getMessage());
							}
					    }
					    dispose();
					}
				});
				okButton.setEnabled(false);
				okButton.setFocusable(false);
				okButton.setActionCommand("Save");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				cancelButton.setFocusable(false);
				buttonPane.add(cancelButton);
			}
		}
		
		//Panel containing information about the key
		JPanel saveKeyPanel = new JPanel();
		getContentPane().add(saveKeyPanel, BorderLayout.CENTER);
		saveKeyPanel.setForeground(new Color(255, 255, 255));
		saveKeyPanel.setBackground(new Color(0, 0, 102));
		saveKeyPanel.setLayout(null);
		{
			JLabel displayKeyLabel = new JLabel("Secret Key:");
			displayKeyLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
			displayKeyLabel.setForeground(new Color(255, 255, 255));
			displayKeyLabel.setBounds(182, 18, 71, 17);
			saveKeyPanel.add(displayKeyLabel);
		}
		{
			keyLabel = new JLabel("\r\n");
			keyLabel.setHorizontalAlignment(SwingConstants.CENTER);
			keyLabel.setForeground(Color.WHITE);
			keyLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
			keyLabel.setBounds(10, 46, 416, 86);
			saveKeyPanel.add(keyLabel);
		}
		{
			JLabel selectPasswordLabel = new JLabel("Select Password To Store Key");
			selectPasswordLabel.setForeground(Color.WHITE);
			selectPasswordLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
			selectPasswordLabel.setBounds(125, 143, 185, 17);
			saveKeyPanel.add(selectPasswordLabel);
		}
		{
			passwordTextField = new JTextField();
			passwordTextField.addKeyListener(new KeyAdapter() {
				public void keyReleased(KeyEvent e) {
			        String password = passwordTextField.getText();
			        if (!password.isEmpty()) {
			            // Enable the button if the text field is not empty
			            okButton.setEnabled(true);
			        }
			        else {
			        	okButton.setEnabled(false);
			        }
			    }
			});
			passwordTextField.setColumns(10);
			passwordTextField.setBounds(170, 171, 96, 20);
			saveKeyPanel.add(passwordTextField);
		}
	}
	
	//Method for setting the label displaying the key
	public void setKey(String key) {
		keyLabel.setText(key);
	}
}
