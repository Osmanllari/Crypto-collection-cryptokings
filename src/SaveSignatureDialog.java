import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.awt.event.ActionEvent;

public class SaveSignatureDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	JPanel saveSigPanel;
	JFileChooser fileChooser = new JFileChooser();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SaveSignatureDialog dialog = new SaveSignatureDialog(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog for prompting the user to save the generated signature.
	 */
	public SaveSignatureDialog(byte[] SignatureData) {
		setBounds(100, 100, 450, 300);
		setTitle("Save Signature");
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Save");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						saveSignatureToFile(SignatureData);
					}
				});
				okButton.setActionCommand("OK");
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
				buttonPane.add(cancelButton);
			}
		}
		saveSigPanel = new JPanel();
		getContentPane().add(saveSigPanel, BorderLayout.CENTER);
		saveSigPanel.setForeground(new Color(255, 255, 255));
		saveSigPanel.setBackground(new Color(0, 0, 102));
		saveSigPanel.setLayout(null);
		
		JLabel displaySignatureLabel = new JLabel("Generated Signature in String Format:");
		displaySignatureLabel.setForeground(Color.WHITE);
		displaySignatureLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		displaySignatureLabel.setBounds(97, 56, 241, 17);
		saveSigPanel.add(displaySignatureLabel);
		
		JLabel signatureLabel = new JLabel("\r\n");
		signatureLabel.setText(SignatureData.toString());
		signatureLabel.setHorizontalAlignment(SwingConstants.CENTER);
		signatureLabel.setForeground(Color.WHITE);
		signatureLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		signatureLabel.setBounds(10, 99, 416, 31);
		saveSigPanel.add(signatureLabel);
	}
	
	//Method for saving a signature to a file 
	public void saveSignatureToFile(byte[] signatureData) {
		//Setting the title of the dialog
		fileChooser.setDialogTitle("Save Signature");
		
		//Showing the save dialog for selecting location to store the signature
		int userSelection = fileChooser.showSaveDialog(null);

        //If the user decides to save, save the signature in the selected file
        if (userSelection == JFileChooser.APPROVE_OPTION) {
        	File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();
            
            //Saving the signature to the selected file
            try {
              Files.write(selectedFile.toPath(), signatureData);
              JOptionPane.showMessageDialog(null, "Signature saved to: " + filePath);
            } catch (IOException e) {
              JOptionPane.showMessageDialog(null, "Error saving signature: " + e.getMessage());
            }
        }
        dispose();
	}
}
