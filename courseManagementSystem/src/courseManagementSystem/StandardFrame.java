package courseManagementSystem;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class StandardFrame extends JFrame{
	
	int width = 500;
	int height = 500;
	boolean visibility = true;
	String logoPath = "assets/logo.png";
	
	StandardFrame(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(visibility);
		setSize(width, height);
		setResizable(false);
		ImageIcon logo = new ImageIcon(logoPath);
		setIconImage(logo.getImage());
	}
	
	StandardFrame(int width, int height){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(visibility);
		setSize(width, height);
	}
	
	
}
