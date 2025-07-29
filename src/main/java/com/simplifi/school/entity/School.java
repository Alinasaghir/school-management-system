package com.simplifi.school.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "school")
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    private String phone;
    private String email;

    @Column(name = "founded_date")
    @Temporal(TemporalType.DATE)
    private Date foundedDate;

    @Column(name = "principal_name")
    private String principalName;

    private String website;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Student> students;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Teacher> teachers;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ClassRoom> classes;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Subject> subjects;

    // Constructors
    public School() {}

    public School(String name, String address) {
        this.name = name;
        this.address = address;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Date getFoundedDate() { return foundedDate; }
    public void setFoundedDate(Date foundedDate) { this.foundedDate = foundedDate; }

    public String getPrincipalName() { return principalName; }
    public void setPrincipalName(String principalName) { this.principalName = principalName; }

    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }

    public List<Student> getStudents() { return students; }
    public void setStudents(List<Student> students) { this.students = students; }

    public List<Teacher> getTeachers() { return teachers; }
    public void setTeachers(List<Teacher> teachers) { this.teachers = teachers; }

    public List<ClassRoom> getClasses() { return classes; }
    public void setClasses(List<ClassRoom> classes) { this.classes = classes; }

    public List<Subject> getSubjects() { return subjects; }
    public void setSubjects(List<Subject> subjects) { this.subjects = subjects; }
}