package org.amadejsky.studentsmaven.repository;

import org.amadejsky.studentsmaven.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

}
