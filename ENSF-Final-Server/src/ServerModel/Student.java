package ServerModel;

import java.util.ArrayList;

public class Student {
	private String studentName;
	private int studentId;
	//private ArrayList<CourseOffering> offeringList;
	private ArrayList<Registration> studentRegList;
	private ArrayList<Course> pastCourses;
	
	public Student (String studentName, int studentId) {
		this.setStudentName(studentName);
		this.setStudentId(studentId);
		studentRegList = new ArrayList<Registration>();
		pastCourses = new ArrayList<Course>();
	}
	
	public boolean removeRegistration(Course course){
		for(Registration reg : studentRegList) {
			if(reg.getTheOffering().getTheCourse().equals(course)) {
				System.out.println("Succefully dropped " + reg.getTheOffering().getTheCourse().getCourseName() + 
						" " + reg.getTheOffering().getTheCourse().getCourseNum());
				reg.getTheOffering().removeRegistration(reg);
				studentRegList.remove(reg);
				return true;
			}	
		}
		return false;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	
	public int getCourseAmount() {
		return studentRegList.size();
	}
	
	public ArrayList<Course> getPastCourses(){
		return pastCourses;
	}

	@Override
	public String toString () {
		String st = "Student Name: " + getStudentName() + "\n" +
				"Student Id: " + getStudentId() + "\n\n";
		return st;
	}

	public void addRegistration(Registration registration) {
		studentRegList.add(registration);
	}
	
	public ArrayList<Registration> getStudentRegList(){
		return studentRegList;
	}
	
	public void addPastCourse(Course course) {
		pastCourses.add(course);
	}
}
