package com.phindulo_dev.student_gradle_tracker.controller;

import com.phindulo_dev.student_gradle_tracker.model.Student;
import com.phindulo_dev.student_gradle_tracker.model.StudentResponse;
import com.phindulo_dev.student_gradle_tracker.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class WebController {
    
    private final StudentService studentService;
    
    public WebController(StudentService studentService) {
        this.studentService = studentService;
    }
    
    // Home page show all students
    @GetMapping("/")
    public String homePage(Model model) {
        List<StudentResponse> students = studentService.getStudents().stream()
                .map(StudentResponse::new)
                .toList();
        
        model.addAttribute("students", students);
        model.addAttribute("totalStudents", students.size());
        model.addAttribute("classAverage", String.format("%.2f", studentService.getClassAverageGPA()));
        model.addAttribute("honorRollCount", studentService.getHonorRollStudents().size());
        model.addAttribute("atRiskCount", studentService.getAtRiskStudents().size());
        
        return "index";
    }
    
    // Improving students page
    @GetMapping("/improving")
    public String improvingStudentsPage(Model model) {
        List<StudentResponse> improvingStudents = studentService.getimprovingStudents().stream()
                .map(StudentResponse::new)
                .toList();
        
        model.addAttribute("students", improvingStudents);
        model.addAttribute("pageTitle", "Improving Students");
        return "students-list";
    }
    
    // Honor roll students page
    @GetMapping("/honor-roll")
    public String honorRollStudentsPage(Model model) {
        List<StudentResponse> honorRollStudents = studentService.getHonorRollStudents().stream()
                .map(StudentResponse::new)
                .toList();
        
        model.addAttribute("students", honorRollStudents);
        model.addAttribute("pageTitle", "Honor Roll Students");
        return "students-list";
    }
    
    // Student details page
    @GetMapping("/students/{id}")
    public String studentDetailsPage(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id);
        if (student != null) {
            model.addAttribute("student", new StudentResponse(student));
            return "student-details";
        }
        return "redirect:/";
    }
    
    // Handle form submission for adding students
    @PostMapping("/students")
    public String addStudent(@RequestParam String name, 
                           @RequestParam String studentId,
                           @RequestParam String major) {
        Student student = new Student();
        student.setName(name);
        student.setStudentId(studentId);
        student.setMajor(major);


        
        studentService.addStudent(student);
        studentService.writeToTxt(student);
        return "redirect:/";
    }
    
    // Add grade to student
    @PostMapping("/students/{id}/grades-web")
    public String addGrade(@PathVariable Long id,
                          @RequestParam String courseName,
                          @RequestParam int semester,
                          @RequestParam double score) {
        studentService.addGradeToStudent(id, new com.phindulo_dev.student_gradle_tracker.model.Grade(courseName, semester, score));

        //get student by id
        Student x = studentService.getStudentById(id);

        //get the student number
        String stdNo = x.getStudentId();

        //add the grades to grades.txt
        studentService.WriteGrades(stdNo,courseName,semester,score);
        return "redirect:/students/" + id;
    }
}