package com.mohamedhefny.marveltask.data.source.remote.responseMapping.details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailsData {
    @SerializedName("offset")
    @Expose
    private long offset;
    @SerializedName("limit")
    @Expose
    private long limit;
    @SerializedName("total")
    @Expose
    private long total;
    @SerializedName("count")
    @Expose
    private long count;
    @SerializedName("results")
    @Expose
    private List<DetailsItem> detailsItemList;

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public long getLimit() {
        return limit;
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<DetailsItem> getDetailsItemList() {
        return detailsItemList;
    }

    public void setDetailsItemList(List<DetailsItem> detailsItemList) {
        this.detailsItemList = detailsItemList;
    }
}
