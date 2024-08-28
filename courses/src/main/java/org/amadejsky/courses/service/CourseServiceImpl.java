package org.amadejsky.courses.service;

import org.amadejsky.courses.exception.CourseError;
import org.amadejsky.courses.exception.CourseException;
import org.amadejsky.courses.model.Course;
import org.amadejsky.courses.repository.CourseRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        if(course.getStartDate().isBefore(LocalDateTime.now())){
            throw new CourseException(CourseError.COURSE_START_DATE_INVALID);
        }
        course.setStartDate(course.getStartDate());
        if(course.getEndDate().isBefore(course.getStartDate())){
            throw new CourseException(CourseError.COURSE_END_DATE_INVALID);
        }
        course.setEndDate(course.getEndDate());
        course.setParticipantsLimit(course.getParticipantsLimit());
        if(course.getGetParticipantsCounter()>course.getParticipantsLimit()){
            throw new CourseException(CourseError.COURSE_PARTICIPANTS_AMOUNT_EXCEEEDED);
        }
        return courseRepository.save(course);
    }

    @Override
    public void deleteCourse(String code) {
        Course course = courseRepository.findById(code)
                .orElseThrow(()-> new CourseException(CourseError.COURSE_NOT_FOUND));
        course.setStatus(Course.Status.INACTIVE);
        courseRepository.save(course);
    }

    @Override
    public Course putCourse(String id, Course course) {
        Course courseFromDb = courseRepository.findById(id)
                .orElseThrow(()->new CourseException(CourseError.COURSE_NOT_FOUND));
        courseFromDb.setName(course.getName());
        courseFromDb.setDescription(course.getDescription());
        if(course.getStartDate().isBefore(LocalDateTime.now())){
            throw new CourseException(CourseError.COURSE_START_DATE_INVALID);
        }
        courseFromDb.setStartDate(course.getStartDate());
        if(course.getEndDate().isBefore(course.getStartDate())){
            throw new CourseException(CourseError.COURSE_END_DATE_INVALID);
        }
        courseFromDb.setEndDate(course.getEndDate());
        courseFromDb.setParticipantsLimit(course.getParticipantsLimit());
        if(course.getGetParticipantsCounter()>course.getParticipantsLimit()){
            throw new CourseException(CourseError.COURSE_PARTICIPANTS_AMOUNT_EXCEEEDED);
        }
        courseFromDb.setGetParticipantsCounter(course.getGetParticipantsCounter());
        courseFromDb.setStatus(course.getStatus());
        return courseRepository.save(courseFromDb);

    }

    @Override
    public Course patchCourse(String id, Course course) {
        Course courseFromDb = courseRepository.findById(id)
                .orElseThrow(()->new CourseException(CourseError.COURSE_NOT_FOUND));
        if(!StringUtils.isEmpty(course.getName())){
            courseFromDb.setName(course.getName());
        }
        if(!StringUtils.isEmpty(course.getDescription())){
            courseFromDb.setDescription(course.getDescription());
        }
        if(course.getStartDate() != null ){
            if(course.getStartDate().isBefore(LocalDateTime.now())){
                throw new CourseException(CourseError.COURSE_START_DATE_INVALID);
            }
            courseFromDb.setStartDate(course.getStartDate());
        }
        if(course.getEndDate() != null){
            if(course.getEndDate().isBefore(course.getStartDate())){
                throw new CourseException(CourseError.COURSE_END_DATE_INVALID);
            }
            courseFromDb.setEndDate(course.getEndDate());
        }
        if(course.getParticipantsLimit() !=null){
            if(course.getParticipantsLimit()<0){
                throw new CourseException(CourseError.COURSE_PARTICIPANTS_LIMIT_NEGATIVE);
            }
            courseFromDb.setParticipantsLimit(course.getParticipantsLimit());
        }
        if(course.getGetParticipantsCounter()!=null){
            if(course.getParticipantsLimit()<0){
                throw new CourseException(CourseError.COURSE_PARTICIPANTS_AMOUNT_NEGATIVE);
            }
            if(course.getGetParticipantsCounter()>course.getParticipantsLimit()){
                throw new CourseException(CourseError.COURSE_PARTICIPANTS_AMOUNT_EXCEEEDED);
            }
            courseFromDb.setGetParticipantsCounter(course.getGetParticipantsCounter());
        }
        courseFromDb.setStatus(course.getStatus());
        return courseRepository.save(courseFromDb);
    }

}
