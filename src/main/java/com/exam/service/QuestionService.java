package com.exam.service;

import com.exam.module.Questions;
import com.exam.repository.IQuestionRepo;
import com.exam.repository.IQuizRepo;
import jakarta.transaction.Transactional;
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

    @Transactional
    public boolean addQuiz(Map<String, String> questionsList) {
        String quizName = questionsList.get("quizName");
        int noOfQuestions = (questionsList.size()-3)/6;
        boolean saved = true;
        for (int i = 0; i < noOfQuestions; i++) {
            if(saved == false) return false;
            String question = questionsList.get("questions[" + i + "].question");
            String optOne = questionsList.get("questions[" + i + "].optOne");
            String optTwo = questionsList.get("questions[" + i + "].optTwo");
            String optThree = questionsList.get("questions[" + i + "].optThree");
            String optFour = questionsList.get("questions[" + i + "].optFour");
            String answer = questionsList.get("questions[" + i + "].answer");

            Questions que = new Questions();
            que.setQuizName(quizName);
            que.setQuestion(question);
            que.setOptOne(optOne);
            que.setOptTwo(optTwo);
            que.setOptThree(optThree);
            que.setOptFour(optFour);
            String ans = questionsList.get("questions[" + i + "]."+answer);
            que.setAnswer(ans);

            Questions questions = saveQuestion(que);
            if(questions == null) saved = false;
        }
        return true;
    }

    private Questions saveQuestion (Questions question) {
        return questionRepo.save(question);
    }

    public List<Questions> getQuiz(String quizName) {
        return questionRepo.getQuiz(quizName);
    }

    public int verifyAnswer(Map<String, String> answer) {
        Set<String> keySet = answer.keySet();
        int marks = 0;
        for (String key : keySet) {
            if(isDigit(key)) {
                String answerOfQuestion = questionRepo.getAnswerOfQuestion(Integer.parseInt(key));
                if (answerOfQuestion.equals(answer.get(key))) {
                    marks++;
                }
            }
        }
        return marks;
    }
    public static boolean isDigit(String key) {
        return key.matches("\\d+");
    }
    public List<String> getQuizList() {
        return questionRepo.getQuizNames();
    }

    public int noOfQuestion(String quizName) {
        return questionRepo.numberOfQuestionOfQuiz(quizName);
    }

    public boolean deleteQuiz(String quizName) {
        if(isQuizPresent(quizName) && questionRepo.deleteQuizName(quizName) != 0) return true;
        return false;
    }

    private boolean isQuizPresent(String quizName) {
        if(getQuiz(quizName).size() != 0) return true;
        return false;
    }
}
