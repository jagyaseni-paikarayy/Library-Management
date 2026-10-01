package com.example.librarymanagement.service;

import com.example.librarymanagement.controller.model.Course;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {

    private final List<Course> courses = new ArrayList<>();

    public List<Course> getAllCourses() {
        return courses;
    }

    public Course addCourse(Course course) {
        courses.add(course);
        return course;
    }

    public Course updateCourse(Long id, Course updatedCourse) {
        for (Course course : courses) {
            if (course.getId().equals(id)) {
                course.setName(updatedCourse.getName());
                course.setInstructor(updatedCourse.getInstructor());
                return course;
            }
        }
        return null;
    }

    public boolean deleteCourse(Long id) {
        return courses.removeIf(course -> course.getId().equals(id));
    }
} 