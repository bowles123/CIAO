package login;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JTextArea;
import javax.swing.JButton;

import org.apache.commons.lang3.SystemUtils;

import main.ciaoGUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Disclaimer {

	JFrame disclaimerFrame;
	private String username;
	private String password;

	public Disclaimer() {
		username = null;
		password = null;
		initialize();
	}

	public Disclaimer(String username, String password) {
		this.username = username;
		this.password = password;
		initialize();
	}

	private void initialize() {
		disclaimerFrame = new JFrame();
		disclaimerFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(ciaoGUI.class.getResource("/main/icon.png")));
		disclaimerFrame.setResizable(false);
		disclaimerFrame.setTitle("CIAO Disclaimer");
		disclaimerFrame.setBounds(100, 100, 450, 541);
		disclaimerFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		disclaimerFrame.getContentPane().setLayout(springLayout);

		JTextArea txtrByContinuingI = new JTextArea();
		springLayout.putConstraint(SpringLayout.WEST, txtrByContinuingI, 68, SpringLayout.WEST, disclaimerFrame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, txtrByContinuingI, -112, SpringLayout.SOUTH, disclaimerFrame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, txtrByContinuingI, -72, SpringLayout.EAST, disclaimerFrame.getContentPane());
		if (SystemUtils.IS_OS_LINUX){
			txtrByContinuingI.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		}
		else {
			txtrByContinuingI.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		}
		txtrByContinuingI.setBackground(new Color(0, 0, 0, 0));
		txtrByContinuingI.setText("By continuing I hereby allow CIAO access to the token I will provide. "
				+ "I am aware that I am fully responsible for the protection of my access token and give CIAO no responsibility. "
				+ "\r\n(Be aware that this application requires 256-bit AES encryption and the standard files for encryption will need to be overwritten with the "
				+ "Unlimited Strength Jurisdiction Policy Files.)");
		txtrByContinuingI.setWrapStyleWord(true);
		txtrByContinuingI.setEditable(false);
		txtrByContinuingI.setLineWrap(true);
		disclaimerFrame.getContentPane().add(txtrByContinuingI);

		JButton cancelBtn = new JButton("Cancel");
		springLayout.putConstraint(SpringLayout.NORTH, cancelBtn, 43, SpringLayout.SOUTH, txtrByContinuingI);
		springLayout.putConstraint(SpringLayout.EAST, cancelBtn, -268, SpringLayout.EAST, disclaimerFrame.getContentPane());
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				disclaimerFrame.dispose();
			}
		});
		disclaimerFrame.getContentPane().add(cancelBtn);

		JButton continueBtn = new JButton("Continue");
		springLayout.putConstraint(SpringLayout.NORTH, continueBtn, 0, SpringLayout.NORTH, cancelBtn);
		springLayout.putConstraint(SpringLayout.WEST, continueBtn, 59, SpringLayout.EAST, cancelBtn);
		continueBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CreateAccount create;

				if (username == null || password == null)
					create = new CreateAccount();
				else {
					create = new CreateAccount(username, password);
					username = null;
					password = null;
				}
				create.createAccountFrame.setVisible(true);
				disclaimerFrame.setVisible(false);
			}
		});
		disclaimerFrame.getContentPane().add(continueBtn);

		JTextArea ciaoTextArea = new JTextArea();
		springLayout.putConstraint(SpringLayout.NORTH, txtrByContinuingI, 37, SpringLayout.SOUTH, ciaoTextArea);
		springLayout.putConstraint(SpringLayout.SOUTH, ciaoTextArea, -423, SpringLayout.SOUTH, disclaimerFrame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, ciaoTextArea, 163, SpringLayout.WEST, disclaimerFrame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, ciaoTextArea, 263, SpringLayout.WEST, disclaimerFrame.getContentPane());
		if (!SystemUtils.IS_OS_WINDOWS) {
			ciaoTextArea.setFont(new Font("Mistral", Font.ITALIC, 40));
		}
		else {
			ciaoTextArea.setFont(new Font("Mistral", Font.ITALIC, 50));
		}
		ciaoTextArea.setBackground(new Color(0, 0, 0, 0));
		ciaoTextArea.setText("CIAO");
		ciaoTextArea.setEditable(false);
		disclaimerFrame.getContentPane().add(ciaoTextArea);
		disclaimerFrame.getRootPane().setDefaultButton(continueBtn);
	}
}
