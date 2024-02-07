package driver;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import component.frame.AdminFrame;
import component.frame.LoginFrame;
import component.frame.MenuFrame;
import component.frame.RegisterFrame;
import component.frame.StudentFrame;
import component.frame.TeacherFrame;
import user.Admin;
import user.Student;
import user.Teacher;

public class Main {
	static LoginFrame loginFrame = new LoginFrame(200, 200, 650, 650);
	static RegisterFrame registerFrame = new RegisterFrame(200, 200, 650, 750);
	static Student student = new Student();
	static Teacher teacher = new Teacher();
	static Admin admin = new Admin();
	
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
		registerFrame.typeOfUserCombo.setSelectedItem("Student");
		registerFrame.setVisible(true);
		loginFrame.setVisible(false);
	}
	
	public static void studentFrameDisplay(JFrame previousFrame, Student student) {
		StudentFrame studentFrame = new StudentFrame(250, 150, 1200, 800);
		studentFrame.typeOfUser = student.getTypeOfUser();
	    studentFrame.setVisible(true);  // Set studentFrame visible

	    previousFrame.setVisible(false);
	    if (!studentFrame.init) {
	        studentFrame.init = true;
	    }
	    try {
	        studentFrame.changeRightPanel(student, "Dashboard");
	    } catch (Exception exp) {
	        String error = exp.getMessage();
	        System.out.println(exp);
	        JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.WARNING_MESSAGE);
	    }
		
	}
	
	public static void teacherFrameDisplay(JFrame previousFrame, Teacher teacher) {
		TeacherFrame teacherFrame = new TeacherFrame(250, 150, 1200, 800);
		teacherFrame.typeOfUser = teacher.getTypeOfUser();
	    teacherFrame.setVisible(true);  // Set teacherFrame visible
	    previousFrame.setVisible(false);
	    if (!teacherFrame.init) {
	        teacherFrame.init = true;
	    }
	    try {
	        teacherFrame.changeRightPanel(teacher, "Dashboard");
	    } catch (Exception exp) {
	        String error = exp.getMessage();
	        System.out.println(exp);
	        JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.WARNING_MESSAGE);
	    }
		
	}
	
	public static void adminFrameDisplay(JFrame previousFrame, Admin admin) {
		AdminFrame adminFrame = new AdminFrame(250, 150, 1200, 800);
		adminFrame.typeOfUser = admin.getTypeOfUser();
		adminFrame.setVisible(true); 
	    previousFrame.setVisible(false);
	    if (!adminFrame.init) {
	    	adminFrame.init = true;
	    }
	    try {
	    	adminFrame.changeRightPanel(admin, "Dashboard");
	    } catch (Exception exp) {
	        String error = exp.getMessage();
	        System.out.println(exp);
	        JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.WARNING_MESSAGE);
	    }
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
