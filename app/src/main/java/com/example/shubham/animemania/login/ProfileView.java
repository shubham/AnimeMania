package com.example.shubham.animemania.login;

import com.example.shubham.animemania.utility.CommonMethods;

/**
 * Contains Methods For ProfileActivity
 * Created by shubham on 23/1/17.
 */
public interface ProfileView extends CommonMethods {

    /**
     * Method For Opening CategoryPage
     */
    void openCategoryPage();

    /**
     * Method for Opening LeaderBoard
     */
    void openLeaderBoardPage();

    /**
     * Method Open LoginPage
     */
    void openLoginPage();

    /**
     * Method for setting Data
     *
     * @param userName   :UserName of Player
     * @param name       : Name of Player
     * @param totalScore :TotalScore Of Player
     * @param avatarId   : Resource id Of that image is saved in DB
     */
    void setData(String userName, String name, int totalScore, int avatarId);

    /**
     * For Showing Some Message by Presenter
     * @param message :Message To be Shown
     */
    void showMessage(String message);
}