package com.architecture.login;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.architecture.utils.Global;
import com.architecture.utils.HttpWrapper;
import com.architecture.utils.RestLoader;

/**
 * Created by manthan on 24-05-2016.
 */
public class LoginPresenterImpl implements LoginPresenter, LoaderManager.LoaderCallbacks<UserResponse>, LoginInteractor.OnLoginFinishedListener {

    private LoginView loginView;
    private LoginInteractor loginInteractor;
    private Context context;

    public LoginPresenterImpl(LoginView loginView, Context context){
        this.loginView = loginView;
        this.context = context;
        this.loginInteractor = new LoginInteractorImpl();
    }

    @Override
    public Loader<UserResponse> onCreateLoader(int id, Bundle args) {
        if (null != args && args.containsKey(Global.ARGS_URI)) {
            HttpWrapper httpWrapper = new HttpWrapper(Global.HTTP_POST, Global.JSON_RESPONSE, args.getString(Global.ARGS_URI));
            httpWrapper.addToParamsMap("userid", args.getString(Global.ARGS_USER_ID));
            httpWrapper.addToParamsMap("password", args.getString(Global.ARGS_PASSWORD));
            httpWrapper.addToParamsMap("isDriver", args.getString(Global.ARGS_DRIVER));
            return new RestLoader<UserResponse>(context, httpWrapper, UserResponse.class);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<UserResponse> loader, UserResponse loginResponse) {
        if (null != loginResponse && loginResponse.getStatus().equalsIgnoreCase("success")) {
            loginInteractor.login(loginResponse.getUser().getUsername(),this);
        }else{
            loginInteractor.login(null,this);
        }
    }

    @Override
    public void onLoaderReset(Loader<UserResponse> loader) {

    }

    @Override
    public void validateCredentials(String username, String password) {
        if(username.equalsIgnoreCase("")){
           loginView.setUsernameError();
        }else if(password.equalsIgnoreCase("")){
            loginView.setPasswordError();
        }else{
            if(loginView != null) {
                loginView.showProgress();
                Bundle args = new Bundle();
                String url = Global.API_BASE_URL + "user/dologin/";
                args.putString(Global.ARGS_URI, url);
                args.putString(Global.ARGS_USER_ID, username);
                args.putString(Global.ARGS_PASSWORD, password);
                args.putString(Global.ARGS_DRIVER, String.valueOf("0"));
                ((LoginActivity) context).getSupportLoaderManager().restartLoader(1, args, this);
            }
        }
    }

    @Override
    public void onDestroy() {
        loginView = null;
    }

    @Override
    public void onUsernamePasswordIncorrect() {
        if(loginView != null) {
            loginView.setUsernameOrPasswordIncorrect();
            loginView.hideProgress();
        }
    }

    @Override
    public void onSuccess() {
        if(loginView != null) {
            loginView.navigateToDashboard();
        }
    }


}
