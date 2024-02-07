package component.frame;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import course.Course;
import exception.FormException;
import font.BigBold;
import font.PlaceHolderFont;
import font.RegularFont;
import font.SubHeadingFont;
import utility.DatabaseConstant;
import utility.TextPrompt;

public class AddCourseFrame extends StandardFrame implements ActionListener{
	
	public AddCourseFrame(int x_coord, int y_coord, int width, int height) {
		super(x_coord, y_coord, width, height);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setElements();
	}
	
	JTextField courseNameTextField = new JTextField();
	JTextField courseIdTextField = new JTextField();
	JTextField facultyTextField = new JTextField();
	JTextField levelTextField = new JTextField();
	
	static StandardButton okBtn = new StandardButton();
	static StandardButton closeBtn = new StandardButton();
	
	private int axisX = 8;
	private int axisY = 110;
	private final int width = 185;
	private final int height = 32;
	
	public void setElements() {
		JLabel titleLabel = new JLabel();
		titleLabel.setText("Add a course");
		titleLabel.setBounds(0, 0, 500, 100);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new BigBold());
		
		String[] labelItems = {
				"Course Id: ", "Course Name: ", "Faculty: ", "Level: "
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
				courseIdTextField, courseNameTextField, facultyTextField, levelTextField
		};
		
		for (Component field : fieldItems) {
			field.setBounds(axisX, axisY, width, height);
			field.setFont(new RegularFont());
			axisY = incrementPosition(axisY);
		}
		
		okBtn.setText("Confirm");
		okBtn.setBounds(100, 275, 100, 35);
		okBtn.addActionListener(this);
		
		closeBtn.setText("Cancel");
		closeBtn.setBounds(300, 275, 100, 35);
		closeBtn.addActionListener(this);
		
		ArrayList<Component> allComponents = new ArrayList<>(Arrays.asList(
			courseNameTextField, courseIdTextField, facultyTextField, levelTextField,
			okBtn, closeBtn
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
	public void actionPerformed(ActionEvent e){
		if (e.getSource() == okBtn) {
			String name = courseNameTextField.getText().trim();
			String id = courseIdTextField.getText().trim();
			String faculty = facultyTextField.getText().trim().toUpperCase();
			String level = levelTextField.getText().trim();
			
			try {
				
				
				if (!Pattern.matches("^[a-zA-Z0-9].{4,10}$", id)) {
				    throw new FormException("Invalid id");
				}else if (!(faculty.equals("BCS") || faculty.equals("BIBM"))) {
				    throw new FormException("Invalid Faculty");
				}else if(!Pattern.matches("^[3-6]{1}$", level)) {
					throw new FormException("Invalid level");
				}
				
				Course course = new Course(name, id, faculty, level);
				
				Class.forName(DatabaseConstant.CLASSNAME);
				Connection conn = DriverManager.getConnection(DatabaseConstant.URL, DatabaseConstant.USERNAME, DatabaseConstant.PASSWORD);
				
				String query1 = "SELECT course_id FROM course WHERE course_id = ?";
				
				PreparedStatement pst1 = conn.prepareStatement(query1);
				
				int index = 1;
				
				pst1.setString(index, course.getCourseId());	
				
				ResultSet result = pst1.executeQuery();

				int rows = 0;
				
				while (result.next()) {
					rows++;
				}
				
				if (rows > 0) {
					throw new FormException("Id already exists");
				}
				
				String query2 = "INSERT INTO course (course_id, course_name, faculty, level, is_active)"
						+ " VALUES"
						+ " (?, ?, ?, ?, ?)";
				
				PreparedStatement pst2 = conn.prepareStatement(query2);
				
				index = 1;
				
				pst2.setString(index, course.getCourseId());
				
				index++;
				
				pst2.setString(index, course.getCourseName());
				
				index++;
				
				pst2.setString(index, course.getFaculty());
				
				index++;
				
				pst2.setString(index, course.getLevel());
				
				index++;
				
				pst2.setString(index, "0");
				
				int rowsAffected = pst2.executeUpdate();
				
				if (rowsAffected < 1) {
					throw new FormException("Please try again");
				}
				
				JOptionPane.showMessageDialog(null, "Course Successfully Added", "Success", JOptionPane.INFORMATION_MESSAGE);
		        
				resetFields();
				
				
			} catch (ClassNotFoundException | SQLException e1) {
				String error = e1.getMessage();
	            System.out.println(e1);
	            JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.WARNING_MESSAGE);
	        
	            
			}catch (FormException e1) {
				String error = e1.getMessage();
	            System.out.println(e1);
	            JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.WARNING_MESSAGE);
	        
			}catch (Exception e1) {
				String error = "Id error";
	            System.out.println(e1);
	            JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.WARNING_MESSAGE);
			}
			
			
		}else {
			removeAll();
			dispose();
		}
		
	}
	
	private void setPlaceHolders() {
		TextPrompt namePlaceHolder = new TextPrompt("Name", courseNameTextField);
		TextPrompt idPlaceHolder = new TextPrompt("Id", courseIdTextField);
		TextPrompt facultyPlaceHolder = new TextPrompt("Faculty", facultyTextField);
		TextPrompt levelPlaceHolder = new TextPrompt("Level", levelTextField);
		
		ArrayList<Component> allComponents = new ArrayList<>(Arrays.asList(
				namePlaceHolder, idPlaceHolder, facultyPlaceHolder,
				levelPlaceHolder));	
		
		for(Component placeHolderComponent : allComponents) {
			placeHolderComponent.setFont(new PlaceHolderFont());
		}
	}
	
	private void resetFields() {
		courseNameTextField.setText("");
		courseIdTextField.setText("");
		facultyTextField.setText("");
		levelTextField.setText("");		
	}
}
