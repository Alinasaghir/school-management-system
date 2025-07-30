package com.simplifi.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simplifi.school.entity.School;

public interface SchoolRepository extends JpaRepository<School, Long> {
}