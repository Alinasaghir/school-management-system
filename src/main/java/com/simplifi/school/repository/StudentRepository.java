package com.simplifi.school.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simplifi.school.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findBySchoolId(Long schoolId);
    List<Student> findByClassRoomId(Long classId);
}