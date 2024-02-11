package component.frame.teacher;

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
import utility.DatabaseConstant;
import utility.TextPrompt;

public class UnassignModuleFrame extends StandardFrame implements ActionListener{
	
	public UnassignModuleFrame(int x_coord, int y_coord, int width, int height) {
		super(x_coord, y_coord, width, height);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setElements();
	}

	JTextField teacherIdTextField = new JTextField();
	JTextField moduleIdTextField = new JTextField();
	
	static StandardButton okBtn = new StandardButton();
	static StandardButton closeBtn = new StandardButton();
	
	private int axisX = 8;
	private int axisY = 110;
	private final int width = 185;
	private final int height = 32;
	
	public void setElements() {
		JLabel titleLabel = new JLabel();
		titleLabel.setText("Unassign module");
		titleLabel.setBounds(0, 0, 500, 100);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new BigBold());
		
		String[] labelItems = {
				"Teacher Id: ","Module Id: ",
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
				teacherIdTextField, moduleIdTextField, 
		};
		
		for (Component field : fieldItems) {
			field.setBounds(axisX, axisY, width, height);
			field.setFont(new RegularFont());
			axisY = incrementPosition(axisY);
		}
		
		okBtn.setText("Unassign");
		okBtn.setBounds(100, 275, 100, 35);
		okBtn.addActionListener(this);
		
		closeBtn.setText("Cancel");
		closeBtn.setBounds(300, 275, 100, 35);
		closeBtn.addActionListener(this);
		
		ArrayList<Component> allComponents = new ArrayList<>(Arrays.asList(
				teacherIdTextField, moduleIdTextField, okBtn, closeBtn
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
			String moduleId = moduleIdTextField.getText().trim();
			String teacherId = teacherIdTextField.getText().trim();
			try {
				
				Class.forName(DatabaseConstant.CLASSNAME);
				Connection conn = DriverManager.getConnection(DatabaseConstant.URL, DatabaseConstant.USERNAME, DatabaseConstant.PASSWORD);
				
				String query1 = "SELECT module_id FROM module WHERE module_id = ?";
				
				PreparedStatement pst1 = conn.prepareStatement(query1);
				
				int index = 1;
				
				pst1.setString(index, moduleId);	
				
				ResultSet result1 = pst1.executeQuery();

				int rows = 0;
				
				while (result1.next()) {
					rows++;
				}
				
				if (rows == 0) {
					throw new FormException();
				}
				
				String query2 = "SELECT teacher_id FROM teacher WHERE teacher_id = ?";
	            
	            PreparedStatement pst2 = conn.prepareStatement(query2);
	            
	            index = 1;
	            
	            pst2.setString(index, teacherId);
	            
	            ResultSet result2 = pst2.executeQuery();

	            rows = 0;
				
				while (result2.next()) {
					rows++;
				}
				
				if (rows == 0) {
					throw new FormException();
				}
				
				String query3 = "SELECT * FROM teacher_module WHERE teacher_id = ? AND module_id = ?";
	            
	            PreparedStatement pst3 = conn.prepareStatement(query3);
	            
	            index = 1;
	            
	            pst3.setString(index, teacherId);
	            
	            index++;
	            
	            pst3.setString(index, moduleId);
	            
	            ResultSet result3 = pst3.executeQuery();

	            rows = 0;
				
				while (result3.next()) {
					rows++;
				}
				
				if (rows == 0) {
					throw new FormException("Not assgined.");
				}
				
				String query4 = "DELETE FROM teacher_module WHERE teacher_id = ? AND module_id = ?";
				
				PreparedStatement pst4 = conn.prepareStatement(query4);
				
				index = 1;
				
				pst4.setString(index, teacherId);
				
				index++;
				
				pst4.setString(index, moduleId);
	            
				int rowsAffected = pst4.executeUpdate();

				conn.close();
			    if (rowsAffected > 0) {
			        JOptionPane.showMessageDialog(null, "Sucessfully Unassigned",
			        		"Success", JOptionPane.INFORMATION_MESSAGE);
			        resetFields();
			    } else {
			        throw new DatabaseException("Unsuccessful. Please try again");
			    }
			    removeAll();
				dispose();
			    
	            resetFields();
	            
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
			
		}else {
			removeAll();
			dispose();
		}
	}
	
	private void setPlaceHolders() {
		TextPrompt moduleIdPlaceHolder = new TextPrompt("Module Id", moduleIdTextField);
		TextPrompt teacherIdPlaceHolder = new TextPrompt("Teacher Id", teacherIdTextField);
		
		ArrayList<Component> allComponents = new ArrayList<>(Arrays.asList(
				moduleIdPlaceHolder, teacherIdPlaceHolder));	
		
		for(Component placeHolderComponent : allComponents) {
			placeHolderComponent.setFont(new PlaceHolderFont());
		}
	}
	
	private void resetFields() {
		teacherIdTextField.setText("");
		moduleIdTextField.setText("");
	}
	
}
