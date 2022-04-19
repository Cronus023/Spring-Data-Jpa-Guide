package com.jazzteam.jpa_guide.service;

import com.jazzteam.jpa_guide.entity.Student;
import com.jazzteam.jpa_guide.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Student entity service
 * @author Andrey Klochko
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> saveAllStudents(List<Student> students) {
        return studentRepository.saveAll(students);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentByIdOrNull(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    /**
     * This is a custom query delete function.
     * @param id - student id
     */
    public void deleteStudentByIdUsingQuery(Long id) {
        studentRepository.queryDeleteStudentById(id);
    }

    public void deleteStudentById(Long id) {
        studentRepository.deleteById(id);
    }

    public Student getStudentByEmailOrNull(String email) {
        return studentRepository.findStudentByEmail(email).orElse(null);
    }

    public List<Student> getGoodStudentsByName(String firstName) {
        return studentRepository.findStudentsByFirstNameAndAgeIsGreaterThanEqual(firstName, 18);
    }

    public Student getStudentByIdAndFetchBooks(Long id) {
        return studentRepository.findStudentByIdAndFetchBooks(id).orElse(null);
    }
}
