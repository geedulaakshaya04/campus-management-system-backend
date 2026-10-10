package com.campus.management.config;

import com.campus.management.entity.*;
import com.campus.management.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class DataSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final NoticeRepository noticeRepository;
    private final PlacementDriveRepository placementDriveRepository;
    private final AssignmentRepository assignmentRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(
            UserRepository userRepository,
            StudentRepository studentRepository,
            NoticeRepository noticeRepository,
            PlacementDriveRepository placementDriveRepository,
            AssignmentRepository assignmentRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.noticeRepository = noticeRepository;
        this.placementDriveRepository = placementDriveRepository;
        this.assignmentRepository = assignmentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        try {
            if (userRepository.count() > 0) {
                log.info("Database already seeded with demo records.");
                return;
            }

        log.info("Seeding initial demo data for all 5 roles...");

        // 1. Admin User
        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@campus.edu");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setFullName("Dr. M. R. Sharma (Director)");
        admin.setRole(Role.ROLE_ADMIN);
        admin.setDepartment("Administration");
        admin.setPhone("+91 9876543210");

        // 2. HOD User
        User hod = new User();
        hod.setUsername("hod_cse");
        hod.setEmail("hod.cse@campus.edu");
        hod.setPassword(passwordEncoder.encode("hod123"));
        hod.setFullName("Dr. K. V. Rao (HOD - CSE)");
        hod.setRole(Role.ROLE_HOD);
        hod.setDepartment("Computer Science & Engineering");
        hod.setPhone("+91 9876543211");

        // 3. Faculty User
        User faculty = new User();
        faculty.setUsername("prof_smith");
        faculty.setEmail("smith@campus.edu");
        faculty.setPassword(passwordEncoder.encode("faculty123"));
        faculty.setFullName("Prof. Rajesh Smith");
        faculty.setRole(Role.ROLE_FACULTY);
        faculty.setDepartment("Computer Science & Engineering");
        faculty.setPhone("+91 9876543212");

        // 4. Placement Officer User
        User placement = new User();
        placement.setUsername("placement_officer");
        placement.setEmail("placement@campus.edu");
        placement.setPassword(passwordEncoder.encode("placement123"));
        placement.setFullName("Mr. Suresh Verma (Head - Corporate Relations)");
        placement.setRole(Role.ROLE_PLACEMENT);
        placement.setDepartment("Training & Placement Cell");
        placement.setPhone("+91 9876543213");

        // 5. Student User
        User studentUser = new User();
        studentUser.setUsername("student");
        studentUser.setEmail("akshaya@campus.edu");
        studentUser.setPassword(passwordEncoder.encode("student123"));
        studentUser.setFullName("Akshaya Narayana");
        studentUser.setRole(Role.ROLE_STUDENT);
        studentUser.setDepartment("Computer Science & Engineering");
        studentUser.setPhone("+91 9876543214");

        userRepository.saveAll(List.of(admin, hod, faculty, placement, studentUser));

        // Create Student Profile
        Student studentProfile = new Student();
        studentProfile.setUser(studentUser);
        studentProfile.setRollNumber("24EG106A18");
        studentProfile.setBranch("Computer Science & Engineering");
        studentProfile.setSemester(6);
        studentProfile.setSection("A");
        studentProfile.setCgpa(8.45);
        studentProfile.setAttendancePercentage(82);
        studentProfile.setBacklogs(0);
        studentProfile.setDateOfBirth(LocalDate.of(2003, 5, 14));
        studentProfile.setAddress("Hyderabad, Telangana");

        studentRepository.save(studentProfile);

        // Seed Sample Campus Notices
        Notice n1 = new Notice();
        n1.setTitle("Mid-Semester Examinations Schedule Released");
        n1.setContent("Mid-Term examinations for B.Tech Semester VI will commence from 15th October 2026. Hall tickets available on portal.");
        n1.setCategory("EXAM");
        n1.setPriority("HIGH");
        n1.setTargetAudience("ALL");
        n1.setPostedBy("Examination Branch");
        n1.setPostedDate(LocalDate.now().minusDays(1));

        Notice n2 = new Notice();
        n2.setTitle("Google Cloud & AI Innovation Hackathon 2026");
        n2.setContent("Register your teams for the 36-hour National Hackathon on Generative AI. Prize pool INR 5,00,000.");
        n2.setCategory("EVENT");
        n2.setPriority("MEDIUM");
        n2.setTargetAudience("STUDENTS");
        n2.setPostedBy("Center for Innovation");
        n2.setPostedDate(LocalDate.now().minusDays(3));

        Notice n3 = new Notice();
        n3.setTitle("TCS Digital On-Campus Recruitment Drive");
        n3.setContent("TCS Digital drive scheduled for 20th October. Eligible students must apply before 10th October.");
        n3.setCategory("PLACEMENT");
        n3.setPriority("HIGH");
        n3.setTargetAudience("STUDENTS");
        n3.setPostedBy("Placement Cell");
        n3.setPostedDate(LocalDate.now().minusDays(2));

        noticeRepository.saveAll(List.of(n1, n2, n3));

        // Seed Sample Placement Drives
        PlacementDrive p1 = new PlacementDrive();
        p1.setCompanyName("TCS Digital");
        p1.setRoleName("Digital Software Engineer");
        p1.setPackageLPA("9.0 LPA");
        p1.setMinCgpa(7.0);
        p1.setMaxBacklogs(0);
        p1.setEligibleBranches("CSE, IT, AI&DS, ECE");
        p1.setDriveDate(LocalDate.now().plusDays(17));
        p1.setLastDateToApply(LocalDate.now().plusDays(7));
        p1.setStatus("ACTIVE");
        p1.setJobLocation("Hyderabad / Bengaluru");

        PlacementDrive p2 = new PlacementDrive();
        p2.setCompanyName("Deloitte USI");
        p2.setRoleName("Analyst - Technology Solutions");
        p2.setPackageLPA("11.5 LPA");
        p2.setMinCgpa(7.5);
        p2.setMaxBacklogs(0);
        p2.setEligibleBranches("CSE, IT, AI&DS");
        p2.setDriveDate(LocalDate.now().plusDays(24));
        p2.setLastDateToApply(LocalDate.now().plusDays(12));
        p2.setStatus("ACTIVE");
        p2.setJobLocation("Hyderabad / Pune");

        PlacementDrive p3 = new PlacementDrive();
        p3.setCompanyName("Amazon AWS");
        p3.setRoleName("Cloud Support Associate");
        p3.setPackageLPA("16.0 LPA");
        p3.setMinCgpa(8.0);
        p3.setMaxBacklogs(0);
        p3.setEligibleBranches("CSE, IT");
        p3.setDriveDate(LocalDate.now().plusDays(35));
        p3.setLastDateToApply(LocalDate.now().plusDays(20));
        p3.setStatus("UPCOMING");
        p3.setJobLocation("Bengaluru");

        placementDriveRepository.saveAll(List.of(p1, p2, p3));

        // Seed Sample Assignments
        Assignment a1 = new Assignment();
        a1.setTitle("Implementation of Transformer Self-Attention");
        a1.setSubject("Deep Learning");
        a1.setDescription("Build Multi-Head Attention layer in PyTorch from scratch with scaled dot-product.");
        a1.setDueDate(LocalDate.now().plusDays(3));
        a1.setTotalMarks(20);
        a1.setBranch("Computer Science & Engineering");
        a1.setSemester(6);
        a1.setFacultyName("Prof. Rajesh Smith");

        Assignment a2 = new Assignment();
        a2.setTitle("Full-Stack Microservices with Spring Boot & Vite");
        a2.setSubject("Web Programming");
        a2.setDescription("Design RESTful JWT authenticated backend communicating with responsive React frontend.");
        a2.setDueDate(LocalDate.now().plusDays(6));
        a2.setTotalMarks(25);
        a2.setBranch("Computer Science & Engineering");
        a2.setSemester(6);
        a2.setFacultyName("Prof. Rajesh Smith");

        assignmentRepository.saveAll(List.of(a1, a2));

        log.info("Demo data seeding completed successfully!");
        } catch (Exception ex) {
            log.warn("Notice: Data seeding skipped or encountered non-fatal error: {}", ex.getMessage());
        }
    }
}
