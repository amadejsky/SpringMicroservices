package org.amadejsky.courses.service;

import org.amadejsky.courses.exception.CourseError;
import org.amadejsky.courses.exception.CourseException;
import org.amadejsky.courses.model.Course;
import org.amadejsky.courses.model.CourseMember;
import org.amadejsky.courses.model.dto.NotificationDto;
import org.amadejsky.courses.model.dto.StudentDto;
import org.amadejsky.courses.repository.CourseRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final StudentServiceClient studentServiceClient;
    private final RabbitTemplate rabbitTemplate;

    public CourseServiceImpl(CourseRepository courseRepository, StudentServiceClient studentServiceClient, RabbitTemplate rabbitTemplate) {
        this.courseRepository = courseRepository;
        this.studentServiceClient = studentServiceClient;
        this.rabbitTemplate = rabbitTemplate;
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
//        if(course.getStartDate().isBefore(LocalDateTime.now())){
//            throw new CourseException(CourseError.COURSE_START_DATE_INVALID);
//        }
//        course.setStartDate(course.getStartDate());
//        if(course.getEndDate().isBefore(course.getStartDate())){
//            throw new CourseException(CourseError.COURSE_END_DATE_INVALID);
//        }
//        course.setEndDate(course.getEndDate());
//        course.setParticipantsLimit(course.getParticipantsLimit());
//        if(course.getGetParticipantsCounter()>course.getParticipantsLimit()){
//            throw new CourseException(CourseError.COURSE_PARTICIPANTS_AMOUNT_EXCEEEDED);
//        }
        course.validate();
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

    @Override
    public void enrollStudent(Long studentId, String courseCode) {
        Course course = getCourse(courseCode);
        validateCourseStatus(course);
        StudentDto studentDto = studentServiceClient.getStudentById(studentId);
        validateStudentBeforeCourseEnrollment(studentDto, course);
        course.incrementParticipantNumber();
        course.getCourseMemberList().add(new CourseMember(studentDto.getEmail()));
        courseRepository.save(course);
    }

    @Override
    public List<StudentDto> getCourseMemebers(String courseCode) {
        Course course = getCourse(courseCode);
        List<String> emailsMember = course.getCourseMemberList().stream()
                .map(CourseMember::getEmail).collect(Collectors.toList());
        return studentServiceClient.getStudentsByEmail(emailsMember);

    }

    @Override
    public void finishEnroll(String courseCode) {
        Course course = getCourse(courseCode);
        validateFinishEnrollment(course);
        course.setStatus(Course.Status.INACTIVE);
        courseRepository.save(course);

        sendNotificationToRabbitMQ(course);
    }

    private void sendNotificationToRabbitMQ(Course course) {
        NotificationDto notificationDto = createNotification(course);

        rabbitTemplate.convertAndSend("enrollment_finish", notificationDto);
    }

    private static NotificationDto createNotification(Course course) {
        List<String> emailsMember = course.getCourseMemberList().stream()
                .map(CourseMember::getEmail).collect(Collectors.toList());
        NotificationDto notificationDto = NotificationDto.builder()
                        .courseCode(course.getCode())
                                .courseName(course.getName())
                                        .courseDescription(course.getDescription())
                                                .courseStartDate(course.getStartDate())
                                                        .courseEndDate(course.getEndDate())
                                                                .emails(emailsMember)
                                                                        .build();
        return notificationDto;
    }

    private static void validateFinishEnrollment(Course course) {
        if(Course.Status.INACTIVE.equals(course.getStatus())){
            throw new CourseException(CourseError.COURSE_ALREADY_FINISHED);
        }
    }


    private static void validateStudentBeforeCourseEnrollment(StudentDto studentDto, Course course) {
        if(!StudentDto.Status.ACTIVE.equals(studentDto.getStatus())){
            throw new CourseException(CourseError.STUDENT_ACCOUNT_IS_INACTIVE);
        }
        if(course.getCourseMemberList().stream()
                .anyMatch(member-> studentDto.getEmail().equals(member.getEmail()))){
            throw new CourseException(CourseError.STUDENT_ALREADY_ENROLLED);
        }
    }

    private static void validateCourseStatus(Course course) {
        if(!Course.Status.ACTIVE.equals(course.getStatus())){
            throw new CourseException(CourseError.COURSE_IS_NOT_ACTIVE);
        }
    }


}
