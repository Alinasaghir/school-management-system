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
import com.simplifi.school.repository.SchoolRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/schools")
@Tag(name = "School Management", description = "Operations pertaining to schools")
public class SchoolController {

    private final SchoolRepository schoolRepository;

    @Autowired
    public SchoolController(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @GetMapping
    @Operation(summary = "Get all schools", description = "Retrieve a list of all schools")
    public List<School> getAllSchools() {
        return schoolRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get school by ID", description = "Retrieve a specific school by its ID")
    public ResponseEntity<School> getSchoolById(@PathVariable Long id) {
        return schoolRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new school", description = "Add a new school to the system")
    public School createSchool(@RequestBody School school) {
        return schoolRepository.save(school);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update school", description = "Update an existing school's information")
    public ResponseEntity<School> updateSchool(@PathVariable Long id, @RequestBody School schoolDetails) {
        return schoolRepository.findById(id)
                .map(school -> {
                    school.setName(schoolDetails.getName());
                    school.setAddress(schoolDetails.getAddress());
                    school.setPhone(schoolDetails.getPhone());
                    school.setEmail(schoolDetails.getEmail());
                    school.setFoundedDate(schoolDetails.getFoundedDate());
                    school.setPrincipalName(schoolDetails.getPrincipalName());
                    school.setWebsite(schoolDetails.getWebsite());
                    School updatedSchool = schoolRepository.save(school);
                    return ResponseEntity.ok(updatedSchool);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete school", description = "Remove a school from the system")
    public ResponseEntity<?> deleteSchool(@PathVariable Long id) {
        return schoolRepository.findById(id)
                .map(school -> {
                    schoolRepository.delete(school);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}