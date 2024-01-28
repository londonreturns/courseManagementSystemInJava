package utility;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import font.SubHeadingFont;

public class MenuFrame extends StandardFrame  implements ActionListener{
	
	static StandardPanel leftPanel = new StandardPanel(0, 0, 400, 800);
	static StandardPanel rightPanel = new StandardPanel(400, 0, 800, 800, Color.GRAY);
	
	JLabel cmsLabel = new JLabel();
	
	ArrayList<MenuButton> menuButtonList = new ArrayList<MenuButton>();
	
	public String selectedMenu = "Dashboard";
	
	private int axisX = 107;
	private int axisY = 200;
	private final int width = 185;
	private final int height = 32;
	
	public MenuFrame(){
		super();
		setElements();
	}
	
	public MenuFrame(int x_coord, int y_coord, int width, int height){
		super(x_coord, y_coord, width, height);
		setElements();
	}
	
	public MenuFrame(int x_coord, int y_coord, int width, int height, Color color){
		super(x_coord, y_coord, width, height, color);
		setElements();
	}

	public void setElements() {
		setLeftPanel();
		this.add(leftPanel);
	}

	private void setLeftPanel() {
		
		cmsLabel.setBounds(10, 0, 1000, 100);
		ImageIcon cmsImageIcon = new ImageIcon("assets/cms2.png");
		cmsLabel.setIcon(cmsImageIcon);
		
		String[] menuItems = {
				"Dashboard", "Course", "Teacher", "Settings"
		};
		
		for (String menu : menuItems) {
			MenuButton menuButton = new MenuButton(Color.pink);
			menuButton.setText(menu);
			menuButton.setBounds(axisX, axisY, width, height);
			menuButton.setFont(new SubHeadingFont());
			axisY = incrementPosition(axisY);
			leftPanel.add(menuButton);
			menuButton.addActionListener(this);
			menuButtonList.add(menuButton);
			if(selectedMenu.equals(menu)) {
				menuButton.setBackground(Color.white);
			}
		}
		
		leftPanel.add(cmsLabel);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (MenuButton menuButton : menuButtonList) {
			if (e.getSource() == menuButton) {
				newSelectedMenu(menuButton);
			}
		}
	}
	
	public void newSelectedMenu(MenuButton newMenuButton) {
		selectedMenu = newMenuButton.getText();
		System.out.println("asdasda");
		for(MenuButton menuButton : menuButtonList) {
			if(newMenuButton == menuButton) {
				menuButton.setBackground(Color.white);
			}else {
				menuButton.setBackground(Color.pink);
			}
		}
	}
	
	private static int incrementPosition(int x) {
		return x += 100;
	}
	
	
}
