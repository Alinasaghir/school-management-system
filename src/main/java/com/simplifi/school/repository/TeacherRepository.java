package com.simplifi.school.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simplifi.school.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    List<Teacher> findBySchoolId(Long schoolId);
}