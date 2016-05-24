package com.architecture.login.helper;

import com.architecture.utils.RestResponse;
import com.google.gson.annotations.SerializedName;

/**
 * Created by manthan on 09-07-2015.
 */
public class UserResponse extends RestResponse{

    @SerializedName("response")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
