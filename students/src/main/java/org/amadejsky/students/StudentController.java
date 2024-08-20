package org.amadejsky.students;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    private StudentRepository repository;

    @Autowired
    public StudentController(StudentRepository repository) {
        this.repository = repository;
    }

    //    @RequestMapping(value = "/hello",method = RequestMethod.GET)
@GetMapping(value = "/hello")
    public String sayHello(){
        return "Hello!";
    }
@GetMapping("/student")
    public Student getStudent(){
        Student student = new Student();
        student.setFirstName("Arnold");
        student.setLastName("Boczek");
        student.setEmail("arnold@boczek.pl");
        return student;
    }
@GetMapping("/students")
    public List<Student> getStudents(){
        return repository.findAll();
    }
    @PostMapping("/students")
    public Student addStudent(@RequestBody Student student){
        return repository.save(student);
    }
}
