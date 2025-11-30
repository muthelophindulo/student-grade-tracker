package com.phindulo_dev.student_gradle_tracker.model;

import java.util.List;

public class StudentResponse {
    private Long id;
    private String name;
    private String studentId;
    private String major;
    private List<Grade> grades;
    private double gpa;
    private boolean improving;
    private boolean improvingBySemester;
    private boolean honorRoll;
    private boolean atRisk;
    private double largestGradeChange;

    public StudentResponse(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.studentId = student.getStudentId();
        this.major = student.getMajor();
        this.grades = student.getGrades();
        this.gpa = student.calculateGPA();
        this.improving = student.isImproving();
        this.improvingBySemester = student.isImprovingBySemester(1);
        this.honorRoll = student.isOnHonorRoll();
        this.atRisk = student.isAtRisk();
        this.largestGradeChange = student.getLargestGradeChange();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getStudentId() { return studentId; }
    public String getMajor() { return major; }
    public List<Grade> getGrades() { return grades; }
    public double getGpa() { return gpa; }
    public boolean isImproving() { return improving; }
    public boolean isHonorRoll() { return honorRoll; }
    public boolean isAtRisk() { return atRisk; }
    public double getLargestGradeChange() { return largestGradeChange; }
    public boolean isImprovingBySemester() {
        return improvingBySemester;
    }
}
