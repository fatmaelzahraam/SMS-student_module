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
    private final UserPhoneNumberRepository userPhoneNumberRepository;

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
                    .birthDate(LocalDate.of(2008, 4, 7))
                    .religion("Muslim")
                    .nationalNumber(30911150101074L)
                    .role(studentRole)
                    .build();
            userRepository.save(studentUser);

            User studentUser2 = User.builder()
                    .firstName("Bassmala")
                    .lastName("Ashraf")
                    .email("bosy@school.edu")
                    .address("20, Cairo, Egypt")
                    .firstNameInArabic("بسملة")
                    .lastNameInArabic("اشرف")
                    .password(passwordEncoder.encode("bosy1234"))
                    .isDeleted(false)
                    .createdAt(LocalDateTime.now())
                    .lastLogin(LocalDateTime.now())
                    .gender('F')
                    .nationality("Egyptian")
                    .birthDate(LocalDate.of(2007, 11, 1))
                    .religion("Muslim")
                    .nationalNumber(30911150101111L)
                    .role(studentRole)
                    .build();
            userRepository.save(studentUser2);

            User studentUser3 = User.builder()
                    .firstName("salma")
                    .lastName("Tamer")
                    .email("salma@school.edu")
                    .address("20, Cairo, Egypt")
                    .firstNameInArabic("سلمى")
                    .lastNameInArabic("تامر")
                    .password(passwordEncoder.encode("soso1234"))
                    .isDeleted(false)
                    .createdAt(LocalDateTime.now())
                    .lastLogin(LocalDateTime.now())
                    .gender('F')
                    .nationality("Egyptian")
                    .birthDate(LocalDate.of(2009, 8, 31))
                    .religion("Muslim")
                    .nationalNumber(30911150101318L)
                    .role(studentRole)
                    .build();
            userRepository.save(studentUser3);

            User studentUser4 = User.builder()
                    .firstName("Loaa")
                    .lastName("Walid")
                    .email("loaa@school.edu")
                    .address("20, Cairo, Egypt")
                    .firstNameInArabic("لؤا")
                    .lastNameInArabic("وليد")
                    .password(passwordEncoder.encode("loaa1234"))
                    .isDeleted(false)
                    .createdAt(LocalDateTime.now())
                    .lastLogin(LocalDateTime.now())
                    .gender('F')
                    .nationality("Egyptian")
                    .birthDate(LocalDate.of(2008, 3, 9))
                    .religion("Muslim")
                    .nationalNumber(30911150101039L)
                    .role(studentRole)
                    .build();
            userRepository.save(studentUser4);

            User studentUser5 = User.builder()
                    .firstName("Jana")
                    .lastName("Mohammed")
                    .email("janaMo@school.edu")
                    .address("20, Cairo, Egypt")
                    .firstNameInArabic("جنى")
                    .lastNameInArabic("محمد")
                    .password(passwordEncoder.encode("jeejee1234"))
                    .isDeleted(false)
                    .createdAt(LocalDateTime.now())
                    .lastLogin(LocalDateTime.now())
                    .gender('F')
                    .nationality("Egyptian")
                    .birthDate(LocalDate.of(2010, 3, 10))
                    .religion("Muslim")
                    .nationalNumber(30911150101039L)
                    .role(studentRole)
                    .build();
            userRepository.save(studentUser5);

            User teacherUser = User.builder()
                    .firstName("Safwat")
                    .lastName("Ahmed")
                    .email("safwat.ahmed@school.edu")
                    .address("15, Cairo, Egypt")
                    .firstNameInArabic("صفوت")
                    .lastNameInArabic("أحمد")
                    .password(passwordEncoder.encode("safwat123"))
                    .isDeleted(false)
                    .createdAt(LocalDateTime.now())
                    .lastLogin(LocalDateTime.now())
                    .gender('M')
                    .nationality("Egyptian")
                    .birthDate(LocalDate.of(2003, 1, 3))
                    .religion("Muslim")
                    .nationalNumber(28503200181055L)
                    .role(teacherRole)
                    .build();
            userRepository.save(teacherUser);

            User teacherUser1 = User.builder()
                    .firstName("Hala")
                    .lastName("Nageh")
                    .email("halanaheh@school.edu")
                    .address("15, Cairo, Egypt")
                    .firstNameInArabic("هالة")
                    .lastNameInArabic("ناجح")
                    .password(passwordEncoder.encode("halaN12349"))
                    .isDeleted(false)
                    .createdAt(LocalDateTime.now())
                    .lastLogin(LocalDateTime.now())
                    .gender('F')
                    .nationality("Egyptian")
                    .birthDate(LocalDate.of(1995, 5, 14))
                    .religion("Muslim")
                    .nationalNumber(28503200101655L)
                    .role(teacherRole)
                    .build();
            userRepository.save(teacherUser1);

            User teacherUser2 = User.builder()
                    .firstName("Hala")
                    .lastName("Mohamed")
                    .email("halaMo@school.edu")
                    .address("15, Cairo, Egypt")
                    .firstNameInArabic("هالة")
                    .lastNameInArabic("ناجح")
                    .password(passwordEncoder.encode("halaMo1234"))
                    .isDeleted(false)
                    .createdAt(LocalDateTime.now())
                    .lastLogin(LocalDateTime.now())
                    .gender('F')
                    .nationality("Egyptian")
                    .birthDate(LocalDate.of(1987, 5, 14))
                    .religion("Muslim")
                    .nationalNumber(29503200101055L)
                    .role(teacherRole)
                    .build();
            userRepository.save(teacherUser2);

            User teacherUser3 = User.builder()
                    .firstName("Nouran")
                    .lastName("Yasser")
                    .email("nouranyasser@school.edu")
                    .address("15, Cairo, Egypt")
                    .firstNameInArabic("نوران")
                    .lastNameInArabic("ياسر")
                    .password(passwordEncoder.encode("nouryy1234"))
                    .isDeleted(false)
                    .createdAt(LocalDateTime.now())
                    .lastLogin(LocalDateTime.now())
                    .gender('F')
                    .nationality("Egyptian")
                    .birthDate(LocalDate.of(2003, 1, 31))
                    .religion("Muslim")
                    .nationalNumber(28503201101055L)
                    .role(teacherRole)
                    .build();
            userRepository.save(teacherUser3);

            User teacherUser4 = User.builder()
                    .firstName("Essraa")
                    .lastName("Essmat")
                    .email("essraae@school.edu")
                    .address("15, Cairo, Egypt")
                    .firstNameInArabic("اسراء")
                    .lastNameInArabic("عصمت")
                    .password(passwordEncoder.encode("essraa1234"))
                    .isDeleted(false)
                    .createdAt(LocalDateTime.now())
                    .lastLogin(LocalDateTime.now())
                    .gender('F')
                    .nationality("Egyptian")
                    .birthDate(LocalDate.of(2000, 12, 22))
                    .religion("Muslim")
                    .nationalNumber(28503500101055L)
                    .role(teacherRole)
                    .build();
            userRepository.save(teacherUser4);

            User teacherUser5 = User.builder()
                    .firstName("Rasha")
                    .lastName("Moammed")
                    .email("rashamo@school.edu")
                    .address("15, Cairo, Egypt")
                    .firstNameInArabic("رشا")
                    .lastNameInArabic("محمد")
                    .password(passwordEncoder.encode("rasha1234"))
                    .isDeleted(false)
                    .createdAt(LocalDateTime.now())
                    .lastLogin(LocalDateTime.now())
                    .gender('F')
                    .nationality("Egyptian")
                    .birthDate(LocalDate.of(1985, 12, 22))
                    .religion("Muslim")
                    .nationalNumber(28503500101055L)
                    .role(teacherRole)
                    .build();
            userRepository.save(teacherUser5);

            User teacherUser6 = User.builder()
                    .firstName("Khaled")
                    .lastName("Moammed")
                    .email("khaled@school.edu")
                    .address("15, Cairo, Egypt")
                    .firstNameInArabic("خالد")
                    .lastNameInArabic("محمد")
                    .password(passwordEncoder.encode("khaled1234"))
                    .isDeleted(false)
                    .createdAt(LocalDateTime.now())
                    .lastLogin(LocalDateTime.now())
                    .gender('M')
                    .nationality("Egyptian")
                    .birthDate(LocalDate.of(1975, 12, 22))
                    .religion("Muslim")
                    .nationalNumber(28503500101055L)
                    .role(teacherRole)
                    .build();
            userRepository.save(teacherUser6);

            User teacherUser7 = User.builder()
                    .firstName("Wael")
                    .lastName("Moammed")
                    .email("waelmo@school.edu")
                    .address("15, Cairo, Egypt")
                    .firstNameInArabic("وائل")
                    .lastNameInArabic("محمد")
                    .password(passwordEncoder.encode("wael1234"))
                    .isDeleted(false)
                    .createdAt(LocalDateTime.now())
                    .lastLogin(LocalDateTime.now())
                    .gender('F')
                    .nationality("Egyptian")
                    .birthDate(LocalDate.of(1980, 12, 22))
                    .religion("Muslim")
                    .nationalNumber(28503500101055L)
                    .role(teacherRole)
                    .build();
            userRepository.save(teacherUser7);

            User teacherUser8 = User.builder()
                    .firstName("Sayed")
                    .lastName("Moammed")
                    .email("sayedmo@school.edu")
                    .address("15, Cairo, Egypt")
                    .firstNameInArabic("سيد")
                    .lastNameInArabic("محمد")
                    .password(passwordEncoder.encode("sayed1234"))
                    .isDeleted(false)
                    .createdAt(LocalDateTime.now())
                    .lastLogin(LocalDateTime.now())
                    .gender('F')
                    .nationality("Egyptian")
                    .birthDate(LocalDate.of(1995, 12, 22))
                    .religion("Muslim")
                    .nationalNumber(28503500101055L)
                    .role(teacherRole)
                    .build();
            userRepository.save(teacherUser8);

            User adminUser = User.builder()
                    .firstName("Basma")
                    .lastName("Hedra")
                    .email("admin@school.edu")
                    .address("10, Cairo, Egypt")
                    .firstNameInArabic("بسمة")
                    .lastNameInArabic("هدرة")
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

            Student student2 = Student.builder()
                    .user(studentUser2)
                    .governorate("Cairo")
                    .academicScoreInMiddleSchool(269L)
                    .placeOfBirth("Cairo")
                    .martialParentsStatus(Student.MartialParentsStatus.MARRIED)
                    .build();
            studentRepository.save(student2);

            Student student3 = Student.builder()
                    .user(studentUser3)
                    .governorate("Cairo")
                    .academicScoreInMiddleSchool(265L)
                    .placeOfBirth("Cairo")
                    .martialParentsStatus(Student.MartialParentsStatus.DIVORCED)
                    .build();
            studentRepository.save(student3);

            Student student4 = Student.builder()
                    .user(studentUser4)
                    .governorate("Cairo")
                    .academicScoreInMiddleSchool(260L)
                    .placeOfBirth("Cairo")
                    .martialParentsStatus(Student.MartialParentsStatus.MARRIED)
                    .build();
            studentRepository.save(student4);

            Student student5 = Student.builder()
                    .user(studentUser5)
                    .governorate("Cairo")
                    .academicScoreInMiddleSchool(270L)
                    .placeOfBirth("Cairo")
                    .martialParentsStatus(Student.MartialParentsStatus.MARRIED)
                    .build();
            studentRepository.save(student5);

            // ====================== TEACHER ======================
            Teacher teacher = new Teacher();
            teacher.setUser(teacherUser);
            teacher.setEducation("B.Sc. Computer Science");
            teacher.setEmploymentHistory("NTG School");
            teacher.setNumberOfYearsOfExperience(1L);
            teacherRepository.save(teacher);

            Teacher teacher1 = new Teacher();
            teacher1.setUser(teacherUser1);
            teacher1.setEducation("B.Sc. Computer Science");
            teacher1.setEmploymentHistory("NTG School");
            teacher1.setNumberOfYearsOfExperience(3L);
            teacherRepository.save(teacher1);

            Teacher teacher2 = new Teacher();
            teacher2.setUser(teacherUser2);
            teacher2.setEducation("B.Sc. Computer Science");
            teacher2.setEmploymentHistory("NTG School");
            teacher2.setNumberOfYearsOfExperience(4L);
            teacherRepository.save(teacher2);

            Teacher teacher3 = new Teacher();
            teacher3.setUser(teacherUser3);
            teacher3.setEducation("B.Sc. Computer Science");
            teacher3.setEmploymentHistory("NTG School");
            teacher3.setNumberOfYearsOfExperience(3L);
            teacherRepository.save(teacher3);

            Teacher teacher4 = new Teacher();
            teacher4.setUser(teacherUser4);
            teacher4.setEducation("B.Sc. Computer Science");
            teacher4.setEmploymentHistory("NTG School");
            teacher4.setNumberOfYearsOfExperience(1L);
            teacherRepository.save(teacher4);

            Teacher teacher5 = new Teacher();
            teacher5.setUser(teacherUser5);
            teacher5.setEducation("Math");
            teacher5.setEmploymentHistory("NTG School");
            teacher5.setNumberOfYearsOfExperience(1L);
            teacherRepository.save(teacher5);

            Teacher teacher6 = new Teacher();
            teacher6.setUser(teacherUser6);
            teacher6.setEducation("Arabic");
            teacher6.setEmploymentHistory("NTG School");
            teacher6.setNumberOfYearsOfExperience(10L);
            teacherRepository.save(teacher6);

            Teacher teacher7 = new Teacher();
            teacher7.setUser(teacherUser7);
            teacher7.setEducation("English");
            teacher7.setEmploymentHistory("NTG School");
            teacher7.setNumberOfYearsOfExperience(10L);
            teacherRepository.save(teacher7);

            Teacher teacher8 = new Teacher();
            teacher8.setUser(teacherUser8);
            teacher8.setEducation("Physics");
            teacher8.setEmploymentHistory("NTG School");
            teacher8.setNumberOfYearsOfExperience(1L);
            teacherRepository.save(teacher8);
            // ====================== TERM ======================
            Term firstTerm = new Term();
            firstTerm.setTerm(1L);
            firstTerm.setYear(2026L);
            termRepository.save(firstTerm);

            Term secondTerm = new Term();
            secondTerm.setTerm(2L);
            secondTerm.setYear(2026L);
            termRepository.save(secondTerm);

            // ====================== GRADE ======================
            Grade grade11 = new Grade();
            grade11.setName("Grade 11");
            grade11.getTerms().add(firstTerm);
            grade11.getTerms().add(secondTerm);
            gradeRepository.save(grade11);

            Grade grade12 = new Grade();
            grade12.setName("Grade 12");
            grade12.getTerms().add(firstTerm);
            grade12.getTerms().add(secondTerm);
            gradeRepository.save(grade12);

            Grade grade10 = new Grade();
            grade10.setName("Grade 10");
            grade10.getTerms().add(firstTerm);
            grade10.getTerms().add(secondTerm);
            gradeRepository.save(grade10);

            // ====================== PREVIOUS TERMS (2025 — Grade 11 year) ======================
            Term prevTerm1 = new Term();
            prevTerm1.setTerm(1L);
            prevTerm1.setYear(2025L);
            termRepository.save(prevTerm1);

            Term prevTerm2 = new Term();
            prevTerm2.setTerm(2L);
            prevTerm2.setYear(2025L);
            termRepository.save(prevTerm2);

            // Attach previous terms to Grade 11 (students were in Grade 11 in 2025)
            grade11.getTerms().add(prevTerm1);
            grade11.getTerms().add(prevTerm2);
            gradeRepository.save(grade11);

            // ====================== CLASS ======================
            Class classEntity = Class.builder()
                    .grade(grade12)
                    .name("12-A")
                    .capacity(30L)
                    .build();
            classRepository.save(classEntity);

            Class classEntity2 = Class.builder()
                    .grade(grade12)
                    .name("12-B")
                    .capacity(28L)
                    .build();
            classRepository.save(classEntity2);

            student.setStudentClass(classEntity2);
            student2.setStudentClass(classEntity);
            student3.setStudentClass(classEntity2);
            student4.setStudentClass(classEntity2);
            student5.setStudentClass(classEntity);
            studentRepository.save(student);
            studentRepository.save(student2);
            studentRepository.save(student3);
            studentRepository.save(student4);
            studentRepository.save(student5);

            // ====================== PREVIOUS COURSES (2025 — when students were in Grade 11) ======================
            // These will show up when the student clicks "View Previous Courses"
            // because their termYear (2025) < latestYear (2026).

            // --- Intro to Java (teacher3 / Nouran Yasser) — prevTerm1 2025 ---
            Course prevCourse1 = new Course();
            prevCourse1.setTeacher(teacher3);
            prevCourse1.setTerm(prevTerm1);
            prevCourse1.setCourseName("Intro to Java");
            prevCourse1.setCourseType("IT");
            prevCourse1.setDescription("Fundamentals of Java programming: variables, loops, arrays, and basic OOP.");
            prevCourse1.setStudyPlan("Week 1-10");

            Assignment prevA1 = new Assignment();
            prevA1.setName("Java Basics Quiz");
            prevA1.setAssignDate(LocalDate.of(2025, 2, 10));
            prevA1.setDeadline(LocalDate.of(2025, 2, 17));
            prevA1.setDescription("Answer 20 questions on Java syntax, data types, and control flow.");
            prevA1.setFileLink("https://drive.google.com/");
            prevA1.setStudentSubmission("SUBMITTED");

            Assignment prevA2 = new Assignment();
            prevA2.setName("Console Calculator");
            prevA2.setAssignDate(LocalDate.of(2025, 3, 1));
            prevA2.setDeadline(LocalDate.of(2025, 3, 10));
            prevA2.setDescription("Build a command-line calculator in Java supporting +, -, *, / with input validation.");
            prevA2.setFileLink("https://drive.google.com/");
            prevA2.setStudentSubmission("SUBMITTED");

            prevCourse1.getAssignments().add(prevA1);
            prevCourse1.getAssignments().add(prevA2);
            courseRepository.save(prevCourse1);

            // --- HTML & CSS Basics (teacher2 / Hala Mohamed) — prevTerm1 2025 ---
            Course prevCourse2 = new Course();
            prevCourse2.setTeacher(teacher2);
            prevCourse2.setTerm(prevTerm1);
            prevCourse2.setCourseName("HTML & CSS Basics");
            prevCourse2.setCourseType("IT");
            prevCourse2.setDescription("Introduction to web markup and styling: HTML5 semantic elements, CSS box model, Flexbox.");
            prevCourse2.setStudyPlan("Week 1-8");

            Assignment prevA3 = new Assignment();
            prevA3.setName("Personal Portfolio Page");
            prevA3.setAssignDate(LocalDate.of(2025, 2, 15));
            prevA3.setDeadline(LocalDate.of(2025, 2, 28));
            prevA3.setDescription("Build a personal portfolio page using HTML5 and CSS only. Must include a header, about section, skills list, and contact form.");
            prevA3.setFileLink("https://drive.google.com/");
            prevA3.setStudentSubmission("SUBMITTED");

            prevCourse2.getAssignments().add(prevA3);
            courseRepository.save(prevCourse2);

            // --- Arabic Language (teacher6 / Khaled Mohammed) — prevTerm1 2025 ---
            Course prevCourse3 = new Course();
            prevCourse3.setTeacher(teacher6);
            prevCourse3.setTerm(prevTerm1);
            prevCourse3.setCourseName("Arabic Language");
            prevCourse3.setCourseType("Theory");
            prevCourse3.setDescription("Arabic grammar, reading comprehension, and essay writing.");
            prevCourse3.setStudyPlan("Week 1-12");

            Assignment prevA4 = new Assignment();
            prevA4.setName("Short Story Analysis");
            prevA4.setAssignDate(LocalDate.of(2025, 3, 5));
            prevA4.setDeadline(LocalDate.of(2025, 3, 12));
            prevA4.setDescription("اكتب تحليلاً أدبياً لقصة قصيرة من المقرر الدراسي، مع توضيح الشخصيات والحبكة والرسالة.");
            prevA4.setFileLink("https://drive.google.com/");
            prevA4.setStudentSubmission("SUBMITTED");

            prevCourse3.getAssignments().add(prevA4);
            courseRepository.save(prevCourse3);

            // --- English Skills (teacher7 / Wael Mohammed) — prevTerm2 2025 ---
            Course prevCourse4 = new Course();
            prevCourse4.setTeacher(teacher7);
            prevCourse4.setTerm(prevTerm2);
            prevCourse4.setCourseName("English Skills");
            prevCourse4.setCourseType("Theory");
            prevCourse4.setDescription("Intermediate English: reading, writing, grammar, and vocabulary expansion.");
            prevCourse4.setStudyPlan("Week 1-10");

            Assignment prevA5 = new Assignment();
            prevA5.setName("Paragraph Writing");
            prevA5.setAssignDate(LocalDate.of(2025, 9, 20));
            prevA5.setDeadline(LocalDate.of(2025, 9, 27));
            prevA5.setDescription("Write a 250-word paragraph about your favourite technology trend and its impact on daily life.");
            prevA5.setFileLink("https://drive.google.com/");
            prevA5.setStudentSubmission("SUBMITTED");

            prevCourse4.getAssignments().add(prevA5);
            courseRepository.save(prevCourse4);

            // --- Mathematics 11 (teacher5 / Rasha Mohammed) — prevTerm2 2025 ---
            Course prevCourse5 = new Course();
            prevCourse5.setTeacher(teacher5);
            prevCourse5.setTerm(prevTerm2);
            prevCourse5.setCourseName("Mathematics 11");
            prevCourse5.setCourseType("Theory");
            prevCourse5.setDescription("Algebra, trigonometry, and an introduction to limits.");
            prevCourse5.setStudyPlan("Week 1-12");

            Assignment prevA6 = new Assignment();
            prevA6.setName("Trigonometry Problem Set");
            prevA6.setAssignDate(LocalDate.of(2025, 10, 1));
            prevA6.setDeadline(LocalDate.of(2025, 10, 8));
            prevA6.setDescription("Solve 18 trigonometry problems covering sine/cosine rules, identities, and angle applications.");
            prevA6.setFileLink("https://drive.google.com/");
            prevA6.setStudentSubmission("SUBMITTED");

            Assignment prevA7 = new Assignment();
            prevA7.setName("Limits Introduction Worksheet");
            prevA7.setAssignDate(LocalDate.of(2025, 11, 5));
            prevA7.setDeadline(LocalDate.of(2025, 11, 12));
            prevA7.setDescription("Complete the 12-problem worksheet on evaluating limits algebraically and from graphs.");
            prevA7.setFileLink("https://drive.google.com/");
            prevA7.setStudentSubmission("SUBMITTED");

            prevCourse5.getAssignments().add(prevA6);
            prevCourse5.getAssignments().add(prevA7);
            courseRepository.save(prevCourse5);

            System.out.println("=== Previous (2025) courses seeded ===");

            // ====================== COURSE + ASSIGNMENTS ======================
            // Assignments are NOT saved separately — CascadeType.PERSIST on Course.assignments
            // handles inserts when courseRepository.save(course) is called.

            // --- Advanced Java (teacher3 / Nouran Yasser) ---
            Course course = new Course();
            course.setTeacher(teacher3);
            course.setTerm(secondTerm);
            course.setCourseName("Advanced Java");
            course.setCourseType("IT");
            course.setDescription("Advanced Java Programming");
            course.setStudyPlan("Week 1-12");

            Assignment assignment1 = new Assignment();
            assignment1.setName("Java OOP Assignment");
            assignment1.setAssignDate(LocalDate.now().minusDays(14));
            assignment1.setDeadline(LocalDate.now().minusDays(7));
            assignment1.setDescription("Implement a Library Management System using Java OOP principles (Inheritance, Polymorphism, Encapsulation).");
            assignment1.setFileLink("https://drive.google.com/");
            assignment1.setStudentSubmission("SUBMITTED");

            Assignment assignment2 = new Assignment();
            assignment2.setName("Spring Boot REST API");
            assignment2.setAssignDate(LocalDate.now().minusDays(7));
            assignment2.setDeadline(LocalDate.now().plusDays(7));
            assignment2.setDescription("Create a full CRUD REST API for a Student Module using Spring Boot and JPA.");
            assignment2.setFileLink("https://drive.google.com/");
            assignment2.setStudentSubmission("PENDING");

            Assignment assignment2b = new Assignment();
            assignment2b.setName("Design Patterns Report");
            assignment2b.setAssignDate(LocalDate.now().minusDays(3));
            assignment2b.setDeadline(LocalDate.now().plusDays(10));
            assignment2b.setDescription("Write a report covering Singleton, Factory, and Observer design patterns with Java code examples.");
            assignment2b.setFileLink("https://drive.google.com/");
            assignment2b.setStudentSubmission("PENDING");

            course.getAssignments().add(assignment1);
            course.getAssignments().add(assignment2);
            course.getAssignments().add(assignment2b);
            courseRepository.save(course); // cascades PERSIST to all 3 assignments

            // --- Database Systems (teacher1 / Hala Nageh) ---
            Course course2 = new Course();
            course2.setTeacher(teacher1);
            course2.setTerm(firstTerm);
            course2.setCourseName("Database Systems");
            course2.setCourseType("IT");
            course2.setDescription("Relational Databases and SQL");
            course2.setStudyPlan("Week 1-10");

            Assignment assignment3 = new Assignment();
            assignment3.setName("ERD Hospital System");
            assignment3.setAssignDate(LocalDate.now().minusDays(10));
            assignment3.setDeadline(LocalDate.now().minusDays(3));
            assignment3.setDescription("Design a complete ERD for a Hospital Management System with at least 10 entities and their relationships.");
            assignment3.setFileLink("https://drive.google.com/");
            assignment3.setStudentSubmission("SUBMITTED");

            Assignment assignment3b = new Assignment();
            assignment3b.setName("SQL Query Practice");
            assignment3b.setAssignDate(LocalDate.now().minusDays(5));
            assignment3b.setDeadline(LocalDate.now().plusDays(5));
            assignment3b.setDescription("Write 15 SQL queries using JOINs, GROUP BY, subqueries, and window functions on the provided school database schema.");
            assignment3b.setFileLink("https://drive.google.com/");
            assignment3b.setStudentSubmission("PENDING");

            course2.getAssignments().add(assignment3);
            course2.getAssignments().add(assignment3b);
            courseRepository.save(course2);

            // --- Mobile Applications (teacher / Safwat Ahmed) ---
            Course course3 = new Course();
            course3.setTeacher(teacher);
            course3.setTerm(secondTerm);
            course3.setCourseName("Mobile Applications");
            course3.setCourseType("IT");
            course3.setDescription("Build mobile applications using flutter");
            course3.setStudyPlan("Week 1-12");

            Assignment assignment4 = new Assignment();
            assignment4.setName("Flutter UI Screens");
            assignment4.setAssignDate(LocalDate.now().minusDays(12));
            assignment4.setDeadline(LocalDate.now().minusDays(5));
            assignment4.setDescription("Build 3 responsive Flutter screens: Login, Dashboard, and Profile. Use custom widgets and navigation.");
            assignment4.setFileLink("https://drive.google.com/");
            assignment4.setStudentSubmission("SUBMITTED");

            Assignment assignment4b = new Assignment();
            assignment4b.setName("State Management with Provider");
            assignment4b.setAssignDate(LocalDate.now().minusDays(4));
            assignment4b.setDeadline(LocalDate.now().plusDays(9));
            assignment4b.setDescription("Implement a To-Do app in Flutter using the Provider package for state management.");
            assignment4b.setFileLink("https://drive.google.com/");
            assignment4b.setStudentSubmission("PENDING");

            course3.getAssignments().add(assignment4);
            course3.getAssignments().add(assignment4b);
            courseRepository.save(course3);

            // --- Testing (teacher1 / Hala Nageh) ---
            Course course4 = new Course();
            course4.setTeacher(teacher1);
            course4.setTerm(secondTerm);
            course4.setCourseName("Testing");
            course4.setCourseType("IT");
            course4.setDescription("Concepts of Testing");
            course4.setStudyPlan("Week 1-12");

            Assignment assignment5 = new Assignment();
            assignment5.setName("Test Case Design");
            assignment5.setAssignDate(LocalDate.now().minusDays(8));
            assignment5.setDeadline(LocalDate.now().minusDays(1));
            assignment5.setDescription("Write a complete test case document for a Login feature using Equivalence Partitioning and Boundary Value Analysis.");
            assignment5.setFileLink("https://drive.google.com/");
            assignment5.setStudentSubmission("SUBMITTED");

            Assignment assignment5b = new Assignment();
            assignment5b.setName("Defect Report Writing");
            assignment5b.setAssignDate(LocalDate.now().minusDays(2));
            assignment5b.setDeadline(LocalDate.now().plusDays(6));
            assignment5b.setDescription("Identify and document 5 defects from the provided buggy web app. Include steps to reproduce, expected vs actual results, and severity.");
            assignment5b.setFileLink("https://drive.google.com/");
            assignment5b.setStudentSubmission("PENDING");

            course4.getAssignments().add(assignment5);
            course4.getAssignments().add(assignment5b);
            courseRepository.save(course4);

            // --- Arabic (teacher6 / Khaled Mohammed) ---
            Course course5 = new Course();
            course5.setTeacher(teacher6);
            course5.setTerm(firstTerm);
            course5.setCourseName("Arabic");
            course5.setCourseType("Theory");
            course5.setDescription("Arabic");
            course5.setStudyPlan("Week 1-12");

            Assignment assignment6 = new Assignment();
            assignment6.setName("Arabic Essay - Technology Impact");
            assignment6.setAssignDate(LocalDate.now().minusDays(9));
            assignment6.setDeadline(LocalDate.now().minusDays(2));
            assignment6.setDescription("اكتب مقالة لا تقل عن 500 كلمة حول تأثير التكنولوجيا على التعليم في العالم العربي.");
            assignment6.setFileLink("https://drive.google.com/");
            assignment6.setStudentSubmission("SUBMITTED");

            Assignment assignment6b = new Assignment();
            assignment6b.setName("Arabic Grammar Exercises");
            assignment6b.setAssignDate(LocalDate.now().minusDays(1));
            assignment6b.setDeadline(LocalDate.now().plusDays(5));
            assignment6b.setDescription("حل تمارين الإعراب والنحو على الجمل الفعلية والاسمية في الكتاب المدرسي صفحات 45-60.");
            assignment6b.setFileLink("https://drive.google.com/");
            assignment6b.setStudentSubmission("PENDING");

            course5.getAssignments().add(assignment6);
            course5.getAssignments().add(assignment6b);
            courseRepository.save(course5);

            // --- English (teacher7 / Wael Mohammed) ---
            Course course6 = new Course();
            course6.setTeacher(teacher7);
            course6.setTerm(firstTerm);
            course6.setCourseName("English");
            course6.setCourseType("Theory");
            course6.setDescription("Advanced English");
            course6.setStudyPlan("Week 1-12");

            Assignment assignment7 = new Assignment();
            assignment7.setName("Descriptive Writing");
            assignment7.setAssignDate(LocalDate.now().minusDays(11));
            assignment7.setDeadline(LocalDate.now().minusDays(4));
            assignment7.setDescription("Write a 400-word descriptive essay about your hometown. Focus on sensory details and vivid language.");
            assignment7.setFileLink("https://drive.google.com/");
            assignment7.setStudentSubmission("SUBMITTED");

            Assignment assignment7b = new Assignment();
            assignment7b.setName("Reading Comprehension Analysis");
            assignment7b.setAssignDate(LocalDate.now().minusDays(2));
            assignment7b.setDeadline(LocalDate.now().plusDays(8));
            assignment7b.setDescription("Read the provided excerpt from 'The Great Gatsby' and answer 10 comprehension and literary analysis questions.");
            assignment7b.setFileLink("https://drive.google.com/");
            assignment7b.setStudentSubmission("PENDING");

            course6.getAssignments().add(assignment7);
            course6.getAssignments().add(assignment7b);
            courseRepository.save(course6);

            // --- Web Development (teacher2 / Hala Mohamed) ---
            Course course7 = new Course();
            course7.setTeacher(teacher2);
            course7.setTerm(firstTerm);
            course7.setCourseName("Web Development");
            course7.setCourseType("IT");
            course7.setDescription("Frontend and backend web development");
            course7.setStudyPlan("Week 1-10");

            Assignment assignment8 = new Assignment();
            assignment8.setName("Responsive Landing Page");
            assignment8.setAssignDate(LocalDate.now().minusDays(13));
            assignment8.setDeadline(LocalDate.now().minusDays(6));
            assignment8.setDescription("Build a fully responsive landing page using HTML, CSS, and JavaScript. Must include a navbar, hero section, features section, and footer.");
            assignment8.setFileLink("https://drive.google.com/");
            assignment8.setStudentSubmission("SUBMITTED");

            Assignment assignment8b = new Assignment();
            assignment8b.setName("Angular Component Library");
            assignment8b.setAssignDate(LocalDate.now().minusDays(3));
            assignment8b.setDeadline(LocalDate.now().plusDays(11));
            assignment8b.setDescription("Create 5 reusable Angular standalone components (Button, Input, Card, Modal, Table) with proper inputs/outputs and documentation.");
            assignment8b.setFileLink("https://drive.google.com/");
            assignment8b.setStudentSubmission("PENDING");

            course7.getAssignments().add(assignment8);
            course7.getAssignments().add(assignment8b);
            courseRepository.save(course7);

