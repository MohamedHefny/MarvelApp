package com.mohamedhefny.marveltask.ui.home;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.mohamedhefny.marveltask.R;
import com.mohamedhefny.marveltask.data.entities.Character;
import com.mohamedhefny.marveltask.ui.BaseActivity;
import com.mohamedhefny.marveltask.ui.details.DetailsActivity;
import com.mohamedhefny.marveltask.ui.home.adapters.HomeAdapter;
import com.mohamedhefny.marveltask.ui.home.adapters.SearchAdapter;
import com.mohamedhefny.marveltask.ui.viewModels.HomeViewModel;
import com.mohamedhefny.marveltask.util.AppConstants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity implements ClickCallback, SearchView.OnQueryTextListener {

    //TODO Use searchable activity to filter result
    @BindView(R.id.toolbar_logo)
    ImageView mLogo;
    @BindView(R.id.toolbar_home_searchview)
    SearchView mSearchView;
    @BindView(R.id.toolbar_home_search_btn)
    ImageButton mSearchBtn;
    @BindView(R.id.toolbar_home_cancel_btn)
    TextView mCloseSearch;
    @BindView(R.id.home_characters_recycler)
    RecyclerView mCharactersRecycler;

    private HomeViewModel mHomeViewModel;
    private HomeAdapter mHomeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mHomeViewModel = obtainViewModel(this);
        mHomeViewModel.loadCharacters();

        prepareRecycler();

        observeCharacters();

        listenForNextPage();
    }

    /**
     * Setup and prepare recyclerView once,
     * then update it every time new characters are loading in observation method.
     */
    private void prepareRecycler() {
        mHomeAdapter = new HomeAdapter(new ArrayList<>(), this);
        mCharactersRecycler.setAdapter(mHomeAdapter);
    }

    private HomeViewModel obtainViewModel(FragmentActivity activity) {
        return ViewModelProviders.of(activity).get(HomeViewModel.class);
    }

    /**
     * Subscribe to new characters.
     */
    private void observeCharacters() {
        mHomeViewModel.getCharsList().observe(this, characters -> mHomeAdapter.addNewCharacters(characters));
    }

    /**
     * Listen while recyclerView reaches to the last item,
     * then fetching the next page of characters.
     */
    private void listenForNextPage() {
        mCharactersRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!mCharactersRecycler.canScrollVertically(1) && !mHomeViewModel.isLoading())
                    mHomeViewModel.loadNextCharactersPage(mHomeAdapter.getItemCount());
            }
        });
    }

    private void onStartSearching() {
        mLogo.setVisibility(View.INVISIBLE);
        mSearchBtn.setVisibility(View.INVISIBLE);
        mCloseSearch.setVisibility(View.VISIBLE);
        mSearchView.setVisibility(View.VISIBLE);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setIconified(false);

        mCharactersRecycler.setAdapter(new SearchAdapter(new ArrayList<>(), this));

        mHomeViewModel.getSearchList()
                .observe(this, characterList ->
                        mCharactersRecycler.setAdapter(new SearchAdapter(characterList, this)));
    }

    private void onCloseSearch() {
        mHomeViewModel.getSearchList().removeObservers(this);
        mCharactersRecycler.setAdapter(mHomeAdapter);
        mLogo.setVisibility(View.VISIBLE);
        mSearchBtn.setVisibility(View.VISIBLE);
        mCloseSearch.setVisibility(View.INVISIBLE);
        mSearchView.setVisibility(View.INVISIBLE);
        mSearchView.setOnQueryTextListener(null);
        mSearchView.setIconified(true);
        mSearchView.clearFocus();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (query.length() < 1)
            return false;

        mHomeViewModel.findCharactersByName(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.length() < 1)
            return false;

        mHomeViewModel.findCharactersByName(newText);
        return true;
    }

    @Override
    public void onCharacterSelected(Character character) {
        mHomeViewModel.setSelectedCharacter(character);
        startActivity(new Intent(HomeActivity.this, DetailsActivity.class));
        overridePendingTransition(R.anim.activity_details_enter, R.anim.activity_home_exit);
    }

    @OnClick({R.id.toolbar_home_search_btn, R.id.toolbar_home_cancel_btn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_home_search_btn:
                onStartSearching();
                break;
            case R.id.toolbar_home_cancel_btn:
                onCloseSearch();
                break;
        }
    }
}
