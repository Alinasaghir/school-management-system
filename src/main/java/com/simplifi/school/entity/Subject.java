package com.simplifi.school.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "subject")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @OneToMany(mappedBy = "subject")
    @JsonIgnore
    private List<ClassSubjectTeacher> classSubjectTeachers;

    // Constructors
    public Subject() {}

    public Subject(String name, School school) {
        this.name = name;
        this.school = school;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public School getSchool() { return school; }
    public void setSchool(School school) { this.school = school; }

    public List<ClassSubjectTeacher> getClassSubjectTeachers() { return classSubjectTeachers; }
    public void setClassSubjectTeachers(List<ClassSubjectTeacher> classSubjectTeachers) {
        this.classSubjectTeachers = classSubjectTeachers;
    }
}