import java.awt.Button;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.io.File;
import javax.swing.JFileChooser;

public class SymEncryptionGUI {

	JFrame symEncryptionFrame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) { 
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SymEncryptionGUI window = new SymEncryptionGUI();
					window.symEncryptionFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SymEncryptionGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		symEncryptionFrame = new JFrame();
		symEncryptionFrame.setResizable(false);
		symEncryptionFrame.getContentPane().setBackground(new Color(0, 0, 51));
		symEncryptionFrame.setTitle("CryptoTool");
		symEncryptionFrame.setBounds(100, 100, 1000, 650);
		symEncryptionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		symEncryptionFrame.setLocationRelativeTo(null);
		symEncryptionFrame.getContentPane().setLayout(null);
		
		//Label showing the page name
		JLabel pageLabel = new JLabel("Symmetric Encryption");
		pageLabel.setBounds(375, 11, 236, 51);
		pageLabel.setForeground(new Color(255, 255, 255));
		pageLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		symEncryptionFrame.getContentPane().add(pageLabel);
		
		//Label asking the user to select an action
		JLabel selectLabel = new JLabel("Please select an action:");
		selectLabel.setBounds(383, 212, 220, 51);
		selectLabel.setForeground(new Color(255, 255, 255));
		selectLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		symEncryptionFrame.getContentPane().add(selectLabel);
		
		//Button for Generating an AES key
		Button generateKeyBtn = new Button("Generate Key");
		generateKeyBtn.setBounds(200, 373, 192, 56);
		generateKeyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Use the GenerateKeyDialog class to create a keyDialog
				GenerateKeyDialog keyDialog = new GenerateKeyDialog();
				keyDialog.setLocationRelativeTo(symEncryptionFrame);
				keyDialog.setVisible(true);
			}
		});
		generateKeyBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		generateKeyBtn.setFocusable(false);
		generateKeyBtn.setBackground(new Color(70, 130, 180));
		generateKeyBtn.setForeground(new Color(255, 255, 255));
		symEncryptionFrame.getContentPane().add(generateKeyBtn);
		
		//Button for Viewing a Key
		Button viewKeyBtn = new Button("View Key");
		viewKeyBtn.setBounds(200, 473, 192, 56);
		viewKeyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();	
				fileChooser.setDialogTitle("Select Key File");
				fileChooser.showOpenDialog(symEncryptionFrame);
				//User selects file location
				File filepath = fileChooser.getSelectedFile();
				    
				if(filepath != null) {
					//Use the ViewKeyDialog class to create a viewKeyDialog
					ViewKeyDialog viewKeyDialog = new ViewKeyDialog(filepath);
					viewKeyDialog.setLocationRelativeTo(symEncryptionFrame);
					viewKeyDialog.setVisible(true);   
				}	
			}
		});
		viewKeyBtn.setForeground(Color.WHITE);
		viewKeyBtn.setFocusable(false);
		viewKeyBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		viewKeyBtn.setBackground(new Color(70, 130, 180));
		symEncryptionFrame.getContentPane().add(viewKeyBtn);
		
		//Button for Encrypting a File
		Button encryptFileBtn = new Button("Encrypt File");
		encryptFileBtn.setBounds(592, 373, 192, 56);
		encryptFileBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				JFileChooser fileChooser = new JFileChooser();	
				fileChooser.setDialogTitle("Select File to Encrypt");
				fileChooser.showOpenDialog(symEncryptionFrame);
				//User selects file they want to encrypt
				File filepath = fileChooser.getSelectedFile();
			    
				if(filepath != null) {
					//Create a dialog that allows user to encrypt selected file
					SymEncryptFileDialog symEncryptFileDialog = new SymEncryptFileDialog(filepath);
					symEncryptFileDialog.setLocationRelativeTo(symEncryptionFrame);
					symEncryptFileDialog.setVisible(true);
				}
			}
		});
		encryptFileBtn.setForeground(Color.WHITE);
		encryptFileBtn.setFocusable(false);
		encryptFileBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		encryptFileBtn.setBackground(new Color(65, 105, 225));
		symEncryptionFrame.getContentPane().add(encryptFileBtn);
		
		//Button for Decrypting a file
		Button decryptFileBtn = new Button("Decrypt File");
		decryptFileBtn.setBounds(592, 473, 192, 56);
		decryptFileBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();	
				fileChooser.setDialogTitle("Select File to Decrypt");
				fileChooser.showOpenDialog(symEncryptionFrame);
				//User selects file location
				File filepath = fileChooser.getSelectedFile();
				    
				if(filepath != null) {
					//Create a dialog that allows user to decrypt selected file
					SymDecryptFileDialog  symDecryptFileDialog = new SymDecryptFileDialog(filepath);
					symDecryptFileDialog.setLocationRelativeTo(symEncryptionFrame);
					symDecryptFileDialog.setVisible(true);
				}	
			}
		});
		decryptFileBtn.setForeground(Color.WHITE);
		decryptFileBtn.setFocusable(false);
		decryptFileBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		decryptFileBtn.setBackground(new Color(65, 105, 225));
		symEncryptionFrame.getContentPane().add(decryptFileBtn);
		
		//Button for going back to WelcomeGUI
		Button backBtn = new Button("Back");
		backBtn.setBounds(871, 11, 105, 25);
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				WelcomeGUI welcomeGUI = new WelcomeGUI();
				welcomeGUI.welcomeFrame.setVisible(true);
				symEncryptionFrame.dispose();
			}
		});
		backBtn.setForeground(Color.WHITE);
		backBtn.setFocusable(false);
		backBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		backBtn.setBackground(new Color(112, 128, 144));
		symEncryptionFrame.getContentPane().add(backBtn);
	}
	
}
