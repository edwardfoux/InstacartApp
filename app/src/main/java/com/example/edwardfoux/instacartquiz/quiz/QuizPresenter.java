package com.example.edwardfoux.instacartquiz.quiz;
import android.util.Log;

import com.example.edwardfoux.instacartquiz.repository.QuizApi;
import com.example.edwardfoux.instacartquiz.repository.QuizDataService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class QuizPresenter {

    private static final String TAG = "QuizPresenter";
    private QuizView quizView;
    private int correctAnswer = - 1;

    private static final int MISSING_ANSWER = -2;

    QuizPresenter(QuizView quizView) {
        this.quizView = quizView;
    }

    void onViewCreated() {
        loadQuizData();
    }

    void onDestory() {

    }

    void onItemSelected(int selectedItem) {
        if (selectedItem == correctAnswer) {
            quizView.onCorrectItemSelected();
        } else {
            quizView.onWrongItemSelected();
        }
        quizView.finishCountDownService();

    }

    private void loadQuizData() {
        QuizDataService quizApi = QuizApi.getQuizDataService();

        quizApi.getQuizes().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    processData(response.body());
                }else {
                    int statusCode  = response.code();
                    Log.e(TAG, "error loading from API due to status code:"+ statusCode);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "error loading from API "+ t.toString());
                quizView.showNetworkError();
            }
        });
    }

    private void processData(String jsonResponse) {
        try {
            JSONObject jsonObject = convertToJson(jsonResponse);
            List<String> items = getKeys(jsonObject);
            Quiz quiz = selectRandomItem(items, jsonObject);
            correctAnswer = QuizUtil.randomizeAnswerOptions(quiz);
            quizView.onNewQuizAvailable(quiz);
            quizView.startCountDownService();

            BroadcastRelay
                    .getInstance()
                    .getRelay()
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(value -> {
                        long remainingTime = TimerService.QUIZ_INTERVAL - value;
                        if (remainingTime < 1) {
                            onItemSelected(MISSING_ANSWER);
                        }
                        quizView.updateCounter(remainingTime);
                    });

        } catch (JSONException e) {
            Log.e(TAG, "error parsing data: "+ e.toString());
        }
    }

    private JSONObject convertToJson(String jsonResponse) throws JSONException{
        return new JSONObject(jsonResponse);
    }

    private List<String> getKeys(JSONObject object) throws JSONException {
        List<String> list = new ArrayList<>();
        Iterator<?> keys = object.keys();

        while ( keys.hasNext() ) {
            String key = (String) keys.next();
            list.add(key);
        }

        return list;
    }

    private Quiz selectRandomItem(List<String> keys, JSONObject jsonObject) throws JSONException {
        int size = keys.size();
        if (size == 0) {
            return null;
        }
        Quiz quiz = new Quiz();
        Random random = new Random();
        int randomItem = random.nextInt(keys.size());

        String randomKey = keys.get(randomItem);

        JSONArray jsonQuiz = jsonObject.getJSONArray(randomKey);

        quiz.setQuizName(randomKey);

        List<String> options = new ArrayList<>();

        for (int i = 0; i < jsonQuiz.length(); i++) {
            options.add(jsonQuiz.getString(i));
        }

        quiz.setQuizOptions(options);
        return quiz;
    }
}
