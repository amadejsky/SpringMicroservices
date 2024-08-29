package org.amadejsky.courses.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDto {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String email;
    @NotNull
    private Status status;

    public enum Status{
        ACTIVE,
        INACTIVE
    }
}
