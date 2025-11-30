package com.phindulo_dev.student_gradle_tracker.respiratory;

import com.phindulo_dev.student_gradle_tracker.model.Grade;
import com.phindulo_dev.student_gradle_tracker.model.Student;
import com.phindulo_dev.student_gradle_tracker.model.StudentResponse;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.io.*;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentData {
    private List<Student> students = new ArrayList<>();
    private List<Grade> grades = new ArrayList<>();

    private List<StudentResponse> studentResponses = new ArrayList<>();

    //load the Students from the txt file
    public List<Student> LoadStudents(){
        try {
            File f = new File("student-gradle-tracker/src/main/resources/students.txt");

            Scanner reader = new Scanner(f);


            while(reader.hasNextLine()){

                String line = reader.nextLine();

                String[] stud = line.split(";");

                Student x = new Student(
                        Long.parseLong(stud[0]),
                        stud[1],
                        stud[2],
                        stud[3]
                        );

                String studId = stud[2];

                students.add(x);
            }
            reader.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        LoadGrades();
        System.out.println("done");

        return students;

    }

    public void LoadGrades(){

        try {
            File f2 = new File("student-gradle-tracker/src/main/resources/Grades.txt");
            Scanner reader2 = new Scanner(f2);

            while (reader2.hasNextLine()){
                String lines = reader2.nextLine();
                String[] line2 = lines.split(";");

                for(Student x : students){
                    //get the studentID
                    String studId = x.getStudentId();

                    if(line2[0].equalsIgnoreCase(studId)){
                        x.getGrades().add(new Grade(line2[1], Integer.parseInt(line2[2]),Double.parseDouble(line2[3])));
                    }
                }
            }
            reader2.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("grades loaded");
        for(Student c : students){
            System.out.println(c);
        }


    }

}
