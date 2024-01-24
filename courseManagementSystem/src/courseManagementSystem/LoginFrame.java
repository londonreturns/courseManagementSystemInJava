package courseManagementSystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class LoginFrame extends StandardFrame implements ActionListener{
	JPanel loginPanel = new StandardPanel(250, 75, 1000, 1005, Color.red);
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
		okBtn.setBounds(75, 250, 200, 100);
		okBtn.addActionListener(this);
		
		registrationLabel.setText("No account?");
		registrationLabel.setForeground(new Color(0x800080));
		registrationLabel.setBounds(75, 400, 85, 25);
		registrationLabel.setBackground(Color.white);
		registrationLabel.setOpaque(true);
		registrationLabel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("Registered");
			}

			@Override
			public void mouseClicked(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {
				registrationLabel.setFont(new Font("Dialog", Font.BOLD, 13));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				registrationLabel.setFont(new Font("Dialog", Font.BOLD, 12));
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
			System.out.println("Id: " + idTextField.getText());
			System.out.println("Password: " + new String(passwordPasswordField.getPassword()));
		}
		
		
	}
}
