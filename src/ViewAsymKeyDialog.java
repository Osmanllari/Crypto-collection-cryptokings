import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewAsymKeyDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	JTextArea keyLabel = new JTextArea();
	JButton okButton;
	JFileChooser fileChooser = new JFileChooser();
	JPanel viewKeyPanel;
	
	static String hexPrivateKey;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ViewAsymKeyDialog dialog = new ViewAsymKeyDialog(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog for viewing Asymmetric Key.
	 */
	public ViewAsymKeyDialog(String keyToBeDisplayed) {
		setTitle("View Key\r\n");
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
				JButton cancelButton = new JButton("Close");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			viewKeyPanel = new JPanel();
			getContentPane().add(viewKeyPanel, BorderLayout.CENTER);
			viewKeyPanel.setForeground(new Color(255, 255, 255));
			viewKeyPanel.setBackground(new Color(0, 0, 102));
			viewKeyPanel.setLayout(null);
		}
		{
			JLabel displayKeyLabel = new JLabel("Key:");
			displayKeyLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
			displayKeyLabel.setForeground(new Color(255, 255, 255));
			displayKeyLabel.setBounds(181, 5, 74, 17);
			viewKeyPanel.add(displayKeyLabel);
		}
		{
			JScrollPane scrollPanePrivKey = new JScrollPane();
			scrollPanePrivKey.setBounds(10, 24, 416, 195);
			scrollPanePrivKey.setBorder(null);
			
			viewKeyPanel.add(scrollPanePrivKey);
			{
				keyLabel.setEnabled(true);
				keyLabel.setEditable(false);
				keyLabel.setBackground(new Color(0, 0, 102));
				scrollPanePrivKey.setViewportView(keyLabel);
				keyLabel.setLineWrap(true);
				keyLabel.setForeground(Color.WHITE);
				keyLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
				keyLabel.setText(keyToBeDisplayed);
			}
		}
	}
}
