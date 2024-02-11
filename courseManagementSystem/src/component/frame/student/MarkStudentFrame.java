package component.frame.student;

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
import component.frame.StandardFrame;
import exception.DatabaseException;
import exception.FormException;
import font.BigBold;
import font.PlaceHolderFont;
import font.RegularFont;
import font.SubHeadingFont;
import user.Teacher;
import utility.DatabaseConstant;
import utility.TextPrompt;

public class MarkStudentFrame extends StandardFrame implements ActionListener{
	
	public MarkStudentFrame(int x_coord, int y_coord, int width, int height, Teacher teacher) {
		super(x_coord, y_coord, width, height);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setElements();
		this.setTeacher(teacher);
	}
	
	private Teacher teacher;

	JTextField studentIdTextField = new JTextField();
	JTextField moduleIdTextField = new JTextField();
	JTextField marksTextField = new JTextField();
	
	static StandardButton okBtn = new StandardButton();
	static StandardButton closeBtn = new StandardButton();
	
	private int axisX = 8;
	private int axisY = 110;
	private final int width = 185;
	private final int height = 32;
	
	public void setElements() {
		JLabel titleLabel = new JLabel();
		titleLabel.setText("Mark Student");
		titleLabel.setBounds(0, 0, 500, 100);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new BigBold());
		
		String[] labelItems = {
				"Teacher Id: ","Module Id: ", "Marks: "
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
				studentIdTextField, moduleIdTextField, marksTextField
		};
		
		for (Component field : fieldItems) {
			field.setBounds(axisX, axisY, width, height);
			field.setFont(new RegularFont());
			axisY = incrementPosition(axisY);
		}
		
		okBtn.setText("Mark");
		okBtn.setBounds(100, 275, 100, 35);
		okBtn.addActionListener(this);
		
		closeBtn.setText("Cancel");
		closeBtn.setBounds(300, 275, 100, 35);
		closeBtn.addActionListener(this);
		
		ArrayList<Component> allComponents = new ArrayList<>(Arrays.asList(
				studentIdTextField, moduleIdTextField, marksTextField,okBtn, closeBtn
		));	
		for(Component comp : allComponents) {
			this.add(comp);
		}
		
		add(titleLabel);
		setPlaceHolders();
	}
	
	public void setDefaultCloseOperation() {
		removeActionListeners();
		removeAll();
		dispose();
	}
	
	private static int incrementPosition(int x) {
		return x += 35;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == okBtn) {
			String moduleId = moduleIdTextField.getText().trim();
			String studentId = studentIdTextField.getText().trim();
			String marksString = marksTextField.getText().trim();
			try {
				int marks = Integer.parseInt(marksString);
				if (!Pattern.matches("^[0-9]{7}$", studentId)) {
				    throw new FormException("Invalid teacher id");
				}if (!((marks >= 0) && (marks <= 100))) {
				    throw new FormException("Invalid marks");
				}
				
				Class.forName(DatabaseConstant.CLASSNAME);
			    Connection conn = DriverManager.getConnection(DatabaseConstant.URL, DatabaseConstant.USERNAME, DatabaseConstant.PASSWORD);

			    // Check if the module exists
			    String query1 = "SELECT module_id FROM module WHERE module_id = ?";
			    PreparedStatement pst1 = conn.prepareStatement(query1);
			    pst1.setString(1, moduleId);

			    ResultSet result1 = pst1.executeQuery();

			    int rows = 0;

			    while (result1.next()) {
			        rows++;
			    }

			    if (rows == 0) {
			        throw new FormException("Invalid module id");
			    }

			    query1 = "SELECT module_id FROM module WHERE module_id = ?";
			    pst1 = conn.prepareStatement(query1);
			    pst1.setString(1, moduleId);

			    result1 = pst1.executeQuery();

			    if (!result1.next()) {
			        throw new FormException("Invalid module id");
			    }

			    // Check if the teacher exists
			    String query2 = "SELECT teacher_id FROM teacher WHERE teacher_id = ?";
			    PreparedStatement pst2 = conn.prepareStatement(query2);
			    pst2.setString(1, teacher.getId());

			    ResultSet result2 = pst2.executeQuery();

			    if (!result2.next()) {
			        throw new FormException("Invalid teacher id");
			    }

			    // Check if the teacher is assigned to the module
			    String query3 = "SELECT * FROM teacher_module WHERE teacher_id = ? AND module_id = ?";
			    PreparedStatement pst3 = conn.prepareStatement(query3);
			    pst3.setString(1, teacher.getId());
			    pst3.setString(2, moduleId);

			    ResultSet result3 = pst3.executeQuery();

			    if (!result3.next()) {
			        throw new FormException("Teacher is not assigned to the module");
			    }

			    // Update marks for the student and module
			    String updateQuery = "UPDATE student_enrollment SET marks = ? WHERE student_id = ? AND module_id = ?";
			    PreparedStatement updatePst = conn.prepareStatement(updateQuery);
			    updatePst.setInt(1, marks);
			    updatePst.setString(2, studentId);
			    updatePst.setString(3, moduleId);

			    int rowsAffected = updatePst.executeUpdate();

			    if (rowsAffected > 0) {
			        JOptionPane.showMessageDialog(null, "Marks Successfully Updated", "Success", JOptionPane.INFORMATION_MESSAGE);
			        resetFields();
			    } else {
			        throw new DatabaseException("Unsuccessful. Please try again");
			    }

			    conn.close();
			}catch (SQLException sqle) {
				JOptionPane.showMessageDialog(null, "Database Error", "Error", JOptionPane.WARNING_MESSAGE);
			}catch (FormException fe) {
				String error = fe.getMessage();
				JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.WARNING_MESSAGE);
			}catch (ClassNotFoundException exp) {
				JOptionPane.showMessageDialog(null, "Please try again", "Error", JOptionPane.WARNING_MESSAGE);
			}catch (Exception exp) {
				JOptionPane.showMessageDialog(null, "Please try again", "Error", JOptionPane.WARNING_MESSAGE);
			}
			
		}
		
		setDefaultCloseOperation();
	}
	
	private void setPlaceHolders() {
		TextPrompt moduleIdPlaceHolder = new TextPrompt("Module Id", moduleIdTextField);
		TextPrompt teacherIdPlaceHolder = new TextPrompt("Student Id", studentIdTextField);
		TextPrompt marksPlaceHolder = new TextPrompt("Marks", marksTextField);
		
		ArrayList<Component> allComponents = new ArrayList<>(Arrays.asList(
				moduleIdPlaceHolder, teacherIdPlaceHolder, marksPlaceHolder
				));	
		
		for(Component placeHolderComponent : allComponents) {
			placeHolderComponent.setFont(new PlaceHolderFont());
		}
	}
	
	private void resetFields() {
		moduleIdTextField.setText("");
		studentIdTextField.setText("");
		marksTextField.setText("");
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	private void removeActionListeners() {
		StandardButton[] buttons = {
				okBtn, closeBtn
		};
		for(StandardButton button : buttons) {
			button.removeActionListener(this);
		}
	}
	
}
