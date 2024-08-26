package org.amadejsky.studentsmaven.exception;

public enum StudentError {
    STUDENT_NOT_FOUND("Student does not exist!"),
    STUDENT_EMAIL_ALREADY_IN_USE("Given email already binded to another acount!");

    private String message;

    StudentError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
