package com.architecture.utils;

/**
 * Created by Manthan on 30-12-2014.
 */
public class RestResponse {

    private String status;
    private String message;
    private int code;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
