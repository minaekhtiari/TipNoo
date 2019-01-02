package com.example.hillavas.tipnoo.Models;

import android.content.Context;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class JsonUtils {

    public static ConfigFile loadConfigInfo(Context context) {
        String json = null;
        ConfigFile configFile = new ConfigFile();
        try {
            InputStream is = context.getAssets().open("config.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(json);


            configFile.setBatchId(jsonObject.getString("batchId"));
            configFile.setchannelId(jsonObject.getString("channelId"));

            return configFile;


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

}
