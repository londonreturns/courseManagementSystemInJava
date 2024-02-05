package component;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class StandardScrollPane extends JScrollPane{
	public StandardScrollPane() {
		super();
		settings();
	}
	
	StandardScrollPane(JTable table){
		super(table);
		settings();
	}
	
	StandardScrollPane(JPanel panel){
		super(panel);
		settings();
	}
	
	private void settings(){
		this.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
	}
}
