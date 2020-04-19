package ClientView;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 * The login window for the user to enter their credentials in order to 
 * login to the application.
 * @author Yassin Bayoumy & Thomas Kahessay
 */
public class LoginView extends JFrame{
	/**
	 * The title of the login frame.
	 */
	JLabel title = new JLabel("Student Login");
	/**
	 * The text field for the user to enter the id.
	 */
	JTextField idField = new JTextField(5);
	/**
	 * The text field for the user to enter the password.
	 */
	JTextField passwordField = new JTextField(4);
	/**
	 * The login button.
	 */
	JButton loginButton = new JButton("Login");
	/**
	 * Constructs the login frame.
	 */
	public LoginView(){
		super("Login");
		
		setSize(500, 175);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel northPanel = new JPanel();
		JPanel centerPanel = new JPanel();
		JPanel southPanel = new JPanel();
		
		northPanel.add(title);
		
		centerPanel.add(new JLabel("ID"));
		centerPanel.add(idField);
		
		centerPanel.add(new JLabel("Password"));
		centerPanel.add(passwordField);
		
		southPanel.add(loginButton);
		
		add("North", northPanel);
		add("Center", centerPanel);
		add("South", southPanel);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); 
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	}
	
	/**
	 * Adds a listener for the login button.
	 * @param listener the listener for the login button
	 */
	public void addLoginListener(ActionListener listener) {
		loginButton.addActionListener(listener);
	}
	/**
	 * Gets the student Id.
	 * @return a string containing the student id.
	 */
	public String getId() {
		return idField.getText();
	}
	/**
	 * Gets the password.
	 * @return a string containing the student password.
	 */
	public String getPassword() {
		return passwordField.getText();
	}
}
