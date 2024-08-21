

package org.amadejsky.studentsmaven.controller;

import ch.qos.logback.core.util.StringUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import org.amadejsky.studentsmaven.repository.StudentRepository;
import org.amadejsky.studentsmaven.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @PutMapping("/{id}")
    public ResponseEntity<Student> putStudent(@PathVariable Long id, @RequestBody @Valid Student student){
        return repository.findById(id)
                .map(studentCurrent -> {
                    studentCurrent.setFirstName(student.getFirstName());
                    studentCurrent.setLastName(student.getLastName());
                    studentCurrent.setEmail(student.getEmail());
                    return ResponseEntity.ok().body(repository.save(studentCurrent));
                }).orElseGet(()->ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Student> patchStudent(@PathVariable Long id, @RequestBody @Valid Student student){
        return repository.findById(id)
                .map(studentCurrent -> {
                    if(!StringUtils.isEmpty(student.getFirstName())){
                        studentCurrent.setFirstName(student.getFirstName());
                    }
                    if(!StringUtils.isEmpty(student.getLastName())){
                        studentCurrent.setFirstName(student.getLastName());
                    }
                    if(!StringUtils.isEmpty(student.getEmail())){
                        studentCurrent.setFirstName(student.getEmail());
                    }
                    return ResponseEntity.ok().body(repository.save(studentCurrent));
                }).orElseGet(()->ResponseEntity.notFound().build());
    }
//    @GetMapping("/lastname")
//    public List<Student> findAllByLastName(@RequestParam String lastName){
//        return repository.findAll().stream()
//                .filter(st -> st.getLastName().equals(lastName))
//                .collect(Collectors.toList());
//    }
//------------------
//TEST Methods
    @GetMapping("/lastname")
    public List<Student> findAllByLastName(@RequestParam String lastName, @RequestParam int numberOfPage){
        Pageable pageable = PageRequest.of(numberOfPage,2, Sort.by("firstName"));
        return repository.findByLastName(lastName, pageable);
//                .stream().collect(Collectors.toList());
    }
    @GetMapping("/find")
    public List<Student> findStudent(@RequestParam String lastName, @RequestParam String firstName){
        return repository.findByLastNameAndFirstNameIsNotLikeAllIgnoreCase(lastName, firstName);
    }

    @GetMapping("/marian")
    public List<Student> findStudentMarian(){
        return repository.findStudentsWithNameMarian();
    }
//-------------------------
}