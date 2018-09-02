package com.example.pc_ekhtiari.tipnoo.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TabList {
    @SerializedName("ParentId")
    @Expose
    private Integer parentId;
    @SerializedName("CategoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("HasChild")
    @Expose
    private Boolean hasChild;

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getHasChild() {
        return hasChild;
    }

    public void setHasChild(Boolean hasChild) {
        this.hasChild = hasChild;
    }
}
