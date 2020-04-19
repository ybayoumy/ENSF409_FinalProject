/**
 * @author Yassin Bayoumy & Thomas Kahessay
 */
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

public class ServerController {

	ServerSocket serverSocket;
	ExecutorService pool;
	ArrayList<Student> studentList;
	CourseCatalogue cat;
	SQLDBManager db;
	
	public ServerController(int portNum, ArrayList<Student> studentList, CourseCatalogue cat, SQLDBManager db) {
		try {
			serverSocket = new ServerSocket(portNum);
			pool = Executors.newCachedThreadPool();
			this.studentList = studentList;
			this.cat = cat;
			this.db = db;
			System.out.println("Server is Running...");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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
				
			}catch (NumberFormatException e) {
				
			}catch (IOException e) {
				e.printStackTrace();
				pool.shutdown();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
	}
	
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
			//db.loadRegistrations();
			ServerController server = new ServerController(9090, studentList, cat, db);
			server.communicate();
		} catch (SQLException e) {
			System.out.println("Database Error");
			e.printStackTrace();
		}
		
	}
}
