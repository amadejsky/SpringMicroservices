package org.amadejsky.studentsmaven.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@SequenceGenerator(name="seqIdGen", initialValue = 20000, allocationSize = 1)
@Getter
@Setter
@ToString
//@Table(name="students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqIdGen")
    private Long id;
    @NotBlank(message = "First name cannot be null!")
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Column(unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status;

    public enum Status{
        ACTIVE,
        INACTIVE
    }


}
