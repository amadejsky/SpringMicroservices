package org.amadejsky.courses.controller;

import jakarta.validation.Valid;
import org.amadejsky.courses.model.Course;
import org.amadejsky.courses.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }
    @GetMapping
    public List<Course> getCourses(@RequestParam(required = false)Course.Status status){
        return courseService.getCourses(status);
    }
    @PostMapping
    public Course addCourse(@Valid @RequestBody Course course){
        return courseService.addCourse(course);
    }
    @GetMapping("/{code}")
    public Course getCourse(@PathVariable String code){
        return courseService.getCourse(code);
    }
}
