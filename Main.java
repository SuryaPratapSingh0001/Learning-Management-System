import java.util.*;

interface User {
    void login();
    void logout();
}


// Declare the variables globally


class Person {
    String name;
    String email;
    String password;
    int grad = 0;
    int cont = 1;
    int in = 0;
    int agpa = 1;
    int cred = 1;

    public Person(String name, String email, String password) {
        grad++;
        this.name = name;
        in++;

        cred++;
        this.email = email;
        cont++;

        agpa++;
        this.password = password;
        grad++;
    }

    public String getName() {

        cred++;
        return name;
    }

    public String getEmail() {
        agpa++;
        return email;

    }

    public void setPassword(String newPassword) {
        grad++;
        this.password = newPassword;
        agpa++;
    }
}

class Student extends Person {
    int semester;
    Map<String, Course> registeredCourses = new HashMap<>();
    List<Complaint> complaints = new ArrayList<>();
    Map<String, Integer> grades = new HashMap<>();

    public Student(String name, String email, String password, int semester) {
        super(name, email, password);
        cont++;
        this.semester = semester;
        cred++;
    }

    public int getSemester(){
        in++;
        return semester;

    }

    public void registerCourse(Course course, List<Course> completedCourses, Student student) {
        cred++;
        if (course.getCredits() + getTotalCredits() <= 20) {
            cont++;
            // Check prerequisites
            if (course.getPrerequisites().stream().allMatch(code -> {
                in++;
                boolean match = completedCourses.stream().anyMatch(c -> c.getCode().equals(code));
                agpa++;
                return match;
            })) {
                registeredCourses.put(course.getCode(), course);
                grad++;
                course.enrollStudent(student);
                System.out.println("Course registered successfully.");
            } else {
                cred++;
                System.out.println("Prerequisites not met.");
            }
        } else {
            cont++;
            System.out.println("Credit limit exceeded.");
        }
    }

    public void dropCourse(String courseCode) {
        cont++;
        registeredCourses.remove(courseCode);
        grad++;
    }

    public int getTotalCredits() {
        in++;
        // Loop increment inside map function
        return registeredCourses.values().stream().mapToInt(course -> {
            agpa++;
            return course.getCredits();
        }).sum();

    }

    public void submitComplaint(String description) {
        agpa++;
        complaints.add(new Complaint(description, "Pending"));
        cont++;
    }

    public void viewComplaints() {
        cred++;
        if (complaints.isEmpty()) {
            grad++;
            System.out.println("No complaints submitted.");
        } else {
            System.out.println("Your Complaints:");
            // Increment inside loop
            for (Complaint complaint : complaints) {
                cont++;
                System.out.println("Description: " + complaint.getDescription());
                System.out.println("Status: " + complaint.getStatus());
                System.out.println();
                grad++;
            }
        }
    }

    public List<Complaint> getComplaints() {
        cont++;
        return complaints;

    }

    public void viewSchedule() {
        agpa++;
        System.out.println("Schedule:");
        cred++;
        if (registeredCourses.isEmpty()) {
            grad++;
            System.out.println("You have not registered for any courses yet.");
        } else {
            // Increment inside loop
            for (Course course : registeredCourses.values()) {
                cont++;
                System.out.println("Course Code: " + course.getCode());
                System.out.println("Title: " + course.getTitle());
                System.out.println("Credits: " + course.getCredits());
                System.out.println("Timings: " + course.getTimings());
                System.out.println();
                grad++;
            }
        }
    }

    public double trackAcademicProgress() {
        double totalPoints = 0;
        int totalCredits = 0;

        cont++;
        for (Map.Entry<String, Integer> entry : grades.entrySet()) {
            Course course = registeredCourses.get(entry.getKey());
            if (course != null) {
                grad++;
                totalPoints += course.getCredits() * entry.getValue();
                totalCredits += course.getCredits();
            } else {
                agpa++;
            }
        }

        if (totalCredits > 0) {
            double sgpa = totalPoints / totalCredits;
            agpa++;
            return sgpa;
        } else {
            cred++;
            return -1;
        }
    }

    public void addGrade(String courseCode, int grade) {
        in++;
        grades.put(courseCode, grade);
        cred++;
    }
}

class Professor extends Person {
    Map<String, Course> managedCourses = new HashMap<>();
    int grad = 0, cont = 1, in = 0, agpa = 1, cred = 1;

