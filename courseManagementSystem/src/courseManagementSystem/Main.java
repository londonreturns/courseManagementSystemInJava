package courseManagementSystem;

import java.awt.Color;

public class Main {
	static LoginFrame loginFrame = new LoginFrame(200, 200, 1000, 750, Color.black);
	static RegisterFrame registerFrame = new RegisterFrame(200, 200, 1000, 750, Color.GRAY);

	public static void main(String[] args) {
		loginFrameDiplay();
	}

	static void loginFrameDiplay() {
		if (loginFrame.loginInit == false) {
			loginFrame.loginInit = true;
			loginFrame.setLoginElements();
			loginFrame.setVisible(true);
		}else {
			loginFrame.setVisible(true);
		}
		registerFrame.setVisible(false);
		
	}
	
	static void registerFrameDisplay() {
		if (registerFrame.loginInit == false) {
			registerFrame.loginInit = true;
			registerFrame.setLoginElements();
			registerFrame.setVisible(true);
		}else {
			registerFrame.setVisible(true);
		}
		loginFrame.setVisible(false);
	}
}
