package com.example.shubham.animemania.model;

import java.io.Serializable;

/**
 * Model Class for LeaderBoard Activity
 * Created by shubham on 27/1/17.
 */
public class LeaderBoardModel implements Serializable{
    private String mUserName;
    private int mTotalScore;

    /**
     * Method For getting UserName
     * @return : UserName
     */
    public String getUserName() {
        return mUserName;
    }

    /**
     * Method for setting UserName
     * @param mUserName : userName Of Player
     */
    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    /**
     * Method for getting TotalScore
     * @return : total Score
     */
    public int getTotalScore() {
        return mTotalScore;
    }

    /**
     * Method for Setting Total Score
     * @param mTotalScore :totalScore  of Player
     */
    public void setTotalScore(int mTotalScore) {
        this.mTotalScore = mTotalScore;
    }
}
