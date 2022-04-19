package com.jazzteam.jpa_guide.repository;

import com.jazzteam.jpa_guide.entity.StudentCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentCardRepository extends JpaRepository<StudentCard, Long> {
}
