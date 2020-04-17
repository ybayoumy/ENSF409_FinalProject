/**
 * @author Yassin Bayoumy & Thomas Kahessay
 */
package ClientController;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

import ClientView.MainView;
import ClientView.RegisterView;
import ClientView.RemoveView;
import ClientView.SearchView;

public class Controller {
	private MainView view;
	private Socket clientSocket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;

	public Controller(MainView view, String serverName, int portNum) throws IOException {
		this.view = view;
		clientSocket = new Socket(serverName, portNum);
		socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		socketOut = new PrintWriter((clientSocket.getOutputStream()), true);
	}

	public void register() {
		RegisterView registerView = new RegisterView();
		registerView.setVisible(true);
		
		registerView.addRegisterListener((ActionEvent e) ->{
			try {
				registerInCourse(registerView);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		registerView.addReturnListener((ActionEvent e) ->{
			registerView.dispose();
		});
	}

	public void search() {
		SearchView searchView = new SearchView();
		searchView.setVisible(true);
		
		searchView.addSearchListener((ActionEvent e) ->{
			try {
				searchCourse(searchView);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		searchView.addReturnListener((ActionEvent e) ->{
			searchView.dispose();
		});
	}
	
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

	public void remove() {
		RemoveView removeView = new RemoveView();
		removeView.setVisible(true);
		
		removeView.addRemoveListener((ActionEvent e) ->{
			try {
				removeCourse(removeView);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		removeView.addReturnListener((ActionEvent e) ->{
			removeView.dispose();
		});
	}

	public void displayCat() {
		socketOut.println("4");
		String s = readFromServer();
		view.setTextArea(s);
	}
	public void displayStudent() {
		socketOut.println("5");
		String s = readFromServer();
		view.setTextArea(s);
	}
	
	private String readFromServer() {
		String s = "";
		while(true) {
			try {
				s += socketIn.readLine() + "\n";
				if(s.contains("\0")) {
					s = s.replace("\0", "\n");
					//System.out.println(s);
					return s;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(s);
		}
	}
	
	public void displayMessage(String s) {
		view.displayMessage(s);
	}
	
	public void displayErrorMessage(String s) {
		view.displayErrorMessage(s);
	}
	
	public void communicate() {
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
		
		socketOut.println(JOptionPane.showInputDialog("Please enter your id: "));
		
		try {
			JOptionPane.showMessageDialog(view, socketIn.readLine());
		}catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		MainView view = new MainView();
		try {
			Controller control = new Controller(view, "localhost", 9090);
			control.communicate();
			view.setVisible(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
