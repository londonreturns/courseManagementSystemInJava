package component.table;

import java.awt.Color;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import font.RegularFont;

public class StandardTable extends JTable{
	
	public StandardTable() {
		super();
		this.settings();
	}
	
	StandardTable(DefaultTableModel model){
		super(model);
		this.settings();
	}
	
	private void settings() {
		RegularFont headerFont = new RegularFont();
		this.getTableHeader().setFont(headerFont);
		this.getTableHeader().setBackground(Color.black);
		this.getTableHeader().setForeground(Color.white);
		this.setBackground(Color.white);
		this.setForeground(Color.black);
		this.setFont(new RegularFont());
		this.setRowHeight(25);
	}
	
	public void setUneditable() {
		for (int i = 0; i < this.getColumnCount(); i++) {
	        this.setDefaultEditor(this.getColumnClass(i), null);
	    }
	}
	
}
