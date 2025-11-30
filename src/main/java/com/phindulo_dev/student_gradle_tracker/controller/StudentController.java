package com.phindulo_dev.student_gradle_tracker.controller;

import com.phindulo_dev.student_gradle_tracker.model.Grade;
import com.phindulo_dev.student_gradle_tracker.model.Student;
import com.phindulo_dev.student_gradle_tracker.model.StudentResponse;
import com.phindulo_dev.student_gradle_tracker.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /* get all the students */
    @GetMapping
    public List<StudentResponse> getAllStudents(){
        return studentService.getStudents().stream()
                .map(StudentResponse::new)
                .toList();
    }

    /* get student by ID */
    @GetMapping("/{id}")
    public StudentResponse getStudentById(@PathVariable long id){
        Student student = studentService.getStudentById(id);
        return student != null ? new StudentResponse(student) : null;
    }

    /* get student by student id */
    @GetMapping("/student-id/{studentid}")
    public StudentResponse getStudentByStudentId(@PathVariable String studentid){
        return studentService.getStudentByStudentId(studentid) != null ? new StudentResponse(studentService.getStudentByStudentId(studentid)) : null;
    }

    /* add new student */
    @PostMapping
    public StudentResponse addStudent(@RequestBody Student student){
        return studentService.addStudent(student) != null ? new StudentResponse(studentService.addStudent(student)) : null;
    }


    /* add student grade */
    @GetMapping("/{studentId}/grades")
    public Grade addGradeToStudent(@PathVariable long studentId,@RequestBody Grade grade){
        return studentService.addGradeToStudent(studentId,grade);
    }


    /* get improving students */
    @GetMapping("/improving")
    public List<StudentResponse> getImprovingStudents(){
        return studentService.getimprovingStudents().stream()
                .map(StudentResponse::new)
                .toList();
    }

    /* get improving students by semester */
    @GetMapping("/improving/{semester}")
    public List<StudentResponse> getImprovingStudents(@PathVariable int semester){
        return studentService.getimprovingStudentsBySemester(semester).stream()
                .map(StudentResponse::new)
                .toList();
    }

    /* get honor roll students */
    @GetMapping("/honor-roll")
    public List<StudentResponse> getHonorRollStudents(){
        return studentService.getHonorRollStudents().stream()
                .map(StudentResponse::new).toList();
    }

    /* get at risk students */
    @GetMapping("/at-risk")
    public List<StudentResponse> getAtRiskStudents(){
        return studentService.getAtRiskStudents().stream()
                .map(StudentResponse::new).toList();
    }

    /* get students with largest grade change */
    @GetMapping("/largest-grade-change")
    public StudentResponse getLargestChangeStudent(){
        return studentService.getStudentWithLargestGradeChange() != null ?
                new StudentResponse(studentService.getStudentWithLargestGradeChange())
                : null;
    }

    // GET students by major
    @GetMapping("/major/{major}")
    public List<Student> getStudentsByMajor(@PathVariable String major) {
        return studentService.getStudentsByMajor(major).stream()
                .filter(student -> student.getMajor().equalsIgnoreCase(major))
                .toList();
    }

    // GET class average GPA
    @GetMapping("/class-average")
    public String getClassAverageGPA() {
        double average = studentService.getClassAverageGPA();
        return String.format("Class Average GPA: %.2f", average);
    }

    // GET student statistics
    @GetMapping("/statistics")
    public String getStatistics() {
        int totalStudents = studentService.getStudents().size();
        int improvingStudents = studentService.getimprovingStudents().size();
        int honorRollStudents = studentService.getHonorRollStudents().size();
        int atRiskStudents = studentService.getAtRiskStudents().size();
        double classAverage = studentService.getClassAverageGPA();

        return String.format("""
            Student Statistics: \n
            - Total Students: %d \n
            - Improving Students: %d \n
            - Honor Roll Students: %d \n
            - At-Risk Students: %d \n
            - Class Average GPA: %.2f \n
            """, totalStudents, improvingStudents, honorRollStudents, atRiskStudents, classAverage);
    }

    // GET - Welcome endpoint
    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to Student Grade Tracker API!";
    }

    // GET - Health check
    @GetMapping("/health")
    public String health() {
        return "Student Grade Tracker API is running smoothly!";
    }
}