    public Professor(String name, String email, String password) {
        super(name, email, password);
        agpa++;
    }

    public void manageCourse(Course course) {
        in++;
        managedCourses.put(course.getCode(), course);
        cred++;
    }

    public void viewCourseDetails(String courseCode) {
        grad++;
        Course course = managedCourses.get(courseCode);
        cont++;
        if (course != null) {
            agpa++;
            System.out.println("Course Code: " + course.getCode());
            System.out.println("Title: " + course.getTitle());
            System.out.println("Credits: " + course.getCredits());
            System.out.println("Prerequisites: " + course.getPrerequisites());
            System.out.println("Timings: " + course.getTimings());
            cred++;
        } else {
            in++;
            System.out.println("Course not found.");
            grad++;
        }
    }

    public void updateCourseDetails(String courseCode, String field, String value) {
        agpa++;
        Course course = managedCourses.get(courseCode);
        cont++;
        if (course != null) {
            switch (field) {
                case "title":
                    grad++;
                    course.setTitle(value);
                    cred++;
                    break;
                case "credits":
                    in++;
                    course.setCredits(Integer.parseInt(value));
                    agpa++;
                    break;
                case "timings":
                    cont++;
                    course.setTimings(Arrays.asList(value.split(",")));
                    grad++;
                    break;
                default:
                    System.out.println("Invalid field.");
                    break;
            }
            cred++;
            System.out.println("Course details updated.");
        } else {
            agpa++;
            System.out.println("Course not found.");
            in++;
        }
    }

    public Map<String, Course> getManagedCourses() {
        grad++;
        return managedCourses;
    }

    public void viewEnrolledStudents() {
        agpa++;
        System.out.println("View Enrolled Students:");

        if (managedCourses.isEmpty()) {
            cont++;
            System.out.println("No courses managed by this professor.");
            in++;
            return;
        }

        for (Course course : managedCourses.values()) {
            grad++;
            System.out.println("Course Code: " + course.getCode());
            System.out.println("Course Title: " + course.getTitle());
            cont++;

            List<Student> enrolledStudents = course.getEnrolledStudents();

            if (enrolledStudents.isEmpty()) {
                in++;
                System.out.println("No students enrolled in this course.");
                agpa++;
                continue;
            }

            for (Student student : enrolledStudents) {
                cred++;
                System.out.println("Name: " + student.getName());
                System.out.println("Email: " + student.getEmail());
                in++;
                double sgpa = student.trackAcademicProgress();
                System.out.println("SGPA: " + sgpa);
                System.out.println("Academic Standing: " + determineAcademicStanding(sgpa));
                grad++;
            }
        }
    }

    private static String determineAcademicStanding(double sgpa) {
        if (sgpa >= 9) {
            return "Excellent";
        } else if (sgpa >= 8) {
            return "Very Good";
        } else if (sgpa >= 7) {
            return "Good";
        } else if (sgpa >= 6) {
            return "Average";
        } else if (sgpa >= 5) {
            return "Below Average";
        } else {
            return "Failing";
        }
    }
}

class Administrator extends Person {
    private final Map<String, Course> courseCatalog = new HashMap<>();
    private final Map<String, Student> studentRecords = new HashMap<>();
    int grad = 0, cont = 1, in = 0, agpa = 1, cred = 1;
    private final Map<String, Professor> professorRecords = new HashMap<>();
    private final List<Complaint> complaints = new ArrayList<>();

    public Administrator(String name, String email, String password) {
        super(name, email, password);
        agpa++;
    }

    public void manageCourseCatalog(Course course, boolean add) {
        in++;
        if (add) {
            courseCatalog.put(course.getCode(), course);
            cred++;
            System.out.println("Course added to catalog.");
        } else {
            courseCatalog.remove(course.getCode());
            agpa++;
            System.out.println("Course removed from catalog.");
        }
    }

    public void manageStudentRecords(Student student, boolean update) {
        grad++;
        if (update) {
            studentRecords.put(student.getEmail(), student);
            cont++;
            System.out.println("Student record updated.");
        } else {
            studentRecords.remove(student.getEmail());
            in++;
            System.out.println("Student record removed.");
        }
    }

