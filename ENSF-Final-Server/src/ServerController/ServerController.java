package ServerController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ServerModel.Application;
import ServerModel.CourseCatalogue;
import ServerModel.DBManager;
import ServerModel.Student;

public class ServerController {

	ServerSocket serverSocket;
	ExecutorService pool;
	ArrayList<Student> studentList;
	CourseCatalogue cat;
	
	
	public ServerController(int portNum, ArrayList<Student> studentList, CourseCatalogue cat) {
		try {
			serverSocket = new ServerSocket(portNum);
			pool = Executors.newCachedThreadPool();
			this.studentList = studentList;
			this.cat = cat;
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
				int id = Integer.parseInt(socketIn.readLine());
				Student theStudent = searchStudents(id);
				Application app = null;
				if(theStudent != null) {
					socketOut.println("Student found with id: " + id);
					 app = new Application(theStudent, cat, socketOut, socketIn);
				} else {
					System.exit(1);
				}
				pool.execute(app);
			} catch (IOException e) {
				pool.shutdown();
				System.out.println("Server is shutting down...");
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
		DBManager db = new DBManager();
		CourseCatalogue cat = new CourseCatalogue(db.readCoursesFromDataBase());
		ArrayList<Student> studentList = db.loadStudents();
		ServerController server = new ServerController(9090, studentList, cat);
		server.communicate();
	}
}
