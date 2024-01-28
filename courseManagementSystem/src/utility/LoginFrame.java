package utility;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import driver.Main;
import exception.DatabaseException;
import exception.FormExecption;
import font.HeadingFont;
import font.PlaceHolderFont;
import font.SubHeadingFont;
import user.Student;
import user.User;
import font.RegularFont;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class LoginFrame extends StandardFrame implements ActionListener{
	
	public JPanel loginPanel = new StandardPanel(275, 75, 450, 450);
	public JLabel loginTitle = new JLabel();
	
	public JLabel idLabel = new JLabel();
	public JLabel passwordLabel = new JLabel();
	
	public static JTextField idTextField = new JTextField();
	public static JPasswordField passwordPasswordField = new JPasswordField();

	public JLabel registerLabel = new JLabel();
	
	public JButton okBtnLogin = new StandardButton();
	
	private int axisX = 8;
	private int axisY = 150;
	private final int width = 185;
	private final int height = 32;
	
	public LoginFrame(){
		super();
	}
	
	public LoginFrame(int x_coord, int y_coord, int width, int height){
		super(x_coord, y_coord, width, height);
	}
	
	public LoginFrame(int x_coord, int y_coord, int width, int height, Color color){
		super(x_coord, y_coord, width, height, color);
	}
	
	public void setLoginElements() {
		loginTitle.setText("Login");
		loginTitle.setBounds(200, 10, 75, 35);
		loginTitle.setFont(new HeadingFont());
		
		idLabel.setText("Id: ");
		idLabel.setBounds(axisX, axisY, width, height);
		idLabel.setFont(new SubHeadingFont());
		
		axisY = incrementPosition(axisY);
		
		passwordLabel.setText("Password: ");
		passwordLabel.setBounds(axisX, axisY, width, height);
		passwordLabel.setFont(new SubHeadingFont());

		axisX = 250;
		axisY = 150;

		idTextField.setBounds(axisX, axisY, width, height);
		idTextField.setFont(new RegularFont());
		
		axisY = incrementPosition(axisY);
		
		passwordPasswordField.setBounds(axisX, axisY, width, height);
		passwordPasswordField.setFont(new RegularFont());
		
		setPlaceHolders();
		
		ArrayList<Component> allLabelComponents = new ArrayList<>(Arrays.asList(
				idLabel, passwordLabel));
		for(Component labelComponent : allLabelComponents) {
			((JLabel) labelComponent).setHorizontalAlignment(SwingConstants.RIGHT);
		}
		
		okBtnLogin.setText("Login");
		okBtnLogin.setBounds(150, 350, 150, 35);
		okBtnLogin.addActionListener(this);
		
		registerLabel.setText("No account?");
		registerLabel.setForeground(new Color(0x321D2F));
		registerLabel.setBounds(190, 300, 85, 25);
		registerLabel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				Main.registerFrameDisplay();
			}

			@Override
			public void mouseClicked(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {
				registerLabel.setForeground(new Color(0xF4D160));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				registerLabel.setForeground(new Color(0x321D2F));
			}
		});
		
		loginPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		ArrayList<Component> allComponents = new ArrayList<>(Arrays.asList(
				loginTitle, idLabel, passwordLabel, idTextField, passwordPasswordField,
				okBtnLogin, registerLabel));	
		
		for(Component comp : allComponents) {
			loginPanel.add(comp);
		}
		
		add(loginPanel);
	}
	
	private void setPlaceHolders(){
		TextPrompt namePlaceHolder = new TextPrompt("Id", idTextField);
		TextPrompt passwordPlaceHolder = new TextPrompt("*********", passwordPasswordField);
		
		ArrayList<Component> allComponents = new ArrayList<>(Arrays.asList(
				namePlaceHolder, passwordPlaceHolder));	
		
		for(Component placeHolderComponent : allComponents) {
			placeHolderComponent.setFont(new PlaceHolderFont());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == okBtnLogin) {
			User user1 = new User();
			user1.setId(idTextField.getText());
			String password = new String(passwordPasswordField.getPassword());
			try {
				if(!Pattern.matches("^[0-9]{7}$", user1.getId())) {
					throw new FormExecption("Invalid id");
				}else if(!Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$", password)) {
					throw new FormExecption("Invalid password");
				}	
				try {
					password = Main.hashAlgorithm(password);
					
					Class.forName("com.mysql.jdbc.Driver");
					Connection conn = DriverManager.getConnection(DatabaseConstant.URL, DatabaseConstant.USERNAME, DatabaseConstant.PASSWORD);
					String query = "SELECT type_of_user FROM user WHERE student_id = ? OR teacher_id = ? OR admin_id = ?";
					PreparedStatement pst = conn.prepareStatement(query);
					
					pst.setString(1, user1.getId());
					pst.setString(2, user1.getId());
					pst.setString(3, user1.getId());
					ResultSet result = pst.executeQuery();
					
					if(!result.next()) {
						throw new DatabaseException("Credentials invalid");
					}else {
						String typeOfUser = result.getString(1);
						user1.setTypeOfUser(typeOfUser);
						
						query = "SELECT * FROM " + user1.getTypeOfUser() + " WHERE " + user1.getTypeOfUser() + "_id = " + user1.getId();
						
						pst = conn.prepareStatement(query);
						
						result = pst.executeQuery();
						
						if(!result.next()) {
							throw new DatabaseException("Credentials invalid");
						}else {
							String dbPassword = result.getString(4);
							if (password.equals(dbPassword)) {
								JOptionPane.showMessageDialog(null, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
						        resetFields();
						        if(user1.getTypeOfUser().equals("student")) {
						        	System.out.println("Student");
						        	Student student1 = new Student();
						        	student1.setName(result.getString("name"));
									student1.setId(result.getString("student_id"));
									student1.setEmail(result.getString("email"));
									student1.setPassword(result.getString("password"));
									student1.setContact(result.getString("contact"));
									student1.setTypeOfUser("student");
									student1.setDateOfBirth(result.getDate("dob"));
									student1.setFaculty(result.getString(7));
									student1.setLevel(result.getInt(8));
							        Main.studentDashboardFrameDisplay(this, student1);
						        }else if(user1.getTypeOfUser().equals("teacher")) {
						        	System.out.println("Teacher");
//						        	Main.dashboardFrameDisplay(this, user1.getId(), "teacher");
						        }else {
						        	System.out.println("Admin");
//						        	Main.dashboardFrameDisplay(this, user1.getId(), "admin");
						        }
							}else {
								throw new DatabaseException("Credentials invalid");
							}
						}
						conn.close();
					}

				}catch (DatabaseException dbe) {
					String exp = dbe.getMessage();
		            JOptionPane.showMessageDialog(null, exp, "Error", JOptionPane.WARNING_MESSAGE);
				} catch (Exception exp) {
					String ex = exp.getMessage();
		            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.WARNING_MESSAGE);
				} 
			}catch(FormExecption fe) {
				String exp = fe.getMessage();
	            System.out.println(fe);
	            JOptionPane.showMessageDialog(null, exp, "Error", JOptionPane.WARNING_MESSAGE);
			}
			
			
		}	
	}
	
	private static void resetFields() {
		idTextField.setText("");	
		passwordPasswordField.setText("");
	}

	private static int incrementPosition(int x) {
		return x += 35;
	}
	
	
}