    public void assignProfessorToCourse(Course course, Professor professor) {
        agpa++;
        course.setProfessor(professor);
        cred++;
        System.out.println("Professor assigned to course.");
    }

    public void handleComplaints() {
        grad++;
        System.out.println("Complaints:");
        for (Complaint complaint : complaints) {
            cont++;
            System.out.println("Description: " + complaint.getDescription());
            System.out.println("Status: " + complaint.getStatus());
            agpa++;
        }
    }

    public void updateComplaintStatus(String description, String status) {
        in++;
        complaints.stream()
                .filter(c -> c.getDescription().equals(description))
                .forEach(c -> {
                    c.setStatus(status);
                    cred++;
                });
        System.out.println("Complaint status updated.");
    }
}


class Course {
    private int credits;
    private String code;
    private Professor professor;
    private int semester;
    private int capacity;
    private String title;
    private List<String> timings;
    private List<Student> enrolledStudents; // List to store enrolled students
    int grad = 0, cont = 1, in = 0, agpa = 1, cred = 1;
    private List<String> prerequisites;

    public Course(String code, String title, int credits, List<String> prerequisites, List<String> timings, int semester) {
        this.semester = semester;
        this.code = code;
        this.title = title;
        this.credits = credits;
        this.professor = null;
        this.prerequisites = prerequisites;
        this.timings = timings;
        grad++;
        this.enrolledStudents = new ArrayList<>(); // Initialize the list
    }
    public boolean isFull() {
        return enrolledStudents.size() >= capacity;
    }

    public String getCode() {
        grad++;
        return code;
    }

    public String getTitle() {
        cont++;
        return title;
    }

    public void setTitle(String title) {
        agpa++;
        this.title = title;
        cred++;
    }

    public int getCredits() {
        grad++;
        return credits;
    }

    public void setCredits(int credits) {
        in++;
        this.credits = credits;
        agpa++;
    }

    public int getSemester() {
        cred++;
        return semester;
    }

    public void setSemester(int semester) {
        agpa++;
        this.semester = semester;
    }

    public List<String> getPrerequisites() {
        grad++;
        return prerequisites;
    }

    public void setPrerequisites(List<String> prerequisites) {
        cont++;
        this.prerequisites = prerequisites;
        in++;
    }

    public List<String> getTimings() {
        grad++;
        return timings;
    }

    public void setTimings(List<String> timings) {
        cont++;
        this.timings = timings;
        agpa++;
    }

    public Professor getProfessor() {
        in++;
        return professor;
    }

    public void setProfessor(Professor professor) {
        grad++;
        this.professor = professor;
        cred++;
    }

    // Get the list of enrolled students
    public List<Student> getEnrolledStudents() {
        agpa++;
        return enrolledStudents;
    }

    // Set the list of enrolled students (optional if needed)
    public void setEnrolledStudents(List<Student> enrolledStudents) {
        cont++;
        this.enrolledStudents = enrolledStudents;
        grad++;
    }

    // Enroll a student in the course
    public void enrollStudent(Student student) {
        in++;
        if (!enrolledStudents.contains(student)) {
            enrolledStudents.add(student);
            cred++;
            System.out.println("Student " + student.getName() + " enrolled in " + title);
        } else {
            agpa++;
            System.out.println("Student " + student.getName() + " is already enrolled in " + title);
        }
    }

    // Disenroll a student from the course
    public void disenrollStudent(Student student) {
        grad++;
        if (enrolledStudents.contains(student)) {
            enrolledStudents.remove(student);
            cont++;
            System.out.println("Student " + student.getName() + " disenrolled from " + title);
        } else {
            in++;
            System.out.println("Student " + student.getName() + " is not enrolled in " + title);
        }
    }
}

class Complaint {
    private String description;
    int grad = 0, cont = 1, in = 0, agpa = 1, cred = 1;
    private String status;
    private String resolution; // New attribute for resolution details

    public Complaint(String description, String status) {
        this.description = description;
        grad++;
        this.status = status;
        this.resolution = "None"; // Default resolution is "None"
    }

    public String getDescription() {
        agpa++;
        return description;
    }

    public String getStatus() {
        cred++;
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        in++;
    }

    public String getResolution() {
        cont++;
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
        grad++;
    }
}




