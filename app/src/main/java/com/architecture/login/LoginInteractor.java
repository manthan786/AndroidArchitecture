package com.architecture.login;

/**
 * Created by manthan on 24-05-2016.
 */
public interface LoginInteractor {

    interface OnLoginFinishedListener {
        void onUsernamePasswordIncorrect();
        void onSuccess();
    }

    void login(String username, OnLoginFinishedListener listener);


}
