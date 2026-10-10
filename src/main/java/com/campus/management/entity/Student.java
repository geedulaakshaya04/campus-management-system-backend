package com.campus.management.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(unique = true, nullable = false)
    private String rollNumber;

    private String branch;
    private Integer semester;
    private String section;
    private Double cgpa;
    private Integer attendancePercentage;
    private Integer backlogs;
    private LocalDate dateOfBirth;
    private String address;

    public Student() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getRollNumber() { return rollNumber; }
    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }

    public String getBranch() { return branch; }
    public void setBranch(String branch) { this.branch = branch; }

    public Integer getSemester() { return semester; }
    public void setSemester(Integer semester) { this.semester = semester; }

    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }

    public Double getCgpa() { return cgpa; }
    public void setCgpa(Double cgpa) { this.cgpa = cgpa; }

    public Integer getAttendancePercentage() { return attendancePercentage; }
    public void setAttendancePercentage(Integer attendancePercentage) { this.attendancePercentage = attendancePercentage; }

    public Integer getBacklogs() { return backlogs; }
    public void setBacklogs(Integer backlogs) { this.backlogs = backlogs; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