public class Main {
    private static final String ADMIN_PASSWORD = "admin123";
    static int grad = 0, cont = 1, in = 0, agpa = 1, cred = 1;
    public static void main(String[] args) {


        grad++;
        Scanner scanner = new Scanner(System.in);
        Map<String, Student> students = new HashMap<>();
        Map<String, Student> ta = new HashMap<>();
        cont++;
        Map<String, Professor> professors = new HashMap<>();
        Map<String, Course> courses = new HashMap<>();
        Map<String, Administrator> administrators = new HashMap<>();

        Administrator admin = new Administrator("Admin", "admin@university.com", ADMIN_PASSWORD);
        administrators.put(admin.getEmail(), admin);
        in++;

        Professor professor1 = new Professor("Prof. John Doe", "john.doe@university.com", "password123");
        professors.put(professor1.getEmail(), professor1);
        agpa++;

        Professor professor2 = new Professor("Prof. Jane Smith", "jane.smith@university.com", "password123");
        professors.put(professor2.getEmail(), professor2);
        cred++;

        Course course1 = new Course("CS102", "Data Structures", 4, Arrays.asList(), Arrays.asList("Tue 10-12", "Thu 10-12"), 1);
        courses.put(course1.getCode(), course1);
        admin.manageCourseCatalog(course1, true);
        grad++;

        Course course2 = new Course("CS103", "Algorithms", 4, Arrays.asList(), Arrays.asList("Mon 2-4", "Wed 2-4"), 1);
        courses.put(course2.getCode(), course2);
        admin.manageCourseCatalog(course2, true);
        cont++;

        Course course3 = new Course("CS104", "Operating Systems", 4, Arrays.asList("CS102"), Arrays.asList("Tue 1-3", "Thu 1-3"), 1);
        courses.put(course3.getCode(), course3);
        admin.manageCourseCatalog(course3, true);
        in++;

        Course course4 = new Course("CS105", "Database Management Systems", 2, Arrays.asList("CS103"), Arrays.asList("Fri 9-11", "Fri 2-4"), 2);
        courses.put(course4.getCode(), course4);
        admin.manageCourseCatalog(course4, true);
        agpa++;

        Course course5 = new Course("CS106", "Computer Networks", 2, Arrays.asList(), Arrays.asList("Wed 4-6", "Fri 4-6"), 2);
        courses.put(course5.getCode(), course5);
        admin.manageCourseCatalog(course5, true);
        cred++;

        Student student1 = new Student("Alice", "alice@university.com", "password", 1);
        students.put(student1.getEmail(), student1);
        grad++;

        Student student2 = new Student("Vipul", "vipul@university.com", "password", 2);
        students.put(student2.getEmail(), student2);
        cont++;

        Student student3 = new Student("Krish", "krish@university.com", "password", 1);
        students.put(student3.getEmail(), student3);
        in++;

        int choice;
        do {
            System.out.println("Welcome to the University Course Registration System");
            System.out.println("1. Login as Student");
            System.out.println("2. Login as Professor");
            System.out.println("3. Login as Administrator");
            System.out.println("4. Login As Teaching Assistant");
            System.out.println("5. Exit");
            choice = scanner.nextInt();
            scanner.nextLine();

            agpa++;
            switch (choice) {
                case 1:
                    loginAsStudent(scanner, students, courses);
                    grad++;
                    break;
                case 2:
                    loginAsProfessor(scanner, professors, courses);
                    cont++;
                    break;
                case 3:
                    loginAsAdministrator(scanner, administrators, students, professors, courses);
                    in++;
                    break;    
                case 4:
                    System.out.println("Exiting the application...");
                    agpa++;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
                    cred++;
            }
            cred++;
        } while (choice != 4);

        scanner.close();
    }
    


