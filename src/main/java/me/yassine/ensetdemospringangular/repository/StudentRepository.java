package me.yassine.ensetdemospringangular.repository;

import me.yassine.ensetdemospringangular.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,String> {
    List<Student> findStudentByProgramId(String programId);
    Optional<Student> findStudentByCode(String code);
}
