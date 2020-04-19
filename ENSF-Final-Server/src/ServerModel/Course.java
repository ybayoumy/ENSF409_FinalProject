package ServerModel;
import java.util.ArrayList;
/**
 * A class representing a course.
 * @author Yassin Bayoumy & Thomas Kahessay
 */
public class Course {
	/**
	 * The name of the course.
	 */
	private String courseName;
	/**
	 * The number of the course.
	 */
	private int courseNum;
	/**
	 * The prerequistes required for the course.
	 */
	private ArrayList<Course> preReq;
	/**
	 * The offerings of the course.
	 */
	private ArrayList<CourseOffering> offeringList;
	/**
	 * Constructs a new course.
	 * @param courseName the course name
	 * @param courseNum the course id
	 */
	public Course(String courseName, int courseNum) {
		this.setCourseName(courseName);
		this.setCourseNum(courseNum);
		preReq = new ArrayList<Course>();
		offeringList = new ArrayList<CourseOffering>();
	}
	/**
	 * Adds an offering for the course.
	 * @param offering the course offering
	 */
	public void addOffering(CourseOffering offering) {
		if (offering != null && offering.getTheCourse() == null) {
			offering.setTheCourse(this);
			if (!offering.getTheCourse().getCourseName().equals(courseName)
					|| offering.getTheCourse().getCourseNum() != courseNum) {
				System.err.println("Error! This section belongs to another course!");
				return;
			}
			offeringList.add(offering);
		}
	}
	/**
	 * Gets the name of the course.
	 * @return courseName
	 */
	public String getCourseName() {
		return courseName;
	}
	/**
	 * Sets the name of the course.
	 * @param courseName
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	/**
	 * Gets the course number.
	 * @return courseNum
	 */
	public int getCourseNum() {
		return courseNum;
	}
	/**
	 * Sets the course nunber
	 * @param courseNum
	 */
	public void setCourseNum(int courseNum) {
		this.courseNum = courseNum;
	}
	/**
	 * Gets the pre-requisites for the course.
	 * @return preReq the list of pre-requisites 
	 */
	public ArrayList<Course> getPreReq(){
		return preReq;
	}
	/**
	 * Adds a pre-requisite for the course.
	 * @param course the pre-req course
	 */
	public void addPreReq(Course course) {
		preReq.add(course);
	}
	/**
	 * Gets a course offering.
	 * @param i the place of the course offering in the list
	 * @return the item at that place in the list 
	 */
	public CourseOffering getCourseOfferingAt(int i) {
		if (i < 0 || i >= offeringList.size() )
			return null;
		else
			return offeringList.get(i);
	}

	@Override
	public String toString () {
		String st = "\n";
		st += getCourseName() + " " + getCourseNum ();
		st += "\nAll course sections:\n";
		for (CourseOffering c : offeringList)
			st += c;
		st += "\n-------\n";
		return st;
	}
}
