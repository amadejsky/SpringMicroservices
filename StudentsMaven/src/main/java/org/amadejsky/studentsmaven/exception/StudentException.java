package org.amadejsky.studentsmaven.exception;

public class StudentException extends RuntimeException{
    private StudentError studentError;

    public StudentError getStudentError() {
        return studentError;
    }

    public void setStudentError(StudentError studentError) {
        this.studentError = studentError;
    }

    public StudentException(StudentError studentError){
        this.studentError = studentError;
    }
}
