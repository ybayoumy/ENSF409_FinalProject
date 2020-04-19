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
 * The Remove window that allows a student to enter the information for the 
 * course they want to drop.
 * @author Yassin Bayoumy & Thomas Kahessay
 */
public class RemoveView extends JFrame{
	/**
	 * The title of the remove frame.
	 */
	JLabel title = new JLabel("Remove a Course");
	/**
	 * The text field for the user to enter the course id.
	 */
	JTextField courseIdField = new JTextField(5);
	/**
	 * The text field for the user to enter the course name.
	 */
	JTextField courseNameField = new JTextField(4);
	/**
	 * The return button.
	 */
	JButton returnButton = new JButton("Cancel");
	/**
	 * The remove button.
	 */
	JButton removeButton = new JButton("Remove");
	/**
	 * Constructs the remove frame.
	 */
	public RemoveView(){
		super("Remove a Course");
		
		setSize(500, 175);
		
		JPanel northPanel = new JPanel();
		JPanel centerPanel = new JPanel();
		JPanel southPanel = new JPanel();
		
		northPanel.add(title);
		
		centerPanel.add(new JLabel("Course Name"));
		centerPanel.add(courseNameField);
		
		centerPanel.add(new JLabel("Course Id"));
		centerPanel.add(courseIdField);
		
		southPanel.add(removeButton);
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
	 * Adds a listener for the remove button.
	 * @param listener the listener
	 */
	public void addRemoveListener(ActionListener listener) {
		removeButton.addActionListener(listener);
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
}
