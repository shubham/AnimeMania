package com.example.shubham.animemania.quiz;

import com.example.shubham.animemania.utility.CommonMethods;

/**
 * QuizView Contains methods Quiz Activity
 * <p>
 * Created by shubham on 5/1/17.
 */
public interface QuizView extends CommonMethods {
    /**
     * Method for setting Data in Quiz Activity
     *
     * @param ques     : question which has to display
     * @param optA     : option A
     * @param optB     : option B
     * @param optC     : option C
     * @param optD     : option D
     * @param category : Category which player has selected
     * @param score    : score for showing progress and UserScore to User
     * @param level    : Level on which user is Currently for that Category
     */
    void setData(String ques, String optA, String optB,
                 String optC, String optD, String category,
                 int score, int level);

    /**
     * Method For opening Result Page
     * When All the question are finished
     */
    void openResultPage();
}