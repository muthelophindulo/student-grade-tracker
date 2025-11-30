package com.phindulo_dev.student_gradle_tracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Stream;

public class Student {
    private long id;
    private String name;
    private String studentId;
    private String major;
    private List<Grade> grades = new ArrayList<>();

    public Student(){}

    public Student(
            long id,
            String name,
            String studentId,
            String major
    ){
        this.id = id;
        this.name = name;
        this.studentId = studentId;
        this.major = major;
    }

    public Student(
            long id,
            String name,
            String studentId,
            String major,
            List<Grade> grades
    ){
        this.id = id;
        this.name = name;
        this.studentId = studentId;
        this.major = major;
        this.grades = grades;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = (grades != null) ? new ArrayList<>(grades) : new ArrayList<>();
    }

    /* App logic */
    /* calculate GPA */
    @JsonIgnore
    public double calculateGPA(){
        double totalPoints = 0.0;

        for(Grade x : grades){
            totalPoints += convertScoreToGrade(x.getScore());
        }

        return totalPoints / grades.size();
    }

    /* check if student is imporoving */
    @JsonIgnore
    public boolean isImproving(){
        // FIX: Added null and size checks
        if (grades == null || grades.size() < 2) {
            return false; // Need at least 2 grades to compare
        }

        // Sort grades by semester to ensure chronological order
        List<Grade> sortedGrades = grades.stream()
                .filter(grade -> grade != null) // FIX: Filter out null grades
                .sorted(Comparator.comparingInt(Grade::getSemester))
                .toList();


        if (sortedGrades.size() < 2) {
            return false;
        }


        for (int i = 0; i < sortedGrades.size() - 1; i++) {
            Grade currentGrade = sortedGrades.get(i);
            Grade nextGrade = sortedGrades.get(i + 1);


            if (currentGrade == null || nextGrade == null) {
                continue; // Skip if any grade is null
            }

            if (currentGrade.getScore() >= nextGrade.getScore()) {
                return false;
            }
        }
        return true;
    }

    @JsonIgnore
    /* check improving by semester */
    public Boolean isImprovingBySemester(int semester){
        //create an array to only hold the grades of that semester
        List<Grade> SemesterGrades = new ArrayList<>();

        for(int i =0; i < grades.size(); i++){
            if(grades.get(i).getSemester() == semester){
                SemesterGrades.add(grades.get(i));
            }
        }

        //sort the array
        List<Grade> sortedGrades = SemesterGrades.stream()
                .sorted(Comparator.comparing(Grade::getSemester)
                        .thenComparing(Grade::getCourseName))
                .toList();

        for(int i= 0; i < sortedGrades.size()-1;i++){
            if(sortedGrades.get(i).getScore() <= sortedGrades.get(i + 1).getScore()){
                return false;
            }
        }

        return true;

    }

    /* find the largest grade between consecutive semesters */
    @JsonIgnore
    public double getLargestGradeChange(){
        // FIX: Added null and size checks
        if (grades == null || grades.size() < 2) {
            return 0.0;
        }

        List<Grade> sortedGrades = grades.stream()
                .filter(Objects::nonNull) // FIX: Filter out null grades
                .sorted(Comparator.comparingInt(Grade::getSemester))
                .toList();


        if (sortedGrades.size() < 2) {
            return 0.0;
        }

        double largestChange = 0.0;

        for (int i = 0; i < sortedGrades.size() - 1; i++) {
            Grade currentGrade = sortedGrades.get(i);
            Grade nextGrade = sortedGrades.get(i + 1);


            if (currentGrade != null && nextGrade != null) {
                double change = Math.abs(currentGrade.getScore() - nextGrade.getScore());
                if (change > largestChange) {
                    largestChange = change;
                }
            }
        }
        return largestChange;
    }

    /* check if the student is on honor roll GPA >= 3.5 */
    @JsonIgnore
    public boolean isOnHonorRoll(){
        return  calculateGPA() >= 3.5;
    }

    /**
     * Check if student is at risk (GPA < 2.0)
     */
    @JsonIgnore
    public boolean isAtRisk() {
        return calculateGPA() < 2.0;
    }

    /* convert Score To GPA */
    private double convertScoreToGrade(double score){
        if(score >= 90) return 4.0;
        if(score >= 80) return 3.0;
        if(score >= 70) return 2.0;
        if(score >= 50) return 1.0;
        return 0.0;
    }

    public String toString() {
        return String.format("%s (%s) - %s - GPA: %.2f", name, studentId, major, calculateGPA());
    }
}
