package org.amadejsky.courses.service;

import org.amadejsky.courses.model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> getCourses(Course.Status status);
    Course getCourse(String id);
    Course addCourse(Course course);

}
