import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.crypto.SecretKey;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.bouncycastle.util.encoders.Hex;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class ViewKeyDialog extends JDialog { 

	private final JPanel contentPanel = new JPanel();
	private JTextField passwordField;
	JLabel viewKeyLabel;
	JLabel enterPassLabel;
	JLabel lblSelectedFile;
	JLabel selectedFileField;
	JPanel viewKeyPanel;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ViewKeyDialog dialog = new ViewKeyDialog(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ViewKeyDialog(File filepath) {
		setTitle("View Key\r\n");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Submit");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						String userPassword = passwordField.getText();
						SecretKey key = null;
						try {
							key = SymmetricEncryption.getKeyFromStorage(filepath.toString(), userPassword);
							if(key != null) {
								String hexKey = Hex.toHexString(key.getEncoded());
								viewKeyLabel.setText(hexKey);
								viewKeyLabel.setVisible(true);
								buttonPane.setVisible(false);
								enterPassLabel.setVisible(false);
								enterPassLabel.setVisible(false);
								passwordField.setVisible(false);
								lblSelectedFile.setText("Secret Key:");
								selectedFileField.setVisible(false);
								
								JButton closeButton = new JButton("Close");
								closeButton.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent arg0) {
										dispose();
									}
								});
								closeButton.setBounds(173, 175, 89, 23);
								closeButton.setFocusable(false);
								viewKeyPanel.add(closeButton);
							} 
						}
						catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Error Getting Secret Key" + e);
							dispose();
						}	
					}
				});
				okButton.setFocusable(false);
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
				cancelButton.setFocusable(false);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				
			}
		}
		viewKeyPanel = new JPanel();
		getContentPane().add(viewKeyPanel, BorderLayout.CENTER);
		viewKeyPanel.setForeground(new Color(255, 255, 255));
		viewKeyPanel.setBackground(new Color(0, 0, 102));
		viewKeyPanel.setLayout(null);
		{
			enterPassLabel = new JLabel("Enter Password:");
			enterPassLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
			enterPassLabel.setForeground(new Color(255, 255, 255));
			enterPassLabel.setBounds(167, 126, 101, 17);
			viewKeyPanel.add(enterPassLabel);
		}
		{
			passwordField = new JTextField();
			passwordField.setBounds(170, 153, 96, 20);
			viewKeyPanel.add(passwordField);
			passwordField.setColumns(10);
		}
		{
			lblSelectedFile = new JLabel("Selected Key:");
			lblSelectedFile.setForeground(Color.WHITE);
			lblSelectedFile.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblSelectedFile.setBounds(176, 26, 84, 17);
			viewKeyPanel.add(lblSelectedFile);
		}
		{
			selectedFileField = new JLabel(filepath.toString());
			selectedFileField.setHorizontalAlignment(SwingConstants.CENTER);
			selectedFileField.setForeground(Color.WHITE);
			selectedFileField.setFont(new Font("Tahoma", Font.PLAIN, 12));
			selectedFileField.setBounds(10, 54, 416, 17);
			viewKeyPanel.add(selectedFileField);
		}
		{
			viewKeyLabel = new JLabel("");
			viewKeyLabel.setHorizontalAlignment(SwingConstants.CENTER);
			viewKeyLabel.setForeground(Color.WHITE);
			viewKeyLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
			viewKeyLabel.setVisible(false);
			viewKeyLabel.setBounds(10, 82, 416, 33);
			viewKeyPanel.add(viewKeyLabel);
		}
	}
}
