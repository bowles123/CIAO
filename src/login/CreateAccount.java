package login;

import main.ciaoGUI;
import security.Crypto;
import security.CryptoException;
import security.Hash;
import security.UserFile;

import javax.swing.*;

import org.apache.commons.lang3.SystemUtils;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.ArrayList;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

public class CreateAccount {

	static Login ciaoLogin;
	JFrame createAccountFrame;
	ArrayList<String> passwords;
	ArrayList<String> usernames;

	private String Username;
	private String Password;
	private String AccessToken;
	private Hash fileHash;
	private Hash keyHash;
	private File fileToEncrypt;

	private JTextField usernameField;
	private JPasswordField passwordField;
	private JPasswordField tokenField;
	private JTextField textTokenField;
	private JCheckBox showToken;

	public CreateAccount() {
		fileHash = null;
		keyHash = null;
		fileToEncrypt = null;
		Username = null;
		Password = null;
		initialize();
	}

	public CreateAccount(String username, String password) {
		fileHash = null;
		keyHash = null;
		fileToEncrypt = null;
		Username = username;
		Password = password;
		initialize();
	}

	private void initialize() {
		createAccountFrame = new JFrame();
		createAccountFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(ciaoGUI.class.getResource("/main/icon.png")));
		createAccountFrame.setResizable(false);
		createAccountFrame.setTitle("Create CIAO Account");
		createAccountFrame.setBounds(100, 100, 400, 350);
		createAccountFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		createAccountFrame.getContentPane().setLayout(springLayout);

		showToken = new JCheckBox();
		showToken.setFocusable(false);
		JLabel step1Label = new JLabel("Step 1: Enter your A# and USU password.");
		springLayout.putConstraint(SpringLayout.NORTH, step1Label, 20, SpringLayout.NORTH, createAccountFrame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, step1Label, 21, SpringLayout.WEST, createAccountFrame.getContentPane());
		createAccountFrame.getContentPane().add(step1Label);

		JLabel usernameLabel = new JLabel("*A-Number:");
		springLayout.putConstraint(SpringLayout.NORTH, usernameLabel, 10, SpringLayout.SOUTH, step1Label);
		springLayout.putConstraint(SpringLayout.WEST, usernameLabel, 10, SpringLayout.WEST, step1Label);
		createAccountFrame.getContentPane().add(usernameLabel);

