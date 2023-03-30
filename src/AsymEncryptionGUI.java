import java.awt.Button;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.bouncycastle.util.encoders.Hex;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;


public class AsymEncryptionGUI {

	JFrame asymEncryptionFrame;
	JFileChooser fileChooser;
	GenerateKeyPairDialog generateKeyPairDialog; //Dialog for generating an asymmetric key pair
	AsymEncryptFileDialog asymEncryptFileDialog; //Dialog for encrypting a file
	AsymDecryptFileDialog asymDecryptFileDialog; //Dialog for decrypting a file

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AsymEncryptionGUI window = new AsymEncryptionGUI();
					window.asymEncryptionFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AsymEncryptionGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		asymEncryptionFrame = new JFrame();
		asymEncryptionFrame.setResizable(false);
		asymEncryptionFrame.getContentPane().setBackground(new Color(0, 0, 51));
		asymEncryptionFrame.setTitle("CryptoTool");
		asymEncryptionFrame.setBounds(100, 100, 1000, 650);
		asymEncryptionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		asymEncryptionFrame.setLocationRelativeTo(null);
		asymEncryptionFrame.getContentPane().setLayout(null);
		
		//Label showing the page name
		JLabel pageLabel = new JLabel("Asymmetric Encryption");
		pageLabel.setBounds(375, 11, 236, 51);
		pageLabel.setForeground(new Color(255, 255, 255));
		pageLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		asymEncryptionFrame.getContentPane().add(pageLabel);
		
		//Label asking the user to select an action
		JLabel selectLabel = new JLabel("Please select an action:");
		selectLabel.setBounds(383, 212, 220, 51);
		selectLabel.setForeground(new Color(255, 255, 255));
		selectLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		asymEncryptionFrame.getContentPane().add(selectLabel);
		
