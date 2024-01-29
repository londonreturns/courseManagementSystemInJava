package driver;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JFrame;

import user.Admin;
import user.Student;
import user.Teacher;
import utility.AdminFrame;
import utility.LoginFrame;
import utility.RegisterFrame;
import utility.StudentFrame;
import utility.TeacherFrame;
import utility.MenuFrame;

public class Main {
	static LoginFrame loginFrame = new LoginFrame(200, 200, 1000, 750);
	static RegisterFrame registerFrame = new RegisterFrame(200, 200, 1000, 750);
	
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
			registerFrame.init = true;
		}
		registerFrame.setRegisterElements();
		registerFrame.setLocation(loginFrame.getX(), loginFrame.getY());
		registerFrame.setVisible(true);
		loginFrame.setVisible(false);
	}
	
	public static void studentFrameDisplay(JFrame previousFrame, Student student) {
		MenuFrame.typeOfUser = student.getTypeOfUser();
		StudentFrame studentFrame = new StudentFrame(250, 150, 1200, 800, student);

		previousFrame.setVisible(false);
		if(!studentFrame.init) {
			studentFrame.init = true;
		}
		studentFrame.changeRightPanel(student, "Dashboard");
		studentFrame.setVisible(true);
	}
	
	public static void teacherFrameDisplay(JFrame previousFrame, Teacher teacher) {
		MenuFrame.typeOfUser = teacher.getTypeOfUser();
		TeacherFrame teacherFrame = new TeacherFrame(250, 150, 1200, 800, teacher);
		
		previousFrame.setVisible(false);
		if(!teacherFrame.init) {
			teacherFrame.init = true;
			teacherFrame.changeRightPanel(teacher, "Dashboard");
		}
		teacherFrame.setVisible(true);
	}
	
	public static void adminFrameDisplay(JFrame previousFrame, Admin admin) {
		MenuFrame.typeOfUser = admin.getTypeOfUser();
		AdminFrame adminFrame = new AdminFrame(200, 200, 1000, 750, admin);
		
		previousFrame.setVisible(false);
		if(!adminFrame.init) {
			adminFrame.init = true;
			adminFrame.changeRightPanel(admin, "Dashboard");
		}
		adminFrame.setVisible(true);
	}
	
	public static String hashAlgorithm(String pass) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("SHA");
		
		messageDigest.update(pass.getBytes());
		
		byte[] result = messageDigest.digest();
		
		StringBuilder sb = new StringBuilder();
		
		for (byte b: result) {
			sb.append(String.format("%02x", b));
		}
		
		if(sb.toString().equals("")) {
			return "";
		}else {
			return sb.toString();
		}
		
	}

	
}
