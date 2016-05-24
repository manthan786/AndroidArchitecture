package com.architecture.login.presenter;

/**
 * Created by manthan on 24-05-2016.
 */
public interface LoginPresenter {
    void validateCredentials(String username, String password);

    void onDestroy();

}
