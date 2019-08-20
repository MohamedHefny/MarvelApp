package com.mohamedhefny.marveltask.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoriesItemRes extends ItemRes {
    @SerializedName("type")
    @Expose
    private String type;

    public StoriesItemRes() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
