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

    private CharacterDetailsRepository mCharacterDetailsRepo;

    public DetailsViewModel(@NonNull Application application) {
        super(application);
        mCharacterDetailsRepo = AppDependencies.provideCharsDetailsRepo();
    }

    public void initSelectedCharacter(CharactersRepository charactersRepo) {
        mCharacterDetailsRepo.setCharacter(charactersRepo.getLastSelectedCharacter());
    }


    /**
     * Load character details
     */
    public void loadDetails() {
        mCharacterDetailsRepo.loadCharacterDetails(AppConstants.EndPoints.COMICS);
        mCharacterDetailsRepo.loadCharacterDetails(AppConstants.EndPoints.SERIES);
        mCharacterDetailsRepo.loadCharacterDetails(AppConstants.EndPoints.STORIES);
        mCharacterDetailsRepo.loadCharacterDetails(AppConstants.EndPoints.EVENTS);
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

    public Character getCharacter() {
        return mCharacterDetailsRepo.getCharacter();
    }

    public LiveData<List<DetailsItem>> getComicsList() {
        return mCharacterDetailsRepo.getComicsList();
    }

    public LiveData<List<DetailsItem>> getSeriesList() {
        return mCharacterDetailsRepo.getSeriesList();
    }

    public LiveData<List<DetailsItem>> getStoriesList() {
        return mCharacterDetailsRepo.getStoriesList();
    }

    public LiveData<List<DetailsItem>> getEventsList() {
        return mCharacterDetailsRepo.getEventsList();
    }
}
