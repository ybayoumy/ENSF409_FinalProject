package ServerModel;
import java.sql.*;
import java.util.*;
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
	 * Initializes a connection.
	 */
	public void initializeConnection() {
		try {
			// Register JDBC driver
			Driver driver = new com.mysql.cj.jdbc.Driver();
			DriverManager.registerDriver(driver);
		} catch (SQLException e) {
			System.out.println("Problem in initializing.");
			e.printStackTrace();
		}
	}
	/**
	 * Creates the students table.
	 */
	public void createStudentsTable() {
		String sql = "CREATE TABLE students " + "(Id INTEGER not NULL, " + " Name VARCHAR(255), "
				+ " Password VARCHAR(255), " + " PRIMARY KEY ( Id ))";

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Student Table can NOT be created!");
		}
	}
	/**
	 * Creates the courses table.
	 */
	public void createCoursesTable() {
		String sql = "CREATE TABLE courses " + "(CourseKey INTEGER not NULL, " + " CourseName VARCHAR(255), " + " CourseId INTEGER not NULL, "
				+ " Section INTEGER not NULL, "  + " Capacity INTEGER not NULL, "  + " PRIMARY KEY ( CourseKey ))";

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Courses Table can NOT be created!");
		}
	}
	/**
	 * Creates the registration table.
	 */
	public void createRegTable() {
		String sql = "CREATE TABLE registrations " + "(StudentId INTEGER not NULL, " + " CourseKey INTEGER not NULL, "
				+ " Grade VARCHAR(255)) ";
		try {
			Statement stmt = con.createStatement(); // construct a statement
			stmt.executeUpdate(sql); // execute my query (i.e. sql)
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Registration Table can NOT be created!");
		}
	}
	/**
	 * Loads the catalogue from the database.
	 * @return the list of all courses.
	 * @throws SQLException
	 */
	public ArrayList<Course> loadCatalogueFromDB() throws SQLException{
		Statement stmnt =  con.createStatement();
		ResultSet rs = stmnt.executeQuery("SELECT * FROM courses;");
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
		ResultSet rs = stmnt.executeQuery("SELECT * FROM students;");
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
		rs = stmnt.executeQuery("SELECT * FROM registrations;");

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
		String query= "SELECT * FROM students where Id= ? and Password =?";
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
	 * Add a student into the database.
	 * @param id the student id
	 * @param name the students name
	 * @param password the students password
	 */
	public void addStudent(int id, String name, String password) throws SQLException{
		String query = "INSERT INTO students (ID,name , password) values(?,?,?)";
		PreparedStatement pStat = con.prepareStatement(query);
		pStat.setInt(1, id);
		pStat.setString(2, name);
		pStat.setString(3, password);
		pStat.executeUpdate();
		pStat.close();
	}
	/**
	 * Adds a course into the database.
	 * @param courseKey the course key
	 * @param courseName the course name
	 * @param courseId the course id
	 * @param section the section number
	 * @param capacity the course capacity
	 */
	public void addCourse(int courseKey, String courseName, int courseId, int section, int capacity) throws SQLException {
		String query = "INSERT INTO courses (courseKey, courseName, courseId, section, capacity) values(?,?,?,?,?)";
		PreparedStatement pStat = con.prepareStatement(query);
		pStat.setInt(1, courseKey);
		pStat.setString(2, courseName);
		pStat.setInt(3, courseId);
		pStat.setInt(4, section);
		pStat.setInt(5, capacity);
		pStat.executeUpdate();
		pStat.close();
	}
	/**
	 * Adds a registration into the database.
	 * @param reg a registration
	 * @throws SQLException
	 */
	public void addRegistration(Registration reg) throws SQLException {
		String query = "INSERT INTO registrations (StudentId, CourseKey, Grade) values (?, ?, ?)";
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
		String query = "DELETE FROM registrations where StudentId = ? and CourseKey = ?";
		PreparedStatement pStat = con.prepareStatement(query);
		pStat.setInt(1, reg.getTheStudent().getStudentId());
		pStat.setInt(2, reg.getTheOffering().getDbKey());
		pStat.executeUpdate();
		pStat.close();
	}

	public static void main(String [] args) {
		try {
			SQLDBManager myApp = new SQLDBManager();
			myApp.initializeConnection();
			myApp.createCoursesTable();
			myApp.createRegTable();
			myApp.createStudentsTable();
			myApp.addStudent(1001, "Yassin", "password");
			myApp.addStudent(1002, "Thomas", "password");
			myApp.addStudent(1003, "Andrew", "password");
			myApp.addStudent(1004, "Jack", "password");
			myApp.addStudent(1005, "Jill", "password");
			myApp.addStudent(1006, "Jessica", "password");
			myApp.addStudent(1007, "Helen", "password");
			myApp.addStudent(1008, "Maria", "password");
			myApp.addStudent(1009, "Sarah", "password");
			myApp.addStudent(1010, "James", "password");
			myApp.addCourse(1, "CHEM", 209, 1, 80);
			myApp.addCourse(2, "CHEM", 209, 2, 80);
			myApp.addCourse(3, "MATH", 211, 1, 80);
			myApp.addCourse(4, "MATH", 211, 2, 80);
			myApp.addCourse(5, "ENGG", 233, 1, 80);
			myApp.addCourse(6, "ENGG", 233, 2, 80);
			myApp.addCourse(7, "ENGG", 200, 1, 80);
			myApp.addCourse(8, "ENGG", 200, 2, 80);
			myApp.addCourse(9, "MATH", 275, 1, 80);
			myApp.addCourse(10, "MATH", 275, 2, 80);
			myApp.addCourse(11, "ENGG", 201, 1, 80);
			myApp.addCourse(12, "ENGG", 201, 2, 80);
			myApp.addCourse(13, "PHYS", 259, 1, 80);
			myApp.addCourse(14, "PHYS", 259, 2, 80);
			myApp.addCourse(15, "ENGG", 202, 1, 80);
			myApp.addCourse(16, "ENGG", 202, 2, 80);
			myApp.addCourse(17, "ENGG", 225, 1, 80);
			myApp.addCourse(18, "ENGG", 225, 2, 80);
			myApp.addCourse(19, "MATH", 277, 1, 80);
			myApp.addCourse(20, "MATH", 277, 2, 80);
		}catch(SQLException e) {
			System.out.println("Error in creating database.");
		}
		System.out.println("Database has been created.");
	}
}
