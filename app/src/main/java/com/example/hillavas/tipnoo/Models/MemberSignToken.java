package com.example.hillavas.tipnoo.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MemberSignToken {


    @SerializedName("IsSuccessfull")
    @Expose
    private Boolean isSuccessfull;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("ResultToken")
    @Expose
    private ResultToken resultToken;

    public Boolean getIsSuccessfull() {
        return isSuccessfull;
    }

    public void setIsSuccessfull(Boolean isSuccessfull) {
        this.isSuccessfull = isSuccessfull;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setResultToken(ResultToken resultToken) {
        this.resultToken = resultToken;
    }

    public ResultToken getResultToken() {
        return resultToken;
    }
}