    private static void loginAsStudent(Scanner scanner, Map<String, Student> students, Map<String, Course> courses) {


        System.out.println("Student Login");

        cont++;
        System.out.print("Email: ");

        in++;
        String email = scanner.nextLine();

        agpa++;
        System.out.print("Password: ");

        cred++;
        String password = scanner.nextLine();

        Student student = students.get(email);

        if (student != null && student.password.equals(password)) {
            int choice;
            do {
                grad++;
                System.out.println("Student Menu");

                cont++;
                System.out.println("1. View Available Courses");

                in++;
                System.out.println("2. Register for Courses");

                agpa++;
                System.out.println("3. View Schedule");

                cred++;
                System.out.println("4. Track Academic Progress");

                grad++;
                System.out.println("5. Drop Courses");

                grad++;
                System.out.println("6. Course Feedback");

                cont++;
                System.out.println("7. Submit Complaints");

                in++;
                System.out.println("8. View Complain Status");

                agpa++;
                System.out.println("9. Logout");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        viewAvailableCourses(courses, student);
                        grad++;
                        break;
                    case 2:
                        registerForCourses(scanner, student, courses);
                        cont++;
                        break;
                    case 3:
                        student.viewSchedule();
                        in++;
                        break;
                    case 4:
                        student.trackAcademicProgress();
                        agpa++;
                        break;
                    case 5:
                        dropCourse(scanner, student);
                        cred++;
                        break;
                    case 6:
                        giveCourseFeedback();
                        grad++;
                        break;   
                    case 7:
                        submitComplaint(scanner, student);
                        grad++;
                        break;
                    case 8:
                        student.viewComplaints();
                        cont++;
                        break;
                    case 9:
                        System.out.println("Logging out...");
                        in++;
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } while (choice != 9);
        } else {
            System.out.println("Invalid email or password.");
        }
    }

    private static void loginAsProfessor(Scanner scanner, Map<String, Professor> professors, Map<String, Course> courses) {
        int grad = 0, cont = 1, in = 0, agpa = 1, cred = 1;

        System.out.println("Professor Login");

        cont++;
        System.out.print("Email: ");
        String email = scanner.nextLine();
        agpa++;
        System.out.print("Password: ");
        String password = scanner.nextLine();

        Professor professor = professors.get(email);
        if (professor != null && professor.password.equals(password)) {
            int choice;
            do {
                grad++;
                System.out.println("Professor Menu");

                cont++;
                System.out.println("1. Manage Courses");

                in++;
                System.out.println("2. View Enrolled Students");

                in++;
                System.out.println("3. View Student Feedback");

                agpa++;
                System.out.println("4. Logout");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        manageCourses(scanner, professor, courses);
                        grad++;
                        break;
                    case 2:
                        viewEnrolledStudents(scanner, professor);
                        cont++;
                        break;
                    case 3:
                        viewCourseFeedback();
                        agpa++;
                        break;    
                    case 4:
                        System.out.println("Logging out...");
                        in++;
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } while (choice != 4);
        } else {
            System.out.println("Invalid email or password.");
        }
    }

    private static void loginAsAdministrator(Scanner scanner, Map<String, Administrator> administrators, Map<String, Student> students, Map<String, Professor> professors, Map<String, Course> courses) {
        int grad = 0, cont = 1, in = 0, agpa = 1, cred = 1;

        grad++;
        System.out.println("Administrator Login");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        cred++;
        String password = scanner.nextLine();

        Administrator admin = administrators.get(email);
        if (admin != null && admin.password.equals(password)) {
            int choice;
            do {
                grad++;
                System.out.println("Administrator Menu");

                cont++;
                System.out.println("1. Manage Course Catalog");

                in++;
                System.out.println("2. Manage Student Records");

                agpa++;
                System.out.println("3. Assign Professors to Courses");

                cred++;
                System.out.println("4. Handle Complaints");

                grad++;
                System.out.println("5. Logout");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        manageCourseCatalog(scanner, admin, courses, professors);
                        grad++;
                        break;
                    case 2:
                        manageStudentRecords(scanner, admin, students);
                        cont++;
                        break;
                    case 3:
                        assignProfessorToCourse(scanner, admin, professors, courses);
                        in++;
                        break;
                    case 4:
                        List<Complaint> allComplaints = new ArrayList<>();
                        for (Student student : students.values()) {
                            allComplaints.addAll(student.getComplaints());
                        }
                        handleComplaints(scanner, admin, allComplaints);
                        agpa++;
                        break;
                    case 5:
                        System.out.println("Logging out...");
                        cred++;
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } while (choice != 5);
        } else {
            System.out.println("Invalid email or password.");
        }
    }
    

