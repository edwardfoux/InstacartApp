package com.example.edwardfoux.instacartquiz.quiz;

import java.util.Collections;
import java.util.List;

public class QuizUtil {

    /**
     * Shuffles the answer options in the quiz
     * @param quiz quiz
     * @return the index of the correct answer
     */
    public static int randomizeAnswerOptions(Quiz quiz) {
        List<String> answerOptions = quiz.getQuizOptions();
        String correctAnswer = answerOptions.get(0);
        Collections.shuffle(answerOptions);

        return answerOptions.indexOf(correctAnswer);}
}
