package com.example.librarymanagement.controller;

import com.example.librarymanagement.controller.model.Course;
import com.example.librarymanagement.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LibraryController {

    private final CourseService courseService;

    public LibraryController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/")
    public String home() {
        return "Library Management System is Running";
    }

    @GetMapping("/courses")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @PostMapping("/courses")
    public Course addCourse(@RequestBody Course course) {
        return courseService.addCourse(course);
    }

    @PutMapping("/courses/{id}")
    public Course updateCourse(
            @PathVariable Long id,
            @RequestBody Course course) {
        return courseService.updateCourse(id, course);
    }

    @DeleteMapping("/courses/{id}")
    public String deleteCourse(@PathVariable Long id) {
        if (courseService.deleteCourse(id)) {
            return "Course deleted successfully";
        }
        return "Course not found";
    }
}