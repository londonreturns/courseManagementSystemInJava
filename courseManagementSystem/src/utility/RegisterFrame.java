package utility;

import courseManagementSystem.*;
import font.Font1;
import font.Font2;
import font.Font3;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class RegisterFrame extends StandardFrame implements ActionListener{
	JPanel registerPanel = new StandardPanel(250, 75, 450, 450);
	JLabel registerTitle = new JLabel();
	JTextField idTextField = new JTextField();
	JPasswordField passwordPasswordField = new JPasswordField();
	JLabel idLabel = new JLabel();
	JLabel passwordLabel = new JLabel();
	JButton okBtnRegister = new StandardButton();
	JLabel loginLabel = new JLabel();
	
	public boolean registerInit = false;
	
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
		registerTitle.setFont(new Font1());
		
		idLabel.setText("Id");
		idLabel.setBounds(10, 110, 75, 50);
		idLabel.setFont(new Font2());
		
		passwordLabel.setText("Password");
		passwordLabel.setBounds(10, 215, 120, 32);
		passwordLabel.setFont(new Font2());

		idTextField.setBounds(250, 115, 180, 32);
		idTextField.setFont(new Font3());
		
		passwordPasswordField.setBounds(250, 215, 180, 32);
		passwordPasswordField.setFont(new Font3());
		
		okBtnRegister.setText("Login");
		okBtnRegister.setBounds(150, 350, 150, 35);
		okBtnRegister.addActionListener(this);
		
		loginLabel.setText("No account?");
		loginLabel.setForeground(new Color(0x321D2F));
		loginLabel.setBounds(190, 300, 85, 25);
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
		
		registerPanel.add(registerTitle);
		registerPanel.add(idLabel);
		registerPanel.add(passwordLabel);
		registerPanel.add(idTextField);
		registerPanel.add(passwordPasswordField);		
		registerPanel.add(okBtnRegister);
		registerPanel.add(loginLabel);		
		
		add(registerPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == okBtnRegister) {
			String id = idTextField.getText();
			String password = new String(passwordPasswordField.getPassword());
			System.out.println("Id: " + id);
			System.out.println("Password: " + password);
			
			String errors = checkUserInput(id, password);
			System.out.println(errors);
			
			if (!(errors.equals(""))) {
				JOptionPane.showMessageDialog(null, errors, "Error", JOptionPane.WARNING_MESSAGE);
			}else {
				System.out.println("Logged in");
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
