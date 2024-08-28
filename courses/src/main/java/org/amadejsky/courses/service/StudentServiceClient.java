package org.amadejsky.courses.service;

import org.amadejsky.courses.model.dto.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "STUDENT-SERVICE")
public interface StudentServiceClient {
    @GetMapping("/students")
    List<Student> getStudents();
}
