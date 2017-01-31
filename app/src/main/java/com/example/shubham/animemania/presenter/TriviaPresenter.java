package com.example.shubham.animemania.presenter;

import android.text.TextUtils;

import com.example.shubham.animemania.leaderboard.LeaderBoardView;
import com.example.shubham.animemania.login.LoginView;
import com.example.shubham.animemania.login.ProfileView;
import com.example.shubham.animemania.login.RegisterView;
import com.example.shubham.animemania.model.Contract;
import com.example.shubham.animemania.model.LeaderBoardModel;
import com.example.shubham.animemania.model.ProfileDetails;
import com.example.shubham.animemania.model.QuestionModel;
import com.example.shubham.animemania.model.TriviaData;
import com.example.shubham.animemania.quiz.CategoryView;
import com.example.shubham.animemania.quiz.QuizView;
import com.example.shubham.animemania.quiz.ResultView;
import com.example.shubham.animemania.utility.AppContext;
import com.example.shubham.animemania.utility.Logger;
import com.example.shubham.animemania.utility.SharedPreferenceHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * TriviaPresenter
 * For Handling All Operation From
 * 1)Getting Data From Model
 * 2)Setting This Data in View of Activity
 * 3)Handling whole Logic
 * Created by shubham on 3/1/17.
 */
public class TriviaPresenter implements PresenterInterface {
    private static final String TAG = TriviaPresenter.class.getSimpleName();
    private static TriviaPresenter ourInstance = new TriviaPresenter();

    private LoginView mLoginView;
    private RegisterView mRegisterView;
    private ProfileView mProfileView;
    private TriviaData mData = TriviaData.getInstance();
    private String mUserName;
    private ArrayList<QuestionModel> mList;
    private List<LeaderBoardModel> boardModelList;
    private QuizView mQuizView;
    private CategoryView mCategoryView;
    private ResultView mResultView;
    private LeaderBoardView mLeaderBoardView;
    private int mIndex = 0;
    private String mQuestion;
    private String mOptA;
    private String mOptB;
    private String mOptC;
    private String mOptD;
    private int mListSize;
    private String mCorrectAns;
    private int mUserScore = 0;
    private int mCurrLevel = 1;
    private String mCurrentCategory;
    private String mPassword;
    private int mAvatarId = -1;

    /**
     * Constructor of TriviaPresenter
     */
    private TriviaPresenter() {
    }

    /**
     * Method for getting Instance of TriviaPresenter
     *
     * @return : instance of TriviaPresenter
     */
    public static TriviaPresenter getInstance() {
        return ourInstance;
    }

