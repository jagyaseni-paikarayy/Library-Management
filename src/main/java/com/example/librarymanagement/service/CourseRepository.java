package com.example.librarymanagement.service;

import com.example.librarymanagement.controller.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}