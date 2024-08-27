package org.amadejsky.courses.service;

import org.amadejsky.courses.exception.CourseError;
import org.amadejsky.courses.exception.CourseException;
import org.amadejsky.courses.model.Course;
import org.amadejsky.courses.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> getCourses(Course.Status status) {
        if(status!=null)
            return courseRepository.findAllByStatus(status);
        return courseRepository.findAll();
    }

    @Override
    public Course getCourse(String code) {
        return courseRepository.findById(code)
                .orElseThrow(() -> new CourseException(CourseError.COURSE_NOT_FOUND));
    }

    @Override
    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }
}
