package utility;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import courseManagementSystem.Main;
import font.Font1;
import font.Font2;
import font.Font3;

import java.util.regex.Pattern;

public class LoginFrame extends StandardFrame implements ActionListener{
	
	public JPanel loginPanel = new StandardPanel(250, 75, 450, 450);
	public JLabel loginTitle = new JLabel();
	public JTextField idTextField = new JTextField();
	public JPasswordField passwordPasswordField = new JPasswordField();
	public JLabel idLabel = new JLabel();
	public JLabel passwordLabel = new JLabel();
	public JButton okBtnLogin = new StandardButton();
	public JLabel registerLabel = new JLabel();
	
	public boolean loginInit = false;
	
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
		loginTitle.setFont(new Font1());
		
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
		
		loginPanel.add(loginTitle);
		loginPanel.add(idLabel);
		loginPanel.add(passwordLabel);
		loginPanel.add(idTextField);
		loginPanel.add(passwordPasswordField);		
		loginPanel.add(okBtnLogin);
		loginPanel.add(registerLabel);
		
		add(loginPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == okBtnLogin) {
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