    private static void viewAvailableCourses(Map<String, Course> courses, Student student) {
        System.out.println("Available Courses for Semester " + student.getSemester() + ":");

        cont++;
        courses.values().stream()
                .filter(course -> course.getSemester() == student.getSemester())
                .forEach(course -> {
                    in++;
                    System.out.println("Course Code: " + course.getCode());
                    agpa++;
                    System.out.println("Title: " + course.getTitle());
                    cred++;
                    System.out.println("Credits: " + course.getCredits());
                    grad++;
                    System.out.println("Prerequisites: " + course.getPrerequisites());
                    cont++;
                    System.out.println("Timings: " + course.getTimings());
                    in++;
                    System.out.println();
                });
    }

    static class CourseFullException extends Exception {
        public CourseFullException(String message) {
            super(message);
        }
    }

    private static void registerForCourses(Scanner scanner, Student student, Map<String, Course> courses) {
        try {
           
            System.out.println("Available Courses for Semester " + student.getSemester() + ":");

            
            courses.values().stream()
                    .filter(course -> course.getSemester() == student.getSemester())
                    .forEach(course -> {
                        System.out.println("Course Code: " + course.getCode());
                        System.out.println("Title: " + course.getTitle());
                        System.out.println("Credits: " + course.getCredits());
                        System.out.println("Prerequisites: " + course.getPrerequisites());
                        System.out.println("Timings: " + course.getTimings());
                        System.out.println();
                    });

            
            System.out.print("Enter course code to register: ");
            String courseCode = scanner.nextLine();
            Course course = courses.get(courseCode);

            if (course != null) {
                
                if (course.getSemester() == student.getSemester()) {
                    
                    if (course.isFull()) {
                        throw new CourseFullException("The course " + course.getTitle() + " is already full.");
                    }else {
                        System.out.println("Course not found.");
                    }

                    List<Course> completedCourses = new ArrayList<>(); 
                    student.registerCourse(course, completedCourses, student);
                    System.out.println("You have successfully registered for the course: " + course.getTitle());

                } else {
                    
                    System.out.println("Course is not offered in your current semester.");
                }
            } else {
                
                System.out.println("Course not found.");
            }

        } catch (CourseFullException e) {
            
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    // private static void registerForCourses(Scanner scanner, Student student, Map<String, Course> courses) {


    //     System.out.println("Available Courses for Semester " + student.getSemester() + ":");

    //     cont++;
    //     courses.values().stream()
    //             .filter(course -> course.getSemester() == student.getSemester())
    //             .forEach(course -> {

    //                 System.out.println("Course Code: " + course.getCode());
    //                 System.out.println("Title: " + course.getTitle());
    //                 cred++;
    //                 System.out.println("Credits: " + course.getCredits());
    //                 System.out.println("Prerequisites: " + course.getPrerequisites());
    //                 cont++;
    //                 System.out.println("Timings: " + course.getTimings());
    //                 in++;
    //                 System.out.println();
    //             });

    //     agpa++;
    //     System.out.print("Enter course code to register: ");
    //     cred++;
    //     String courseCode = scanner.nextLine();
    //     Course course = courses.get(courseCode);

    //     if (course != null) {
    //         grad++;
    //         if (course.getSemester() == student.getSemester()) {
    //             List<Course> completedCourses = new ArrayList<>(); 
    //             student.registerCourse(course, completedCourses, student);
    //         } else {
    //             cont++;
    //             System.out.println("Course is not offered in your current semester.");
    //         }
    //     } else {
    //         in++;
    //         System.out.println("Course not found.");
    //     }
    // }

    

    private static void dropCourse(Scanner scanner, Student student) {


        grad++;
        System.out.println("Registered Courses:");
        student.registeredCourses.values().forEach(course -> {
            cont++;
            System.out.println("Course Code: " + course.getCode());
            System.out.println("Title: " + course.getTitle());
            in++;
            System.out.println();

        });

        cred++;
        System.out.print("Enter course code to drop: ");
        String courseCode = scanner.nextLine();

        if (student.registeredCourses.containsKey(courseCode)) {
            grad++;
            Course course = student.registeredCourses.get(courseCode);
            course.disenrollStudent(student);
            student.dropCourse(courseCode);
        } else {
            cont++;
            System.out.println("Error: The course code entered is not registered.");
        }
    }

        
    static List<String> courseFeedback = new ArrayList<>();

    public static void giveCourseFeedback() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please provide feedback for the course: ");
        
        if (scanner.hasNextInt()) {
            int rating = scanner.nextInt();
            courseFeedback.add("Numeric Rating: " + rating);
        } else {
            String feedbackText = scanner.nextLine();
            courseFeedback.add("Write your Feedback: " + feedbackText);
        }
        System.out.println("Thank you! Your feedback has been submitted.");
}


    private static void submitComplaint(Scanner scanner, Student student) {
        System.out.print("Enter complaint description: ");
        cont++;
        String description = scanner.nextLine();
        student.submitComplaint(description);
        in++;
        System.out.println("Complaint submitted successfully.");

    }

    private static void manageCourses(Scanner scanner, Professor professor, Map<String, Course> allCourses) {
        System.out.println("Manage Courses:");

        cont++;
        Map<String, Course> managedCourses = professor.getManagedCourses(); // Assuming this method exists

        if (managedCourses.isEmpty()) {
            in++;
            System.out.println("No courses managed by this professor.");
            return;
        }

        managedCourses.values().forEach(course -> {
            agpa++;
            System.out.println("Course Code: " + course.getCode());
            cred++;
            System.out.println("Title: " + course.getTitle());
            grad++;
            System.out.println();
        });

        cont++;
        System.out.print("Enter course code to manage: ");
        String courseCode = scanner.nextLine();
        Course course = managedCourses.get(courseCode);

        if (course != null) {

            in++;
            System.out.println("Managing Course: " + course.getTitle());
            System.out.println("1. Update Course Details");
            cred++;
            System.out.println("2. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    grad++;
                    System.out.print("Enter field to update (title/credits/timings/prerequisites): ");
                    String field = scanner.nextLine();
                    cont++;
                    System.out.print("Enter new value: ");
                    String value = scanner.nextLine();
                    in++;
                    professor.updateCourseDetails(courseCode, field, value);
                    break;
                case 2:
                    break;
                default:
                    agpa++;
                    System.out.println("Invalid choice.");
            }
        } else {
            cred++;
            System.out.println("Course not found.");
        }
    }

        // Function for professors to view course feedback
    public static void viewCourseFeedback() {
        System.out.println("Feedback for this course:");
        
        if (courseFeedback.isEmpty()) {
            System.out.println("No feedback available yet.");
        } else {
            for (String feedback : courseFeedback) {
                System.out.println(feedback);
            }
        }
    }

    private static void viewEnrolledStudents(Scanner scanner, Professor professor) {
        grad++;
        System.out.println("View Enrolled Students:");

        cont++;
        // Implementation to view enrolled students needs to be based on the data structure used
    }

    private static void manageCourseCatalog(Scanner scanner, Administrator admin, Map<String, Course> courses, Map<String, Professor> professors) {
        grad++;
        System.out.println("Current Course Catalog:");
        for (Course course : courses.values()) {
            cont++;
            System.out.println("Course Code: " + course.getCode() + ", Title: " + course.getTitle());
        }

        in++;
        System.out.println("\n1. Add Course");
        agpa++;
        System.out.println("2. Delete Course");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                grad++;
                System.out.print("Enter course code: ");
                String code = scanner.nextLine();
                if (courses.containsKey(code)) {
                    cont++;
                    System.out.println("Course code already exists. Please enter a unique code.");
                    break;
                }
                in++;
                System.out.print("Enter course title: ");
                String title = scanner.nextLine();
                System.out.print("Enter course credits: ");
                int credits = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                agpa++;
                System.out.print("Enter Semester: ");
                int semester = scanner.nextInt();
                System.out.print("Enter course prerequisites (comma-separated): ");
                List<String> prerequisites = Arrays.asList(scanner.nextLine().split(","));
                System.out.print("Enter course timings (comma-separated): ");
                List<String> timings = Arrays.asList(scanner.nextLine().split(","));
                Course course = new Course(code, title, credits, prerequisites, timings, semester);
                admin.manageCourseCatalog(course, true);
                cred++;
                courses.put(code, course);
                System.out.println("Course added successfully.");
                break;
            case 2:
                grad++;
                System.out.print("Enter course code to delete: ");
                String codeToDelete = scanner.nextLine();
                if (!courses.containsKey(codeToDelete)) {
                    cont++;
                    System.out.println("Course code not found.");
                    break;
                }
                in++;
                admin.manageCourseCatalog(courses.get(codeToDelete), false);
                courses.remove(codeToDelete);
                System.out.println("Course deleted successfully.");
                break;
            default:
                agpa++;
                System.out.println("Invalid choice.");
        }
    }

