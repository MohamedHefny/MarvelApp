package com.mohamedhefny.marveltask.data.source.remote.responseMapping.characters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mohamedhefny.marveltask.data.entities.Character;

import java.util.List;

/**
 * Class represents a chartres object within the response
 */
public class CharactersData {
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
    private List<Character> characters = null;

    public CharactersData() {
    }

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

    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }
}
