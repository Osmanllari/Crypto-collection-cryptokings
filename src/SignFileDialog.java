import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class SignFileDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	JPanel signFilePanel;
	JFileChooser fileChooser;
	PrivateKey privateKey;
	JLabel selectedPrivateKeyFile;
	JButton btnSelectPrivateKey;
	JLabel lblSelectPrivateKey;
	JLabel lblSelectedPrivKeyFile;
	JButton okButton;
	SaveSignatureDialog saveSignatureDialog;
	
	public static void main(String[] args) {
		try {
			SignFileDialog dialog = new SignFileDialog(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Dialog for interacting with the user to sign a file
	public SignFileDialog(File fileToBeSigned) {
		setBounds(100, 100, 450, 300);
		setTitle("Sign File");
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("Sign\r\n");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						byte[] signatureBytes;
						try {
							//Call the signFile method to sign a file using a private key
							signatureBytes = DigitalSigning.signFile(privateKey, fileToBeSigned);
							//Show success message
							JOptionPane.showMessageDialog(null, "File Successfully Signed!");
							
							//Call the SaveSignatureDialog to prompt the user to save the generated signature
							saveSignatureDialog = new SaveSignatureDialog(signatureBytes);
							saveSignatureDialog.setLocationRelativeTo(signFilePanel);
							saveSignatureDialog.setVisible(true);
						} catch (Exception e) {
							//In case the signing fails, show error message
							JOptionPane.showMessageDialog(null, "Signing Failed: " + e.getMessage());
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
		signFilePanel = new JPanel();
		getContentPane().add(signFilePanel, BorderLayout.CENTER);
		signFilePanel.setForeground(new Color(255, 255, 255));
		signFilePanel.setBackground(new Color(0, 0, 102));
		signFilePanel.setLayout(null);
		
		JLabel lblSelectedFile = new JLabel("Selected File:");
		lblSelectedFile.setForeground(Color.WHITE);
		lblSelectedFile.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSelectedFile.setBounds(180, 28, 83, 17);
		signFilePanel.add(lblSelectedFile);
		
		//Label showing the file the user selected to sign
		JLabel selectedFileField = new JLabel((String) null);
		if(fileToBeSigned != null) {
			selectedFileField.setText(fileToBeSigned.toString());
		}
		selectedFileField.setHorizontalAlignment(SwingConstants.CENTER);
		selectedFileField.setForeground(Color.WHITE);
		selectedFileField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		selectedFileField.setBounds(10, 56, 416, 17);
		signFilePanel.add(selectedFileField);
		
		lblSelectPrivateKey = new JLabel("Select private key for signing file:");
		lblSelectPrivateKey.setForeground(Color.WHITE);
		lblSelectPrivateKey.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSelectPrivateKey.setBounds(113, 103, 209, 17);
		signFilePanel.add(lblSelectPrivateKey);
		
		btnSelectPrivateKey = new JButton("Select Private Key");
		btnSelectPrivateKey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectPrivateKey();
			}
		});
		btnSelectPrivateKey.setFocusable(false);
		btnSelectPrivateKey.setBounds(145, 131, 142, 23);
		signFilePanel.add(btnSelectPrivateKey);
		
		selectedPrivateKeyFile = new JLabel((String) null);
		selectedPrivateKeyFile.setHorizontalAlignment(SwingConstants.CENTER);
		selectedPrivateKeyFile.setForeground(Color.WHITE);
		selectedPrivateKeyFile.setFont(new Font("Tahoma", Font.PLAIN, 12));
		selectedPrivateKeyFile.setBounds(10, 147, 416, 17);
		signFilePanel.add(selectedPrivateKeyFile);
		
		lblSelectedPrivKeyFile = new JLabel("Selected Private Key File:");
		lblSelectedPrivKeyFile.setVisible(false);
		lblSelectedPrivKeyFile.setForeground(Color.WHITE);
		lblSelectedPrivKeyFile.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSelectedPrivKeyFile.setBounds(139, 119, 157, 17);
		signFilePanel.add(lblSelectedPrivKeyFile);
	}

	//Method that modifies the dialog to display the file of the key the user selected and the option to encrypt file
	public void displaySelectedPrivateKeyFile(File selectedFile) {
		selectedPrivateKeyFile.setText(selectedFile.toString());
		btnSelectPrivateKey.setVisible(false);
		lblSelectPrivateKey.setVisible(false);
		lblSelectedPrivKeyFile.setVisible(true);
		okButton.setVisible(true);
	}
	
	//Method that prompts the user to select a private key and displays the selected key
	public void selectPrivateKey() {
		File selectedFile = null;
		
		fileChooser = new JFileChooser();
		
		fileChooser.setDialogTitle("Select Private Key");
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			selectedFile = fileChooser.getSelectedFile();
			try {
				privateKey = DigitalSigning.getDSAPrivateKeyFromStorage(selectedFile);
				displaySelectedPrivateKeyFile(selectedFile);
			} catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
				JOptionPane.showMessageDialog(null, "Private Key Not Found: " + e.getMessage());
			}
		}
	}
}
