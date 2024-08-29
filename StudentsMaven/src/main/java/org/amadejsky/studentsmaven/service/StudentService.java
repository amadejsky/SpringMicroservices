package org.amadejsky.studentsmaven.service;

import org.amadejsky.studentsmaven.model.Student;

import java.util.List;

public interface StudentService {
    List<Student> getStudents(Student.Status status);
    Student getStudent(Long id);
    Student addStudent(Student student);
    void deleteStudent(Long id);
    Student putStudent(Long id,Student student);
    Student patchStudent(Long id,Student student);

    List<Student> getStudentsByEmails(List<String> emails);
}
