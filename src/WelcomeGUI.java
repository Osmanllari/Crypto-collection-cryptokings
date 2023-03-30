import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Button;

public class WelcomeGUI {

	JFrame welcomeFrame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WelcomeGUI window = new WelcomeGUI();
					window.welcomeFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WelcomeGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		welcomeFrame = new JFrame();
		welcomeFrame.setResizable(false);
		welcomeFrame.getContentPane().setBackground(new Color(0, 0, 51));
		welcomeFrame.setTitle("CryptoTool");
		welcomeFrame.setBounds(100, 100, 1000, 650);
		welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		welcomeFrame.getContentPane().setLayout(null);
		welcomeFrame.setLocationRelativeTo(null);
		
		JLabel welcomeLabel = new JLabel("Welcome to CryptoTool");
		welcomeLabel.setForeground(new Color(255, 255, 255));
		welcomeLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		welcomeLabel.setBounds(375, 11, 236, 51);
		welcomeFrame.getContentPane().add(welcomeLabel);
		
		JLabel SelectActionLabel = new JLabel("Please select an action:");
		SelectActionLabel.setForeground(new Color(255, 255, 255));
		SelectActionLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		SelectActionLabel.setBounds(383, 212, 220, 51);
		welcomeFrame.getContentPane().add(SelectActionLabel);
		
		Button symEncBtn = new Button("Symmetric Encryption");
		symEncBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SymEncryptionGUI symEncryptionFrame = new SymEncryptionGUI();
				symEncryptionFrame.symEncryptionFrame.setVisible(true);
				welcomeFrame.dispose();
			}
		});
		symEncBtn.setFocusable(false);
		symEncBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		symEncBtn.setBackground(new Color(70, 130, 180));
		symEncBtn.setForeground(new Color(255, 255, 255));
		symEncBtn.setBounds(83, 331, 192, 56);
		welcomeFrame.getContentPane().add(symEncBtn);
		
		Button asymEncBtn = new Button("Asymmetric Encryption");
		asymEncBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AsymEncryptionGUI asymEncryptionFrame = new AsymEncryptionGUI();
				asymEncryptionFrame.asymEncryptionFrame.setVisible(true);
				welcomeFrame.dispose();
			}
		});
		asymEncBtn.setForeground(Color.WHITE);
		asymEncBtn.setFocusable(false);
		asymEncBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		asymEncBtn.setBackground(new Color(65, 105, 225));
		asymEncBtn.setBounds(395, 331, 192, 56);
		welcomeFrame.getContentPane().add(asymEncBtn);
		
		Button digitalSigBtn = new Button("Digital Signing");
		digitalSigBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DigSignGUI digSignGUI = new DigSignGUI();
				digSignGUI.digSignFrame.setVisible(true);
				welcomeFrame.dispose();
			}
		});
		digitalSigBtn.setForeground(Color.WHITE);
		digitalSigBtn.setFocusable(false);
		digitalSigBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		digitalSigBtn.setBackground(new Color(0, 128, 128));
		digitalSigBtn.setBounds(707, 331, 192, 56);
		welcomeFrame.getContentPane().add(digitalSigBtn);
	}
}
