package com.mohamedhefny.marveltask.data.source.remote;

import com.mohamedhefny.marveltask.data.source.remote.responseMapping.characters.CharacterResponse;
import com.mohamedhefny.marveltask.data.source.remote.responseMapping.details.DetailsResponse;
import com.mohamedhefny.marveltask.util.AppConstants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServices {

    /**
     * Get character from API
     *
     * @param apiKey    is your public API key.
     * @param hashKey   is a hash value generated using your private API key and timestamp.
     * @param timeStamp value that used to generate a hash key.
     * @param limit     number of returned character per request.
     * @param offset    to start fetch more characters after.
     */
    @GET(AppConstants.EndPoints.CHARACTERS)
    Call<CharacterResponse> getCharacters(
            @Query("apikey") String apiKey,
            @Query("hash") String hashKey,
            @Query("ts") long timeStamp,
            @Query("limit") byte limit,
            @Query("offset") long offset,
            @Query("nameStartsWith") String name);

    @GET("{detailsPath}")
    Call<DetailsResponse> getCharacterDetails(
            @Path("detailsPath") String detailPath,
            @Query("characters") long characterId,
            @Query("apikey") String apiKey,
            @Query("hash") String hashKey,
            @Query("ts") long timeStamp);
}
