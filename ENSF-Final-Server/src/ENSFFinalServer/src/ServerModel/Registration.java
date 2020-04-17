/**
 * @author Yassin Bayoumy & Thomas Kahessay
 */
package ServerModel;

public class Registration {
	private Student theStudent;
	private CourseOffering theOffering;
	private char grade;
	
	public void completeRegistration (Student st, CourseOffering of) {
		if(of == null)
			System.err.println("This Section does not Exist");
		else if (of.isFull())
			System.err.println("This Section is Full");
		else {
			theStudent = st;
			theOffering = of;
			if(checkStudentEligibility()) {
				addRegistration();
				System.out.println("Succefully Registered into " + theOffering.getTheCourse().getCourseName() + 
						" " + theOffering.getTheCourse().getCourseNum());
			}
			else 
				System.err.println("Student is not eligible to register in this course");
		}
	}
	
	private boolean checkStudentEligibility() {
		return checkCourseAmount() && checkPreReqs() && checkIfCurrentlyTaking();
	}
	
	private boolean checkCourseAmount() {
		if(theStudent.getCourseAmount() >= 6) {
			System.err.println("Student is Registered in 6 Classes. Cannot Register in any more.");
			return false;
		}
		return true;
	}
	
	private boolean checkIfCurrentlyTaking() {
		for(Registration reg : theStudent.getStudentRegList()) {
			if(reg.getTheOffering().getTheCourse().equals(theOffering.getTheCourse())) {
				System.err.println("Student is already registered in this course");
				return false;
			}
		}
		return true;
	}
	
	private boolean checkPreReqs() {
		Course theCourse = theOffering.getTheCourse();
		if(theStudent.getPastCourses().contains(theCourse)) {
			System.err.println("Student has already taken this class");
			return false;
		}
		for(Course c : theCourse.getPreReq()) {
			if(!theStudent.getPastCourses().contains(c)) {
				System.err.println("Student does not have the required prerequisite courses");
				return false;
			}
		}
		return true;
	}
	
	private void addRegistration () {
		theStudent.addRegistration(this);
		theOffering.addRegistration(this);
	}
	
	
	public Student getTheStudent() {
		return theStudent;
	}
	public void setTheStudent(Student theStudent) {
		this.theStudent = theStudent;
	}
	public CourseOffering getTheOffering() {
		return theOffering;
	}
	public void setTheOffering(CourseOffering theOffering) {
		this.theOffering = theOffering;
	}
	public char getGrade() {
		return grade;
	}
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
