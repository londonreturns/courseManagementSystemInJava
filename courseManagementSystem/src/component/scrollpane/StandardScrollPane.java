package component.scrollpane;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class StandardScrollPane extends JScrollPane{
	public StandardScrollPane() {
		super();
		settings();
	}
	
	public StandardScrollPane(JTable table){
		super(table);
		settings();
	}
	
	public StandardScrollPane(JPanel panel){
		super(panel);
		settings();
	}
	
	private void settings(){
		this.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
	}
}
