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

import com.simplifi.school.entity.School;
import com.simplifi.school.entity.Teacher;
import com.simplifi.school.repository.SchoolRepository;
import com.simplifi.school.repository.TeacherRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/teachers")
@Tag(name = "Teacher Management", description = "Operations pertaining to teachers")
public class TeacherController {

    private final TeacherRepository teacherRepository;
    private final SchoolRepository schoolRepository;

    @Autowired
    public TeacherController(TeacherRepository teacherRepository, SchoolRepository schoolRepository) {
        this.teacherRepository = teacherRepository;
        this.schoolRepository = schoolRepository;
    }

    @GetMapping
    @Operation(summary = "Get all teachers", description = "Retrieve a list of all teachers")
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get teacher by ID", description = "Retrieve a specific teacher by its ID")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable Long id) {
        return teacherRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/school/{schoolId}")
    @Operation(summary = "Get teachers by school", description = "Retrieve teachers belonging to a specific school")
    public List<Teacher> getTeachersBySchool(@PathVariable Long schoolId) {
        return teacherRepository.findBySchoolId(schoolId);
    }

    @PostMapping("/school/{schoolId}")
    @Operation(summary = "Create teacher", description = "Add a new teacher to a specific school")
    public Teacher createTeacher(@RequestBody Teacher teacher, @PathVariable Long schoolId) {
        School school = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new RuntimeException("School not found with id: " + schoolId));
        teacher.setSchool(school);
        return teacherRepository.save(teacher);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update teacher", description = "Update an existing teacher's information")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable Long id, @RequestBody Teacher teacherDetails) {
        return teacherRepository.findById(id)
                .map(teacher -> {
                    teacher.setFirstName(teacherDetails.getFirstName());
                    teacher.setLastName(teacherDetails.getLastName());
                    teacher.setEmail(teacherDetails.getEmail());
                    teacher.setPhone(teacherDetails.getPhone());
                    teacher.setDateOfBirth(teacherDetails.getDateOfBirth());
                    teacher.setGender(teacherDetails.getGender());
                    teacher.setQualification(teacherDetails.getQualification());
                    teacher.setSpecialization(teacherDetails.getSpecialization());
                    teacher.setJoiningDate(teacherDetails.getJoiningDate());
                    Teacher updatedTeacher = teacherRepository.save(teacher);
                    return ResponseEntity.ok(updatedTeacher);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete teacher", description = "Remove a teacher from the system")
    public ResponseEntity<?> deleteTeacher(@PathVariable Long id) {
        return teacherRepository.findById(id)
                .map(teacher -> {
                    teacherRepository.delete(teacher);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}