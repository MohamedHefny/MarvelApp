package com.mohamedhefny.marveltask.data.source.remote;

import com.mohamedhefny.marveltask.util.AppConstants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConn {

    private static ApiServices mApiServices = null;

    public static ApiServices initRetrofit() {
        if (mApiServices == null) {
            mApiServices = new Retrofit.Builder().baseUrl(AppConstants.EndPoints.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ApiServices.class);
        }

        return mApiServices;
    }
}
