import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.crypto.SecretKey;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JRadioButtonMenuItem;
import org.bouncycastle.util.encoders.Hex;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;

public class GenerateKeyDialog extends JDialog { 
	int keySize = 128; //default key Size is set to 128bits
	String sourceOfRandomness = "Default";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			GenerateKeyDialog keyDialog = new GenerateKeyDialog();
			keyDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			keyDialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public GenerateKeyDialog() {
		setTitle("Generate Key\r\n");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		ButtonGroup keySizeGroup = new ButtonGroup();
		ButtonGroup randomnessSourceGroup = new ButtonGroup();
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton generateButton = new JButton("Generate");
				generateButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						SecretKey key = null;
						try {
							key = SymmetricEncryption.generateAESkey(keySize, sourceOfRandomness);
						} catch (NoSuchAlgorithmException e) {
							JOptionPane.showMessageDialog(null, "Key Generation Failed! " + e);
						}
					    String hexKey = Hex.toHexString(key.getEncoded());
						
						SaveKeyDialog saveKeyDialog = new SaveKeyDialog(key);
						saveKeyDialog.setLocationRelativeTo(getContentPane());
						saveKeyDialog.setKey(hexKey); 
						saveKeyDialog.setVisible(true);	  
						
						dispose();
					}
				});
				generateButton.setActionCommand("OK");
				buttonPane.add(generateButton);
				getRootPane().setDefaultButton(generateButton);
				generateButton.setFocusable(false);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.setFocusable(false);
				
			}
		}
		
		JRadioButtonMenuItem keySize128 = new JRadioButtonMenuItem("128 bits");
		keySize128.setSelected(true);
		keySize128.setBounds(55, 96, 97, 23);
		keySize128.setBackground(new Color(0, 0, 102));
		keySize128.setForeground(Color.WHITE);
		keySize128.setFont(new Font("Tahoma", Font.PLAIN, 14));
		keySize128.setBorder(null);
		keySize128.setContentAreaFilled(false);
		keySize128.setFocusable(false);
		keySize128.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	keySize = 128;
		    }
		});
		keySizeGroup.add(keySize128);
		
		
		JRadioButtonMenuItem keySize192 = new JRadioButtonMenuItem("192 bits");
		keySize192.setBounds(55, 130, 97, 23);
		keySize192.setBackground(new Color(0, 0, 102));
		keySize192.setForeground(Color.WHITE);
		keySize192.setFont(new Font("Tahoma", Font.PLAIN, 14));
		keySize192.setBorder(null);
		keySize192.setContentAreaFilled(false);
		keySize192.setFocusable(false);
		keySize192.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	keySize = 192;
		    }
		});
		keySizeGroup.add(keySize192);
		
		JRadioButtonMenuItem keySize256 = new JRadioButtonMenuItem("256 bits");
		keySize256.setBounds(55, 164, 97, 23);
		keySize256.setBackground(new Color(0, 0, 102));
		keySize256.setForeground(Color.WHITE);
		keySize256.setFont(new Font("Tahoma", Font.PLAIN, 14));
		keySize256.setBorder(null);
		keySize256.setContentAreaFilled(false);
		keySize256.setFocusable(false);
		keySize256.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	keySize = 256;
		    }
		});
		keySizeGroup.add(keySize256);
		
		JPanel keySizePanel = new JPanel();
		getContentPane().add(keySizePanel, BorderLayout.CENTER);
		keySizePanel.setForeground(new Color(255, 255, 255));
		keySizePanel.setBackground(new Color(0, 0, 102));
		keySizePanel.setLayout(null);
		keySizePanel.add(keySize128);
		keySizePanel.add(keySize192);
		keySizePanel.add(keySize256);
		
		JLabel selectSizeLabel = new JLabel("Key Size:");
		selectSizeLabel.setForeground(Color.WHITE);
		selectSizeLabel.setFont(new Font("Tahoma", Font.PLAIN, 19));
		selectSizeLabel.setBounds(55, 40, 77, 23);
		keySizePanel.add(selectSizeLabel);
		
		JLabel selectRandomnessLabel = new JLabel("Source of Randomness:");
		selectRandomnessLabel.setForeground(Color.WHITE);
		selectRandomnessLabel.setFont(new Font("Tahoma", Font.PLAIN, 19));
		selectRandomnessLabel.setBounds(225, 40, 201, 23);
		keySizePanel.add(selectRandomnessLabel);
		
		JRadioButtonMenuItem sourceDefault = new JRadioButtonMenuItem("Default\r\n");
		sourceDefault.setSelected(true);
		sourceDefault.setForeground(Color.WHITE);
		sourceDefault.setFont(new Font("Tahoma", Font.PLAIN, 14));
		sourceDefault.setFocusable(false);
		sourceDefault.setContentAreaFilled(false);
		sourceDefault.setBorder(null);
		sourceDefault.setBackground(new Color(0, 0, 102));
		sourceDefault.setBounds(285, 96, 97, 23);
		sourceDefault.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	sourceOfRandomness = "Default";
		    }
		});
		keySizePanel.add(sourceDefault);
		
		JRadioButtonMenuItem sourceDRBG = new JRadioButtonMenuItem("DRBG\r\n");
		sourceDRBG.setForeground(Color.WHITE);
		sourceDRBG.setFont(new Font("Tahoma", Font.PLAIN, 14));
		sourceDRBG.setFocusable(false);
		sourceDRBG.setContentAreaFilled(false);
		sourceDRBG.setBorder(null);
		sourceDRBG.setBackground(new Color(0, 0, 102));
		sourceDRBG.setBounds(285, 130, 97, 23);
		sourceDRBG.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	sourceOfRandomness = "DRBG";
		    }
		});
		keySizePanel.add(sourceDRBG);
		
		JRadioButtonMenuItem sourceSHA1PRNG = new JRadioButtonMenuItem("SHA1PRNG");
		sourceSHA1PRNG.setForeground(Color.WHITE);
		sourceSHA1PRNG.setFont(new Font("Tahoma", Font.PLAIN, 14));
		sourceSHA1PRNG.setFocusable(false);
		sourceSHA1PRNG.setContentAreaFilled(false);
		sourceSHA1PRNG.setBorder(null);
		sourceSHA1PRNG.setBackground(new Color(0, 0, 102));
		sourceSHA1PRNG.setBounds(285, 164, 117, 23);
		sourceSHA1PRNG.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	sourceOfRandomness = "SHA1PRNG";
		    }
		});
		keySizePanel.add(sourceSHA1PRNG);
		
		randomnessSourceGroup.add(sourceDefault);
		randomnessSourceGroup.add(sourceDRBG);
		randomnessSourceGroup.add(sourceSHA1PRNG);
	}
}
