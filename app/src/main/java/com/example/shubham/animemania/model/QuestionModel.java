package com.example.shubham.animemania.model;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Model Class for Question in app
 * <p>
 * Created by shubham on 11/1/17.
 */
public class QuestionModel implements Serializable {

    private int mRowId;
    private String mQuestion;
    private String mOptionA;
    private String mOptionB;
    private String mOptionC;
    private String mOptionD;
    private String mAnswer;
    private int mLevel;
    private String mCategory;

    /*
    Constructor for Question Model class
     */
    public QuestionModel() {

    }

    /**
     * Method for getting row id
     *
     * @return :rowId
     */
    public int getRowId() {
        return mRowId;
    }

    /**
     * Method for setting Row id
     *
     * @param mRowId :id Of Row
     */
    public void setRowId(int mRowId) {
        this.mRowId = mRowId;
    }

    /**
     * Method for getting Question
     *
     * @return : String
     */
    public String getQuestion() {
        return mQuestion;
    }

    /**
     * Method for setting question
     *
     * @param mQuestion : string
     */
    public void setQuestion(String mQuestion) {
        if (!TextUtils.isEmpty(mQuestion))
            this.mQuestion = mQuestion;
        else
            this.mQuestion = "question";
    }

    /**
     * Method for getting optionA
     *
     * @return :String
     */
    public String getOptionA() {
        return mOptionA;
    }

    /**
     * Method for setting Option A
     *
     * @param mOptionA :String
     */
    public void setOptionA(String mOptionA) {
        if (!TextUtils.isEmpty(mOptionA))
            this.mOptionA = mOptionA;
    }

    /**
     * Method for getting optionB
     *
     * @return :String
     */
    public String getOptionB() {
        return mOptionB;
    }

    /**
     * Method for setting OptionB
     *
     * @param mOptionB :String
     */
    public void setOptionB(String mOptionB) {
        if (!TextUtils.isEmpty(mOptionB))
            this.mOptionB = mOptionB;

    }

    /**
     * Method for getting optionC
     *
     * @return :String
     */
    public String getOptionC() {
        return mOptionC;
    }

    /**
     * Method for setting OptionC
     *
     * @param mOptionC:String
     */
    public void setOptionC(String mOptionC) {
        if (!TextUtils.isEmpty(mOptionC))
            this.mOptionC = mOptionC;
    }

    /**
     * Method for getting OptionD
     *
     * @return :String
     */
    public String getOptionD() {
        return mOptionD;
    }

    /**
     * Method For Setting OptionD
     *
     * @param mOptionD: String
     */
    public void setOptionD(String mOptionD) {
        if (!TextUtils.isEmpty(mOptionD))
            this.mOptionD = mOptionD;
    }

    /**
     * Method for getting getting Answer
     *
     * @return :String
     */
    public String getAnswer() {
        return mAnswer;
    }

    /**
     * Method for Setting Answer
     *
     * @param mAnswer :String
     */
    public void setAnswer(String mAnswer) {
        if (!TextUtils.isEmpty(mAnswer))
            this.mAnswer = mAnswer;
    }

    /**
     * Method for getting level
     *
     * @return :int
     */
    public int getLevel() {
        return mLevel;
    }

    /**
     * Method for Setting level
     *
     * @param mLevel :int
     */
    public void setLevel(int mLevel) {
        if (mLevel != 0)
            this.mLevel = mLevel;
        else
            this.mLevel = 1;
    }

    /**
     * Method for getting Category
     *
     * @return :String
     */
    public String getCategory() {
        return mCategory;
    }

    /**
     * Method for Setting Category
     *
     * @param mCategory :String
     */
    public void setCategory(String mCategory) {
        if (!TextUtils.isEmpty(mCategory))
            this.mCategory = mCategory;
        else
            this.mCategory="category";
    }
}