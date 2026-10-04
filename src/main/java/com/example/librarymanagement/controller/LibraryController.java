package com.example.librarymanagement.controller;

import com.example.librarymanagement.controller.model.Course;
import com.example.librarymanagement.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class LibraryController {

    private final CourseService courseService;

    public LibraryController(CourseService courseService) {
        this.courseService = courseService;
    }

    // Home page
    @GetMapping("/")
    public String home(Model model) {
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        return "index";
    }

    // Add course from browser form
    @PostMapping("/courses/add")
    public String addCourse(
            @RequestParam Long id,
            @RequestParam String name,
            @RequestParam String instructor) {

        Course course = new Course(id, name, instructor);
        courseService.addCourse(course);

        return "redirect:/";
    }

    // View all courses
    @GetMapping("/courses")
    public String viewCourses(Model model) {
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        return "courses";
    }

    // Delete course
    @GetMapping("/courses/delete/{id}")
    public String deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return "redirect:/";
    }

    // Update course
    @PostMapping("/courses/update/{id}")
    public String updateCourse(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam String instructor) {

        Course course = new Course(id, name, instructor);
        courseService.updateCourse(id, course);

        return "redirect:/";
    }
}