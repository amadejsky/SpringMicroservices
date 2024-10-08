package org.amadejsky.courses.exception;

public class CourseException extends RuntimeException{
    private CourseError courseError;

    public CourseError getCourseError() {
        return courseError;
    }

    public void setStudentError(CourseError courseError) {
        this.courseError = courseError;
    }

    public CourseException(CourseError courseError){
        this.courseError = courseError;
    }
}
