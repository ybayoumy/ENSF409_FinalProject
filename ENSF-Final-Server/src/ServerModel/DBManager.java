/**
 * @author Yassin Bayoumy & Thomas Kahessay
 */
package ServerModel;

import java.util.ArrayList;

public class DBManager {
	ArrayList <Student> studentList;
	ArrayList <Course> courseList;

	public DBManager () {
		courseList = new ArrayList<Course>();
		studentList = new ArrayList<Student>();
	}
	
	public ArrayList<Student> loadStudents(){
		studentList.add(new Student("Jack", 1000));
		studentList.add(new Student("Jill", 1001));
		
		// Jack and Jill have completed MATH 211 but Bob has not
		for(Student s : studentList) {
			s.addPastCourse(courseList.get(5));
		}
		studentList.add(new Student("Bob", 1002));
		
		//All students have completed MATH 275 and ENGG 233
		for(Student s : studentList) {
			s.addPastCourse(courseList.get(0));
			s.addPastCourse(courseList.get(2));
		}
		return studentList;
	}
	public ArrayList<Course> readCoursesFromDataBase() {
		courseList.add(new Course ("ENGG", 233));
		courseList.add(new Course ("ENSF", 337));
		courseList.add(new Course ("MATH", 275));
		courseList.add(new Course ("PHYS", 259));
		courseList.add(new Course ("CHEM", 209));
		courseList.add(new Course ("MATH", 211));
		courseList.add(new Course ("MATH", 277));
		courseList.add(new Course ("ENGG", 200));
		courseList.add(new Course ("ENEL", 327));
		courseList.add(new Course ("ENEL", 201));
		
		//ENGG 233 is a PreReq to ENSF 337
		courseList.get(1).addPreReq(courseList.get(0));
		
		//MATH 211 and MATH 275 are a PreReq to MATH 277
		courseList.get(6).addPreReq(courseList.get(5));
		courseList.get(6).addPreReq(courseList.get(2));
		
		//Each Course has 2 Course Offerings
		for(Course c : courseList) {
			c.addOffering(new CourseOffering(1, 80));
			c.addOffering(new CourseOffering(2, 60));
		}
		return courseList;
	}
}
