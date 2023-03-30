import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Key;
import java.security.KeyPair;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import org.bouncycastle.util.encoders.Hex;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

public class SaveKeyPairDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	JTextArea privateKeyLabel = new JTextArea();
	JButton okButton;
	JFileChooser fileChooser = new JFileChooser();
	JTextArea publicKeyLabel;
	
	String hexPrivateKey;
	String hexPublicKey;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SaveKeyPairDialog dialog = new SaveKeyPairDialog(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog for storing Asymmetric Key Pair.
	 */
	public SaveKeyPairDialog(KeyPair keyPair) {
		setTitle("Save Key Pair\r\n");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("Save");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						savePrivateKeyDialog(keyPair.getPrivate());
						savePublicKeyDialog(keyPair.getPublic());
						dispose();
					}
				});
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
			JLabel displayPrivateKeyLabel = new JLabel("Private Key:");
			displayPrivateKeyLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
			displayPrivateKeyLabel.setForeground(new Color(255, 255, 255));
			displayPrivateKeyLabel.setBounds(181, 5, 74, 17);
			saveKeyPanel.add(displayPrivateKeyLabel);
		}
		{
			JScrollPane scrollPanePrivKey = new JScrollPane();
			scrollPanePrivKey.setBounds(10, 24, 416, 89);
			scrollPanePrivKey.setBorder(null);
			
			saveKeyPanel.add(scrollPanePrivKey);
			{
				privateKeyLabel = new JTextArea();
				if(keyPair != null) {
					Key privateKey = keyPair.getPrivate();
					String hexPrivateKey = Hex.toHexString(privateKey.getEncoded());
					privateKeyLabel.setText(hexPrivateKey);
				}
				privateKeyLabel.setEnabled(true);
				privateKeyLabel.setEditable(false);
				privateKeyLabel.setBackground(new Color(0, 0, 102));
				scrollPanePrivKey.setViewportView(privateKeyLabel);
				privateKeyLabel.setLineWrap(true);
				privateKeyLabel.setForeground(Color.WHITE);
				privateKeyLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));			
			}
		}
		
		JLabel displayPublicKeyLabel = new JLabel("Public Key:");
		displayPublicKeyLabel.setForeground(Color.WHITE);
		displayPublicKeyLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		displayPublicKeyLabel.setBounds(181, 113, 74, 17);
		saveKeyPanel.add(displayPublicKeyLabel);
		
		JScrollPane scrollPanePubKey = new JScrollPane();
		scrollPanePubKey.setBorder(null);
		scrollPanePubKey.setBounds(10, 130, 416, 89);
		saveKeyPanel.add(scrollPanePubKey);
		{
			publicKeyLabel = new JTextArea();
			if(keyPair != null) {
				Key publicKey = keyPair.getPublic();
				String hexPublicKey = Hex.toHexString(publicKey.getEncoded());
				publicKeyLabel.setText(hexPublicKey);
			}
			publicKeyLabel.setLineWrap(true);
			publicKeyLabel.setForeground(Color.WHITE);
			publicKeyLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
			publicKeyLabel.setEnabled(true);
			publicKeyLabel.setEditable(false);
			publicKeyLabel.setBackground(new Color(0, 0, 102));
			scrollPanePubKey.setViewportView(publicKeyLabel);
		}
	}
	
	//Method that handles the dialog for saving a private key in PEM format
	public void savePrivateKeyDialog(Key privateKey) {
		//Setting the title of the file chooser
		fileChooser.setDialogTitle("Save Private Key");
		
	    //Dialog for selecting where file is stored
	    int userSelection = fileChooser.showSaveDialog(null);

        // If the user selected a file, save the public key to it
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String filePath = file.getAbsolutePath();
            if (!filePath.endsWith(".pem")) {
                filePath += ".pem";
            }
            try {
            	savePrivateKey(filePath, privateKey);
			} catch (IOException e) {
				e.printStackTrace();
			}
            JOptionPane.showMessageDialog(null, "Private key saved to " + filePath);
        }
	}
	
	//Method that handles the dialog for saving a public key in PEM format
	public void savePublicKeyDialog(Key publicKey) {
		//Setting the title of the file chooser
		fileChooser.setDialogTitle("Save Public Key");
		
		//Dialog for selecting where file is stored
		int userSelection = fileChooser.showSaveDialog(null);

        // If the user selected a file, save the public key to it
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String filePath = file.getAbsolutePath();
            if (!filePath.endsWith(".pem")) {
                filePath += ".pem";
            }
            try {
            	savePublicKey(filePath, publicKey);
			} catch (IOException e) {
				e.printStackTrace();
			}
            JOptionPane.showMessageDialog(null, "Public key saved to " + filePath);
        }
		
	}
	
	public static void savePublicKey(String fileName, Key key) throws IOException {
		try (PemWriter pemWriter = new PemWriter(new FileWriter(fileName))) {
			pemWriter.writeObject(new PemObject("PUBLIC KEY", key.getEncoded()));
	    }
	}
	
	public static void savePrivateKey(String fileName, Key key) throws IOException {
		try (PemWriter pemWriter = new PemWriter(new FileWriter(fileName))) {
			pemWriter.writeObject(new PemObject("PRIVATE KEY", key.getEncoded()));
	    }
	}
	
	
}
