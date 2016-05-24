package com.architecture.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.androidArch.R;
import com.architecture.login.presenter.LoginPresenter;
import com.architecture.login.presenter.LoginPresenterImpl;
import com.architecture.login.view.LoginView;
import com.architecture.main.BaseActivity;
import com.architecture.main.MainActivity;



public class LoginActivity extends BaseActivity implements LoginView,View.OnClickListener{

    private ProgressBar progressBar;
    private EditText username;
    private EditText password;
    private Button login;

    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void init(){
        progressBar = (ProgressBar)findViewById(R.id.progress);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.button) ;

        login.setOnClickListener(this);

        presenter = new LoginPresenterImpl(this,this);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setUsernameError() {
        username.setError("Username cannot be empty");
    }

    @Override
    public void setPasswordError() {
        password.setError("Password cannot be empty");
    }

    @Override
    public void setUsernameOrPasswordIncorrect() {

    }

    @Override
    public void navigateToDashboard() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onClick(View view) {
       switch(view.getId()){
           case R.id.button:
               presenter.validateCredentials(username.getText().toString(),password.getText().toString());
       }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_login;
    }
}
