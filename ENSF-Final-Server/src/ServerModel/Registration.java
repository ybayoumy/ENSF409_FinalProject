package ServerModel;
/**
 * The class representing a registration.
 * @author Yassin Bayoumy & Thomas Kahessay
 */
public class Registration {
	/**
	 * The student who is registering.
	 */
	private Student theStudent;
	/**
	 * The offering of the course.
	 */
	private CourseOffering theOffering;
	/**
	 * The grade for the course.
	 */
	private char grade;
	/**
	 * Completes a registration.
	 * @param st the student registering
	 * @param of the course offering
	 * @return a string saying whether the registration was successful or not
	 */
	public String completeRegistration (Student st, CourseOffering of) {
		if(of == null)
			return "This Section does not Exist";
		else if (of.isFull())
			return "This Section is Full";
		else {
			theStudent = st;
			theOffering = of;
			if(!checkCourseAmount()) {
				return "Registered in 6 Classes. Cannot Register in any more.";
			} else if (!checkPreReqs()) {
				return "PreReq/AntiReq Conflict";
			} else if(!checkIfCurrentlyTaking()) {
				return "Already currently registered in this course";
			} else {
				addRegistration();
				return "Succefully Registered into " + theOffering.getTheCourse().getCourseName() + 
						" " + theOffering.getTheCourse().getCourseNum();
			}
		}
	}
	/**
	 * Checks the amount of courses the student has.
	 * @return true if the student has less than 6 courses otherwise false
	 */
	private boolean checkCourseAmount() {
		if(theStudent.getCourseAmount() >= 6) {
			return false;
		}
		return true;
	}
	/**
	 * Checks if the student is currently enrolled in the course.
	 * @return true if they are in the course, false otherwise
	 */
	private boolean checkIfCurrentlyTaking() {
		for(Registration reg : theStudent.getStudentRegList()) {
			if(reg.getTheOffering().getTheCourse().equals(theOffering.getTheCourse())) {
				return false;
			}
		}
		return true;
	}
	/**
	 * Checks if the student has the pre-req's for a course.
	 * @return true if they have all pre-req's, false if not
	 */
	private boolean checkPreReqs() {
		Course theCourse = theOffering.getTheCourse();
		if(theStudent.getPastCourses().contains(theCourse)) {
			return false;
		}
		for(Course c : theCourse.getPreReq()) {
			if(!theStudent.getPastCourses().contains(c)) {
				return false;
			}
		}
		return true;
	}
	/**
	 * Adds the registration to the student and the offering.
	 */
	private void addRegistration () {
		theStudent.addRegistration(this);
		theOffering.addRegistration(this);
	}
	/**
	 * Gets the student.
	 * @return theStudent
	 */
	public Student getTheStudent() {
		return theStudent;
	}
	/**
	 * Sets the student.
	 * @param theStudent
	 */
	public void setTheStudent(Student theStudent) {
		this.theStudent = theStudent;
	}
	/**
	 * Gets the offering.
	 * @return theOffering
	 */
	public CourseOffering getTheOffering() {
		return theOffering;
	}
	/**
	 * Sets the offering.
	 * @param theOffering
	 */
	public void setTheOffering(CourseOffering theOffering) {
		this.theOffering = theOffering;
	}
	/**
	 * Gets the grade.
	 * @return grade
	 */
	public char getGrade() {
		return grade;
	}
	/**
	 * Sets the grade
	 * @param grade
	 */
	public void setGrade(char grade) {
		this.grade = grade;
	}
	
	@Override
	public String toString () {
		String st = "\n";
		st += "Student Name: " + getTheStudent() + "\n";
		st += "The Offering: " + getTheOffering () + "\n";
		st += "Grade: " + getGrade();
		st += "\n-----------\n";
		return st;
		
	}
}
