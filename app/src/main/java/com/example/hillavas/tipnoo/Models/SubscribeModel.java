
package com.example.hillavas.tipnoo.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubscribeModel {


    @SerializedName("MobileNumber")
    @Expose
    private String MobileNumber;
    @SerializedName("Channel")
    @Expose
    private String Channel;

    @SerializedName("ServiceId")
    @Expose
    private String servicecId;

    public SubscribeModel() {

        Channel = "A-Teepeto";
        servicecId="11";
    }

    public String getMobileNumber() {

        return MobileNumber;
    }

    public String getServicecId() {
        return servicecId;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getChannel() {
        return Channel;
    }

    public void setChannel(String channel) {
        Channel = channel;
    }
}
