package com.example.librarymanagement.controller;

import com.example.librarymanagement.controller.model.Course;
import com.example.librarymanagement.service.CourseService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseApiController {

private final CourseService courseService;

public CourseApiController(CourseService courseService) {
    this.courseService = courseService;
}

@GetMapping
public ResponseEntity<List<Course>> getAllCourses() {
    return ResponseEntity.ok(courseService.getAllCourses());
}

@PostMapping
public ResponseEntity<?> addCourse(@RequestBody Course course) {
    if (course.getId() == null
            || course.getName() == null
            || course.getName().isBlank()
            || course.getInstructor() == null
            || course.getInstructor().isBlank()) {
        return ResponseEntity.badRequest()
                .body("Course ID, name, and instructor are required.");
    }

    try {
        Course savedCourse = courseService.addCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCourse);
    } catch (org.springframework.dao.DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("A course with this ID may already exist.");
    }
}

@DeleteMapping("/{id}")
public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
    boolean deleted = courseService.deleteCourse(id);

    if (deleted) {
        return ResponseEntity.noContent().build();
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("Course not found.");
}

@PutMapping("/{id}")
public ResponseEntity<?> updateCourse(
        @PathVariable Long id,
        @RequestBody Course updatedCourse) {

    if (updatedCourse.getName() == null
            || updatedCourse.getName().isBlank()
            || updatedCourse.getInstructor() == null
            || updatedCourse.getInstructor().isBlank()) {
        return ResponseEntity.badRequest()
                .body("Course name and instructor are required.");
    }

    Course course = courseService.updateCourse(id, updatedCourse);

    if (course == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Course not found.");
    }

    return ResponseEntity.ok(course);
}

}