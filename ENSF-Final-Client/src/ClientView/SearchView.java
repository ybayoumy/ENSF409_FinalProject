package ClientView;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 * The main class that handles the operations of the search frame.
 * @author Thomas Kahessay & Yassin Bayoumy
 * @version 1.0
 * @since April 3, 2020
 */
public class SearchView extends JFrame{
	/**
	 * The title of the search frame.
	 */
	JLabel title = new JLabel("Search for a Course");
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
	 * The search button.
	 */
	JButton searchButton = new JButton("Search");
	/**
	 * Constructs the search frame.
	 */
	public SearchView(){
		super("Search For Course");
		
		setSize(500, 175);
		
		JPanel northPanel = new JPanel();
		JPanel centerPanel = new JPanel();
		JPanel southPanel = new JPanel();
		
		northPanel.add(title);
		
		centerPanel.add(new JLabel("Course Name"));
		centerPanel.add(courseNameField);
		
		centerPanel.add(new JLabel("Course Id"));
		centerPanel.add(courseIdField);

		southPanel.add(searchButton);
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
	 * Adds a listener for the search button.
	 * @param listener the listener
	 */
	public void addSearchListener(ActionListener listener) {
		searchButton.addActionListener(listener);
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
