# Student Management System

A Java full-stack web application to manage student records. Built with **Java 17, Spring Boot, Spring Data JPA (Hibernate), JSP, Bootstrap 5 and MySQL**.

## Features

- Dashboard with total student count and recently added students
- Create, read, update and delete student records (CRUD)
- Search students by name, email, department or course
- Server-side validation for required fields, email, phone number and date of birth
- Duplicate email protection
- Responsive Bootstrap interface
- Layered architecture: Controller → Service → Repository → Entity

## Technology stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Backend | Spring Boot 3.3.5, Spring MVC |
| Persistence | Spring Data JPA, Hibernate |
| Frontend | JSP, HTML, CSS, Bootstrap 5 |
| Database | MySQL 8+ |
| Build | Maven |

## Prerequisites

Install:
1. JDK 17 or later
2. MySQL 8+
3. Apache Maven 3.8+
4. IDE such as IntelliJ IDEA, Eclipse or VS Code

Check installations:
```bash
java -version
mvn -version
```

## Database setup

Start MySQL and execute:
```sql
CREATE DATABASE student_management_db;
```
Or run `sql/schema.sql` in MySQL Workbench.

## Configure database credentials

The default local configuration is in `src/main/resources/application.properties`:
- URL: `jdbc:mysql://localhost:3306/student_management_db`
- Username: `root`
- Password: `root`

Change the username/password to match your MySQL setup. Prefer environment variables for real deployments:
```bash
# macOS/Linux
export DB_USERNAME=root
export DB_PASSWORD=your_password

# Windows PowerShell
$env:DB_USERNAME="root"
$env:DB_PASSWORD="your_password"
```

## Run the application

From the project root:
```bash
mvn clean spring-boot:run
```
Open **http://localhost:8080**.

To package as a WAR:
```bash
mvn clean package
```
The output is `target/student-management-system.war`.

## Main routes

| URL | Purpose |
|---|---|
| `/` | Dashboard |
| `/students` | Student directory and search |
| `/students/new` | Add student form |
| `/students/edit/{id}` | Edit student |
| `/students/save` | Save student (POST) |
| `/students/delete/{id}` | Delete student (POST) |

## Project structure

```text
src/main/
├── java/com/anitha/sms/
│   ├── StudentManagementApplication.java
│   ├── controller/StudentController.java
│   ├── entity/Student.java
│   ├── repository/StudentRepository.java
│   └── service/StudentService.java
├── resources/
│   ├── application.properties
│   └── static/css/style.css
└── webapp/WEB-INF/views/
    ├── dashboard.jsp
    ├── students.jsp
    ├── student-form.jsp
    └── fragments/
sql/schema.sql
```

## Validation rules

- Full name, department, course, email, phone, date of birth and gender are required.
- Email must have a valid format and must be unique.
- Phone number must contain exactly 10 digits.
- Date of birth must be in the past.

## Notes

- Hibernate creates/updates the `students` table at application startup (`ddl-auto=update`).
- JSP views are placed under `WEB-INF` so they cannot be requested directly.
- This is a portfolio/learning project. Before production use, add authentication/authorization, CSRF configuration review, pagination, centralized exception handling, automated integration tests, and secure secret management.
- The app uses Spring Boot's default security-free configuration; the hidden CSRF input in JSP templates is harmless only if Spring Security is later added and configured. If adding Spring Security, wire the CSRF token into the model using the security integration.

## Author

**Anitha**  
GitHub: [Anitha022051](https://github.com/Anitha022051)
