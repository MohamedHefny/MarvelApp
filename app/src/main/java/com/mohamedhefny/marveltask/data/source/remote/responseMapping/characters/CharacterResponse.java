package com.mohamedhefny.marveltask.data.source.remote.responseMapping.characters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mohamedhefny.marveltask.data.source.remote.responseMapping.GeneralResponse;

public class CharacterResponse extends GeneralResponse {
    @SerializedName("data")
    @Expose
    private CharactersData charactersData;

    public CharactersData getCharactersData() {
        return charactersData;
    }

    public void setCharactersData(CharactersData charactersData) {
        this.charactersData = charactersData;
    }
}
