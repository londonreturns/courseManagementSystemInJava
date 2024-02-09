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
import java.util.List;

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
import component.frame.module.AddModuleFrame;
import component.frame.module.EditModuleFrame;
import component.frame.module.RemoveModuleFrame;
import component.frame.student.GenerateReportFrame;
import component.frame.student.MarkStudentFrame;
import component.frame.teacher.AssignModuleFrame;
import component.frame.teacher.UnassignModuleFrame;
import component.panel.StandardPanel;
import component.scrollpane.StandardScrollPane;
import component.table.StandardTable;
import course.Course;
import course.Module_;
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
    private AssignModuleFrame assignCourseFrame;
    private UnassignModuleFrame unassignCourseFrame;
    
    private AddModuleFrame addModuleFrame;
    private EditModuleFrame editModuleFrame;
    private RemoveModuleFrame removeModuleFrame;
    
    private MarkStudentFrame markStudentFrame;
	
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
	
	static StandardButton addModuleButton = new StandardButton();
	static StandardButton editModuleButton = new StandardButton();
	static StandardButton removeModuleButton = new StandardButton();
	
	static StandardButton generateReportButton = new StandardButton();
	
	static StandardButton assignModuleButton = new StandardButton();
	static StandardButton unassignModuleButton = new StandardButton();
	
	static StandardButton markStudentButton = new StandardButton();
	
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
	                menuItems = new String[] {"Dashboard", "Module", "Teacher"};
	                break;
	            case "teacher":
	                menuItems = new String[] {"Dashboard", "Module", "Student"};
	                break;
	            case "admin":
	                menuItems = new String[] {"Dashboard", "Course", "Module", "Student", "Teacher"};
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
//				try {
//					String[] columns = {"Course Name", "Course Id"};
//					Class.forName(DatabaseConstant.CLASSNAME);
//					Connection conn = DriverManager.getConnection(DatabaseConstant.URL, DatabaseConstant.USERNAME, DatabaseConstant.PASSWORD);
//					
//					String query1 = "SELECT course_id FROM student_course WHERE student_id = ?";
//					
//					PreparedStatement pst1 = conn.prepareStatement(query1);
//					
//					pst1.setString(1, student.getId());
//					
//					ResultSet result1 = pst1.executeQuery();
//					while (result1.next()) {
//					    String courseId = result1.getString("course_id");
//					    String query2 = "SELECT course_name FROM course WHERE course_id = ?";
//					    
//					    PreparedStatement pst = conn.prepareStatement(query2);
//						
//						pst.setString(1, courseId);
//						
//						ResultSet result2 = pst.executeQuery();
//						result2.next();
//						Course course = new Course(result2.getString("course_name"), courseId);
//						student.setCourse(course);
//					}
//					
//					ArrayList<Course> course = student.getCourses();
//					
//					Object[][] data = new Object[course.size()][];
//					for (int i = 0; i < course.size(); i++) {
//					    Course tempCourse = course.get(i);
//					    Object[] courseData = {tempCourse.getCourseName(), tempCourse.getCourseId()};
//					    data[i] = courseData;
//					}
//			        
//					JLabel nameLabel = new JLabel("Name:");
//					nameLabel.setBounds(40, 0, 120, 100);
//					nameLabel.setFont(new BigBold());
//					nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
//
//					JLabel idLabel = new JLabel("Id:");
//					idLabel.setBounds(480, 0, 60, 100);
//					idLabel.setFont(new BigBold());
//					idLabel.setHorizontalAlignment(SwingConstants.LEFT);
//
//					JLabel numberOfCourseLabel = new JLabel("No. of courses currently enrolled:");
//					numberOfCourseLabel.setBounds(40, 150, 530, 100);
//					numberOfCourseLabel.setFont(new BigBold());
//					numberOfCourseLabel.setHorizontalAlignment(SwingConstants.LEFT);
//
//					JLabel name = new JLabel(student.getName());
//					name.setBounds(170, 0, 400, 100);
//					name.setFont(new SmallBold());
//
//					JLabel id = new JLabel(student.getId());
//					id.setBounds(550, 0, 400, 100);
//					id.setFont(new SmallBold());
//
//					JLabel numberOfCourse = new JLabel(student.getNumberOfCourses());
//					numberOfCourse.setBounds(580, 150, 100, 100);
//					numberOfCourse.setFont(new SmallBold());
//
//					
//					System.out.println(student.getNumberOfCourses());
//					
//					StandardTable table = new StandardTable();
//					
//					StandardScrollPane sp = createTable(rightPanel, table, data, columns);
//					
//					TableColumnModel columnModel = table.getColumnModel();
//					int columnIndex = Arrays.asList(columns).indexOf("Course Id"); 
//					columnModel.getColumn(columnIndex).setMaxWidth(150);
//					
//					sp.setBounds(200, 350, 400, 200);
//					
//					table.setUneditable();
//					
//					rightPanel.add(sp);
//					rightPanel.add(nameLabel);
//					rightPanel.add(idLabel);
//					rightPanel.add(numberOfCourseLabel);
//					rightPanel.add(name);
//					rightPanel.add(id);
//					rightPanel.add(numberOfCourse);
//					rightPanel.validate();
//					rightPanel.repaint();
//					
//					course.removeAll(course);
//					
//					break;
//					
//				}catch (ClassNotFoundException cnfe) {
//					String error = cnfe.getMessage();
//		            System.out.println(cnfe);
//		            JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.WARNING_MESSAGE);
//				}catch (SQLException sqle) {
//					String error = sqle.getMessage();
//		            System.out.println(sqle);
//		            JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.WARNING_MESSAGE);
//				}
				
				
			case "Module":
				JLabel text2 = new JLabel(student.getId());
				text2.setBounds(0, 0, 100, 100);
				rightPanel.add(text2);
				break;
			case "Teacher":
