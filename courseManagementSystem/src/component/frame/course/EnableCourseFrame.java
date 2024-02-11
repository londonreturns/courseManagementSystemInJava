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
import java.util.regex.Pattern;

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

public class EnableCourseFrame extends StandardFrame implements ActionListener, MouseListener{
	
	public EnableCourseFrame(int x_coord, int y_coord, int width, int height) {
		super(x_coord, y_coord, width, height);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setElements();
	}
	
	JTextField courseNameTextField = new JTextField();
	JTextField courseIdTextField = new JTextField();
	JTextField isEnabledTextField = new JTextField();
	
	static StandardButton okBtn = new StandardButton();
	static StandardButton closeBtn = new StandardButton();
	static StandardButton getDetailsBtn = new StandardButton();
	
	private int axisX = 8;
	private int axisY = 110;
	private final int width = 185;
	private final int height = 32;
	
	public void setElements() {
		JLabel titleLabel = new JLabel();
		titleLabel.setText("Enable course");
		titleLabel.setBounds(0, 0, 500, 100);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new BigBold());
		
		courseNameTextField.setEditable(false);
		isEnabledTextField.setEditable(false);
		
		String[] labelItems = {
				"Course Id: ", "Course Name: ", "Enabled: "
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
				courseIdTextField, courseNameTextField, isEnabledTextField
		};
		
		for (Component field : fieldItems) {
			field.setBounds(axisX, axisY, width, height);
			field.setFont(new RegularFont());
			axisY = incrementPosition(axisY);
		}
		
		okBtn.setText("Enable");
		okBtn.setBounds(50, 275, 100, 35);
		okBtn.addMouseListener(this);
		
		getDetailsBtn.setText("Get Details");
		getDetailsBtn.setBounds(200, 275, 100, 35);
		getDetailsBtn.addMouseListener(this);
		
		closeBtn.setText("Cancel");
		closeBtn.setBounds(350, 275, 100, 35);
		closeBtn.addActionListener(this);
		
		ArrayList<Component> allComponents = new ArrayList<>(Arrays.asList(
				courseNameTextField, courseIdTextField, isEnabledTextField,
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
		TextPrompt idPlaceHolder = new TextPrompt("Id", courseIdTextField);
		
		ArrayList<Component> allComponents = new ArrayList<>(Arrays.asList(
				idPlaceHolder));	
		
		for(Component placeHolderComponent : allComponents) {
			placeHolderComponent.setFont(new PlaceHolderFont());
		}
	}
	
	private void resetFields() {
		courseNameTextField.setText("");
		courseIdTextField.setText("");
		isEnabledTextField.setText("");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getSource() == okBtn) {
			String id = courseIdTextField.getText().trim();
			String isEnabled = "1";
			try {
				if (!Pattern.matches("^[a-zA-Z0-9].{4,10}$", id)) {
				    throw new FormException("Invalid id");
				}
				
				Class.forName(DatabaseConstant.CLASSNAME);
				Connection conn = DriverManager.getConnection(DatabaseConstant.URL, DatabaseConstant.USERNAME, DatabaseConstant.PASSWORD);
				
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
				
				String query2 = "UPDATE course SET is_active = ? WHERE course_id = ?";
	            
	            PreparedStatement updateStatement = conn.prepareStatement(query2);
	            updateStatement.setString(1, isEnabled);
	            updateStatement.setString(2, id);
	            
	            int updatedRows = updateStatement.executeUpdate();

	            if (updatedRows > 0) {
	            	JOptionPane.showMessageDialog(null, "Course Successfully Updated", "Success", JOptionPane.INFORMATION_MESSAGE);
	            } else {
	                throw new FormException("Failed to update record");
	            }
	            
	            resetFields();
	            
				conn.close();
			}catch (SQLException sqle) {
				JOptionPane.showMessageDialog(null, "Database Error", "Error", JOptionPane.WARNING_MESSAGE);
			}catch (FormException fe) {
				String error = fe.getMessage();
				JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.WARNING_MESSAGE);
			}catch (Exception exp) {
				JOptionPane.showMessageDialog(null, "Please try again", "Error", JOptionPane.WARNING_MESSAGE);
			}
			
		}else if (e.getSource() == getDetailsBtn){
			String id = courseIdTextField.getText().trim();
			
			try {
				Class.forName(DatabaseConstant.CLASSNAME);
				Connection conn = DriverManager.getConnection(DatabaseConstant.URL, DatabaseConstant.USERNAME, DatabaseConstant.PASSWORD);
				
				String query1 = "SELECT * FROM course WHERE course_id = ?";
				
				PreparedStatement pst1 = conn.prepareStatement(query1);
				
				int index = 1;
				
				pst1.setString(index, id);	
				
				ResultSet result = pst1.executeQuery();

				int rows = 0;
				
				while (result.next()) {
					rows++;
					String nameDb = result.getString("course_name");
				    Boolean isEnabled = result.getBoolean("is_active");

				    courseNameTextField.setText(nameDb);
					isEnabledTextField.setText(String.valueOf(isEnabled));
				}
				
				if (rows == 0) {
					throw new FormException("Failed to get details");
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
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
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
