package com.example.hillavas.tipnoo.Models;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class VideoContentObject implements Serializable {

    public VideoContentObject() {
    }



    @SerializedName("ContentId")
    @Expose
    private Integer contentId;
    @SerializedName("FavoriteId")
    @Expose
    private Integer favoriteId;
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
    @SerializedName("Tags")
    @Expose
    private String tags;
    @SerializedName("AllTags")
    @Expose
    private List<AllTag> allTags = null;
    @SerializedName("VideoFileAddress")
    @Expose
    private String videoFileAddress;
    @SerializedName("HeaderImageFileAddress")
    @Expose
    private String headerImageFileAddress;
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

    public Integer getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(Integer favoriteId) {
        this.favoriteId = favoriteId;
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public List<AllTag> getAllTags() {
        return allTags;
    }

    public void setAllTags(List<AllTag> allTags) {
        this.allTags = allTags;
    }

    public String getVideoFileAddress() {
        return videoFileAddress;
    }

    public void setVideoFileAddress(String videoFileAddress) {
        this.videoFileAddress = videoFileAddress;
    }

    public String getHeaderImageFileAddress() {
        return headerImageFileAddress;
    }

    public void setHeaderImageFileAddress(String headerImageFileAddress) {
        this.headerImageFileAddress = headerImageFileAddress;
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
