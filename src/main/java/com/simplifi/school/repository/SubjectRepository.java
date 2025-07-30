package com.simplifi.school.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simplifi.school.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findBySchoolId(Long schoolId);
}