package courseManagementSystem;

import java.awt.Color;

public class Main {
	public static void main(String[] args) {
		LoginFrame loginFrame = new LoginFrame(0, 0, 1000, 1000, Color.black);
		loginFrame.setElements();
		loginFrame.setVisible(true);
	}	
}
