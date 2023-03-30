import java.awt.Button;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.bouncycastle.util.encoders.Hex;

public class DigSignGUI {

	JFrame digSignFrame;
	JFileChooser fileChooser;
	SignFileDialog SignFileDialog; //Dialog for signing a file
	VerifyFileDialog verifyFileDialog; //Dialog for verifying a signed file

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DigSignGUI window = new DigSignGUI();
					window.digSignFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DigSignGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		digSignFrame = new JFrame();
		digSignFrame.setResizable(false);
		digSignFrame.getContentPane().setBackground(new Color(0, 0, 51));
		digSignFrame.setTitle("CryptoTool");
		digSignFrame.setBounds(100, 100, 1000, 650);
		digSignFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		digSignFrame.setLocationRelativeTo(null);
		digSignFrame.getContentPane().setLayout(null);
		
		//Label showing the page name
		JLabel pageLabel = new JLabel("Digital Signing");
		pageLabel.setBounds(417, 11, 152, 51);
		pageLabel.setForeground(new Color(255, 255, 255));
		pageLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		digSignFrame.getContentPane().add(pageLabel);
		
		//Label asking the user to select an action
		JLabel selectLabel = new JLabel("Please select an action:");
		selectLabel.setBounds(383, 212, 220, 51);
		selectLabel.setForeground(new Color(255, 255, 255));
		selectLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		digSignFrame.getContentPane().add(selectLabel);
		
		//Button for Generating a DSA key pair
		Button generateKeyBtn = new Button("Generate Key Pair");
		generateKeyBtn.setBounds(200, 373, 192, 56);
		generateKeyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Display the keyDialog when Generate Key button is clicked
				GenerateDSAKeyPairDialog generateDSAKeyPairDialog = new GenerateDSAKeyPairDialog();
				generateDSAKeyPairDialog.setLocationRelativeTo(digSignFrame);
				generateDSAKeyPairDialog.setVisible(true);
			}
		});
		generateKeyBtn.setFocusable(false);
		generateKeyBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		generateKeyBtn.setBackground(new Color(70, 130, 180));
		generateKeyBtn.setForeground(new Color(255, 255, 255));
		digSignFrame.getContentPane().add(generateKeyBtn);
		
		//Button for Displaying a Private Key
		Button viewPrivateKeyBtn = new Button("View Private Key");
		viewPrivateKeyBtn.setBounds(95, 473, 192, 56);
		viewPrivateKeyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displayPrivateKey();
			}
		});
		viewPrivateKeyBtn.setFocusable(false);
		viewPrivateKeyBtn.setForeground(Color.WHITE);
		viewPrivateKeyBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		viewPrivateKeyBtn.setBackground(new Color(70, 130, 180));
		digSignFrame.getContentPane().add(viewPrivateKeyBtn);	
		
		//Button for Signing a File
		Button signFileBtn = new Button("Sign File");
		signFileBtn.setBounds(592, 373, 192, 56);
		signFileBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Prompt user to select the file they want to Sign
				fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Select File to Sign");
				int res = fileChooser.showOpenDialog(null);
				if (res == JFileChooser.APPROVE_OPTION) {
					//User selects file they want to Sign
					File fileToBeSigned = fileChooser.getSelectedFile();
					if(fileToBeSigned != null) {
						//If user has selected a file, open a dialog for Signing the selected file
						SignFileDialog = new SignFileDialog(fileToBeSigned);
						SignFileDialog.setLocationRelativeTo(null);
						SignFileDialog.setVisible(true);
					}
				}						 
			}
		});
		signFileBtn.setFocusable(false);
		signFileBtn.setForeground(Color.WHITE);
		signFileBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		signFileBtn.setBackground(new Color(65, 105, 225));
		digSignFrame.getContentPane().add(signFileBtn);
		
		//Button for Verifying a file
		Button verifyFileBtn = new Button("Verify File");
		verifyFileBtn.setBounds(592, 473, 192, 56);
		verifyFileBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//User selects file they want to verify
				File fileToBeVerified = getFileToSign();
				if(fileToBeVerified != null) {
					//If user has selected a file, open a dialog for verifying the selected file
					verifyFileDialog = new VerifyFileDialog(fileToBeVerified);
					verifyFileDialog.setLocationRelativeTo(null);
					verifyFileDialog.setVisible(true);
				}
			}
		});
		verifyFileBtn.setFocusable(false);
		verifyFileBtn.setForeground(Color.WHITE);
		verifyFileBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		verifyFileBtn.setBackground(new Color(65, 105, 225));
		digSignFrame.getContentPane().add(verifyFileBtn);
		
		//Button for going back to WelcomeGUI
		Button backBtn = new Button("Back");
		backBtn.setBounds(871, 11, 105, 25);
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				WelcomeGUI welcomeGUI = new WelcomeGUI();
				welcomeGUI.welcomeFrame.setVisible(true);
				digSignFrame.dispose();
			}
		});
		backBtn.setForeground(Color.WHITE);
		backBtn.setFocusable(false);
		backBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		backBtn.setBackground(new Color(112, 128, 144));
		digSignFrame.getContentPane().add(backBtn);
		
		//Button for Displaying a Public Key
		Button viewPublicKeyBtn = new Button("View Public Key");
		viewPublicKeyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displayPublicKey();
			}
		});
		viewPublicKeyBtn.setFocusable(false);
		viewPublicKeyBtn.setForeground(Color.WHITE);
		viewPublicKeyBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		viewPublicKeyBtn.setBackground(new Color(70, 130, 180));
		viewPublicKeyBtn.setBounds(314, 473, 192, 56);
		digSignFrame.getContentPane().add(viewPublicKeyBtn);
	}
			
	//Method that handles displaying a private key on the screen by calling ViewAsymKeyDialog
	public void displayPrivateKey() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Select Private Key");
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
		
			try {
				PrivateKey privateKey = DigitalSigning.getDSAPrivateKeyFromStorage(selectedFile);
				if(privateKey != null) {
					//Convert key to hex
					String hexPrivateKey = Hex.toHexString(privateKey.getEncoded());
					//Create a view key dialog
					ViewAsymKeyDialog viewAsymKeyDialog = new ViewAsymKeyDialog(hexPrivateKey);
					viewAsymKeyDialog.setLocationRelativeTo(digSignFrame);
					viewAsymKeyDialog.setVisible(true);
				}
			} catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
				JOptionPane.showMessageDialog(null, "Error displaying Private Key: " + e.getMessage());
			}
		}		
	}
	
	//Method that handles displaying a public key on the screen by calling ViewAsymKeyDialog
	public void displayPublicKey() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Select Public Key");
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
		
			try {
				PublicKey publicKey = DigitalSigning.getDSAPublicKeyFromStorage(selectedFile);
				if(publicKey != null) {
					//Convert key to hex
					String hexPublicKey = Hex.toHexString(publicKey.getEncoded());
					//Create a view key dialog
					ViewAsymKeyDialog viewAsymKeyDialog = new ViewAsymKeyDialog(hexPublicKey);
					viewAsymKeyDialog.setLocationRelativeTo(digSignFrame);
					viewAsymKeyDialog.setVisible(true);
				}
			} catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
				JOptionPane.showMessageDialog(null, "Error displaying Public Key: " + e.getMessage());
			}
		}
	}
	
	//Method that prompts the user to select the file where the private key is stored
	public File getFileToSign() {	
		fileChooser = new JFileChooser();
		
		fileChooser.setDialogTitle("Select File to Verify");
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile();
		}
		return null;
	}
	
}
