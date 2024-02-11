package component.button;

import java.awt.Cursor;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import color.Color0;

public class StandardButton extends JButton{
	public StandardButton() {
		Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
		this.setCursor(handCursor);
		this.setFocusable(false);
		this.setBorder(BorderFactory.createLineBorder(new Color0()));
	}
	
	StandardButton(String text){
		super(text);
		Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
		this.setCursor(handCursor);
		this.setFocusable(false);
		this.setBorder(BorderFactory.createLineBorder(new Color0()));
	}
}
