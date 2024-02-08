package component.frame;

import font.HeadingFont;
import font.SubHeadingFont;
import user.Admin;
import user.Student;
import user.Teacher;
import utility.DatabaseConstant;
import utility.TextPrompt;
import font.RegularFont;
import font.PlaceHolderFont;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.toedter.calendar.JDateChooser;

import component.button.StandardButton;
import component.panel.StandardPanel;
import driver.*;
import exception.DatabaseException;
import exception.DateException;
import exception.FormException;

public class RegisterFrame extends StandardFrame implements ActionListener,  ItemListener{
	
	JPanel registerPanel = new StandardPanel(100, 75, 450, 550);
	JLabel registerTitle = new JLabel();
	
	JLabel nameLabel = new JLabel();
	JLabel emailLabel = new JLabel();
	JLabel passwordLabel = new JLabel();
	JLabel confirmPasswordLabel = new JLabel();
	JLabel contactNumberLabel = new JLabel();
	JLabel dateOfBirthLabel = new JLabel();
	JLabel typeOfUserLabel = new JLabel();
	JLabel facultyLabel = new JLabel();
	
	JTextField nameTextField = new JTextField();
	JTextField emailTextField = new JTextField();
	JTextField contactNumberField = new JTextField();
	
	JDateChooser dateOfBirthDate = new JDateChooser();
	
	String[] typeOfUsers = {"Student", "Teacher", "Admin"};
	
	public JComboBox<String> typeOfUserCombo = new JComboBox<String>(typeOfUsers);
	
	String[] faculties = {"BCS", "BIBM"};
	
	JComboBox<String> facultyCombo = new JComboBox<String>(faculties);
	
	JPasswordField passwordPasswordField = new JPasswordField();
	JPasswordField confirmPasswordField = new JPasswordField();
	
	JLabel loginLabel = new JLabel();
	
	JButton okBtnRegister = new StandardButton();
	
	private int axisX = 8;
	private int axisY = 110;
	private final int width = 185;
	private final int height = 32;
	
	public RegisterFrame(){
		super();
	}
	
	public RegisterFrame(int x_coord, int y_coord, int width, int height){
		super(x_coord, y_coord, width, height);
	}
	
	public RegisterFrame(int x_coord, int y_coord, int width, int height, Color color){
		super(x_coord, y_coord, width, height, color);
	}
	
