package component.frame.course;

import java.awt.Component;
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

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import component.button.StandardButton;
import component.frame.StandardFrame;
import exception.FormException;
import font.BigBold;
import font.PlaceHolderFont;
import font.RegularFont;
import font.SubHeadingFont;
import utility.DatabaseConstant;
import utility.TextPrompt;

public class EditCourseFrame extends StandardFrame implements ActionListener, MouseListener{
	
	public EditCourseFrame(int x_coord, int y_coord, int width, int height) {
		super(x_coord, y_coord, width, height);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setElements();
		toFront();
	}
	
	JTextField courseNameTextField = new JTextField();
	JTextField courseIdTextField = new JTextField();
	
	static StandardButton okBtn = new StandardButton();
	static StandardButton closeBtn = new StandardButton();
	static StandardButton getDetailsBtn = new StandardButton();
	
	private int axisX = 8;
	private int axisY = 110;
	private final int width = 185;
	private final int height = 32;
	
	public void setElements() {
		JLabel titleLabel = new JLabel();
		titleLabel.setText("Edit a course");
		titleLabel.setBounds(0, 0, 500, 100);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new BigBold());
		
		String[] labelItems = {
				"Course Id: ", "Course Name: "
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
				courseIdTextField, courseNameTextField
		};
		
		for (Component field : fieldItems) {
			field.setBounds(axisX, axisY, width, height);
			field.setFont(new RegularFont());
			axisY = incrementPosition(axisY);
		}
		
		okBtn.setText("Confirm");
		okBtn.setBounds(50, 275, 100, 35);
		okBtn.addMouseListener(this);
		
		getDetailsBtn.setText("Get Details");
		getDetailsBtn.setBounds(200, 275, 100, 35);
		getDetailsBtn.addMouseListener(this);
		
		closeBtn.setText("Cancel");
		closeBtn.setBounds(350, 275, 100, 35);
		closeBtn.addActionListener(this);
		
		ArrayList<Component> allComponents = new ArrayList<>(Arrays.asList(
				courseNameTextField, courseIdTextField,
			okBtn, getDetailsBtn, closeBtn
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
	public void actionPerformed(ActionEvent e){
		
		setDefaultCloseOperation();
		
	}
	
	private void setPlaceHolders() {
		TextPrompt namePlaceHolder = new TextPrompt("Name", courseNameTextField);
		TextPrompt idPlaceHolder = new TextPrompt("Id", courseIdTextField);
		
		ArrayList<Component> allComponents = new ArrayList<>(Arrays.asList(
				namePlaceHolder, idPlaceHolder));	
		
		for(Component placeHolderComponent : allComponents) {
			placeHolderComponent.setFont(new PlaceHolderFont());
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getSource() == okBtn) {
			String name = courseNameTextField.getText().trim();
			String id = courseIdTextField.getText().trim();
			
			try {
				
				Class.forName(DatabaseConstant.CLASSNAME);
				Connection conn = DriverManager.getConnection(DatabaseConstant.URL, DatabaseConstant.USERNAME, DatabaseConstant.PASSWORD);
				
				// select course id
				String query1 = "SELECT course_id FROM course WHERE course_id = ?";
				
				PreparedStatement pst1 = conn.prepareStatement(query1);
				
				int index = 1;
				
				pst1.setString(index, id);	
				
				ResultSet result = pst1.executeQuery();

				int rows = 0;
				
				while (result.next()) {
					rows++;
				}
				
				if (rows == 0) {
					throw new FormException("Id not found");
				}
				
				// update course
				String query2 = "UPDATE course SET course_name=? WHERE course_id=?";
	            
	            PreparedStatement updateStatement = conn.prepareStatement(query2);
	            updateStatement.setString(1, name);
	            updateStatement.setString(2, id);
	            
	            updateStatement.executeUpdate();

	            setDefaultCloseOperation();
				conn.close();
			}catch (SQLException sqle) {
				JOptionPane.showMessageDialog(null, "Database Error", "Error", JOptionPane.WARNING_MESSAGE);
			}catch (Exception exp) {
				JOptionPane.showMessageDialog(null, "Please try again", "Error", JOptionPane.WARNING_MESSAGE);
			}
		}else {
			String id = courseIdTextField.getText().trim();
			
			try {
				Class.forName(DatabaseConstant.CLASSNAME);
				Connection conn = DriverManager.getConnection(DatabaseConstant.URL, DatabaseConstant.USERNAME, DatabaseConstant.PASSWORD);
				
				// select course
				String query1 = "SELECT * FROM course WHERE course_id = ?";
				
				PreparedStatement pst1 = conn.prepareStatement(query1);
				
				int index = 1;
				
				pst1.setString(index, id);	
				
				ResultSet result = pst1.executeQuery();

				int rows = 0;
				
				while (result.next()) {
					rows++;
					String nameDb = result.getString("course_name");

				    courseNameTextField.setText(nameDb);
				}
				
				if (rows == 0) {
					throw new FormException("Id not found");
				}
				
			    conn.close();
			}catch (SQLException sqle) {
				JOptionPane.showMessageDialog(null, "Database Error", "Error", JOptionPane.WARNING_MESSAGE);
			}catch (FormException fe) {
				String error = fe.getMessage();
				JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.WARNING_MESSAGE);
			}catch (Exception exp) {
				JOptionPane.showMessageDialog(null, "Please try again", "Error", JOptionPane.WARNING_MESSAGE);
			}
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
	
	private void removeActionListeners() {
		StandardButton[] buttons = {
				okBtn, closeBtn, getDetailsBtn
		};
		for(StandardButton button : buttons) {
			button.removeActionListener(this);
			button.removeMouseListener(this);
		}
	}
	
}
