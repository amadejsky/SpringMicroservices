package org.amadejsky.courses.service;

import org.amadejsky.courses.model.dto.StudentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "STUDENT-SERVICE")
public interface StudentServiceClient {
    @GetMapping("/students")
    List<StudentDto> getStudents();

    @GetMapping("/students/{studentId}")
    StudentDto getStudentById(@PathVariable Long studentId);

    @PostMapping("/students/emails")
    List<StudentDto> getStudentsByEmail(@RequestBody List<String> emails);

}
