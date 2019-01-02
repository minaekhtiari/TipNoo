
package com.example.hillavas.tipnoo.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubscribeConfirmModel {

//    @SerializedName("MobileNumber")
//    @Expose
//    private long mobileNumber;

    @SerializedName("TransactionId")
    @Expose
    private String transactionId;
    @SerializedName("ServiceId")
    @Expose
    private Integer serviceId;
    @SerializedName("pin")
    @Expose
    private String pin;

    public SubscribeConfirmModel() {
        serviceId=11;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
