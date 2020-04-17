/**
 * @author Yassin Bayoumy & Thomas Kahessay
 */
package ServerModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Application implements Runnable{
	private CourseCatalogue cat;
	private Student student;
	PrintWriter socketOut;
	BufferedReader socketIn;
	
	public Application(Student student, CourseCatalogue cat, PrintWriter socketOut, BufferedReader socketIn){
		this.cat = cat;
		this.student = student;
		this.socketIn = socketIn;
		this.socketOut = socketOut;
	}
	
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
	
	public synchronized void displayStudentCourses() {
		String s = "Here are " + student.getStudentName() + "'s current Courses: \n";
		for(Registration reg : student.getStudentRegList()) {
			s += reg.getTheOffering() + "\n";
		}
		s += "\0";
		socketOut.println(s);
	}
	
	public synchronized void removeCourse(String courseName, int courseId) {
		Course theCourse = cat.searchCat(courseName, courseId);
		if(theCourse == null)
			socketOut.println("Course was not found\0");
		else {
			if(student.removeRegistration(theCourse))
				socketOut.println("Succefully dropped Course\0");
			else 
				socketOut.println("Could not drop the Course\0");
		}
	}
	
	public synchronized void searchCatalogue(String name, int id) {
		Course result = cat.searchCat(name, id);
		if(result != null)
			socketOut.println("Course was found, here it is...\n" + result + "\0");
		else 
			socketOut.println("Course was not found\0");
	}

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
				socketOut.println(res + "\0");
			}
		}
	}
	
	@Override
	public void run() {
		try {
			menu();
		} catch (NullPointerException e) {
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	/*public void run() {
		menu();
	}*/
}
