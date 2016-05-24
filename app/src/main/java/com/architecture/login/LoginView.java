package com.architecture.login;

/**
 * Created by manthan on 24-05-2016.
 */
public interface LoginView {

    void showProgress();

    void hideProgress();

    void setUsernameError();

    void setPasswordError();

    void setUsernameOrPasswordIncorrect();

    void navigateToDashboard();

}
