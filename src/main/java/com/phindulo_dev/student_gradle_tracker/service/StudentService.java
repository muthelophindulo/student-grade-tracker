package com.phindulo_dev.student_gradle_tracker.service;

import com.phindulo_dev.student_gradle_tracker.model.*;
import com.phindulo_dev.student_gradle_tracker.respiratory.*;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

@Service
public class StudentService {
    private  List<Student> students = new ArrayList<>();
    private long nextId;
    private StudentData sd = new StudentData();

    public StudentService(){
        initialiseSampleDate();
    }

    private void initialiseSampleDate(){
        System.out.println("initialising sample data");


       /*
        // Student 1: Improving grades
        Student student1 = new Student(nextId++, "John Smith", "S1001", "Computer Science");
        student1.getGrades().add(new Grade("Programming 101", 1, 75.0));
        student1.getGrades().add(new Grade("Data Structures", 1, 82.0));
        student1.getGrades().add(new Grade("Algorithms", 2, 88.0));
        student1.getGrades().add(new Grade("Database Systems", 2, 92.0));
        students.add(student1);

        // Student 2: Consistent high performer
        Student student2 = new Student(nextId++, "Sarah Johnson", "S1002", "Mathematics");
        student2.getGrades().add(new Grade("Calculus I", 1, 92.0));
        student2.getGrades().add(new Grade("Linear Algebra", 2, 94.0));
        student2.getGrades().add(new Grade("Statistics", 1, 91.0));
        student2.getGrades().add(new Grade("Discrete Math", 2, 95.0));
        students.add(student2);

        // Student 3: Struggling student
        Student student3 = new Student(nextId++, "Mike Brown", "S1003", "Physics");
        student3.getGrades().add(new Grade("Mechanics", 1, 65.0));
        student3.getGrades().add(new Grade("Electromagnetism", 2, 58.0));
        student3.getGrades().add(new Grade("Thermodynamics", 1, 62.0));
        student3.getGrades().add(new Grade("Quantum Physics", 2, 59.0));
        students.add(student3);

        // Student 4: Mixed performance
        Student student4 = new Student(nextId++, "Emily Davis", "S1004", "Biology");
        student4.getGrades().add(new Grade("Cell Biology", 1, 85.0));
        student4.getGrades().add(new Grade("Genetics", 2, 78.0));
        student4.getGrades().add(new Grade("Ecology", 1, 82.0));
        student4.getGrades().add(new Grade("Biochemistry", 2, 79.0));
        students.add(student4);

        // Student 5: New student with only one grade
        Student student5 = new Student(nextId++, "David Wilson", "S1005", "Biology");
        student5.getGrades().add(new Grade("Organic Chemistry", 1, 87.0));
        students.add(student5);*/

        List<Student> x1 = sd.LoadStudents();

        students.addAll(x1);
        //sd.LoadGrades();


        System.out.println("Sample data initialized with " + students.size() + " students");
        nextId=students.size()+1;
    }

    /* get all the students */
    public List<Student> getStudents(){
        return new ArrayList<>(students);
    }

    /* get student by id */
    public Student getStudentById(long id){
        return students.stream()
                .filter(student -> student.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /* Get student by student ID */
    public Student getStudentByStudentId(String studentId) {
        return students.stream()
                .filter(student -> student.getStudentId().equalsIgnoreCase(studentId))
                .findFirst()
                .orElse(null);
    }

    /* add new student */
    public Student addStudent(Student student){
        student.setId(nextId++);
        students.add(student);
        return student;
    }

    /* ad grade to student */
    public Grade addGradeToStudent(Long studentId,Grade grade){
        Student student = getStudentById(studentId);
        if(student != null && grade != null){
            student.getGrades().add(grade);
            return grade;
        }
        return null;
    }

    /* get student with improving grades */
    public List<Student> getimprovingStudents(){
        return students.stream()
                .filter(Student::isImproving)
                .toList();
    }

    /* get student with improving grades by semester */
    public List<Student> getimprovingStudentsBySemester(int semester){
       List<Student> isImprovingBySemester = new ArrayList<>();

       for(Student x : students){
           if(x.isImprovingBySemester(semester)){
               isImprovingBySemester.add(x);
           }
       }

       return isImprovingBySemester;
    }

    /* Get students on honor roll (GPA >= 3.5) */
    public List<Student> getHonorRollStudents() {
        return students.stream()
                .filter(Student::isOnHonorRoll)
                .toList();
    }

    /* Get students at risk (GPA < 2.0) */
    public List<Student> getAtRiskStudents() {
        return students.stream()
                .filter(Student::isAtRisk)
                .toList();
    }

    // Get student with largest grade change
    public Student getStudentWithLargestGradeChange() {
        return students.stream()
                .filter(student -> student.getGrades().size() >= 2)
                .max((s1, s2) -> Double.compare(s1.getLargestGradeChange(), s2.getLargestGradeChange()))
                .orElse(null);
    }

    // Get students by major
    public List<Student> getStudentsByMajor(String major) {
        return students.stream()
                .filter(student -> student.getMajor().equalsIgnoreCase(major))
                .toList();
    }

    // Calculate class average GPA
    public double getClassAverageGPA() {
        if (students.isEmpty()) {
            return 0.0;
        }

        double totalGPA = students.stream()
                .mapToDouble(Student::calculateGPA)
                .sum();

        return totalGPA / students.size();
    }

    //used to write student on txt file
    public void writeToTxt(Student x){
        try {
            FileWriter fw = new FileWriter("student-gradle-tracker/src/main/resources/students.txt",true);
            PrintWriter pw = new PrintWriter(fw);
            pw.print(x.getId());
            pw.write(";");
            pw.write(x.getName());
            pw.write(";");
            pw.write(x.getStudentId());
            pw.write(";");
            pw.write(x.getMajor());
            pw.write("\n");
            pw.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //used to write the students grades to grade.txt
    public void WriteGrades(String StudNo,String moduleName,int semester,Double score){
        try {
            FileWriter fw = new FileWriter("student-gradle-tracker/src/main/resources/Grades.txt",true);
            PrintWriter pw = new PrintWriter(fw);

            pw.print(StudNo);
            pw.print(";");
            pw.print(moduleName);
            pw.print(";");
            pw.print(semester);
            pw.print(";");
            pw.print(score);
            pw.print("\n");

            pw.close();
            fw.close();
            System.out.println("grade added");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }



    }
}
