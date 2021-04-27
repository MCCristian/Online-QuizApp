package com.example.licenceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.licenceapp.Common.Common;
import com.example.licenceapp.Model.QuestionScore;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class Done extends AppCompatActivity {

    Button btnTryAgain;
    TextView txtResultScore;
    TextView getTxtResultQuestion;
    ProgressBar progressBar;

    FirebaseDatabase database;
    DatabaseReference question_score;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);

        database = FirebaseDatabase.getInstance();
        question_score = database.getReference("Question_Score");

        txtResultScore = (TextView)findViewById(R.id.text_total_score);
        getTxtResultQuestion = (TextView)findViewById(R.id.txt_total_question);
        progressBar = (ProgressBar)findViewById(R.id.done_progress_bar);
        btnTryAgain = (Button)findViewById(R.id.btn_try_again);

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Done.this,  Categories.class);
                startActivity(intent);
                finish();
            }
        });
        // Get data from bundle and set to view
        Bundle extra = getIntent().getExtras();
        if (extra != null)
        {
            int score = extra.getInt("SCORE");
            int totalQuestion = extra.getInt("TOTAL");
            int correctAnswer = extra.getInt("CORRECT");

            txtResultScore.setText(String.format("SCORE: %d", score));
            //getTxtResultQuestion.setText(String.format("PASSED: %d / %d" + correctAnswer, totalQuestion));


            progressBar.setMax(totalQuestion);
            progressBar.setProgress(correctAnswer);

            //Upload point in DB
            question_score.child(String.format("%s_%s", Common.currentUserRegister.getUsernameRegister(),
                                                                Common.categoryId))
                    .setValue(new QuestionScore(String.format("%s_%s", Common.currentUserRegister.getUsernameRegister(),
                            Common.categoryId),
                            Common.currentUserRegister.getUsernameRegister(),
                            String.valueOf(score),
                            Common.categoryId,
                            Common.categoryName));

        }
    }
}