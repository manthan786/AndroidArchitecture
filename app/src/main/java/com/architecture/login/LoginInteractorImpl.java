package com.architecture.login;

/**
 * Created by manthan on 24-05-2016.
 */
public class LoginInteractorImpl implements LoginInteractor{

    @Override
    public void login(String username, OnLoginFinishedListener listener) {
        boolean error = false;

        if(null == username){
            listener.onUsernamePasswordIncorrect();
            error = true;
        }
        if(!error){
            listener.onSuccess();
        }
    }
}
