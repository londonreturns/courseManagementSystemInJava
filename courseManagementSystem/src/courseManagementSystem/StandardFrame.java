package courseManagementSystem;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class StandardFrame extends JFrame{
	
	int width = 500;
	int height = 500;
	boolean visibility = true;
	
	StandardFrame(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(visibility);
		setSize(width, height);
	}
	
	StandardFrame(int width, int height){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(visibility);
		setSize(width, height);
	}
	
	
}
