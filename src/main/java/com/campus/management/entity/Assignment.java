package com.campus.management.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "assignments")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String subject;
    private String description;
    private LocalDate dueDate;
    private Integer totalMarks;
    private String branch;
    private Integer semester;
    private String facultyName;

    public Assignment() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public Integer getTotalMarks() { return totalMarks; }
    public void setTotalMarks(Integer totalMarks) { this.totalMarks = totalMarks; }

    public String getBranch() { return branch; }
    public void setBranch(String branch) { this.branch = branch; }

    public Integer getSemester() { return semester; }
    public void setSemester(Integer semester) { this.semester = semester; }

    public String getFacultyName() { return facultyName; }
    public void setFacultyName(String facultyName) { this.facultyName = facultyName; }
}
