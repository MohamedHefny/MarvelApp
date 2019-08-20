package com.mohamedhefny.marveltask.data.source;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mohamedhefny.marveltask.data.entities.Character;
import com.mohamedhefny.marveltask.data.source.loacal.MarvelDatabase;
import com.mohamedhefny.marveltask.data.source.remote.ApiServices;
import com.mohamedhefny.marveltask.data.source.remote.RetrofitConn;
import com.mohamedhefny.marveltask.data.source.remote.responseMapping.characters.CharacterResponse;
import com.mohamedhefny.marveltask.util.AppConstants;
import com.mohamedhefny.marveltask.util.HashGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A repository for characters that work to get data from remote or local source.
 * <p>
 * Singleton design pattern
 */
public class CharactersRepository {

    private static CharactersRepository mCharsRepository;
    private ApiServices mApiServices;
    private MarvelDatabase mLocalSource;

    private LiveData<List<Character>> mCharactersList;
    private MutableLiveData<List<Character>> mSearchList;
    private MutableLiveData<Boolean> mIsLoading;

    private static final byte mLoadingLimitNumber = 12;
    private long mMaxPageNumber;

    private CharactersRepository(Context context) {
        mApiServices = RetrofitConn.initRetrofit();
        mLocalSource = MarvelDatabase.getInstance(context);
        mCharactersList = mLocalSource.characterDao().getCharacters();
        mIsLoading = new MutableLiveData<>();
    }

    public static CharactersRepository getInstance(Context context) {
        if (mCharsRepository == null)
            mCharsRepository = new CharactersRepository(context);

        return mCharsRepository;
    }

    public LiveData<List<Character>> getCharacters(long offset) {

        //Return if the last item reached.
        if (offset >= mMaxPageNumber && offset > 0)
            return mCharactersList;


        long timestamp = System.currentTimeMillis() / 1000;

        //Generate hash using private key and timestamp to use it for request data from API.
        String hash = HashGenerator.generate(timestamp, AppConstants.API_PRIVATE_KEY, AppConstants.API_PUBLIC_KEY);

        mIsLoading.setValue(true);
        mApiServices.getCharacters(AppConstants.API_PUBLIC_KEY, hash, timestamp, mLoadingLimitNumber, offset, null)
                .enqueue(new Callback<CharacterResponse>() {
                    @Override
                    public void onResponse(Call<CharacterResponse> call, Response<CharacterResponse> response) {
                        mLocalSource.characterDao().insertAll(response.body().getCharactersData().getCharacters());
                        mMaxPageNumber = response.body().getCharactersData().getTotal();
                        mIsLoading.setValue(false);
                    }

                    @Override
                    public void onFailure(Call<CharacterResponse> call, Throwable t) {
                        mIsLoading.setValue(false);
                        //Handel error
                    }
                });

        return mCharactersList;
    }

    public void searchByName(String name) {

        long timestamp = System.currentTimeMillis() / 1000;

        //Generate hash using private key and timestamp to use it for request data from API.
        String hash = HashGenerator.generate(timestamp, AppConstants.API_PRIVATE_KEY, AppConstants.API_PUBLIC_KEY);

        mIsLoading.setValue(true);
        mApiServices.getCharacters(AppConstants.API_PUBLIC_KEY, hash, timestamp, (byte) 25, 0, name)
                .enqueue(new Callback<CharacterResponse>() {
                    @Override
                    public void onResponse(Call<CharacterResponse> call, Response<CharacterResponse> response) {
                        mSearchList.setValue(response.body().getCharactersData().getCharacters());
                    }

                    @Override
                    public void onFailure(Call<CharacterResponse> call, Throwable t) {
                        //Handel error
                    }
                });
    }

    public LiveData<List<Character>> getSearchList() {
        if (mSearchList == null)
            mSearchList = new MutableLiveData<>();
        return mSearchList;
    }

    public Character getCharacterFromList(int position, boolean searchFlag) {
        if (searchFlag)
            return mSearchList.getValue().get(position);
        else
            return mCharactersList.getValue().get(position);
    }

    public LiveData<Boolean> isLoading() {
        return mIsLoading;
    }

}
