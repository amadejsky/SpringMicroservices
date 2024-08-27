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
        if(e.getStudentError().equals(CourseError.COURSE_NOT_FOUND))
            httpStatus = HttpStatus.NOT_FOUND;
        else if(e.getStudentError().equals(CourseError.COURSE_EMAIL_ALREADY_IN_USE))
            httpStatus = HttpStatus.CONFLICT;
        else if(CourseError.COURSE_ACCOUNT_IS_INACTIVE.equals(e.getStudentError()))
            httpStatus = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(httpStatus).body(new ErrorInfo(e.getStudentError().getMessage()));
    }
}
