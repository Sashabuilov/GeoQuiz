package com.example.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";

    private Button mTrue_Button;
    private Button mFalse_Button;
    private ImageButton mNextButton;
    //private ImageButton mPrevButton;
    private TextView mQuestionTextView;
    private int resultat;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate(Bundle) called");

        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        mQuestionTextView = findViewById(R.id.question_text_view);
        updateQuestion();

        mTrue_Button = findViewById(R.id.true_button);
        //mTrue_Button=findViewById(R.id.question_text_view);
        mFalse_Button = findViewById(R.id.false_button);
        mNextButton = findViewById(R.id.next_button);


        /*mPrevButton = findViewById(R.id.prev_button);
          mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex - 1);
                if (mCurrentIndex < 0) {
                    mCurrentIndex = mQuestionBank.length - 1;
                }
                updateQuestion();
            }
        });*/

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCurrentIndex = (mCurrentIndex + 1); //% mQuestionBank.length;

                if (mCurrentIndex == mQuestionBank.length) {
                    mQuestionTextView.setText("Верных ответов " + resultat + "%");
                } else {
                    updateQuestion();
                    mTrue_Button.setEnabled(true);
                    mFalse_Button.setEnabled(true);
                    Log.d(TAG, "NextButtonPressed");
                }
            }
        });

        mTrue_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                mTrue_Button.setEnabled(false);
                mFalse_Button.setEnabled(false);
            }
        });

        mFalse_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                mTrue_Button.setEnabled(false);
                mFalse_Button.setEnabled(false);

            }
        });

        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });
    }

    //реакция на состояние активности
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);


    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }


    //функция обновления вопроса
    private void updateQuestion() {

        //Комманда регистрации исключения
        //Log.d(TAG, "Updating question text", new Exception());

        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId;
        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;

            resultat = resultat + (100 / mQuestionBank.length);
        } else {
            messageResId = R.string.incorrect_toast;
            //проверка, чтобы не выйти в < 0 %
            if (resultat == 0) {
            } else {
                resultat = resultat - (100 / mQuestionBank.length);
            }
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

}
