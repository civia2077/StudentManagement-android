Student Management System
Project Overview
The Student Management System is a client-server-based application designed to help educational institutions or administrators efficiently manage student information. The system includes two roles: students and administrators, with the following features:

Students: View personal information.
Administrators: Perform create, read, update, and delete (CRUD) operations on all student records.
Common Features: User registration, login, search, and filter student information.
The project uses a local database to store user and student data and provides an efficient management experience through a user-friendly interface.

Features
Core Features
User Management:
User registration and login validation.
Role differentiation: Students and administrators.
Student Information Management:
Students: View their own detailed information.
Administrators: Perform CRUD operations on all student records.
Information Search:
Search for student information using a search bar or filter options.
Error Notifications:
Provide instant feedback for invalid inputs or failed operations.
Technical Features
Local Database:
MySQL is used to store user and student data.
Error Handling:
The system provides detailed error notifications to enhance user experience.
Security:
User passwords are encrypted to ensure data security.
Technology Stack
Backend: Spring Boot
Frontend: HTML + CSS + JavaScript
Database: MySQL
Development Tools:
IntelliJ IDEA
Navicat for MySQL
Version Control: Git
System Deployment
Environment Requirements
JDK: Version 11 or higher
MySQL: Version 5.7 or higher
Maven: Version 3.6 or higher
Steps to Run
Clone the Repository:

git clone https://github.com/your-repository/student-management-system.git
cd student-management-system
Configure the Database:
Create a database springboot_student in MySQL.
Import the schema.sql file from the project.
Start the Project:
Run the Spring Boot main class using an IDE or use the command:

mvn spring-boot:run
Access the System:
Open a browser and navigate to http://localhost:8080.
Database Design
The database includes the following core tables:

role: Stores user role information.
user: Stores user account information.
student: Stores detailed student information.
user_roles: Represents the many-to-many relationship between users and roles.
For detailed table structures and relationships, refer to docs/database_diagram.puml.

Project Structure

src/
├── main/
│   ├── java/com/yourname/studentmanagement/
│   │   ├── controller/    # Controller layer
│   │   ├── service/       # Service layer
│   │   ├── repository/    # Database access layer
│   │   └── model/         # Entity classes
│   └── resources/
│       ├── static/        # Static resources
│       ├── templates/     # Frontend templates
│       └── application.yml # Configuration file
└── test/                  # Test code
Future Plans
Add student attendance management functionality.
Implement data import/export features (e.g., Excel).
Support a mobile-responsive user interface.
Provide more detailed operation logs.
Contribution Guide
Contributions are welcome! Please follow these steps:

Fork the repository.
Create a branch for your changes:

git checkout -b feature-branch
Commit your changes:

git commit -m "Add new feature"
Push your branch and create a pull request.

License
This project is licensed under the MIT License. Feel free to modify and distribute it.
