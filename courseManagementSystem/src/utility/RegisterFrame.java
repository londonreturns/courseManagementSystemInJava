package utility;

import font.HeadingFont;
import font.SubHeadingFont;
import font.RegularFont;
import font.PlaceHolderFont;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import driver.*;

public class RegisterFrame extends StandardFrame implements ActionListener{
	
	JPanel registerPanel = new StandardPanel(275, 75, 450, 550);
	JLabel registerTitle = new JLabel();
	
	JLabel nameLabel = new JLabel();
	JLabel emailLabel = new JLabel();
	JLabel passwordLabel = new JLabel();
	JLabel confirmPasswordLabel = new JLabel();
	JLabel contactNumberLabel = new JLabel();
	JLabel dateOfBirth = new JLabel();
	
	JTextField nameTextField = new JTextField();
	JTextField emailTextField = new JTextField();
	JTextField contactNumberField = new JTextField();
	
	JPasswordField passwordPasswordField = new JPasswordField();
	JPasswordField confirmPasswordField = new JPasswordField();
	
	JLabel loginLabel = new JLabel();
	
	JButton okBtnRegister = new StandardButton();
	
	public boolean registerInit = false;
	
	private final int labelX = 0;
	private int labelY = 0;
	private final int fieldX = 0;
	private int fieldY = 0;
	
	public RegisterFrame(){
		super();
	}
	
	public RegisterFrame(int x_coord, int y_coord, int width, int height){
		super(x_coord, y_coord, width, height);
	}
	
	public RegisterFrame(int x_coord, int y_coord, int width, int height, Color color){
		super(x_coord, y_coord, width, height, color);
	}
	
	public void setLoginElements() {
		registerTitle.setText("Register");
		registerTitle.setBounds(185, 10, 175, 35);
		registerTitle.setFont(new HeadingFont());
		
		nameLabel.setText("Name");
		nameLabel.setBounds(10, 110, 75, 50);
		nameLabel.setFont(new SubHeadingFont());
		
		emailLabel.setText("Email");
		emailLabel.setBounds(10, 145, 75, 50);
		emailLabel.setFont(new SubHeadingFont());
		
		passwordLabel.setText("Password");
		passwordLabel.setBounds(10, 215, 120, 32);
		passwordLabel.setFont(new SubHeadingFont());
		
		confirmPasswordLabel.setText("Confirm Password");
		confirmPasswordLabel.setBounds(10, 250, 150, 32);
		confirmPasswordLabel.setFont(new SubHeadingFont());
		
		contactNumberLabel.setText("Contact Number");
		contactNumberLabel.setBounds(10, 285, 150, 32);
		contactNumberLabel.setFont(new SubHeadingFont());
		
		dateOfBirth.setText("Date of Birth");
		dateOfBirth.setBounds(10, 320, 150, 32);
		dateOfBirth.setFont(new SubHeadingFont());

		setPlaceHolder();
		
		nameTextField.setBounds(250, 115, 180, 32);
		nameTextField.setFont(new RegularFont());
		
		emailTextField.setBounds(250, 150, 180, 32);
		emailTextField.setFont(new RegularFont());
		
		passwordPasswordField.setBounds(250, 215, 180, 32);
		passwordPasswordField.setFont(new RegularFont());
		
		confirmPasswordField.setBounds(250, 250, 180, 32);
		confirmPasswordField.setFont(new RegularFont());
		
		contactNumberField.setBounds(250, 285, 180, 32);
		contactNumberField.setFont(new RegularFont());
		
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
//				registrationLabel.setFont(new Font("Dialog", Font.BOLD, 13));
				loginLabel.setForeground(new Color(0xF4D160));
			}

			@Override
			public void mouseExited(MouseEvent e) {
//				registrationLabel.setFont(new Font("Dialog", Font.BOLD, 12));
				loginLabel.setForeground(new Color(0x321D2F));
			}
		});
		
		registerPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		ArrayList<Component> allComponents = new ArrayList<>(Arrays.asList(
				registerTitle, nameLabel, emailLabel, passwordLabel,
				confirmPasswordLabel, contactNumberLabel, dateOfBirth,
				nameTextField, emailTextField, passwordPasswordField,
				confirmPasswordField, contactNumberField,
				
				okBtnRegister, loginLabel));	
		for(Component comp : allComponents) {
			registerPanel.add(comp);
		}
		
		add(registerPanel);
	}

	private void setPlaceHolder() {
		TextPrompt namePlaceHolder = new TextPrompt("Name", nameTextField);
		TextPrompt emailPlaceHolder = new TextPrompt("Email", emailTextField);
		TextPrompt passwordPlaceHolder = new TextPrompt("*********", passwordPasswordField);
		TextPrompt confirmPasswordPlaceHolder = new TextPrompt("********", confirmPasswordField);
		TextPrompt contactNumberPlaceHolder = new TextPrompt("0123456789", contactNumberField);
		
		ArrayList<Component> allComponents = new ArrayList<>(Arrays.asList(
				namePlaceHolder, emailPlaceHolder, passwordPlaceHolder,
				confirmPasswordPlaceHolder, contactNumberPlaceHolder));	
		
		for(Component placeHolderComponent : allComponents) {
			placeHolderComponent.setFont(new PlaceHolderFont());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == okBtnRegister) {
			String id = nameTextField.getText();
			String password = new String(passwordPasswordField.getPassword());
			System.out.println("Id: " + id);
			System.out.println("Password: " + password);
			
			String errors = checkUserInput(id, password);
			System.out.println(errors);
			
			if (!(errors.equals(""))) {
				JOptionPane.showMessageDialog(null, errors, "Error", JOptionPane.WARNING_MESSAGE);
			}else {
				System.out.println("Registered");
			}
		}	
	}
	
	String checkUserInput(String id, String password) {
		if (!Pattern.matches("^[0-9]{7}$", id)) {
			return "ID not valid.";
        }else if(!Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$", password)){
        	return"                                                                   "
        			+ "Password not valid.\n"
        			+ "Password should have at least one lowercase, one uppercase, one digit and be between 8 - 15 character";
        }
		return "";
	}
}
