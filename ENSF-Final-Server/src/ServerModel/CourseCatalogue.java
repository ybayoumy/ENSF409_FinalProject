package ServerModel;
import java.util.ArrayList;
/**
 * The class representing a course catalogue.
 * @author Yassin Bayoumy & Thomas Kahessay
 */
public class CourseCatalogue {
	/**
	 * The list of all courses.
	 */
	private ArrayList <Course> courseList;
	/**
	 * Constructs a new course catalogue.
	 * @param courseList the list of all courses.
	 */
	public CourseCatalogue(ArrayList<Course> courseList) {
		this.courseList = courseList;
	}
	/**
	 * Searches the catalogue for a specific course.
	 * @param courseName the name of the course
	 * @param courseNum the number of the course
	 * @return the course if found, otherwise null
	 */
	public Course searchCat (String courseName, int courseNum) {
		for (Course c : courseList) {
			if (courseName.equals(c.getCourseName()) &&
					courseNum == c.getCourseNum()) {
				return c;
			}	
		}
		displayCourseNotFoundError();
		return null;
	}
	/**
	 * Displays an error that the course was not found.
	 */
	private void displayCourseNotFoundError() {
		System.err.println("Course was not found!");
		
	}
	/**
	 * Gets the list of all courses.
	 * @return courseList the list of all courses
	 */
	public ArrayList <Course> getCourseList() {
		return courseList;
	}
	/**
	 * Sets the course list.
	 * @param courseList the list of all courses
	 */
	public void setCourseList(ArrayList <Course> courseList) {
		this.courseList = courseList;
	}
	
	@Override
	public String toString () {
		String st = "All courses in the catalogue: \n";
		for (Course c : courseList) {
			st += c;  //This line invokes the toString() method of Course
			st += "\n";
		}
		return st;
	}
}
