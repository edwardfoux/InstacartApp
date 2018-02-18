package com.example.edwardfoux.instacartquiz.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.edwardfoux.instacartquiz.R;

public class QuizActivity extends AppCompatActivity implements QuizView, View.OnClickListener {

    private QuizPresenter quizPresenter;

    private AppCompatImageButton[] buttonList;
    private AppCompatTextView timerTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_layout);

        AppCompatImageButton button1 = findViewById(R.id.first_button);
        AppCompatImageButton button2 = findViewById(R.id.second_button);
        AppCompatImageButton button3 = findViewById(R.id.third_button);
        AppCompatImageButton button4 = findViewById(R.id.fourth_button);

        timerTV = findViewById(R.id.timer);

        buttonList = new AppCompatImageButton[4];
        buttonList[0] = button1;
        buttonList[1] = button2;
        buttonList[2] = button3;
        buttonList[3] = button4;

        quizPresenter = new QuizPresenter(this);

        quizPresenter.onViewCreated();

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        findViewById(R.id.button_no).setOnClickListener(this);
        findViewById(R.id.button_yes).setOnClickListener(this);

        findViewById(R.id.end_dialog).setVisibility(View.GONE);
    }

    @Override
    public void onNewQuizAvailable(Quiz quiz) {
        TextView textView = findViewById(R.id.item_name);
        textView.setText(quiz.getQuizName());
        for (int i = 0; i < quiz.getQuizOptions().size(); i++) {
            Glide.with(this)
                    .load(quiz.getQuizOptions().get(i))
                    .into(buttonList[i]);
        }
    }

    @Override
    public void onCorrectItemSelected() {
        findViewById(R.id.end_dialog).setVisibility(View.VISIBLE);
        findViewById(R.id.success_message).setVisibility(View.VISIBLE);
        findViewById(R.id.error_message).setVisibility(View.GONE);
    }

    @Override
    public void onWrongItemSelected() {
        findViewById(R.id.end_dialog).setVisibility(View.VISIBLE);
        findViewById(R.id.success_message).setVisibility(View.GONE);
        findViewById(R.id.error_message).setVisibility(View.VISIBLE);
    }

    @Override
    public void startCountDownService() {
        Intent intent = new Intent(this, TimerService.class);
        startService(intent);
    }

    @Override
    public void finishCountDownService() {
        Intent intent = new Intent(this, TimerService.class);
        stopService(intent);
    }

    @Override
    public void updateCounter(Long value) {
        timerTV.setText(String.valueOf(value));
    }

    @Override
    public void showNetworkError() {
        Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.first_button:
                quizPresenter.onItemSelected(0);
                break;
            case R.id.second_button:
                quizPresenter.onItemSelected(1);
                break;
            case R.id.third_button:
                quizPresenter.onItemSelected(2);
                break;
            case R.id.fourth_button:
                quizPresenter.onItemSelected(3);
                break;
            case R.id.button_yes:
                quizPresenter.onViewCreated();
                findViewById(R.id.end_dialog).setVisibility(View.GONE);
                break;
            case R.id.button_no:
                finish();
                break;
        }
    }
}
