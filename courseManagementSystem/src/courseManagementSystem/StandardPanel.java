package courseManagementSystem;

import java.awt.Color;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class StandardPanel extends JPanel{
	
	int x_coord;
	int y_coord;
	int width;
	int height;
	boolean opacity;
	
	StandardPanel(){
		this.setOpaque(opacity);
		this.setBounds(x_coord, y_coord, width, height);
	}
	
	StandardPanel(int x_coord, int y_coord, int width, int height, boolean opacity){
		this.x_coord = x_coord;
		this.y_coord = y_coord;
		this.width = width;
		this.height = height;
		this.opacity = opacity;
		
		this.setOpaque(opacity);
		this.setBounds(x_coord, y_coord, width, height);
	}
	
	
}
