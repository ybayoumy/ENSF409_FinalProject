package ServerModel;
import java.io.*;
import java.sql.*;
/**
 * The main application that implements runnable in order to allow multiple 
 * users onto the application at the same time.
 * @author Yassin Bayoumy & Thomas Kahessay
 */
public class Application implements Runnable{
	/**
	 * The course catalogue.
	 */
	private CourseCatalogue cat;
	/**
	 * The student that is using the application.
	 */
	private Student student;
	/**
	 * The database storing the courses, the students and the offerings.
	 */
	SQLDBManager db;
	/**
	 * The print writer thats used to send info to the client.
	 */
	PrintWriter socketOut;
	/**
	 * The reader that is used to read info from the client.
	 */
	BufferedReader socketIn;
	/**
	 * Constructs a new application.
	 * @param student the user
	 * @param cat the catalogue
	 * @param socketOut the printwriter
	 * @param socketIn the reader
	 * @param db the database
	 */
	public Application(Student student, CourseCatalogue cat, PrintWriter socketOut, BufferedReader socketIn, SQLDBManager db){
		this.cat = cat;
		this.student = student;
		this.socketIn = socketIn;
		this.socketOut = socketOut;
		this.db = db;
	}
	/**
	 * The main menu to choose different functionalities of the application.
	 * @throws IOException
	 * @throws NullPointerException
	 */
	public void menu() throws IOException, NullPointerException {
		int input;
		String courseName;
		int courseId;
		int courseSection;
		while(true) {
			String request = socketIn.readLine();
			String[] args = request.split(",");
			input = Integer.parseInt(args[0]);
			switch(input) {
			case 1:
				courseName = args[1].toUpperCase();
				courseId = Integer.parseInt(args[2]);
				searchCatalogue(courseName, courseId);
				break;
			case 2: 
				courseName = args[1].toUpperCase();
				courseId = Integer.parseInt(args[2]);
				courseSection = Integer.parseInt(args[3]);
				registerStudent(courseName, courseId, courseSection);
				break;
			case 3:
				courseName = args[1].toUpperCase();
				courseId = Integer.parseInt(args[2]);
				removeCourse(courseName, courseId);
				break;
			case 4:
				socketOut.println(cat + "\0");
				break;
			case 5:
				displayStudentCourses();
				break;
			default:
				System.out.println("Quitting Program");
				return;
			}
		}
	}
	/**
	 * Sends all the student courses to the client to display.
	 */
	public synchronized void displayStudentCourses() {
		String s = "Here are " + student.getStudentName() + "'s current Courses: \n";
		for(Registration reg : student.getStudentRegList()) {
			s += reg.getTheOffering() + "\n";
		}
		s += "\0";
		socketOut.println(s);
	}
	/**
	 * Removes a course from the list of all the student courses.
	 * @param courseName the name of the course
	 * @param courseId the course id
	 */
	public synchronized void removeCourse(String courseName, int courseId) {
		Course theCourse = cat.searchCat(courseName, courseId);
		if(theCourse == null)
			socketOut.println("Course was not found\0");
		else {
			if(student.removeRegistration(theCourse, db))
				socketOut.println("Succefully dropped Course\0");
			else 
				socketOut.println("Could not drop the Course\0");
		}
	}
	/**
	 * Searches the catalogue for a specific course.
	 * @param name the course name
	 * @param id the course id
	 */
	public synchronized void searchCatalogue(String name, int id) {
		Course result = cat.searchCat(name, id);
		if(result != null)
			socketOut.println("Course was found, here it is...\n" + result + "\0");
		else 
			socketOut.println("Course was not found\0");
	}
	/**
	 * Registers the student into a course.
	 * @param courseName the name of the course
	 * @param courseId the id of the course
	 * @param courseSection the section of the course
	 */
	private synchronized void registerStudent(String courseName, int courseId, int courseSection) {
		Course course = cat.searchCat(courseName, courseId);
		if(course == null) {
			socketOut.println("Course was not found, Cannot Register\0");
		} else {
			Registration reg = new Registration();
			CourseOffering offering = course.getCourseOfferingAt(courseSection-1);
			if(offering == null) {
				socketOut.println("Offering was not found, Cannot Register\0");
			} else if (offering.isFull()) {
				socketOut.println("Offering is Full, Cannot Register\0");
			} else {
				String res = reg.completeRegistration(student, offering);
				try {
					db.addRegistration(reg);
				} catch (SQLException e) {
					System.out.println("SQL Exception in application, regsitering student.");
					e.printStackTrace();
				}
				socketOut.println(res + "\0");
			}
		}
	}
	/**
	 * Runs the application through a single thread.
	 */
	@Override
	public void run() {
		try {
			menu();
		} catch (NullPointerException e) {

		} catch (IOException e) {
			System.out.println("Null Pointer Error in server communicate.");
			e.printStackTrace();
		} 
	}
}
