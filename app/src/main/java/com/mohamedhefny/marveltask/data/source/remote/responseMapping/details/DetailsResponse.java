package com.mohamedhefny.marveltask.data.source.remote.responseMapping.details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mohamedhefny.marveltask.data.source.remote.responseMapping.GeneralResponse;
import com.mohamedhefny.marveltask.data.source.remote.responseMapping.details.DetailsData;

/**
 * Class that represent a character details response with simple data as needed to display.
 */
public class DetailsResponse extends GeneralResponse {

    @SerializedName("data")
    @Expose
    private DetailsData detailsData;

    public DetailsData getDetailsData() {
        return detailsData;
    }

    public void setDetailsData(DetailsData detailsData) {
        this.detailsData = detailsData;
    }
}
