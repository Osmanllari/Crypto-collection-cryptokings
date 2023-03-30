import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.File;
import javax.crypto.SecretKey;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class SymEncryptFileDialog extends JDialog { 

	private final JPanel contentPanel = new JPanel();
	JPanel encryptFilePanel;
	JLabel lblSelectedFile;
	JLabel selectedFileField;
	JLabel selectedKeyField;
	JButton encryptButton;
	JLabel lblSelectedKey;
	JButton selectKeyButton;
	JLabel lblSelectKey;
	JButton cancelButton;
	
	File keyPath = null;
	private JLabel lblEnterPassword;
	private JTextField keyPasswordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SymEncryptFileDialog dialog = new SymEncryptFileDialog(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog for encrypting a file.
	 */
	public SymEncryptFileDialog(File filepath) {
		setTitle("Encrypt File\r\n");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				encryptButton = new JButton("Encrypt");
				encryptButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {	
						//Get the password typed by the user
						String userPassword = keyPasswordField.getText();
						SecretKey key = null;
						
						try {
							key = SymmetricEncryption.getKeyFromStorage(keyPath.toString(), userPassword);
							if(key != null && filepath != null) {
								SymmetricEncryption.encryptFile(key, filepath); 	
								showSuccessfulEncryption();
							} 
						}
						catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Encryption Failed! " + e);
							dispose();
						}	
					}
				});
				encryptButton.setActionCommand("OK");
				encryptButton.setEnabled(false);
				encryptButton.setFocusable(false);
				buttonPane.add(encryptButton);
				getRootPane().setDefaultButton(encryptButton);
			}
			{
				cancelButton = new JButton("Cancel");
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
		encryptFilePanel = new JPanel();
		getContentPane().add(encryptFilePanel, BorderLayout.CENTER);
		encryptFilePanel.setForeground(new Color(255, 255, 255));
		encryptFilePanel.setBackground(new Color(0, 0, 102));
		encryptFilePanel.setLayout(null);
		{
			{
				lblSelectedFile = new JLabel("Selected File:");
				lblSelectedFile.setForeground(Color.WHITE);
				lblSelectedFile.setFont(new Font("Tahoma", Font.PLAIN, 14));
				lblSelectedFile.setBounds(176, 26, 83, 17);
				encryptFilePanel.add(lblSelectedFile);
			}
			{
				selectedFileField = new JLabel(filepath.toString());
				selectedFileField.setHorizontalAlignment(SwingConstants.CENTER);
				selectedFileField.setForeground(Color.WHITE);
				selectedFileField.setFont(new Font("Tahoma", Font.PLAIN, 12));
				selectedFileField.setBounds(10, 54, 416, 17);
				encryptFilePanel.add(selectedFileField);
			}
		}
		{
			lblSelectKey = new JLabel("Select key for encrypting file:");
			lblSelectKey.setForeground(Color.WHITE);
			lblSelectKey.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblSelectKey.setBounds(126, 95, 183, 17);
			encryptFilePanel.add(lblSelectKey);
		}
		{
			selectKeyButton = new JButton("Select Key");
			selectKeyButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					 JFileChooser fileChooser = new JFileChooser();	
					 fileChooser.setDialogTitle("Select Key for Encryption");
					 fileChooser.showOpenDialog(selectKeyButton);
					 //User selects file they want to encrypt
					 keyPath = fileChooser.getSelectedFile();
					 
					//If user selects a key, display the location of the key
					 if(keyPath != null) { 
						 showKeyLocation();
					 }
					 else {
						 dispose();
					 }
				}
			});
			selectKeyButton.setFocusable(false);
			selectKeyButton.setBounds(162, 127, 112, 23);
			encryptFilePanel.add(selectKeyButton);
		}
		{
			selectedKeyField = new JLabel((String) null);
			selectedKeyField.setHorizontalAlignment(SwingConstants.CENTER);
			selectedKeyField.setForeground(Color.WHITE);
			selectedKeyField.setVisible(false);
			selectedKeyField.setFont(new Font("Tahoma", Font.PLAIN, 12));
			selectedKeyField.setBounds(10, 123, 416, 17);
			encryptFilePanel.add(selectedKeyField);
		}
		{
			lblSelectedKey = new JLabel("Selected Key:\r\n");
			lblSelectedKey.setVisible(false);
			lblSelectedKey.setForeground(Color.WHITE);
			lblSelectedKey.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblSelectedKey.setBounds(176, 95, 84, 17);
			encryptFilePanel.add(lblSelectedKey);
		}
		{
			lblEnterPassword = new JLabel("Enter password to use the key:");
			lblEnterPassword.setVisible(false);
			lblEnterPassword.setForeground(Color.WHITE);
			lblEnterPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblEnterPassword.setBounds(121, 161, 194, 17);
			encryptFilePanel.add(lblEnterPassword);
		}
		{
			keyPasswordField = new JTextField();
			keyPasswordField.setVisible(false);
			keyPasswordField.setColumns(10);
			keyPasswordField.setBounds(170, 189, 96, 20);
			encryptFilePanel.add(keyPasswordField);
		}
	}
	
	//Method modifying the dialog to show that encryption was successful
	public void showSuccessfulEncryption() {
		lblSelectedFile.setVisible(false);
		selectedFileField.setVisible(false);
		selectedKeyField.setVisible(false);
		encryptButton.setVisible(false);
		lblSelectedKey.setVisible(false);
		selectKeyButton.setVisible(false);
		keyPasswordField.setVisible(false);
		lblEnterPassword.setVisible(false);
		lblSelectKey.setText("File Successfully Encrypted!");
		lblSelectKey.setForeground(Color.GREEN);
		lblSelectKey.setVisible(true);
		cancelButton.setText("Close");
	}
	
	//Method modifying the dialog to show the location of selected key
	public void showKeyLocation() {
		selectedKeyField.setText(keyPath.toString());
		selectKeyButton.setVisible(false);
        lblSelectKey.setVisible(false);
		encryptButton.setEnabled(true); 
        selectedKeyField.setVisible(true);
        lblSelectedKey.setVisible(true);
        lblEnterPassword.setVisible(true);
        keyPasswordField.setVisible(true); 
	}
	

}
