package com.example.shubham.animemania.login;

import com.example.shubham.animemania.utility.CommonMethods;

/**
 * Interface Class For Login Activity
 * Created by shubham on 3/1/17.
 */
public interface LoginView extends CommonMethods{

    /**
     * Method for opening Register Page
     */
    void openRegisterPage();

    /**
     * Method for getting String Name
     *
     * @return :Returns UserName which user Entered
     */
    String getUserName();

    /**
     * Method for Getting Password
     *
     * @return :Returns Password which User Entered
     */
    String getUserPassword();

    /**
     * Method For Displaying UserName
     */
    void loginButtonDisplay();

    /**
     * Method For opening the Profile Page
     */
    void openProfilePage();

    /**
     * Method For Setting Data in userName and Password
     * @param userName: Name of User
     * @param password :Password
     */
    void setData(String userName,String password);
}