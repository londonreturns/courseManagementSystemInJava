package component.frame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.GeneralPath;
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
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.text.StyleContext.SmallAttributeSet;

import component.button.MenuButton;
import component.button.StandardButton;
import component.frame.course.AddCourseFrame;
import component.frame.course.DisableCourseFrame;
import component.frame.course.EditCourseFrame;
import component.frame.course.EnableCourseFrame;
import component.frame.course.RemoveCourseFrame;
import component.frame.student.GenerateReportFrame;
import component.frame.teacher.AssignCourseFrame;
import component.frame.teacher.UnassignCourseFrame;
import component.panel.StandardPanel;
import component.scrollpane.StandardScrollPane;
import component.table.StandardTable;
import course.Course;
import driver.Main;
import font.BigBold;
import font.RegularFont;
import font.SmallBold;
import font.SubHeadingFont;
import user.Admin;
import user.Student;
import user.Teacher;
import utility.DatabaseConstant;

public class MenuFrame extends StandardFrame  implements ActionListener{
	
	static StandardPanel leftPanel = new StandardPanel(0, 0, 400, 800) ;
	static StandardPanel rightPanel = new StandardPanel(400, 0, 800, 700, new Color(0xE5E3E2));
	static StandardPanel bottomPanel = new StandardPanel(400, 700, 800, 100, new Color(0x36454F)) ;
	
	public MenuFrame(int x_coord, int y_coord, int width, int height, String typeOfUser){
		super(x_coord, y_coord, width, height);
		setElements(typeOfUser);
		this.typeOfUser = typeOfUser;
	}
	
	private AddCourseFrame addCourseFrame;
    private EditCourseFrame editCourseFrame;
    private EnableCourseFrame enableCourseFrame;
    private DisableCourseFrame disableCourseFrame;
    private RemoveCourseFrame removeCourseFrame;
    private GenerateReportFrame generateReportFrame;
    private AssignCourseFrame assignCourseFrame;
    private UnassignCourseFrame unassignCourseFrame;
	
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
	public String typeOfUser;
	
	static StandardButton logOut = new StandardButton();
	
	static StandardButton addCourseButton = new StandardButton();
	static StandardButton editCourseButton = new StandardButton();
	static StandardButton enableCourseButton = new StandardButton();
	static StandardButton disableCourseButton = new StandardButton();
	static StandardButton removeCourseButton = new StandardButton();
	
	static StandardButton generateReportButton = new StandardButton();
	
	static StandardButton assignCourseButton = new StandardButton();
	static StandardButton unassignCourseButton = new StandardButton();
	
	boolean leftPanelInit = false;
	boolean rightPanelInit = false;
	boolean bottomPanelInit = false;
	
	private int axisX = 107;
	private int axisY = 150;
	private final int width = 185;
	private final int height = 32;
	

	public void setElements(String typeOfUser) {
	    setLeftPanel(typeOfUser);
	    setBottomPanel();
		this.add(leftPanel);
		this.add(bottomPanel);
	}

