package org.amadejsky.courses.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class CourseMember {
    @NotNull
    private LocalDateTime enrollmentData;
    @NotNull
    private String email;

    public CourseMember(@NotNull String email) {
        this.enrollmentData = LocalDateTime.now();
        this.email = email;
    }
}
