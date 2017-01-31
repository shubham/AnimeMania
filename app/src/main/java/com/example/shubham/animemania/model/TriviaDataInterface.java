package com.example.shubham.animemania.model;

import java.util.ArrayList;
import java.util.List;

/**
 * TriviaData Interface for Model Operations
 * <p>
 * Created by shubham on 12/1/17.
 */
public interface TriviaDataInterface {

    /**
     * Method for getting questionFrom the Db for the selected category
     *
     * @param level    :Level of Player in that category
     * @param category :Category which user Selected
     * @return :ArrayList which contains question
     */
    ArrayList<QuestionModel> fetchQuestionData(int level, String category);

    /**
     * Method for Checking User is Present or not
     *
     * @param userName :userName which player entered
     * @param password :password which player entered
     * @return : user present or not
     */
    boolean isUserPresent(String userName, String password);

    /**
     * Method for Registering new Player
     *
     * @param userName :userName of Player
     * @param name     :Full Name Of Player
     * @param password :Password of Player
     * @param avatarId :Resource ID of user's Image
     * @return : status of user added or not
     */
    boolean addUser(String userName, String name, String password, long avatarId);

    /**
     * Method for get Profile Data of layer
     *
     * @param userName :userName of Player
     * @return : ProfileDetails object which contain detail of player
     */
    ProfileDetails getProfileData(String userName);

    /**
     * Method For Saving the score of user for that category
     *
     * @param userName  :userName of Player
     * @param category  :Category for which to update score
     * @param status    :status of Category
     * @param userScore :score of User in that category
     * @return : no of row affected
     */
    int saveScore(String userName, String category, int status, int userScore);

    /**
     * Method For getting Status of Category for User
     *
     * @param userName :userName of Player
     * @param category :Category which user Selected
     * @return : Status of User
     */
    int getCategoryStatus(String userName, String category);


    /**
     * Method For getting The List of LeaderBoard
     * @return :List which Contains UserName and Their Total Score
     */
    List<LeaderBoardModel> getLeaderBoard();
}