package com.jazzteam.jpa_guide.repository;

import com.jazzteam.jpa_guide.entity.Student;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findStudentByEmail(String email);

    @Query("SELECT s FROM Student s WHERE s.firstName = ?1 AND s.age >= ?2")
    List<Student> selectStudentWhereFirstNameAndAgeGreaterOrEqual(
            String firstName, Integer age);

    List<Student> findStudentsByFirstNameAndAgeIsGreaterThanEqual(String firstName, Integer age);

    @Transactional
    @Modifying
    @Query("DELETE FROM Student u WHERE u.id = ?1")
    int queryDeleteStudentById(Long id);

    @EntityGraph(value = "student.books")
    @Query("SELECT s FROM Student s WHERE s.id = ?1")
    Optional<Student> findStudentByIdAndFetchBooks(Long id);
}
