package com.example.shubham.animemania.quiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.shubham.animemania.R;
import com.example.shubham.animemania.presenter.TriviaPresenter;
import com.example.shubham.animemania.utility.Logger;

/**
 * Quiz Activity
 * <p>
 * This Activity shows the Questions to user
 * Also Displays the Score
 * There is No Negative Marking
 */
public class QuizActivity extends AppCompatActivity implements QuizView, View.OnClickListener {

    private static final String TAG = QuizActivity.class.getName();
    private TriviaPresenter mTriviaPresenter;

    private TextView mLevelTextView;
    private TextView mQuestionTextView;
    private TextView mScoreTextView;
    private RadioButton mOptionARadioButton;
    private RadioButton mOptionBRadioButton;
    private RadioButton mOptionCRadioButton;
    private RadioButton mOptionDRadioButton;
    private FloatingActionButton mNextFab;
    private RadioGroup mOptionRadioGroup;
    private String mOptA;
    private String mOptB;
    private String mOptC;
    private String mOptD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        initialiseWidgets();
        addListeners();
        configureMVP();
    }

    @Override
    public void initialiseWidgets() {
        mLevelTextView = (TextView) findViewById(R.id.level_textView);
        mQuestionTextView = (TextView) findViewById(R.id.question_textView);
        mScoreTextView = (TextView) findViewById(R.id.score_textView);
        mOptionRadioGroup = (RadioGroup) findViewById(R.id.option_radioGroup);
        mNextFab = (FloatingActionButton) findViewById(R.id.next_fab);
        mOptionARadioButton = (RadioButton) findViewById(R.id.option_a_radioButton);
        mOptionBRadioButton = (RadioButton) findViewById(R.id.option_b_radioButton);
        mOptionCRadioButton = (RadioButton) findViewById(R.id.option_c_radioButton);
        mOptionDRadioButton = (RadioButton) findViewById(R.id.option_d_radioButton);

    }

    @Override
    public void addListeners() {
        mNextFab.setOnClickListener(this);
    }

    @Override
    public void configureMVP() {
        mTriviaPresenter = TriviaPresenter.getInstance();
        mTriviaPresenter.setView(this);
    }

    @Override
    public void setData(String ques, String optionA, String optionB,
                        String optionC, String optionD, String category, int score, int level) {
        try {
            if (!TextUtils.isEmpty(category)) {
                assert getSupportActionBar() != null;
                getSupportActionBar().setTitle(category);
            } else {
                assert getSupportActionBar() != null;
                getSupportActionBar().setTitle("Category");
            }
        } catch (NullPointerException e) {
            Logger.error(TAG, "Error in Title", e);
        }
        mQuestionTextView.setText(ques);
        mOptA = optionA;
        mOptionARadioButton.setText(mOptA);
        mOptB = optionB;
        mOptionBRadioButton.setText(mOptB);
        mOptC = optionC;
        mOptionCRadioButton.setText(mOptC);
        mOptD = optionD;
        mOptionDRadioButton.setText(mOptD);
        mScoreTextView.setText(String.valueOf(score));
        mLevelTextView.setText(String.valueOf(level));
    }


    @Override
    public void openResultPage() {

        launchIntent(ResultActivity.class);
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        mTriviaPresenter.quizPageDisplayed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next_fab:
                String answer = "";
                int rowId = mOptionRadioGroup.getCheckedRadioButtonId();
                Logger.debug(TAG, "" + rowId);
                if (rowId != -1) {
                    switch (rowId) {
                        case R.id.option_a_radioButton:
                            answer = mOptA;
                            break;
                        case R.id.option_b_radioButton:
                            answer = mOptB;
                            break;
                        case R.id.option_c_radioButton:
                            answer = mOptC;
                            break;
                        case R.id.option_d_radioButton:
                            answer = mOptD;
                            break;
                    }
                    Logger.debug(TAG, "" + rowId + " " + answer);
                    if (!TextUtils.isEmpty(answer)) {

                        mOptionRadioGroup.clearCheck();
                        mTriviaPresenter.nextFabClicked(answer);
                    }
                } else
                    mTriviaPresenter.showMessage("Please select an Answer");

                break;
        }
    }

    /**
     * method for launching activity
     *
     * @param iActivityClass: instance of passed class name
     */
    private void launchIntent(Class iActivityClass) {
        //opening the passed class
        Intent intent = new Intent(this, iActivityClass);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit the Quiz?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finish();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

}