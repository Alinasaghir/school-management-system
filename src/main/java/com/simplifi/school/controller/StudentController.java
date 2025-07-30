package com.simplifi.school.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simplifi.school.entity.Student;
import com.simplifi.school.service.StudentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/students")
@Tag(name = "Student Management", description = "Operations pertaining to students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    @Operation(summary = "Get all students", description = "Retrieve a list of all students")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get student by ID", description = "Retrieve a specific student by its ID")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @GetMapping("/school/{schoolId}")
    @Operation(summary = "Get students by school", description = "Retrieve students belonging to a specific school")
    public List<Student> getStudentsBySchool(@PathVariable Long schoolId) {
        return studentService.getStudentsBySchool(schoolId);
    }

    @GetMapping("/class/{classId}")
    @Operation(summary = "Get students by class", description = "Retrieve students belonging to a specific class")
    public List<Student> getStudentsByClass(@PathVariable Long classId) {
        return studentService.getStudentsByClass(classId);
    }

    @PostMapping("/school/{schoolId}")
    @Operation(summary = "Create student", description = "Add a new student to a specific school")
    public Student createStudent(@RequestBody Student student, @PathVariable Long schoolId) {
        return studentService.createStudent(student, schoolId);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update student", description = "Update an existing student's information")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        return studentService.updateStudent(id, studentDetails);
    }

    @PutMapping("/{studentId}/assign-class/{classId}")
    @Operation(summary = "Assign student to class", description = "Assign a student to a specific class")
    public Student assignStudentToClass(@PathVariable Long studentId, @PathVariable Long classId) {
        return studentService.assignStudentToClass(studentId, classId);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete student", description = "Remove a student from the system")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
}