package main;

import org.apache.commons.lang3.SystemUtils;
import javax.swing.UIManager;
import login.Login;

public class ciaoMain
{
	private static void setLookFeel(String osType) {
		// Workaround method for dealing with my process of dynamically updating
		// UI elements; not using it resulted in
		// tabs refusing to accept custom UI themes applied in any other way

		if (osType == "win" || osType == "mac" || osType == "linux") {
			try {
				UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		else if (osType == "none") {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static int findOS() {
		int os = -1;

		if (SystemUtils.IS_OS_WINDOWS) {
			os = 0;
		}

		else if (SystemUtils.IS_OS_MAC) {
			os = 1;
		}

		else if (SystemUtils.IS_OS_LINUX) {
			os = 2;
		}

		else {
			os = 3;
		}

		return os;
	}

	private static String findJava() {
		String[] osTypes = {"win", "mac", "linux", "none"};

		int os;
		os = findOS();

		if (os == -1 || os == 3) {
			System.out.println("Operating System Not Determined: Reverting to System Default");
		}
		
		try {
			System.out.println(System.getenv("PATH"));
		} catch (Exception e) {
			System.out.println("Java install not found: exiting program.");
			System.exit(1);
		}

		return osTypes[os];
	}

	private static void launchProgram(){
		Login login = new Login();
		login.ciaoLoginFrame.setVisible(true);

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
			}
		});
	}

	public static void main(String args[]) {
		String osType;

		osType = findJava();
		setLookFeel(osType);
		launchProgram();

	}
}