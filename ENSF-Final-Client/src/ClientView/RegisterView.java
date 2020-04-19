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
 * The Register window that allows a student to enter the information for the 
 * course they want to register in.
 * @author Yassin Bayoumy & Thomas Kahessay
 */
public class RegisterView extends JFrame{
	/**
	 * The title of the register frame.
	 */
	JLabel title = new JLabel("Register In a Course");
	/**
	 * The text field for the user to enter the course id.
	 */
	JTextField courseIdField = new JTextField(5);
	/**
	 * The text field for the user to enter the courseName.
	 */
	JTextField courseNameField = new JTextField(4);
	/**
	 * The text field for the user to enter the course section.
	 */
	JTextField courseSectionField = new JTextField(2);
	/**
	 * The return/cancel button.
	 */
	JButton returnButton = new JButton("Cancel");
	/**
	 * The register button.
	 */
	JButton registerButton = new JButton("Register");
	/**
	 * Constructs the register frame.
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
		
		
		southPanel.add(registerButton);
		southPanel.add(returnButton);
		
		add("North", northPanel);
		add("Center", centerPanel);
		add("South", southPanel);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); 
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	}
	/**
	 * Adds a listener for the return button.
	 * @param listener the listener
	 */
	public void addReturnListener(ActionListener listener) {
		returnButton.addActionListener(listener);
	}
	/**
	 * Adds a listener for the register button.
	 * @param listener the listener
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
	/**
	 * Gets the Course Section.
	 * @return a string containing the course section
	 */
	public String getCourseSection() {
		return courseSectionField.getText();
	}
}
