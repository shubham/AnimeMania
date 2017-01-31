package com.example.shubham.animemania.presenter;

import com.example.shubham.animemania.leaderboard.LeaderBoardView;
import com.example.shubham.animemania.login.LoginView;
import com.example.shubham.animemania.login.ProfileView;
import com.example.shubham.animemania.login.RegisterView;
import com.example.shubham.animemania.quiz.CategoryView;
import com.example.shubham.animemania.quiz.QuizView;
import com.example.shubham.animemania.quiz.ResultView;

/**
 * Interface for Common Presenter
 *
 * Created by shubham on 3/1/17.
 */
public interface PresenterInterface {

    /**
     * Method for Checking the Shared Preference
     *
     * @return :boolean
     */
    boolean checkSharedPreference();

    /**
     * Method For setting LoginView in Presenter so that Presenter can call LoginView Methods
     *
     * @param loginView :instance
     */
    void setView(LoginView loginView);

    /**
     * Method For setting registerView in Presenter so that Presenter can call registerView Methods
     *
     * @param registerView :instance
     */
    void setView(RegisterView registerView);

    /**
     * Method For setting profileView in Presenter so that Presenter can call ProfileView Methods
     *
     * @param profileView :instance
     */
    void setView(ProfileView profileView);

    /**
     * Method For opening Profile Page When Login Is Completed
     */
    void loginComplete();

    /**
     * Method For setting Data if Present in Shared Preference
     * when LoginPage is Displayed
     */
    void loginPageDisplayed();

    /**
     * Method for Performing When Login Button is clicked
     */
    void loginButtonClicked();

    /**
     * Method for Opening Register Page when clicked in Login Page
     */
    void clickedRegisterButton();

    /**
     * Method for setting View
     */
    void registerViewDisplayed();

    /**
     * Method for Opening Profile page when Register is completed
     */
    void registerComplete();

    /**
     * Method For Setting Data in ProfilePage after Displaying
     */
    void profilePageDisplayed();

    /**
     * Method For opening Category Page when Play button is pressed in Profile Page
     */
    void playingButtonClicked();

    /**
     * Method for Logging out the user
     */
    void signOutClicked();

    /**
     * Method for Registering User
     */
    void registerUserClicked();

    /**
     * Method For setting categoryView in Presenter so that Presenter can call categoryView Methods
     *
     * @param categoryView :instance
     */
    void setView(CategoryView categoryView);

    /**
     * Method For setting Data in Category Page after it is displayed
     */
    void categoryPageDisplayed();

    /**
     * Method For Starting The Quiz
     *
     * @param level    :Current level of user in that category
     * @param category :selected Category
     */
    void startQuizButtonClicked(int level, String category);

    /**
     * Method For setting quizView in Presenter so that Presenter can call quizView Methods
     *
     * @param quizView :instance
     */
    void setView(QuizView quizView);

    /**
     * Method for setting data in quiz Page after it is displayed
     */
    void quizPageDisplayed();

    /**
     * Method for displaying next question and checking the user's answer of previous question
     *
     * @param userAnswer :String
     */
    void nextFabClicked(String userAnswer);

    /**
     * Method for getting QuestionList
     */
    void getQuestionList();

    /**
     * Method for Showing Toast message
     *
     * @param message :String
     */
    void showMessage(String message);

    /**
     * Method For setting resultView in Presenter so that Presenter can call resultView Methods
     *
     * @param resultView :instance
     */
    void setView(ResultView resultView);

    /**
     * Method For Setting data in Result Page after it is displayed
     */
    void resultPageDisplayed();

    /**
     * Method For Playing again or for playing next level
     *
     * @param level    :int
     * @param category :string
     */
    void nextLevelButtonClicked(int level, String category);

    /**
     * Method for Opening Profile Page
     */
    void homeButtonClicked();

    /**
     * Method for Getting status for user for the selected category
     *
     * @param category :String
     * @return :status of user on that category
     */
    int getStatus(String category);

    /**
     * Method for Updating Score
     */
    void updateScore();

    /**
     * Method for opening LeaderBoard Page in Profile page
     */
    void leaderBoardButtonClicked();

    /**
     * Method For setting Data in LeaderBoard Page
     */
    void leaderBoardPageDisplayed();

    /**
     * Method For setting leaderBoardView in Presenter so that Presenter can call leaderBoardView Methods
     *
     * @param leaderBoardView :instance
     */
    void setView(LeaderBoardView leaderBoardView);

    /**
     * Method for Opening leaderBoard in Result Page
     */
    void leaderBoardResultButtonClicked();
}