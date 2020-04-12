package ClientView;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RegisterView extends JFrame{
	/**
	 * The title of the insert frame.
	 */
	JLabel title = new JLabel("Register In a Course");
	/**
	 * The text field for the user to enter the id.
	 */
	JTextField courseIdField = new JTextField(5);
	/**
	 * The text field for the user to enter the courseName.
	 */
	JTextField courseNameField = new JTextField(4);
	/**
	 * The text field for the user to enter the major.
	 */
	JTextField courseSectionField = new JTextField(2);
	/**
	 * The return button.
	 */
	JButton returnButton = new JButton("Cancel");
	/**
	 * The insert button.
	 */
	JButton registerButton = new JButton("Register");
	/**
	 * Constructs the insert frame.
	 */
	public RegisterView(){
		super("Register In a Course");
		
		setSize(500, 175);
		
		JPanel northPanel = new JPanel();
		JPanel centerPanel = new JPanel();
		JPanel southPanel = new JPanel();
		
		northPanel.add(title);
		
		centerPanel.add(new JLabel("Course Name"));
		centerPanel.add(courseNameField);
		
		centerPanel.add(new JLabel("Course Id"));
		centerPanel.add(courseIdField);
		
		centerPanel.add(new JLabel("Course Section"));
		centerPanel.add(courseSectionField);
		
		//centerPanel.add(Box.createHorizontalStrut(150));
		
		southPanel.add(registerButton);
		southPanel.add(returnButton);
		
		add("North", northPanel);
		add("Center", centerPanel);
		add("South", southPanel);
	}
	/**
	 * Adds a listener for the return button.
	 * @param listener the listener
	 */
	public void addReturnListener(ActionListener listener) {
		returnButton.addActionListener(listener);
	}
	/**
	 * Adds a listener for the search button.
	 * @param listener
	 */
	public void addRegisterListener(ActionListener listener) {
		registerButton.addActionListener(listener);
	}
	/**
	 * Gets the Course Id.
	 * @return a string containing the Course Id
	 */
	public String getCourseId() {
		return courseIdField.getText();
	}
	/**
	 * Gets the Course Name.
	 * @return a string containing the Course Name
	 */
	public String getCourseName() {
		return courseNameField.getText();
	}
	
	public String getCourseSection() {
		return courseSectionField.getText();
	}
}
