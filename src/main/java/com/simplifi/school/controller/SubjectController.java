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
import com.simplifi.school.entity.Subject;
import com.simplifi.school.repository.SchoolRepository;
import com.simplifi.school.repository.SubjectRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/subjects")
@Tag(name = "Subject Management", description = "Operations pertaining to subjects")
public class SubjectController {

    private final SubjectRepository subjectRepository;
    private final SchoolRepository schoolRepository;

    @Autowired
    public SubjectController(SubjectRepository subjectRepository, SchoolRepository schoolRepository) {
        this.subjectRepository = subjectRepository;
        this.schoolRepository = schoolRepository;
    }

    @GetMapping
    @Operation(summary = "Get all subjects", description = "Retrieve a list of all subjects")
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get subject by ID", description = "Retrieve a specific subject by its ID")
    public ResponseEntity<Subject> getSubjectById(@PathVariable Long id) {
        return subjectRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/school/{schoolId}")
    @Operation(summary = "Get subjects by school", description = "Retrieve subjects belonging to a specific school")
    public List<Subject> getSubjectsBySchool(@PathVariable Long schoolId) {
        return subjectRepository.findBySchoolId(schoolId);
    }

    @PostMapping("/school/{schoolId}")
    @Operation(summary = "Create subject", description = "Add a new subject to a specific school")
    public Subject createSubject(@RequestBody Subject subject, @PathVariable Long schoolId) {
        School school = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new RuntimeException("School not found with id: " + schoolId));
        subject.setSchool(school);
        return subjectRepository.save(subject);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update subject", description = "Update an existing subject's information")
    public ResponseEntity<Subject> updateSubject(@PathVariable Long id, @RequestBody Subject subjectDetails) {
        return subjectRepository.findById(id)
                .map(subject -> {
                    subject.setName(subjectDetails.getName());
                    subject.setDescription(subjectDetails.getDescription());
                    Subject updatedSubject = subjectRepository.save(subject);
                    return ResponseEntity.ok(updatedSubject);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete subject", description = "Remove a subject from the system")
    public ResponseEntity<?> deleteSubject(@PathVariable Long id) {
        return subjectRepository.findById(id)
                .map(subject -> {
                    subjectRepository.delete(subject);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}