package ClientController;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JOptionPane;

import ClientView.LoginView;
import ClientView.MainView;
import ClientView.RegisterView;
import ClientView.RemoveView;
import ClientView.SearchView;
/**
 * The main controller for the client where the program is run and starts to communicate 
 * with the server controller. The main function is located here.
 * @author Yassin Bayoumy & Thomas Kahessay
 */
public class Controller {
	/**
	 * The main view of the program showing all user options
	 */
	private MainView view;
	/**
	 * The view prompting the user to login.
	 */
	private LoginView loginView;
	/**
	 * The socket used to connect the client to the server.
	 */
	private Socket clientSocket;
	/**
	 * Used to send messages to the server from the client.
	 */
	private PrintWriter socketOut;
	/**
	 * Used to read messages from the server.
	 */
	private BufferedReader socketIn;
	/**
	 * Constructs a new controller.
	 * @param view the main view 
	 * @param serverName the name of the server
	 * @param portNum the port number
	 * @throws IOException
	 */
	public Controller(MainView view, String serverName, int portNum) throws IOException {
		this.view = view;
		clientSocket = new Socket(serverName, portNum);
		socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		socketOut = new PrintWriter((clientSocket.getOutputStream()), true);
	}
	/**
	 * Sets up the register view for the user.
	 */
	public void register() {
		RegisterView registerView = new RegisterView();
		registerView.setVisible(true);
		
		registerView.addRegisterListener((ActionEvent e) ->{
			try {
				registerInCourse(registerView);
			} catch (IOException e1) {
				System.out.println("Error in register view.");
				e1.printStackTrace();
			}
		});
		
		registerView.addReturnListener((ActionEvent e) ->{
			registerView.dispose();
		});
	}
	/**
	 * Sets up the search view for the user.
	 */
	public void search() {
		SearchView searchView = new SearchView();
		searchView.setVisible(true);
		
		searchView.addSearchListener((ActionEvent e) ->{
			try {
				searchCourse(searchView);
			} catch (IOException e1) {
				System.out.println("Error in search view.");
				e1.printStackTrace();
			}
		});
		
		searchView.addReturnListener((ActionEvent e) ->{
			searchView.dispose();
		});
	}
	/**
	 * Registers a student into the course they choose.
	 * @param view the register view 
	 * @throws IOException
	 */
	private void registerInCourse(RegisterView view) throws IOException {
    	String courseId = view.getCourseId();
		String courseName = view.getCourseName();
		String courseSection = view.getCourseSection();
		if(courseId.isEmpty() || courseName.isEmpty() || courseSection.isEmpty()) {
			displayErrorMessage("Make sure all fields are full");
		}
		else {
			socketOut.println("2," + courseName + "," + courseId + "," + courseSection);
			displayMessage(readFromServer());
			view.dispose();
		}
    }
	/**
	 * Searches for a course in the catalogue.
	 * @param view the search view
	 * @throws IOException
	 */
    private void searchCourse(SearchView view) throws IOException {
    	String courseId = view.getCourseId();
		String courseName = view.getCourseName();
		if(courseId.isEmpty() || courseName.isEmpty()) {
			displayErrorMessage("Make sure all fields are full");
		}
		else {
			socketOut.println("1," + courseName + "," + courseId);
			displayMessage(readFromServer());
			view.dispose();
		}
    }
    /**
     * Removes a course from the students courses.
     * @param view the remove view 
     * @throws IOException
     */
    private void removeCourse(RemoveView view) throws IOException {
    	String courseId = view.getCourseId();
		String courseName = view.getCourseName();
		if(courseId.isEmpty() || courseName.isEmpty()) {
			displayErrorMessage("Make sure all fields are full");
		}
		else {
			socketOut.println("3," + courseName + "," + courseId);
			displayMessage(readFromServer());
			view.dispose();
		}
    }
    /**
     * Sets up the remove view for the user. 
     */
	public void remove() {
		RemoveView removeView = new RemoveView();
		removeView.setVisible(true);
		
		removeView.addRemoveListener((ActionEvent e) ->{
			try {
				removeCourse(removeView);
			} catch (IOException e1) {
				System.out.println("Error in remove view.");
				e1.printStackTrace();
			}
		});
		
		removeView.addReturnListener((ActionEvent e) ->{
			removeView.dispose();
		});
	}
	/**
	 * Displays the catalogue onto the main view.
	 */
	public void displayCat() {
		socketOut.println("4");
		String s = readFromServer();
		view.setTextArea(s);
	}
	/**
	 * Displays the list of student courses onto the main view.
	 */
	public void displayStudent() {
		socketOut.println("5");
		String s = readFromServer();
		view.setTextArea(s);
	}
	/**
	 * Reads output from the server to use for the client.
	 * @return a string containing the output from server.
	 */
	private String readFromServer() {
		String s = "";
		while(true) {
			try {
				s += socketIn.readLine() + "\n";
				if(s.contains("\0")) {
					s = s.replace("\0", "\n");
					return s;
				}
			} catch (IOException e) {
				System.out.println("Error in reading from server.");
				e.printStackTrace();
			}
			System.out.println(s);
		}
	}
	/**
	 * Displays a message from the main view.
	 * @param s the message
	 */
	public void displayMessage(String s) {
		view.displayMessage(s);
	}
	/**
	 * Displays an error message from the main view.
	 * @param s the message
	 */
	public void displayErrorMessage(String s) {
		view.displayErrorMessage(s);
	}
	/**
	 * Prompts the user to login to the application.
	 */
	private void login() {
		String id = loginView.getId();
		String pass = loginView.getPassword();
		if(id.isEmpty() || pass.isEmpty()) {
			displayErrorMessage("Make sure all fields are full");
		} else {
			socketOut.println(id + "," + pass);
			String s = readFromServer();
			displayMessage(s);
			if(!s.contains("Wrong")) { // Checks for correct password from the server.
				view.setVisible(true);
			} else {
				System.exit(1);
			}
			loginView.dispose();
		}
	}
	/**
	 * Communicates between the client and the server using listeners to check for buttons presses.
	 */
	public void communicate() {
		loginView = new LoginView();
		loginView.setVisible(true);
		
		loginView.addLoginListener((ActionEvent e) ->{
			login();
		});
		
		view.addRegisterListener((ActionEvent e) ->{
			register();
		});
		
		view.addSearchListener((ActionEvent e) ->{
			search();
		});
		view.addRemoveListener((ActionEvent e) ->{
			remove();
		});
		view.addDisplayCatListener((ActionEvent e) ->{
			displayCat();
		});
		view.addDisplayStudentListener((ActionEvent e) ->{
			displayStudent();
		});
	}
	
	public static void main(String[] args) {
		MainView view = new MainView();
		try {
			Controller control = new Controller(view, "localhost", 9090);
			control.communicate();
			
		} catch (IOException e) {
			System.out.println("Error in main client controller");
			e.printStackTrace();
		}
	}
}
