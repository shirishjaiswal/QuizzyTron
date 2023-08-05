package com.exam.repository;

import com.exam.module.Questions;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IQuestionRepo extends JpaRepository<Questions, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM Questions WHERE Quiz_Name = :quizName")
    List<Questions> getQuiz(@Param("quizName") String quizName);

    @Query(nativeQuery = true, value = "SELECT answer FROM Questions WHERE id = :id")
    String getAnswerOfQuestion(@Param("id") int id);

    @Query(nativeQuery = true, value = "SELECT DISTINCT Quiz_Name FROM Questions ORDER BY Quiz_Name ASC")
    List<String> getQuizNames();
    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM Questions WHERE Quiz_Name = :quizName")
    int numberOfQuestionOfQuiz(@Param("quizName") String quizName);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "DELETE FROM Questions WHERE Quiz_Name = :quizName")
    int deleteQuizName(@Param("quizName")String quizName);
}
