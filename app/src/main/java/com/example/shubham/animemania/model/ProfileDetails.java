package com.example.shubham.animemania.model;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * ProfileDetails
 * For Storing the Details of
 * User to Show in ProfileActivity
 * Created by shubham on 12/1/17.
 */
public class ProfileDetails implements Serializable {

    private String mUserName;
    private String mName;
    private int mUserTotalScore;
    private int mAvatarId;

    /**
     * Method for getting Avatar ResourceId
     * @return :Resource ID
     */
    public int getAvatarId() {
        return mAvatarId;
    }

    /**
     * Method for setting AvatarId
     * @param mAvatarId :Store this ID in
     */
    public void setAvatarId(int mAvatarId) {
        this.mAvatarId = mAvatarId;
    }

    /**
     * Method for getting UserName of Player
     *
     * @return :UserName
     */
    public String getUserName() {
        return mUserName;
    }

    /**
     * Method for setting userName
     *
     * @param userName :UserName of Player
     */
    public void setUserName(String userName) {
        if (!TextUtils.isEmpty(userName)) {
            mUserName = userName;
        } else
            mUserName = "user_name";

    }

    /**
     * Method For getting Name of Player
     *
     * @return : Name of Player
     */
    public String getName() {
        return mName;
    }

    /**
     * Method for Setting name
     *
     * @param name :Name of Player
     */
    public void setName(String name) {
        if (!TextUtils.isEmpty(name))
            mName = name;
        else
            mName = "name";
    }

    /**
     * Method For getting Total Score Of User
     *
     * @return :User TotalScore
     */
    public int getUserTotalScore() {
        return mUserTotalScore;
    }

    /**
     * Method For Setting userTotalScore
     *
     * @param userTotalScore :TotalScore of user
     */
    public void setUserTotalScore(int userTotalScore) {
        if (userTotalScore != 0)
            mUserTotalScore = userTotalScore;
        else
            mUserTotalScore = 0;
    }
}