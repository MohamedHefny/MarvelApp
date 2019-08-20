package com.mohamedhefny.marveltask.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CharacterDetails {

    @SerializedName("available")
    @Expose
    private long available;
    @SerializedName("collectionURI")
    @Expose
    private String collectionURI;
    @SerializedName("items")
    @Expose
    private List<ItemRes> items = null;
    @SerializedName("returned")
    @Expose
    private long returned;

    public long getAvailable() {
        return available;
    }

    public void setAvailable(long available) {
        this.available = available;
    }

    public String getCollectionURI() {
        return collectionURI;
    }

    public void setCollectionURI(String collectionURI) {
        this.collectionURI = collectionURI;
    }

    public List<ItemRes> getItems() {
        return items;
    }

    public void setItems(List<ItemRes> items) {
        this.items = items;
    }

    public long getReturned() {
        return returned;
    }

    public void setReturned(long returned) {
        this.returned = returned;
    }
}
