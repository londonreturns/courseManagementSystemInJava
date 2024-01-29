package utility;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import course.Course;
import driver.Main;
import font.BigBold;
import font.RegularFont;
import font.SubHeadingFont;
import user.Admin;
import user.Student;
import user.Teacher;

public class MenuFrame extends StandardFrame  implements ActionListener{
	
	static StandardPanel leftPanel = new StandardPanel(0, 0, 400, 800) ;
	static StandardPanel rightPanel = new StandardPanel(400, 0, 800, 700, new Color(0xE5E3E2));
	static StandardPanel bottomPanel = new StandardPanel(400, 700, 800, 100, new Color(0x36454F)) ;
	
	Student student = new Student();
	Teacher teacher = new Teacher();
	Admin admin = new Admin();
	Course course = new Course();
	
	JLabel cmsLabel = new JLabel();
	
	JLabel nameLabel = new JLabel();
	JLabel idLabel = new JLabel();
	ArrayList<Course> courses = new ArrayList<Course>();
	
	ArrayList<MenuButton> menuButtonList = new ArrayList<MenuButton>();
	
	final private String defaultMenu = "Dashboard";
	private String selectedMenu = defaultMenu;
	public static String typeOfUser;
	
	static StandardButton logOut = new StandardButton();
	
	boolean leftPanelInit = false;
	boolean rightPanelInit = false;
	boolean bottomPanelInit = false;
	
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
		setBottomPanel();
		this.add(bottomPanel);
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
			MenuButton menuButton = new MenuButton(Color.black);
			menuButton.setText(menu);
			menuButton.setForeground(Color.white);
			menuButton.setBounds(axisX, axisY, width, height);
			menuButton.setFont(new SubHeadingFont());
			axisY = incrementPosition(axisY);
			leftPanel.add(menuButton);
			menuButton.addActionListener(this);
			menuButtonList.add(menuButton);
			if(selectedMenu.equals(menu)) {
				menuButton.setBackground(Color.white);
				menuButton.setForeground(Color.black);
			}
		}
		leftPanel.validate();
		leftPanel.repaint();
		leftPanel.add(cmsLabel);
		leftPanelInit = true;
	}

	private void setBottomPanel() {
		
		logOut.setText("Log out");
		logOut.setBounds(640, 25, 100, 25);
		
		logOut.addActionListener(this);
		
		bottomPanel.add(logOut);
		bottomPanel.validate();
		bottomPanel.repaint();
		bottomPanelInit = true;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)  {
		try {
			if(e.getSource() == logOut) {
				System.out.println("LOG");
				this.setVisible(false);
				Main.loginFrameDisplay();
			}else {
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
		}catch(Exception exp) {
			String error = exp.getMessage();
	        System.out.println(exp);
	        JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.WARNING_MESSAGE);
		}
		
		
	}
	
	public void newSelectedMenu(MenuButton newMenuButton) {
		selectedMenu = newMenuButton.getText();
		for(MenuButton menuButton : menuButtonList) {
			if(newMenuButton == menuButton) {
				menuButton.setBackground(Color.white);
				menuButton.setForeground(Color.black);
			}else {
				menuButton.setBackground(Color.black);
				menuButton.setForeground(Color.white);
			}
		}
	}
	
	public void changeRightPanel(Student student, String menu) throws ClassNotFoundException {
		rightPanel.removeAll();
		switch (menu) {
			case "Dashboard":
				
				String[] columns = {"Course Name", "Course Id"};
				
				try {
					Class.forName(DatabaseConstant.CLASSNAME);
					Connection conn = DriverManager.getConnection(DatabaseConstant.URL, DatabaseConstant.USERNAME, DatabaseConstant.PASSWORD);
					
					String query1 = "SELECT course_id FROM student_course WHERE student_id = ?";
					
					PreparedStatement pst1 = conn.prepareStatement(query1);
					
					pst1.setString(1, student.getId());
					
					ResultSet result1 = pst1.executeQuery();
					while (result1.next()) {
					    int courseId = result1.getInt("course_id");
					    String query2 = "SELECT course_name FROM course WHERE course_id = ?";
					    
					    PreparedStatement pst = conn.prepareStatement(query2);
						
						pst.setInt(1, courseId);
						
						ResultSet result2 = pst.executeQuery();
						result2.next();
						Course course = new Course(result2.getString("course_name"), courseId);
						student.setCourse(course);
					}
					
					ArrayList<Course> course = student.getCourses();
					
					Object[][] data = new Object[course.size()][];
					for (int i = 0; i < course.size(); i++) {
					    Course tempCourse = course.get(i);
					    Object[] courseData = {tempCourse.getCourseName(), tempCourse.getCourseId()};
					    data[i] = courseData;
					}
			            
					StandardTable table = new StandardTable();
					
					StandardScrollPane sp = createTable(rightPanel, table, data, columns);
					
					TableColumnModel columnModel = table.getColumnModel();
					int columnIndex = Arrays.asList(columns).indexOf("Course Id"); 
					columnModel.getColumn(columnIndex).setMaxWidth(150);
					
					sp.setBounds(50, 500, 350, 100);
					
					rightPanel.add(sp);
					rightPanel.validate();
					rightPanel.repaint();
					break;
					
				}catch (ClassNotFoundException cnfe) {
					String error = cnfe.getMessage();
		            System.out.println(cnfe);
		            JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.WARNING_MESSAGE);
				}catch (SQLException sqle) {
					String error = sqle.getMessage();
		            System.out.println(sqle);
		            JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.WARNING_MESSAGE);
				}
				
				
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
	
	private static StandardScrollPane createTable(StandardPanel panel, StandardTable table, Object[][] data, String[] columns) {
		DefaultTableModel model = new DefaultTableModel() ;
		model.setColumnIdentifiers(columns);
		
		for (int i = 0; i < data.length; i++) {
			model.addRow(data[i]);
		}
		
		table.setModel(model);
		table.setFocusable(false);
		
		StandardScrollPane sp = new StandardScrollPane(table);
		
		sp.setBounds(100, 100, 500, 100);
		sp.setVisible(true);
		return sp;
	}
	
	private static int incrementPosition(int x) {
		return x += 100;
	}

	
	
}
