package com.example.hillavas.tipnoo.Models;

public class AddFavoriteBody {
   public String token;
 public  int contentId;

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public int getContentId() {
        return contentId;
    }
}
