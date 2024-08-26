package org.amadejsky.studentsmaven.service;

import io.micrometer.common.util.StringUtils;
import org.amadejsky.studentsmaven.exception.StudentError;
import org.amadejsky.studentsmaven.exception.StudentException;
import org.amadejsky.studentsmaven.model.Student;
import org.amadejsky.studentsmaven.repository.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudent(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(()-> new StudentException(StudentError.STUDENT_NOT_FOUND));
    }

    @Override
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(()-> new StudentException(StudentError.STUDENT_NOT_FOUND));
        studentRepository.delete(student);
    }

    @Override
    public Student putStudent(Long id, Student student) {
        return studentRepository.findById(id)
                .map(studentCurrent -> {
                    studentCurrent.setFirstName(student.getFirstName());
                    studentCurrent.setLastName(student.getLastName());
                    studentCurrent.setEmail(student.getEmail());
                    return studentRepository.save(studentCurrent);
                }).orElseThrow(()->new StudentException(StudentError.STUDENT_NOT_FOUND));
    }

    @Override
    public Student patchStudent(Long id, Student student) {
        return studentRepository.findById(id)
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
                    return studentRepository.save(studentCurrent);
                }).orElseThrow(()->new StudentException(StudentError.STUDENT_NOT_FOUND));
    }
}
