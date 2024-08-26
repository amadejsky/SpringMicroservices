package org.amadejsky.studentsmaven.exception;

public enum StudentError {
    STUDENT_NOT_FOUND("Student does not exist!");

    private String message;

    StudentError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
