package ClientView;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginView extends JFrame{
	/**
	 * The title of the insert frame.
	 */
	JLabel title = new JLabel("Student Login");
	/**
	 * The text field for the user to enter the id.
	 */
	JTextField idField = new JTextField(5);
	/**
	 * The text field for the user to enter the major.
	 */
	JTextField passwordField = new JTextField(4);
	/**
	 * The insert button.
	 */
	JButton loginButton = new JButton("Login");
	/**
	 * Constructs the insert frame.
	 */
	public LoginView(){
		super("Login");
		
		setSize(500, 175);
		
		JPanel northPanel = new JPanel();
		JPanel centerPanel = new JPanel();
		JPanel southPanel = new JPanel();
		
		northPanel.add(title);
		
		centerPanel.add(new JLabel("ID"));
		centerPanel.add(idField);
		
		centerPanel.add(new JLabel("Password"));
		centerPanel.add(passwordField);
		
		//centerPanel.add(Box.createHorizontalStrut(150));
		
		southPanel.add(loginButton);
		
		add("North", northPanel);
		add("Center", centerPanel);
		add("South", southPanel);
	}
	
	/**
	 * Adds a listener for the search button.
	 * @param listener
	 */
	public void addLoginListener(ActionListener listener) {
		loginButton.addActionListener(listener);
	}
	/**
	 * Gets the Course Id.
	 * @return a string containing the Course Id
	 */
	public String getId() {
		return idField.getText();
	}
	/**
	 * Gets the Course Name.
	 * @return a string containing the Course Name
	 */
	public String getPassword() {
		return passwordField.getText();
	}
}
