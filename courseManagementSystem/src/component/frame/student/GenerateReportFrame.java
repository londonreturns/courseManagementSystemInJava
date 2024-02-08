package component.frame.student;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import component.button.StandardButton;
import component.frame.StandardFrame;
import course.Course;
import exception.FormException;
import font.BigBold;
import font.PlaceHolderFont;
import font.RegularFont;
import font.SubHeadingFont;
import utility.DatabaseConstant;
import utility.TextPrompt;

public class GenerateReportFrame extends StandardFrame implements ActionListener{
	
	public GenerateReportFrame(int x_coord, int y_coord, int width, int height) {
		super(x_coord, y_coord, width, height);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setElements();
	}
	
	JTextField studentNameTextField = new JTextField();
	JTextField studentIdTextField = new JTextField();
	
	static StandardButton okBtn = new StandardButton();
	static StandardButton closeBtn = new StandardButton();
	static StandardButton getDetailsBtn = new StandardButton();
	
	private int axisX = 8;
	private int axisY = 110;
	private final int width = 185;
	private final int height = 32;
	
	public void setElements() {
		JLabel titleLabel = new JLabel();
		titleLabel.setText("Generate Report");
		titleLabel.setBounds(0, 0, 500, 100);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new BigBold());
		
		studentNameTextField.setEditable(false);
		
		String[] labelItems = {
				"Student Id: ", "Student Name: "
		};
		
		for (String menu : labelItems) {
			JLabel menuLabels = new JLabel();
			menuLabels.setText(menu);
			menuLabels.setBounds(axisX, axisY, width, height);
			menuLabels.setFont(new SubHeadingFont());
			axisY = incrementPosition(axisY);
			add(menuLabels);
			((JLabel) menuLabels).setHorizontalAlignment(SwingConstants.RIGHT);
		}
		
		axisX = 250;
		axisY = 110;
		
		Component[] fieldItems = {
				studentIdTextField, studentNameTextField
		};
		
		for (Component field : fieldItems) {
			field.setBounds(axisX, axisY, width, height);
			field.setFont(new RegularFont());
			axisY = incrementPosition(axisY);
		}
		
		okBtn.setText("Generate");
		okBtn.setBounds(50, 275, 100, 35);
		okBtn.addActionListener(this);
		
		getDetailsBtn.setText("Get Details");
		getDetailsBtn.setBounds(200, 275, 100, 35);
		getDetailsBtn.addActionListener(this);
		
		closeBtn.setText("Cancel");
		closeBtn.setBounds(350, 275, 100, 35);
		closeBtn.addActionListener(this);
		
		ArrayList<Component> allComponents = new ArrayList<>(Arrays.asList(
				studentNameTextField, studentIdTextField,
			okBtn, getDetailsBtn, closeBtn
		));	
		for(Component comp : allComponents) {
			this.add(comp);
		}
		
		add(titleLabel);
		setPlaceHolders();
	}
	
	public void setDefaultCloseOperation() {
		removeAll();
		dispose();
	}
	
	private static int incrementPosition(int x) {
		return x += 35;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == okBtn) {
			String id = studentIdTextField.getText().trim();
			try {
	            Class.forName(DatabaseConstant.CLASSNAME);
	            Connection conn = DriverManager.getConnection(DatabaseConstant.URL, DatabaseConstant.USERNAME, DatabaseConstant.PASSWORD);

	            // Check if the student exists
	            String checkStudentQuery = "SELECT student_id FROM student WHERE student_id = ?";
	            PreparedStatement checkStudentPst = conn.prepareStatement(checkStudentQuery);
	            checkStudentPst.setString(1, id);

	            ResultSet studentResult = checkStudentPst.executeQuery();
	            int studentRows = 0;

	            while (studentResult.next()) {
	                studentRows++;
	            }

	            if (studentRows == 0) {
	                throw new FormException("Id not found");
	            }

	            // If the student exists, retrieve marks and course name from student_course and course tables
	            String getMarksAndCourseQuery = "SELECT sc.marks, c.course_name " +
	                                            "FROM student_course sc " +
	                                            "INNER JOIN course c ON sc.course_id = c.course_id " +
	                                            "WHERE sc.student_id = ?";
	            PreparedStatement getMarksAndCoursePst = conn.prepareStatement(getMarksAndCourseQuery);
	            getMarksAndCoursePst.setString(1, id);

	            ResultSet marksAndCourseResult = getMarksAndCoursePst.executeQuery();

	            // Write to CSV file
	            File csvFile = new File("studentMarks/" + id + ".csv");
	            try (PrintWriter writer = new PrintWriter(csvFile)) {
	                // Write header
	                writer.println("Course Name, Marks");

	                // Write data
	                while (marksAndCourseResult.next()) {
	                    String courseName = marksAndCourseResult.getString("course_name");
	                    int marks = marksAndCourseResult.getInt("marks");

	                    writer.println(courseName + "," + marks);
	                }
	            }

	            System.out.println("Data has been written to studentMarks/" + id + ".csv");

	            conn.close();

	            JOptionPane.showMessageDialog(null, "Report Successfully Generated", "Success", JOptionPane.INFORMATION_MESSAGE);

	            resetFields();
	            
	        } catch (ClassNotFoundException | SQLException | FormException | java.io.FileNotFoundException e1) {
	            e1.printStackTrace();
	            JOptionPane.showMessageDialog(null, "Error Generating Report", "Error", JOptionPane.ERROR_MESSAGE);
	            // Handle exceptions appropriately
	        }
			
		}else if (e.getSource() == getDetailsBtn){
			String id = studentIdTextField.getText().trim();
			
			try {
				Class.forName(DatabaseConstant.CLASSNAME);
				Connection conn = DriverManager.getConnection(DatabaseConstant.URL, DatabaseConstant.USERNAME, DatabaseConstant.PASSWORD);
				
				String query1 = "SELECT * FROM student WHERE student_id = ?";
				
				PreparedStatement pst1 = conn.prepareStatement(query1);
				
				int index = 1;
				
				pst1.setString(index, id);	
				
				ResultSet result = pst1.executeQuery();

				int rows = 0;
				
				while (result.next()) {
					rows++;
					String nameDb = result.getString("name");

				    studentNameTextField.setText(nameDb);
				}
				
				if (rows == 0) {
					throw new FormException();
				}
				
			    conn.close();
			}catch (Exception e1) {
				String error = "Course id not found";
				System.out.println(e1);
	            JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.WARNING_MESSAGE);	            
			}
			
		}
		removeAll();
		dispose();
		
	}
	
	private void setPlaceHolders() {
		TextPrompt idPlaceHolder = new TextPrompt("Id", studentIdTextField);
		
		ArrayList<Component> allComponents = new ArrayList<>(Arrays.asList(
				idPlaceHolder));	
		
		for(Component placeHolderComponent : allComponents) {
			placeHolderComponent.setFont(new PlaceHolderFont());
		}
	}
	
	private void resetFields() {
		studentNameTextField.setText("");
		studentIdTextField.setText("");
	}
	
}
