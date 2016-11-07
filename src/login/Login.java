package login;

import javax.swing.JFrame;
import javax.swing.SpringLayout;

import org.apache.commons.lang3.SystemUtils;

import main.ciaoGUI;
import security.Crypto;
import security.CryptoException;
import security.Hash;
import security.UserFile;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

public class Login {
	public JFrame ciaoLoginFrame;
	JTextField usernameField;

	private JPasswordField passwordField;
	private Hash fileHash;
	private Hash keyHash;
	private File encryptedFile;
	private String Username;

	public Login() {
		fileHash = null;
		keyHash = null;
		encryptedFile = null;
		Username = null;
		initialize();
	}

	public Login(String username) {
		fileHash = null;
		keyHash = null;
		encryptedFile = null;
		Username = username;
		initialize();
	}

	private void initialize() {
		ciaoLoginFrame = new JFrame();
		ciaoLoginFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(ciaoGUI.class.getResource("/main/icon.png")));
		ciaoLoginFrame.setTitle("CIAO Login");
		ciaoLoginFrame.setResizable(false);
		ciaoLoginFrame.setBounds(250, 250, 250, 250);
		ciaoLoginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		ciaoLoginFrame.getContentPane().setLayout(springLayout);


		JLabel usernameLabel = new JLabel("Username:");

		JTextArea ciaoTextArea = new JTextArea();
		springLayout.putConstraint(SpringLayout.SOUTH, ciaoTextArea, -160, SpringLayout.SOUTH, ciaoLoginFrame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, ciaoTextArea, 80, SpringLayout.WEST, ciaoLoginFrame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, ciaoTextArea, 160, SpringLayout.WEST, ciaoLoginFrame.getContentPane());
		if (!SystemUtils.IS_OS_WINDOWS) {
			ciaoTextArea.setFont(new Font("Mistral", Font.ITALIC, 30));
		}
		else {
			ciaoTextArea.setFont(new Font("Mistral", Font.ITALIC, 40));
		}
		ciaoTextArea.setBackground(new Color(0, 0, 0, 0));
		ciaoTextArea.setText("CIAO");
		ciaoTextArea.setEditable(false);
		ciaoLoginFrame.getContentPane().add(ciaoTextArea);

		springLayout.putConstraint(SpringLayout.NORTH, usernameLabel, 80, SpringLayout.NORTH, ciaoLoginFrame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, usernameLabel, 10, SpringLayout.WEST, ciaoLoginFrame.getContentPane());
		ciaoLoginFrame.getContentPane().add(usernameLabel);

		if (Username == null)
			usernameField = new JTextField();
		else
			usernameField = new JTextField(Username);
		springLayout.putConstraint(SpringLayout.NORTH, usernameField, -3, SpringLayout.NORTH, usernameLabel);
		springLayout.putConstraint(SpringLayout.WEST, usernameField, 6, SpringLayout.EAST, usernameLabel);
		ciaoLoginFrame.getContentPane().add(usernameField);
		usernameField.setColumns(10);

		JLabel passwordLabel = new JLabel("Password:");
		springLayout.putConstraint(SpringLayout.SOUTH, passwordLabel, -80, SpringLayout.SOUTH, ciaoLoginFrame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, passwordLabel, 0, SpringLayout.EAST, usernameLabel);
		ciaoLoginFrame.getContentPane().add(passwordLabel);

		passwordField = new JPasswordField();
		springLayout.putConstraint(SpringLayout.SOUTH, passwordField, -80, SpringLayout.SOUTH, ciaoLoginFrame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, passwordField, 6, SpringLayout.EAST, passwordLabel);
		springLayout.putConstraint(SpringLayout.EAST, passwordField, 0, SpringLayout.EAST, usernameField);
		ciaoLoginFrame.getContentPane().add(passwordField);

		final JButton createBtn = new JButton("Create Account");
		createBtn.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				Disclaimer disclaimer;

				if (usernameField.getText().equals("") || passwordField.getText().equals(""))
					disclaimer = new Disclaimer();
				else {
					disclaimer = new Disclaimer(usernameField.getText(), passwordField.getText());
					passwordField.setText("");
				}
				disclaimer.disclaimerFrame.setVisible(true);
				ciaoLoginFrame.dispose();
			}
		});

		final JButton submitBtn = new JButton("Submit");
		submitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String username = usernameField.getText();
				@SuppressWarnings("deprecation")
				String password = passwordField.getText();
				String subANum = username.substring(1);

				if (username.equals("") || password.equals(""))
					JOptionPane.showMessageDialog(ciaoLoginFrame, "Username and/or Password cannot be left blank.");
				else if (username.length() > 9 || !subANum.matches("[0-9]+")) {
					JOptionPane.showMessageDialog(ciaoLoginFrame, "Invalid Username, please try again.");
					usernameField.setText("");
					passwordField.setText("");
				}
				else {
			    	try {
						fileHash = new Hash(username);
						encryptedFile = new File(fileHash.getHashed());
					} catch (Exception e) {
						e.printStackTrace();
					}

					try {
						keyHash = new Hash(username+password);
					} catch (Exception e) {
						e.printStackTrace();
					}

					username = "";
					password = "";

			    	if (UserFile.exists(fileHash.getHashed())){
			    		try {
							if (Crypto.decrypt(keyHash.getHashed(), encryptedFile).startsWith("1009~")){
								ciaoLoginFrame.dispose();
								ciaoGUI gui = new ciaoGUI(Crypto.decrypt(keyHash.getHashed(), encryptedFile));
								gui.setVisible(true);
							}
						} catch (CryptoException ex) {
				            if(ex.getCause().getClass().getName().equals("javax.crypto.BadPaddingException")) {
					            JOptionPane.showMessageDialog(ciaoLoginFrame, "Invalid Password, please try again.");
					            passwordField.setText("");
				            }
				            else if (ex.getCause().getClass().getName().equals("java.security.InvalidKeyException")) {
				            	JOptionPane.showMessageDialog(ciaoLoginFrame, "Files for AES 256-bit encryption not in Java's security package,\n"
				            			+ "please overwrite the current local_policy.jar and US_export_policy.jar files.\nthen log into ciao.");
				            	usernameField.setText("");
				            	passwordField.setText("");
				            }
				            else {
					            System.out.println(ex.getMessage());
					            ex.printStackTrace();
				            }
				        }

			    	}else{
			    		JOptionPane.showMessageDialog(ciaoLoginFrame, "User does not exist, please create a user.");
			    		usernameField.setEditable(false);
			    		passwordField.setEditable(false);
			    		submitBtn.setEnabled(false);
			    		ciaoLoginFrame.getRootPane().setDefaultButton(createBtn);
			    	}
				}
			}
		});
		springLayout.putConstraint(SpringLayout.WEST, submitBtn, 0, SpringLayout.WEST, usernameLabel);
		ciaoLoginFrame.getContentPane().add(submitBtn);
		ciaoLoginFrame.getRootPane().setDefaultButton(submitBtn);

		springLayout.putConstraint(SpringLayout.NORTH, submitBtn, 0, SpringLayout.NORTH, createBtn);
		springLayout.putConstraint(SpringLayout.SOUTH, createBtn, -25, SpringLayout.SOUTH, ciaoLoginFrame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, createBtn, -10, SpringLayout.EAST, ciaoLoginFrame.getContentPane());
		ciaoLoginFrame.getContentPane().add(createBtn);
	}
}
