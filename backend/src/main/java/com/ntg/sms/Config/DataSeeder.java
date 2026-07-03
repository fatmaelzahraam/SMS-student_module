package com.ntg.sms.Config;

import com.ntg.sms.Entities.*;
import com.ntg.sms.Entities.Class;
import com.ntg.sms.Repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TeacherRepository teacherRepository;
    private final GradeRepository gradeRepository;
    private final TermRepository termRepository;
    private final CourseRepository courseRepository;
    private final AssignmentRepository assignmentRepository;
    private final ClassRepository classRepository;
    private final MarkRepository markRepository;
    private final MarksTypeRepository marksTypeRepository;
    private final ProjectRepository projectRepository;
    private final NotificationsReository notificationRepository;
    private final StudentRepository studentRepository;
    private final PermissionRepository permissionRepository;
    private final ViolationRepository violationRepository;

    @Override
    public void run(String... args) throws Exception {

        // ====================== ROLES ======================
        Role studentRole = roleRepository.findByRoleName("STUDENT").orElseGet(() -> {
            Role role = Role.builder().roleName("STUDENT").build();
            return roleRepository.save(role);
        });

        Role teacherRole = roleRepository.findByRoleName("TEACHER").orElseGet(() -> {
            Role role = Role.builder().roleName("TEACHER").build();
            return roleRepository.save(role);
        });

        Role adminRole = roleRepository.findByRoleName("ADMIN").orElseGet(() -> {
            Role role = Role.builder().roleName("ADMIN").build();
            return roleRepository.save(role);
        });

        if (userRepository.findByFirstName("Fatma Elzahraa").isEmpty()) {

            // ====================== USERS ======================
            User studentUser = User.builder()
                    .firstName("Fatma Elzahraa")
                    .lastName("Mohammed")
                    .email("fatma@school.edu")
                    .address("20, Cairo, Egypt")
                    .firstNameInArabic("فاطمة الزهراء")
                    .lastNameInArabic("محمد")
                    .password(passwordEncoder.encode("123456"))
                    .isDeleted(false)
                    .createdAt(LocalDateTime.now())
                    .lastLogin(LocalDateTime.now())
                    .gender('F')
                    .nationality("Egyptian")
                    .birthDate(LocalDate.of(2009, 11, 15))
                    .religion("Muslim")
                    .nationalNumber(30911150101074L)
                    .role(studentRole)
                    .build();
            userRepository.save(studentUser);

            User teacherUser = User.builder()
                    .firstName("Ahmed")
                    .lastName("Hassan")
                    .email("a.hassan@school.edu")
                    .address("15, Giza, Egypt")
                    .firstNameInArabic("أحمد")
                    .lastNameInArabic("حسن")
                    .password(passwordEncoder.encode("teacher123"))
                    .isDeleted(false)
                    .createdAt(LocalDateTime.now())
                    .lastLogin(LocalDateTime.now())
                    .gender('M')
                    .nationality("Egyptian")
                    .birthDate(LocalDate.of(1985, 3, 20))
                    .religion("Muslim")
                    .nationalNumber(28503200101055L)
                    .role(teacherRole)
                    .build();
            userRepository.save(teacherUser);

            User adminUser = User.builder()
                    .firstName("Sara")
                    .lastName("Ali")
                    .email("admin@school.edu")
                    .address("10, Alexandria, Egypt")
                    .firstNameInArabic("سارة")
                    .lastNameInArabic("علي")
                    .password(passwordEncoder.encode("admin123"))
                    .isDeleted(false)
                    .createdAt(LocalDateTime.now())
                    .lastLogin(LocalDateTime.now())
                    .gender('F')
                    .nationality("Egyptian")
                    .birthDate(LocalDate.of(1990, 6, 10))
                    .religion("Muslim")
                    .nationalNumber(29006100101022L)
                    .role(adminRole)
                    .build();
            userRepository.save(adminUser);

            // ====================== STUDENT ======================
            Student student = Student.builder()
                    .user(studentUser)
                    .governorate("Cairo")
                    .academicScoreInMiddleSchool(266L)
                    .placeOfBirth("Cairo")
                    .martialParentsStatus(Student.MartialParentsStatus.MARRIED)

                    .build();
            studentRepository.save(student);

            // ====================== TEACHER ======================
            Teacher teacher = new Teacher();
            teacher.setId(1L);
            teacher.setUser(teacherUser);
            teacher.setEducation("B.Sc. Computer Science");
            teacher.setEmploymentHistory("NTG School");
            teacher.setNumberOfYearsOfExperience(8L);
            teacherRepository.save(teacher);

            // ====================== TERM ======================
            Term firstTerm = new Term();
            firstTerm.setId(1L);
            firstTerm.setTerm(1L);
            firstTerm.setYear(2026L);
            termRepository.save(firstTerm);

            Term secondTerm = new Term();
            secondTerm.setId(2L);
            secondTerm.setTerm(2L);
            secondTerm.setYear(2026L);
            termRepository.save(secondTerm);

            // ====================== GRADE ======================
            Grade grade = new Grade();
            grade.setId(1L);
            grade.setName("Grade 11");
            grade.getTerms().add(firstTerm);
            grade.getTerms().add(secondTerm);
            gradeRepository.save(grade);

            // ====================== CLASS ======================
            Class classEntity = Class.builder()
                    .grade(grade)
                    .name("11-A")
                    .capacity(30L)
                    .build();
            classRepository.save(classEntity);

            Class classEntity2 = Class.builder()
                    .grade(grade)
                    .name("11-B")
                    .capacity(28L)
                    .build();
            classRepository.save(classEntity2);

            // ====================== ASSIGNMENTS ======================
            Assignment assignment1 = new Assignment();
            assignment1.setName("Java OOP Assignment");
            assignment1.setAssignDate(LocalDate.now());
            assignment1.setDeadline(LocalDate.now().plusDays(7));
            assignment1.setDescription("Implement a Library Management System.");
            assignment1.setFileLink("https://drive.google.com/java-oop");
            assignment1.setStudentSubmission("Pending");
            assignmentRepository.save(assignment1);

            Assignment assignment2 = new Assignment();
            assignment2.setName("Spring Boot API");
            assignment2.setAssignDate(LocalDate.now());
            assignment2.setDeadline(LocalDate.now().plusDays(10));
            assignment2.setDescription("Create CRUD APIs for Student Module.");
            assignment2.setFileLink("https://drive.google.com/spring-api");
            assignment2.setStudentSubmission("Pending");
            assignmentRepository.save(assignment2);

            Assignment assignment3 = new Assignment();
            assignment3.setName("Database Design");
            assignment3.setAssignDate(LocalDate.now());
            assignment3.setDeadline(LocalDate.now().plusDays(14));
            assignment3.setDescription("Design an ERD for a Hospital Management System.");
            assignment3.setFileLink("https://drive.google.com/db-design");
            assignment3.setStudentSubmission("Pending");
            assignmentRepository.save(assignment3);

            // ====================== COURSE ======================
            Course course = new Course();
            course.setId(1L);
            course.setTeacher(teacher);
            course.setTerm(firstTerm);
            course.setCourseName("Advanced Java");
            course.setCourseType("Theory");
            course.setDescription("Advanced Java Programming");
            course.setStudyPlan("Week 1-12");
            course.getAssignments().add(assignment1);
            course.getAssignments().add(assignment2);
            courseRepository.save(course);

            Course course2 = new Course();
            course2.setId(2L);
            course2.setTeacher(teacher);
            course2.setTerm(firstTerm);
            course2.setCourseName("Database Systems");
            course2.setCourseType("Practical");
            course2.setDescription("Relational Databases and SQL");
            course2.setStudyPlan("Week 1-10");
            course2.getAssignments().add(assignment3);
            courseRepository.save(course2);

            // ====================== MARKS TYPE ======================
            MarksType examType = MarksType.builder()
                    .id(1L)
                    .type("EXAM")
                    .build();
            marksTypeRepository.save(examType);

            MarksType quizType = MarksType.builder()
                    .id(2L)
                    .type("QUIZ")
                    .build();
            marksTypeRepository.save(quizType);

            MarksType assignmentType = MarksType.builder()
                    .id(3L)
                    .type("ASSIGNMENT")
                    .build();
            marksTypeRepository.save(assignmentType);

            // ====================== MARKS ======================
            Mark mark1 = new Mark();
            mark1.setId(1L);
            mark1.setCourse(course);
            mark1.setUser(studentUser);
            mark1.setType(examType);
            mark1.setFeedbackDate(LocalDate.now());
            mark1.setFeedback("Good performance overall.");
            mark1.setNotes("Needs improvement in OOP concepts.");
            mark1.setIsApproved(true);
            mark1.setScore(97L);
            mark1.setMaxScore(100L);
            markRepository.save(mark1);

            Mark mark2 = new Mark();
            mark2.setId(2L);
            mark2.setCourse(course);
            mark2.setUser(studentUser);
            mark2.setType(quizType);
            mark2.setFeedbackDate(LocalDate.now());
            mark2.setFeedback("Well done.");
            mark2.setNotes("Keep it up.");
            mark2.setIsApproved(true);
            mark2.setScore(18L);
            mark2.setMaxScore(20L);
            markRepository.save(mark2);

            Mark mark3 = new Mark();
            mark3.setId(3L);
            mark3.setCourse(course2);
            mark3.setUser(studentUser);
            mark3.setType(assignmentType);
            mark3.setFeedbackDate(LocalDate.now());
            mark3.setFeedback("Great ERD design.");
            mark3.setNotes("Minor naming issues.");
            mark3.setIsApproved(false);
            mark3.setScore(47L);
            mark3.setMaxScore(50L);
            markRepository.save(mark3);

            // ====================== PROJECT ======================
            Project project1 = new Project();
            project1.setId(1L);
            project1.setCourse(course);
            project1.setName("Library System");
            project1.setDescription("Full Java-based library management system.");
            project1.setAssignDate(LocalDate.now());
            project1.setDeadline(LocalDate.now().plusDays(30));
            projectRepository.save(project1);

            Project project2 = new Project();
            project2.setId(2L);
            project2.setCourse(course2);
            project2.setName("Hospital DB");
            project2.setDescription("Oracle DB schema for a hospital system.");
            project2.setAssignDate(LocalDate.now());
            project2.setDeadline(LocalDate.now().plusDays(21));
            projectRepository.save(project2);

            // ====================== NOTIFICATION ======================
            Notification notification1 = new Notification();
            notification1.setId(1L);
            notification1.setTitle("Exam Schedule Released");
            notification1.setType("ACADEMIC");
            notification1.setPriority("HIGH");
            notification1.setBody("The final exam schedule for Term 1 has been published. Please check the portal.");
            notification1.setSentAt(LocalDate.now());
            notificationRepository.save(notification1);

            Notification notification2 = new Notification();
            notification2.setId(2L);
            notification2.setTitle("Holiday Notice");
            notification2.setType("GENERAL");
            notification2.setPriority("MEDIUM");
            notification2.setBody("School will be closed on the 25th for the national holiday.");
            notification2.setSentAt(LocalDate.now());
            notificationRepository.save(notification2);

            Notification notification3 = new Notification();
            notification3.setId(3L);
            notification3.setTitle("Assignment Deadline Reminder");
            notification3.setType("ACADEMIC");
            notification3.setPriority("HIGH");
            notification3.setBody("Reminder: Java OOP Assignment is due in 2 days. Submit via the portal.");
            notification3.setSentAt(LocalDate.now());
            notificationRepository.save(notification3);

            // ====================== PERMISSION ======================
            Permission permission1 = new Permission();
            permission1.setStudent(student);
            permission1.setReason("Medical appointment");
            permission1.setNotes("Has a doctor's note.");
            permission1.setDate(LocalDate.now());
            permissionRepository.save(permission1);

            Permission permission2 = new Permission();
            permission2.setStudent(student);
            permission2.setReason("Family event");
            permission2.setNotes("Requested 2 days in advance.");
            permission2.setDate(LocalDate.now().plusDays(3));
            permissionRepository.save(permission2);

            // ====================== VIOLATION ======================
            Violation violation1 = new Violation();
            violation1.setStudent(student);
            violation1.setViolation("Late submission of assignment");
            violation1.setNameOfViolator("Fatma Elzahraa Mohammed");
            violation1.setApplicableProcedure("Written warning issued");
            violation1.setReferringAuthority("Class Teacher");
            violation1.setIsmeeting(0L);
            violation1.setNotes("First offense, student apologized.");
            violation1.setDate(LocalDate.now());
            violationRepository.save(violation1);

            Violation violation2 = new Violation();
            violation2.setStudent(student);
            violation2.setViolation("Disruptive behavior during class");
            violation2.setNameOfViolator("Fatma Elzahraa Mohammed");
            violation2.setApplicableProcedure("Parent meeting scheduled");
            violation2.setReferringAuthority("Vice Principal");
            violation2.setIsmeeting(1L);
            violation2.setNotes("Meeting set for next Monday.");
            violation2.setDate(LocalDate.now().minusDays(5));
            violationRepository.save(violation2);

        }
    }
}