	public void setRegisterElements() {
		try {
			registerTitle.setText("Register");
			registerTitle.setBounds(185, 10, 175, 35);
			registerTitle.setFont(new HeadingFont());
			
			String[] labelItems = {
					"Name: ", "Email: ", "Password: ", "Confirm Password: ", "Contact Number: ",
					"Date of Birth: ", "User Type: "
			};
			
			for (String menu : labelItems) {
				JLabel menuLabels = new JLabel();
				menuLabels.setText(menu);
				menuLabels.setBounds(axisX, axisY, width, height);
				menuLabels.setFont(new SubHeadingFont());
				axisY = incrementPosition(axisY);
				registerPanel.add(menuLabels);
				((JLabel) menuLabels).setHorizontalAlignment(SwingConstants.RIGHT);
			}
			
			facultyLabel.setText("Faculty: ");
			facultyLabel.setFont(new SubHeadingFont());
			facultyLabel.setVisible(true);
			facultyLabel.setBounds(axisX, axisY, width, height);
			
			registerPanel.add(facultyLabel);
			
			setPlaceHolders();
			
			ArrayList<Component> allLabelComponents = new ArrayList<>(Arrays.asList(
					nameLabel, emailLabel, passwordLabel, confirmPasswordLabel,
					contactNumberLabel, dateOfBirthLabel, typeOfUserLabel, facultyLabel));
			for(Component labelComponent : allLabelComponents) {
				((JLabel) labelComponent).setHorizontalAlignment(SwingConstants.RIGHT);
			}

			axisX = 250;
			axisY = 115;
			
			Component[] fieldItems = {
					nameTextField, emailTextField, passwordPasswordField, confirmPasswordField,
					contactNumberField, dateOfBirthDate, typeOfUserCombo, facultyCombo
			};
			
			for (Component field : fieldItems) {
				field.setBounds(axisX, axisY, width, height);
				field.setFont(new RegularFont());
				axisY = incrementPosition(axisY);
			}
			
			typeOfUserCombo.setBackground(Color.white);
			typeOfUserCombo.addItemListener(this);
			
			facultyCombo.setBackground(Color.white);
			
			okBtnRegister.setText("Register");
			okBtnRegister.setBounds(150, 450, 150, 35);
			okBtnRegister.addActionListener(this);
			
			loginLabel.setText("Already have an account?");
			loginLabel.setForeground(new Color(0x321D2F));
			loginLabel.setBounds(150, 400, 175, 25);
			loginLabel.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					Main.loginFrameDisplay();
				}

				@Override
				public void mouseClicked(MouseEvent e) {}

				@Override
				public void mousePressed(MouseEvent e) {}

				@Override
				public void mouseEntered(MouseEvent e) {
//					registrationLabel.setFont(new Font("Dialog", Font.BOLD, 13));
					loginLabel.setForeground(new Color(0xF4D160));
				}

				@Override
				public void mouseExited(MouseEvent e) {
//					registrationLabel.setFont(new Font("Dialog", Font.BOLD, 12));
					loginLabel.setForeground(new Color(0x321D2F));
				}
			});
			
			registerPanel.setBorder(BorderFactory.createLineBorder(Color.black));
			
			ArrayList<Component> allComponents = new ArrayList<>(Arrays.asList(
					registerTitle, nameLabel, emailLabel, passwordLabel,
					confirmPasswordLabel, contactNumberLabel, dateOfBirthLabel, typeOfUserLabel,
					facultyLabel, nameTextField, emailTextField, passwordPasswordField,
					confirmPasswordField, contactNumberField,
					dateOfBirthDate, okBtnRegister, loginLabel, typeOfUserCombo, facultyCombo));	
			for(Component comp : allComponents) {
				registerPanel.add(comp);
			}
			
			add(registerPanel);
		}catch(NullPointerException npe) {
			
		}catch(Exception e) {
			
		}finally {
			axisX = 8;
			axisY = 110;
		}
	}
		

	private void setPlaceHolders() {
		TextPrompt namePlaceHolder = new TextPrompt("Name", nameTextField);
		TextPrompt emailPlaceHolder = new TextPrompt("Email", emailTextField);
		TextPrompt passwordPlaceHolder = new TextPrompt("*********", passwordPasswordField);
		TextPrompt confirmPasswordPlaceHolder = new TextPrompt("*********", confirmPasswordField);
		TextPrompt contactNumberPlaceHolder = new TextPrompt("0123456789", contactNumberField);
		
		ArrayList<Component> allComponents = new ArrayList<>(Arrays.asList(
				namePlaceHolder, emailPlaceHolder, passwordPlaceHolder,
				confirmPasswordPlaceHolder, contactNumberPlaceHolder));	
		
		for(Component placeHolderComponent : allComponents) {
			placeHolderComponent.setFont(new PlaceHolderFont());
		}
	}

	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == typeOfUserCombo && e.getStateChange() == ItemEvent.SELECTED) {
			if (typeOfUserCombo.getSelectedIndex() == 0) {
	            facultyLabel.setVisible(true);
	            facultyCombo.setVisible(true);
	        } else {
	            facultyCombo.setVisible(false);
	            facultyLabel.setVisible(false);
	        }
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		if (e.getSource() == okBtnRegister) {
			try {
				String name = nameTextField.getText().trim();
				String email = emailTextField.getText().trim();
				String password = new String(passwordPasswordField.getPassword());
				String confirmPassword = new String(confirmPasswordField.getPassword());
				String contactNumber = new String(contactNumberField.getText().trim());
				String user = ((String) typeOfUserCombo.getSelectedItem()).toLowerCase();
				
				Date dateOfBirth =  dateOfBirthDate.getDate();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String dob = dateFormat.format(dateOfBirth);
				LocalDate convertedDob = dateOfBirth.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
				LocalDate today = LocalDate.now();
				
				if (convertedDob.isAfter(today)) {
					throw new DateException("Invalid date");
				}else if(name.length() == 0 || name.length() > 50) {
					throw new FormException("Invalid name");
				}else if(!Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$", password)) {
					throw new FormException("Invalid password");
				}else if(!password.equals(confirmPassword)) {
					throw new FormException("Passwords do not match");
				}else if(!Pattern.matches("^[0-9]{10}$", contactNumber)) {
					throw new FormException("Invalid contact number");
				}else if(!Pattern.matches("^[a-zA-Z0-9._@]{9,50}$", email)) {
					throw new FormException("Invalid email");
				}
				
				password = Main.hashAlgorithm(password);
				
				int tempId = 0;
				String id = "";
				
				if(user.equals("student")) {					
					String faculty = (String) facultyCombo.getSelectedItem();
					System.out.println(user);
					try {						
						Class.forName(DatabaseConstant.CLASSNAME);
						Connection conn = DriverManager.getConnection(DatabaseConstant.URL, DatabaseConstant.USERNAME, DatabaseConstant.PASSWORD);
						
						while (!false) {
							tempId = (int)(Math.random() * (9999999 - 1000000)) + 1000000;
							id = Integer.toString(tempId);
							System.out.println("Generated ID: " + tempId);
							
							String query = "SELECT * FROM user WHERE student_id = ? OR teacher_id = ? OR admin_id = ?";
							PreparedStatement pst = conn.prepareStatement(query);
							
							pst.setString(1, id);
							pst.setString(2, id);
							pst.setString(3, id);
							ResultSet result = pst.executeQuery();

							int rows = 0;
							
							while (result.next()) {
								rows++;
							}
							if(!(rows > 0)) {
								break;
							}
							
						}

						String level;
						
						if(faculty.equals("BCS")) {
							level = "4";
						}else {
							level = "5";
						}
						
						Student student1 = new Student();
						student1.setName(name);
						student1.setId(id);
						student1.setEmail(email);
						student1.setPassword(password);
						student1.setContact(contactNumber);
						student1.setTypeOfUser(user);
						student1.setDateOfBirth(new java.sql.Date(dateOfBirth.getTime()));
						student1.setFaculty(faculty);
						student1.setLevel(level);
						
						String query = "INSERT INTO " + student1.getTypeOfUser() + " (student_id, name, email, password, contact, dob, faculty, level)"
								+ " VALUES"
								+ " (?, ?, ?, ?, ?, ?, ?, ?)";
						
						PreparedStatement pst = conn.prepareStatement(query);
						
						int index = 1;
						
						pst.setString(index, student1.getId());
						
						index++;
						
						pst.setString(index, student1.getName());
						
						index++;
						
						pst.setString(index, student1.getEmail());
						
						index++;
						
						pst.setString(index, student1.getPassword());
						
						index++;
						
						pst.setString(index, student1.getContact());
						
						index++;
						
						pst.setDate(index, student1.getDateOfBirth());
						
						index++;
						
						pst.setString(index, student1.getFaculty());
						
						index++;						

						pst.setString(index, student1.getLevel());
						
						int rowsAffected = pst.executeUpdate();
						
						query = "INSERT INTO user (student_id, name, type_of_user)"
								+ " VALUES"
								+ " (?, ?, ?)";
						
						pst = conn.prepareStatement(query);
						
						index = 1;
						
						pst.setString(index, student1.getId());
						
						index++;
						
						pst.setString(index, student1.getName());
						
						index++;
						
						pst.setString(index, student1.getTypeOfUser());
						
						rowsAffected += pst.executeUpdate();

					    if (rowsAffected > 0) {
					        JOptionPane.showMessageDialog(null, "Registration successful!"
					        		+ "\nYour id is " + id, "Success", JOptionPane.INFORMATION_MESSAGE);
					        resetFields();
					    } else {
					        throw new DatabaseException("Registration unsuccessful");
					    }
					    conn.close();
			        } catch (DatabaseException dbExp) {
			            String error = dbExp.getMessage();
			            System.out.println(dbExp);
			            JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.WARNING_MESSAGE);
			        } catch (Exception exp) {
			            System.out.println(exp);
			        	String err = "Please try again" + exp;
			            JOptionPane.showMessageDialog(null, err, "Error", JOptionPane.WARNING_MESSAGE);
					}
				}else if(user.equals("teacher")) {
					try {						
						Class.forName(DatabaseConstant.CLASSNAME);
						Connection conn = DriverManager.getConnection(DatabaseConstant.URL, DatabaseConstant.USERNAME, DatabaseConstant.PASSWORD);
						
						while (!false) {
							tempId = (int)(Math.random() * (9999999 - 1000000)) + 1000000;
							id = Integer.toString(tempId);
							System.out.println("Generated ID: " + tempId);
							
							String query = "SELECT * FROM user WHERE student_id = ? OR teacher_id = ? OR admin_id = ?";
							PreparedStatement pst = conn.prepareStatement(query);
							
							pst.setString(1, id);
							pst.setString(2, id);
							pst.setString(3, id);
							ResultSet result = pst.executeQuery();

							int rows = 0;
							
							while (result.next()) {
								rows++;
							}
							if(!(rows > 0)) {
								break;
							}
							
						}
						
						Teacher teacher1 = new Teacher();
						teacher1.setId(id);
						teacher1.setName(name);
						teacher1.setEmail(email);
						teacher1.setPassword(password);
						teacher1.setContact(contactNumber);
						teacher1.setTypeOfUser(user);
						teacher1.setDateOfBirth(new java.sql.Date(dateOfBirth.getTime()));
						
						String query = "INSERT INTO " + teacher1.getTypeOfUser() + " (" + teacher1.getTypeOfUser() + "_id, name, email, password, contact, dob)"
						        + " VALUES"
						        + " (?, ?, ?, ?, ?, ?)";

						PreparedStatement pst = conn.prepareStatement(query);

						int index = 1;
						
						pst.setString(index, teacher1.getId());
						
						index++;
						
						pst.setString(index, teacher1.getName());
						
						index++;
						
						pst.setString(index, teacher1.getEmail());
						
						index++;
						
						pst.setString(index, teacher1.getPassword());
						
						index++;
						
						pst.setString(index, teacher1.getContact());
						
						index++;
						
						pst.setDate(index, teacher1.getDateOfBirth());
						
						int rowsAffected = pst.executeUpdate();
						
						query = "INSERT INTO user (" + teacher1.getTypeOfUser() + "_id, name, type_of_user)"
								+ " VALUES"
								+ " (?, ?, ?)";
						
						pst = conn.prepareStatement(query);
						
						index = 1;
						
						pst.setString(index, id);
						
						index++;
						
						pst.setString(index, name);
						
						index++;
						
						pst.setString(index, user);
						
						rowsAffected += pst.executeUpdate();

					    if (rowsAffected > 0) {
					        JOptionPane.showMessageDialog(null, "Registration successful!"
					        		+ "\nYour id is " + id, "Success", JOptionPane.INFORMATION_MESSAGE);
					        resetFields();
					    } else {
					        throw new DatabaseException("Registration unsuccessful");
					    }
					    conn.close();
			        } catch (DatabaseException dbExp) {
			            String error = dbExp.getMessage();
			            System.out.println(dbExp);
			            JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.WARNING_MESSAGE);
			        } catch (Exception error) {
			            System.out.println(error);
			        	String err = "Please try again" + error;
			            JOptionPane.showMessageDialog(null, err, "Error", JOptionPane.WARNING_MESSAGE);
					}
				}else {
					try {						
						Class.forName(DatabaseConstant.CLASSNAME);
						Connection conn = DriverManager.getConnection(DatabaseConstant.URL, DatabaseConstant.USERNAME, DatabaseConstant.PASSWORD);
						
						while (!false) {
							tempId = (int)(Math.random() * (9999999 - 1000000)) + 1000000;
							id = Integer.toString(tempId);
							System.out.println("Generated ID: " + tempId);
							
							String query = "SELECT * FROM user WHERE student_id = ? OR teacher_id = ? OR admin_id = ?";
							PreparedStatement pst = conn.prepareStatement(query);
							
							pst.setString(1, id);
							pst.setString(2, id);
							pst.setString(3, id);
							ResultSet result = pst.executeQuery();

							int rows = 0;
							
							while (result.next()) {
								rows++;
							}
							if(!(rows > 0)) {
								break;
							}
							
						}
						
						Admin admin1 = new Admin();
						admin1.setId(id);
						admin1.setName(name);
						admin1.setEmail(email);
						admin1.setPassword(password);
						admin1.setContact(contactNumber);
						admin1.setTypeOfUser(user);
						admin1.setDateOfBirth(new java.sql.Date(dateOfBirth.getTime()));
						
						String query = "INSERT INTO " + admin1.getTypeOfUser() + " (" + admin1.getTypeOfUser() + "_id, name, email, password, contact, dob)"
						        + " VALUES"
						        + " (?, ?, ?, ?, ?, ?)";

						PreparedStatement pst = conn.prepareStatement(query);

						int index = 1;
						
						pst.setString(index, admin1.getId());
						
						index++;
						
						pst.setString(index, admin1.getName());
						
						index++;
						
						pst.setString(index, admin1.getEmail());
						
						index++;
						
						pst.setString(index, admin1.getPassword());
						
						index++;
						
						pst.setString(index, admin1.getContact());
						
						index++;
						
						pst.setDate(index, admin1.getDateOfBirth());
						
						int rowsAffected = pst.executeUpdate();
						
						query = "INSERT INTO user (" + admin1.getTypeOfUser() + "_id, name, type_of_user)"
								+ " VALUES"
								+ " (?, ?, ?)";
						
						pst = conn.prepareStatement(query);
						
						index = 1;
						
						pst.setString(index, id);
						
						index++;
						
						pst.setString(index, name);
						
						index++;
						
						pst.setString(index, user);
						
						rowsAffected += pst.executeUpdate();

					    if (rowsAffected > 0) {
					        JOptionPane.showMessageDialog(null, "Registration successful!"
					        		+ "\nYour id is " + id, "Success", JOptionPane.INFORMATION_MESSAGE);
					        resetFields();
					    } else {
					        throw new DatabaseException("Registration unsuccessful");
					    }
					    conn.close();
			        } catch (DatabaseException dbExp) {
			            String error = dbExp.getMessage();
			            System.out.println(dbExp);
			            JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.WARNING_MESSAGE);
			        } catch (Exception error) {
			            System.out.println(error);
			        	String err = "Please try again" + error;
			            JOptionPane.showMessageDialog(null, err, "Error", JOptionPane.WARNING_MESSAGE);
					}
				}
				
			}catch (DateException | NullPointerException npe) {
				String error = "Incorrect Date";
				JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.WARNING_MESSAGE);
			}catch (FormException fe) {
				String error = fe.getMessage();
				JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.WARNING_MESSAGE);
			} catch (NoSuchAlgorithmException nsae) {
				String error = nsae.getMessage();
				JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.WARNING_MESSAGE);
			}
		}	
	}
	
	private void resetFields() {
		nameTextField.setText("");
		emailTextField.setText("");
		contactNumberField.setText("");
		dateOfBirthDate.setDate(null);				
		typeOfUserCombo.setSelectedIndex(0);		
		facultyCombo.setSelectedIndex(0);		
		passwordPasswordField.setText("");
		confirmPasswordField.setText("");
	}

	private static int incrementPosition(int x) {
		return x += 35;
	}
	
	
}