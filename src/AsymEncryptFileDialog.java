import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AsymEncryptFileDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	JPanel encryptFilePanel;
	JFileChooser fileChooser;
	PublicKey publicKey;
	JLabel selectedPublicKeyFile;
	JButton btnSelectPublicKey;
	JLabel lblSelectPublicKey;
	JLabel lblSelectedPubKeyFile;
	JButton okButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AsymEncryptFileDialog dialog = new AsymEncryptFileDialog(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog allowing the user to encrypt a file.
	 */
	public AsymEncryptFileDialog(File fileToBeEncrypted) {
		setBounds(100, 100, 450, 300);
		setTitle("Asymmetric Encryption");
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("Encrypt\r\n");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try {
							//Call the encryptFile method to encrypt a file using a public key
							AsymmetricEncryption.encryptFile(publicKey, fileToBeEncrypted);
							//Show success message
							JOptionPane.showMessageDialog(null, "File Successfully Encrypted!");
						} catch (Exception e) {
							//In case the encryption fails, show error message
							JOptionPane.showMessageDialog(null, "Encryption Failed: " + e.getMessage());
						}
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				okButton.setFocusable(false);
				okButton.setVisible(false);
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
		encryptFilePanel = new JPanel();
		getContentPane().add(encryptFilePanel, BorderLayout.CENTER);
		encryptFilePanel.setForeground(new Color(255, 255, 255));
		encryptFilePanel.setBackground(new Color(0, 0, 102));
		encryptFilePanel.setLayout(null);
		
		JLabel lblSelectedFile = new JLabel("Selected File:");
		lblSelectedFile.setForeground(Color.WHITE);
		lblSelectedFile.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSelectedFile.setBounds(180, 28, 83, 17);
		encryptFilePanel.add(lblSelectedFile);
		
		JLabel selectedFileField = new JLabel((String) null);
		if(fileToBeEncrypted != null) {
			selectedFileField.setText(fileToBeEncrypted.toString());
		}
		selectedFileField.setHorizontalAlignment(SwingConstants.CENTER);
		selectedFileField.setForeground(Color.WHITE);
		selectedFileField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		selectedFileField.setBounds(10, 56, 416, 17);
		encryptFilePanel.add(selectedFileField);
		
		lblSelectPublicKey = new JLabel("Select public key for encrypting file:");
		lblSelectPublicKey.setForeground(Color.WHITE);
		lblSelectPublicKey.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSelectPublicKey.setBounds(105, 103, 225, 17);
		encryptFilePanel.add(lblSelectPublicKey);
		
		btnSelectPublicKey = new JButton("Select Public Key");
		btnSelectPublicKey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Prompt the user to select a file that stores the public key
				File publicKeyFile = getPublicKeyFile();
				
				//If the file is not empty, get the public key from storage and display the file location
				if(publicKeyFile != null) {
					try {
						publicKey = AsymmetricEncryption.getPublicKeyFromStorage(publicKeyFile);
						displaySelectedPublicKeyFile(publicKeyFile);
					} catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		btnSelectPublicKey.setFocusable(false);
		btnSelectPublicKey.setBounds(145, 131, 142, 23);
		encryptFilePanel.add(btnSelectPublicKey);
		
		selectedPublicKeyFile = new JLabel((String) null);
		selectedPublicKeyFile.setHorizontalAlignment(SwingConstants.CENTER);
		selectedPublicKeyFile.setForeground(Color.WHITE);
		selectedPublicKeyFile.setFont(new Font("Tahoma", Font.PLAIN, 12));
		selectedPublicKeyFile.setBounds(10, 147, 416, 17);
		encryptFilePanel.add(selectedPublicKeyFile);
		
		lblSelectedPubKeyFile = new JLabel("Selected Public Key File:");
		lblSelectedPubKeyFile.setVisible(false);
		lblSelectedPubKeyFile.setForeground(Color.WHITE);
		lblSelectedPubKeyFile.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSelectedPubKeyFile.setBounds(142, 119, 152, 17);
		encryptFilePanel.add(lblSelectedPubKeyFile);
	}

	//Method that modifies the dialog to display the file of the key the user selected and the option to encrypt file
	public void displaySelectedPublicKeyFile(File selectedFile) {
		selectedPublicKeyFile.setText(selectedFile.toString());
		btnSelectPublicKey.setVisible(false);
		lblSelectPublicKey.setVisible(false);
		lblSelectedPubKeyFile.setVisible(true);
		okButton.setVisible(true);
	}
	
	//Method that prompts the user to select the file where the private key is stored
	public File getPublicKeyFile() {		
		fileChooser = new JFileChooser();
		
		fileChooser.setDialogTitle("Select Public Key");
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile();
		}
		return null;
	}
}