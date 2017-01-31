package com.example.shubham.animemania.model;

import android.text.TextUtils;

import com.example.shubham.animemania.utility.AppContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Model Class For Implementing operation which require data to be fetched from Db
 * <p>
 * Created by shubham on 11/1/17.
 */
public class TriviaData implements TriviaDataInterface {
    private static TriviaData ourInstance = new TriviaData();
    private ArrayList<QuestionModel> arrayList;
    private DbHelper helper=DbHelper.getInstance(AppContext.getContext(),Contract.DATABASE_NAME);

    /**
     * Constructor of TriviaData
     */
    private TriviaData() {
    }

    /**
     * Method for getting Instance of TriviaData
     *
     * @return : instance of TriviaData
     */
    public static TriviaData getInstance() {
        return ourInstance;
    }

    /**
     * Method for getting ArrayList of Questions
     *
     * @return arrayList of question
     */
    public ArrayList<QuestionModel> getArrayList() {
        return arrayList;
    }

    /**
     * method for setting list
     *
     * @param arrayList: List which contain questions
     */
    public void setArrayList(ArrayList<QuestionModel> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public ArrayList<QuestionModel> fetchQuestionData(int level, String category) {
        if (level != 0 && !TextUtils.isEmpty(category)) {
            return DbHelper.getQuestionData(level, category);
        } else {
            level = 1;
            category = "Naruto";
            return DbHelper.getQuestionData(level, category);
        }
    }

    @Override
    public boolean isUserPresent(String userName, String password) {
        return DbHelper.isExist(userName, password);
    }

    @Override
    public boolean addUser(String userName, String name, String password,long avatarId) {
        return DbHelper.addRegister(userName, name, password,avatarId);
    }

    @Override
    public ProfileDetails getProfileData(String userName) {
        return DbHelper.getProfileData(userName);
    }

    @Override
    public List<LeaderBoardModel> getLeaderBoard() {
        return DbHelper.getLeaderBoardList();
    }

    @Override
    public int saveScore(String userName, String category, int level, int userScore) {
        int out;
        long outNew;
        int oldTotalScore=DbHelper.getOldTotalScore(userName);
        int oldScore=DbHelper.getOldScore(userName,category,level);
        if (oldScore==-1)
        {
            if (oldTotalScore==0) {
                out = DbHelper.updateTotalScore(userName, userScore);
            }
            else
            {
                oldTotalScore=oldTotalScore+userScore;
                out = DbHelper.updateTotalScore(userName, oldTotalScore);
            }
                outNew = DbHelper.addScore(userName, category, userScore);
        }
        else
        {
            if (oldTotalScore==0)
            {
                oldTotalScore=oldTotalScore+userScore;
                outNew=DbHelper.updateScore(userName,category,userScore,level);
                out=DbHelper.updateTotalScore(userName,oldTotalScore);
            }
            else{
                oldTotalScore=oldTotalScore-oldScore;
                oldTotalScore=oldTotalScore+userScore;
                out=DbHelper.updateTotalScore(userName,oldTotalScore);
                outNew=DbHelper.updateScore(userName,category,userScore,level);
            }
        }
        outNew=out+outNew;
        if (outNew>2)
        {
            return 1;
        }
        else
            return 0;
    }

    @Override
    public int getCategoryStatus(String userName, String category) {
        return DbHelper.getStatus(userName, category);
    }

}