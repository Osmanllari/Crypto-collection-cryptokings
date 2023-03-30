import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AsymDecryptFileDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	JPanel decryptFilePanel;
	JFileChooser fileChooser;
	PrivateKey privateKey;
	JLabel selectedPrivateKeyFile;
	JButton btnSelectPrivateKey;
	JLabel lblSelectPrivateKey;
	JLabel lblSelectedPrivKeyFile;
	JButton okButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AsymDecryptFileDialog dialog = new AsymDecryptFileDialog(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog allowing the user to decrypt a file.
	 */
	public AsymDecryptFileDialog(File fileToBeDecrypted) {
		setBounds(100, 100, 450, 300);
		setTitle("Asymmetric Decryption");
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("Decrypt\r\n");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try {
							//Call the decryptFile method to decrypt a file using a private key
							AsymmetricEncryption.decryptFile(privateKey, fileToBeDecrypted);
							//Show success message
							JOptionPane.showMessageDialog(null, "File Successfully Decrypted!");
						} catch (Exception e) {
							//In case the decryption fails, show error message
							JOptionPane.showMessageDialog(null, "Decryption Failed: " + e.getMessage());
						}
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				okButton.setVisible(false);
				okButton.setFocusable(false);
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
		decryptFilePanel = new JPanel();
		getContentPane().add(decryptFilePanel, BorderLayout.CENTER);
		decryptFilePanel.setForeground(new Color(255, 255, 255));
		decryptFilePanel.setBackground(new Color(0, 0, 102));
		decryptFilePanel.setLayout(null);
		
		JLabel lblSelectedFile = new JLabel("Selected File:");
		lblSelectedFile.setForeground(Color.WHITE);
		lblSelectedFile.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSelectedFile.setBounds(180, 28, 83, 17);
		decryptFilePanel.add(lblSelectedFile);
		
		JLabel selectedFileField = new JLabel((String) null);
		if(fileToBeDecrypted != null) {
			selectedFileField.setText(fileToBeDecrypted.toString());
		}
		selectedFileField.setHorizontalAlignment(SwingConstants.CENTER);
		selectedFileField.setForeground(Color.WHITE);
		selectedFileField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		selectedFileField.setBounds(10, 56, 416, 17);
		decryptFilePanel.add(selectedFileField);
		
		lblSelectPrivateKey = new JLabel("Select private key for decrypting file:");
		lblSelectPrivateKey.setForeground(Color.WHITE);
		lblSelectPrivateKey.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSelectPrivateKey.setBounds(102, 103, 231, 17);
		decryptFilePanel.add(lblSelectPrivateKey);
		
		btnSelectPrivateKey = new JButton("Select Private Key");
		btnSelectPrivateKey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Prompt the user to select a file that stores the private key
				File privateKeyFile = getPrivateKeyFile();
				
				//If the file is not empty, get the private key from storage and display the file location
				if(privateKeyFile != null)
					try {
						privateKey = AsymmetricEncryption.getPrivateKeyFromStorage(privateKeyFile);
						displaySelectedPrivateKeyFile(privateKeyFile);
					} catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
						JOptionPane.showMessageDialog(null, "Error Finding Private Key: " + e.getMessage());
					}
				}		
		});
		btnSelectPrivateKey.setFocusable(false);
		btnSelectPrivateKey.setBounds(145, 131, 142, 23);
		decryptFilePanel.add(btnSelectPrivateKey);
		
		selectedPrivateKeyFile = new JLabel((String) null);
		selectedPrivateKeyFile.setHorizontalAlignment(SwingConstants.CENTER);
		selectedPrivateKeyFile.setForeground(Color.WHITE);
		selectedPrivateKeyFile.setFont(new Font("Tahoma", Font.PLAIN, 12));
		selectedPrivateKeyFile.setBounds(10, 147, 416, 17);
		decryptFilePanel.add(selectedPrivateKeyFile);
		
		lblSelectedPrivKeyFile = new JLabel("Selected Private Key File:");
		lblSelectedPrivKeyFile.setVisible(false);
		lblSelectedPrivKeyFile.setForeground(Color.WHITE);
		lblSelectedPrivKeyFile.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSelectedPrivKeyFile.setBounds(139, 119, 157, 17);
		decryptFilePanel.add(lblSelectedPrivKeyFile);
	}

	//Method that modifies the dialog to display the file of the key the user selected and the option to encrypt file
	public void displaySelectedPrivateKeyFile(File selectedFile) {
		selectedPrivateKeyFile.setText(selectedFile.toString());
		btnSelectPrivateKey.setVisible(false);
		lblSelectPrivateKey.setVisible(false);
		lblSelectedPrivKeyFile.setVisible(true);
		okButton.setVisible(true);
	}
	
	//Method that prompts the user to select the file where the private key is stored
	public File getPrivateKeyFile() {		
		fileChooser = new JFileChooser();
		
		fileChooser.setDialogTitle("Select Private Key");
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile();
		}
		return null;
	}
}
