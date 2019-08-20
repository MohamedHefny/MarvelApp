package com.mohamedhefny.marveltask.util;

import android.content.Context;

import com.mohamedhefny.marveltask.data.entities.Character;
import com.mohamedhefny.marveltask.data.source.CharacterDetailsRepository;
import com.mohamedhefny.marveltask.data.source.CharactersRepository;
import com.mohamedhefny.marveltask.data.source.loacal.MarvelDatabase;
import com.mohamedhefny.marveltask.data.source.remote.ApiServices;
import com.mohamedhefny.marveltask.data.source.remote.RetrofitConn;

public class AppDependencies {

    /**
     * Singleton design pattern to allow only direct calls without objects instantiate for this class
     */
    private AppDependencies() {

    }

    public static CharactersRepository provideCharsRepo(Context context) {
        return CharactersRepository.getInstance(provideLocalSource(context), provideApiServices());
    }

    public static CharacterDetailsRepository provideCharsDetailsRepo() {
        return CharacterDetailsRepository.getInstance(null);
    }

    public static CharacterDetailsRepository provideCharsDetailsRepo(Character character) {
        return CharacterDetailsRepository.getInstance(character);
    }

    private static ApiServices provideApiServices() {
        return RetrofitConn.initRetrofit();
    }

    private static MarvelDatabase provideLocalSource(Context context) {
        return MarvelDatabase.getInstance(context);
    }
}
