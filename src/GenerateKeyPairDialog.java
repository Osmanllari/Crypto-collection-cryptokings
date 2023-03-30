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

public class GenerateKeyPairDialog extends JDialog {

	int keySize = 512; //default key size
	String sourceOfRandomness = "Default";
	JFileChooser fileChooser = new JFileChooser();
	JPanel keyChoicePanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			GenerateKeyPairDialog dialog = new GenerateKeyPairDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public GenerateKeyPairDialog() {
		setTitle("Generate Key Pair\r\n");
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
								//Generate a RSA Key Pair
								keyPair = AsymmetricEncryption.generateRSAKeyPair(keySize, sourceOfRandomness);
								//Generate dialog for saving the Key Pair
								SaveKeyPairDialog saveKeyPairDialog = new SaveKeyPairDialog(keyPair);
								saveKeyPairDialog.setLocationRelativeTo(keyChoicePanel);
								saveKeyPairDialog.setVisible(true);
							} catch (NoSuchAlgorithmException e) {
								JOptionPane.showMessageDialog(null, "Error Generating RSA Key Pair: " + e.getMessage());
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
			
			JRadioButtonMenuItem keySize2048 = new JRadioButtonMenuItem("2048 bits");
			keySize2048.setBounds(54, 147, 97, 23);
			keySize2048.setBackground(new Color(0, 0, 102));
			keySize2048.setForeground(Color.WHITE);
			keySize2048.setFont(new Font("Tahoma", Font.PLAIN, 14));
			keySize2048.setBorder(null);
			keySize2048.setContentAreaFilled(false);
			keySize2048.setFocusable(false);
			keySize2048.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			    	keySize = 2048;
			    }
			});
			keySizeGroup.add(keySize2048);
			
			JRadioButtonMenuItem keySize4096 = new JRadioButtonMenuItem("4096 bits");
			keySize4096.setForeground(Color.WHITE);
			keySize4096.setFont(new Font("Tahoma", Font.PLAIN, 14));
			keySize4096.setFocusable(false);
			keySize4096.setContentAreaFilled(false);
			keySize4096.setBorder(null);
			keySize4096.setBackground(new Color(0, 0, 102));
			keySize4096.setBounds(54, 181, 97, 23);
			keySize4096.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			    	keySize = 4096;
			    }
			});
			keySizeGroup.add(keySize4096);
			
			keyChoicePanel = new JPanel();
			getContentPane().add(keyChoicePanel, BorderLayout.CENTER);
			keyChoicePanel.setForeground(new Color(255, 255, 255));
			keyChoicePanel.setBackground(new Color(0, 0, 102));
			keyChoicePanel.setLayout(null);
			keyChoicePanel.add(keySize512);
			keyChoicePanel.add(keySize1024);
			keyChoicePanel.add(keySize2048);
			keyChoicePanel.add(keySize4096);
			
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
			
			JRadioButtonMenuItem sourceSHA1PRNG = new JRadioButtonMenuItem("SHA1PRNG");
			sourceSHA1PRNG.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			    	sourceOfRandomness = "SHA1PRNG";
			    }
			});
			sourceSHA1PRNG.setForeground(Color.WHITE);
			sourceSHA1PRNG.setFont(new Font("Tahoma", Font.PLAIN, 14));
			sourceSHA1PRNG.setFocusable(false);
			sourceSHA1PRNG.setContentAreaFilled(false);
			sourceSHA1PRNG.setBorder(null);
			sourceSHA1PRNG.setBackground(new Color(0, 0, 102));
			sourceSHA1PRNG.setBounds(280, 147, 117, 23);
			keyChoicePanel.add(sourceSHA1PRNG);
			
			JLabel selectRandomnessLabel = new JLabel("Source of Randomness:");
			selectRandomnessLabel.setForeground(Color.WHITE);
			selectRandomnessLabel.setFont(new Font("Tahoma", Font.PLAIN, 19));
			selectRandomnessLabel.setBounds(222, 31, 204, 39);
			keyChoicePanel.add(selectRandomnessLabel);
			
			randomnessSourceGroup.add(sourceDefault);
			randomnessSourceGroup.add(sourceDRBG);
			randomnessSourceGroup.add(sourceSHA1PRNG);
		}	
	}
}
