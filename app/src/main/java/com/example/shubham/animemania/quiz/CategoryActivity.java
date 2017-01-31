package com.example.shubham.animemania.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.shubham.animemania.R;
import com.example.shubham.animemania.presenter.TriviaPresenter;
import com.example.shubham.animemania.utility.Logger;

/**
 * CategoryActivity For Showing The Category to user
 * <p>
 * From These Categories user Select anyone for playing
 */
public class CategoryActivity extends AppCompatActivity implements CategoryView, View.OnClickListener {

    private static final String TAG = CategoryActivity.class.getName();
    private static final int LEVEL_1 = 1;
    private static final int LEVEL_2 = 2;
    private Button mStartButton;
    private RadioGroup mCategoryRadioGroup;

    private TriviaPresenter mTriviaPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        initialiseWidgets();
        addListeners();
        configureMVP();
    }

    @Override
    public void initialiseWidgets() {
        mStartButton = (Button) findViewById(R.id.start_quiz_button);
        mCategoryRadioGroup = (RadioGroup) findViewById(R.id.option_radioGroup);

    }

    @Override
    public void addListeners() {
        mStartButton.setOnClickListener(this);

    }

    @Override
    public void configureMVP() {
        mTriviaPresenter = TriviaPresenter.getInstance();
        mTriviaPresenter.setView(this);
    }

    @Override
    public void openQuizPage() {
        launchIntent(QuizActivity.class);
    }

    @Override
    public void onClick(View view) {
        String category;
        int rowId = mCategoryRadioGroup.getCheckedRadioButtonId();
        Logger.debug(TAG, "" + rowId);
        if (rowId != -1) {
            RadioButton radioButton = (RadioButton) findViewById(rowId);
            Logger.debug(TAG, "" + rowId + "  " + radioButton.getText());
            category = radioButton.getText().toString();
            if (!TextUtils.isEmpty(category)) {
                int status = mTriviaPresenter.getStatus(category);
                switch (status) {
                    case 0:/*if status is 0 means User is Playing for first time
                                  or Have Not Cleared the first level*/
                        if (!TextUtils.isEmpty(category)) {
                            mTriviaPresenter.startQuizButtonClicked(LEVEL_1, category);
                        } else {
                            mTriviaPresenter.startQuizButtonClicked(LEVEL_1, "Naruto");
                        }
                        break;
                    case 1:
                        /*if status is 1 means User has cleared level 1
                                  and not cleared level 2 or not Played */
                        if (!TextUtils.isEmpty(category)) {
                            mTriviaPresenter.startQuizButtonClicked(LEVEL_2, category);
                        } else {
                            mTriviaPresenter.startQuizButtonClicked(LEVEL_2, "Naruto");
                        }
                        break;
                    default:
                        mTriviaPresenter.showMessage("You Have completed All the Level");
                        break;
                }
            } else {
                mTriviaPresenter.showMessage("Please Select a Category");
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mTriviaPresenter.categoryPageDisplayed();
    }

    /**
     * method for launching activity
     *
     * @param iActivityClass: instance of passed class name
     */
    private void launchIntent(Class iActivityClass) {
        //opening the passed class
        Intent intent = new Intent(this, iActivityClass);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}