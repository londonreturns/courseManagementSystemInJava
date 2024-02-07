package component.frame;

import java.awt.Color;
import java.awt.Container;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import color.Color2;

public class StandardFrame extends JFrame{
	
	String title = "Course Management System";
	int x_coord = 0;
	int y_coord = 0;
	int width = 1000;
	int height = 1000;
	public boolean init = false;
	Container container = this.getContentPane();
	
	StandardFrame(){
		setTitle(title);
		setBounds(this.x_coord, this.y_coord, this.width, this.height);
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		ImageIcon logo = new ImageIcon("assets/logo.png");
		setIconImage(logo.getImage());	
		container.setBackground(new Color2());
		setLocationRelativeTo(null);
	}
	
	StandardFrame(int x_coord, int y_coord, int width, int height){
		setTitle(title);
		setBounds(x_coord, y_coord, width, height);
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		ImageIcon logo = new ImageIcon("assets/logo.png");
		setIconImage(logo.getImage());
		container.setBackground(new Color2());
		setLocationRelativeTo(null);
	}
	
	StandardFrame(int x_coord, int y_coord, int width, int height, Color color){
		setTitle(title);
		setBounds(x_coord, y_coord, width, height);
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		container.setBackground(color);
		setResizable(false);
		ImageIcon logo = new ImageIcon("assets/logo.png");
		setIconImage(logo.getImage());
		container.setBackground(new Color2());
		setLocationRelativeTo(null);
	}
	
	
}
