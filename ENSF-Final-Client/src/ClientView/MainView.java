package ClientView;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.*;
/**
 * The main class that handles the GUI components of the program.
 * @author Thomas Kahessay & Yassin Bayoumy
 * @version 1.0
 * @since April 3, 2020
 */
public class MainView extends JFrame{
	/**
	 * The insert button for the main frame.
	 */
	private JButton searchButton = new JButton("Search Courses");
	/**
	 * The find button for the main frame.
	 */
	private JButton registerButton = new JButton("Enroll in a Course");
	/**
	 * The browse button for the main frame.
	 */
	private JButton removeButton = new JButton("Drop a Course");
	/**
	 * The create button for the main frame.
	 */
	private JButton displayCatButton = new JButton("Display Course Catalougue");
	private JButton displayStudentButton = new JButton("Display Student Courses");
	/**
	 * The title of the main frame.
	 */
	private JLabel title = new JLabel("An Application to Maintain Student Records");
	/**
	 * The center text area for the main frame.
	 */
	private JTextArea textArea = new JTextArea(10, 10);
	/**
	 * Constructs the main frame.
	 */
	public MainView() {
		super("Main Window");
		
		setSize(750, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Closes the java application when the user exits
		
		textArea.setEditable(false);
		
		JPanel northPanel = new JPanel();
		JScrollPane centerPanel = new JScrollPane(textArea);
		JPanel southPanel = new JPanel();
		
		northPanel.add(title);
		
		southPanel.add(searchButton);
		southPanel.add(registerButton);
		southPanel.add(removeButton);
		southPanel.add(displayCatButton);
		southPanel.add(displayStudentButton);
		
		add("North", northPanel);
		add("Center", centerPanel);
		add("South", southPanel);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); 
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2); //Sets the location of the frame to the center of the screen
		
		pack();
	}
	/**
	 * Adds an action listener for the insert button.
	 * @param listener the action listener
	 */
	public void addSearchListener(ActionListener listener) {
		searchButton.addActionListener(listener);
	}
	/**
	 * Adds an action listener for the find button.
	 * @param listener the action listener
	 */
	public void addRegisterListener(ActionListener listener) {
		registerButton.addActionListener(listener);
	}
	/**
	 * Adds an action listener for the browse button.
	 * @param listener the action listener
	 */
	public void addRemoveListener(ActionListener listener) {
		removeButton.addActionListener(listener);
	}
	/**
	 * Adds an action listener for the create button.
	 * @param listener the action listener
	 */
	public void addDisplayCatListener(ActionListener listener) {
		displayCatButton.addActionListener(listener);
	}
	public void addDisplayStudentListener(ActionListener listener) {
		displayStudentButton.addActionListener(listener);
	}
	/**
	 * Displays a message to the user.
	 * @param message the message to be displayed
	 */
	public void displayMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}
	/**
	 * Displays an error message to the user.
	 * @param errorMessage the error to be displayed
	 */
	public void displayErrorMessage(String errorMessage) {
		JOptionPane.showMessageDialog(this, errorMessage);
	}
	/**
	 * Adds new text to the text area.
	 * @param content the text to be added to the area
	 */
	public void setTextArea(String content) {
		textArea.setText(content);
		textArea.setCaretPosition(0); // When displaying to the text area, starts the view at the top rather than bottom
	}
}
