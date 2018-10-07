package com.example.hillavas.tipnoo.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContentResult {

    @SerializedName("IsSuccessful")
    @Expose
    private Boolean isSuccessful;
    @SerializedName("Message")
    @Expose
    private Object message;
    @SerializedName("Result")
    @Expose
    private List<ContentList> result = null;

    public Boolean getIsSuccessful() {
        return isSuccessful;
    }

    public void setIsSuccessful(Boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public List<ContentList> getResult() {
        return result;
    }

    public void setResult(List<ContentList> result) {
        this.result = result;
    }

}
