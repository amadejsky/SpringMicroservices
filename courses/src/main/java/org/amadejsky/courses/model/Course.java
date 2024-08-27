package org.amadejsky.courses.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
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

    public enum Status{
        ACTIVE,
        INACTIVE,
        FULL
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Long getParticipantsLimit() {
        return participantsLimit;
    }

    public void setParticipantsLimit(Long participantsLimit) {
        this.participantsLimit = participantsLimit;
    }

    public Long getGetParticipantsCounter() {
        return getParticipantsCounter;
    }

    public void setGetParticipantsCounter(Long getParticipantsCounter) {
        this.getParticipantsCounter = getParticipantsCounter;
    }
}
