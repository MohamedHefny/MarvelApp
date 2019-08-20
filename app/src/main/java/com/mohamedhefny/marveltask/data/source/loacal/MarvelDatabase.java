package com.mohamedhefny.marveltask.data.source.loacal;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.mohamedhefny.marveltask.data.entities.Character;
import com.mohamedhefny.marveltask.util.AppConstants;
import com.mohamedhefny.marveltask.util.Converters;

import retrofit2.Converter;

/**
 * App database class
 * <p>
 * Singleton design pattern
 */
@Database(entities = {Character.class}, exportSchema = false, version = 1)
@TypeConverters(Converters.class)
public abstract class MarvelDatabase extends RoomDatabase {

    private static MarvelDatabase INSTANCE;

    public abstract CharacterDao characterDao();

    private static final Object sLock = new Object();

    public static MarvelDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        MarvelDatabase.class, AppConstants.DATABASE_NAME)
                        .allowMainThreadQueries() //TODO remove main thread queries and work in background.
                        .build();
            }
            return INSTANCE;
        }
    }
}