		//Button for Generating an RSA key pair
		Button generateKeyBtn = new Button("Generate Key Pair");
		generateKeyBtn.setBounds(200, 373, 192, 56);
		generateKeyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Launch dialog for generating an RSA key pair
				generateKeyPairDialog = new GenerateKeyPairDialog();
				generateKeyPairDialog.setLocationRelativeTo(asymEncryptionFrame);
				generateKeyPairDialog.setVisible(true);
			}
		});
		generateKeyBtn.setFocusable(false);
		generateKeyBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		generateKeyBtn.setBackground(new Color(70, 130, 180));
		generateKeyBtn.setForeground(new Color(255, 255, 255));
		asymEncryptionFrame.getContentPane().add(generateKeyBtn);
		
		//Button for Viewing a Key
		Button viewPrivateKeyBtn = new Button("View Private Key");
		viewPrivateKeyBtn.setBounds(95, 473, 192, 56);
		viewPrivateKeyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displayPrivateKey();
			}
		});
		viewPrivateKeyBtn.setForeground(Color.WHITE);
		viewPrivateKeyBtn.setFocusable(false);
		viewPrivateKeyBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		viewPrivateKeyBtn.setBackground(new Color(70, 130, 180));
		asymEncryptionFrame.getContentPane().add(viewPrivateKeyBtn);
		
		
		//Button for Encrypting a File
		Button encryptFileBtn = new Button("Encrypt File");
		encryptFileBtn.setBounds(592, 373, 192, 56);
		encryptFileBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Prompt user to select the file they want to Encrypt
				fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Select File to Encrypt");
				int res = fileChooser.showOpenDialog(null);
				if (res == JFileChooser.APPROVE_OPTION) {
					//User selects file they want to encrypt
					File fileToBeEncrypted = fileChooser.getSelectedFile();
					if(fileToBeEncrypted != null) {
						//If user has selected a file, open a dialog for encrypting the selected file
						asymEncryptFileDialog = new AsymEncryptFileDialog(fileToBeEncrypted);
						asymEncryptFileDialog.setLocationRelativeTo(null);
						asymEncryptFileDialog.setVisible(true);
					}
				}						 
			}
		});
		encryptFileBtn.setForeground(Color.WHITE);
		encryptFileBtn.setFocusable(false);
		encryptFileBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		encryptFileBtn.setBackground(new Color(65, 105, 225));
		asymEncryptionFrame.getContentPane().add(encryptFileBtn);
		
		//Button for Decrypting a file
		Button decryptFileBtn = new Button("Decrypt File");
		decryptFileBtn.setBounds(592, 473, 192, 56);
		decryptFileBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Prompt user to select the file they want to Decrypt
				fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Select File to Decrypt");
				int res = fileChooser.showOpenDialog(null);
				if (res == JFileChooser.APPROVE_OPTION) {
					//User selects file they want to decrypt
					File fileToBeDecrypted = fileChooser.getSelectedFile();
					if(fileToBeDecrypted != null) {
						//If user has selected a file, open a dialog for decrypting the selected file
						asymDecryptFileDialog = new AsymDecryptFileDialog(fileToBeDecrypted);
						asymDecryptFileDialog.setLocationRelativeTo(null);
						asymDecryptFileDialog.setVisible(true);
					}
				}				
			}
		});
		decryptFileBtn.setForeground(Color.WHITE);
		decryptFileBtn.setFocusable(false);
		decryptFileBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		decryptFileBtn.setBackground(new Color(65, 105, 225));
		asymEncryptionFrame.getContentPane().add(decryptFileBtn);
		
		//Button for going back to WelcomeGUI
		Button backBtn = new Button("Back");
		backBtn.setBounds(871, 11, 105, 25);
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				WelcomeGUI welcomeGUI = new WelcomeGUI();
				welcomeGUI.welcomeFrame.setVisible(true);
				asymEncryptionFrame.dispose();
			}
		});
		backBtn.setForeground(Color.WHITE);
		backBtn.setFocusable(false);
		backBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		backBtn.setBackground(new Color(112, 128, 144));
		asymEncryptionFrame.getContentPane().add(backBtn);
		
		Button viewPublicKeyBtn = new Button("View Public Key");
		viewPublicKeyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displayPublicKey();
			}
		});
		viewPublicKeyBtn.setForeground(Color.WHITE);
		viewPublicKeyBtn.setFocusable(false);
		viewPublicKeyBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		viewPublicKeyBtn.setBackground(new Color(70, 130, 180));
		viewPublicKeyBtn.setBounds(314, 473, 192, 56);
		asymEncryptionFrame.getContentPane().add(viewPublicKeyBtn);
	}
	
	//Method that handles displaying a private key on the screen by calling ViewAsymKeyDialog
	public void displayPrivateKey() {
		File selectedFile = null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Select Private Key");
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			selectedFile = fileChooser.getSelectedFile();
			try {
				PrivateKey privateKey = AsymmetricEncryption.getPrivateKeyFromStorage(selectedFile);
				if(privateKey != null) {
					//Convert key to hex
					String hexPrivateKey = Hex.toHexString(privateKey.getEncoded());
					//Launch dialog for viewing private key
					ViewAsymKeyDialog viewAsymKeyDialog = new ViewAsymKeyDialog(hexPrivateKey);
					viewAsymKeyDialog.setLocationRelativeTo(asymEncryptionFrame);
					viewAsymKeyDialog.setVisible(true);
				}
			} catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
				JOptionPane.showMessageDialog(null, "Error displaying Private Key: " + e.getMessage());
			}
		}
	}
	
	//Method that handles displaying a public key on the screen by calling ViewAsymKeyDialog
	public void displayPublicKey() {
		File selectedFile = null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Select Public Key");
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			selectedFile = fileChooser.getSelectedFile();
			try {
				PublicKey publicKey = AsymmetricEncryption.getPublicKeyFromStorage(selectedFile);
				if(publicKey != null) {
					//Convert key to hex
					String hexPublicKey = Hex.toHexString(publicKey.getEncoded());
					//Launch dialog for viewing public key
					ViewAsymKeyDialog viewAsymKeyDialog = new ViewAsymKeyDialog(hexPublicKey);
					viewAsymKeyDialog.setLocationRelativeTo(asymEncryptionFrame);
					viewAsymKeyDialog.setVisible(true);
				}
			} catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
				JOptionPane.showMessageDialog(null, "Error displaying Public Key: " + e.getMessage());
			}
		}
	}
}
