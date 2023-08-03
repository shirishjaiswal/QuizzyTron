package com.exam.repository;

import com.exam.module.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface IQuizRepo extends JpaRepository<Quiz, Integer> {
    List<Quiz> findAllByUserId(long id);
}
