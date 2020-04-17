/**
 * @author Yassin Bayoumy & Thomas Kahessay
 */
package ServerModel;

public class Registration {
	private Student theStudent;
	private CourseOffering theOffering;
	private char grade;
	
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

	private boolean checkCourseAmount() {
		if(theStudent.getCourseAmount() >= 6) {
			return false;
		}
		return true;
	}
	
	private boolean checkIfCurrentlyTaking() {
		for(Registration reg : theStudent.getStudentRegList()) {
			if(reg.getTheOffering().getTheCourse().equals(theOffering.getTheCourse())) {
				return false;
			}
		}
		return true;
	}
	
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
