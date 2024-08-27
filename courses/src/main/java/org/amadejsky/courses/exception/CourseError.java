package org.amadejsky.courses.exception;

public enum CourseError {
    COURSE_NOT_FOUND("COURSE does not exist!"),
    COURSE_EMAIL_ALREADY_IN_USE("Given email already binded to another acount!"),
    COURSE_ACCOUNT_IS_INACTIVE("COURSE account is inactive!");

    private String message;

    CourseError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
