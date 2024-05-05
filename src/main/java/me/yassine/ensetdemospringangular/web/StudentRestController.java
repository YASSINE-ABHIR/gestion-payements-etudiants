package me.yassine.ensetdemospringangular.web;
import lombok.AllArgsConstructor;
import me.yassine.ensetdemospringangular.domain.Student;
import me.yassine.ensetdemospringangular.repository.StudentRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
public class StudentRestController {
    private StudentRepository studentRepository;

    /**
     * Find all students
     * @return List<Student>
     */
    @GetMapping(path = "/students/all")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    /**
     * Find all students by program
     * @param programId is student program id
     * @return List<Student>
     */
    @GetMapping(path = "/students/{programId}")
    public List<Student> getStudentsByProgramId(@PathVariable String programId) {
        return studentRepository.findStudentByProgramId(programId);
    }

    /**
     * Find student by his code
     * @param code is student code
     * @return Optional<Student>
     */
    @GetMapping(path = "/students/{code}")
    public Optional<Student> getStudentByCode(@PathVariable String code) {
        return studentRepository.findStudentByCode(code);
    }

}
