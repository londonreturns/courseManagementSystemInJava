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

import javax.swing.AbstractButton;
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

import driver.*;

public class RegisterFrame extends StandardFrame implements ActionListener{
	
	JPanel registerPanel = new StandardPanel(275, 75, 450, 550);
	JLabel registerTitle = new JLabel();
	
	JLabel nameLabel = new JLabel();
	JLabel emailLabel = new JLabel();
	JLabel passwordLabel = new JLabel();
	JLabel confirmPasswordLabel = new JLabel();
	JLabel contactNumberLabel = new JLabel();
	JLabel dateOfBirthLabel = new JLabel();
	JLabel typeOfUserLabel = new JLabel();
	
	JTextField nameTextField = new JTextField();
	JTextField emailTextField = new JTextField();
	JTextField contactNumberField = new JTextField();
	
	JDateChooser dateChooser = new JDateChooser();
	
	String[] typeOfUsers = {"Student", "Teacher", "Admin"};
	
	JComboBox<String> typeOfUserCombo = new JComboBox(typeOfUsers);
	
	JPasswordField passwordPasswordField = new JPasswordField();
	JPasswordField confirmPasswordField = new JPasswordField();
	
	JLabel loginLabel = new JLabel();
	
	JButton okBtnRegister = new StandardButton();
	
	public boolean registerInit = false;
	
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
	
	public void setLoginElements() {
		registerTitle.setText("Register");
		registerTitle.setBounds(185, 10, 175, 35);
		registerTitle.setFont(new HeadingFont());
		
		nameLabel.setText("Name: ");
		nameLabel.setBounds(axisX, axisY, width, height);
		nameLabel.setFont(new SubHeadingFont());
		
		axisY = incrementPosition(axisY);
		
		emailLabel.setText("Email: ");
		emailLabel.setBounds(axisX, axisY, width, height);
		emailLabel.setFont(new SubHeadingFont());
		
		axisY = incrementPosition(axisY);
		
		passwordLabel.setText("Password: ");
		passwordLabel.setBounds(axisX, axisY, width, height);
		passwordLabel.setFont(new SubHeadingFont());
		
		axisY = incrementPosition(axisY);
		
		confirmPasswordLabel.setText("Confirm Password: ");
		confirmPasswordLabel.setBounds(axisX, axisY, width, height);
		confirmPasswordLabel.setFont(new SubHeadingFont());

		axisY = incrementPosition(axisY);
		
		contactNumberLabel.setText("Contact Number: ");
		contactNumberLabel.setBounds(axisX, axisY, width, height);
		contactNumberLabel.setFont(new SubHeadingFont());
		
		axisY = incrementPosition(axisY);
		
		dateOfBirthLabel.setText("Date of Birth: ");
		dateOfBirthLabel.setBounds(axisX, axisY, width, height);
		dateOfBirthLabel.setFont(new SubHeadingFont());
		
		axisY = incrementPosition(axisY);

		typeOfUserLabel.setText("User Type: ");
		typeOfUserLabel.setBounds(axisX, axisY, width, height);
		typeOfUserLabel.setFont(new SubHeadingFont());
		
		setPlaceHolders();
		
		ArrayList<Component> allLabelComponents = new ArrayList<>(Arrays.asList(
				nameLabel, emailLabel, passwordLabel, confirmPasswordLabel,
				contactNumberLabel, dateOfBirthLabel, typeOfUserLabel));
		for(Component labelComponent : allLabelComponents) {
			((JLabel) labelComponent).setHorizontalAlignment(SwingConstants.RIGHT);
		}

		axisX = 250;
		axisY = 115;
		
		nameTextField.setBounds(axisX, axisY, width, height);
		nameTextField.setFont(new RegularFont());
		
		axisY = incrementPosition(axisY);
		
		emailTextField.setBounds(axisX, axisY, width, height);
		emailTextField.setFont(new RegularFont());
		
		axisY = incrementPosition(axisY);
		
		passwordPasswordField.setBounds(axisX, axisY, width, height);
		passwordPasswordField.setFont(new RegularFont());
		
		axisY = incrementPosition(axisY);
		
		confirmPasswordField.setBounds(axisX, axisY, width, height);
		confirmPasswordField.setFont(new RegularFont());
		
		axisY = incrementPosition(axisY);
		
		contactNumberField.setBounds(axisX, axisY, width, height);
		contactNumberField.setFont(new RegularFont());
		
		axisY = incrementPosition(axisY);
		
		dateChooser.setBounds(axisX, axisY, width, height);
		dateChooser.setFont(new RegularFont());
		
		axisY = incrementPosition(axisY);
		
		typeOfUserCombo.setBounds(axisX, axisY, width, height);
		typeOfUserCombo.setFont(new RegularFont());
		
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
				confirmPasswordLabel, contactNumberLabel, dateOfBirthLabel, typeOfUserLabel,
				nameTextField, emailTextField, passwordPasswordField,
				confirmPasswordField, contactNumberField,
				dateChooser, okBtnRegister, loginLabel, typeOfUserCombo));	
		for(Component comp : allComponents) {
			registerPanel.add(comp);
		}
		
		add(registerPanel);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == okBtnRegister) {
			
		}	
	}
	
	
	
	private static int incrementPosition(int x) {
		return x += 35;
	}
}
