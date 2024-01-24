package courseManagementSystem;

import java.awt.Cursor;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class StandardButton extends JButton{
	StandardButton() {
		Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
		this.setCursor(handCursor);
	}
	
	StandardButton(String text){
		super(text);
		Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
		this.setCursor(handCursor);
	}
}
