package com.example.shubham.animemania.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.shubham.animemania.R;
import com.example.shubham.animemania.leaderboard.LeaderBoardActivity;
import com.example.shubham.animemania.login.ProfileActivity;
import com.example.shubham.animemania.presenter.TriviaPresenter;
import com.example.shubham.animemania.utility.Logger;

/**
 * ResultActivity
 * For Showing The Result of Quiz
 * Of User for That played category particular category
 */
public class ResultActivity extends AppCompatActivity implements ResultView, View.OnClickListener {

    private Button mNextLevelButton;
    private TextView mScoreTextView;
    private Button mHomeButton;
    private Button mLeaderBoardButton;
    private TriviaPresenter mTriviaPresenter;
    private String mCategory;
    private int mLevel = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        initialiseWidgets();
        addListeners();
        configureMVP();
    }

    @Override
    public void initialiseWidgets() {
        mScoreTextView = (TextView) findViewById(R.id.result_textView);
        mNextLevelButton = (Button) findViewById(R.id.next_level_button);
        mHomeButton = (Button) findViewById(R.id.home);
        mLeaderBoardButton=(Button) findViewById(R.id.leaderboard_button);
    }

    @Override
    public void addListeners() {
        mNextLevelButton.setOnClickListener(this);
        mHomeButton.setOnClickListener(this);
        mLeaderBoardButton.setOnClickListener(this);
    }

    @Override
    public void configureMVP() {
        mTriviaPresenter=TriviaPresenter.getInstance();
        mTriviaPresenter.setView(this);
    }

    @Override
    public void setData(int result, int level, String category) {
        mCategory = category;
        switch (level) {
            case 1:
                mNextLevelButton.setVisibility(View.VISIBLE);
                break;
            case 2:
                mNextLevelButton.setVisibility(View.INVISIBLE);
                break;
        }

        mScoreTextView.setText(String.valueOf(result));
    }

    @Override
    public void openQuizPage() {
         launchIntent(QuizActivity.class);
        finish();
    }

    @Override
    public void openProfilePage() {
         launchIntent(ProfileActivity.class);
    }

    @Override
    public void setButtonText(String message) {
        mNextLevelButton.setText(message);

    }

    @Override
    public void openLeaderBoardPage() {
        launchIntent(LeaderBoardActivity.class);
    }

    @Override
    public void showMessage(String message) {
        Logger.toastShort(message);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home:
                mTriviaPresenter.homeButtonClicked();
                break;
            case R.id.next_level_button:
                String buttonText=mNextLevelButton.getText().toString();
                if (!TextUtils.isEmpty(buttonText))
                {
                    switch (buttonText)
                    {
                        case "Play Again":
                            mLevel=1;
                            break;
                        case "Play Next Level":
                            mLevel=2;
                            break;
                    }
                }

                mTriviaPresenter.nextLevelButtonClicked(mLevel, mCategory);
                break;
            case R.id.leaderboard_button:
                mTriviaPresenter.leaderBoardResultButtonClicked();
                break;


        }

    }

    @Override
    public void onResume() {
        super.onResume();
        mTriviaPresenter.resultPageDisplayed();
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
        super.onBackPressed();
    }
}