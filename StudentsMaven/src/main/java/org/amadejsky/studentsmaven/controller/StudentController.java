

package org.amadejsky.studentsmaven.controller;

import ch.qos.logback.core.util.StringUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import org.amadejsky.studentsmaven.repository.StudentRepository;
import org.amadejsky.studentsmaven.model.Student;
import org.amadejsky.studentsmaven.service.StudentService;
import org.amadejsky.studentsmaven.service.StudentServiceImpl;
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

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    //    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    @GetMapping()
    public List<Student> getStudents(@RequestParam(required = false) Student.Status status) {
        return studentService.getStudents(status);
    }

    @PostMapping("/emails")
    public List<Student> getStudentsByEmails(@RequestBody List<String> emails){
        return studentService.getStudentsByEmails(emails);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Student addStudent(@RequestBody @Valid Student student) {
        return studentService.addStudent(student);
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
    public Student getStudent(@PathVariable Long id) {
       return studentService.getStudent(id);
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
    public void deleteStudent(@PathVariable Long id) {
      studentService.deleteStudent(id);

    }
    @PutMapping("/{id}")
    public Student putStudent(@PathVariable Long id, @RequestBody Student student){
        return studentService.putStudent(id, student);
    }

    @PatchMapping("/{id}")
    public Student patchStudent(@PathVariable Long id, @RequestBody Student student){
        return studentService.patchStudent(id,student);
    }


//    @GetMapping("/lastname")
//    public List<Student> findAllByLastName(@RequestParam String lastName){
//        return repository.findAll().stream()
//                .filter(st -> st.getLastName().equals(lastName))
//                .collect(Collectors.toList());
//    }
//------------------
//TEST Methods
//    @GetMapping("/lastname")
//    public List<Student> findAllByLastName(@RequestParam String lastName, @RequestParam int numberOfPage){
//        Pageable pageable = PageRequest.of(numberOfPage,2, Sort.by("firstName"));
//        return repository.findByLastName(lastName, pageable);
////                .stream().collect(Collectors.toList());
//    }
//    @GetMapping("/find")
//    public List<Student> findStudent(@RequestParam String lastName, @RequestParam String firstName){
//        return repository.findByLastNameAndFirstNameIsNotLikeAllIgnoreCase(lastName, firstName);
//    }
//
//    @GetMapping("/marian")
//    public List<Student> findStudentMarian(){
//        return repository.findStudentsWithNameMarian();
//    }
//-------------------------
}