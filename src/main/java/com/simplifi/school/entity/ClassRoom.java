package com.simplifi.school.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "class_room")
public class ClassRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @Column(name = "academic_year", nullable = false)
    private String academicYear;

    @Column(name = "room_number")
    private String roomNumber;

    @ManyToOne
    @JoinColumn(name = "class_teacher_id")
    private Teacher classTeacher;

    @OneToMany(mappedBy = "classRoom")
    @JsonIgnore
    private List<Student> students;

    @OneToMany(mappedBy = "classRoom")
    @JsonIgnore
    private List<ClassSubjectTeacher> classSubjectTeachers;

    // Constructors
    public ClassRoom() {}

    public ClassRoom(String name, School school, String academicYear) {
        this.name = name;
        this.school = school;
        this.academicYear = academicYear;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public School getSchool() { return school; }
    public void setSchool(School school) { this.school = school; }

    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public Teacher getClassTeacher() { return classTeacher; }
    public void setClassTeacher(Teacher classTeacher) { this.classTeacher = classTeacher; }

    public List<Student> getStudents() { return students; }
    public void setStudents(List<Student> students) { this.students = students; }

    public List<ClassSubjectTeacher> getClassSubjectTeachers() { return classSubjectTeachers; }
    public void setClassSubjectTeachers(List<ClassSubjectTeacher> classSubjectTeachers) { 
        this.classSubjectTeachers = classSubjectTeachers; 
    }
}