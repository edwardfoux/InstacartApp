package com.example.edwardfoux.instacartquiz.quiz;

public interface QuizView {

    void onNewQuizAvailable(Quiz quiz);
    void onCorrectItemSelected();
    void onWrongItemSelected();

    void startCountDownService();
    void finishCountDownService();
    void updateCounter(Long value);
    void showNetworkError();
}
