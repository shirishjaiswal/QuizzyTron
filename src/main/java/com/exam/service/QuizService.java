package com.exam.service;

import com.exam.module.Quiz;
import com.exam.module.UserEntity;
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

    public boolean userAttendedQuiz(UserEntity userEntity, String quizName, int obtainedMarks, int totalMarks) {
        Quiz quiz = new Quiz();
        quiz.setQuizName(quizName);
        quiz.setObtainedMarks(obtainedMarks);
        quiz.setTotalMarks(totalMarks);
        quiz.setUserEntity(userEntity);

        Quiz save = quizRepo.save(quiz);
        if(save != null) return true;
        throw new RuntimeException("Error Occurred while submitting the quiz");
    }

    public List<Quiz> findByUserName(String userName) {
        UserEntity userByUserNameEntity = userService.findUserByUserName(userName);
        List<Quiz> quizList = quizRepo.findAllByUserEntityId(userByUserNameEntity.getId());
        return quizList;
    }

    public List<Quiz> findByQuizName(String quizName) {
        return quizRepo.findByQuizName(quizName);
    }
}
