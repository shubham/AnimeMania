package com.example.shubham.animemania.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.shubham.animemania.R;
import com.example.shubham.animemania.presenter.TriviaPresenter;

/**
 * LoginActivity
 * Player enter their credentials for logging in
 * after LogIn The Profile page is Displayed
 */
public class LoginActivity extends AppCompatActivity
        implements LoginView, View.OnClickListener {
    private static final int MAX_USER_NAME_LENGTH = 8;
    private Button mRegisterButton;
    private Button mLoginButton;
    private EditText mUserNameEditText;
    private EditText mUserPasswordEditText;
    private TriviaPresenter mTriviaPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialiseWidgets();
        addListeners();
        configureMVP();
    }

    @Override
    public void onResume() {
        super.onResume();
        mTriviaPresenter.loginPageDisplayed();
    }

    @Override
    public void initialiseWidgets() {
        mLoginButton = (Button) findViewById(R.id.login_button);
        mRegisterButton = (Button) findViewById(R.id.register_button);
        mUserNameEditText = (EditText) findViewById(R.id.user_name_editText);
        mUserPasswordEditText = (EditText) findViewById(R.id.user_password_edit_Text);

    }

    @Override
    public void addListeners() {
        mRegisterButton.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);

    }

    @Override
    public void configureMVP() {
        mTriviaPresenter = TriviaPresenter.getInstance();
        mTriviaPresenter.setView(this);

    }

    @Override
    public void openRegisterPage() {
        launchIntent(RegisterActivity.class);
    }

    @Override
    public String getUserName() {
        Editable name = mUserNameEditText.getText();
        return name != null ? name.toString() : "";
    }

    @Override
    public String getUserPassword() {
        Editable password = mUserPasswordEditText.getText();
        return password != null ? password.toString() : "";
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.login_button:
                if (validate()) {
                    mTriviaPresenter.loginButtonClicked();
                }
                break;
            case R.id.register_button:
                mTriviaPresenter.clickedRegisterButton();
                break;
        }

    }

    @Override
    public void loginButtonDisplay() {
        mLoginButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void openProfilePage() {
        launchIntent(ProfileActivity.class);

    }

    @Override
    public void setData(String userName, String password) {
        mUserNameEditText.setText(userName);
        mUserPasswordEditText.setText(password);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private boolean validate() {
        boolean check=true;

        String userName = mUserNameEditText.getText().toString();
        String password = mUserPasswordEditText.getText().toString();

        if (userName.isEmpty() || userName.length() > MAX_USER_NAME_LENGTH) {
            check=false;
            mUserNameEditText.setError("less or equal to 8 Characters");
        } else {
            mUserNameEditText.setError(null);

        }
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            mUserPasswordEditText.setError("between 4 and 10 alphanumeric characters");
            check = false;
        }else {
            mUserPasswordEditText.setError(null);
        }
        return check;
    }
}