package com.example.hillavas.tipnoo.Models;

public class ConfigFile {

     String channelId;
      String appName;
      String batchId;

    public void setchannelId(String channelId) {
        this.channelId = channelId;
    }


    public String getchannelId() {
        return channelId;
    }


    public void setAppName(String appName) {
        this.appName = appName;
    }


    public String getAppName() {
        return appName;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getBatchId() {
        return batchId;
    }

}



