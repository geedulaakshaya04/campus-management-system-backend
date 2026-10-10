package com.campus.management.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "placement_drives")
public class PlacementDrive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;
    private String roleName;
    private String packageLPA;
    private Double minCgpa;
    private Integer maxBacklogs;
    private String eligibleBranches;
    private LocalDate driveDate;
    private LocalDate lastDateToApply;
    private String status;
    private String jobLocation;

    public PlacementDrive() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }

    public String getPackageLPA() { return packageLPA; }
    public void setPackageLPA(String packageLPA) { this.packageLPA = packageLPA; }

    public Double getMinCgpa() { return minCgpa; }
    public void setMinCgpa(Double minCgpa) { this.minCgpa = minCgpa; }

    public Integer getMaxBacklogs() { return maxBacklogs; }
    public void setMaxBacklogs(Integer maxBacklogs) { this.maxBacklogs = maxBacklogs; }

    public String getEligibleBranches() { return eligibleBranches; }
    public void setEligibleBranches(String eligibleBranches) { this.eligibleBranches = eligibleBranches; }

    public LocalDate getDriveDate() { return driveDate; }
    public void setDriveDate(LocalDate driveDate) { this.driveDate = driveDate; }

    public LocalDate getLastDateToApply() { return lastDateToApply; }
    public void setLastDateToApply(LocalDate lastDateToApply) { this.lastDateToApply = lastDateToApply; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getJobLocation() { return jobLocation; }
    public void setJobLocation(String jobLocation) { this.jobLocation = jobLocation; }
}
