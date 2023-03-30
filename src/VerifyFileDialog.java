import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class VerifyFileDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	JPanel decryptFilePanel;
	JFileChooser fileChooser;
	PublicKey publicKey;
	JLabel selectedPublicKeyField;
	JButton btnSelectPublicKey;
	JLabel lblSelectPublicKey;
	JButton okButton;
	JLabel selectedSignatureField;
	JLabel lblSelectSignature;
	JButton btnSelectSignature;
	JLabel lblSelectedPublicKey;
	JLabel lblSelectedSig;
	File signatureFile;

	public static void main(String[] args) {
		try {
			VerifyFileDialog dialog = new VerifyFileDialog(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Dialog shown to the user for verifying that a file has been signed
	public VerifyFileDialog(File fileToBeVerified) {
		setBounds(100, 100, 450, 300);
		setTitle("Verify File");
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("Verify\r\n");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try {
							//Get the signature from stored file
							byte[] signatureData = getSignatureData(signatureFile);

							//Call the isFileSigned method to verify a file using a public key
							boolean isVerified = DigitalSigning.verifySignature(publicKey, fileToBeVerified, signatureData);
							
							//Show message based on verification status
							if(isVerified == true) {
								JOptionPane.showMessageDialog(null, "File Successfully Verified!");
							} else if(isVerified == false) {
								JOptionPane.showMessageDialog(null, "File is Not Verified!");
							}
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Verification Failed: " + e.getMessage());
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
		lblSelectedFile.setBounds(180, 11, 83, 17);
		decryptFilePanel.add(lblSelectedFile);
		
		JLabel selectedFileField = new JLabel((String) null);
		if(fileToBeVerified != null) {
			selectedFileField.setText(fileToBeVerified.toString());
		}
		selectedFileField.setHorizontalAlignment(SwingConstants.CENTER);
		selectedFileField.setForeground(Color.WHITE);
		selectedFileField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		selectedFileField.setBounds(10, 34, 416, 17);
		decryptFilePanel.add(selectedFileField);
		
		lblSelectPublicKey = new JLabel("Select public key for verifying file:");
		lblSelectPublicKey.setForeground(Color.WHITE);
		lblSelectPublicKey.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSelectPublicKey.setBounds(114, 62, 213, 17);
		decryptFilePanel.add(lblSelectPublicKey);
		
		btnSelectPublicKey = new JButton("Select Public Key");
		btnSelectPublicKey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File publicKeyFile = getPublicKeyFile();
				if (publicKeyFile != null) {
					try {
						publicKey = DigitalSigning.getDSAPublicKeyFromStorage(publicKeyFile);
						displaySelectedPublicKeyFile(publicKeyFile);
					} catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
						JOptionPane.showMessageDialog(null, "Public Key Not Found: " + e.getMessage());
					}
				}
			}
		});
		btnSelectPublicKey.setFocusable(false);
		btnSelectPublicKey.setBounds(152, 90, 142, 23);
		decryptFilePanel.add(btnSelectPublicKey);
		
		selectedPublicKeyField = new JLabel((String) null);
		selectedPublicKeyField.setHorizontalAlignment(SwingConstants.CENTER);
		selectedPublicKeyField.setForeground(Color.WHITE);
		selectedPublicKeyField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		selectedPublicKeyField.setBounds(10, 86, 416, 28);
		decryptFilePanel.add(selectedPublicKeyField);
		
		lblSelectSignature = new JLabel("Select signature for verifying file:");
		lblSelectSignature.setForeground(Color.WHITE);
		lblSelectSignature.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSelectSignature.setBounds(114, 130, 208, 17);
		decryptFilePanel.add(lblSelectSignature);
		
		btnSelectSignature = new JButton("Select Signature\r\n");
		btnSelectSignature.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				signatureFile = getSignatureFile();
				if(signatureFile != null) {
					displaySelectedSignatureFile(signatureFile);
					selectedSignatureField.setText(signatureFile.getAbsolutePath());
				}
			}
		});
		btnSelectSignature.setFocusable(false);
		btnSelectSignature.setBounds(152, 158, 142, 23);
		decryptFilePanel.add(btnSelectSignature);
		
		lblSelectedSig = new JLabel("Selected Signature File:\r\n");
		lblSelectedSig.setVisible(false);
		lblSelectedSig.setForeground(Color.WHITE);
		lblSelectedSig.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSelectedSig.setBounds(144, 130, 148, 17);
		decryptFilePanel.add(lblSelectedSig);
		
		selectedSignatureField = new JLabel((String) null);
		selectedSignatureField.setHorizontalAlignment(SwingConstants.CENTER);
		selectedSignatureField.setForeground(Color.WHITE);
		selectedSignatureField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		selectedSignatureField.setBounds(10, 158, 416, 28);
		decryptFilePanel.add(selectedSignatureField);
		
		lblSelectedPublicKey = new JLabel("Selected Public Key File:\r\n\r\n");
		lblSelectedPublicKey.setVisible(false);
		lblSelectedPublicKey.setForeground(Color.WHITE);
		lblSelectedPublicKey.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSelectedPublicKey.setBounds(142, 62, 152, 17);
		decryptFilePanel.add(lblSelectedPublicKey);
	}

	//Method that modifies the dialog to display the file of the key the user selected and the option to verify a file
	public void displaySelectedPublicKeyFile(File selectedFile) {
		btnSelectPublicKey.setVisible(false);
		lblSelectPublicKey.setVisible(false);
		
		selectedPublicKeyField.setText(selectedFile.toString());
		lblSelectedPublicKey.setVisible(true);
		
		//Check to see if both the signature and the public key have been selected
		if(!btnSelectSignature.isVisible()) {
			okButton.setVisible(true);
		}
	}
	
	//Method that modifies the dialog to display the file of the signature the user selected and the option to verify a file
	public void displaySelectedSignatureFile(File selectedFile) {
		btnSelectSignature.setVisible(false);
		lblSelectSignature.setVisible(false);
		
		selectedSignatureField.setText(selectedFile.toString());
		lblSelectedSig.setVisible(true);
		
		//Check to see if both the signature and the public key have been selected
		if(!btnSelectPublicKey.isVisible()) {
			okButton.setVisible(true);
		}
	}
	
	
	//Method that prompts the user to select a file for the signature and returns that file
	public File getSignatureFile() {
		fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Select Signature");
	    int res = fileChooser.showOpenDialog(null);
	    if (res == JFileChooser.APPROVE_OPTION) {
	      return fileChooser.getSelectedFile();   
	    }
	    return null;
	}
	
	//Method that prompts the user to select a file for the Public Key and returns that file
	public File getPublicKeyFile() {
		fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Select Public Key");
	    int res = fileChooser.showOpenDialog(null);
	    if (res == JFileChooser.APPROVE_OPTION) {
	      return fileChooser.getSelectedFile();   
	    }
	    return null;
	}
	
	//Method that reads the signature stored in a file
	public byte[] getSignatureData(File signatureFile) {
		try {
			return Files.readAllBytes(signatureFile.toPath());
		} 
		catch (IOException e) {
			throw new RuntimeException("Error reading file: " + signatureFile.getAbsolutePath(), e);
		}
	}
	
}
