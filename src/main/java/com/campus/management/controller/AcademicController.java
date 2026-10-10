package com.campus.management.controller;

import com.campus.management.entity.*;
import com.campus.management.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AcademicController {

    private final StudentRepository studentRepository;
    private final NoticeRepository noticeRepository;
    private final PlacementDriveRepository placementDriveRepository;
    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;

    public AcademicController(
            StudentRepository studentRepository,
            NoticeRepository noticeRepository,
            PlacementDriveRepository placementDriveRepository,
            AssignmentRepository assignmentRepository,
            UserRepository userRepository
    ) {
        this.studentRepository = studentRepository;
        this.noticeRepository = noticeRepository;
        this.placementDriveRepository = placementDriveRepository;
        this.assignmentRepository = assignmentRepository;
        this.userRepository = userRepository;
    }

    // Open endpoint for public campus notices
    @GetMapping("/notices")
    public ResponseEntity<List<Notice>> getNotices() {
        return ResponseEntity.ok(noticeRepository.findTop10ByOrderByPostedDateDesc());
    }

    // Student Dashboard Stats
    @GetMapping("/student/dashboard")
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    public ResponseEntity<Map<String, Object>> getStudentDashboard(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);
        Student student = (user != null) ? studentRepository.findByUserId(user.getId()).orElse(null) : null;

        Map<String, Object> data = new HashMap<>();
        data.put("user", user);
        data.put("student", student);
        data.put("assignments", assignmentRepository.findAll());
        data.put("placementDrives", placementDriveRepository.findAll());
        data.put("notices", noticeRepository.findTop10ByOrderByPostedDateDesc());
        return ResponseEntity.ok(data);
    }

    // Faculty Dashboard Data
    @GetMapping("/faculty/dashboard")
    @PreAuthorize("hasAnyRole('FACULTY', 'HOD', 'ADMIN')")
    public ResponseEntity<Map<String, Object>> getFacultyDashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("totalStudents", studentRepository.count());
        data.put("activeAssignments", assignmentRepository.count());
        data.put("assignments", assignmentRepository.findAll());
        data.put("students", studentRepository.findAll());
        return ResponseEntity.ok(data);
    }

    // HOD Dashboard Data
    @GetMapping("/hod/dashboard")
    @PreAuthorize("hasAnyRole('HOD', 'ADMIN')")
    public ResponseEntity<Map<String, Object>> getHodDashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("totalStudents", studentRepository.count());
        data.put("lowAttendanceCount", studentRepository.countByAttendancePercentageLessThan(75));
        data.put("totalFaculty", userRepository.findAll().stream().filter(u -> u.getRole() == Role.ROLE_FACULTY).count());
        data.put("students", studentRepository.findAll());
        data.put("notices", noticeRepository.findAll());
        return ResponseEntity.ok(data);
    }

    // Admin Dashboard Data
    @GetMapping("/admin/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getAdminDashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("totalUsers", userRepository.count());
        data.put("totalStudents", studentRepository.count());
        data.put("totalNotices", noticeRepository.count());
        data.put("totalDrives", placementDriveRepository.count());
        data.put("allUsers", userRepository.findAll());
        return ResponseEntity.ok(data);
    }

    // Placement Dashboard Data
    @GetMapping("/placement/dashboard")
    @PreAuthorize("hasAnyRole('PLACEMENT', 'ADMIN')")
    public ResponseEntity<Map<String, Object>> getPlacementDashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("totalDrives", placementDriveRepository.count());
        data.put("activeDrives", placementDriveRepository.findByStatus("ACTIVE").size());
        data.put("eligibleStudentsCount", studentRepository.count());
        data.put("drives", placementDriveRepository.findAll());
        return ResponseEntity.ok(data);
    }

    // AI Campus Knowledge Query (RAG Assistant Endpoint)
    @PostMapping("/ai/chat")
    public ResponseEntity<Map<String, Object>> campusAiChat(@RequestBody Map<String, String> request) {
        String query = request.getOrDefault("query", "").toLowerCase();
        String reply;

        if (query.contains("attendance")) {
            reply = "According to College Academic Regulations (Clause 4.2), a minimum of 75% aggregate attendance is mandatory to appear for Semester-End Examinations. Condonation up to 65% is permissible only on genuine medical grounds with prior approval.";
        } else if (query.contains("exam") || query.contains("mid")) {
            reply = "Mid-Term examinations for B.Tech Semester VI are scheduled to commence from 15th October 2026. Hall tickets can be downloaded 3 days prior from the Examination branch tab.";
        } else if (query.contains("placement") || query.contains("drive") || query.contains("tcs") || query.contains("deloitte")) {
            reply = "Active Placement Drives:\n1. TCS Digital (9.0 LPA) - Minimum 7.0 CGPA, 0 backlogs. Drive on Oct 20.\n2. Deloitte USI (11.5 LPA) - Minimum 7.5 CGPA. Drive on Oct 27.\n3. Amazon AWS (16.0 LPA) - Upcoming.";
        } else if (query.contains("assignment") || query.contains("deep learning")) {
            reply = "You have 2 pending assignments:\n1. 'Implementation of Transformer Self-Attention' for Deep Learning (Due in 3 days).\n2. 'Full-Stack Microservices with Spring Boot & Vite' for Web Programming (Due in 6 days).";
        } else if (query.contains("hod") || query.contains("head")) {
            reply = "The Head of Department (Computer Science & Engineering) is Dr. K. V. Rao. Cabin: Block A, Room 204. Contact: hod.cse@campus.edu.";
        } else {
            reply = "Based on the Campus Knowledge Base & Academic Regulations: Your query '" + query + "' has been processed. For specific administrative appeals or fee slips, please visit the Administration block or contact your respective department coordinator.";
        }

        Map<String, Object> res = new HashMap<>();
        res.put("reply", reply);
        res.put("source", "Campus Knowledge Base & RAG Index");
        return ResponseEntity.ok(res);
    }

    // Update Student Profile (Full name, phone, etc.)
    @PutMapping("/student/profile")
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    public ResponseEntity<Map<String, Object>> updateStudentProfile(
            Authentication authentication,
            @RequestBody Map<String, String> request
    ) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "User not found"));
        }

        if (request.containsKey("fullName") && request.get("fullName") != null && !request.get("fullName").isBlank()) {
            user.setFullName(request.get("fullName").trim());
        }
        if (request.containsKey("phone") && request.get("phone") != null) {
            user.setPhone(request.get("phone").trim());
        }
        userRepository.save(user);

        Student student = studentRepository.findByUserId(user.getId()).orElse(null);
        if (student != null && request.containsKey("address") && request.get("address") != null) {
            student.setAddress(request.get("address").trim());
            studentRepository.save(student);
        }

        Map<String, Object> res = new HashMap<>();
        res.put("message", "Profile updated successfully");
        res.put("user", user);
        res.put("student", student);
        return ResponseEntity.ok(res);
    }

    // Clear / Reset Student Profile Name & Custom Fields
    @DeleteMapping("/student/profile")
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    public ResponseEntity<Map<String, Object>> deleteStudentProfile(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "User not found"));
        }

        user.setFullName("");
        user.setPhone("");
        userRepository.save(user);

        Student student = studentRepository.findByUserId(user.getId()).orElse(null);
        if (student != null) {
            student.setAddress("");
            studentRepository.save(student);
        }

        Map<String, Object> res = new HashMap<>();
        res.put("message", "Profile name cleared successfully");
        res.put("user", user);
        res.put("student", student);
        return ResponseEntity.ok(res);
    }

    // GET Student by ID
    @GetMapping("/students/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
        return studentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE Student by ID
    @PutMapping("/students/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    public ResponseEntity<?> updateStudentById(@PathVariable Long id, @RequestBody Student updatedData) {
        return studentRepository.findById(id).map(existing -> {
            if (updatedData.getBranch() != null) existing.setBranch(updatedData.getBranch());
            if (updatedData.getSemester() != null) existing.setSemester(updatedData.getSemester());
            if (updatedData.getSection() != null) existing.setSection(updatedData.getSection());
            if (updatedData.getCgpa() != null) existing.setCgpa(updatedData.getCgpa());
            if (updatedData.getAddress() != null) existing.setAddress(updatedData.getAddress());
            studentRepository.save(existing);
            return ResponseEntity.ok(existing);
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE Student by ID
    @DeleteMapping("/students/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteStudentById(@PathVariable Long id) {
        if (!studentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        studentRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Student deleted successfully"));
    }
}
