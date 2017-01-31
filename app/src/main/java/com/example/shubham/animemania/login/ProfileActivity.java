package com.example.shubham.animemania.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shubham.animemania.R;
import com.example.shubham.animemania.leaderboard.LeaderBoardActivity;
import com.example.shubham.animemania.presenter.TriviaPresenter;
import com.example.shubham.animemania.quiz.CategoryActivity;
import com.example.shubham.animemania.utility.Logger;

/**
 * Player Profile Activity For Showing The USer Profile
 */
public class ProfileActivity extends AppCompatActivity implements ProfileView, View.OnClickListener {

    private static final String TAG = ProfileActivity.class.getName();
    private TextView mUserNameTextView;
    private TextView mNameTextView;
    private TextView mUserScoreTextView;
    private Button mPlayButton;
    private Button mLeaderBoardButton;
    private Button mSignOutButton;
    private ImageView mAvatarImage;
    private TriviaPresenter mTriviaPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initialiseWidgets();
        addListeners();
        configureMVP();
    }

    @Override
    public void initialiseWidgets() {
        mUserNameTextView = (TextView) findViewById(R.id.user_name_textView);
        mNameTextView = (TextView) findViewById(R.id.name_textView);
        mUserScoreTextView = (TextView) findViewById(R.id.total_score_textView);
        mPlayButton = (Button) findViewById(R.id.play_button);
        mLeaderBoardButton = (Button) findViewById(R.id.leaderboard_profile_button);
        mSignOutButton = (Button) findViewById(R.id.sign_out_button);
        mAvatarImage = (ImageView) findViewById(R.id.profile_avatar_imageView);

    }

    @Override
    public void addListeners() {
        mPlayButton.setOnClickListener(this);
        mLeaderBoardButton.setOnClickListener(this);
        mSignOutButton.setOnClickListener(this);

    }

    @Override
    public void configureMVP() {
        mTriviaPresenter = TriviaPresenter.getInstance();
        mTriviaPresenter.setView(this);
    }

    @Override
    public void openCategoryPage() {
        launchIntent(CategoryActivity.class);

    }

    @Override
    public void showMessage(String message) {
        Logger.toastShort(message);
    }

    @Override
    public void setData(String userName, String name, int totalScore, int avatarId) {
        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(name) && totalScore >= 0) {
            mUserNameTextView.setText(userName);
            mNameTextView.setText(name);
            mUserScoreTextView.setText(String.valueOf(totalScore));
            mAvatarImage.setImageResource(avatarId);
        } else {
            Logger.debug(TAG, "Error in Getting Value So Setting Blank");
            mUserNameTextView.setText(" ");
            mNameTextView.setText(" ");
            mUserScoreTextView.setText(String.valueOf(0));
            mAvatarImage.setImageResource(R.drawable.bitmap_avatar_1);
        }
    }

    @Override
    public void openLeaderBoardPage() {
        //opening LeaderBoardActivity for Showing LeaderBoard
        launchIntent(LeaderBoardActivity.class);
    }

    @Override
    public void openLoginPage() {
        //After Sign out Clicked launch LoginPage
        launchIntent(LoginActivity.class);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.play_button:
                mTriviaPresenter.playingButtonClicked();
                break;
            case R.id.leaderboard_profile_button:
                mTriviaPresenter.leaderBoardButtonClicked();
                break;
            case R.id.sign_out_button:
                mTriviaPresenter.signOutClicked();
                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        mTriviaPresenter.profilePageDisplayed();
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
        builder.setMessage("Do you want to Exit ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
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