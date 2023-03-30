import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;

public class GenerateDSAKeyPairDialog extends JDialog {

	int keySize = 512; //default key size
	String sourceOfRandomness = "Default"; 
	JFileChooser fileChooser = new JFileChooser();
	JPanel keyChoicePanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			GenerateDSAKeyPairDialog dialog = new GenerateDSAKeyPairDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog allowing the user to generate a DSA Key Pair.
	 */
	public GenerateDSAKeyPairDialog() {
		setTitle("Generate DSA Key Pair\r\n");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		ButtonGroup keySizeGroup = new ButtonGroup();
		ButtonGroup randomnessSourceGroup = new ButtonGroup();
		{
			{
				JPanel buttonPane = new JPanel();
				buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
				getContentPane().add(buttonPane, BorderLayout.SOUTH);
				{
					JButton generateButton = new JButton("Generate");
					generateButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							KeyPair keyPair = null;
							try {
								//Generate DSA Key Pair
								keyPair = DigitalSigning.generateDSAKeyPair(keySize, sourceOfRandomness);
								//Generate the dialog for saving the key pair
								SaveKeyPairDialog saveKeyPairDialog = new SaveKeyPairDialog(keyPair);
								saveKeyPairDialog.setLocationRelativeTo(keyChoicePanel);
								saveKeyPairDialog.setVisible(true);
							} catch (NoSuchAlgorithmException e) {
								JOptionPane.showMessageDialog(null, "Error Generating DSA Key Pair: " + e.getMessage());
							}
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
			
			//Option for generating 512 bits Key Pair
			JRadioButtonMenuItem keySize512 = new JRadioButtonMenuItem("512 bits");
			keySize512.setSelected(true);
			keySize512.setBounds(54, 79, 97, 23);
			keySize512.setBackground(new Color(0, 0, 102));
			keySize512.setForeground(Color.WHITE);
			keySize512.setFont(new Font("Tahoma", Font.PLAIN, 14));
			keySize512.setBorder(null);
			keySize512.setContentAreaFilled(false);
			keySize512.setFocusable(false);
			keySize512.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			    	keySize = 512;
			    }
			});
			keySizeGroup.add(keySize512);
			
			//Option for generating 1024 bits Key Pair
			JRadioButtonMenuItem keySize1024 = new JRadioButtonMenuItem("1024 bits");
			keySize1024.setBounds(54, 113, 97, 23);
			keySize1024.setBackground(new Color(0, 0, 102));
			keySize1024.setForeground(Color.WHITE);
			keySize1024.setFont(new Font("Tahoma", Font.PLAIN, 14));
			keySize1024.setBorder(null);
			keySize1024.setContentAreaFilled(false);
			keySize1024.setFocusable(false);
			keySize1024.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			    	keySize = 1024;
			    }
			});
			keySizeGroup.add(keySize1024);
			
			keyChoicePanel = new JPanel();
			getContentPane().add(keyChoicePanel, BorderLayout.CENTER);
			keyChoicePanel.setForeground(new Color(255, 255, 255));
			keyChoicePanel.setBackground(new Color(0, 0, 102));
			keyChoicePanel.setLayout(null);
			keyChoicePanel.add(keySize512);
			keyChoicePanel.add(keySize1024);
			
			JLabel selectSizeLabel = new JLabel("Key Size:");
			selectSizeLabel.setForeground(Color.WHITE);
			selectSizeLabel.setFont(new Font("Tahoma", Font.PLAIN, 19));
			selectSizeLabel.setBounds(54, 39, 77, 23);
			keyChoicePanel.add(selectSizeLabel);
			
			JRadioButtonMenuItem sourceDefault = new JRadioButtonMenuItem("Default\r\n");
			sourceDefault.setSelected(true);
			sourceDefault.setForeground(Color.WHITE);
			sourceDefault.setFont(new Font("Tahoma", Font.PLAIN, 14));
			sourceDefault.setFocusable(false);
			sourceDefault.setContentAreaFilled(false);
			sourceDefault.setBorder(null);
			sourceDefault.setBackground(new Color(0, 0, 102));
			sourceDefault.setBounds(280, 79, 97, 23);
			keyChoicePanel.add(sourceDefault);
			
			//Option for creating Key Pair using DRBG
			JRadioButtonMenuItem sourceDRBG = new JRadioButtonMenuItem("DRBG\r\n");
			sourceDRBG.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			    	sourceOfRandomness = "DRBG";
			    }
			});
			sourceDRBG.setForeground(Color.WHITE);
			sourceDRBG.setFont(new Font("Tahoma", Font.PLAIN, 14));
			sourceDRBG.setFocusable(false);
			sourceDRBG.setContentAreaFilled(false);
			sourceDRBG.setBorder(null);
			sourceDRBG.setBackground(new Color(0, 0, 102));
			sourceDRBG.setBounds(280, 113, 97, 23);
			keyChoicePanel.add(sourceDRBG);
			
			//Option for creating Key Pair using SHA1PRNG
			JRadioButtonMenuItem sourcePRNG = new JRadioButtonMenuItem("SHA1PRNG\r\n");
			sourcePRNG.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			    	sourceOfRandomness = "SHA1PRNG";
			    }
			});
			sourcePRNG.setForeground(Color.WHITE);
			sourcePRNG.setFont(new Font("Tahoma", Font.PLAIN, 14));
			sourcePRNG.setFocusable(false);
			sourcePRNG.setContentAreaFilled(false);
			sourcePRNG.setBorder(null);
			sourcePRNG.setBackground(new Color(0, 0, 102));
			sourcePRNG.setBounds(280, 147, 117, 23);
			keyChoicePanel.add(sourcePRNG);
			
			JLabel selectRandomnessLabel = new JLabel("Source of Randomness:");
			selectRandomnessLabel.setForeground(Color.WHITE);
			selectRandomnessLabel.setFont(new Font("Tahoma", Font.PLAIN, 19));
			selectRandomnessLabel.setBounds(222, 31, 204, 39);
			keyChoicePanel.add(selectRandomnessLabel);
			
			randomnessSourceGroup.add(sourceDefault);
			randomnessSourceGroup.add(sourceDRBG);
			randomnessSourceGroup.add(sourcePRNG);
		}	
	}
}
