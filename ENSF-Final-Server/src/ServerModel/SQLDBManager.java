package ServerModel;
import java.sql.*;
import java.util.ArrayList;
/**
 * The class that manages the database.
 * @author Yassin Bayoumy & Thomas Kahessay
 */
public class SQLDBManager implements DBCredentials {
	/**
	 * The connection to the driver.
	 */
	Connection con;
	/**
	 * The result of searching through the table.
	 */
	private ResultSet rs;
	/**
	 * The list of all courses.
	 */
	ArrayList<Course> courseList;
	/**
	 * The list of all offerings.
	 */
	ArrayList<CourseOffering> offeringList;
	/**
	 * The list of all students.
	 */
	ArrayList<Student> studentList;
	/**
	 * Creates a new database manager.
	 * @throws SQLException
	 */
	public SQLDBManager() throws SQLException{
			con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			courseList = new ArrayList<Course>();
			studentList = new ArrayList<Student>();
			offeringList = new ArrayList<CourseOffering>();
	}
	/**
	 * Loads the catalogue from the database.
	 * @return the list of all courses.
	 * @throws SQLException
	 */
	public ArrayList<Course> loadCatalogueFromDB() throws SQLException{
		Statement stmnt =  con.createStatement();
		ResultSet rs = stmnt.executeQuery("SELECT * FROM project.courses;");
		courseList = new ArrayList<Course>();
		
		Course currentCourse = null;
		while(rs.next()) {
			CourseOffering offering = new CourseOffering(rs.getInt("Section"), rs.getInt("Capacity"), rs.getInt("CourseKey"));
			if(currentCourse == null || 
			!(currentCourse.getCourseName().equals(rs.getString("CourseName")) && currentCourse.getCourseNum() == rs.getInt("CourseId"))) {
				currentCourse = new Course(rs.getString("CourseName"), rs.getInt("CourseId"));
				courseList.add(currentCourse);
			}
			currentCourse.addOffering(offering);
			offeringList.add(offering);
		}
		return courseList;
	}
	/**
	 * Loads all the students from the database.
	 * @return the list of all students.
	 * @throws SQLException
	 */
	public ArrayList<Student> loadStudentsFromDB() throws SQLException{
		Statement stmnt =  con.createStatement();
		ResultSet rs = stmnt.executeQuery("SELECT * FROM project.students;");
		studentList = new ArrayList<Student>();
		
		while(rs.next()) {
			studentList.add(new Student(rs.getString("Name"), rs.getInt("Id")));
		}
		return studentList;
	}
	/**
	 * Loads all the registrations from the database.
	 * @throws SQLException
	 */
	public void loadRegistrations() throws SQLException {
		Statement stmnt =  con.createStatement();
		rs = stmnt.executeQuery("SELECT * FROM project.registrations;");
		
		while(rs.next()) {
			//Finding Specific Student
			Student theStudent = null;
			for(Student s: studentList) {
				if(s.getStudentId() == rs.getInt("StudentId")) {
					theStudent = s;
					break;
				}
			}
			CourseOffering theOffering = offeringList.get(rs.getInt("CourseKey") - 1);
			Registration reg = new Registration();
			reg.completeRegistration(theStudent, theOffering);
		}
	}
	/**
	 * Verifies the login.
	 * @param id the users id
	 * @param password the users password
	 * @return true if the id and password match, false otherwise.
	 * @throws SQLException
	 */
	public boolean verifyLogin(int id, String password) throws SQLException {
		String query= "SELECT * FROM project.students where Id= ? and Password =?";
		PreparedStatement pStat= con.prepareStatement(query);
		pStat.setInt(1, id);
		pStat.setString(2, password);
		rs = pStat.executeQuery();
		if(rs.next()) {
			return true;
		}
		return false;
	}
	/**
	 * Adds a registration into the database.
	 * @param reg a registration
	 * @throws SQLException
	 */
	public void addRegistration(Registration reg) throws SQLException {
		String query = "INSERT INTO project.registrations (StudentId, CourseKey, Grade) values (?, ?, ?)";
		PreparedStatement pStat = con.prepareStatement(query);
		pStat.setInt(1, reg.getTheStudent().getStudentId());
		pStat.setInt(2, reg.getTheOffering().getDbKey());
		pStat.setString(3, reg.getGrade() + "");
		pStat.executeUpdate();
		pStat.close();
	}
	/**
	 * Removes a registration from the database.
	 * @param reg a registration
	 * @throws SQLException
	 */
	public void removeRegistration(Registration reg) throws SQLException {
		String query = "DELETE FROM project.registrations where StudentId = ? and CourseKey = ?";
		PreparedStatement pStat = con.prepareStatement(query);
		pStat.setInt(1, reg.getTheStudent().getStudentId());
		pStat.setInt(2, reg.getTheOffering().getDbKey());
		pStat.executeUpdate();
		pStat.close();
	}
}
