

package org.amadejsky.studentsmaven.controller;

import jakarta.validation.Valid;
import org.amadejsky.studentsmaven.repository.StudentRepository;
import org.amadejsky.studentsmaven.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    private StudentRepository repository;

    @Autowired
    public StudentController(StudentRepository repository) {
        this.repository = repository;
    }

    //    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    @GetMapping()
    public List<Student> getStudents() {
        return repository.findAll();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Student addStudent(@RequestBody @Valid Student student) {
        return repository.save(student);
    }

    //    @GetMapping("/{id}")
//    public ResponseEntity<Student> getStudent(@PathVariable Long id){
//        Optional<Student> student = repository.findById(id);
//        if(student.isPresent()){
//            return ResponseEntity.ok(student.get());
//        };
//        return ResponseEntity.notFound().build();
//    }
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Optional<Student> studentOptional = repository.findById(id);
        return studentOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //    @DeleteMapping("/{id}")
//    public ResponseEntity<Student> deleteStudent(@PathVariable Long id){
//        Optional<Student> studentOptional = repository.findById(id);
//        if(studentOptional.isPresent()){
//            repository.deleteById(id);
//            return ResponseEntity.noContent().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        return repository.findById(id)
                .map(student -> {
                    repository.delete(student);
                    return ResponseEntity.ok().build();
                }).orElseGet( () -> ResponseEntity.notFound().build());

    }


}