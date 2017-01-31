package com.example.shubham.animemania.utility;

/**
 * This Interface Contains Methods Which are Common in Every View
 * Which are
 * 1)initialising widgets
 * 2)Adding Listeners
 * 3)configuring MVP
 * Created by shubham on 26/1/17.
 */
@SuppressWarnings("ALL")
public interface CommonMethods {
    /**
     * Method for Initialising widgets
     */
    void initialiseWidgets();

    /**
     * Method for Adding Listening
     */
    void addListeners();

    /**
     * Method for configuring MVP
     */
    void configureMVP();

    interface LoginView {
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
    }
}