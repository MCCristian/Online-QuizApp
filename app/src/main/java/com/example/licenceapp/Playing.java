package com.example.licenceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.licenceapp.Common.Common;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.snapshot.DoubleNode;
import com.squareup.picasso.Picasso;

public class Playing extends AppCompatActivity implements View.OnClickListener{

    final static long INTERVAL = 1000; // 1 sec
    final static long TIMEOUT = 7000; // 7 sec
    int progressValue = 0;

    CountDownTimer mCountDown;

    int index = 0, score = 0, thisQuestion = 0, totalQuestion, correctAnswer;

    ProgressBar progressBar;
    ImageView question_image;
    Button btnA, btnB, btnC, btnD;
    TextView txtScore, txtQuestionNum, question_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);

        // Views
        txtScore = (TextView)findViewById(R.id.txt_score);
        txtQuestionNum = (TextView)findViewById(R.id.txt_total_question);
        question_text = (TextView)findViewById(R.id.question_text);
        question_image = (ImageView)findViewById(R.id.question_image);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        btnA = (Button)findViewById(R.id.btn_answerA);
        btnB = (Button)findViewById(R.id.btn_answerB);
        btnC = (Button)findViewById(R.id.btn_answerC);
        btnD = (Button)findViewById(R.id.btn_answerD);

        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        mCountDown.cancel();
        if (index < totalQuestion)  // still have question in list
        {
            Button clickedButton = (Button)v;
            if(clickedButton.getText().equals(Common.questionList.get(index).getCorrectAnswer()))
            {
                // Choose correct answer
                score += 10;
                correctAnswer++;
                showQuestion(++index);  // next question
            }
            else
            {
                // Choose wrong answer
                Intent intent = new Intent(this, Done.class);
                Bundle dataSend = new Bundle();
                dataSend.putInt("SCORE", score);
                dataSend.putInt("TOTAL", totalQuestion);
                dataSend.putInt("CORRECT", correctAnswer);
                intent.putExtras(dataSend);
                startActivity(intent);
                finish();
            }
            txtScore.setText(String.format("%d", score));
        }
    }

    private void showQuestion(int i)
    {
        if (index < totalQuestion)
        {
            thisQuestion++;
            txtQuestionNum.setText(String.format("%d / %d", thisQuestion, totalQuestion));
            progressBar.setProgress(0);
            progressValue = 0;

            if(Common.questionList.get(index).getIsImageQuestion().equals("true"))
            {
                // If is image
                Picasso.get()
                        .load(Common.questionList.get(index).getQuestion())
                        .into(question_image);
                question_image.setVisibility(View.VISIBLE);
                question_text.setVisibility(View.INVISIBLE);
            }
            else
            {
                question_text.setText(Common.questionList.get(index).getQuestion());
                question_image.setVisibility(View.INVISIBLE);
                question_text.setVisibility(View.VISIBLE);
            }
            btnA.setText(Common.questionList.get(index).getAnswerA());
            btnB.setText(Common.questionList.get(index).getAnswerB());
            btnC.setText(Common.questionList.get(index).getAnswerC());
            btnD.setText(Common.questionList.get(index).getAnswerD());

            mCountDown.start(); // Start timer
        }
        else
        {
            // If it is final question
            Intent intent = new Intent(this, Done.class);
            Bundle dataSend = new Bundle();
            dataSend.putInt("SCORE", score);
            dataSend.putInt("TOTAL", totalQuestion);
            dataSend.putInt("CORRECT", correctAnswer);
            intent.putExtras(dataSend);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        totalQuestion = Common.questionList.size();

        mCountDown = new CountDownTimer(TIMEOUT, INTERVAL) {
            @Override
            public void onTick(long minisec) {
                progressBar.setProgress(progressValue);
                progressValue++;
            }

            @Override
            public void onFinish() {
                mCountDown.cancel();
                showQuestion(++index);
            }
        };
        showQuestion(index);
    }
}