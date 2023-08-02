package com.exam.service;

import com.exam.module.Questions;
import com.exam.repository.IQuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class QuestionService {
    @Autowired
    private IQuestionRepo questionRepo;
    
    public String addQuiz(ArrayList<Questions> questions) {
        Stream<Questions> stream = questions.stream();
        stream.forEach(Questions -> questionRepo.save(Questions));
        return "Saved";
    }

    public List<Questions> getQuiz(String quizName) {
        return questionRepo.getQuiz(quizName);
    }

    public int verifyAnswer(Map<String, String> answer) {
        Set<String> keySet = answer.keySet();
        int marks = 0;
        for (String key : keySet) {
            if(!key.equals("quizName")) {
                String answerOfQuestion = questionRepo.getAnswerOfQuestion(Integer.parseInt(key));
                if (answerOfQuestion.equals(answer.get(key))) {
                    marks++;
                }
            }
        }
        return marks;
    }

    public List<String> getQuizList() {
        List<String> quizNames = questionRepo.getQuizNames();
        if(quizNames.isEmpty()){
            throw new RuntimeException("No Quiz Available at this time");
        }
        return quizNames;
    }

    public int noOfQuestion(String quizName) {
        return questionRepo.numberofQuestionofQuiz(quizName);
    }
}
