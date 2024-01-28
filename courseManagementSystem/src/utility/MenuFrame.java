package utility;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import font.SubHeadingFont;
import user.Admin;
import user.Student;
import user.Teacher;

public class MenuFrame extends StandardFrame  implements ActionListener{
	
	static StandardPanel leftPanel = new StandardPanel(0, 0, 400, 800) ;
	static StandardPanel rightPanel = new StandardPanel(400, 0, 800, 800, Color.GRAY);
	
	Student student = new Student();
	Teacher teacher = new Teacher();
	Admin admin = new Admin();
	
	JLabel cmsLabel = new JLabel();
	
	ArrayList<MenuButton> menuButtonList = new ArrayList<MenuButton>();
	
	private String selectedMenu = "Dashboard";
	public static String typeOfUser;
	
	private int axisX = 107;
	private int axisY = 150;
	private final int width = 185;
	private final int height = 32;
	
	public MenuFrame(){
		super();
		setElements();
	}
	
	public MenuFrame(int x_coord, int y_coord, int width, int height, Student student){
		super(x_coord, y_coord, width, height);
		this.student = student;
		setElements();
	}
	
	public MenuFrame(int x_coord, int y_coord, int width, int height, Teacher teacher){
		super(x_coord, y_coord, width, height);
		this.teacher = teacher;
		setElements();
	}
	
	public MenuFrame(int x_coord, int y_coord, int width, int height, Admin admin){
		super(x_coord, y_coord, width, height);
		this.admin = admin;
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
		
		String[] menuItems = {};
		
		switch (typeOfUser) {
	            case "student":
	                menuItems = new String[] {"Dashboard", "Course", "Teacher", "Settings"};
	                break;
	            case "teacher":
	                menuItems = new String[] {"Dashboard", "Course", "Student", "Settings"};
	                break;
	            case "admin":
	                menuItems = new String[] {"Dashboard", "Course", "Student", "Teacher", "Settings"};
	                break;
	        }
		
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
		leftPanel.validate();
		leftPanel.repaint();
		leftPanel.add(cmsLabel);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (MenuButton menuButton : menuButtonList) {
			if (e.getSource() == menuButton) {
				newSelectedMenu(menuButton);
			}
		}
		if (typeOfUser.equals("student")) {
			changeRightPanel(student, selectedMenu);
		}else if(typeOfUser.equals("teacher")){
			changeRightPanel(teacher, selectedMenu);
		}else {
			changeRightPanel(admin, selectedMenu);
		}
	}
	
	public void newSelectedMenu(MenuButton newMenuButton) {
		selectedMenu = newMenuButton.getText();
		for(MenuButton menuButton : menuButtonList) {
			if(newMenuButton == menuButton) {
				menuButton.setBackground(Color.white);
			}else {
				menuButton.setBackground(Color.pink);
			}
		}
	}
	
	public void changeRightPanel(Student student, String menu) {
		rightPanel.removeAll();
		switch (menu) {
			case "Dashboard":
				JLabel text = new JLabel(student.getName());
				text.setBounds(0, 0, 100, 100);
				rightPanel.add(text);
				break;
			case "Course":
				JLabel text2 = new JLabel(student.getId());
				text2.setBounds(0, 0, 100, 100);
				rightPanel.add(text2);
				break;
			case "Teacher":
				JLabel text3 = new JLabel(student.getFaculty());
				text3.setBounds(0, 0, 100, 100);
				rightPanel.add(text3);
				break;
			case "Settings":
				JLabel text4 = new JLabel(student.getEmail());
				text4.setBounds(0, 0, 100, 100);
				rightPanel.add(text4);
				break;
		}
		rightPanel.repaint();
		rightPanel.validate();
		add(rightPanel);
		
		System.out.println("right panel : " + rightPanel.getComponents());
		
	}
	
	public void changeRightPanel(Teacher teacher, String menu) {
		rightPanel.removeAll();
		switch (menu) {
			case "Dashboard":
				JLabel text = new JLabel(teacher.getName());
				text.setBounds(0, 0, 100, 100);
				rightPanel.add(text);
				break;
			case "Course":
				JLabel text2 = new JLabel(teacher.getId());
				text2.setBounds(0, 0, 100, 100);
				rightPanel.add(text2);
				break;
			case "Student":
				JLabel text3 = new JLabel(teacher.getContact());
				text3.setBounds(0, 0, 100, 100);
				rightPanel.add(text3);
				break;
			case "Settings":
				JLabel text4 = new JLabel(teacher.getEmail());
				text4.setBounds(0, 0, 100, 100);
				rightPanel.add(text4);
				break;
		}
		rightPanel.repaint();
		rightPanel.validate();
		add(rightPanel);
		
		System.out.println("right panel : " + rightPanel.getComponents());
		
	}
	
	public void changeRightPanel(Admin admin, String menu) {
		rightPanel.removeAll();
		switch (menu) {
			case "Dashboard":
				JLabel text = new JLabel(admin.getName());
				text.setBounds(0, 0, 100, 100);
				rightPanel.add(text);
				break;
			case "Course":
				JLabel text2 = new JLabel(admin.getId());
				text2.setBounds(0, 0, 100, 100);
				rightPanel.add(text2);
				break;
			case "Student":
				JLabel text3 = new JLabel(admin.getEmail());
				text3.setBounds(0, 0, 100, 100);
				rightPanel.add(text3);
				break;
			case "Teacher":
				JLabel text4 = new JLabel(admin.getDateOfBirth().toString());
				text4.setBounds(0, 0, 100, 100);
				rightPanel.add(text4);
				break;
			case "Settings":
				JLabel text5 = new JLabel(admin.getPassword());
				text5.setBounds(0, 0, 100, 100);
				rightPanel.add(text5);
				break;
		}
		rightPanel.repaint();
		rightPanel.validate();
		add(rightPanel);
		
		System.out.println("right panel : " + rightPanel.getComponents());
		
	}
	
	public void setSelectedMenu(String menu) {
		selectedMenu = menu;
	}
	
	public String getSelectedMenu() {
		return selectedMenu;
	}
	
	private static int incrementPosition(int x) {
		return x += 100;
	}
	
	
}
