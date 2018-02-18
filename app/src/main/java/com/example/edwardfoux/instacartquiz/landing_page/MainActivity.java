package com.example.edwardfoux.instacartquiz.landing_page;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.edwardfoux.instacartquiz.R;
import com.example.edwardfoux.instacartquiz.quiz.Quiz;
import com.example.edwardfoux.instacartquiz.quiz.QuizActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        View button = findViewById(R.id.take_quiz_button);

        button.setOnClickListener(__ -> {
            Intent intent = new Intent(this, QuizActivity.class);
            startActivity(intent);
        });
    }
}
