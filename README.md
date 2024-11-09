# User Access Management System
The User Access Management System is a web-based application for managing user access to software applications within an organization. It allows users to sign up, log in, request access to software, and enables managers to approve or reject these requests. Admins have additional privileges to add new software applications.

# Features
User Registration: New users can register with a default role of "Employee."
User Authentication: Registered users can log in to the system.
Role-Based Access:
Employee: Can request access to software.
Manager: Can view and approve/reject access requests.
Admin: Can add new software applications.
Software Management: Admins can add and manage software applications.
Request Management: Managers can approve or reject access requests.

Technology Stack
Backend: Java Servlets
Frontend: JSP, HTML, CSS, JavaScript
Database: PostgreSQL
Build Tool: Maven
Server: Apache Tomcat

Prerequisites
Java (version 8 or later)
Apache Tomcat (version 9 or later)
PostgreSQL (version 9.5 or later)
Maven (for building the project)
Setup Instructions
1. Clone the Repository

git clone <repository-url>
cd UserAccessManagement
2. Configure the Database
Open PostgreSQL and create a new database:

sql
CREATE DATABASE user_access_management;
Create the required tables by running the following SQL commands:

sql
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL
);

CREATE TABLE software (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description TEXT,
    access_levels VARCHAR(50)
);

CREATE TABLE requests (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id),
    software_id INT REFERENCES software(id),
    access_type VARCHAR(20),
    reason TEXT,
    status VARCHAR(20) DEFAULT 'Pending'
);
Update database connection details in Database.java:


private static final String URL = "jdbc:postgresql://localhost:5432/user_access_management";
private static final String USER = "your_database_username";
private static final String PASSWORD = "your_database_password";
3. Build the Project
Run the following command to build the project using Maven:

bash
mvn clean install
4. Deploy on Tomcat
Copy the generated WAR file (target/UserAccessManagement.war) to the Tomcat webapps directory.
Start the Tomcat server by running:

bash
<TOMCAT_HOME>/bin/startup.sh
Access the application at http://localhost:8080/UserAccessManagement.
5. Initial User Setup (Optional)
You can manually insert initial user records in the users table for testing:

sql
INSERT INTO users (username, password, role) VALUES ('admin', 'hashed_password_here', 'Admin');
INSERT INTO users (username, password, role) VALUES ('manager', 'hashed_password_here', 'Manager');
INSERT INTO users (username, password, role) VALUES ('employee', 'hashed_password_here', 'Employee');
(Replace 'hashed_password_here' with actual hashed values.)

Usage
Sign Up: Go to /signup.jsp to create a new account as an "Employee."
Login: Access the login page at /login.jsp.

Request Access:
Employees can request access to software applications.
Managers can view and approve/reject these requests.
Add Software: Admin users can add new software through /createSoftware.jsp.
Logout: Use the logout link to end the session.


Project Structure
UserAccessManagement/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── tabarak/
│   │   │           └── useraccess/
│   │   │               ├── SignUpServlet.java
│   │   │               ├── LoginServlet.java
│   │   │               ├── SoftwareServlet.java
│   │   │               ├── RequestServlet.java
│   │   │               ├── ApprovalServlet.java
│   │   │               └── LogoutServlet.java
│   │   ├── resources/
│   │   └── webapp/
│   │       ├── css/
│   │       ├── jsp/
│   │       │   ├── signup.jsp
│   │       │   ├── login.jsp
│   │       │   ├── createSoftware.jsp
│   │       │   ├── requestAccess.jsp
│   │       │   └── pendingRequests.jsp
│   │       └── WEB-INF/
│   │           ├── web.xml
├── pom.xml
└── README.md
Troubleshooting
404 Errors: Ensure that Tomcat is running and that servlets are mapped correctly in web.xml.
Database Connection Issues: Verify PostgreSQL credentials in Database.java and ensure the PostgreSQL server is running.
