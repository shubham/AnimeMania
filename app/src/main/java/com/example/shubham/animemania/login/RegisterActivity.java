package com.example.shubham.animemania.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.example.shubham.animemania.R;
import com.example.shubham.animemania.model.Avatar;
import com.example.shubham.animemania.model.AvatarAdapter;
import com.example.shubham.animemania.presenter.TriviaPresenter;
import com.example.shubham.animemania.utility.AppContext;
import com.example.shubham.animemania.utility.Logger;

/**
 * RegisterActivity
 * For Registering New User
 * <p/>
 * User Enters Three Things
 * 1)Unique userName of 8 Letters
 * 2)FullName of Player
 * 3)Password
 */
public class RegisterActivity extends AppCompatActivity implements RegisterView, View.OnClickListener {
    private static final int MAX_USER_NAME_LENGTH = 8;
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private TriviaPresenter mTriviaPresenter;
    private Button mRegisterUserButton;
    private EditText mNewUserNameEditText;
    private EditText mNewUserPasswordEditText;
    private EditText mNewNameEditText;
    private GridView mAvatarGridView;
    private Avatar mSelectedAvatar;
    private View mSelectedAvatarView;
    private int mAvatarId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialiseWidgets();
        addListeners();
        configureMVP();
    }

    @Override
    public void initialiseWidgets() {

        mRegisterUserButton = (Button) findViewById(R.id.register_user_button);
        mNewUserNameEditText = (EditText) findViewById(R.id.new_user_name_editText);
        mNewUserPasswordEditText = (EditText) findViewById(R.id.new_user_password_edit_Text);
        mNewNameEditText = (EditText) findViewById(R.id.new_name_editText);
        mAvatarGridView = (GridView) findViewById(R.id.avatars);
        //Setting Adapter
        mAvatarGridView.setAdapter(new AvatarAdapter(AppContext.getContext()));
    }

    @Override
    public void addListeners() {
        mRegisterUserButton.setOnClickListener(this);
        mAvatarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectedAvatarView = view;
                int viewId = mSelectedAvatarView.getId();
                mSelectedAvatar = Avatar.values()[position];
                mAvatarId = mSelectedAvatar.getDrawableId();
                if (mAvatarId == -1) {
                    mAvatarId = viewId;
                }
                //when User Select an Avatar then Register Button Would Display
                mRegisterUserButton.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void configureMVP() {
        mTriviaPresenter = TriviaPresenter.getInstance();
        mTriviaPresenter.setView(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        mTriviaPresenter.registerViewDisplayed();
    }

    @Override
    public String getNewUserName() {
        Editable name = mNewUserNameEditText.getText();
        return name != null ? name.toString() : "";
    }

    @Override
    public String getNewUserPassword() {
        Editable password = mNewUserPasswordEditText.getText();
        return password != null ? password.toString() : "";
    }

    @Override
    public String getNewName() {
        Editable name = mNewNameEditText.getText();
        return name != null ? name.toString() : "";
    }

    @Override
    public int getAvatarId() {
        return mAvatarId;
    }

    @Override
    public void openProfilePage() {
        launchIntent(ProfileActivity.class);

    }

    @Override
    public void onClick(View view) {

        Logger.debug(TAG, "User Register Button Clicked");
        if (validate())
            mTriviaPresenter.registerUserClicked();
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

    private boolean validate() {
        boolean check = true;

        String userName = mNewUserNameEditText.getText().toString();
        String password = mNewUserPasswordEditText.getText().toString();
        String name = mNewNameEditText.getText().toString();
        if (name.isEmpty() || name.length() < 1 || name.length() > 20) {
            check = false;
            mNewNameEditText.setError("Enter Name Correctly");
        } else {
            mNewNameEditText.setError(null);
        }
        if (userName.isEmpty() || userName.length() > MAX_USER_NAME_LENGTH) {
            check = false;
            mNewUserNameEditText.setError("less or equal to 8 Characters");
        } else {
            mNewUserNameEditText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            mNewUserPasswordEditText.setError("between 4 and 10 alphanumeric characters");
            check = false;
        } else {
            mNewUserPasswordEditText.setError(null);
        }
        return check;
    }
}