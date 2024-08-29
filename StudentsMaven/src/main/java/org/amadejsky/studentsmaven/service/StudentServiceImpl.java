package org.amadejsky.studentsmaven.service;

import io.micrometer.common.util.StringUtils;
import org.amadejsky.studentsmaven.exception.StudentError;
import org.amadejsky.studentsmaven.exception.StudentException;
import org.amadejsky.studentsmaven.model.Student;
import org.amadejsky.studentsmaven.repository.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> getStudents(Student.Status status) {
        if(status!=null){
            return studentRepository.findAllByStatus(status);
        }
        return studentRepository.findAll();
    }

    @Override
    public Student getStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(()-> new StudentException(StudentError.STUDENT_NOT_FOUND));
        if(!Student.Status.ACTIVE.equals(student.getStatus())){
            throw new StudentException(StudentError.STUDENT_ACCOUNT_IS_INACTIVE);
        }
        return student;

    }

    @Override
    public Student addStudent(Student student) {
        validate(student);
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(()-> new StudentException(StudentError.STUDENT_NOT_FOUND));
        student.setStatus(Student.Status.INACTIVE);
        studentRepository.save(student);
    }

    @Override
    public Student putStudent(Long id, Student student) {
        return studentRepository.findById(id)
                .map(studentCurrent -> {
                    if(studentRepository.existsByEmail(student.getEmail()) &&
                    !studentCurrent.getEmail().equals(student.getEmail())){
                        throw new StudentException(StudentError.STUDENT_EMAIL_ALREADY_IN_USE);
                    }
                    studentCurrent.setFirstName(student.getFirstName());
                    studentCurrent.setLastName(student.getLastName());
                    studentCurrent.setEmail(student.getEmail());
                    studentCurrent.setStatus(student.getStatus());
                    return studentRepository.save(studentCurrent);
                }).orElseThrow(()->new StudentException(StudentError.STUDENT_NOT_FOUND));
    }

    private void validate(Student student) {
        if(studentRepository.existsByEmail(student.getEmail())){
            throw new StudentException(StudentError.STUDENT_EMAIL_ALREADY_IN_USE);
        }
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
                    if(studentCurrent.getStatus() != null) {
                        studentCurrent.setStatus(student.getStatus());
                    }
                    return studentRepository.save(studentCurrent);
                }).orElseThrow(()->new StudentException(StudentError.STUDENT_NOT_FOUND));
    }

    @Override
    public List<Student> getStudentsByEmails(List<String> emails) {
//        return studentRepository.findAll().stream()
//                .filter(student-> emails.contains(student.getEmail())).collect(Collectors.toList());
        return studentRepository.findAllByEmailIn(emails);
    }
}
