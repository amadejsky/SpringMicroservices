package org.amadejsky.courses.controller;

import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import org.amadejsky.courses.model.Course;
import org.amadejsky.courses.model.dto.StudentDto;
import org.amadejsky.courses.service.CourseService;
import org.amadejsky.courses.service.StudentServiceClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;
    private final StudentServiceClient studentServiceClient;

    public CourseController(CourseService courseService, StudentServiceClient studentServiceClient) {
        this.courseService = courseService;
        this.studentServiceClient = studentServiceClient;
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

    @DeleteMapping("/{code}")
    public void deleteCourse(@PathVariable String code){
        courseService.deleteCourse(code);
    }

//    @GetMapping("/students")
//    public List<Student> getStudents(){
//        return studentServiceClient.getStudents();
//    }
//    @GetMapping("/students/{studentId}")
//    public Student getStudentById(@PathVariable Long studentId) {
//    return studentServiceClient.getStudentById(studentId);
//    }
    @PostMapping("/{courseCode}/student/{studentId}")
    public ResponseEntity<?> courseEnrollment(@PathVariable String courseCode, @PathVariable Long studentId){
        courseService.enrollStudent(studentId, courseCode);
        return ResponseEntity.ok().build();
    }

//    @GetMapping("/{code}/members")
//    public void getCourseMemebers(@PathVariable String code){
//        courseService.getCourseMembers(code);
//    }
    @GetMapping("/emails")
    public List<StudentDto> getStudentsByEmail(@RequestBody List<String> emails){
        return studentServiceClient.getStudentsByEmail(emails);
    }
    @GetMapping("/{code}/members")
    public List<StudentDto> getCourseMembers(@PathVariable String code){
        return courseService.getCourseMemebers(code);
    }
    @PostMapping("/{code}/finish-enroll")
    public ResponseEntity<?> finishCourseEnrollment(@PathVariable String code){
        courseService.finishEnroll(code);
        return ResponseEntity.ok().build();
    }

}