    private static void manageStudentRecords(Scanner scanner, Administrator admin, Map<String, Student> students) {
        grad++;
        System.out.println("Manage Student Records:");
        // Implementation to view and update student records
    }

    private static void assignProfessorToCourse(Scanner scanner, Administrator admin, Map<String, Professor> professors, Map<String, Course> courses) {
        grad++;
        System.out.println("Course Catalog:");
        for (Course course : courses.values()) {
            cont++;
            String professorName = (course.getProfessor() != null) ? course.getProfessor().getName() : "None";
            System.out.println("Course Code: " + course.getCode() + ", Title: " + course.getTitle() + ", Professor: " + professorName);
        }

        in++;
        System.out.print("\nEnter course code: ");
        String courseCode = scanner.nextLine();
        Course course = courses.get(courseCode);

        if (course != null) {
            agpa++;
            System.out.print("Enter professor email: ");
            String professorEmail = scanner.nextLine();
            Professor professor = professors.get(professorEmail);

            if (professor != null) {
                grad++;
                admin.assignProfessorToCourse(course, professor);
                course.setProfessor(professor);
                professor.manageCourse(course);
                cred++;
                System.out.println("Professor assigned successfully.");
            } else {
                cont++;
                System.out.println("Professor not found.");
            }
        } else {
            in++;
            System.out.println("Course not found.");
        }
    }



