package com.example.hillavas.tipnoo.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultJsonMemberSignUp {

    @SerializedName("IsSuccessful")
    @Expose
    private Boolean isSuccessful;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Result")
    @Expose
    private MemberSignUp memberSignUp;

    public Boolean getIsSuccessful() {
        return isSuccessful;
    }

    public void setIsSuccessful(Boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(Boolean successful) {
        isSuccessful = successful;
    }

    public MemberSignUp getMemberSignUp() {
        return memberSignUp;
    }

    public void setMemberSignUp(MemberSignUp memberSignUp) {
        this.memberSignUp = memberSignUp;
    }
}
