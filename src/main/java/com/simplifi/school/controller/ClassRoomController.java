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

import com.simplifi.school.entity.ClassRoom;
import com.simplifi.school.entity.School;
import com.simplifi.school.entity.Teacher;
import com.simplifi.school.repository.ClassRoomRepository;
import com.simplifi.school.repository.SchoolRepository;
import com.simplifi.school.repository.TeacherRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/classes")
@Tag(name = "Class Management", description = "Operations pertaining to classes")
public class ClassRoomController {

    private final ClassRoomRepository classRoomRepository;
    private final SchoolRepository schoolRepository;
    private final TeacherRepository teacherRepository;

    @Autowired
    public ClassRoomController(ClassRoomRepository classRoomRepository, 
                             SchoolRepository schoolRepository,
                             TeacherRepository teacherRepository) {
        this.classRoomRepository = classRoomRepository;
        this.schoolRepository = schoolRepository;
        this.teacherRepository = teacherRepository;
    }

    @GetMapping
    @Operation(summary = "Get all classes", description = "Retrieve a list of all classes")
    public List<ClassRoom> getAllClasses() {
        return classRoomRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get class by ID", description = "Retrieve a specific class by its ID")
    public ResponseEntity<ClassRoom> getClassById(@PathVariable Long id) {
        return classRoomRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/school/{schoolId}")
    @Operation(summary = "Get classes by school", description = "Retrieve classes belonging to a specific school")
    public List<ClassRoom> getClassesBySchool(@PathVariable Long schoolId) {
        return classRoomRepository.findBySchoolId(schoolId);
    }

    @PostMapping("/school/{schoolId}")
    @Operation(summary = "Create class", description = "Add a new class to a specific school")
    public ClassRoom createClass(@RequestBody ClassRoom classRoom, @PathVariable Long schoolId) {
        School school = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new RuntimeException("School not found with id: " + schoolId));
        classRoom.setSchool(school);
        return classRoomRepository.save(classRoom);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update class", description = "Update an existing class's information")
    public ResponseEntity<ClassRoom> updateClass(@PathVariable Long id, @RequestBody ClassRoom classDetails) {
        return classRoomRepository.findById(id)
                .map(classRoom -> {
                    classRoom.setName(classDetails.getName());
                    classRoom.setAcademicYear(classDetails.getAcademicYear());
                    classRoom.setRoomNumber(classDetails.getRoomNumber());
                    ClassRoom updatedClass = classRoomRepository.save(classRoom);
                    return ResponseEntity.ok(updatedClass);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{classId}/assign-teacher/{teacherId}")
    @Operation(summary = "Assign class teacher", description = "Assign a teacher as the class teacher for a specific class")
    public ResponseEntity<ClassRoom> assignClassTeacher(@PathVariable Long classId, @PathVariable Long teacherId) {
        return classRoomRepository.findById(classId)
                .map(classRoom -> {
                    Teacher teacher = teacherRepository.findById(teacherId)
                            .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + teacherId));
                    classRoom.setClassTeacher(teacher);
                    ClassRoom updatedClass = classRoomRepository.save(classRoom);
                    return ResponseEntity.ok(updatedClass);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete class", description = "Remove a class from the system")
    public ResponseEntity<?> deleteClass(@PathVariable Long id) {
        return classRoomRepository.findById(id)
                .map(classRoom -> {
                    classRoomRepository.delete(classRoom);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}