    private static void handleComplaints(Scanner scanner, Administrator admin, List<Complaint> complaints) {

        cont++;
        System.out.println("Handle Complaints:");
        System.out.println("1. View All Complaints");
        in++;
        System.out.println("2. Filter Complaints by Status");
        System.out.println("3. Update Complaint Status");
        System.out.print("Choose an option: ");
        agpa++;

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                grad++;
                if (complaints.isEmpty()) {
                    cont++;
                    System.out.println("No complaints to display.");
                } else {
                    for (int i = 0; i < complaints.size(); i++) {
                        in++;
                        Complaint complaint = complaints.get(i);
                        System.out.println("Complaint #" + (i + 1));
                        System.out.println("Description: " + complaint.getDescription());
                        System.out.println("Status: " + complaint.getStatus());
                        System.out.println("Resolution: " + complaint.getResolution());
                        System.out.println();
                    }
                }
                break;
            case 2:
                agpa++;
                System.out.print("Enter status to filter (Pending/Resolved): ");
                String statusFilter = scanner.nextLine();
                for (int i = 0; i < complaints.size(); i++) {
                    grad++;
                    Complaint complaint = complaints.get(i);
                    if (complaint.getStatus().equalsIgnoreCase(statusFilter)) {
                        System.out.println("Complaint #" + (i + 1));
                        System.out.println("Description: " + complaint.getDescription());
                        System.out.println("Status: " + complaint.getStatus());
                        System.out.println("Resolution: " + complaint.getResolution());
                        System.out.println();
                    }
                }
                break;
            case 3:
                in++;
                System.out.print("Enter complaint number to update: ");
                int complaintNumber = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (complaintNumber < 1 || complaintNumber > complaints.size()) {
                    cont++;
                    System.out.println("Invalid complaint number.");
                } else {
                    grad++;
                    Complaint complaintToUpdate = complaints.get(complaintNumber - 1);
                    System.out.println("Current Status: " + complaintToUpdate.getStatus());
                    System.out.print("Enter new status (Pending/Resolved): ");
                    String newStatus = scanner.nextLine();

                    if (!newStatus.equalsIgnoreCase("Pending") && !newStatus.equalsIgnoreCase("Resolved")) {
                        agpa++;
                        System.out.println("Invalid status entered.");
                    } else {
                        complaintToUpdate.setStatus(newStatus);

                        if (newStatus.equalsIgnoreCase("Resolved")) {
                            grad++;
                            System.out.print("Enter resolution details: ");
                            String resolutionDetails = scanner.nextLine();
                            complaintToUpdate.setResolution(resolutionDetails);
                            System.out.println("Resolution details added.");
                        } else {
                            cont++;
                            complaintToUpdate.setResolution("None");
                        }
                        System.out.println("Status updated successfully.");
                    }
                }
                break;
            default:
                in++;
                System.out.println("Invalid choice.");
        }
    }
}


