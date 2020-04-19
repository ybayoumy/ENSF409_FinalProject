package ServerModel;

import java.sql.SQLException;
import java.util.ArrayList;
/**
 * The class representing a student.
 * @author Yassin Bayoumy & Thomas Kahessay
 */
public class Student {
	/**
	 * The name of the student.
	 */
	private String studentName;
	/**
	 * The id of the student.
	 */
	private int studentId;
	/**
	 * The list of all offerings.
	 */
	//private ArrayList<CourseOffering> offeringList;
	/**
	 * The list of all registrations.
	 */
	private ArrayList<Registration> studentRegList;
	/**
	 * The list of all past courses.
	 */
	private ArrayList<Course> pastCourses;
	/**
	 * Constructs a student.
	 * @param studentName the name of the student.
	 * @param studentId the id of the student.
	 */
	public Student (String studentName, int studentId) {
		this.setStudentName(studentName);
		this.setStudentId(studentId);
		studentRegList = new ArrayList<Registration>();
		pastCourses = new ArrayList<Course>();
	}
	/**
	 * Removes a registration from the registration list.
	 * @param course the course being removed
	 * @param db the database
	 * @return true if removed, false if not
	 */
	public boolean removeRegistration(Course course, SQLDBManager db){
		for(Registration reg : studentRegList) {
			if(reg.getTheOffering().getTheCourse().equals(course)) {
				reg.getTheOffering().removeRegistration(reg);
				studentRegList.remove(reg);
				try {
					db.removeRegistration(reg);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return true;
			}	
		}
		return false;
	}
	/**
	 * Gets the student name.
	 * @return studentName
	 */
	public String getStudentName() {
		return studentName;
	}
	/**
	 * Sets the student name.
	 * @param studentName
	 */
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	/**
	 * Gets the student id.
	 * @return studentId
	 */
	public int getStudentId() {
		return studentId;
	}
	/**
	 * Sets the student id.
	 * @param studentId
	 */
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	/**
	 * Gets the amount of courses.
	 * @return amount of courses
	 */
	public int getCourseAmount() {
		return studentRegList.size();
	}
	/**
	 * Gets the past courses.
	 * @return the past courses
	 */
	public ArrayList<Course> getPastCourses(){
		return pastCourses;
	}
	/**
	 * Adds a registration to the student.
	 * @param registration
	 */
	public void addRegistration(Registration registration) {
		studentRegList.add(registration);
	}
	/**
	 * Gets the list of registrations.
	 * @return studentRegList
	 */
	public ArrayList<Registration> getStudentRegList(){
		return studentRegList;
	}
	/**
	 * Adds a course to the list of past courses.
	 * @param course
	 */
	public void addPastCourse(Course course) {
		pastCourses.add(course);
	}
	
	@Override
	public String toString () {
		String st = "Student Name: " + getStudentName() + "\n" +
				"Student Id: " + getStudentId() + "\n\n";
		return st;
	}

}
