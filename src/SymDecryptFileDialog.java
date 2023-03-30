import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.crypto.SecretKey;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class SymDecryptFileDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	JPanel decryptFilePanel;
	JLabel selectedFileField;
	JLabel lblSelectedFile;
	JLabel selectedKeyField;
	JButton decryptButton;
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
			SymDecryptFileDialog dialog = new SymDecryptFileDialog(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog for Symmetric Decryption.
	 */
	public SymDecryptFileDialog(File filepath) {
		setTitle("Decrypt File\r\n");
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
				decryptButton = new JButton("Decrypt");
				decryptButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						//Get the password typed by the user
						String userPassword = keyPasswordField.getText();
						SecretKey key = null;
						 
						try {
							key = SymmetricEncryption.getKeyFromStorage(keyPath.toString(), userPassword);
							if(key != null && filepath != null) {
								SymmetricEncryption.decryptFile(key, filepath);
								showSuccessfulDecryption();
							}
						} catch (Exception e) {
							JOptionPane.showMessageDialog(decryptFilePanel, "Decryption Failed! " + e);
							dispose();
						} 
					}
				});
				decryptButton.setActionCommand("OK");
				decryptButton.setFocusable(false);
				buttonPane.add(decryptButton);
				getRootPane().setDefaultButton(decryptButton);
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
		decryptFilePanel = new JPanel();
		getContentPane().add(decryptFilePanel, BorderLayout.CENTER);
		decryptFilePanel.setForeground(new Color(255, 255, 255));
		decryptFilePanel.setBackground(new Color(0, 0, 102));
		decryptFilePanel.setLayout(null);
		{
			{
				lblSelectedFile = new JLabel("Selected File:");
				lblSelectedFile.setForeground(Color.WHITE);
				lblSelectedFile.setFont(new Font("Tahoma", Font.PLAIN, 14));
				lblSelectedFile.setBounds(176, 26, 83, 17);
				decryptFilePanel.add(lblSelectedFile);
			}
			{
				selectedFileField = new JLabel(filepath.toString());
				selectedFileField.setHorizontalAlignment(SwingConstants.CENTER);
				selectedFileField.setForeground(Color.WHITE);
				selectedFileField.setFont(new Font("Tahoma", Font.PLAIN, 12));
				selectedFileField.setBounds(10, 54, 416, 17);
				decryptFilePanel.add(selectedFileField);
			}
		}
		{
			lblSelectKey = new JLabel("Select key for decrypting file:");
			lblSelectKey.setForeground(Color.WHITE);
			lblSelectKey.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblSelectKey.setBounds(126, 95, 183, 17);
			decryptFilePanel.add(lblSelectKey);
		}
		{
			selectKeyButton = new JButton("Select Key");
			selectKeyButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					 JFileChooser fileChooser = new JFileChooser();	
					 fileChooser.setDialogTitle("Select Key For Decryption");
					 fileChooser.showOpenDialog(selectKeyButton);
					 //User selects key they want to use to decrypt
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
			decryptFilePanel.add(selectKeyButton);
		}
		{
			selectedKeyField = new JLabel((String) null);
			selectedKeyField.setHorizontalAlignment(SwingConstants.CENTER);
			selectedKeyField.setForeground(Color.WHITE);
			selectedKeyField.setVisible(false);
			selectedKeyField.setFont(new Font("Tahoma", Font.PLAIN, 12));
			selectedKeyField.setBounds(10, 123, 416, 17);
			decryptFilePanel.add(selectedKeyField);
		}
		{
			lblSelectedKey = new JLabel("Selected Key:\r\n");
			lblSelectedKey.setVisible(false);
			lblSelectedKey.setForeground(Color.WHITE);
			lblSelectedKey.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblSelectedKey.setBounds(176, 95, 84, 17);
			decryptFilePanel.add(lblSelectedKey);
		}
		{
			lblEnterPassword = new JLabel("Enter password to use the key:");
			lblEnterPassword.setVisible(false);
			lblEnterPassword.setForeground(Color.WHITE);
			lblEnterPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblEnterPassword.setBounds(121, 161, 194, 17);
			decryptFilePanel.add(lblEnterPassword);
		}
		{
			keyPasswordField = new JTextField();
			keyPasswordField.setVisible(false);
			keyPasswordField.setColumns(10);
			keyPasswordField.setBounds(170, 189, 96, 20);
			decryptFilePanel.add(keyPasswordField);
		}
	}
	
	public void showSuccessfulDecryption() {
		lblSelectedFile.setVisible(false);
		selectedFileField.setVisible(false);
		selectedKeyField.setVisible(false);
		decryptButton.setVisible(false);
		lblSelectedKey.setVisible(false);
		selectKeyButton.setVisible(false);
		keyPasswordField.setVisible(false);
		lblEnterPassword.setVisible(false);
		lblSelectKey.setText("File Successfully Decrypted!");
		lblSelectKey.setForeground(Color.GREEN);
		lblSelectKey.setVisible(true);
		cancelButton.setText("Close");
	}
	
	public void showKeyLocation() {
		selectedKeyField.setText(keyPath.toString());
		selectKeyButton.setVisible(false);
        lblSelectKey.setVisible(false);
		decryptButton.setEnabled(true); 
        selectedKeyField.setVisible(true);
        lblSelectedKey.setVisible(true);
        lblEnterPassword.setVisible(true);
        keyPasswordField.setVisible(true);
	}

}