//				JLabel text3 = new JLabel(student.getFaculty());
//				text3.setBounds(0, 0, 100, 100);
//				rightPanel.add(text3);
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
		this.teacher = teacher;
		rightPanel.removeAll();
		switch(menu) {
			case "Dashboard":
				try {
					JLabel nameLabel = new JLabel("Name:");
					nameLabel.setBounds(40, 0, 120, 100);
					nameLabel.setFont(new BigBold());
					nameLabel.setHorizontalAlignment(SwingConstants.LEFT);

					JLabel idLabel = new JLabel("Id:");
					idLabel.setBounds(480, 0, 60, 100);
					idLabel.setFont(new BigBold());
					idLabel.setHorizontalAlignment(SwingConstants.LEFT);
					
					JLabel studentLabel = new JLabel("No. of students:");
					JLabel moduleLabel = new JLabel("No. of modules:");
					
					ArrayList<JLabel> labels = new ArrayList<>(Arrays.asList(
							studentLabel, moduleLabel
					));	
					
					axisX = 75;
					axisY = 250;
					
					for(JLabel label : labels) {
						label.setBounds(axisX, axisY, 400, 100);
						label.setFont(new BigBold());
						label.setHorizontalAlignment(SwingConstants.RIGHT);
						axisY += 100;
						rightPanel.add(label);
					}

					JLabel name = new JLabel(teacher.getName());
					name.setBounds(170, 0, 400, 100);
					name.setFont(new SmallBold());

					JLabel id = new JLabel(teacher.getId());
					id.setBounds(550, 0, 400, 100);
					id.setFont(new SmallBold());
					
					Connection conn = DriverManager.getConnection(DatabaseConstant.URL, DatabaseConstant.USERNAME, DatabaseConstant.PASSWORD);

					// Query to count the number of modules that the teacher teaches
					String countModulesQuery = "SELECT COUNT(DISTINCT tm.module_id) AS module_count " +
					                           "FROM teacher_module tm " +
					                           "INNER JOIN module m ON tm.module_id = m.module_id " +
					                           "WHERE tm.teacher_id = ?";
					PreparedStatement countModulesPst = conn.prepareStatement(countModulesQuery);
					countModulesPst.setString(1, teacher.getId());
					ResultSet moduleCountResult = countModulesPst.executeQuery();
					int numberOfModules = 0;
					if (moduleCountResult.next()) {
					    numberOfModules = moduleCountResult.getInt("module_count");
					}

					// Query to count the number of students enrolled in modules that the teacher teaches
					String countStudentsQuery = "SELECT COUNT(DISTINCT se.student_id) AS student_count " +
					                            "FROM student_enrollment se " +
					                            "INNER JOIN teacher_module tm ON se.module_id = tm.module_id " +
					                            "WHERE tm.teacher_id = ?";
					PreparedStatement countStudentsPst = conn.prepareStatement(countStudentsQuery);
					countStudentsPst.setString(1, teacher.getId());
					ResultSet studentCountResult = countStudentsPst.executeQuery();
					int numberOfStudents = 0;
					if (studentCountResult.next()) {
					    numberOfStudents = studentCountResult.getInt("student_count");
					}
					
					JLabel numberStudents = new JLabel(Integer.toString(numberOfStudents));
					JLabel numberModules = new JLabel(Integer.toString(numberOfModules));
					
					ArrayList<JLabel> numbers = new ArrayList<>(Arrays.asList(
							numberStudents, numberModules
					));	
					
					axisX = 505;
					axisY = 250;
					
					for(JLabel label : numbers) {
						label.setBounds(axisX, axisY, 400, 100);
						label.setFont(new SmallBold());
						label.setHorizontalAlignment(SwingConstants.LEFT);
						axisY += 100;
						rightPanel.add(label);
					}
					
					conn.close();
					rightPanel.add(nameLabel);
					rightPanel.add(idLabel);
					rightPanel.add(name);
					rightPanel.add(id);
					rightPanel.validate();
					rightPanel.repaint();
					
				}catch(Exception exp) {
					
				}
				break;
			case "Module":
				try {
					
					JLabel allCourseLabel = new JLabel();
					allCourseLabel.setText("Modules:");
					allCourseLabel.setFont(new BigBold());
					allCourseLabel.setBounds(40, 0, 400, 100);
					
					ArrayList<Module_> modules = new ArrayList<Module_>();
	
					String[] columns = {"Module Name", "Module Id", "Course", "Level", "Semester"};
					Class.forName(DatabaseConstant.CLASSNAME);
					Connection conn = DriverManager.getConnection(DatabaseConstant.URL, DatabaseConstant.USERNAME, DatabaseConstant.PASSWORD);
	
					String query = "SELECT m.module_id, m.module_name, m.semester, m.level, m.course_id, c.course_name " +
				               "FROM module m " +
				               "INNER JOIN teacher_module tm ON m.module_id = tm.module_id " +
				               "INNER JOIN course c ON m.course_id = c.course_id " +
				               "WHERE tm.teacher_id = ?";

					PreparedStatement pst = conn.prepareStatement(query);
					pst.setString(1, teacher.getId());
					ResultSet result = pst.executeQuery();
	
					while (result.next()) {
					    Module_ module = new Module_();
	
					    int moduleId = result.getInt("module_id");
					    String moduleName = result.getString("module_name");
					    int semester = result.getInt("semester");
					    int level = result.getInt("level");
					    int courseId = result.getInt("course_id");
					    String courseName = result.getString("course_name");
	
					    module.setModuleId(moduleId);
					    module.setModuleName(moduleName);
					    module.setSemester(semester);
					    module.setLevel(level);
	
					    Course course = new Course();
					    course.setCourseId(Integer.toString(courseId));
					    course.setCourseName(courseName);
	
					    module.setCourse(course);
	
					    modules.add(module);
					}

					
					conn.close();
	
					Object[][] cData = new Object[modules.size()][];
					for (int i = 0; i < modules.size(); i++) {
					    Module_ tempModule = modules.get(i);
					    Object[] moduleData = {tempModule.getModuleName(), tempModule.getModuleId(), tempModule.getCourse().getCourseName(), tempModule.getLevel(), tempModule.getSemester()};
					    cData[i] = moduleData;
					}
	
					StandardTable moduleTable = new StandardTable();
					StandardScrollPane sp = createTable(rightPanel, moduleTable, cData, columns);
	
					sp.setBounds(10, 125, 770, 550);
					moduleTable.setUneditable();
					
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
					allStudentLabel.setText("Students:");
					allStudentLabel.setFont(new BigBold());
					allStudentLabel.setBounds(40, 0, 400, 100);

					ArrayList<Student> students = new ArrayList<Student>();
					String[] columns = {"Name", "Id", "Module", "Course", "Marks"}; // Added "Marks" column
					Class.forName(DatabaseConstant.CLASSNAME);
					Connection conn = DriverManager.getConnection(DatabaseConstant.URL, DatabaseConstant.USERNAME, DatabaseConstant.PASSWORD);

					String query = "SELECT s.*, m.module_name, c.course_name, se.marks " +
					               "FROM student_enrollment se " +
					               "JOIN student s ON se.student_id = s.student_id " +
					               "JOIN module m ON se.module_id = m.module_id " +
					               "JOIN course c ON se.course_id = c.course_id " +
					               "JOIN teacher_module tm ON m.module_id = tm.module_id " +
					               "WHERE tm.teacher_id = ?";

					PreparedStatement pst = conn.prepareStatement(query);
					pst.setString(1, teacher.getId());
					ResultSet result = pst.executeQuery();

					while (result.next()) {
					    String studentId = result.getString("student_id");
					    String studentName = result.getString("name");
					    String moduleName = result.getString("module_name");
					    String courseName = result.getString("course_name");
					    int marks = result.getInt("marks"); // Added "marks" column

					    Student student = new Student();
					    student.setName(studentName);
					    student.setId(studentId);

					    Module_ module = new Module_();
					    module.setModuleName(moduleName);
					    student.addModule(module);

					    Course course = new Course();
					    course.setCourseName(courseName);
					    student.setCourse(course);
					    
					    // Set marks for each module
					    module.setMarks(marks);

					    students.add(student);
					}

					Object[][] sData = new Object[students.size()][];
					int rowIndex = 0; // Variable to keep track of the current row index

					for (int i = 0; i < students.size(); i++) {
					    Student tempStudent = students.get(i);

					    // Get modules for the current student
					    ArrayList<Module_> studentModules = tempStudent.getModules();

					    // Create separate rows for each module
					    for (int j = 0; j < studentModules.size(); j++) {
					        Object[] studentData = {
					            tempStudent.getName(),
					            tempStudent.getId(),
					            studentModules.get(j).getModuleName(),
					            tempStudent.getCourse().getCourseName(),
					            studentModules.get(j).getMarks() // Added marks for each module
					        };
					        sData[rowIndex++] = studentData;
					    }
					}

					StandardTable studentTable = new StandardTable();
					StandardScrollPane sp = createTable(rightPanel, studentTable, sData, columns);

					sp.setBounds(10, 125, 770, 500);
					studentTable.setUneditable();

					markStudentButton.setText("Mark Student");
					markStudentButton.addActionListener(this);
					markStudentButton.setBounds(300, 645, 200, 35);

					rightPanel.add(markStudentButton);
					rightPanel.add(allStudentLabel);
					rightPanel.add(sp);


					
				}catch (SQLException sqle) {
					String error = sqle.getMessage();
					System.out.println(sqle);
					JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.WARNING_MESSAGE);
				}
				break;
				
		}
		rightPanel.repaint();
		rightPanel.validate();
		add(rightPanel);	
	}
	
	public void changeRightPanel(Admin admin, String menu) throws ClassNotFoundException {
		this.admin = admin;
		rightPanel.removeAll();
		switch (menu) {
			case "Dashboard":				
				try {
					
				JLabel nameLabel = new JLabel("Name:");
				nameLabel.setBounds(40, 0, 120, 100);
				nameLabel.setFont(new BigBold());
				nameLabel.setHorizontalAlignment(SwingConstants.LEFT);

				JLabel idLabel = new JLabel("Id:");
				idLabel.setBounds(480, 0, 60, 100);
				idLabel.setFont(new BigBold());
				idLabel.setHorizontalAlignment(SwingConstants.LEFT);
				
				JLabel studentLabel = new JLabel("No. of students:");
				JLabel teacherLabel = new JLabel("No. of teachers:");
				JLabel courseLabel = new JLabel("No. of courses:");
				JLabel moduleLabel = new JLabel("No. of modules:");
				
				ArrayList<JLabel> labels = new ArrayList<>(Arrays.asList(
						studentLabel, teacherLabel,
						courseLabel, moduleLabel
				));	
				
				axisX = 75;
				axisY = 180;
				
				for(JLabel label : labels) {
					label.setBounds(axisX, axisY, 400, 100);
					label.setFont(new BigBold());
					label.setHorizontalAlignment(SwingConstants.RIGHT);
					axisY += 100;
					rightPanel.add(label);
				}

				JLabel name = new JLabel(admin.getName());
				name.setBounds(170, 0, 400, 100);
				name.setFont(new SmallBold());

				JLabel id = new JLabel(admin.getId());
				id.setBounds(550, 0, 400, 100);
				id.setFont(new SmallBold());
				
				Class.forName(DatabaseConstant.CLASSNAME);
				Connection conn = DriverManager.getConnection(DatabaseConstant.URL, DatabaseConstant.USERNAME, DatabaseConstant.PASSWORD);
				
				int numberOfStudents = 0, numberOfTeachers = 0, numberOfCourses = 0, numberOfModules = 0;
				
				String query1 = "SELECT * FROM student";
				
				PreparedStatement pst1 = conn.prepareStatement(query1);
				
				ResultSet result = pst1.executeQuery();
				
				while (result.next()) {
					numberOfStudents++;
				}
				
				query1 = "SELECT * FROM teacher";
				
				pst1 = conn.prepareStatement(query1);
				
				result = pst1.executeQuery();
				
				while (result.next()) {
					numberOfTeachers++;
				}
				
				query1 = "SELECT * FROM course";
				
				pst1 = conn.prepareStatement(query1);
				
				result = pst1.executeQuery();
				
				while (result.next()) {
					numberOfCourses++;
				}
				
				query1 = "SELECT * FROM module";
				
				pst1 = conn.prepareStatement(query1);
				
				result = pst1.executeQuery();
				
				while (result.next()) {
					numberOfModules++;
				}
				
				JLabel numberStudents = new JLabel(Integer.toString(numberOfStudents));
				JLabel numberTeachers = new JLabel(Integer.toString(numberOfTeachers));
				JLabel numberCourses = new JLabel(Integer.toString(numberOfCourses));
				JLabel numberModules = new JLabel(Integer.toString(numberOfModules));
				
				ArrayList<JLabel> numbers = new ArrayList<>(Arrays.asList(
						numberStudents, numberTeachers,
						numberCourses, numberModules
				));	
				
				axisX = 505;
				axisY = 180;
				
				for(JLabel label : numbers) {
					label.setBounds(axisX, axisY, 400, 100);
					label.setFont(new SmallBold());
					label.setHorizontalAlignment(SwingConstants.LEFT);
					axisY += 100;
					rightPanel.add(label);
				}
				
				conn.close();
				rightPanel.add(nameLabel);
				rightPanel.add(idLabel);
				rightPanel.add(name);
				rightPanel.add(id);
				rightPanel.validate();
				rightPanel.repaint();
				
			}catch (Exception exp){
				JOptionPane.showMessageDialog(null, exp, "Error", JOptionPane.WARNING_MESSAGE);
			
			}
				
				
				
				break;
			case "Course":
				try {
					
					JLabel allCourseLabel = new JLabel();
					allCourseLabel.setText("All Courses:");
					allCourseLabel.setFont(new BigBold());
					allCourseLabel.setBounds(40, 0, 400, 100);
					
					ArrayList<Course> courses = new ArrayList<Course>();
					String[] columns = {"Course Name", "Course Id", "Enabled"};
					Class.forName(DatabaseConstant.CLASSNAME);
					Connection conn = DriverManager.getConnection(DatabaseConstant.URL, DatabaseConstant.USERNAME, DatabaseConstant.PASSWORD);

					String query = "SELECT * FROM course";

					PreparedStatement pst = conn.prepareStatement(query);
					ResultSet result = pst.executeQuery();

					while (result.next()) {
					    String courseId = result.getString("course_id");
					    String courseName = result.getString("course_name");
					    Boolean isEnabled = result.getBoolean("is_active");

					    Course course = new Course();
					    course.setCourseName(courseName);
					    course.setCourseId(courseId);
					    course.setActive(isEnabled);
					    courses.add(course);
					}

					Object[][] cData = new Object[courses.size()][];
					for (int i = 0; i < courses.size(); i++) {
					    Course tempCourse = courses.get(i);
					    Object[] courseData = {tempCourse.getCourseName(), tempCourse.getCourseId(), tempCourse.isActive()};
					    cData[i] = courseData;
					}

					StandardTable courseTable = new StandardTable();
					StandardScrollPane sp = createTable(rightPanel, courseTable, cData, columns);

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
			case "Module":
				
				try {
					
					JLabel allCourseLabel = new JLabel();
					allCourseLabel.setText("All Modules:");
					allCourseLabel.setFont(new BigBold());
					allCourseLabel.setBounds(40, 0, 400, 100);
					
					ArrayList<Module_> modules = new ArrayList<Module_>();

					String[] columns = {"Module Name", "Module Id", "Course", "Level", "Semester"};
					Class.forName(DatabaseConstant.CLASSNAME);
					Connection conn = DriverManager.getConnection(DatabaseConstant.URL, DatabaseConstant.USERNAME, DatabaseConstant.PASSWORD);

					String query = "SELECT * FROM module";

					PreparedStatement pst = conn.prepareStatement(query);
					ResultSet result = pst.executeQuery();

					while (result.next()) {
					    Module_ module = new Module_();

					    int moduleId = result.getInt("module_id");
					    String moduleName = result.getString("module_name");
					    int semester = result.getInt("semester");
					    int level = result.getInt("level");
					    int courseId = result.getInt("course_id");

					    module.setModuleId(moduleId);
					    module.setModuleName(moduleName);
					    module.setSemester(semester);
					    module.setLevel(level);

					    // Create a new PreparedStatement for the nested query
					    String courseQuery = "SELECT course_name FROM course WHERE course_id = ?";
					    PreparedStatement coursePst = conn.prepareStatement(courseQuery);
					    coursePst.setInt(1, courseId);

					    // Execute the nested query
					    ResultSet courseResult = coursePst.executeQuery();

					    Course course = new Course();

					    while (courseResult.next()) {
					        String courseName = courseResult.getString("course_name");
					        course.setCourseName(courseName);
					    }

					    module.setCourse(course);

					    modules.add(module);
					}
					
					conn.close();

					Object[][] cData = new Object[modules.size()][];
					for (int i = 0; i < modules.size(); i++) {
					    Module_ tempModule = modules.get(i);
					    Object[] moduleData = {tempModule.getModuleName(), tempModule.getModuleId(), tempModule.getCourse().getCourseName(), tempModule.getLevel(), tempModule.getSemester()};
					    cData[i] = moduleData;
					}

					StandardTable moduleTable = new StandardTable();
					StandardScrollPane sp = createTable(rightPanel, moduleTable, cData, columns);

					sp.setBounds(10, 125, 655, 500);
					moduleTable.setUneditable();
					
					addModuleButton.setText("Add");
					editModuleButton.setText("Edit");
					removeModuleButton.setText("Remove");	
					
					ArrayList<StandardButton> buttons = new ArrayList<>(Arrays.asList(
							addModuleButton, editModuleButton, removeModuleButton
					));
					
					axisX = 675;
					axisY = 200;
					
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
				    String[] columns = {"Name", "Id", "Course"};
				    Class.forName(DatabaseConstant.CLASSNAME);
				    Connection conn = DriverManager.getConnection(DatabaseConstant.URL, DatabaseConstant.USERNAME, DatabaseConstant.PASSWORD);

				    String query = "SELECT * FROM student";

				    PreparedStatement pst = conn.prepareStatement(query);
				    ResultSet result = pst.executeQuery();

				    while (result.next()) {
				        String studentId = result.getString("student_id");
				        String studentName = result.getString("name");

				        Student student = new Student();
				        student.setName(studentName);
				        student.setId(studentId);

				        String courseQuery = "SELECT DISTINCT course_id FROM student_enrollment WHERE student_id = ?";
				        PreparedStatement coursePst = conn.prepareStatement(courseQuery);
				        coursePst.setString(1, studentId);
				        ResultSet courseResult = coursePst.executeQuery();

				        boolean studentHasCourses = false; 
				        
				        while (courseResult.next()) {
				            studentHasCourses = true;

				            String courseId = courseResult.getString("course_id");
				            String courseNameQuery = "SELECT course_name FROM course WHERE course_id = ?";
				            PreparedStatement courseNamePst = conn.prepareStatement(courseNameQuery);
				            courseNamePst.setString(1, courseId);
				            ResultSet courseNameResult = courseNamePst.executeQuery();

				            while (courseNameResult.next()) {
				                String courseName = courseNameResult.getString("course_name");

				                Course course = new Course();
				                course.setCourseName(courseName);
				                student.setCourse(course);

				                students.add(student);
				            }
				        }

				        
				        if (!studentHasCourses) {
				            students.add(student);
				        }
				    }

				    Object[][] sData = new Object[students.size()][];
				    for (int i = 0; i < students.size(); i++) {
				        Student tempStudent = students.get(i);
				        Object[] studentData = {tempStudent.getName(), tempStudent.getId(), tempStudent.getCourse().getCourseName()};
				        sData[i] = studentData;
				    }

					StandardTable courseTable = new StandardTable();
					StandardScrollPane sp = createTable(rightPanel, courseTable, sData, columns);

					sp.setBounds(10, 125, 770, 500);
					courseTable.setUneditable();
					
					generateReportButton.setText("Generate Report");
					generateReportButton.addActionListener(this);
					generateReportButton.setBounds(300, 645, 200, 35);
					
					rightPanel.add(generateReportButton);
					rightPanel.add(allStudentLabel);
					rightPanel.add(sp);
					
				}catch (SQLException sqle) {
				}

				break;
			case "Teacher":
				
				try {
					JLabel allTeacherLabel = new JLabel();
					allTeacherLabel.setText("All Teachers:");
					allTeacherLabel.setFont(new BigBold());
					allTeacherLabel.setBounds(40, 0, 400, 100);

					ArrayList<Teacher> teachers = new ArrayList<Teacher>();
					String[] columns = {"Name", "Id", "Modules"};
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

				        String moduleQuery = "SELECT module_id FROM teacher_module WHERE teacher_id = ?";
				        PreparedStatement modulePst = conn.prepareStatement(moduleQuery);
				        modulePst.setString(1, teacher.getId());
				        ResultSet moduleResult = modulePst.executeQuery();

				        boolean teacherHasModules = false; 
				        
				        while (moduleResult.next()) {
				        	teacherHasModules = true;

				            String moduleId = moduleResult.getString("module_id");
				            String moduleNameQuery = "SELECT module_name FROM module WHERE module_id = ?";
				            PreparedStatement moduleNamePst = conn.prepareStatement(moduleNameQuery);
				            moduleNamePst.setString(1, moduleId);
				            ResultSet moduleNameResult = moduleNamePst.executeQuery();

				            while (moduleNameResult.next()) {
				                String moduleName = moduleNameResult.getString("module_name");
				                
				                Teacher moduleTeacher = new Teacher();
				                moduleTeacher.setName(teacherName);
				                moduleTeacher.setId(teacherId);
				                Module_ module = new Module_();
				                module.setModuleName(moduleName);
				                moduleTeacher.addModule(module);

				                teachers.add(moduleTeacher);
				            }
				        }

				        
				        if (!teacherHasModules) {
				            teachers.add(teacher);
				        }
				    }

					Object[][] sData = new Object[teachers.size()][];
					for (int i = 0; i < teachers.size(); i++) {
					    Teacher tempTeacher = teachers.get(i);
					    
					    List<String> moduleNames = tempTeacher.getModuleNames();
					    

					    sData[i] = new Object[3 + moduleNames.size()]; 
					    
					    sData[i][0] = tempTeacher.getName();
					    sData[i][1] = tempTeacher.getId();
					    
					    for (int j = 0; j < moduleNames.size(); j++) {
					        sData[i][2 + j] = moduleNames.get(j);
					    }
					}

					StandardTable courseTable = new StandardTable();
					StandardScrollPane sp = createTable(rightPanel, courseTable, sData, columns);

					sp.setBounds(10, 125, 770, 500);
					courseTable.setUneditable();

					assignModuleButton.setText("Assign Module");
					assignModuleButton.addActionListener(this);
					assignModuleButton.setBounds(150, 645, 200, 35);
					
					unassignModuleButton.setText("Unassign Module");
					unassignModuleButton.addActionListener(this);
					unassignModuleButton.setBounds(400, 645, 200, 35);

					rightPanel.add(assignModuleButton);
					rightPanel.add(unassignModuleButton);
					rightPanel.add(allTeacherLabel);
					rightPanel.add(sp);
					
				}catch (SQLException sqle) {
					String error = sqle.getMessage();
		            System.out.println(sqle);
		            JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.WARNING_MESSAGE);
				}
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
			} else if (e.getSource() == assignModuleButton) {
			    if (assignCourseFrame != null) {
			    	assignCourseFrame.removeAll();
			        assignCourseFrame.dispose();
			    }
			    assignCourseFrame = new AssignModuleFrame(0, 0, 500, 400);
			    assignCourseFrame.setVisible(true);
			    assignCourseFrame.toFront();
			    repaint();
			    revalidate();
			}else if (e.getSource() == unassignModuleButton) {
			    if (unassignCourseFrame != null) {
			    	unassignCourseFrame.removeAll();
			        unassignCourseFrame.dispose();
			    }
			    unassignCourseFrame = new UnassignModuleFrame(0, 0, 500, 400);
			    unassignCourseFrame.setVisible(true);
			    unassignCourseFrame.toFront();
			    repaint();
			    revalidate();
			}else if (e.getSource() == addModuleButton) {
			    if (addModuleFrame != null) {
			    	addModuleFrame.removeAll();
			    	addModuleFrame.dispose();
			    }
			    addModuleFrame = new AddModuleFrame(0, 0, 500, 400);
			    addModuleFrame.setVisible(true);
			    addModuleFrame.toFront();
			    repaint();
			    revalidate();
			}else if (e.getSource() == editModuleButton) {
			    if (editModuleFrame != null) {
			    	editModuleFrame.removeAll();
			    	editModuleFrame.dispose();
			    }
			    editModuleFrame = new EditModuleFrame(0, 0, 500, 400);
			    editModuleFrame.setVisible(true);
			    editModuleFrame.toFront();
			    repaint();
			    revalidate();
			}else if (e.getSource() == removeModuleButton) {
			    if (removeModuleFrame != null) {
			    	removeModuleFrame.removeAll();
			    	removeModuleFrame.dispose();
			    }
			    removeModuleFrame = new RemoveModuleFrame(0, 0, 500, 400);
			    removeModuleFrame.setVisible(true);
			    removeModuleFrame.toFront();
			    repaint();
			    revalidate();
			}else if (e.getSource() == markStudentButton) {
			    if (markStudentFrame != null) {
			    	markStudentFrame.removeAll();
			    	markStudentFrame.dispose();
			    }
			    markStudentFrame = new MarkStudentFrame(0, 0, 500, 400, teacher);
			    markStudentFrame.setVisible(true);
			    markStudentFrame.toFront();
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
		rightPanel.removeAll();
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
