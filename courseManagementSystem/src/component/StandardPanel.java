package component;

import java.awt.Color;

import javax.swing.JPanel;

import color.Color3;

public class StandardPanel extends JPanel{

    int x_coord = 0;
    int y_coord = 0;
    int width = 100;
    int height = 100;
    
    public boolean init = false;

    StandardPanel(){
        setBounds(this.x_coord, this.y_coord, this.width, this.height);
        setBackground(new Color3());
        setOpaque(true);
        setLayout(null);
    }
    
    public StandardPanel(int x_coord, int y_coord, int width, int height){
    	setBounds(x_coord, y_coord, width, height);
        setBackground(new Color3());
        setOpaque(true);
        setLayout(null);
    }
    
    StandardPanel(int x_coord, int y_coord, int width, int height, Color color){
    	setBounds(x_coord, y_coord, width, height);
        setBackground(color);
        setOpaque(true);
        setLayout(null);
    }
}
