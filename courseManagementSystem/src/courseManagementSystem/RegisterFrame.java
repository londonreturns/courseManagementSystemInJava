package courseManagementSystem;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class RegisterFrame extends StandardFrame implements ActionListener{
	JPanel loginPanel = new StandardPanel(250, 75, 450, 450, Color.red);
	JTextField idTextField = new JTextField();
	JPasswordField passwordPasswordField = new JPasswordField();
	JLabel idLabel = new JLabel();
	JLabel passwordLabel = new JLabel();
	JButton okBtnLogin = new StandardButton();
	JLabel registrationLabel = new JLabel();
	
	boolean loginInit = false;
	
	RegisterFrame(){
		super();
	}
	
	RegisterFrame(int x_coord, int y_coord, int width, int height){
		super(x_coord, y_coord, width, height);
	}
	
	RegisterFrame(int x_coord, int y_coord, int width, int height, Color color){
		super(x_coord, y_coord, width, height, color);
	}
	
	protected void setLoginElements() {
		idLabel.setText("Id");
		idLabel.setBounds(10, 10, 75, 50);
		idLabel.setBackground(Color.green);
		idLabel.setOpaque(true);
		
		passwordLabel.setText("Password");
		passwordLabel.setBounds(10, 115, 75, 32);
		passwordLabel.setBackground(Color.blue);
		passwordLabel.setOpaque(true);

		idTextField.setBounds(250, 15, 200, 32);
		
		passwordPasswordField.setBounds(250, 115, 200, 32);
		
		okBtnLogin.setText("Register");
		okBtnLogin.setBounds(75, 250, 150, 35);
		okBtnLogin.addActionListener(this);
		
		registrationLabel.setText("No account?");
		registrationLabel.setForeground(new Color(0x321D2F));
		registrationLabel.setBounds(75, 400, 85, 25);
		registrationLabel.setBackground(Color.white);
		registrationLabel.setOpaque(true);
		registrationLabel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				Main.loginFrameDiplay();
			}

			@Override
			public void mouseClicked(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {
//				registrationLabel.setFont(new Font("Dialog", Font.BOLD, 13));
				registrationLabel.setForeground(new Color(0xF4D160));
			}

			@Override
			public void mouseExited(MouseEvent e) {
//				registrationLabel.setFont(new Font("Dialog", Font.BOLD, 12));
				registrationLabel.setForeground(new Color(0x321D2F));
			}
		});
		
		loginPanel.add(idLabel);
		loginPanel.add(passwordLabel);
		loginPanel.add(idTextField);
		loginPanel.add(passwordPasswordField);		
		loginPanel.add(okBtnLogin);
		loginPanel.add(registrationLabel);
		
		add(loginPanel);
		
		loginPanel.add(idLabel);
		loginPanel.add(passwordLabel);
		loginPanel.add(idTextField);
		loginPanel.add(passwordPasswordField);		
		loginPanel.add(okBtnLogin);
		loginPanel.add(registrationLabel);
		
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
