package com.example.shubham.animemania.quiz;

import com.example.shubham.animemania.utility.CommonMethods;

/**
 * ResultView interface for Showing Result
 * Created by shubham on 19/1/17.
 */
public interface ResultView extends CommonMethods {
    /**
     * Method for setting Data
     *
     * @param result   :userScore
     * @param level    :Level of user
     * @param category : Category of User
     */
    void setData(int result, int level, String category);

    /**
     * Method for opening QuizFragment
     */
    void openQuizPage();

    /**
     * Method For ProfileActivity
     */
    void openProfilePage();

    /**
     * Method for Setting text in NextLevelButton
     *
     * @param message :text to be set on  Button
     */
    void setButtonText(String message);

    /**
     * Method for opening LeaderBoardPage
     */
    void openLeaderBoardPage();
    /**
     * For Showing Some Message by Presenter
     * @param message :Message To be Shown
     */
    void showMessage(String message);
}