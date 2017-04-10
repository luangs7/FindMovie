package com.example.luan.findmovie.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dev_Maker on 19/10/2016.
 */
public class BaseRequest {


    @SerializedName("success")
    @Expose
    private Boolean success;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("count")
    private Long count;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Boolean isSuccess() {
        if(success==null)
            return  false;
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        if(message==null)
            return "";
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
