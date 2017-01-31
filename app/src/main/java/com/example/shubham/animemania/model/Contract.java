package com.example.shubham.animemania.model;

/**
 * This Class holds DB,Table,Columns Names
 * <p/>
 * Created by shubham on 3/1/17.
 */
public class Contract {

    public static final String DATABASE_NAME = "AnimeMania";
    public static final int VERSION = 1;
    public static final String TRIVIA_TABLE_NAME = "trivia";
    public static final String LOGIN_TABLE_NAME = "login";
    public static final String LEADERBOARD_TABLE_NAME = "leaderboard";
    public static final String SCORE_TABLE_NAME = "score";

    //Constants for Trivia Question Module
    public static final String TRIVIA_ROW_ID = "id";
    public static final String TRIVIA_ROW_QUESTION = "question";
    public static final String TRIVIA_ROW_OPTION_A = "optionA";
    public static final String TRIVIA_ROW_OPTION_B = "optionB";
    public static final String TRIVIA_ROW_OPTION_C = "optionC";
    public static final String TRIVIA_ROW_OPTION_D = "optionD";
    public static final String TRIVIA_ROW_ANSWER = "answer";
    public static final String TRIVIA_ROW_LEVEL = "level";
    public static final String TRIVIA_ROW_CATEGORY = "category";

    //Constants for Login Table
    public static final String LOGIN_USER_NAME = "user_name";
    public static final String LOGIN_USER_PASSWORD = "password";
    public static final String LOGIN_NAME = "name";
    public static final String LOGIN_AVATAR_ID = "avatar_id";

    //Constants for LeaderBoard Table
    public static final String LEADERBOARD_USER_NAME = "user_name";
    public static final String LEADERBOARD_TOTAL_SCORE = "total_score";

    //constants for score table
    public static final String SCORE_USER_NAME = "user_name";
    public static final String SCORE_CATEGORY = "category";
    public static final String SCORE_LEVEL_1 = "level_1_score";
    public static final String SCORE_LEVEL_2 = "level_2_score";
}