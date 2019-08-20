package com.mohamedhefny.marveltask.ui.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mohamedhefny.marveltask.data.entities.Character;
import com.mohamedhefny.marveltask.data.source.CharacterDetailsRepository;
import com.mohamedhefny.marveltask.data.source.CharactersRepository;
import com.mohamedhefny.marveltask.data.source.remote.responseMapping.details.DetailsItem;
import com.mohamedhefny.marveltask.util.AppConstants;
import com.mohamedhefny.marveltask.util.AppDependencies;

import java.util.List;

public class DetailsViewModel extends AndroidViewModel {

    private CharacterDetailsRepository mCharacterDetailsRepository;

    private Character mCharacter;

    public DetailsViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Initialize variables
     *
     * @param position   is the character position to select from current mCharsRepository list,
     *                   value can be negative if you init() for the second time, it will just get the existing repo object.
     * @param searchFlag boolean to determine which list to select character from using position.
     */
    public void init(CharactersRepository charactersRepo, int position, boolean searchFlag) {

        if (position < 0) {
            mCharacterDetailsRepository = AppDependencies.provideCharsDetailsRepo();
            return;
        }

        mCharacter = charactersRepo.getCharacterFromList(position, searchFlag);
        mCharacterDetailsRepository = AppDependencies.provideCharsDetailsRepo(mCharacter);
        checkPersistenceData();
    }

    /**
     * Load character details
     */
    public void loadDetails() {
        mCharacterDetailsRepository.loadCharacterDetails(AppConstants.EndPoints.COMICS, mCharacter.getCharId());
        mCharacterDetailsRepository.loadCharacterDetails(AppConstants.EndPoints.SERIES, mCharacter.getCharId());
        mCharacterDetailsRepository.loadCharacterDetails(AppConstants.EndPoints.STORIES, mCharacter.getCharId());
        mCharacterDetailsRepository.loadCharacterDetails(AppConstants.EndPoints.EVENTS, mCharacter.getCharId());
    }

    /**
     * @param listType is a string value that have the name of the list.
     * @return the corresponding list type from repository.
     */
    public LiveData<List<DetailsItem>> getPagerList(String listType) {
        switch (listType) {
            case AppConstants.EndPoints.COMICS:
                return getComicsList();
            case AppConstants.EndPoints.SERIES:
                return getSeriesList();
            case AppConstants.EndPoints.STORIES:
                return getStoriesList();
            case AppConstants.EndPoints.EVENTS:
                return getEventsList();
        }
        return null;
    }

    /**
     * Check if the new character selected, then clear the old character data
     */
    private void checkPersistenceData() {
        if (mCharacter.getCharId() != mCharacterDetailsRepository.getCharacter().getCharId())
            mCharacterDetailsRepository.clearPersistence();
    }

    public Character getCharacter() {
        return mCharacter;
    }

    public LiveData<List<DetailsItem>> getComicsList() {
        return mCharacterDetailsRepository.getComicsList();
    }

    public LiveData<List<DetailsItem>> getSeriesList() {
        return mCharacterDetailsRepository.getSeriesList();
    }

    public LiveData<List<DetailsItem>> getStoriesList() {
        return mCharacterDetailsRepository.getStoriesList();
    }

    public LiveData<List<DetailsItem>> getEventsList() {
        return mCharacterDetailsRepository.getEventsList();
    }
}
