/**
 * @author Yassin Bayoumy & Thomas Kahessay
 */
package ServerModel;

import java.util.ArrayList;

public class CourseCatalogue {
private ArrayList <Course> courseList;
	
	public CourseCatalogue(ArrayList<Course> courseList) {
		this.courseList = courseList;
	}

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

	private void displayCourseNotFoundError() {
		System.err.println("Course was not found!");
		
	}
	public ArrayList <Course> getCourseList() {
		return courseList;
	}

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
