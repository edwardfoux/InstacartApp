package com.example.edwardfoux.instacartquiz.repository;

public class QuizApi {

    public static final String BASE_URL = "https://gist.githubusercontent.com/";

    public static QuizDataService getQuizDataService() {
        return RetrofitClient.getClient(BASE_URL).create(QuizDataService.class);
    }
}
