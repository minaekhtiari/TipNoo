package com.example.hillavas.tipnoo.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContentList {
    @SerializedName("ContentId")
    @Expose
    private Integer contentId;
    @SerializedName("CategoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("LikeCount")
    @Expose
    private Integer likeCount;
    @SerializedName("ViewCount")
    @Expose
    private Integer viewCount;
    @SerializedName("IsLiked")
    @Expose
    private Boolean isLiked;
    @SerializedName("IsBookmarked")
    @Expose
    private Boolean isBookmarked;
    @SerializedName("TeaserId")
    @Expose
    private String teaserId;
    @SerializedName("VideoId")
    @Expose
    private String videoId;
    @SerializedName("HeaderImageId")
    @Expose
    private String headerImageId;
    @SerializedName("InsertDateMi")
    @Expose
    private String insertDateMi;
    @SerializedName("InsertDateSh")
    @Expose
    private String insertDateSh;
    @SerializedName("Subject")
    @Expose
    private String subject;
    @SerializedName("CategoryName")
    @Expose
    private String categoryName;
    @SerializedName("Body")
    @Expose
    private String body;

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Boolean getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(Boolean isLiked) {
        this.isLiked = isLiked;
    }

    public Boolean getIsBookmarked() {
        return isBookmarked;
    }

    public void setIsBookmarked(Boolean isBookmarked) {
        this.isBookmarked = isBookmarked;
    }

    public String getTeaserId() {
        return teaserId;
    }

    public void setTeaserId(String teaserId) {
        this.teaserId = teaserId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getHeaderImageId() {
        return headerImageId;
    }

    public void setHeaderImageId(String headerImageId) {
        this.headerImageId = headerImageId;
    }

    public String getInsertDateMi() {
        return insertDateMi;
    }

    public void setInsertDateMi(String insertDateMi) {
        this.insertDateMi = insertDateMi;
    }

    public String getInsertDateSh() {
        return insertDateSh;
    }

    public void setInsertDateSh(String insertDateSh) {
        this.insertDateSh = insertDateSh;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
