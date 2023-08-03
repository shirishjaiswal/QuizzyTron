package com.exam.service;

import com.exam.module.Quiz;
import com.exam.module.User;
import com.exam.repository.IQuizRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {
    @Autowired
    private IQuizRepo quizRepo;

    @Autowired
    private UserService userService;

    public boolean userAttendedQuiz(User user, String quizName, int marks) {
        Quiz quiz = new Quiz();
        quiz.setQuizName(quizName);
        quiz.setMarks(marks);
        quiz.setUser(user);

        Quiz save = quizRepo.save(quiz);
        if(save != null) return true;
        throw new RuntimeException("Error Occurred while submitting the quiz");
    }

    public List<Quiz> findByUserName(String userName) {
        User userByUserName = userService.findUserByUserName(userName);
        List<Quiz> quizList = quizRepo.findAllByUserId(userByUserName.getId());
        return quizList;
    }
}
