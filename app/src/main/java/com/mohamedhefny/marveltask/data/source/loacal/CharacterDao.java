package com.mohamedhefny.marveltask.data.source.loacal;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mohamedhefny.marveltask.data.entities.Character;

import java.util.List;

@Dao
public interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCharacter(Character character);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<Character> characters);

    @Query("SELECT * FROM characters_table ORDER BY name")
    LiveData<List<Character>> getCharacters();

    @Query("SELECT * FROM characters_table WHERE name = :name")
    Character getCharacterByName(String name);

    @Query("DELETE FROM characters_table")
    void deleteCharacters();

}
