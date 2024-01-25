package courseManagementSystem;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.util.regex.Pattern;

public class LoginFrame extends StandardFrame implements ActionListener{
	
	JPanel loginPanel = new StandardPanel(250, 75, 450, 450, Color.red);
	JTextField idTextField = new JTextField();
	JPasswordField passwordPasswordField = new JPasswordField();
	JLabel idLabel = new JLabel();
	JLabel passwordLabel = new JLabel();
	JButton okBtn = new StandardButton();
	JLabel registrationLabel = new JLabel();
	
	LoginFrame(){
		super();
	}
	
	LoginFrame(int x_coord, int y_coord, int width, int height){
		super(x_coord, y_coord, width, height);
	}
	
	LoginFrame(int x_coord, int y_coord, int width, int height, Color color){
		super(x_coord, y_coord, width, height, color);
	}
	
	protected void setElements() {
		idLabel.setText("Id");
		idLabel.setBounds(10, 10, 200, 50);
		idLabel.setBackground(Color.green);
		idLabel.setOpaque(true);
		
		passwordLabel.setText("Password");
		passwordLabel.setBounds(10, 100, 200, 50);
		passwordLabel.setBackground(Color.blue);
		passwordLabel.setOpaque(true);

		idTextField.setBounds(250, 15, 200, 32);
		
		passwordPasswordField.setBounds(250, 115, 200, 32);
		
		okBtn.setText("Login");
		okBtn.setBounds(75, 250, 150, 35);
		okBtn.addActionListener(this);
		
		registrationLabel.setText("No account?");
		registrationLabel.setForeground(new Color(0x0006B1));
		registrationLabel.setBounds(75, 400, 85, 25);
		registrationLabel.setBackground(Color.white);
		registrationLabel.setOpaque(true);
		registrationLabel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("Registered");
				RegisterFrame registerFrame = new RegisterFrame(0, 0, 1000, 750, new Color(0xF0F0F0));
				registerFrame.setVisible(true);
				setVisible(false);
			}

			@Override
			public void mouseClicked(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {
//				registrationLabel.setFont(new Font("Dialog", Font.BOLD, 13));
				registrationLabel.setForeground(new Color(0x0006B1));
			}

			@Override
			public void mouseExited(MouseEvent e) {
//				registrationLabel.setFont(new Font("Dialog", Font.BOLD, 12));
				registrationLabel.setForeground(new Color(0x800080));
			}
		});
		
		loginPanel.add(idLabel);
		loginPanel.add(passwordLabel);
		loginPanel.add(idTextField);
		loginPanel.add(passwordPasswordField);		
		loginPanel.add(okBtn);
		loginPanel.add(registrationLabel);
		
		add(loginPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == okBtn) {
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
		StringBuilder errors = new StringBuilder();
		
		if (!Pattern.matches("^[0-9]{7}$", id)) {
			errors.append("ID not valid.");
			return errors.toString();
        }else if(!Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$", password)){
        	errors.append("                                                                   "
        			+ "Password not valid.\n"
        			+ "Password should have at least one lowercase, one uppercase, one digit and be between 8 - 15 character");
			return errors.toString();
        }
		
		return errors.toString();
	}
}
