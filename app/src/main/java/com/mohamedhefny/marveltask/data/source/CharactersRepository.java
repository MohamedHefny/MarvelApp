package com.mohamedhefny.marveltask.data.source;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mohamedhefny.marveltask.data.entities.Character;
import com.mohamedhefny.marveltask.data.source.loacal.MarvelDatabase;
import com.mohamedhefny.marveltask.data.source.remote.ApiServices;
import com.mohamedhefny.marveltask.data.source.remote.responseMapping.characters.CharacterResponse;
import com.mohamedhefny.marveltask.util.AppConstants;
import com.mohamedhefny.marveltask.util.HashGenerator;

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

    private static CharactersRepository mINSTANCE;

    private ApiServices mApiServices;
    private MarvelDatabase mLocalSource;

    private MutableLiveData<Boolean> mLoadingIndicator;
    private LiveData<List<Character>> mCharactersList;
    private MutableLiveData<List<Character>> mSearchList;

    private Character mLastSelectedCharacter;

    private static final byte mLoadingLimitNumber = 12;
    private long mMaxPageNumber;

    private CharactersRepository(MarvelDatabase localSource, ApiServices remoteSource) {
        mLocalSource = localSource;
        mApiServices = remoteSource;
        mLoadingIndicator = new MutableLiveData<>();
        mCharactersList = mLocalSource.characterDao().getCharacters();
    }

    public static CharactersRepository getInstance(MarvelDatabase localSource, ApiServices remoteSource) {
        if (mINSTANCE == null)
            mINSTANCE = new CharactersRepository(localSource, remoteSource);

        return mINSTANCE;
    }

    public LiveData<List<Character>> getCharacters(long offset) {

        //Return if the last item reached.
        if (offset >= mMaxPageNumber && offset > 0)
            return mCharactersList;

        mLoadingIndicator.setValue(true);

        long timestamp = System.currentTimeMillis() / 1000;

        mApiServices.getCharacters(AppConstants.API_PUBLIC_KEY, getHash(timestamp), timestamp, mLoadingLimitNumber, offset, null)
                .enqueue(new Callback<CharacterResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<CharacterResponse> call, @NonNull Response<CharacterResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            mMaxPageNumber = response.body().getCharactersData().getTotal();
                            mLocalSource.characterDao().insertAll(response.body().getCharactersData().getCharacters());
                        }
                        mLoadingIndicator.setValue(false);
                    }

                    @Override
                    public void onFailure(@NonNull Call<CharacterResponse> call, @NonNull Throwable t) {
                        mLoadingIndicator.setValue(false);
                        //Handel error
                    }
                });

        return mCharactersList;
    }

    public void searchByName(String name) {
        mLoadingIndicator.setValue(true);

        long timestamp = System.currentTimeMillis() / 1000;

        mApiServices.getCharacters(AppConstants.API_PUBLIC_KEY, getHash(timestamp), timestamp, (byte) 25, 0, name)
                .enqueue(new Callback<CharacterResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<CharacterResponse> call, @NonNull Response<CharacterResponse> response) {
                        if (response.isSuccessful() && response.body() != null)
                            mSearchList.setValue(response.body().getCharactersData().getCharacters());
                    }

                    @Override
                    public void onFailure(@NonNull Call<CharacterResponse> call, @NonNull Throwable t) {
                        //Handel error
                    }
                });
    }

    /**
     * Get hash using private key and timestamp to use it for request data from API.
     */
    private String getHash(long timestamp) {
        return HashGenerator.generate(timestamp, AppConstants.API_PRIVATE_KEY, AppConstants.API_PUBLIC_KEY);
    }

    public LiveData<List<Character>> getSearchList() {
        if (mSearchList == null)
            mSearchList = new MutableLiveData<>();
        return mSearchList;
    }

    public void setLastSelectedCharacter(Character mLastSelectedCharacter) {
        this.mLastSelectedCharacter = mLastSelectedCharacter;
    }

    public Character getLastSelectedCharacter() {
        return mLastSelectedCharacter;
    }

    public LiveData<Boolean> isLoading() {
        return mLoadingIndicator;
    }
}
