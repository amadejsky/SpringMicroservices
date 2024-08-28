package org.amadejsky.courses.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CourseExceptionHandler {
    @ExceptionHandler(value = CourseException.class)
    public ResponseEntity<ErrorInfo> handleException(CourseException e){
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if(CourseError.COURSE_NOT_FOUND.equals(e.getCourseError()))
            httpStatus = HttpStatus.NOT_FOUND;
        else if(CourseError.COURSE_END_DATE_INVALID.equals(e.getCourseError()))
            httpStatus = HttpStatus.BAD_REQUEST;
        else if(CourseError.COURSE_PARTICIPANTS_AMOUNT_EXCEEEDED.equals(e.getCourseError()))
            httpStatus = HttpStatus.CONFLICT;
        else if(CourseError.COURSE_STATUS_ERROR.equals(e.getCourseError()))
            httpStatus = HttpStatus.CONFLICT;
        else if(CourseError.COURSE_ACTIVE_ERROR.equals(e.getCourseError()))
            httpStatus = HttpStatus.CONFLICT;
        return ResponseEntity.status(httpStatus).body(new ErrorInfo(e.getCourseError().getMessage()));
    }
}
