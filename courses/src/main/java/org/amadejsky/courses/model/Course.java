package org.amadejsky.courses.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.amadejsky.courses.exception.CourseError;
import org.amadejsky.courses.exception.CourseException;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document
@Getter
@Setter
public class Course {
    @Id
    private String code;
    @NotBlank
    private String name;
    private String description;
    @NotNull
    @Future
    private LocalDateTime startDate;
    @NotNull
    @Future
    private LocalDateTime endDate;
    @Min(0)
    private Long participantsLimit;
    @NotNull
    @Min(0)
    private Long getParticipantsCounter;
    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status;
    private List<CourseMember> courseMemberList = new ArrayList<>();

    public enum Status{
        ACTIVE,
        INACTIVE,
        FULL
    }
    public void validate(){
        validateParticipantsLimit();
        validateCourseDate();
        validateStatus();
        validateActive();
    }
    private void validateActive(){
        if(getParticipantsLimit().equals(getGetParticipantsCounter()) && Status.ACTIVE.equals(status)){
            throw new CourseException(CourseError.COURSE_ACTIVE_ERROR);
        }
    }
    public void incrementParticipantNumber(){
        getParticipantsCounter++;
        if(getGetParticipantsCounter().equals(getParticipantsLimit())){
            setStatus(Course.Status.FULL);
        }
    }
    private void validateCourseDate(){
        if(startDate.isAfter(endDate)){
            throw new CourseException(CourseError.COURSE_START_DATE_INVALID);
        }
    }
    private void validateParticipantsLimit(){
        if(getParticipantsLimit()<getParticipantsCounter){
            throw new CourseException(CourseError.COURSE_PARTICIPANTS_AMOUNT_EXCEEEDED);
        }
    }
    private void validateStatus(){
        if(Status.FULL.equals(status)&& !getParticipantsCounter.equals(participantsLimit)){
            throw new CourseException(CourseError.COURSE_STATUS_ERROR);
        }
    }


}
