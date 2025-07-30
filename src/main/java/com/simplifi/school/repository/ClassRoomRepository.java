package com.simplifi.school.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simplifi.school.entity.ClassRoom;

public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long> {
    List<ClassRoom> findBySchoolId(Long schoolId);
}