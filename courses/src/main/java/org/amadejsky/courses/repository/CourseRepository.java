package org.amadejsky.courses.repository;

import org.amadejsky.courses.model.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CourseRepository extends MongoRepository<Course,String> {
    List<Course> findAllByStatus(Course.Status status);
}
