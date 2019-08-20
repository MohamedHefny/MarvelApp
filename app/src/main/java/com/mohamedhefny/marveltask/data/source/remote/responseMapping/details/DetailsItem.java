package com.mohamedhefny.marveltask.data.source.remote.responseMapping.details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mohamedhefny.marveltask.data.entities.Thumbnail;

/**
 * This class represents a simple Comic, Story, Event or Series object with details that needed to display
 * in character details screen.
 */
public class DetailsItem {

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("thumbnail")
    @Expose
    private Thumbnail thumbnail;

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
