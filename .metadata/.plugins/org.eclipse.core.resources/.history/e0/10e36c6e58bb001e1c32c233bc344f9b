package courseManagementSystem;

import java.awt.Color;
import java.awt.Container;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class StandardFrame extends JFrame{
	
	int x_coord = 0;
	int y_coord = 0;
	int width = 1000;
	int height = 1000;
	Container container = this.getContentPane();
	
	StandardFrame(){
		setBounds(this.x_coord, this.y_coord, this.width, this.height);
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		ImageIcon logo = new ImageIcon("assets/logo.png");
		setIconImage(logo.getImage());
				
	}
	
	StandardFrame(int x_coord, int y_coord, int width, int height){
		setBounds(x_coord, y_coord, width, height);
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

		ImageIcon logo = new ImageIcon("assets/logo.png");
		setIconImage(logo.getImage());
			
	}
	
	StandardFrame(int x_coord, int y_coord, int width, int height, Color color){
		setBounds(x_coord, y_coord, width, height);
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		container.setBackground(color);
		setResizable(false);

		ImageIcon logo = new ImageIcon("assets/logo.png");
		setIconImage(logo.getImage());
			
	}
}
