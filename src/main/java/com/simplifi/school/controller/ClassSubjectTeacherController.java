package com.simplifi.school.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simplifi.school.entity.ClassRoom;
import com.simplifi.school.entity.ClassSubjectTeacher;
import com.simplifi.school.entity.Subject;
import com.simplifi.school.entity.Teacher;
import com.simplifi.school.repository.ClassRoomRepository;
import com.simplifi.school.repository.ClassSubjectTeacherRepository;
import com.simplifi.school.repository.SubjectRepository;
import com.simplifi.school.repository.TeacherRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/assignments")
@Tag(name = "Class-Subject-Teacher Assignment", description = "Operations for assigning teachers to subjects in classes")
public class ClassSubjectTeacherController {

    private final ClassSubjectTeacherRepository assignmentRepository;
    private final ClassRoomRepository classRoomRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;

    @Autowired
    public ClassSubjectTeacherController(ClassSubjectTeacherRepository assignmentRepository,
                                      ClassRoomRepository classRoomRepository,
                                      SubjectRepository subjectRepository,
                                      TeacherRepository teacherRepository) {
        this.assignmentRepository = assignmentRepository;
        this.classRoomRepository = classRoomRepository;
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
    }

    @GetMapping("/class/{classId}")
    @Operation(summary = "Get subjects for class", description = "Retrieve all subject assignments for a specific class")
    public List<ClassSubjectTeacher> getSubjectsForClass(@PathVariable Long classId) {
        return assignmentRepository.findByClassRoomId(classId);
    }

    @GetMapping("/subject/{subjectId}")
    @Operation(summary = "Get classes for subject", description = "Retrieve all class assignments for a specific subject")
    public List<ClassSubjectTeacher> getClassesForSubject(@PathVariable Long subjectId) {
        return assignmentRepository.findBySubjectId(subjectId);
    }

    @GetMapping("/teacher/{teacherId}")
    @Operation(summary = "Get assignments for teacher", description = "Retrieve all assignments for a specific teacher")
    public List<ClassSubjectTeacher> getAssignmentsForTeacher(@PathVariable Long teacherId) {
        return assignmentRepository.findByTeacherId(teacherId);
    }

    @PostMapping
    @Operation(summary = "Create assignment", description = "Assign a teacher to teach a subject in a class")
    public ClassSubjectTeacher createAssignment(@RequestBody ClassSubjectTeacher assignment) {
        ClassRoom classRoom = classRoomRepository.findById(assignment.getClassRoom().getId())
                .orElseThrow(() -> new RuntimeException("Class not found with id: " + assignment.getClassRoom().getId()));
        Subject subject = subjectRepository.findById(assignment.getSubject().getId())
                .orElseThrow(() -> new RuntimeException("Subject not found with id: " + assignment.getSubject().getId()));
        Teacher teacher = teacherRepository.findById(assignment.getTeacher().getId())
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + assignment.getTeacher().getId()));
        
        assignment.setClassRoom(classRoom);
        assignment.setSubject(subject);
        assignment.setTeacher(teacher);
        
        return assignmentRepository.save(assignment);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete assignment", description = "Remove a teacher-subject-class assignment")
    public ResponseEntity<?> deleteAssignment(@PathVariable Long id) {
        return assignmentRepository.findById(id)
                .map(assignment -> {
                    assignmentRepository.delete(assignment);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}