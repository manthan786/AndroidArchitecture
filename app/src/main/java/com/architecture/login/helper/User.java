package com.architecture.login.helper;

import com.architecture.utils.RestResponse;

/**
 * Created by manthan on 09-07-2015.
 */
public class User extends RestResponse{

    private String userId;
    private String username;
    private String is_changed;
    private String isDriver;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIs_changed() {
        return is_changed;
    }

    public void setIs_changed(String is_changed) {
        this.is_changed = is_changed;
    }

    public String getIsDriver() {
        return isDriver;
    }

    public void setIsDriver(String isDriver) {
        this.isDriver = isDriver;
    }

}
