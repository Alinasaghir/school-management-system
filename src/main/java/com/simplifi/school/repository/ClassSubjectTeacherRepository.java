package com.simplifi.school.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simplifi.school.entity.ClassSubjectTeacher;

public interface ClassSubjectTeacherRepository extends JpaRepository<ClassSubjectTeacher, Long> {
    List<ClassSubjectTeacher> findByClassRoomId(Long classId);
    List<ClassSubjectTeacher> findBySubjectId(Long subjectId);
    List<ClassSubjectTeacher> findByTeacherId(Long teacherId);
}