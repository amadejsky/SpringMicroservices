package org.amadejsky.courses.exception;

public enum CourseError {
    COURSE_NOT_FOUND("COURSE does not exist!"),
    COURSE_EMAIL_ALREADY_IN_USE("Given email already binded to another acount!"),
    STUDENT_ACCOUNT_IS_INACTIVE("Student account is inactive!"),
    COURSE_START_DATE_INVALID("COURSE Start date cannot be in the PAST!"),
    COURSE_END_DATE_INVALID("COURSE end date cannot be set as before start date!"),
    COURSE_PARTICIPANTS_AMOUNT_NEGATIVE("COURSE participants amount must be positive number!"),
    COURSE_PARTICIPANTS_AMOUNT_EXCEEEDED("COURSE participants amount must match the participation limit!"),
    COURSE_PARTICIPANTS_LIMIT_NEGATIVE("COURSE participants limit must be positive number!"),
    COURSE_STATUS_ERROR("COURSE cannot have full status without reaching participants limit!"),

    COURSE_ACTIVE_ERROR("COURSE with participants limit reacg cannot have ACTIVE status"),
    COURSE_IS_NOT_ACTIVE("COURSE is no longer ACTIVE!"),
    STUDENT_ALREADY_ENROLLED("Student is already enrolled!"),
    COURSE_ALREADY_FINISHED("Course enrollment is already finished!");

    private String message;

    CourseError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
