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
import java.time.LocalTime;

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
    private final SessionRepository sessionRepository;
    private final AttendanceRepository attendanceRepository;
    private final UserNotificationRepository userNotificationRepository;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("=== DataSeeder running ===");

        if (userRepository.findByFirstName("Fatma Elzahraa").isEmpty()) {
            System.out.println("=== Seeding data ===");

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

            student.setStudentClass(classEntity);
            studentRepository.save(student);

            // ====================== ASSIGNMENTS ======================
            Assignment assignment1 = new Assignment();
            assignment1.setName("Java OOP Assignment");
            assignment1.setAssignDate(LocalDate.now());
            assignment1.setDeadline(LocalDate.now().plusDays(7));
            assignment1.setDescription("Implement a Library Management System.");
            assignment1.setFileLink("https://drive.google.com/java-oop");
            assignment1.setStudentSubmission("PENDING");
            assignmentRepository.save(assignment1);

            Assignment assignment2 = new Assignment();
            assignment2.setName("Spring Boot API");
            assignment2.setAssignDate(LocalDate.now());
            assignment2.setDeadline(LocalDate.now().plusDays(10));
            assignment2.setDescription("Create CRUD APIs for Student Module.");
            assignment2.setFileLink("https://drive.google.com/spring-api");
            assignment2.setStudentSubmission("PENDING");
            assignmentRepository.save(assignment2);

            Assignment assignment3 = new Assignment();
            assignment3.setName("Database Design");
            assignment3.setAssignDate(LocalDate.now());
            assignment3.setDeadline(LocalDate.now().plusDays(14));
            assignment3.setDescription("Design an ERD for a Hospital Management System.");
            assignment3.setFileLink("https://drive.google.com/db-design");
            assignment3.setStudentSubmission("PENDING");
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

            Course course3 = new Course();
            course3.setId(3L);
            course3.setTeacher(teacher);
            course3.setTerm(firstTerm);
            course3.setCourseName("Data Structures");
            course3.setCourseType("Theory");
            course3.setDescription("Study of linear and non-linear data structures");
            course3.setStudyPlan("Week 1-12");
            courseRepository.save(course3);

            Course course4 = new Course();
            course4.setId(4L);
            course4.setTeacher(teacher);
            course4.setTerm(firstTerm);
            course4.setCourseName("Operating Systems");
            course4.setCourseType("Theory");
            course4.setDescription("Concepts of modern operating systems");
            course4.setStudyPlan("Week 1-12");
            courseRepository.save(course4);

            Course course5 = new Course();
            course5.setId(5L);
            course5.setTeacher(teacher);
            course5.setTerm(firstTerm);
            course5.setCourseName("Computer Networks");
            course5.setCourseType("Theory");
            course5.setDescription("Network protocols and communication");
            course5.setStudyPlan("Week 1-12");
            courseRepository.save(course5);

            Course course6 = new Course();
            course6.setId(6L);
            course6.setTeacher(teacher);
            course6.setTerm(firstTerm);
            course6.setCourseName("Software Engineering");
            course6.setCourseType("Theory");
            course6.setDescription("Software development lifecycle and methodologies");
            course6.setStudyPlan("Week 1-12");
            courseRepository.save(course6);

            Course course7 = new Course();
            course7.setId(7L);
            course7.setTeacher(teacher);
            course7.setTerm(firstTerm);
            course7.setCourseName("Web Development");
            course7.setCourseType("Practical");
            course7.setDescription("Frontend and backend web development");
            course7.setStudyPlan("Week 1-10");
            courseRepository.save(course7);

            Course course8 = new Course();
            course8.setId(8L);
            course8.setTeacher(teacher);
            course8.setTerm(firstTerm);
            course8.setCourseName("Mobile Application Development");
            course8.setCourseType("Practical");
            course8.setDescription("Android application development");
            course8.setStudyPlan("Week 1-10");
            courseRepository.save(course8);

            Course course9 = new Course();
            course9.setId(9L);
            course9.setTeacher(teacher);
            course9.setTerm(firstTerm);
            course9.setCourseName("Artificial Intelligence");
            course9.setCourseType("Theory");
            course9.setDescription("Introduction to AI and machine learning");
            course9.setStudyPlan("Week 1-12");
            courseRepository.save(course9);

            // ====================== MARKS TYPE ======================
            MarksType examType = MarksType.builder()
                    .id(1L)
                    .typeName("EXAM")
                    .build();
            marksTypeRepository.save(examType);

            MarksType quizType = MarksType.builder()
                    .id(2L)
                    .typeName("HOME_WORK")
                    .build();
            marksTypeRepository.save(quizType);

            MarksType assignmentType = MarksType.builder()
                    .id(3L)
                    .typeName("ASSIGNMENT")
                    .build();
            marksTypeRepository.save(assignmentType);

            // ====================== MARKS ======================
            Mark mark1 = new Mark();
            mark1.setId(1L);
            mark1.setCourse(course);
            mark1.setStudent(student);
            mark1.setType(examType);
            mark1.setFeedbackDate(LocalDate.now().minusDays(20));
            mark1.setFeedback("Good performance overall.");
            mark1.setNotes("Needs improvement in OOP concepts.");
            mark1.setIsApproved(true);
            mark1.setScore(97L);
            mark1.setMaxScore(100L);
            markRepository.save(mark1);

            Mark mark2 = new Mark();
            mark2.setId(2L);
            mark2.setCourse(course);
            mark2.setStudent(student);
            mark2.setType(quizType);
            mark2.setFeedbackDate(LocalDate.now().minusDays(14));
            mark2.setFeedback("Well done.");
            mark2.setNotes("Keep it up.");
            mark2.setIsApproved(true);
            mark2.setScore(18L);
            mark2.setMaxScore(20L);
            markRepository.save(mark2);

            Mark mark3 = new Mark();
            mark3.setId(3L);
            mark3.setCourse(course2);
            mark3.setStudent(student);
            mark3.setType(assignmentType);
            mark3.setFeedbackDate(LocalDate.now().minusDays(7));
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

            // ====================== NOTIFICATIONS ======================
            Notification notification1 = notificationRepository.save(Notification.builder()
                    .id(1L).title("Exam Schedule Released").type("ACADEMIC")
                    .priority("HIGH").sentAt(LocalDate.now())
                    .body("The final exam schedule for Term 1 has been published. Please check the portal.")
                    .build());

            Notification notification2 = notificationRepository.save(Notification.builder()
                    .id(2L).title("Holiday Notice").type("GENERAL")
                    .priority("MEDIUM").sentAt(LocalDate.now().minusDays(1))
                    .body("School will be closed on the 25th for the national holiday.")
                    .build());

            Notification notification3 = notificationRepository.save(Notification.builder()
                    .id(3L).title("Assignment Deadline Reminder").type("ACADEMIC")
                    .priority("HIGH").sentAt(LocalDate.now().minusDays(2))
                    .body("Reminder: Java OOP Assignment is due in 2 days. Submit via the portal.")
                    .build());

            System.out.println("=== Notifications saved: " + notificationRepository.count() + " ===");

            // ====================== USER NOTIFICATIONS ======================

            UserNotificationId unId1 = new UserNotificationId();
            unId1.setUserId(adminUser.getId());
            unId1.setNotificationId(notification1.getId());
            unId1.setSentTo(studentUser.getId());
            UserNotification un1 = new UserNotification();
            un1.setId(unId1);
            un1.setUser(adminUser);
            un1.setNotification(notification1);
            un1.setSentTo(studentUser);
            userNotificationRepository.save(un1);

            UserNotificationId unId2 = new UserNotificationId();
            unId2.setUserId(adminUser.getId());
            unId2.setNotificationId(notification2.getId());
            unId2.setSentTo(studentUser.getId());
            UserNotification un2 = new UserNotification();
            un2.setId(unId2);
            un2.setUser(adminUser);
            un2.setNotification(notification2);
            un2.setSentTo(studentUser);
            userNotificationRepository.save(un2);

            UserNotificationId unId3 = new UserNotificationId();
            unId3.setUserId(adminUser.getId());
            unId3.setNotificationId(notification3.getId());
            unId3.setSentTo(studentUser.getId());
            UserNotification un3 = new UserNotification();
            un3.setId(unId3);
            un3.setUser(adminUser);
            un3.setNotification(notification3);
            un3.setSentTo(studentUser);
            userNotificationRepository.save(un3);

            System.out.println("=== UserNotifications saved: " + userNotificationRepository.count() + " ===");


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

            // ====================== SESSIONS (CLASS SCHEDULE) ======================
            // dayOfWeek: 1=Sunday, 2=Monday, 3=Tuesday, 4=Wednesday, 5=Thursday

            // ====================== SESSIONS (CLASS SCHEDULE) ======================
// dayOfWeek: 1=Sunday, 2=Monday, 3=Tuesday, 4=Wednesday, 5=Thursday, 6=Friday, 7=Saturday

// ====================== SUNDAY ======================
            Session s1 = new Session(); s1.setClassField(classEntity); s1.setCourse(course);  s1.setDayOfWeek(1L); s1.setStartAt(LocalTime.of(8,0));  s1.setEndAt(LocalTime.of(8,50));  s1.setSessionType(Session.SessionType.CLASS); s1.setUpdatedAt(LocalDate.now()); sessionRepository.save(s1);
            Session s2 = new Session(); s2.setClassField(classEntity); s2.setCourse(course2); s2.setDayOfWeek(1L); s2.setStartAt(LocalTime.of(8,50)); s2.setEndAt(LocalTime.of(9,40));  s2.setSessionType(Session.SessionType.CLASS); s2.setUpdatedAt(LocalDate.now()); sessionRepository.save(s2);
            Session s3 = new Session(); s3.setClassField(classEntity); s3.setCourse(course);  s3.setDayOfWeek(1L); s3.setStartAt(LocalTime.of(9,40)); s3.setEndAt(LocalTime.of(10,30)); s3.setSessionType(Session.SessionType.CLASS); s3.setUpdatedAt(LocalDate.now()); sessionRepository.save(s3);
            Session s4 = new Session(); s4.setClassField(classEntity); s4.setCourse(course2); s4.setDayOfWeek(1L); s4.setStartAt(LocalTime.of(10,30)); s4.setEndAt(LocalTime.of(11,20)); s4.setSessionType(Session.SessionType.CLASS); s4.setUpdatedAt(LocalDate.now()); sessionRepository.save(s4);
            Session s5 = new Session(); s5.setClassField(classEntity); s5.setCourse(course);  s5.setDayOfWeek(1L); s5.setStartAt(LocalTime.of(11,20)); s5.setEndAt(LocalTime.of(12,10)); s5.setSessionType(Session.SessionType.CLASS); s5.setUpdatedAt(LocalDate.now()); sessionRepository.save(s5);
            Session s6 = new Session(); s6.setClassField(classEntity); s6.setCourse(course2); s6.setDayOfWeek(1L); s6.setStartAt(LocalTime.of(12,10)); s6.setEndAt(LocalTime.of(13,0));  s6.setSessionType(Session.SessionType.CLASS); s6.setUpdatedAt(LocalDate.now()); sessionRepository.save(s6);
            Session s7 = new Session(); s7.setClassField(classEntity); s7.setCourse(course);  s7.setDayOfWeek(1L); s7.setStartAt(LocalTime.of(13,0));  s7.setEndAt(LocalTime.of(13,50)); s7.setSessionType(Session.SessionType.CLASS); s7.setUpdatedAt(LocalDate.now()); sessionRepository.save(s7);
            Session s8 = new Session(); s8.setClassField(classEntity); s8.setCourse(course2); s8.setDayOfWeek(1L); s8.setStartAt(LocalTime.of(13,50)); s8.setEndAt(LocalTime.of(14,40)); s8.setSessionType(Session.SessionType.CLASS); s8.setUpdatedAt(LocalDate.now()); sessionRepository.save(s8);


// ====================== MONDAY ======================
            Session s9 = new Session(); s9.setClassField(classEntity); s9.setCourse(course2); s9.setDayOfWeek(2L); s9.setStartAt(LocalTime.of(8,0)); s9.setEndAt(LocalTime.of(8,50)); s9.setSessionType(Session.SessionType.CLASS); s9.setUpdatedAt(LocalDate.now()); sessionRepository.save(s9);
            Session s10 = new Session(); s10.setClassField(classEntity); s10.setCourse(course); s10.setDayOfWeek(2L); s10.setStartAt(LocalTime.of(8,50)); s10.setEndAt(LocalTime.of(9,40)); s10.setSessionType(Session.SessionType.CLASS); s10.setUpdatedAt(LocalDate.now()); sessionRepository.save(s10);
            Session s11 = new Session(); s11.setClassField(classEntity); s11.setCourse(course2); s11.setDayOfWeek(2L); s11.setStartAt(LocalTime.of(9,40)); s11.setEndAt(LocalTime.of(10,30)); s11.setSessionType(Session.SessionType.CLASS); s11.setUpdatedAt(LocalDate.now()); sessionRepository.save(s11);
            Session s12 = new Session(); s12.setClassField(classEntity); s12.setCourse(course); s12.setDayOfWeek(2L); s12.setStartAt(LocalTime.of(10,30)); s12.setEndAt(LocalTime.of(11,20)); s12.setSessionType(Session.SessionType.CLASS); s12.setUpdatedAt(LocalDate.now()); sessionRepository.save(s12);
            Session s13 = new Session(); s13.setClassField(classEntity); s13.setCourse(course2); s13.setDayOfWeek(2L); s13.setStartAt(LocalTime.of(11,20)); s13.setEndAt(LocalTime.of(12,10)); s13.setSessionType(Session.SessionType.CLASS); s13.setUpdatedAt(LocalDate.now()); sessionRepository.save(s13);
            Session s14 = new Session(); s14.setClassField(classEntity); s14.setCourse(course); s14.setDayOfWeek(2L); s14.setStartAt(LocalTime.of(12,10)); s14.setEndAt(LocalTime.of(13,0)); s14.setSessionType(Session.SessionType.CLASS); s14.setUpdatedAt(LocalDate.now()); sessionRepository.save(s14);
            Session s15 = new Session(); s15.setClassField(classEntity); s15.setCourse(course2); s15.setDayOfWeek(2L); s15.setStartAt(LocalTime.of(13,0)); s15.setEndAt(LocalTime.of(13,50)); s15.setSessionType(Session.SessionType.CLASS); s15.setUpdatedAt(LocalDate.now()); sessionRepository.save(s15);
            Session s16 = new Session(); s16.setClassField(classEntity); s16.setCourse(course); s16.setDayOfWeek(2L); s16.setStartAt(LocalTime.of(13,50)); s16.setEndAt(LocalTime.of(14,40)); s16.setSessionType(Session.SessionType.CLASS); s16.setUpdatedAt(LocalDate.now()); sessionRepository.save(s16);

            // ====================== TUESDAY ======================
            Session s17 = new Session(); s17.setClassField(classEntity); s17.setCourse(course);  s17.setDayOfWeek(3L); s17.setStartAt(LocalTime.of(8,0)); s17.setEndAt(LocalTime.of(8,50)); s17.setSessionType(Session.SessionType.CLASS); s17.setUpdatedAt(LocalDate.now()); sessionRepository.save(s17);
            Session s18 = new Session(); s18.setClassField(classEntity); s18.setCourse(course2); s18.setDayOfWeek(3L); s18.setStartAt(LocalTime.of(8,50)); s18.setEndAt(LocalTime.of(9,40)); s18.setSessionType(Session.SessionType.CLASS); s18.setUpdatedAt(LocalDate.now()); sessionRepository.save(s18);
            Session s19 = new Session(); s19.setClassField(classEntity); s19.setCourse(course);  s19.setDayOfWeek(3L); s19.setStartAt(LocalTime.of(9,40)); s19.setEndAt(LocalTime.of(10,30)); s19.setSessionType(Session.SessionType.CLASS); s19.setUpdatedAt(LocalDate.now()); sessionRepository.save(s19);
            Session s20 = new Session(); s20.setClassField(classEntity); s20.setCourse(course2); s20.setDayOfWeek(3L); s20.setStartAt(LocalTime.of(10,30)); s20.setEndAt(LocalTime.of(11,20)); s20.setSessionType(Session.SessionType.CLASS); s20.setUpdatedAt(LocalDate.now()); sessionRepository.save(s20);
            Session s21 = new Session(); s21.setClassField(classEntity); s21.setCourse(course);  s21.setDayOfWeek(3L); s21.setStartAt(LocalTime.of(11,20)); s21.setEndAt(LocalTime.of(12,10)); s21.setSessionType(Session.SessionType.CLASS); s21.setUpdatedAt(LocalDate.now()); sessionRepository.save(s21);
            Session s22 = new Session(); s22.setClassField(classEntity); s22.setCourse(course2); s22.setDayOfWeek(3L); s22.setStartAt(LocalTime.of(12,10)); s22.setEndAt(LocalTime.of(13,0)); s22.setSessionType(Session.SessionType.CLASS); s22.setUpdatedAt(LocalDate.now()); sessionRepository.save(s22);
            Session s23 = new Session(); s23.setClassField(classEntity); s23.setCourse(course);  s23.setDayOfWeek(3L); s23.setStartAt(LocalTime.of(13,0)); s23.setEndAt(LocalTime.of(13,50)); s23.setSessionType(Session.SessionType.CLASS); s23.setUpdatedAt(LocalDate.now()); sessionRepository.save(s23);
            Session s24 = new Session(); s24.setClassField(classEntity); s24.setCourse(course2); s24.setDayOfWeek(3L); s24.setStartAt(LocalTime.of(13,50)); s24.setEndAt(LocalTime.of(14,40)); s24.setSessionType(Session.SessionType.CLASS); s24.setUpdatedAt(LocalDate.now()); sessionRepository.save(s24);


            // ====================== WEDNESDAY ======================
            Session s25 = new Session(); s25.setClassField(classEntity); s25.setCourse(course2); s25.setDayOfWeek(4L); s25.setStartAt(LocalTime.of(8,0)); s25.setEndAt(LocalTime.of(8,50)); s25.setSessionType(Session.SessionType.CLASS); s25.setUpdatedAt(LocalDate.now()); sessionRepository.save(s25);
            Session s26 = new Session(); s26.setClassField(classEntity); s26.setCourse(course); s26.setDayOfWeek(4L); s26.setStartAt(LocalTime.of(8,50)); s26.setEndAt(LocalTime.of(9,40)); s26.setSessionType(Session.SessionType.CLASS); s26.setUpdatedAt(LocalDate.now()); sessionRepository.save(s26);
            Session s27 = new Session(); s27.setClassField(classEntity); s27.setCourse(course2); s27.setDayOfWeek(4L); s27.setStartAt(LocalTime.of(9,40)); s27.setEndAt(LocalTime.of(10,30)); s27.setSessionType(Session.SessionType.CLASS); s27.setUpdatedAt(LocalDate.now()); sessionRepository.save(s27);
            Session s28 = new Session(); s28.setClassField(classEntity); s28.setCourse(course); s28.setDayOfWeek(4L); s28.setStartAt(LocalTime.of(10,30)); s28.setEndAt(LocalTime.of(11,20)); s28.setSessionType(Session.SessionType.CLASS); s28.setUpdatedAt(LocalDate.now()); sessionRepository.save(s28);
            Session s29 = new Session(); s29.setClassField(classEntity); s29.setCourse(course2); s29.setDayOfWeek(4L); s29.setStartAt(LocalTime.of(11,20)); s29.setEndAt(LocalTime.of(12,10)); s29.setSessionType(Session.SessionType.CLASS); s29.setUpdatedAt(LocalDate.now()); sessionRepository.save(s29);
            Session s30 = new Session(); s30.setClassField(classEntity); s30.setCourse(course); s30.setDayOfWeek(4L); s30.setStartAt(LocalTime.of(12,10)); s30.setEndAt(LocalTime.of(13,0)); s30.setSessionType(Session.SessionType.CLASS); s30.setUpdatedAt(LocalDate.now()); sessionRepository.save(s30);
            Session s31 = new Session(); s31.setClassField(classEntity); s31.setCourse(course2); s31.setDayOfWeek(4L); s31.setStartAt(LocalTime.of(13,0)); s31.setEndAt(LocalTime.of(13,50)); s31.setSessionType(Session.SessionType.CLASS); s31.setUpdatedAt(LocalDate.now()); sessionRepository.save(s31);
            Session s32 = new Session(); s32.setClassField(classEntity); s32.setCourse(course); s32.setDayOfWeek(4L); s32.setStartAt(LocalTime.of(13,50)); s32.setEndAt(LocalTime.of(14,40)); s32.setSessionType(Session.SessionType.CLASS); s32.setUpdatedAt(LocalDate.now()); sessionRepository.save(s32);

            // ====================== THURSDAY ======================
            Session s33 = new Session(); s33.setClassField(classEntity); s33.setCourse(course);  s33.setDayOfWeek(5L); s33.setStartAt(LocalTime.of(8,0));   s33.setEndAt(LocalTime.of(8,50));  s33.setSessionType(Session.SessionType.CLASS); s33.setUpdatedAt(LocalDate.now()); sessionRepository.save(s33);
            Session s34 = new Session(); s34.setClassField(classEntity); s34.setCourse(course2); s34.setDayOfWeek(5L); s34.setStartAt(LocalTime.of(8,50));  s34.setEndAt(LocalTime.of(9,40));  s34.setSessionType(Session.SessionType.CLASS); s34.setUpdatedAt(LocalDate.now()); sessionRepository.save(s34);
            Session s35 = new Session(); s35.setClassField(classEntity); s35.setCourse(course);  s35.setDayOfWeek(5L); s35.setStartAt(LocalTime.of(9,40));  s35.setEndAt(LocalTime.of(10,30)); s35.setSessionType(Session.SessionType.CLASS); s35.setUpdatedAt(LocalDate.now()); sessionRepository.save(s35);
            Session s36 = new Session(); s36.setClassField(classEntity); s36.setCourse(course2); s36.setDayOfWeek(5L); s36.setStartAt(LocalTime.of(10,30)); s36.setEndAt(LocalTime.of(11,20)); s36.setSessionType(Session.SessionType.CLASS); s36.setUpdatedAt(LocalDate.now()); sessionRepository.save(s36);
            Session s37 = new Session(); s37.setClassField(classEntity); s37.setCourse(course);  s37.setDayOfWeek(5L); s37.setStartAt(LocalTime.of(11,20)); s37.setEndAt(LocalTime.of(12,10)); s37.setSessionType(Session.SessionType.CLASS); s37.setUpdatedAt(LocalDate.now()); sessionRepository.save(s37);
            Session s38 = new Session(); s38.setClassField(classEntity); s38.setCourse(course2); s38.setDayOfWeek(5L); s38.setStartAt(LocalTime.of(12,10)); s38.setEndAt(LocalTime.of(13,0));  s38.setSessionType(Session.SessionType.CLASS); s38.setUpdatedAt(LocalDate.now()); sessionRepository.save(s38);
            Session s39 = new Session(); s39.setClassField(classEntity); s39.setCourse(course);  s39.setDayOfWeek(5L); s39.setStartAt(LocalTime.of(13,0));  s39.setEndAt(LocalTime.of(13,50)); s39.setSessionType(Session.SessionType.CLASS); s39.setUpdatedAt(LocalDate.now()); sessionRepository.save(s39);
            Session s40 = new Session(); s40.setClassField(classEntity); s40.setCourse(course2); s40.setDayOfWeek(5L); s40.setStartAt(LocalTime.of(13,50)); s40.setEndAt(LocalTime.of(14,40)); s40.setSessionType(Session.SessionType.CLASS); s40.setUpdatedAt(LocalDate.now()); sessionRepository.save(s40);
            // ====================== SESSIONS (MONTH EXAMS) ======================
            Session monthExam1 = new Session(); monthExam1.setClassField(classEntity); monthExam1.setCourse(course);  monthExam1.setDayOfWeek(1L); monthExam1.setStartAt(LocalTime.of(9, 0)); monthExam1.setEndAt(LocalTime.of(11, 0)); monthExam1.setSessionType(Session.SessionType.MONTH_EXAM); monthExam1.setUpdatedAt(LocalDate.now()); sessionRepository.save(monthExam1);
            Session monthExam2 = new Session(); monthExam2.setClassField(classEntity); monthExam2.setCourse(course2); monthExam2.setDayOfWeek(3L); monthExam2.setStartAt(LocalTime.of(9, 0)); monthExam2.setEndAt(LocalTime.of(11, 0)); monthExam2.setSessionType(Session.SessionType.MONTH_EXAM); monthExam2.setUpdatedAt(LocalDate.now()); sessionRepository.save(monthExam2);

            // ====================== SESSIONS (FINAL EXAMS) ======================
            Session finalExam1 = new Session(); finalExam1.setClassField(classEntity); finalExam1.setCourse(course);  finalExam1.setDayOfWeek(2L); finalExam1.setStartAt(LocalTime.of(9, 0)); finalExam1.setEndAt(LocalTime.of(12, 0)); finalExam1.setSessionType(Session.SessionType.FINAL_EXAM); finalExam1.setUpdatedAt(LocalDate.now()); sessionRepository.save(finalExam1);
            Session finalExam2 = new Session(); finalExam2.setClassField(classEntity); finalExam2.setCourse(course2); finalExam2.setDayOfWeek(4L); finalExam2.setStartAt(LocalTime.of(9, 0)); finalExam2.setEndAt(LocalTime.of(12, 0)); finalExam2.setSessionType(Session.SessionType.FINAL_EXAM); finalExam2.setUpdatedAt(LocalDate.now()); sessionRepository.save(finalExam2);

            System.out.println("=== Sessions saved: " + sessionRepository.count() + " ===");

            // ====================== ATTENDANCE ======================
            // 30 records across the last 30 days — realistic mix of P / A / L
            // Each day has 2 sessions (course + course2), except absences and lates

            LocalDateTime base = LocalDateTime.now().minusDays(30).withHour(8).withMinute(0);

            char[] statuses = {
                    'P','P','P','P','P',   // week 1 — all present
                    'P','P','L','P','P',   // week 2 — one late
                    'P','A','P','P','P',   // week 3 — one absent
                    'P','P','P','L','P',   // week 4 — one late
                    'P','P','P','P','P',   // week 5 — all present
                    'P','P','A','P','P',   // week 6 — one absent
            };

            Session[] sessions = { s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11 };

            for (int i = 0; i < statuses.length; i++) {
                Session session = sessions[i % sessions.length];
                Attendance attendance = Attendance.builder()
                        .student(student)
                        .session(session)
                        .status(statuses[i])
                        .dateTime(base.plusDays(i))
                        .build();
                attendanceRepository.save(attendance);
            }

            System.out.println("=== Attendance saved: " + attendanceRepository.count() + " ===");

        } else {
            System.out.println("=== Data already exists, skipping ===");
        }
    }
}