		if (Username != null)
			usernameField = new JTextField(Username);
		else
			usernameField = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, usernameField, 6, SpringLayout.SOUTH, step1Label);
		springLayout.putConstraint(SpringLayout.WEST, usernameField, 6, SpringLayout.EAST, usernameLabel);
		createAccountFrame.getContentPane().add(usernameField);
		usernameField.setColumns(10);

		JLabel passwordLabel = new JLabel("*Password:");
		springLayout.putConstraint(SpringLayout.NORTH, passwordLabel, 17, SpringLayout.SOUTH, usernameLabel);
		springLayout.putConstraint(SpringLayout.WEST, passwordLabel, 0, SpringLayout.WEST, usernameLabel);
		createAccountFrame.getContentPane().add(passwordLabel);

		if (Password != null)
			passwordField = new JPasswordField(Password);
		else
			passwordField = new JPasswordField();
		passwordField.setEchoChar('*');
		springLayout.putConstraint(SpringLayout.NORTH, passwordField, -3, SpringLayout.NORTH, passwordLabel);
		springLayout.putConstraint(SpringLayout.WEST, passwordField, 0, SpringLayout.WEST, usernameField);
		createAccountFrame.getContentPane().add(passwordField);
		passwordField.setColumns(10);

		JLabel step2Label = new JLabel("Step 2: Create Access Token.");
		springLayout.putConstraint(SpringLayout.WEST, step2Label, 0, SpringLayout.WEST, step1Label);
		createAccountFrame.getContentPane().add(step2Label);

		final JCheckBox showPassword = new JCheckBox();
		showPassword.setFocusable(false);
		showPassword.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					passwordField.setEchoChar((char)0);
				}
				else {
					passwordField.setEchoChar('*');
				}
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, showPassword, -19, SpringLayout.SOUTH, passwordLabel);
		springLayout.putConstraint(SpringLayout.WEST, showPassword, 8, SpringLayout.WEST, createAccountFrame.getContentPane());
		createAccountFrame.getContentPane().add(showPassword);

		JLabel step2ALabel = new JLabel("a. Go to http://usu.instructure.com & Login");
		springLayout.putConstraint(SpringLayout.SOUTH, step2Label, -6, SpringLayout.NORTH, step2ALabel);
		springLayout.putConstraint(SpringLayout.WEST, step2ALabel, 0, SpringLayout.WEST, usernameLabel);
		createAccountFrame.getContentPane().add(step2ALabel);

		JLabel step2BLabel = new JLabel("b. Go to Settings & Click \"+ New Access Token\"");
		springLayout.putConstraint(SpringLayout.NORTH, step2BLabel, 163, SpringLayout.NORTH, createAccountFrame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, step2ALabel, -6, SpringLayout.NORTH, step2BLabel);
		springLayout.putConstraint(SpringLayout.WEST, step2BLabel, 0, SpringLayout.WEST, usernameLabel);
		createAccountFrame.getContentPane().add(step2BLabel);

		JLabel step2CLabel = new JLabel("c. Paste the New Access Token Below");
		springLayout.putConstraint(SpringLayout.NORTH, step2CLabel, 6, SpringLayout.SOUTH, step2BLabel);
		springLayout.putConstraint(SpringLayout.WEST, step2CLabel, 0, SpringLayout.WEST, usernameLabel);
		createAccountFrame.getContentPane().add(step2CLabel);

		JLabel tokenLabel = new JLabel("*Access Token:");
		springLayout.putConstraint(SpringLayout.NORTH, tokenLabel, 6, SpringLayout.SOUTH, step2CLabel);
		springLayout.putConstraint(SpringLayout.WEST, tokenLabel, 51, SpringLayout.WEST, createAccountFrame.getContentPane());
		createAccountFrame.getContentPane().add(tokenLabel);

		showToken.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					tokenField.setEchoChar((char)0);
				}
				else {
					tokenField.setEchoChar('*');
				}
			}

		});
		springLayout.putConstraint(SpringLayout.NORTH, showToken, 3, SpringLayout.SOUTH, step2CLabel);
		springLayout.putConstraint(SpringLayout.WEST, showToken, 25, SpringLayout.WEST, createAccountFrame.getContentPane());
		createAccountFrame.getContentPane().add(showToken);

		if (SystemUtils.IS_OS_MAC) {
			textTokenField = new JTextField();
			springLayout.putConstraint(SpringLayout.NORTH, textTokenField, 3, SpringLayout.SOUTH, step2CLabel);
			springLayout.putConstraint(SpringLayout.WEST, textTokenField, 6, SpringLayout.EAST, tokenLabel);
			springLayout.putConstraint(SpringLayout.EAST, textTokenField, 220, SpringLayout.EAST, tokenLabel);
			createAccountFrame.getContentPane().add(textTokenField);
			textTokenField.setColumns(10);
		}
		else {
			tokenField = new JPasswordField();
			tokenField.setEchoChar('*');
			springLayout.putConstraint(SpringLayout.NORTH, tokenField, 3, SpringLayout.SOUTH, step2CLabel);
			springLayout.putConstraint(SpringLayout.WEST, tokenField, 6, SpringLayout.EAST, tokenLabel);
			springLayout.putConstraint(SpringLayout.EAST, tokenField, 220, SpringLayout.EAST, tokenLabel);
			createAccountFrame.getContentPane().add(tokenField);
			tokenField.setColumns(10);
		}

		JLabel step3Label = new JLabel("Step 3: Click Create Account.");
		springLayout.putConstraint(SpringLayout.NORTH, step3Label, 28, SpringLayout.SOUTH, tokenLabel);
		springLayout.putConstraint(SpringLayout.WEST, step3Label, 21, SpringLayout.WEST, createAccountFrame.getContentPane());
		createAccountFrame.getContentPane().add(step3Label);

		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createAccountFrame.dispose();
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, cancelBtn, 15, SpringLayout.SOUTH, step3Label);
		springLayout.putConstraint(SpringLayout.WEST, cancelBtn, 50, SpringLayout.WEST, createAccountFrame.getContentPane());
		createAccountFrame.getContentPane().add(cancelBtn);

		JButton createBtn = new JButton("Create Account & Login");
		createBtn.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				Username = usernameField.getText();
				Password = passwordField.getText();
				if (SystemUtils.IS_OS_MAC){
					AccessToken = textTokenField.getText();
				}
				else {
					AccessToken = tokenField.getText();
				}
				usernameField.setText("");
				passwordField.setText("");
				if (SystemUtils.IS_OS_MAC){
					textTokenField.setText("");
				}
				else {
					tokenField.setText("");
				}
				String subANum = Username.substring(1);

				if (!Username.toUpperCase().startsWith("A") || !(Username.length() == 9) || !subANum.matches("[0-9]+")) {
					JOptionPane.showMessageDialog(createAccountFrame, "Invalid A#, please try again");
					showToken.setSelected(false);
					showPassword.setSelected(false);
				}
				else if (Username.equals("") || Password.equals("") || AccessToken.equals(""))
					JOptionPane.showMessageDialog(createAccountFrame, "A '*' denotes a required fill, try again.");
				else if (!AccessToken.startsWith("1009~")){
					if (SystemUtils.IS_OS_MAC){
						textTokenField.setText("");
					}
					else {
						tokenField.setText("");
					}
					JOptionPane.showMessageDialog(createAccountFrame, "Invalid token, please try again.");
				}
				else {
			    	try {
						fileHash = new Hash(Username);
					} catch (Exception e) {
						e.printStackTrace();
					}

					try {
						keyHash = new Hash(Username+Password);
					} catch (Exception e) {
						e.printStackTrace();
					}

					if(UserFile.exists(fileHash.getHashed())) {
						JOptionPane.showMessageDialog(createAccountFrame, "User already exists, please login or create a new user.");
						createAccountFrame.dispose();
						ciaoLogin = new Login(Username);
						ciaoLogin.ciaoLoginFrame.setVisible(true);
					}
					else {
						UserFile.create(fileHash.getHashed());
						fileToEncrypt = new File(fileHash.getHashed());

						try {
				            Crypto.encrypt(keyHash.getHashed(), AccessToken, fileToEncrypt);
							JOptionPane.showMessageDialog(createAccountFrame, "Successfully created account.");
							createAccountFrame.dispose();
							ciaoGUI gui = new ciaoGUI(AccessToken);
							AccessToken = "";
							gui.setVisible(true);

				        } catch (CryptoException ex) {
				        	if (ex.getCause().getClass().getName().equals("java.security.InvalidKeyException")) {
				        		fileToEncrypt.delete();
				            	JOptionPane.showMessageDialog(createAccountFrame, "Files for AES 256-bit encryption are not currently in Java's security package,\n"
				            			+ "please overwrite the current local_policy.jar and US_export_policy.jar files, \nthen create an account.");
				            	usernameField.setText("");
				            	passwordField.setText("");
								if (SystemUtils.IS_OS_MAC){
									textTokenField.setText("");
								}
								else {
									tokenField.setText("");
								}
				            	showToken.setSelected(false);
				        	}
				        	else {
					            System.out.println(ex.getMessage());
					            ex.printStackTrace();
				        	}
				        }
					}

					Username = "";
					Password = "";
				}
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, createBtn, 15, SpringLayout.SOUTH, step3Label);
		springLayout.putConstraint(SpringLayout.WEST, createBtn, 175, SpringLayout.WEST, createAccountFrame.getContentPane());
		createAccountFrame.getContentPane().add(createBtn);
		createAccountFrame.getRootPane().setDefaultButton(createBtn);
	}
}
