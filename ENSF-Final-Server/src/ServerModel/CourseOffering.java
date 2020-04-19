package ServerModel;
import java.util.ArrayList;
/**
 * The class for a course offering.
 * @author Yassin Bayoumy & Thomas Kahessay
 */
public class CourseOffering {
	/**
	 * The data base key for the offering.
	 */
	private int dbKey;
	/**
	 * The section number.
	 */
	private int secNum;
	/**
	 * The section capacity.
	 */
	private int secCap;
	/**
	 * The course that is offered.
	 */
	private Course theCourse;
	/**
	 * The list of all registrations.
	 */
	private ArrayList <Registration> offeringRegList;
	/**
	 * Constructs a new course offering.
	 * @param secNum the section number
	 * @param secCap the section capacity 
	 * @param dbKey the database key for the offering
	 */
	public CourseOffering (int secNum, int secCap, int dbKey) {
		this.setSecNum(secNum);
		this.setSecCap(secCap);
		this.setDbKey(dbKey);
		offeringRegList = new ArrayList <Registration>();
	}
	/**
	 * Gets the section number.
	 * @return the section number
	 */
	public int getSecNum() {
		return secNum;
	}
	/**
	 * Sets the section number.
	 * @param secNum the section number
	 */
	public void setSecNum(int secNum) {
		this.secNum = secNum;
	}
	/**
	 * Gets the section capacity.
	 * @return secCap the section capacity
	 */
	public int getSecCap() {
		return secCap;
	}
	/**
	 * Sets the section capacity.
	 * @param secCap the section capacity
	 */
	public void setSecCap(int secCap) {
		this.secCap = secCap;
	}
	/**
	 * Gets the course.
	 * @return theCourse
	 */
	public Course getTheCourse() {
		return theCourse;
	}
	/**
	 * Sets the course.
	 * @param theCourse
	 */
	public void setTheCourse(Course theCourse) {
		this.theCourse = theCourse;
	}
	/**
	 * Adds a registration into the offering.
	 * @param registration the registration
	 */
	public void addRegistration(Registration registration) {
		offeringRegList.add(registration);
	}
	/**
	 * Removes a registration from the offering.
	 * @param reg the registration
	 */
	public void removeRegistration(Registration reg) {
		offeringRegList.remove(reg);
	}
	/**
	 * Checks if the offering is full.
	 * @return true if full,f alse if not.
	 */
	public boolean isFull() {
		return secCap <= offeringRegList.size();
	}
	/**
	 * Gets the database key for the offering.
	 * @return the database key
	 */
	public int getDbKey() {
		return dbKey;
	}
	/**
	 * Sets the database key for the offering.
	 * @param dbKey the database key
	 */
	public void setDbKey(int dbKey) {
		this.dbKey = dbKey;
	}
	@Override
	public String toString () {
		String st = "\n";
		st += getTheCourse().getCourseName() + " " + getTheCourse().getCourseNum() + "\n";
		st += "Section Num: " + getSecNum() + ", section cap: "+ offeringRegList.size() + "/" + getSecCap() +"\n";
		//We also want to print the names of all students in the section
		return st;
	}
}
