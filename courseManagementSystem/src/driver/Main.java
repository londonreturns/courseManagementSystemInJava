package driver;

import utility.LoginFrame;
import utility.RegisterFrame;
import utility.StudentFrame;

public class Main {
	static LoginFrame loginFrame = new LoginFrame(200, 200, 1000, 750);
	static RegisterFrame registerFrame = new RegisterFrame(200, 200, 1000, 750);
	static StudentFrame studentFrame = new StudentFrame(200, 200, 1000, 750);
//	static TeacherFrame teacherFrame = new TeacherFrame(200, 200, 1000, 750);
//	static AdminFrame adminFrame = new AdminFrame(200, 200, 1000, 750);
	
	public static void main(String[] args) {
		loginFrameDisplay();
	}

	public static void loginFrameDisplay() {
		if (loginFrame.init == false) {
			loginFrame.setLocation(registerFrame.getX(), registerFrame.getY());
			loginFrame.setVisible(true);
			loginFrame.init = true;
			loginFrame.setLoginElements();
		}else {
			loginFrame.setLocation(registerFrame.getX(), registerFrame.getY());
			loginFrame.setVisible(true);
		}
		registerFrame.setVisible(false);
	}
	
	public static void registerFrameDisplay() {
		if (registerFrame.init == false) {
			registerFrame.setLocation(loginFrame.getX(), loginFrame.getY());
			registerFrame.setVisible(true);
			registerFrame.init = true;
			registerFrame.setLoginElements();
		}else {
			registerFrame.setLocation(loginFrame.getX(), loginFrame.getY());
			registerFrame.setVisible(true);
		}
		loginFrame.setVisible(false);
	}
	
	public static void dashboardFrameDisplay() {
		loginFrame.setVisible(false);
	}
}
