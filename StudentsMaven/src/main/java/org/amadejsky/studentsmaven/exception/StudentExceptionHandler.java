package org.amadejsky.studentsmaven.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class StudentExceptionHandler {
    @ExceptionHandler(value = StudentException.class)
    public ResponseEntity<ErrorInfo> handleException(StudentException e){
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if(e.getStudentError().equals(StudentError.STUDENT_NOT_FOUND))
            httpStatus = HttpStatus.NOT_FOUND;
        else if(e.getStudentError().equals(StudentError.STUDENT_EMAIL_ALREADY_IN_USE))
            httpStatus = HttpStatus.CONFLICT;
        return ResponseEntity.status(httpStatus).body(new ErrorInfo(e.getStudentError().getMessage()));
    }
}
