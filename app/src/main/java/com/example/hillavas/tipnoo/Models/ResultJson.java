
package com.example.hillavas.tipnoo.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultJson {

    @SerializedName("IsSuccessful")
    @Expose
    private boolean isSuccessfull;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Result")
    @Expose
    private String result;

    public boolean isIsSuccessfull() {
        return isSuccessfull;
    }

    public void setIsSuccessfull(boolean isSuccessfull) {
        this.isSuccessfull = isSuccessfull;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccessfull() {
        return isSuccessfull;
    }

    public void setSuccessfull(boolean successfull) {
        isSuccessfull = successfull;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