//            // --- English section B (teacher / Safwat Ahmed — no assignments) ---
//            Course course8 = new Course();
//            course8.setTeacher(teacher);
//            course8.setTerm(firstTerm);
//            course8.setCourseName("English");
//            course8.setCourseType("Theory");
//            course8.setDescription("Advanced English");
//            course8.setStudyPlan("Week 1-10");
//            courseRepository.save(course8);

            // --- Physics (teacher8 / Sayed Mohammed) ---
            Course course9 = new Course();
            course9.setTeacher(teacher8);
            course9.setTerm(firstTerm);
            course9.setCourseName("Physics");
            course9.setCourseType("Theory");
            course9.setDescription("Advanced Physics");
            course9.setStudyPlan("Week 1-12");

            Assignment assignment9 = new Assignment();
            assignment9.setName("Newton's Laws Problem Set");
            assignment9.setAssignDate(LocalDate.now().minusDays(10));
            assignment9.setDeadline(LocalDate.now().minusDays(3));
            assignment9.setDescription("Solve the 20 problems on Newton's Laws of Motion from Chapter 3. Show all workings with free body diagrams.");
            assignment9.setFileLink("https://drive.google.com/");
            assignment9.setStudentSubmission("SUBMITTED");

            Assignment assignment9b = new Assignment();
            assignment9b.setName("Waves and Sound Lab Report");
            assignment9b.setAssignDate(LocalDate.now().minusDays(4));
            assignment9b.setDeadline(LocalDate.now().plusDays(10));
            assignment9b.setDescription("Write a full lab report for the sound wave experiment conducted in class. Include hypothesis, procedure, results table, calculations, and conclusion.");
            assignment9b.setFileLink("https://drive.google.com/");
            assignment9b.setStudentSubmission("PENDING");

            course9.getAssignments().add(assignment9);
            course9.getAssignments().add(assignment9b);
            courseRepository.save(course9);

            // --- Math (teacher5 / Rasha Mohammed) ---
            Course course10 = new Course();
            course10.setTeacher(teacher5);
            course10.setTerm(firstTerm);
            course10.setCourseName("Math");
            course10.setCourseType("Theory");
            course10.setDescription("Advanced Mathematics");
            course10.setStudyPlan("Week 1-12");

            Assignment assignment10 = new Assignment();
            assignment10.setName("Calculus Derivatives Worksheet");
            assignment10.setAssignDate(LocalDate.now().minusDays(9));
            assignment10.setDeadline(LocalDate.now().minusDays(2));
            assignment10.setDescription("Complete the derivatives worksheet covering chain rule, product rule, and quotient rule. All 25 problems must be solved with full steps.");
            assignment10.setFileLink("https://drive.google.com/");
            assignment10.setStudentSubmission("SUBMITTED");

            Assignment assignment10b = new Assignment();
            assignment10b.setName("Integration Techniques");
            assignment10b.setAssignDate(LocalDate.now().minusDays(1));
            assignment10b.setDeadline(LocalDate.now().plusDays(7));
            assignment10b.setDescription("Solve 15 integration problems using substitution, integration by parts, and partial fractions from the textbook pages 120–135.");
            assignment10b.setFileLink("https://drive.google.com/");
            assignment10b.setStudentSubmission("PENDING");

            course10.getAssignments().add(assignment10);
            course10.getAssignments().add(assignment10b);
            courseRepository.save(course10);

            // --- Religion (teacher6 / Khaled Mohammed — no assignments) ---
            Course course11 = new Course();
            course11.setTeacher(teacher6);
            course11.setTerm(firstTerm);
            course11.setCourseName("Religion");
            course11.setCourseType("Theory");
            course11.setDescription("Religion");
            course11.setStudyPlan("Week 1-12");
            courseRepository.save(course11);

            // ====================== MARKS TYPE ======================
            MarksType examType = MarksType.builder()
                    .typeName("Month Exam")
                    .build();
            marksTypeRepository.save(examType);

            MarksType quizType = MarksType.builder()
                    .typeName("Homework")
                    .build();
            marksTypeRepository.save(quizType);

            MarksType assignmentType = MarksType.builder()
                    .typeName("Assignment")
                    .build();
            marksTypeRepository.save(assignmentType);

            // ====================== MARKS ======================
            // 3 mark types × 5 courses × 5 students — each student has a distinct academic profile
            // student  (12-B) — High achiever
            // student2 (12-A) — Consistent average
            // student3 (12-B) — Struggling, improving
            // student4 (12-B) — Strong in IT, weak in theory
            // student5 (12-A) — Strong in theory, weak in IT

            // ── student (Fatma Elzahraa) — High achiever ──
            Mark mark1 = new Mark(); mark1.setCourse(course);  mark1.setStudent(student); mark1.setType(examType);       mark1.setScore(97L);  mark1.setMaxScore(100L); mark1.setIsApproved(true);  mark1.setFeedback("Excellent work."); mark1.setNotes("Top of class."); mark1.setFeedbackDate(LocalDate.now().minusDays(20)); markRepository.save(mark1);
            Mark mark2 = new Mark(); mark2.setCourse(course);  mark2.setStudent(student); mark2.setType(quizType);       mark2.setScore(18L);  mark2.setMaxScore(20L);  mark2.setIsApproved(true);  mark2.setFeedback("Well done."); mark2.setNotes("Keep it up."); mark2.setFeedbackDate(LocalDate.now().minusDays(14)); markRepository.save(mark2);
            Mark mark3 = new Mark(); mark3.setCourse(course);  mark3.setStudent(student); mark3.setType(assignmentType); mark3.setScore(48L);  mark3.setMaxScore(50L);  mark3.setIsApproved(true);  mark3.setFeedback("Outstanding."); mark3.setNotes("Clean implementation."); mark3.setFeedbackDate(LocalDate.now().minusDays(7)); markRepository.save(mark3);

            Mark mark4 = new Mark(); mark4.setCourse(course2); mark4.setStudent(student); mark4.setType(examType);       mark4.setScore(92L);  mark4.setMaxScore(100L); mark4.setIsApproved(true);  mark4.setFeedback("Great ERD design."); mark4.setNotes("Minor naming issues."); mark4.setFeedbackDate(LocalDate.now().minusDays(18)); markRepository.save(mark4);
            Mark mark5 = new Mark(); mark5.setCourse(course2); mark5.setStudent(student); mark5.setType(quizType);       mark5.setScore(19L);  mark5.setMaxScore(20L);  mark5.setIsApproved(true);  mark5.setFeedback("Perfect SQL."); mark5.setNotes(""); mark5.setFeedbackDate(LocalDate.now().minusDays(12)); markRepository.save(mark5);
            Mark mark6 = new Mark(); mark6.setCourse(course2); mark6.setStudent(student); mark6.setType(assignmentType); mark6.setScore(47L);  mark6.setMaxScore(50L);  mark6.setIsApproved(true);  mark6.setFeedback("Well structured."); mark6.setNotes("Minor issues."); mark6.setFeedbackDate(LocalDate.now().minusDays(6)); markRepository.save(mark6);

            Mark mark7 = new Mark(); mark7.setCourse(course5); mark7.setStudent(student); mark7.setType(examType);       mark7.setScore(95L);  mark7.setMaxScore(100L); mark7.setIsApproved(true);  mark7.setFeedback("Excellent essay."); mark7.setNotes(""); mark7.setFeedbackDate(LocalDate.now().minusDays(16)); markRepository.save(mark7);
            Mark mark8 = new Mark(); mark8.setCourse(course5); mark8.setStudent(student); mark8.setType(quizType);       mark8.setScore(20L);  mark8.setMaxScore(20L);  mark8.setIsApproved(true);  mark8.setFeedback("Perfect score."); mark8.setNotes(""); mark8.setFeedbackDate(LocalDate.now().minusDays(10)); markRepository.save(mark8);
            Mark mark9 = new Mark(); mark9.setCourse(course9); mark9.setStudent(student); mark9.setType(examType);       mark9.setScore(89L);  mark9.setMaxScore(100L); mark9.setIsApproved(true);  mark9.setFeedback("Good understanding."); mark9.setNotes("Review wave equations."); mark9.setFeedbackDate(LocalDate.now().minusDays(15)); markRepository.save(mark9);
            Mark mark10 = new Mark(); mark10.setCourse(course10); mark10.setStudent(student); mark10.setType(examType);  mark10.setScore(94L); mark10.setMaxScore(100L); mark10.setIsApproved(true); mark10.setFeedback("Strong calculus."); mark10.setNotes(""); mark10.setFeedbackDate(LocalDate.now().minusDays(13)); markRepository.save(mark10);

            // ── student2  — Consistent average ──
            Mark mark11 = new Mark(); mark11.setCourse(course);  mark11.setStudent(student2); mark11.setType(examType);       mark11.setScore(74L);  mark11.setMaxScore(100L); mark11.setIsApproved(true);  mark11.setFeedback("Satisfactory."); mark11.setNotes("Work on edge cases."); mark11.setFeedbackDate(LocalDate.now().minusDays(20)); markRepository.save(mark11);
            Mark mark12 = new Mark(); mark12.setCourse(course);  mark12.setStudent(student2); mark12.setType(quizType);       mark12.setScore(14L);  mark12.setMaxScore(20L);  mark12.setIsApproved(true);  mark12.setFeedback("Average."); mark12.setNotes("Review polymorphism."); mark12.setFeedbackDate(LocalDate.now().minusDays(14)); markRepository.save(mark12);
            Mark mark13 = new Mark(); mark13.setCourse(course);  mark13.setStudent(student2); mark13.setType(assignmentType); mark13.setScore(36L);  mark13.setMaxScore(50L);  mark13.setIsApproved(true);  mark13.setFeedback("Adequate."); mark13.setNotes("Missing some requirements."); mark13.setFeedbackDate(LocalDate.now().minusDays(7)); markRepository.save(mark13);

            Mark mark14 = new Mark(); mark14.setCourse(course2); mark14.setStudent(student2); mark14.setType(examType);       mark14.setScore(70L);  mark14.setMaxScore(100L); mark14.setIsApproved(true);  mark14.setFeedback("Acceptable."); mark14.setNotes("Revisit normalization."); mark14.setFeedbackDate(LocalDate.now().minusDays(18)); markRepository.save(mark14);
            Mark mark15 = new Mark(); mark15.setCourse(course2); mark15.setStudent(student2); mark15.setType(quizType);       mark15.setScore(13L);  mark15.setMaxScore(20L);  mark15.setIsApproved(true);  mark15.setFeedback("Needs more practice."); mark15.setNotes(""); mark15.setFeedbackDate(LocalDate.now().minusDays(12)); markRepository.save(mark15);
            Mark mark16 = new Mark(); mark16.setCourse(course5); mark16.setStudent(student2); mark16.setType(examType);       mark16.setScore(78L);  mark16.setMaxScore(100L); mark16.setIsApproved(true);  mark16.setFeedback("Good grammar."); mark16.setNotes("Essay needs more examples."); mark16.setFeedbackDate(LocalDate.now().minusDays(16)); markRepository.save(mark16);
            Mark mark17 = new Mark(); mark17.setCourse(course9); mark17.setStudent(student2); mark17.setType(examType);       mark17.setScore(72L);  mark17.setMaxScore(100L); mark17.setIsApproved(true);  mark17.setFeedback("Decent."); mark17.setNotes("Work on problem-solving speed."); mark17.setFeedbackDate(LocalDate.now().minusDays(15)); markRepository.save(mark17);
            Mark mark18 = new Mark(); mark18.setCourse(course10); mark18.setStudent(student2); mark18.setType(examType);      mark18.setScore(68L);  mark18.setMaxScore(100L); mark18.setIsApproved(true);  mark18.setFeedback("Fair."); mark18.setNotes("Integration needs work."); mark18.setFeedbackDate(LocalDate.now().minusDays(13)); markRepository.save(mark18);
            Mark mark19 = new Mark(); mark19.setCourse(course10); mark19.setStudent(student2); mark19.setType(assignmentType); mark19.setScore(32L); mark19.setMaxScore(50L);  mark19.setIsApproved(true);  mark19.setFeedback("Incomplete steps shown."); mark19.setNotes(""); mark19.setFeedbackDate(LocalDate.now().minusDays(8)); markRepository.save(mark19);

            // ── student3  Struggling, improving ──
            Mark mark20 = new Mark(); mark20.setCourse(course);  mark20.setStudent(student3); mark20.setType(examType);       mark20.setScore(55L);  mark20.setMaxScore(100L); mark20.setIsApproved(true);  mark20.setFeedback("Needs significant improvement."); mark20.setNotes("Revisit OOP basics."); mark20.setFeedbackDate(LocalDate.now().minusDays(20)); markRepository.save(mark20);
            Mark mark21 = new Mark(); mark21.setCourse(course);  mark21.setStudent(student3); mark21.setType(quizType);       mark21.setScore(10L);  mark21.setMaxScore(20L);  mark21.setIsApproved(true);  mark21.setFeedback("Below average."); mark21.setNotes("Practice more exercises."); mark21.setFeedbackDate(LocalDate.now().minusDays(14)); markRepository.save(mark21);
            Mark mark22 = new Mark(); mark22.setCourse(course);  mark22.setStudent(student3); mark22.setType(assignmentType); mark22.setScore(28L);  mark22.setMaxScore(50L);  mark22.setIsApproved(false); mark22.setFeedback("Resubmission required."); mark22.setNotes("Missing core features."); mark22.setFeedbackDate(LocalDate.now().minusDays(7)); markRepository.save(mark22);

            Mark mark23 = new Mark(); mark23.setCourse(course2); mark23.setStudent(student3); mark23.setType(examType);       mark23.setScore(60L);  mark23.setMaxScore(100L); mark23.setIsApproved(true);  mark23.setFeedback("Improving."); mark23.setNotes("ERD relationships need work."); mark23.setFeedbackDate(LocalDate.now().minusDays(18)); markRepository.save(mark23);
            Mark mark24 = new Mark(); mark24.setCourse(course2); mark24.setStudent(student3); mark24.setType(quizType);       mark24.setScore(11L);  mark24.setMaxScore(20L);  mark24.setIsApproved(true);  mark24.setFeedback("Effort noted."); mark24.setNotes(""); mark24.setFeedbackDate(LocalDate.now().minusDays(12)); markRepository.save(mark24);
            Mark mark25 = new Mark(); mark25.setCourse(course5); mark25.setStudent(student3); mark25.setType(examType);       mark25.setScore(65L);  mark25.setMaxScore(100L); mark25.setIsApproved(true);  mark25.setFeedback("Better than last time."); mark25.setNotes("Work on syntax accuracy."); mark25.setFeedbackDate(LocalDate.now().minusDays(16)); markRepository.save(mark25);
            Mark mark26 = new Mark(); mark26.setCourse(course9); mark26.setStudent(student3); mark26.setType(examType);       mark26.setScore(58L);  mark26.setMaxScore(100L); mark26.setIsApproved(true);  mark26.setFeedback("Needs lab practice."); mark26.setNotes("Revisit Newton laws."); mark26.setFeedbackDate(LocalDate.now().minusDays(15)); markRepository.save(mark26);
            Mark mark27 = new Mark(); mark27.setCourse(course10); mark27.setStudent(student3); mark27.setType(examType);      mark27.setScore(50L);  mark27.setMaxScore(100L); mark27.setIsApproved(true);  mark27.setFeedback("Borderline pass."); mark27.setNotes("Seek extra help."); mark27.setFeedbackDate(LocalDate.now().minusDays(13)); markRepository.save(mark27);
            Mark mark28 = new Mark(); mark28.setCourse(course10); mark28.setStudent(student3); mark28.setType(assignmentType); mark28.setScore(22L); mark28.setMaxScore(50L);  mark28.setIsApproved(false); mark28.setFeedback("Resubmit with full steps."); mark28.setNotes(""); mark28.setFeedbackDate(LocalDate.now().minusDays(8)); markRepository.save(mark28);

            // ── student4  Strong in IT, weak in theory ──
            Mark mark29 = new Mark(); mark29.setCourse(course);  mark29.setStudent(student4); mark29.setType(examType);       mark29.setScore(91L);  mark29.setMaxScore(100L); mark29.setIsApproved(true);  mark29.setFeedback("Very good."); mark29.setNotes("Minor logic errors."); mark29.setFeedbackDate(LocalDate.now().minusDays(20)); markRepository.save(mark29);
            Mark mark30 = new Mark(); mark30.setCourse(course);  mark30.setStudent(student4); mark30.setType(quizType);       mark30.setScore(17L);  mark30.setMaxScore(20L);  mark30.setIsApproved(true);  mark30.setFeedback("Strong."); mark30.setNotes(""); mark30.setFeedbackDate(LocalDate.now().minusDays(14)); markRepository.save(mark30);
            Mark mark31 = new Mark(); mark31.setCourse(course);  mark31.setStudent(student4); mark31.setType(assignmentType); mark31.setScore(45L);  mark31.setMaxScore(50L);  mark31.setIsApproved(true);  mark31.setFeedback("Well coded."); mark31.setNotes("Add more comments."); mark31.setFeedbackDate(LocalDate.now().minusDays(7)); markRepository.save(mark31);

            Mark mark32 = new Mark(); mark32.setCourse(course2); mark32.setStudent(student4); mark32.setType(examType);       mark32.setScore(88L);  mark32.setMaxScore(100L); mark32.setIsApproved(true);  mark32.setFeedback("Great SQL skills."); mark32.setNotes(""); mark32.setFeedbackDate(LocalDate.now().minusDays(18)); markRepository.save(mark32);
            Mark mark33 = new Mark(); mark33.setCourse(course2); mark33.setStudent(student4); mark33.setType(assignmentType); mark33.setScore(43L);  mark33.setMaxScore(50L);  mark33.setIsApproved(true);  mark33.setFeedback("Solid ERD."); mark33.setNotes(""); mark33.setFeedbackDate(LocalDate.now().minusDays(6)); markRepository.save(mark33);
            Mark mark34 = new Mark(); mark34.setCourse(course5); mark34.setStudent(student4); mark34.setType(examType);       mark34.setScore(62L);  mark34.setMaxScore(100L); mark34.setIsApproved(true);  mark34.setFeedback("Acceptable."); mark34.setNotes("Work on essay structure."); mark34.setFeedbackDate(LocalDate.now().minusDays(16)); markRepository.save(mark34);
            Mark mark35 = new Mark(); mark35.setCourse(course9); mark35.setStudent(student4); mark35.setType(examType);       mark35.setScore(64L);  mark35.setMaxScore(100L); mark35.setIsApproved(true);  mark35.setFeedback("Needs more effort."); mark35.setNotes("Physics concepts unclear."); mark35.setFeedbackDate(LocalDate.now().minusDays(15)); markRepository.save(mark35);
            Mark mark36 = new Mark(); mark36.setCourse(course10); mark36.setStudent(student4); mark36.setType(examType);      mark36.setScore(60L);  mark36.setMaxScore(100L); mark36.setIsApproved(true);  mark36.setFeedback("Average."); mark36.setNotes("Practice integration."); mark36.setFeedbackDate(LocalDate.now().minusDays(13)); markRepository.save(mark36);

            // ── student5  Strong in theory, weaker in IT ──
            Mark mark37 = new Mark(); mark37.setCourse(course);  mark37.setStudent(student5); mark37.setType(examType);       mark37.setScore(66L);  mark37.setMaxScore(100L); mark37.setIsApproved(true);  mark37.setFeedback("Fair."); mark37.setNotes("OOP concepts need revision."); mark37.setFeedbackDate(LocalDate.now().minusDays(20)); markRepository.save(mark37);
            Mark mark38 = new Mark(); mark38.setCourse(course);  mark38.setStudent(student5); mark38.setType(quizType);       mark38.setScore(12L);  mark38.setMaxScore(20L);  mark38.setIsApproved(true);  mark38.setFeedback("Below average."); mark38.setNotes("Study design patterns."); mark38.setFeedbackDate(LocalDate.now().minusDays(14)); markRepository.save(mark38);
            Mark mark39 = new Mark(); mark39.setCourse(course);  mark39.setStudent(student5); mark39.setType(assignmentType); mark39.setScore(30L);  mark39.setMaxScore(50L);  mark39.setIsApproved(true);  mark39.setFeedback("Partial implementation."); mark39.setNotes("Missing unit tests."); mark39.setFeedbackDate(LocalDate.now().minusDays(7)); markRepository.save(mark39);

            Mark mark40 = new Mark(); mark40.setCourse(course2); mark40.setStudent(student5); mark40.setType(examType);       mark40.setScore(70L);  mark40.setMaxScore(100L); mark40.setIsApproved(true);  mark40.setFeedback("Decent SQL knowledge."); mark40.setNotes("More JOIN practice needed."); mark40.setFeedbackDate(LocalDate.now().minusDays(18)); markRepository.save(mark40);
            Mark mark41 = new Mark(); mark41.setCourse(course5); mark41.setStudent(student5); mark41.setType(examType);       mark41.setScore(93L);  mark41.setMaxScore(100L); mark41.setIsApproved(true);  mark41.setFeedback("Excellent essay."); mark41.setNotes("Strong language skills."); mark41.setFeedbackDate(LocalDate.now().minusDays(16)); markRepository.save(mark41);
            Mark mark42 = new Mark(); mark42.setCourse(course5); mark42.setStudent(student5); mark42.setType(quizType);       mark42.setScore(20L);  mark42.setMaxScore(20L);  mark42.setIsApproved(true);  mark42.setFeedback("Perfect."); mark42.setNotes(""); mark42.setFeedbackDate(LocalDate.now().minusDays(10)); markRepository.save(mark42);
            Mark mark43 = new Mark(); mark43.setCourse(course9); mark43.setStudent(student5); mark43.setType(examType);       mark43.setScore(85L);  mark43.setMaxScore(100L); mark43.setIsApproved(true);  mark43.setFeedback("Good physics grasp."); mark43.setNotes("Show more working steps."); mark43.setFeedbackDate(LocalDate.now().minusDays(15)); markRepository.save(mark43);
            Mark mark44 = new Mark(); mark44.setCourse(course10); mark44.setStudent(student5); mark44.setType(examType);      mark44.setScore(88L);  mark44.setMaxScore(100L); mark44.setIsApproved(true);  mark44.setFeedback("Strong math."); mark44.setNotes(""); mark44.setFeedbackDate(LocalDate.now().minusDays(13)); markRepository.save(mark44);
            Mark mark45 = new Mark(); mark45.setCourse(course10); mark45.setStudent(student5); mark45.setType(assignmentType); mark45.setScore(46L); mark45.setMaxScore(50L);  mark45.setIsApproved(true);  mark45.setFeedback("Excellent working shown."); mark45.setNotes(""); mark45.setFeedbackDate(LocalDate.now().minusDays(8)); markRepository.save(mark45);

            System.out.println("=== Marks saved: " + markRepository.count() + " ===");

            // ====================== PROJECT ======================
            Project project1 = new Project();
            project1.setCourse(course);
            project1.setName("Library System");
            project1.setDescription("Full Java-based library management system.");
            project1.setAssignDate(LocalDate.now());
            project1.setDeadline(LocalDate.now().plusDays(30));
            projectRepository.save(project1);

            Project project2 = new Project();
            project2.setCourse(course2);
            project2.setName("Hospital DB");
            project2.setDescription("Oracle DB schema for a hospital system.");
            project2.setAssignDate(LocalDate.now());
            project2.setDeadline(LocalDate.now().plusDays(21));
            projectRepository.save(project2);

            // ====================== NOTIFICATIONS ======================
            Notification notification1 = notificationRepository.save(Notification.builder()
                    .title("Exam Schedule Released").type("ACADEMIC")
                    .priority("HIGH").sentAt(LocalDate.now())
                    .body("The final exam schedule for Term 1 has been published. Please check the portal.")
                    .build());

            Notification notification2 = notificationRepository.save(Notification.builder()
                    .title("Holiday Notice").type("GENERAL")
                    .priority("MEDIUM").sentAt(LocalDate.now().minusDays(1))
                    .body("School will be closed on the 25th for the national holiday.")
                    .build());

            Notification notification3 = notificationRepository.save(Notification.builder()
                    .title("Assignment Deadline Reminder").type("ACADEMIC")
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
// TIME_SLOTS: 8:00-8:50 | 8:50-9:40 | 9:40-10:30 | BREAK 10:30-11:00 | 11:00-11:50 | 11:50-12:40 | 12:40-1:30 | BREAK 1:30-1:50 | 1:50-2:40 | 2:40-3:30
// dayOfWeek: 1=Sunday, 2=Monday, 3=Tuesday, 4=Wednesday, 5=Thursday

// ── SUNDAY ──
            Session s1 = new Session(); s1.setClassField(classEntity); s1.setCourse(course);  s1.setDayOfWeek(1L); s1.setStartAt(LocalTime.of(8,0));   s1.setEndAt(LocalTime.of(8,50));  s1.setSessionType(Session.SessionType.CLASS); s1.setUpdatedAt(LocalDate.now());  sessionRepository.save(s1);
            Session s2 = new Session(); s2.setClassField(classEntity); s2.setCourse(course2); s2.setDayOfWeek(1L); s2.setStartAt(LocalTime.of(8,50));  s2.setEndAt(LocalTime.of(9,40));  s2.setSessionType(Session.SessionType.CLASS); s2.setUpdatedAt(LocalDate.now());  sessionRepository.save(s2);
            Session s3 = new Session(); s3.setClassField(classEntity); s3.setCourse(course3); s3.setDayOfWeek(1L); s3.setStartAt(LocalTime.of(9,40));  s3.setEndAt(LocalTime.of(10,30)); s3.setSessionType(Session.SessionType.CLASS); s3.setUpdatedAt(LocalDate.now());  sessionRepository.save(s3);
// 10:30-11:00 = BREAK (no session)
            Session s4 = new Session(); s4.setClassField(classEntity); s4.setCourse(course4); s4.setDayOfWeek(1L); s4.setStartAt(LocalTime.of(11,0));  s4.setEndAt(LocalTime.of(11,50)); s4.setSessionType(Session.SessionType.CLASS); s4.setUpdatedAt(LocalDate.now());  sessionRepository.save(s4);
            Session s5 = new Session(); s5.setClassField(classEntity); s5.setCourse(course5); s5.setDayOfWeek(1L); s5.setStartAt(LocalTime.of(11,50)); s5.setEndAt(LocalTime.of(12,40)); s5.setSessionType(Session.SessionType.CLASS); s5.setUpdatedAt(LocalDate.now());  sessionRepository.save(s5);
            Session s6 = new Session(); s6.setClassField(classEntity); s6.setCourse(course6); s6.setDayOfWeek(1L); s6.setStartAt(LocalTime.of(12,40)); s6.setEndAt(LocalTime.of(13,30)); s6.setSessionType(Session.SessionType.CLASS); s6.setUpdatedAt(LocalDate.now());  sessionRepository.save(s6);
// 1:30-1:50 = BREAK (no session)
            Session s7 = new Session(); s7.setClassField(classEntity); s7.setCourse(course7); s7.setDayOfWeek(1L); s7.setStartAt(LocalTime.of(13,50)); s7.setEndAt(LocalTime.of(14,40)); s7.setSessionType(Session.SessionType.CLASS); s7.setUpdatedAt(LocalDate.now());  sessionRepository.save(s7);
            Session s8 = new Session(); s8.setClassField(classEntity); s8.setCourse(course7); s8.setDayOfWeek(1L); s8.setStartAt(LocalTime.of(14,40)); s8.setEndAt(LocalTime.of(15,30)); s8.setSessionType(Session.SessionType.CLASS); s8.setUpdatedAt(LocalDate.now());  sessionRepository.save(s8);

// ── MONDAY ──
            Session s9  = new Session(); s9.setClassField(classEntity);  s9.setCourse(course2); s9.setDayOfWeek(2L); s9.setStartAt(LocalTime.of(8,0));   s9.setEndAt(LocalTime.of(8,50));  s9.setSessionType(Session.SessionType.CLASS); s9.setUpdatedAt(LocalDate.now());  sessionRepository.save(s9);
            Session s10 = new Session(); s10.setClassField(classEntity); s10.setCourse(course3); s10.setDayOfWeek(2L); s10.setStartAt(LocalTime.of(8,50));  s10.setEndAt(LocalTime.of(9,40));  s10.setSessionType(Session.SessionType.CLASS); s10.setUpdatedAt(LocalDate.now()); sessionRepository.save(s10);
            Session s11 = new Session(); s11.setClassField(classEntity); s11.setCourse(course);  s11.setDayOfWeek(2L); s11.setStartAt(LocalTime.of(9,40));  s11.setEndAt(LocalTime.of(10,30)); s11.setSessionType(Session.SessionType.CLASS); s11.setUpdatedAt(LocalDate.now()); sessionRepository.save(s11);
// BREAK 10:30-11:00
            Session s12 = new Session(); s12.setClassField(classEntity); s12.setCourse(course4); s12.setDayOfWeek(2L); s12.setStartAt(LocalTime.of(11,0));  s12.setEndAt(LocalTime.of(11,50)); s12.setSessionType(Session.SessionType.CLASS); s12.setUpdatedAt(LocalDate.now()); sessionRepository.save(s12);
            Session s13 = new Session(); s13.setClassField(classEntity); s13.setCourse(course5); s13.setDayOfWeek(2L); s13.setStartAt(LocalTime.of(11,50)); s13.setEndAt(LocalTime.of(12,40)); s13.setSessionType(Session.SessionType.CLASS); s13.setUpdatedAt(LocalDate.now()); sessionRepository.save(s13);
            Session s14 = new Session(); s14.setClassField(classEntity); s14.setCourse(course6); s14.setDayOfWeek(2L); s14.setStartAt(LocalTime.of(12,40)); s14.setEndAt(LocalTime.of(13,30)); s14.setSessionType(Session.SessionType.CLASS); s14.setUpdatedAt(LocalDate.now()); sessionRepository.save(s14);
// BREAK 1:30-1:50
            Session s15 = new Session(); s15.setClassField(classEntity); s15.setCourse(course9); s15.setDayOfWeek(2L); s15.setStartAt(LocalTime.of(13,50)); s15.setEndAt(LocalTime.of(14,40)); s15.setSessionType(Session.SessionType.CLASS); s15.setUpdatedAt(LocalDate.now()); sessionRepository.save(s15);
            Session s16 = new Session(); s16.setClassField(classEntity); s16.setCourse(course7); s16.setDayOfWeek(2L); s16.setStartAt(LocalTime.of(14,40)); s16.setEndAt(LocalTime.of(15,30)); s16.setSessionType(Session.SessionType.CLASS); s16.setUpdatedAt(LocalDate.now()); sessionRepository.save(s16);

// ── TUESDAY ──
            Session s17 = new Session(); s17.setClassField(classEntity); s17.setCourse(course3); s17.setDayOfWeek(3L); s17.setStartAt(LocalTime.of(8,0));   s17.setEndAt(LocalTime.of(8,50));  s17.setSessionType(Session.SessionType.CLASS); s17.setUpdatedAt(LocalDate.now()); sessionRepository.save(s17);
            Session s18 = new Session(); s18.setClassField(classEntity); s18.setCourse(course);  s18.setDayOfWeek(3L); s18.setStartAt(LocalTime.of(8,50));  s18.setEndAt(LocalTime.of(9,40));  s18.setSessionType(Session.SessionType.CLASS); s18.setUpdatedAt(LocalDate.now()); sessionRepository.save(s18);
            Session s19 = new Session(); s19.setClassField(classEntity); s19.setCourse(course7); s19.setDayOfWeek(3L); s19.setStartAt(LocalTime.of(9,40));  s19.setEndAt(LocalTime.of(10,30)); s19.setSessionType(Session.SessionType.CLASS); s19.setUpdatedAt(LocalDate.now()); sessionRepository.save(s19);
// BREAK 10:30-11:00
            Session s20 = new Session(); s20.setClassField(classEntity); s20.setCourse(course);  s20.setDayOfWeek(3L); s20.setStartAt(LocalTime.of(11,0));  s20.setEndAt(LocalTime.of(11,50)); s20.setSessionType(Session.SessionType.CLASS); s20.setUpdatedAt(LocalDate.now()); sessionRepository.save(s20);
            Session s21 = new Session(); s21.setClassField(classEntity); s21.setCourse(course6); s21.setDayOfWeek(3L); s21.setStartAt(LocalTime.of(11,50)); s21.setEndAt(LocalTime.of(12,40)); s21.setSessionType(Session.SessionType.CLASS); s21.setUpdatedAt(LocalDate.now()); sessionRepository.save(s21);
            Session s22 = new Session(); s22.setClassField(classEntity); s22.setCourse(course6); s22.setDayOfWeek(3L); s22.setStartAt(LocalTime.of(12,40)); s22.setEndAt(LocalTime.of(13,30)); s22.setSessionType(Session.SessionType.CLASS); s22.setUpdatedAt(LocalDate.now()); sessionRepository.save(s22);
// BREAK 1:30-1:50
            Session s23 = new Session(); s23.setClassField(classEntity); s23.setCourse(course4); s23.setDayOfWeek(3L); s23.setStartAt(LocalTime.of(13,50)); s23.setEndAt(LocalTime.of(14,40)); s23.setSessionType(Session.SessionType.CLASS); s23.setUpdatedAt(LocalDate.now()); sessionRepository.save(s23);
            Session s24 = new Session(); s24.setClassField(classEntity); s24.setCourse(course2); s24.setDayOfWeek(3L); s24.setStartAt(LocalTime.of(14,40)); s24.setEndAt(LocalTime.of(15,30)); s24.setSessionType(Session.SessionType.CLASS); s24.setUpdatedAt(LocalDate.now()); sessionRepository.save(s24);

// ── WEDNESDAY ──
            Session s25 = new Session(); s25.setClassField(classEntity); s25.setCourse(course5); s25.setDayOfWeek(4L); s25.setStartAt(LocalTime.of(8,0));   s25.setEndAt(LocalTime.of(8,50));  s25.setSessionType(Session.SessionType.CLASS); s25.setUpdatedAt(LocalDate.now()); sessionRepository.save(s25);
            Session s26 = new Session(); s26.setClassField(classEntity); s26.setCourse(course4); s26.setDayOfWeek(4L); s26.setStartAt(LocalTime.of(8,50));  s26.setEndAt(LocalTime.of(9,40));  s26.setSessionType(Session.SessionType.CLASS); s26.setUpdatedAt(LocalDate.now()); sessionRepository.save(s26);
            Session s27 = new Session(); s27.setClassField(classEntity); s27.setCourse(course2); s27.setDayOfWeek(4L); s27.setStartAt(LocalTime.of(9,40));  s27.setEndAt(LocalTime.of(10,30)); s27.setSessionType(Session.SessionType.CLASS); s27.setUpdatedAt(LocalDate.now()); sessionRepository.save(s27);
// BREAK 10:30-11:00
            Session s28 = new Session(); s28.setClassField(classEntity); s28.setCourse(course9); s28.setDayOfWeek(4L); s28.setStartAt(LocalTime.of(11,0));  s28.setEndAt(LocalTime.of(11,50)); s28.setSessionType(Session.SessionType.CLASS); s28.setUpdatedAt(LocalDate.now()); sessionRepository.save(s28);
            Session s29 = new Session(); s29.setClassField(classEntity); s29.setCourse(course3); s29.setDayOfWeek(4L); s29.setStartAt(LocalTime.of(11,50)); s29.setEndAt(LocalTime.of(12,40)); s29.setSessionType(Session.SessionType.CLASS); s29.setUpdatedAt(LocalDate.now()); sessionRepository.save(s29);
            Session s30 = new Session(); s30.setClassField(classEntity); s30.setCourse(course);  s30.setDayOfWeek(4L); s30.setStartAt(LocalTime.of(12,40)); s30.setEndAt(LocalTime.of(13,30)); s30.setSessionType(Session.SessionType.CLASS); s30.setUpdatedAt(LocalDate.now()); sessionRepository.save(s30);
// BREAK 1:30-1:50
            Session s31 = new Session(); s31.setClassField(classEntity); s31.setCourse(course5  ); s31.setDayOfWeek(4L); s31.setStartAt(LocalTime.of(13,50)); s31.setEndAt(LocalTime.of(14,40)); s31.setSessionType(Session.SessionType.CLASS); s31.setUpdatedAt(LocalDate.now()); sessionRepository.save(s31);
            Session s32 = new Session(); s32.setClassField(classEntity); s32.setCourse(course5); s32.setDayOfWeek(4L); s32.setStartAt(LocalTime.of(14,40)); s32.setEndAt(LocalTime.of(15,30)); s32.setSessionType(Session.SessionType.CLASS); s32.setUpdatedAt(LocalDate.now()); sessionRepository.save(s32);

// ── THURSDAY ──
            Session s33 = new Session(); s33.setClassField(classEntity); s33.setCourse(course6); s33.setDayOfWeek(5L); s33.setStartAt(LocalTime.of(8,0));   s33.setEndAt(LocalTime.of(8,50));  s33.setSessionType(Session.SessionType.CLASS); s33.setUpdatedAt(LocalDate.now()); sessionRepository.save(s33);
            Session s34 = new Session(); s34.setClassField(classEntity); s34.setCourse(course);  s34.setDayOfWeek(5L); s34.setStartAt(LocalTime.of(8,50));  s34.setEndAt(LocalTime.of(9,40));  s34.setSessionType(Session.SessionType.CLASS); s34.setUpdatedAt(LocalDate.now()); sessionRepository.save(s34);
            Session s35 = new Session(); s35.setClassField(classEntity); s35.setCourse(course);  s35.setDayOfWeek(5L); s35.setStartAt(LocalTime.of(9,40));  s35.setEndAt(LocalTime.of(10,30)); s35.setSessionType(Session.SessionType.CLASS); s35.setUpdatedAt(LocalDate.now()); sessionRepository.save(s35);
// BREAK 10:30-11:00
            Session s36 = new Session(); s36.setClassField(classEntity); s36.setCourse(course2); s36.setDayOfWeek(5L); s36.setStartAt(LocalTime.of(11,0));  s36.setEndAt(LocalTime.of(11,50)); s36.setSessionType(Session.SessionType.CLASS); s36.setUpdatedAt(LocalDate.now()); sessionRepository.save(s36);
            Session s37 = new Session(); s37.setClassField(classEntity); s37.setCourse(course7); s37.setDayOfWeek(5L); s37.setStartAt(LocalTime.of(11,50)); s37.setEndAt(LocalTime.of(12,40)); s37.setSessionType(Session.SessionType.CLASS); s37.setUpdatedAt(LocalDate.now()); sessionRepository.save(s37);
            Session s38 = new Session(); s38.setClassField(classEntity); s38.setCourse(course3); s38.setDayOfWeek(5L); s38.setStartAt(LocalTime.of(12,40)); s38.setEndAt(LocalTime.of(13,30)); s38.setSessionType(Session.SessionType.CLASS); s38.setUpdatedAt(LocalDate.now()); sessionRepository.save(s38);
// BREAK 1:30-1:50
            Session s39 = new Session(); s39.setClassField(classEntity); s39.setCourse(course9); s39.setDayOfWeek(5L); s39.setStartAt(LocalTime.of(13,50)); s39.setEndAt(LocalTime.of(14,40)); s39.setSessionType(Session.SessionType.CLASS); s39.setUpdatedAt(LocalDate.now()); sessionRepository.save(s39);
// slot 2:40-3:30 empty on Thursday

// ====================== SESSIONS (CLASS SCHEDULE — 12-B classEntity2) ======================
// Same 8-slot structure: 8:00-8:50 | 8:50-9:40 | 9:40-10:30 | BREAK | 11:00-11:50 | 11:50-12:40 | 12:40-1:30 | BREAK | 1:50-2:40 | 2:40-3:30
// 12-B is taught by a different rotation of teachers/courses

// ── SUNDAY (12-B) ──
            Session b1 = new Session(); b1.setClassField(classEntity2); b1.setCourse(course2);  b1.setDayOfWeek(1L); b1.setStartAt(LocalTime.of(8,0));   b1.setEndAt(LocalTime.of(8,50));  b1.setSessionType(Session.SessionType.CLASS); b1.setUpdatedAt(LocalDate.now());  sessionRepository.save(b1);
            Session b2 = new Session(); b2.setClassField(classEntity2); b2.setCourse(course5);  b2.setDayOfWeek(1L); b2.setStartAt(LocalTime.of(8,50));  b2.setEndAt(LocalTime.of(9,40));  b2.setSessionType(Session.SessionType.CLASS); b2.setUpdatedAt(LocalDate.now());  sessionRepository.save(b2);
            Session b3 = new Session(); b3.setClassField(classEntity2); b3.setCourse(course9);  b3.setDayOfWeek(1L); b3.setStartAt(LocalTime.of(9,40));  b3.setEndAt(LocalTime.of(10,30)); b3.setSessionType(Session.SessionType.CLASS); b3.setUpdatedAt(LocalDate.now());  sessionRepository.save(b3);
// BREAK 10:30-11:00
            Session b4 = new Session(); b4.setClassField(classEntity2); b4.setCourse(course10); b4.setDayOfWeek(1L); b4.setStartAt(LocalTime.of(11,0));  b4.setEndAt(LocalTime.of(11,50)); b4.setSessionType(Session.SessionType.CLASS); b4.setUpdatedAt(LocalDate.now());  sessionRepository.save(b4);
            Session b5 = new Session(); b5.setClassField(classEntity2); b5.setCourse(course);   b5.setDayOfWeek(1L); b5.setStartAt(LocalTime.of(11,50)); b5.setEndAt(LocalTime.of(12,40)); b5.setSessionType(Session.SessionType.CLASS); b5.setUpdatedAt(LocalDate.now());  sessionRepository.save(b5);
            Session b6 = new Session(); b6.setClassField(classEntity2); b6.setCourse(course6);  b6.setDayOfWeek(1L); b6.setStartAt(LocalTime.of(12,40)); b6.setEndAt(LocalTime.of(13,30)); b6.setSessionType(Session.SessionType.CLASS); b6.setUpdatedAt(LocalDate.now());  sessionRepository.save(b6);
// BREAK 1:30-1:50
            Session b7 = new Session(); b7.setClassField(classEntity2); b7.setCourse(course3);  b7.setDayOfWeek(1L); b7.setStartAt(LocalTime.of(13,50)); b7.setEndAt(LocalTime.of(14,40)); b7.setSessionType(Session.SessionType.CLASS); b7.setUpdatedAt(LocalDate.now());  sessionRepository.save(b7);
            Session b8 = new Session(); b8.setClassField(classEntity2); b8.setCourse(course4);  b8.setDayOfWeek(1L); b8.setStartAt(LocalTime.of(14,40)); b8.setEndAt(LocalTime.of(15,30)); b8.setSessionType(Session.SessionType.CLASS); b8.setUpdatedAt(LocalDate.now());  sessionRepository.save(b8);

// ── MONDAY (12-B) ──
            Session b9  = new Session(); b9.setClassField(classEntity2);  b9.setCourse(course);   b9.setDayOfWeek(2L); b9.setStartAt(LocalTime.of(8,0));   b9.setEndAt(LocalTime.of(8,50));  b9.setSessionType(Session.SessionType.CLASS); b9.setUpdatedAt(LocalDate.now());  sessionRepository.save(b9);
            Session b10 = new Session(); b10.setClassField(classEntity2); b10.setCourse(course9);  b10.setDayOfWeek(2L); b10.setStartAt(LocalTime.of(8,50));  b10.setEndAt(LocalTime.of(9,40));  b10.setSessionType(Session.SessionType.CLASS); b10.setUpdatedAt(LocalDate.now()); sessionRepository.save(b10);
            Session b11 = new Session(); b11.setClassField(classEntity2); b11.setCourse(course10); b11.setDayOfWeek(2L); b11.setStartAt(LocalTime.of(9,40));  b11.setEndAt(LocalTime.of(10,30)); b11.setSessionType(Session.SessionType.CLASS); b11.setUpdatedAt(LocalDate.now()); sessionRepository.save(b11);
// BREAK 10:30-11:00
            Session b12 = new Session(); b12.setClassField(classEntity2); b12.setCourse(course6);  b12.setDayOfWeek(2L); b12.setStartAt(LocalTime.of(11,0));  b12.setEndAt(LocalTime.of(11,50)); b12.setSessionType(Session.SessionType.CLASS); b12.setUpdatedAt(LocalDate.now()); sessionRepository.save(b12);
            Session b13 = new Session(); b13.setClassField(classEntity2); b13.setCourse(course2);  b13.setDayOfWeek(2L); b13.setStartAt(LocalTime.of(11,50)); b13.setEndAt(LocalTime.of(12,40)); b13.setSessionType(Session.SessionType.CLASS); b13.setUpdatedAt(LocalDate.now()); sessionRepository.save(b13);
            Session b14 = new Session(); b14.setClassField(classEntity2); b14.setCourse(course5);  b14.setDayOfWeek(2L); b14.setStartAt(LocalTime.of(12,40)); b14.setEndAt(LocalTime.of(13,30)); b14.setSessionType(Session.SessionType.CLASS); b14.setUpdatedAt(LocalDate.now()); sessionRepository.save(b14);
// BREAK 1:30-1:50
            Session b15 = new Session(); b15.setClassField(classEntity2); b15.setCourse(course7);  b15.setDayOfWeek(2L); b15.setStartAt(LocalTime.of(13,50)); b15.setEndAt(LocalTime.of(14,40)); b15.setSessionType(Session.SessionType.CLASS); b15.setUpdatedAt(LocalDate.now()); sessionRepository.save(b15);
            Session b16 = new Session(); b16.setClassField(classEntity2); b16.setCourse(course3);  b16.setDayOfWeek(2L); b16.setStartAt(LocalTime.of(14,40)); b16.setEndAt(LocalTime.of(15,30)); b16.setSessionType(Session.SessionType.CLASS); b16.setUpdatedAt(LocalDate.now()); sessionRepository.save(b16);

// ── TUESDAY (12-B) ──
            Session b17 = new Session(); b17.setClassField(classEntity2); b17.setCourse(course10); b17.setDayOfWeek(3L); b17.setStartAt(LocalTime.of(8,0));   b17.setEndAt(LocalTime.of(8,50));  b17.setSessionType(Session.SessionType.CLASS); b17.setUpdatedAt(LocalDate.now()); sessionRepository.save(b17);
            Session b18 = new Session(); b18.setClassField(classEntity2); b18.setCourse(course3);  b18.setDayOfWeek(3L); b18.setStartAt(LocalTime.of(8,50));  b18.setEndAt(LocalTime.of(9,40));  b18.setSessionType(Session.SessionType.CLASS); b18.setUpdatedAt(LocalDate.now()); sessionRepository.save(b18);
            Session b19 = new Session(); b19.setClassField(classEntity2); b19.setCourse(course4);  b19.setDayOfWeek(3L); b19.setStartAt(LocalTime.of(9,40));  b19.setEndAt(LocalTime.of(10,30)); b19.setSessionType(Session.SessionType.CLASS); b19.setUpdatedAt(LocalDate.now()); sessionRepository.save(b19);
// BREAK 10:30-11:00
            Session b20 = new Session(); b20.setClassField(classEntity2); b20.setCourse(course);   b20.setDayOfWeek(3L); b20.setStartAt(LocalTime.of(11,0));  b20.setEndAt(LocalTime.of(11,50)); b20.setSessionType(Session.SessionType.CLASS); b20.setUpdatedAt(LocalDate.now()); sessionRepository.save(b20);
            Session b21 = new Session(); b21.setClassField(classEntity2); b21.setCourse(course2);  b21.setDayOfWeek(3L); b21.setStartAt(LocalTime.of(11,50)); b21.setEndAt(LocalTime.of(12,40)); b21.setSessionType(Session.SessionType.CLASS); b21.setUpdatedAt(LocalDate.now()); sessionRepository.save(b21);
            Session b22 = new Session(); b22.setClassField(classEntity2); b22.setCourse(course9);  b22.setDayOfWeek(3L); b22.setStartAt(LocalTime.of(12,40)); b22.setEndAt(LocalTime.of(13,30)); b22.setSessionType(Session.SessionType.CLASS); b22.setUpdatedAt(LocalDate.now()); sessionRepository.save(b22);
// BREAK 1:30-1:50
            Session b23 = new Session(); b23.setClassField(classEntity2); b23.setCourse(course6);  b23.setDayOfWeek(3L); b23.setStartAt(LocalTime.of(13,50)); b23.setEndAt(LocalTime.of(14,40)); b23.setSessionType(Session.SessionType.CLASS); b23.setUpdatedAt(LocalDate.now()); sessionRepository.save(b23);
            Session b24 = new Session(); b24.setClassField(classEntity2); b24.setCourse(course5);  b24.setDayOfWeek(3L); b24.setStartAt(LocalTime.of(14,40)); b24.setEndAt(LocalTime.of(15,30)); b24.setSessionType(Session.SessionType.CLASS); b24.setUpdatedAt(LocalDate.now()); sessionRepository.save(b24);

// ── WEDNESDAY (12-B) ──
            Session b25 = new Session(); b25.setClassField(classEntity2); b25.setCourse(course4);  b25.setDayOfWeek(4L); b25.setStartAt(LocalTime.of(8,0));   b25.setEndAt(LocalTime.of(8,50));  b25.setSessionType(Session.SessionType.CLASS); b25.setUpdatedAt(LocalDate.now()); sessionRepository.save(b25);
            Session b26 = new Session(); b26.setClassField(classEntity2); b26.setCourse(course);   b26.setDayOfWeek(4L); b26.setStartAt(LocalTime.of(8,50));  b26.setEndAt(LocalTime.of(9,40));  b26.setSessionType(Session.SessionType.CLASS); b26.setUpdatedAt(LocalDate.now()); sessionRepository.save(b26);
            Session b27 = new Session(); b27.setClassField(classEntity2); b27.setCourse(course7);  b27.setDayOfWeek(4L); b27.setStartAt(LocalTime.of(9,40));  b27.setEndAt(LocalTime.of(10,30)); b27.setSessionType(Session.SessionType.CLASS); b27.setUpdatedAt(LocalDate.now()); sessionRepository.save(b27);
// BREAK 10:30-11:00
            Session b28 = new Session(); b28.setClassField(classEntity2); b28.setCourse(course3);  b28.setDayOfWeek(4L); b28.setStartAt(LocalTime.of(11,0));  b28.setEndAt(LocalTime.of(11,50)); b28.setSessionType(Session.SessionType.CLASS); b28.setUpdatedAt(LocalDate.now()); sessionRepository.save(b28);
            Session b29 = new Session(); b29.setClassField(classEntity2); b29.setCourse(course10); b29.setDayOfWeek(4L); b29.setStartAt(LocalTime.of(11,50)); b29.setEndAt(LocalTime.of(12,40)); b29.setSessionType(Session.SessionType.CLASS); b29.setUpdatedAt(LocalDate.now()); sessionRepository.save(b29);
            Session b30 = new Session(); b30.setClassField(classEntity2); b30.setCourse(course2);  b30.setDayOfWeek(4L); b30.setStartAt(LocalTime.of(12,40)); b30.setEndAt(LocalTime.of(13,30)); b30.setSessionType(Session.SessionType.CLASS); b30.setUpdatedAt(LocalDate.now()); sessionRepository.save(b30);
// BREAK 1:30-1:50
            Session b31 = new Session(); b31.setClassField(classEntity2); b31.setCourse(course5);  b31.setDayOfWeek(4L); b31.setStartAt(LocalTime.of(13,50)); b31.setEndAt(LocalTime.of(14,40)); b31.setSessionType(Session.SessionType.CLASS); b31.setUpdatedAt(LocalDate.now()); sessionRepository.save(b31);
            Session b32 = new Session(); b32.setClassField(classEntity2); b32.setCourse(course6);  b32.setDayOfWeek(4L); b32.setStartAt(LocalTime.of(14,40)); b32.setEndAt(LocalTime.of(15,30)); b32.setSessionType(Session.SessionType.CLASS); b32.setUpdatedAt(LocalDate.now()); sessionRepository.save(b32);

// ── THURSDAY (12-B) ──
            Session b33 = new Session(); b33.setClassField(classEntity2); b33.setCourse(course9);  b33.setDayOfWeek(5L); b33.setStartAt(LocalTime.of(8,0));   b33.setEndAt(LocalTime.of(8,50));  b33.setSessionType(Session.SessionType.CLASS); b33.setUpdatedAt(LocalDate.now()); sessionRepository.save(b33);
            Session b34 = new Session(); b34.setClassField(classEntity2); b34.setCourse(course6);  b34.setDayOfWeek(5L); b34.setStartAt(LocalTime.of(8,50));  b34.setEndAt(LocalTime.of(9,40));  b34.setSessionType(Session.SessionType.CLASS); b34.setUpdatedAt(LocalDate.now()); sessionRepository.save(b34);
            Session b35 = new Session(); b35.setClassField(classEntity2); b35.setCourse(course2);  b35.setDayOfWeek(5L); b35.setStartAt(LocalTime.of(9,40));  b35.setEndAt(LocalTime.of(10,30)); b35.setSessionType(Session.SessionType.CLASS); b35.setUpdatedAt(LocalDate.now()); sessionRepository.save(b35);
// BREAK 10:30-11:00
            Session b36 = new Session(); b36.setClassField(classEntity2); b36.setCourse(course);   b36.setDayOfWeek(5L); b36.setStartAt(LocalTime.of(11,0));  b36.setEndAt(LocalTime.of(11,50)); b36.setSessionType(Session.SessionType.CLASS); b36.setUpdatedAt(LocalDate.now()); sessionRepository.save(b36);
            Session b37 = new Session(); b37.setClassField(classEntity2); b37.setCourse(course4);  b37.setDayOfWeek(5L); b37.setStartAt(LocalTime.of(11,50)); b37.setEndAt(LocalTime.of(12,40)); b37.setSessionType(Session.SessionType.CLASS); b37.setUpdatedAt(LocalDate.now()); sessionRepository.save(b37);
            Session b38 = new Session(); b38.setClassField(classEntity2); b38.setCourse(course7);  b38.setDayOfWeek(5L); b38.setStartAt(LocalTime.of(12,40)); b38.setEndAt(LocalTime.of(13,30)); b38.setSessionType(Session.SessionType.CLASS); b38.setUpdatedAt(LocalDate.now()); sessionRepository.save(b38);
// BREAK 1:30-1:50
            Session b39 = new Session(); b39.setClassField(classEntity2); b39.setCourse(course10); b39.setDayOfWeek(5L); b39.setStartAt(LocalTime.of(13,50)); b39.setEndAt(LocalTime.of(14,40)); b39.setSessionType(Session.SessionType.CLASS); b39.setUpdatedAt(LocalDate.now()); sessionRepository.save(b39);
            Session b40 = new Session(); b40.setClassField(classEntity2); b40.setCourse(course5);  b40.setDayOfWeek(5L); b40.setStartAt(LocalTime.of(14,40)); b40.setEndAt(LocalTime.of(15,30)); b40.setSessionType(Session.SessionType.CLASS); b40.setUpdatedAt(LocalDate.now()); sessionRepository.save(b40);

// ====================== SESSIONS (MONTH EXAMS — 12-B) ======================
            Session bMonthExam1 = new Session();
            bMonthExam1.setClassField(classEntity2); bMonthExam1.setCourse(course2);
            bMonthExam1.setDayOfWeek(1L); // Sunday
            bMonthExam1.setStartAt(LocalTime.of(9,0)); bMonthExam1.setEndAt(LocalTime.of(11,0));
            bMonthExam1.setSessionType(Session.SessionType.MONTH_EXAM);
            bMonthExam1.setUpdatedAt(LocalDate.of(2026, 4, 26));
            sessionRepository.save(bMonthExam1);

            Session bMonthExam2 = new Session();
            bMonthExam2.setClassField(classEntity2); bMonthExam2.setCourse(course);
            bMonthExam2.setDayOfWeek(2L); // Monday
            bMonthExam2.setStartAt(LocalTime.of(9,0)); bMonthExam2.setEndAt(LocalTime.of(11,0));
            bMonthExam2.setSessionType(Session.SessionType.MONTH_EXAM);
            bMonthExam2.setUpdatedAt(LocalDate.of(2026, 4, 27));
            sessionRepository.save(bMonthExam2);

            Session bMonthExam3 = new Session();
            bMonthExam3.setClassField(classEntity2); bMonthExam3.setCourse(course9);
            bMonthExam3.setDayOfWeek(3L); // Tuesday
            bMonthExam3.setStartAt(LocalTime.of(9,0)); bMonthExam3.setEndAt(LocalTime.of(10,30));
            bMonthExam3.setSessionType(Session.SessionType.MONTH_EXAM);
            bMonthExam3.setUpdatedAt(LocalDate.of(2026, 4, 28));
            sessionRepository.save(bMonthExam3);

            Session bMonthExam4 = new Session();
            bMonthExam4.setClassField(classEntity2); bMonthExam4.setCourse(course10);
            bMonthExam4.setDayOfWeek(3L); // Tuesday
            bMonthExam4.setStartAt(LocalTime.of(11,0)); bMonthExam4.setEndAt(LocalTime.of(12,30));
            bMonthExam4.setSessionType(Session.SessionType.MONTH_EXAM);
            bMonthExam4.setUpdatedAt(LocalDate.of(2026, 4, 28));
            sessionRepository.save(bMonthExam4);

            Session bMonthExam5 = new Session();
            bMonthExam5.setClassField(classEntity2); bMonthExam5.setCourse(course5);
            bMonthExam5.setDayOfWeek(5L); // Thursday
            bMonthExam5.setStartAt(LocalTime.of(9,0)); bMonthExam5.setEndAt(LocalTime.of(11,0));
            bMonthExam5.setSessionType(Session.SessionType.MONTH_EXAM);
            bMonthExam5.setUpdatedAt(LocalDate.of(2026, 4, 30));
            sessionRepository.save(bMonthExam5);

// ====================== SESSIONS (FINAL EXAMS — 12-B) ======================
            Session bFinalExam1 = new Session();
            bFinalExam1.setClassField(classEntity2); bFinalExam1.setCourse(course);
            bFinalExam1.setDayOfWeek(1L); // Sunday
            bFinalExam1.setStartAt(LocalTime.of(9,0)); bFinalExam1.setEndAt(LocalTime.of(11,0));
            bFinalExam1.setSessionType(Session.SessionType.FINAL_EXAM);
            bFinalExam1.setUpdatedAt(LocalDate.of(2026, 5, 10));
            sessionRepository.save(bFinalExam1);

            Session bFinalExam2 = new Session();
            bFinalExam2.setClassField(classEntity2); bFinalExam2.setCourse(course2);
            bFinalExam2.setDayOfWeek(1L); // Sunday
            bFinalExam2.setStartAt(LocalTime.of(11,30)); bFinalExam2.setEndAt(LocalTime.of(12,30));
            bFinalExam2.setSessionType(Session.SessionType.FINAL_EXAM);
            bFinalExam2.setUpdatedAt(LocalDate.of(2026, 5, 10));
            sessionRepository.save(bFinalExam2);

            Session bFinalExam3 = new Session();
            bFinalExam3.setClassField(classEntity2); bFinalExam3.setCourse(course9);
            bFinalExam3.setDayOfWeek(3L); // Tuesday
            bFinalExam3.setStartAt(LocalTime.of(9,0)); bFinalExam3.setEndAt(LocalTime.of(10,30));
            bFinalExam3.setSessionType(Session.SessionType.FINAL_EXAM);
            bFinalExam3.setUpdatedAt(LocalDate.of(2026, 5, 12));
            sessionRepository.save(bFinalExam3);

            Session bFinalExam4 = new Session();
            bFinalExam4.setClassField(classEntity2); bFinalExam4.setCourse(course10);
            bFinalExam4.setDayOfWeek(3L); // Tuesday
            bFinalExam4.setStartAt(LocalTime.of(11,0)); bFinalExam4.setEndAt(LocalTime.of(13,0));
            bFinalExam4.setSessionType(Session.SessionType.FINAL_EXAM);
            bFinalExam4.setUpdatedAt(LocalDate.of(2026, 5, 12));
            sessionRepository.save(bFinalExam4);

            Session bFinalExam5 = new Session();
            bFinalExam5.setClassField(classEntity2); bFinalExam5.setCourse(course5);
            bFinalExam5.setDayOfWeek(5L); // Thursday
            bFinalExam5.setStartAt(LocalTime.of(9,0)); bFinalExam5.setEndAt(LocalTime.of(11,0));
            bFinalExam5.setSessionType(Session.SessionType.FINAL_EXAM);
            bFinalExam5.setUpdatedAt(LocalDate.of(2026, 5, 14));
            sessionRepository.save(bFinalExam5);

            Session bFinalExam6 = new Session();
            bFinalExam6.setClassField(classEntity2); bFinalExam6.setCourse(course6);
            bFinalExam6.setDayOfWeek(1L); // Sunday
            bFinalExam6.setStartAt(LocalTime.of(9,0)); bFinalExam6.setEndAt(LocalTime.of(10,30));
            bFinalExam6.setSessionType(Session.SessionType.FINAL_EXAM);
            bFinalExam6.setUpdatedAt(LocalDate.of(2026, 5, 17));
            sessionRepository.save(bFinalExam6);

            Session bFinalExam7 = new Session();
            bFinalExam7.setClassField(classEntity2); bFinalExam7.setCourse(course7);
            bFinalExam7.setDayOfWeek(3L); // Tuesday
            bFinalExam7.setStartAt(LocalTime.of(9,0)); bFinalExam7.setEndAt(LocalTime.of(12,0));
            bFinalExam7.setSessionType(Session.SessionType.FINAL_EXAM);
            bFinalExam7.setUpdatedAt(LocalDate.of(2026, 5, 19));
            sessionRepository.save(bFinalExam7);

// ====================== SESSIONS (MONTH EXAMS — 12-A classEntity) ======================
// Month exams use updatedAt as the exam DATE — this is what groupByDate() reads
            Session monthExam1 = new Session();
            monthExam1.setClassField(classEntity); monthExam1.setCourse(course);
            monthExam1.setDayOfWeek(1L); // Sunday
            monthExam1.setStartAt(LocalTime.of(9,0)); monthExam1.setEndAt(LocalTime.of(11,0));
            monthExam1.setSessionType(Session.SessionType.MONTH_EXAM);
            monthExam1.setUpdatedAt(LocalDate.of(2026, 4, 24)); // Sunday 24 Apr
            sessionRepository.save(monthExam1);

            Session monthExam2 = new Session();
            monthExam2.setClassField(classEntity); monthExam2.setCourse(course2);
            monthExam2.setDayOfWeek(2L); // Monday
            monthExam2.setStartAt(LocalTime.of(9,0)); monthExam2.setEndAt(LocalTime.of(11,0));
            monthExam2.setSessionType(Session.SessionType.MONTH_EXAM);
            monthExam2.setUpdatedAt(LocalDate.of(2026, 4, 27)); // Monday 27 Apr
            sessionRepository.save(monthExam2);

            Session monthExam3 = new Session();
            monthExam3.setClassField(classEntity); monthExam3.setCourse(course3);
            monthExam3.setDayOfWeek(3L); // Tuesday
            monthExam3.setStartAt(LocalTime.of(9,0)); monthExam3.setEndAt(LocalTime.of(10,30));
            monthExam3.setSessionType(Session.SessionType.MONTH_EXAM);
            monthExam3.setUpdatedAt(LocalDate.of(2026, 4, 28)); // Tuesday 28 Apr
            sessionRepository.save(monthExam3);

            Session monthExam4 = new Session();
            monthExam4.setClassField(classEntity); monthExam4.setCourse(course4);
            monthExam4.setDayOfWeek(3L); // Tuesday
            monthExam4.setStartAt(LocalTime.of(11,0)); monthExam4.setEndAt(LocalTime.of(12,30));
            monthExam4.setSessionType(Session.SessionType.MONTH_EXAM);
            monthExam4.setUpdatedAt(LocalDate.of(2026, 4, 28)); // same day, 2nd exam
            sessionRepository.save(monthExam4);

            Session monthExam5 = new Session();
            monthExam5.setClassField(classEntity); monthExam5.setCourse(course5);
            monthExam5.setDayOfWeek(5L); // Thursday
            monthExam5.setStartAt(LocalTime.of(9,0)); monthExam5.setEndAt(LocalTime.of(11,0));
            monthExam5.setSessionType(Session.SessionType.MONTH_EXAM);
            monthExam5.setUpdatedAt(LocalDate.of(2026, 4, 30)); // Thursday 30 Apr
            sessionRepository.save(monthExam5);

// ====================== SESSIONS (FINAL EXAMS) ======================
            Session finalExam1 = new Session();
            finalExam1.setClassField(classEntity); finalExam1.setCourse(course);
            finalExam1.setDayOfWeek(1L); // Sunday
            finalExam1.setStartAt(LocalTime.of(9,0)); finalExam1.setEndAt(LocalTime.of(11,0));
            finalExam1.setSessionType(Session.SessionType.FINAL_EXAM);
            finalExam1.setUpdatedAt(LocalDate.of(2026, 5, 10)); // Sunday 10 May
            sessionRepository.save(finalExam1);

            Session finalExam2 = new Session();
            finalExam2.setClassField(classEntity); finalExam2.setCourse(course2);
            finalExam2.setDayOfWeek(1L); // Sunday
            finalExam2.setStartAt(LocalTime.of(11,30)); finalExam2.setEndAt(LocalTime.of(12,30));
            finalExam2.setSessionType(Session.SessionType.FINAL_EXAM);
            finalExam2.setUpdatedAt(LocalDate.of(2026, 5, 10)); // same day, 2nd exam
            sessionRepository.save(finalExam2);

            Session finalExam3 = new Session();
            finalExam3.setClassField(classEntity); finalExam3.setCourse(course3);
            finalExam3.setDayOfWeek(3L); // Tuesday
            finalExam3.setStartAt(LocalTime.of(9,0)); finalExam3.setEndAt(LocalTime.of(10,30));
            finalExam3.setSessionType(Session.SessionType.FINAL_EXAM);
            finalExam3.setUpdatedAt(LocalDate.of(2026, 5, 12)); // Tuesday 12 May
            sessionRepository.save(finalExam3);

            Session finalExam4 = new Session();
            finalExam4.setClassField(classEntity); finalExam4.setCourse(course4);
            finalExam4.setDayOfWeek(3L); // Tuesday
            finalExam4.setStartAt(LocalTime.of(11,0)); finalExam4.setEndAt(LocalTime.of(13,0));
            finalExam4.setSessionType(Session.SessionType.FINAL_EXAM);
            finalExam4.setUpdatedAt(LocalDate.of(2026, 5, 12)); // same day
            sessionRepository.save(finalExam4);

            Session finalExam5 = new Session();
            finalExam5.setClassField(classEntity); finalExam5.setCourse(course5);
            finalExam5.setDayOfWeek(5L); // Thursday
            finalExam5.setStartAt(LocalTime.of(9,0)); finalExam5.setEndAt(LocalTime.of(11,0));
            finalExam5.setSessionType(Session.SessionType.FINAL_EXAM);
            finalExam5.setUpdatedAt(LocalDate.of(2026, 5, 14)); // Thursday 14 May
            sessionRepository.save(finalExam5);

            Session finalExam6 = new Session();
            finalExam6.setClassField(classEntity); finalExam6.setCourse(course6);
            finalExam6.setDayOfWeek(1L); // Sunday
            finalExam6.setStartAt(LocalTime.of(11,30)); finalExam6.setEndAt(LocalTime.of(12,30));
            finalExam6.setSessionType(Session.SessionType.FINAL_EXAM);
            finalExam6.setUpdatedAt(LocalDate.of(2026, 5, 17)); // Sunday 17 May
            finalExam6.setStartAt(LocalTime.of(9,0));
            sessionRepository.save(finalExam6);

            Session finalExam7 = new Session();
            finalExam7.setClassField(classEntity); finalExam7.setCourse(course7);
            finalExam7.setDayOfWeek(3L); // Tuesday
            finalExam7.setStartAt(LocalTime.of(9,0)); finalExam7.setEndAt(LocalTime.of(12,0));
            finalExam7.setSessionType(Session.SessionType.FINAL_EXAM);
            finalExam7.setUpdatedAt(LocalDate.of(2026, 5, 19)); // Tuesday 19 May
            sessionRepository.save(finalExam7);

            System.out.println("=== Sessions saved: " + sessionRepository.count() + " ===");
            // ====================== ATTENDANCE ======================
            // 3 months of attendance for all 5 students
            // Each record linked to the correct Session object by class + day of week + slot
            //
            // 12-B (Fatma, Salma, Loaa): b1-b8(Sun) b9-b16(Mon) b17-b24(Tue) b25-b32(Wed) b33-b39(Thu=7 sessions)
            // 12-A (Bassmala, Jana):     s1-s8(Sun) s9-s16(Mon) s17-s24(Tue) s25-s32(Wed) s33-s39(Thu=7 sessions)
            //
            // Profiles:
            //   Fatma    (student)  12-B  100%
            //   Bassmala (student2) 12-A  ~92%
            //   Salma    (student3) 12-B  ~78%
            //   Loaa     (student4) 12-B  ~65%
            //   Jana     (student5) 12-A  ~88%

            LocalDate attendanceStart = LocalDate.now().minusMonths(3);
            LocalDate attendanceEnd   = LocalDate.now();

            // ── Session arrays by class and day of week ──────────────────────
            // Index [0]=slot1 .. [7]=slot8  (Thu only has 7, index 7 = null for Thu)

            // 12-B Sunday  (dow=7 in Java)
            Session[] bSun = {b1, b2, b3, b4, b5, b6, b7, b8};
            // 12-B Monday  (dow=1)
            Session[] bMon = {b9, b10, b11, b12, b13, b14, b15, b16};
            // 12-B Tuesday (dow=2)
            Session[] bTue = {b17, b18, b19, b20, b21, b22, b23, b24};
            // 12-B Wednesday (dow=3)
            Session[] bWed = {b25, b26, b27, b28, b29, b30, b31, b32};
            // 12-B Thursday (dow=4) — 7 sessions only, slot8 = null
            Session[] bThu = {b33, b34, b35, b36, b37, b38, b39, null};

            // 12-A Sunday  (dow=7)
            Session[] sSun = {s1, s2, s3, s4, s5, s6, s7, s8};
            // 12-A Monday  (dow=1)
            Session[] sMon = {s9, s10, s11, s12, s13, s14, s15, s16};
            // 12-A Tuesday (dow=2)
            Session[] sTue = {s17, s18, s19, s20, s21, s22, s23, s24};
            // 12-A Wednesday (dow=3)
            Session[] sWed = {s25, s26, s27, s28, s29, s30, s31, s32};
            // 12-A Thursday (dow=4) — 7 sessions only, slot8 = null
            Session[] sThu = {s33, s34, s35, s36, s37, s38, s39, null};

            // Helper: get session array for a given class and Java DayOfWeek value
            // dow: 7=Sun 1=Mon 2=Tue 3=Wed 4=Thu

            // ── Fatma (student) — 12-B — 100% all P ─────────────────────────
            {
                char[] weekPattern = {
                        'P','P','P','P','P','P','P','P',  // Sun  slots 1-8
                        'P','P','P','P','P','P','P','P',  // Mon
                        'P','P','P','P','P','P','P','P',  // Tue
                        'P','P','P','P','P','P','P','P',  // Wed
                        'P','P','P','P','P','P','P','P'   // Thu  (slot8 skipped if null)
                };
                Session[][] schedule = {bSun, bMon, bTue, bWed, bThu};
                for (LocalDate d = attendanceStart; !d.isAfter(attendanceEnd); d = d.plusDays(1)) {
                    int dow = d.getDayOfWeek().getValue();
                    if (dow == 7 || dow <= 4) {
                        int schoolDay = (dow == 7) ? 0 : dow;
                        Session[] daySessions = schedule[schoolDay];
                        for (int slot = 0; slot < 8; slot++) {
                            if (daySessions[slot] == null) continue; // Thu slot8 skip
                            attendanceRepository.save(Attendance.builder()
                                    .student(student)
                                    .session(daySessions[slot])
                                    .status(weekPattern[schoolDay * 8 + slot])
                                    .dateTime(d.atTime(daySessions[slot].getStartAt()))
                                    .build());
                        }
                    }
                }
            }

            // ── Bassmala (student2) — 12-A — ~92% ───────────────────────────
            {
                char[] weekPattern = {
                        'P','P','P','P','P','P','P','P',  // Sun
                        'P','P','P','P','P','P','P','L',  // Mon  1L
                        'P','P','P','P','P','P','P','P',  // Tue
                        'P','P','L','P','P','P','P','P',  // Wed  1L
                        'P','P','P','A','P','P','P','P'   // Thu  1A
                };
                Session[][] schedule = {sSun, sMon, sTue, sWed, sThu};
                for (LocalDate d = attendanceStart; !d.isAfter(attendanceEnd); d = d.plusDays(1)) {
                    int dow = d.getDayOfWeek().getValue();
                    if (dow == 7 || dow <= 4) {
                        int schoolDay = (dow == 7) ? 0 : dow;
                        Session[] daySessions = schedule[schoolDay];
                        for (int slot = 0; slot < 8; slot++) {
                            if (daySessions[slot] == null) continue;
                            attendanceRepository.save(Attendance.builder()
                                    .student(student2)
                                    .session(daySessions[slot])
                                    .status(weekPattern[schoolDay * 8 + slot])
                                    .dateTime(d.atTime(daySessions[slot].getStartAt()))
                                    .build());
                        }
                    }
                }
            }

            // ── Salma (student3) — 12-B — ~78% ──────────────────────────────
            {
                char[] weekPattern = {
                        'P','P','P','L','P','P','A','P',  // Sun
                        'P','P','A','P','P','L','P','P',  // Mon
                        'P','A','P','P','P','P','L','P',  // Tue
                        'P','P','P','P','A','P','P','P',  // Wed
                        'P','L','P','P','P','A','P','P'   // Thu
                };
                Session[][] schedule = {bSun, bMon, bTue, bWed, bThu};
                for (LocalDate d = attendanceStart; !d.isAfter(attendanceEnd); d = d.plusDays(1)) {
                    int dow = d.getDayOfWeek().getValue();
                    if (dow == 7 || dow <= 4) {
                        int schoolDay = (dow == 7) ? 0 : dow;
                        Session[] daySessions = schedule[schoolDay];
                        for (int slot = 0; slot < 8; slot++) {
                            if (daySessions[slot] == null) continue;
                            attendanceRepository.save(Attendance.builder()
                                    .student(student3)
                                    .session(daySessions[slot])
                                    .status(weekPattern[schoolDay * 8 + slot])
                                    .dateTime(d.atTime(daySessions[slot].getStartAt()))
                                    .build());
                        }
                    }
                }
            }

            // ── Loaa (student4) — 12-B — ~65% ───────────────────────────────
            {
                char[] weekPattern = {
                        'P','A','P','L','P','P','A','P',  // Sun
                        'P','P','A','P','L','P','A','P',  // Mon
                        'A','P','P','P','P','L','P','A',  // Tue
                        'P','P','A','P','P','P','L','P',  // Wed
                        'P','A','P','P','A','P','P','L'   // Thu
                };
                Session[][] schedule = {bSun, bMon, bTue, bWed, bThu};
                for (LocalDate d = attendanceStart; !d.isAfter(attendanceEnd); d = d.plusDays(1)) {
                    int dow = d.getDayOfWeek().getValue();
                    if (dow == 7 || dow <= 4) {
                        int schoolDay = (dow == 7) ? 0 : dow;
                        Session[] daySessions = schedule[schoolDay];
                        for (int slot = 0; slot < 8; slot++) {
                            if (daySessions[slot] == null) continue;
                            attendanceRepository.save(Attendance.builder()
                                    .student(student4)
                                    .session(daySessions[slot])
                                    .status(weekPattern[schoolDay * 8 + slot])
                                    .dateTime(d.atTime(daySessions[slot].getStartAt()))
                                    .build());
                        }
                    }
                }
            }

            // ── Jana (student5) — 12-A — ~88% ───────────────────────────────
            {
                char[] weekPattern = {
                        'P','P','P','P','P','P','P','A',  // Sun
                        'P','P','P','P','P','P','P','P',  // Mon
                        'P','P','A','P','P','P','P','P',  // Tue
                        'P','L','P','P','P','P','P','P',  // Wed
                        'P','P','P','P','L','P','A','P'   // Thu
                };
                Session[][] schedule = {sSun, sMon, sTue, sWed, sThu};
                for (LocalDate d = attendanceStart; !d.isAfter(attendanceEnd); d = d.plusDays(1)) {
                    int dow = d.getDayOfWeek().getValue();
                    if (dow == 7 || dow <= 4) {
                        int schoolDay = (dow == 7) ? 0 : dow;
                        Session[] daySessions = schedule[schoolDay];
                        for (int slot = 0; slot < 8; slot++) {
                            if (daySessions[slot] == null) continue;
                            attendanceRepository.save(Attendance.builder()
                                    .student(student5)
                                    .session(daySessions[slot])
                                    .status(weekPattern[schoolDay * 8 + slot])
                                    .dateTime(d.atTime(daySessions[slot].getStartAt()))
                                    .build());
                        }
                    }
                }
            }

            System.out.println("=== Attendance saved: " + attendanceRepository.count() + " ===");
// ====================== STUDENT PHONE NUMBERS ======================
            if (userPhoneNumberRepository.count() == 0) {

                // Fatma — 2 numbers
                savePhone(userPhoneNumberRepository, studentUser, 1008617188L);
                savePhone(userPhoneNumberRepository, studentUser, 1091128806L);

                // Bassmala
                savePhone(userPhoneNumberRepository, studentUser2, 1225557116L);

                // Salma
                savePhone(userPhoneNumberRepository, studentUser3, 1207718642L);

                // Loaa
                savePhone(userPhoneNumberRepository, studentUser4, 1278543900L);

                // Jana
                savePhone(userPhoneNumberRepository, studentUser5, 1067725608L);

                System.out.println("Student phone numbers seeded.");
            }



        } else {
            System.out.println("=== Data already exists, skipping ===");
        }
    }
    private void savePhone(UserPhoneNumberRepository repo, User user, long phone) {
        UserPhoneNumberId id = new UserPhoneNumberId();
        id.setUserId(user.getId());
        id.setPhoneNumber(phone);

        UserPhoneNumber upn = new UserPhoneNumber();
        upn.setId(id);
        upn.setUser(user);

        repo.save(upn);
    }
}