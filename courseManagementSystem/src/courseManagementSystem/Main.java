package courseManagementSystem;

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
			loginFrame.loginInit = true;
			loginFrame.setLoginElements();
			loginFrame.setLocation(registerFrame.getX(), registerFrame.getY());
			loginFrame.setVisible(true);
		}else {
			loginFrame.setLocation(registerFrame.getX(), registerFrame.getY());
			loginFrame.setVisible(true);
		}
		registerFrame.setVisible(false);
	}
	
	public static void registerFrameDisplay() {
		if (registerFrame.registerInit == false) {
			registerFrame.registerInit = true;
			registerFrame.setLoginElements();
			registerFrame.setLocation(loginFrame.getX(), loginFrame.getY());
			registerFrame.setVisible(true);
		}else {
			registerFrame.setLocation(loginFrame.getX(), loginFrame.getY());
			registerFrame.setVisible(true);
		}
		loginFrame.setVisible(false);
	}
}