    @Override
    public boolean checkSharedPreference() {
        boolean check;
        String userName = SharedPreferenceHelper.getSharedPreferenceString(AppContext.getContext(),
                Contract.LOGIN_USER_NAME, "");
        mPassword = SharedPreferenceHelper.getSharedPreferenceString(AppContext.getContext(),
                Contract.LOGIN_USER_PASSWORD, "");
        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(mPassword)) {
            mUserName = userName;
            check = true;
        } else {
            check = false;
        }
        return check;
    }

    @Override
    public void setView(LoginView loginView) {
        mLoginView = loginView;
    }

    @Override
    public void setView(RegisterView registerView) {
        mRegisterView = registerView;
    }

    @Override
    public void setView(ProfileView profileView) {
        mProfileView = profileView;
    }

    @Override
    public void loginComplete() {
        Logger.debug(TAG, "Login Complete opening profile Fragment");

        mLoginView.openProfilePage();
    }

    @Override
    public void loginPageDisplayed() {

        boolean check = checkSharedPreference();
        if (check)
            mLoginView.setData(mUserName, mPassword);
        Logger.debug(TAG, "Login View Displayed");
    }

    @Override
    public void loginButtonClicked() {
        mUserName = mLoginView.getUserName();
        String password = mLoginView.getUserPassword();
        boolean value = mData.isUserPresent(mUserName, password);

        if (value) {
            Logger.debug(TAG, "Open LeaderBoard");
            //open the dashboard
            SharedPreferenceHelper.setSharedPreferenceString(AppContext.getContext(),
                    Contract.LOGIN_USER_NAME, mUserName);
            SharedPreferenceHelper.setSharedPreferenceString(AppContext.getContext(),
                    Contract.LOGIN_USER_PASSWORD, password);
            loginComplete();
        } else {
            Logger.toastShort("Please Enter Correct UserName/Password");
        }
    }

    @Override
    public void clickedRegisterButton() {
        Logger.debug(TAG, "Opening Register Fragment");
        mLoginView.openRegisterPage();
    }

    @Override
    public void registerViewDisplayed() {
        Logger.debug(TAG, "Register View Displayed");
    }

    @Override
    public void registerComplete() {
        Logger.toastShort("User Registered");
        mRegisterView.openProfilePage();
    }

    @Override
    public void profilePageDisplayed() {
        Logger.debug(TAG, "Profile Page Displayed ");
        if (!TextUtils.isEmpty(mUserName)) {
            ProfileDetails details = mData.getProfileData(mUserName);
            String userName = details.getUserName();
            String name = details.getName();
            int score = details.getUserTotalScore();
            mAvatarId = details.getAvatarId();
            Logger.debug(TAG, " " + mUserName + " " + name + " " + score + " " + mAvatarId);

            if (!TextUtils.isEmpty(name) && score >= 0) {
                mProfileView.setData(userName, name, score, mAvatarId);
            }
        } else {
            Logger.debug(TAG, "Error in Setting Data in Profile");
        }
    }

    @Override
    public void playingButtonClicked() {
        Logger.debug(TAG, "Opening CategoryActivity");
        mProfileView.openCategoryPage();

    }

    @Override
    public void signOutClicked() {
        SharedPreferenceHelper.clearSharedPreference(AppContext.getContext());
        mProfileView.openLoginPage();

    }

    @Override
    public void registerUserClicked() {

        mUserName = mRegisterView.getNewUserName();
        mPassword = mRegisterView.getNewUserPassword();
        String name = mRegisterView.getNewName();
        mAvatarId = mRegisterView.getAvatarId();
        boolean check = false;
        if (!TextUtils.isEmpty(mUserName) && !TextUtils.isEmpty(mPassword) && !TextUtils.isEmpty(name)) {
            if (mUserName.length() <= 8) {
                try {
                    check = mData.addUser(mUserName, name, mPassword, mAvatarId);

                } catch (Exception e) {
                    Logger.error(TAG, "Error Unknown ", e);
                }

                if (check) {
                    registerComplete();
                } else {
                    Logger.toastShort("User Already Exist");
                }
            } else {
                Logger.toastShort("UserName must have 8 or less Characters");
            }

        } else {
            Logger.toastShort("Please Enter Details Properly and Correctly");
        }

    }


    @Override
    public void categoryPageDisplayed() {
        Logger.debug(TAG, "Opening Category Page");

    }

    @Override
    public void quizPageDisplayed() {
        getQuestionList();
        mUserScore = 0;
        mIndex = 0;
        if (mList.size() != 0) {
            mQuestion = mList.get(mIndex).getQuestion();
            mOptA = mList.get(mIndex).getOptionA();
            mOptB = mList.get(mIndex).getOptionB();
            mOptC = mList.get(mIndex).getOptionC();
            mOptD = mList.get(mIndex).getOptionD();
            mCorrectAns = mList.get(mIndex).getAnswer();
            if (!TextUtils.isEmpty(mQuestion) && !TextUtils.isEmpty(mOptA)
                    && !TextUtils.isEmpty(mOptB) && !TextUtils.isEmpty(mOptC)
                    && !TextUtils.isEmpty(mOptD)) {
                mQuizView.setData(mQuestion, mOptA, mOptB, mOptC, mOptD,
                        mCurrentCategory, mUserScore, mCurrLevel);
            } else {
                Logger.debug(TAG, "Error in Getting Values from List");
            }
        }

    }

    @Override
    public void nextFabClicked(String userAnswer) {
        if (mCorrectAns.equals(userAnswer)) {
            Logger.debug("LEngth",mCorrectAns+"  length "+mCorrectAns.length()+" " +
                    userAnswer+" length"+ userAnswer.length());
            mUserScore = mUserScore + 1;
            Logger.debug("user Answer if",
                    "" + mCorrectAns + " " + userAnswer + " " + mUserScore);
        }
        mIndex++;
        if (mIndex < mListSize) {
            Logger.debug(TAG + " Index", " " + mIndex);
            mQuestion = mList.get(mIndex).getQuestion();
            mOptA = mList.get(mIndex).getOptionA();
            mOptB = mList.get(mIndex).getOptionB();
            mOptC = mList.get(mIndex).getOptionC();
            mOptD = mList.get(mIndex).getOptionD();
            mCorrectAns = mList.get(mIndex).getAnswer();

            if (!TextUtils.isEmpty(mQuestion) && !TextUtils.isEmpty(mOptA)
                    && !TextUtils.isEmpty(mOptB) && !TextUtils.isEmpty(mOptC)
                    && !TextUtils.isEmpty(mOptD)) {
                mQuizView.setData(mQuestion, mOptA, mOptB, mOptC, mOptD,
                        mCurrentCategory, mUserScore, mCurrLevel);

            } else {
                Logger.debug(TAG, "Error in Getting Values from List");
            }
        }
        if (mIndex == mListSize) {
            Logger.debug(TAG + " Index", " " + mIndex +" user Score"+ mUserScore);
            updateScore();
            mQuizView.openResultPage();
        }

    }

    @Override
    public void getQuestionList() {
        mList = mData.getArrayList();
        mListSize = mList.size();
        Logger.debug(TAG, " list Size" + mListSize);

    }

    @Override
    public void showMessage(String message) {
        Logger.toastShort(message);

    }

    @Override
    public void setView(ResultView resultView) {
        mResultView = resultView;
    }

    @Override
    public void setView(LeaderBoardView leaderBoardView) {
        this.mLeaderBoardView = leaderBoardView;
    }

    @Override
    public void resultPageDisplayed() {
        Logger.debug("user Score in Result", "" + mUserScore);
        if (mUserScore < mListSize ) {
            mResultView.setButtonText("Play Again");
        }
        mResultView.setData(mUserScore, mCurrLevel, mCurrentCategory);
    }

    @Override
    public void nextLevelButtonClicked(int level, String category) {
        mCurrentCategory = category;
        mCurrLevel = level;
        Logger.debug("Current Level and category", " " + mCurrLevel + " " + mCurrentCategory);
        mList = mData.fetchQuestionData(level, category);
        if (!mList.isEmpty()) {
            mData.setArrayList(mList);
            mResultView.openQuizPage();
        } else
            Logger.debug(TAG, "Error while getting List ");
    }

    @Override
    public void homeButtonClicked() {
        mResultView.openProfilePage();
    }

    @Override
    public void updateScore() {
        int levelUpdate = mData.saveScore(mUserName, mCurrentCategory, mCurrLevel, mUserScore);

        if (levelUpdate == 1) {
            Logger.debug(TAG, "Updated Score + Status");
        } else {
            Logger.debug(TAG, "Updated Score onlyScore");
        }
    }

    @Override
    public void leaderBoardButtonClicked() {
        boardModelList = mData.getLeaderBoard();
        Logger.debug(TAG + " LeaderboardList Size", " " + boardModelList.size());
        if (boardModelList.size() != 0) {
            mProfileView.openLeaderBoardPage();
        } else {
            mProfileView.showMessage("No User has Played till now any Category");
        }
    }

    @Override
    public void leaderBoardResultButtonClicked() {
        boardModelList = mData.getLeaderBoard();
        Logger.debug(TAG + " LeaderboardList Size", " " + boardModelList.size());
        if (boardModelList.size() != 0) {
            mResultView.openLeaderBoardPage();
        } else {
            mResultView.showMessage("No User has Played till now");
        }
    }

    @Override
    public void leaderBoardPageDisplayed() {
        mLeaderBoardView.setData(boardModelList);
    }

    @Override
    public int getStatus(String category) {
        mCurrentCategory=category;
        return mData.getCategoryStatus(mUserName, mCurrentCategory);
    }

    @Override
    public void startQuizButtonClicked(int level, String category) {
        mCurrentCategory = category;
        mCurrLevel = level;
        mList = mData.fetchQuestionData(level, category);
        if (!mList.isEmpty()) {
            mData.setArrayList(mList);
            mCategoryView.openQuizPage();
        } else
            Logger.debug(TAG, "Error while getting List ");
    }

    @Override
    public void setView(CategoryView categoryView) {
        mCategoryView = categoryView;
    }

    @Override
    public void setView(QuizView quizView) {
        mQuizView = quizView;
    }
}