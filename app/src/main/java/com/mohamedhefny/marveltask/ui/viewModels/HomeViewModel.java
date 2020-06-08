package com.mohamedhefny.marveltask.ui.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mohamedhefny.marveltask.data.entities.Character;
import com.mohamedhefny.marveltask.data.source.CharactersRepository;
import com.mohamedhefny.marveltask.util.AppDependencies;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private CharactersRepository mCharsRepository;
    private LiveData<List<Character>> mCharsList;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        mCharsRepository = AppDependencies.provideCharsRepo(application);
    }

    /**
     * Start retrieving data from repository starting from the first item
     */
    public void loadCharacters() {
        mCharsList = mCharsRepository.getCharacters(0);
    }

    /**
     * Load more characters if available
     *
     * @param offset the current number of existing characters to load after
     */
    public void loadNextCharactersPage(long offset) {
        mCharsRepository.getCharacters(offset);
    }

    /**
     * Using this method to search in characters by name
     *
     * @param name any string you want to find characters within
     */
    public void findCharactersByName(String name) {
        mCharsRepository.searchByName(name);
    }

    public LiveData<List<Character>> getCharsList() {
        return mCharsList;
    }

    public LiveData<List<Character>> getSearchList() {
        return mCharsRepository.getSearchList();
    }

    public void setSelectedCharacter(Character character) {
        mCharsRepository.setLastSelectedCharacter(character);
    }

    public boolean isLoading() {
        return mCharsRepository.isLoading().getValue();
    }
}