	private void setLeftPanel(String typeOfUser) {
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
		this.student = student;
		rightPanel.removeAll();
		switch (menu) {
			case "Dashboard":				
				try {
					String[] columns = {"Course Name", "Course Id"};
					Class.forName(DatabaseConstant.CLASSNAME);
					Connection conn = DriverManager.getConnection(DatabaseConstant.URL, DatabaseConstant.USERNAME, DatabaseConstant.PASSWORD);
					
					String query1 = "SELECT course_id FROM student_course WHERE student_id = ?";
					
					PreparedStatement pst1 = conn.prepareStatement(query1);
					
					pst1.setString(1, student.getId());
					
					ResultSet result1 = pst1.executeQuery();
					while (result1.next()) {
					    String courseId = result1.getString("course_id");
					    String query2 = "SELECT course_name FROM course WHERE course_id = ?";
					    
					    PreparedStatement pst = conn.prepareStatement(query2);
						
						pst.setString(1, courseId);
						
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
			        
					JLabel nameLabel = new JLabel("Name:");
					nameLabel.setBounds(40, 0, 120, 100);
					nameLabel.setFont(new BigBold());
					nameLabel.setHorizontalAlignment(SwingConstants.LEFT);

					JLabel idLabel = new JLabel("Id:");
					idLabel.setBounds(480, 0, 60, 100);
					idLabel.setFont(new BigBold());
					idLabel.setHorizontalAlignment(SwingConstants.LEFT);

					JLabel numberOfCourseLabel = new JLabel("No. of courses currently enrolled:");
					numberOfCourseLabel.setBounds(40, 150, 530, 100);
					numberOfCourseLabel.setFont(new BigBold());
					numberOfCourseLabel.setHorizontalAlignment(SwingConstants.LEFT);

					JLabel name = new JLabel(student.getName());
					name.setBounds(170, 0, 400, 100);
					name.setFont(new SmallBold());

					JLabel id = new JLabel(student.getId());
					id.setBounds(550, 0, 400, 100);
					id.setFont(new SmallBold());

					JLabel numberOfCourse = new JLabel(student.getNumberOfCourses());
					numberOfCourse.setBounds(580, 150, 100, 100);
					numberOfCourse.setFont(new SmallBold());

					
					System.out.println(student.getNumberOfCourses());
					
					StandardTable table = new StandardTable();
					
					StandardScrollPane sp = createTable(rightPanel, table, data, columns);
					
					TableColumnModel columnModel = table.getColumnModel();
					int columnIndex = Arrays.asList(columns).indexOf("Course Id"); 
					columnModel.getColumn(columnIndex).setMaxWidth(150);
					
					sp.setBounds(200, 350, 400, 200);
					
					table.setUneditable();
					
					rightPanel.add(sp);
					rightPanel.add(nameLabel);
					rightPanel.add(idLabel);
					rightPanel.add(numberOfCourseLabel);
					rightPanel.add(name);
					rightPanel.add(id);
					rightPanel.add(numberOfCourse);
					rightPanel.validate();
					rightPanel.repaint();
					
					course.removeAll(course);
					
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
	
	public void changeRightPanel(Teacher teacher, String menu) throws ClassNotFoundException {
		
	}
	
	public void changeRightPanel(Admin admin, String menu) throws ClassNotFoundException {
		this.admin = admin;
		rightPanel.removeAll();
		switch (menu) {
			case "Dashboard":				
				
				break;
			case "Course":
				try {
					
					JLabel allCourseLabel = new JLabel();
					allCourseLabel.setText("All courses:");
					allCourseLabel.setFont(new BigBold());
					allCourseLabel.setBounds(40, 0, 400, 100);
					
					ArrayList<Course> courses = new ArrayList<Course>();
					String[] columns = {"Course Name", "Course Id", "Faculty", "Level", "Enabled"};
					Class.forName(DatabaseConstant.CLASSNAME);
					Connection conn = DriverManager.getConnection(DatabaseConstant.URL, DatabaseConstant.USERNAME, DatabaseConstant.PASSWORD);

					String query = "SELECT * FROM course";

					PreparedStatement pst = conn.prepareStatement(query);
					ResultSet result = pst.executeQuery();

					while (result.next()) {
					    String courseId = result.getString("course_id");
					    String courseName = result.getString("course_name");
					    String faculty = result.getString("faculty");
					    String level = result.getString("level");
					    String isEnabled = result.getString("is_active");

					    Course course = new Course(courseName, courseId, faculty, level, isEnabled);
					    courses.add(course);
					}

					Object[][] cData = new Object[courses.size()][];
					for (int i = 0; i < courses.size(); i++) {
					    Course tempCourse = courses.get(i);
					    Object[] courseData = {tempCourse.getCourseName(), tempCourse.getCourseId(), tempCourse.getFaculty(), tempCourse.getLevel(), tempCourse.getIsEnabled()};
					    cData[i] = courseData;
					}

					StandardTable courseTable = new StandardTable();
					StandardScrollPane sp = createTable(rightPanel, courseTable, cData, columns);

					TableColumnModel columnModel = courseTable.getColumnModel();
					int nameIndex = Arrays.asList(columns).indexOf("Course Name"); 
					columnModel.getColumn(nameIndex).setMaxWidth(600);
					int idIndex = Arrays.asList(columns).indexOf("Course Id"); 
					columnModel.getColumn(idIndex).setMaxWidth(150);
					int facultyIndex = Arrays.asList(columns).indexOf("Faculty"); 
					columnModel.getColumn(facultyIndex).setMaxWidth(150);
					int levelIndex = Arrays.asList(columns).indexOf("Level"); 
					columnModel.getColumn(levelIndex).setMaxWidth(150);
					int isEnabledIndex = Arrays.asList(columns).indexOf("Enabled"); 
					columnModel.getColumn(isEnabledIndex).setMaxWidth(150);

					sp.setBounds(10, 125, 655, 500);
					courseTable.setUneditable();
					
					addCourseButton.setText("Add");
					editCourseButton.setText("Edit");
					enableCourseButton.setText("Enable");
					disableCourseButton.setText("Disable");
					removeCourseButton.setText("Remove");
					
					ArrayList<StandardButton> buttons = new ArrayList<>(Arrays.asList(
							addCourseButton, editCourseButton, enableCourseButton, disableCourseButton, removeCourseButton
					));
					
					axisX = 675;
					axisY = 150;
					
					for (StandardButton button : buttons) {
						button.addActionListener(this);
						button.setBounds(axisX, axisY, 100, 35);
						rightPanel.add(button);
						axisY = incrementPosition(axisY);
					}
					
					rightPanel.add(allCourseLabel);
					rightPanel.add(sp);
					
				}catch (SQLException sqle) {
					String error = sqle.getMessage();
		            System.out.println(sqle);
		            JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.WARNING_MESSAGE);
				}
				break;
			case "Student":
				
				try {
					JLabel allStudentLabel = new JLabel();
				    allStudentLabel.setText("All Students:");
				    allStudentLabel.setFont(new BigBold());
				    allStudentLabel.setBounds(40, 0, 400, 100);

				    ArrayList<Student> students = new ArrayList<Student>();
				    String[] columns = {"Name", "Id", "Faculty", "Level", "Courses"};
				    Class.forName(DatabaseConstant.CLASSNAME);
				    Connection conn = DriverManager.getConnection(DatabaseConstant.URL, DatabaseConstant.USERNAME, DatabaseConstant.PASSWORD);

				    String query = "SELECT * FROM student";

				    PreparedStatement pst = conn.prepareStatement(query);
				    ResultSet result = pst.executeQuery();

				    while (result.next()) {
				        String studentId = result.getString("student_id");
				        String studentName = result.getString("name");
				        String faculty = result.getString("faculty");
				        String level = result.getString("level");

				        Student student = new Student();
				        student.setName(studentName);
				        student.setId(studentId);
				        student.setFaculty(faculty);
				        student.setLevel(level);

				        String courseQuery = "SELECT course_id FROM student_course WHERE student_id = ?";
				        PreparedStatement coursePst = conn.prepareStatement(courseQuery);
				        coursePst.setString(1, studentId);
				        ResultSet courseResult = coursePst.executeQuery();

				        boolean studentHasCourses = false; // Flag to check if the student has any courses

				        while (courseResult.next()) {
				            studentHasCourses = true;

				            String courseId = courseResult.getString("course_id");
				            String courseNameQuery = "SELECT course_name FROM course WHERE course_id = ?";
				            PreparedStatement courseNamePst = conn.prepareStatement(courseNameQuery);
				            courseNamePst.setString(1, courseId);
				            ResultSet courseNameResult = courseNamePst.executeQuery();

				            while (courseNameResult.next()) {
				                String courseName = courseNameResult.getString("course_name");

				                
				                Student courseStudent = new Student();
				                courseStudent.setName(studentName);
				                courseStudent.setId(studentId);
				                courseStudent.setFaculty(faculty);
				                courseStudent.setLevel(level);
				                courseStudent.setCoursesString(courseName);

				                students.add(courseStudent);
				            }
				        }

				        
				        if (!studentHasCourses) {
				            students.add(student);
				        }
				    }

				    Object[][] sData = new Object[students.size()][];
				    for (int i = 0; i < students.size(); i++) {
				        Student tempStudent = students.get(i);
				        Object[] studentData = {tempStudent.getName(), tempStudent.getId(), tempStudent.getFaculty(), tempStudent.getLevel(), tempStudent.getCoursesString()};
				        sData[i] = studentData;
				    }

					StandardTable courseTable = new StandardTable();
					StandardScrollPane sp = createTable(rightPanel, courseTable, sData, columns);
					
					TableColumnModel columnModel = courseTable.getColumnModel();
					int nameIndex = Arrays.asList(columns).indexOf("Name"); 
					columnModel.getColumn(nameIndex).setMaxWidth(150);
					int idIndex = Arrays.asList(columns).indexOf("Id"); 
					columnModel.getColumn(idIndex).setMaxWidth(100);
					int facultyIndex = Arrays.asList(columns).indexOf("Faculty"); 
					columnModel.getColumn(facultyIndex).setMaxWidth(100);
					int levelIndex = Arrays.asList(columns).indexOf("Level"); 
					columnModel.getColumn(levelIndex).setMaxWidth(100);
					int coursesIndex = Arrays.asList(columns).indexOf("Courses"); 
					columnModel.getColumn(coursesIndex).setMaxWidth(600);

					sp.setBounds(10, 125, 770, 500);
					courseTable.setUneditable();
					
					generateReportButton.setText("Generate Report");
					generateReportButton.addActionListener(this);
					generateReportButton.setBounds(300, 645, 200, 35);
					
					rightPanel.add(generateReportButton);
					rightPanel.add(allStudentLabel);
					rightPanel.add(sp);
					
				}catch (SQLException sqle) {
					String error = sqle.getMessage();
		            System.out.println(sqle);
		            JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.WARNING_MESSAGE);
				}

				break;
			case "Teacher":
				
				try {
					JLabel allCourseLabel = new JLabel();
					allCourseLabel.setText("All Teachers:");
					allCourseLabel.setFont(new BigBold());
					allCourseLabel.setBounds(40, 0, 400, 100);

					ArrayList<Teacher> teachers = new ArrayList<Teacher>();
					String[] columns = {"Name", "Id", "Courses"};
					Class.forName(DatabaseConstant.CLASSNAME);
					Connection conn = DriverManager.getConnection(DatabaseConstant.URL, DatabaseConstant.USERNAME, DatabaseConstant.PASSWORD);

					String query = "SELECT * FROM teacher";

					PreparedStatement pst = conn.prepareStatement(query);
					ResultSet result = pst.executeQuery();

					while (result.next()) {
				        String teacherId = result.getString("teacher_id");
				        String teacherName = result.getString("name");

				        Teacher teacher = new Teacher();
				        teacher.setName(teacherName);
				        teacher.setId(teacherId);

				        String courseQuery = "SELECT course_id FROM teacher_course WHERE teacher_id = ?";
				        PreparedStatement coursePst = conn.prepareStatement(courseQuery);
				        coursePst.setString(1, teacher.getId());
				        ResultSet courseResult = coursePst.executeQuery();

				        boolean teacherHasCourses = false; 
				        
				        while (courseResult.next()) {
				            teacherHasCourses = true;

				            String courseId = courseResult.getString("course_id");
				            String courseNameQuery = "SELECT course_name FROM course WHERE course_id = ?";
				            PreparedStatement courseNamePst = conn.prepareStatement(courseNameQuery);
				            courseNamePst.setString(1, courseId);
				            ResultSet courseNameResult = courseNamePst.executeQuery();

				            while (courseNameResult.next()) {
				                String courseName = courseNameResult.getString("course_name");

				                
				                Teacher courseTeacher = new Teacher();
				                courseTeacher.setName(teacherName);
				                courseTeacher.setId(teacherId);
				                courseTeacher.setCoursesString(courseName);

				                teachers.add(courseTeacher);
				            }
				        }

				        
				        if (!teacherHasCourses) {
				            teachers.add(teacher);
				        }
				    }

				    Object[][] sData = new Object[teachers.size()][];
				    for (int i = 0; i < teachers.size(); i++) {
				        Teacher tempTeacher = teachers.get(i);
				        Object[] teacherData = {tempTeacher.getName(), tempTeacher.getId(), tempTeacher.getCoursesString()};
				        sData[i] = teacherData;
				    }

					StandardTable courseTable = new StandardTable();
					StandardScrollPane sp = createTable(rightPanel, courseTable, sData, columns);

					TableColumnModel columnModel = courseTable.getColumnModel();
					int nameIndex = Arrays.asList(columns).indexOf("Name");
					columnModel.getColumn(nameIndex).setMaxWidth(150);
					int idIndex = Arrays.asList(columns).indexOf("Id");
					columnModel.getColumn(idIndex).setMaxWidth(100);
					int coursesIndex = Arrays.asList(columns).indexOf("Courses");
					columnModel.getColumn(coursesIndex).setMaxWidth(600);

					sp.setBounds(10, 125, 770, 500);
					courseTable.setUneditable();

					assignCourseButton.setText("Assign Course");
					assignCourseButton.addActionListener(this);
					assignCourseButton.setBounds(150, 645, 200, 35);
					
					unassignCourseButton.setText("Unassign Course");
					unassignCourseButton.addActionListener(this);
					unassignCourseButton.setBounds(400, 645, 200, 35);

					rightPanel.add(assignCourseButton);
					rightPanel.add(unassignCourseButton);
					rightPanel.add(allCourseLabel);
					rightPanel.add(sp);
					
				}catch (SQLException sqle) {
					String error = sqle.getMessage();
		            System.out.println(sqle);
		            JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.WARNING_MESSAGE);
				}
				break;
			case "Settings":
				
				break;
		}
		rightPanel.repaint();
		rightPanel.validate();
		add(rightPanel);		
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
			if(e.getSource() == logOut) {
				System.out.println("LOG");
				this.setVisible(false);
				rightPanel.removeAll();
				leftPanel.removeAll();
				bottomPanel.removeAll();
				this.dispose();
				Main.loginFrameDisplay();
			} else if (e.getSource() == addCourseButton) {
			    if (addCourseFrame != null) {
			    	addCourseFrame.removeAll();
			        addCourseFrame.dispose();
			    }
			    addCourseFrame = new AddCourseFrame(0, 0, 500, 400);
			    addCourseFrame.setVisible(true);
			    addCourseFrame.toFront();
			    repaint();
			    revalidate();
			} else if (e.getSource() == editCourseButton) {
			    if (editCourseFrame != null) {
			    	editCourseFrame.removeAll();
			        editCourseFrame.dispose();
			    }
			    editCourseFrame = new EditCourseFrame(0, 0, 500, 400);
			    editCourseFrame.setVisible(true);
			    editCourseFrame.toFront();
			    repaint();
			    revalidate();
			} else if (e.getSource() == enableCourseButton) {
			    if (enableCourseFrame != null) {
			    	enableCourseFrame.removeAll();
			        enableCourseFrame.dispose();
			    }
			    enableCourseFrame = new EnableCourseFrame(0, 0, 500, 400);
			    enableCourseFrame.setVisible(true);
			    enableCourseFrame.toFront();
			    repaint();
			    revalidate();
			} else if (e.getSource() == disableCourseButton) {
			    if (disableCourseFrame != null) {
			    	disableCourseFrame.removeAll();
			        disableCourseFrame.dispose();
			    }
			    disableCourseFrame = new DisableCourseFrame(0, 0, 500, 400);
			    disableCourseFrame.setVisible(true);
			    disableCourseFrame.toFront();
			    repaint();
			    revalidate();
			} else if (e.getSource() == removeCourseButton) {
			    if (removeCourseFrame != null) {
			    	removeCourseFrame.removeAll();
			        removeCourseFrame.dispose();
			    }
			    removeCourseFrame = new RemoveCourseFrame(0, 0, 500, 400);
			    removeCourseFrame.setVisible(true);
			    removeCourseFrame.toFront();
			    repaint();
			    revalidate();
			} else if (e.getSource() == generateReportButton) {
			    if (generateReportFrame != null) {
			    	generateReportFrame.removeAll();
			        generateReportFrame.dispose();
			    }
			    generateReportFrame = new GenerateReportFrame(0, 0, 500, 400);
			    generateReportFrame.setVisible(true);
			    generateReportFrame.toFront();
			    repaint();
			    revalidate();
			} else if (e.getSource() == assignCourseButton) {
			    if (assignCourseFrame != null) {
			    	assignCourseFrame.removeAll();
			        assignCourseFrame.dispose();
			    }
			    assignCourseFrame = new AssignCourseFrame(0, 0, 500, 400);
			    assignCourseFrame.setVisible(true);
			    assignCourseFrame.toFront();
			    repaint();
			    revalidate();
			}else if (e.getSource() == unassignCourseButton) {
			    if (unassignCourseFrame != null) {
			    	unassignCourseFrame.removeAll();
			        unassignCourseFrame.dispose();
			    }
			    unassignCourseFrame = new UnassignCourseFrame(0, 0, 500, 400);
			    unassignCourseFrame.setVisible(true);
			    unassignCourseFrame.toFront();
			    repaint();
			    revalidate();
			}else {
				for (MenuButton menuButton : menuButtonList) {
					if (e.getSource() == menuButton) {
						newSelectedMenu(menuButton);
					}
				}
				try {
					changePanel(typeOfUser);
				}catch(Exception exp) {
					System.out.println(exp);
				}
				
			}
		}
	
	
	private void changePanel(String typeOfUser2) throws ClassNotFoundException{
		if (typeOfUser.equals("student")) {
			changeRightPanel(student, selectedMenu);
		}else if(typeOfUser.equals("teacher")){
			changeRightPanel(teacher, selectedMenu);
		}else {
			changeRightPanel(admin, selectedMenu);
		}
		
	}

	private static int incrementPosition(int x) {
		return x += 100;
	}

	
	
}
