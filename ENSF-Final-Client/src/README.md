# ENSF 409 Final Project

## Usage

First the files must have been downloaded and unzipped and set up as two separate projects in any IDE or run through command line. Once this is
done the user must edit the DBCredentials file in order to change the DB_URL and enter the name of their schema into the underscore
after the port number and forward slash.

```
final String DB_URL = "jdbc:mysql://localhost:3306/_____?serverTimezone=MST";
```

The USERNAME and PASSWORD fields in the DBCredentials must also be edited in order to connect to MySQL.

Once this is completed the user can run the SQLDBManager class once in order to setup the database into their desired schema. If this is
done correctly, the user should have "Database has been created." printed to their console. The following is the step-by-step for setting up the database through the command line.

```bash
javac SQLDBManager.java
```

Followed by

```bash
java SQLDBManager
```

Following this, the user can begin running the program by locating and running the ServerController class within the ENSF-Final-Server folder and inside the ServerController package. Then, multiple clients can be run through locating running the Controller class within the ENSF-Final-Client and inside the ClientController package. The following is the step-by-step for setting up the database through the command line.

```bash
javac ServerController.java
javac Controller.java
```

Followed by

```bash
java ServerController
java Controller
```

If all goes well their should be a GUI to login which can be logged in using

		username: 1001 password: password
		username: 1002 password: password
		... 
		username: 1010 password: password

After they have logged in, the functions of the program allow the user to search for a course, enroll in a course, drop a course, view course catalogue, and view student courses. (None of the students come with registrations in the database to begin with).

## Bonus Features
The bonus feature implemented into our program is the login component.

## Info
* Thomas Kahessay 		thomas.kahessay@ucalgary.ca 
* Yassin Bayoumy			yassin.bayoumy@ucalgary.ca
