package com.example.shubham.animemania.login;

import com.example.shubham.animemania.utility.CommonMethods;

/**
 * RegisterActivity's Interface
 * This Activity is Used for Registering New Player
 * <p>
 * Created by shubham on 3/1/17.
 */
public interface RegisterView extends CommonMethods {
    /**
     * Method for getting newUserName for Entering in Db and Registering
     *
     * @return : USerName
     */
    String getNewUserName();

    /**
     * Method for getting User's Password for storing in DB
     *
     * @return :Password in String format
     */
    String getNewUserPassword();

    /**
     * Method for getting Player Name for Storing in Db
     *
     * @return :FullName of Player
     */
    String getNewName();

    /**
     * Method for getting resource id
     * @return
     */
    int getAvatarId();

    /**
     * Method For opening ProfilePage
     */
    void openProfilePage();
}