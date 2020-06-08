package com.mohamedhefny.marveltask.data.source;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.mohamedhefny.marveltask.data.entities.Character;
import com.mohamedhefny.marveltask.data.source.remote.ApiServices;
import com.mohamedhefny.marveltask.data.source.remote.RetrofitConn;
import com.mohamedhefny.marveltask.data.source.remote.responseMapping.details.DetailsItem;
import com.mohamedhefny.marveltask.data.source.remote.responseMapping.details.DetailsResponse;
import com.mohamedhefny.marveltask.util.AppConstants;
import com.mohamedhefny.marveltask.util.HashGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A repository for character details that work to fetch details from remote source and do some operations on it.
 * <p>
 * Singleton design pattern
 */
public class CharacterDetailsRepository {

    private static CharacterDetailsRepository mCharDetailsRepository;
    private ApiServices mApiServices;

    private Character mCharacter;

    private MutableLiveData<List<DetailsItem>> mComicsList;
    private MutableLiveData<List<DetailsItem>> mSeriesList;
    private MutableLiveData<List<DetailsItem>> mStoriesList;
    private MutableLiveData<List<DetailsItem>> mEventsList;

    private CharacterDetailsRepository() {
        mApiServices = RetrofitConn.initRetrofit();
        mComicsList = new MutableLiveData<>();
        mSeriesList = new MutableLiveData<>();
        mStoriesList = new MutableLiveData<>();
        mEventsList = new MutableLiveData<>();
    }

    public static CharacterDetailsRepository getInstance() {
        if (mCharDetailsRepository == null)
            mCharDetailsRepository = new CharacterDetailsRepository();

        return mCharDetailsRepository;
    }

    //TODO Schedule net work calling to improve performance.
    public void loadCharacterDetails(String detailPath) {

        long timestamp = System.currentTimeMillis() / 1000;

        //Generate hash using private key and timestamp to use it for request data from API.
        String hash = HashGenerator.generate(timestamp, AppConstants.API_PRIVATE_KEY, AppConstants.API_PUBLIC_KEY);

        mApiServices.getCharacterDetails(detailPath, mCharacter.getCharId(), AppConstants.API_PUBLIC_KEY, hash, timestamp)
                .enqueue(new Callback<DetailsResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<DetailsResponse> call, @NonNull Response<DetailsResponse> response) {
                        if (response.body() != null) {
                            getListType(detailPath).setValue(response.body().getDetailsData().getDetailsItemList());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<DetailsResponse> call, @NonNull Throwable t) {
                        //Handel error
                    }
                });
    }

    private MutableLiveData<List<DetailsItem>> getListType(String detailPath) {
        switch (detailPath) {
            case AppConstants.EndPoints.COMICS:
                return mComicsList;
            case AppConstants.EndPoints.SERIES:
                return mSeriesList;
            case AppConstants.EndPoints.STORIES:
                return mStoriesList;
            case AppConstants.EndPoints.EVENTS:
                return mEventsList;
        }
        return null;
    }

    public void clearPersistence() {
        mComicsList.setValue(new ArrayList<>());
        mSeriesList.setValue(new ArrayList<>());
        mStoriesList.setValue(new ArrayList<>());
        mEventsList.setValue(new ArrayList<>());
    }

    public void setCharacter(Character character) {
        mCharacter = character;
        clearPersistence();
    }

    public Character getCharacter() {
        return mCharacter;
    }

    public MutableLiveData<List<DetailsItem>> getComicsList() {
        return mComicsList;
    }

    public MutableLiveData<List<DetailsItem>> getSeriesList() {
        return mSeriesList;
    }

    public MutableLiveData<List<DetailsItem>> getStoriesList() {
        return mStoriesList;
    }

    public MutableLiveData<List<DetailsItem>> getEventsList() {
        return mEventsList;
    }
}
