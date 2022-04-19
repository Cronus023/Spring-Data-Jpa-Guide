package com.jazzteam.jpa_guide.service;

import com.jazzteam.jpa_guide.entity.StudentCard;
import com.jazzteam.jpa_guide.repository.StudentCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * StudentCard entity service
 * @author Andrey Klochko
 */
@Service
@RequiredArgsConstructor
public class StudentCardService {

    private final StudentCardRepository studentCardRepository;

    public StudentCard createStudentCard(StudentCard studentCard) {
        return studentCardRepository.save(studentCard);
    }

    public StudentCard updateStudentCard(StudentCard studentCard) {
        return studentCardRepository.save(studentCard);
    }

    public void deleteStudentCard(Long id) {
        studentCardRepository.deleteById(id);
    }
}
