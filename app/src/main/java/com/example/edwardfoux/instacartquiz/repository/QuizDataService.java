package com.example.edwardfoux.instacartquiz.repository;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QuizDataService {

    @GET("/mataanin/8d1afd0ba11f0d50f5b7/raw/4a1573c3089c109d2aefcf488f0bf8fbf89e7753/zquestions.json")
    Call<String> getQuizes();
}
