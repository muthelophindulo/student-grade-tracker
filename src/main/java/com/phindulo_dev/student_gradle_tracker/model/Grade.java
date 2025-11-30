package com.phindulo_dev.student_gradle_tracker.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Grade {
    private String courseName;
    private int semester;
    private double score;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;

    public Grade(){}

    public Grade(
        String courseName,
        int semester,
        double score
    ){
        this.courseName = courseName;
        this.semester = semester;
        this.score = score;
        this.date = new Date();

    }

    public Grade(
            String courseName,
            int semester,
            double score,
            Date date
    ){
        this.courseName = courseName;
        this.semester = semester;
        this.score = score;
        this.date = date;

    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getSemester() {
        return semester;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getScore() {
        return score;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    // Business methods
    public String getLetterGrade() {
        if (score >= 90) return "A";
        if (score >= 80) return "B";
        if (score >= 70) return "C";
        if (score >= 50) return "D";
        return "F";
    }

    public boolean isPassing() {
        return score >= 60;
    }

    @Override
    public String toString() {
        return String.format("%s - Semester %d: %.1f (%s)", courseName, semester, score, getLetterGrade());
    }

}
