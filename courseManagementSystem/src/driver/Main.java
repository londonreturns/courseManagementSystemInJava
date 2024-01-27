package driver;

import javax.swing.JOptionPane;

import utility.LoginFrame;
import utility.RegisterFrame;

public class Main {
	static LoginFrame loginFrame = new LoginFrame(200, 200, 1000, 750);
	static RegisterFrame registerFrame = new RegisterFrame(200, 200, 1000, 750);

	public static void main(String[] args) {
		loginFrameDisplay();
	}

	public static void loginFrameDisplay() {
		if (loginFrame.loginInit == false) {
			loginFrame.setLocation(registerFrame.getX(), registerFrame.getY());
			loginFrame.setVisible(true);
			loginFrame.loginInit = true;
			loginFrame.setLoginElements();
		}else {
			loginFrame.setLocation(registerFrame.getX(), registerFrame.getY());
			loginFrame.setVisible(true);
		}
		registerFrame.setVisible(false);
	}
	
	public static void registerFrameDisplay() {
		if (registerFrame.registerInit == false) {
			registerFrame.setLocation(loginFrame.getX(), loginFrame.getY());
			registerFrame.setVisible(true);
			registerFrame.registerInit = true;
			registerFrame.setLoginElements();
		}else {
			registerFrame.setLocation(loginFrame.getX(), loginFrame.getY());
			registerFrame.setVisible(true);
		}
		loginFrame.setVisible(false);
	}
}