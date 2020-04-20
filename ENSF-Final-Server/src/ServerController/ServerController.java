package ServerController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ServerModel.Application;
import ServerModel.CourseCatalogue;
import ServerModel.SQLDBManager;
import ServerModel.Student;
/**
 * The server controller which connects to the client controller to pass information 
 * between the server and client.
 * @author Yassin Bayoumy & Thomas Kahessay
 */
public class ServerController {
	/**
	 * The server socket which connects to the client.
	 */
	ServerSocket serverSocket;
	/**
	 * The thread pool allowing for multiple users on the application.
	 */
	ExecutorService pool;
	/**
	 * The list of all the students. 
	 */
	ArrayList<Student> studentList;
	/**
	 * The course catalogue.
	 */
	CourseCatalogue cat;
	/**
	 * The database containing all the students, courses and registrations.
	 */
	SQLDBManager db;
	/**
	 * Constructs a new server controller.
	 * @param portNum the port number
	 * @param studentList the list of all students 
	 * @param cat the course catalogue
	 * @param db the database
	 */
	public ServerController(int portNum, ArrayList<Student> studentList, CourseCatalogue cat, SQLDBManager db) {
		try {
			serverSocket = new ServerSocket(portNum);
			pool = Executors.newCachedThreadPool();
			this.studentList = studentList;
			this.cat = cat;
			this.db = db;
			System.out.println("Server is Running...");
		} catch (IOException e) {
			System.out.println("Error in creating server controller.");
			e.printStackTrace();
		}
	}
	/**
	 * Communicates to the client by passing information through to it using sockets.
	 */
	public void communicate() {
		while(true) {
			try {
				Socket socket = serverSocket.accept();
				BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
				
				String request = socketIn.readLine();
				String[] args = request.split(",");
				int id = Integer.parseInt(args[0]);
				String pass = args[1];
				Student theStudent = searchStudents(id);
				Application app = null;
				if(db.verifyLogin(id, pass) && theStudent != null) {
					 socketOut.println("Succefully Logged In\0");
					 app = new Application(theStudent, cat, socketOut, socketIn, db);
					 pool.execute(app);
				} else {
					socketOut.println("Wrong Id or Password\0");
				}
			} catch (NullPointerException e) {
				System.out.println("Null Pointer Error in server communicate.");
			}catch (NumberFormatException e) {
				System.out.println("Number Format Exception in server communicate.");
			}catch (IOException e) {
				System.out.println("IOException in server communicate.");
				e.printStackTrace();
				pool.shutdown();
			} catch (SQLException e) {
				System.out.println("SQL Exception in server communicate.");
				e.printStackTrace();
			} 
		}
	}
	/**
	 * Searches the student list by id.
	 * @param id the id of the student
	 * @return the student if found otherwise null
	 */
	private Student searchStudents(int id) {
		for(Student s : studentList) {
			if(s.getStudentId() == id) {
				return s;
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		CourseCatalogue cat;
		ArrayList<Student> studentList;
		try {
			SQLDBManager db = new SQLDBManager();
			cat = new CourseCatalogue(db.loadCatalogueFromDB());
			studentList = db.loadStudentsFromDB();
			db.loadRegistrations();
			ServerController server = new ServerController(9090, studentList, cat, db);
			server.communicate();
		} catch (SQLException e) {
			System.out.println("Database Error");
			e.printStackTrace();
		}
		
	}
}
