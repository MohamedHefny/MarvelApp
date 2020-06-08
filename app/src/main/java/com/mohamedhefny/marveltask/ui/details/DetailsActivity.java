package com.mohamedhefny.marveltask.ui.details;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.jgabrielfreitas.core.BlurImageView;
import com.mohamedhefny.marveltask.R;
import com.mohamedhefny.marveltask.data.entities.CharLinks;
import com.mohamedhefny.marveltask.data.entities.Character;
import com.mohamedhefny.marveltask.data.source.remote.responseMapping.details.DetailsItem;
import com.mohamedhefny.marveltask.ui.BaseActivity;
import com.mohamedhefny.marveltask.ui.details.adapters.DetailsAdapter;
import com.mohamedhefny.marveltask.ui.viewModels.DetailsViewModel;
import com.mohamedhefny.marveltask.util.AppConstants;
import com.mohamedhefny.marveltask.util.AppDependencies;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DetailsActivity extends BaseActivity {

    @BindView(R.id.details_character_poster)
    ImageView mPosterImg;
    @BindView(R.id.details_blur_img)
    BlurImageView mBlurBackground;
    @BindView(R.id.details_char_name_tv)
    TextView mCharacterName;
    @BindView(R.id.details_char_description_tv)
    TextView mCharacterDescription;
    @BindView(R.id.details_comics_recycler)
    RecyclerView mComicsRecycler;
    @BindView(R.id.details_series_recycler)
    RecyclerView mSeriesRecycler;
    @BindView(R.id.details_stories_recycler)
    RecyclerView mStoriesRecycler;
    @BindView(R.id.details_events_recycler)
    RecyclerView mEventsRecycler;
    @BindView(R.id.details_links_recycler)
    RecyclerView mLinksRecycler;

    private DetailsViewModel mDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableFullScreen(HIDE_STATUS_BAR, HIDE_STATUS_BAR);
        setContentView(R.layout.activity_details);

        mDetailsViewModel = obtainViewModel(this);

        mDetailsViewModel.initSelectedCharacter(AppDependencies.provideCharsRepo(this));

        mDetailsViewModel.loadDetails();

        Character mCharacter = mDetailsViewModel.getCharacter();

        bindPrimaryData(mCharacter);
        bindLinksRecycler(mCharacter.getUrls());

        observeDetailsLists();
    }

    private DetailsViewModel obtainViewModel(FragmentActivity activity) {
        return ViewModelProviders.of(activity).get(DetailsViewModel.class);
    }

    /**
     * Subscribe to details.
     */
    private void observeDetailsLists() {
        mDetailsViewModel.getComicsList().observe(this, detailsItems -> bindDetailsRecyclers(mComicsRecycler, AppConstants.EndPoints.COMICS, detailsItems));
        mDetailsViewModel.getSeriesList().observe(this, detailsItems -> bindDetailsRecyclers(mSeriesRecycler, AppConstants.EndPoints.SERIES, detailsItems));
        mDetailsViewModel.getStoriesList().observe(this, detailsItems -> bindDetailsRecyclers(mStoriesRecycler, AppConstants.EndPoints.STORIES, detailsItems));
        mDetailsViewModel.getEventsList().observe(this, detailsItems -> bindDetailsRecyclers(mEventsRecycler, AppConstants.EndPoints.EVENTS, detailsItems));
    }

    /**
     * Bind existing primary data for character details
     *
     * @param character is the current character to bind its details.
     */
    private void bindPrimaryData(Character character) {

        mCharacterName.setText(character.getName());

        mCharacterDescription.setText(character.getDescription());

        Picasso.get()
                .load(character.getThumbnail().getPath().concat("/standard_xlarge.")
                        .concat(character.getThumbnail().getExtension()))
                .into(mPosterImg, new Callback() {
                    @Override
                    public void onSuccess() {
                        //Set blurring image from the loaded poster to avoid loading the same image twice.
                        mBlurBackground.setImageDrawable(mPosterImg.getDrawable());
                        mBlurBackground.setBlur(10);
                    }

                    @Override
                    public void onError(Exception e) {
                    }
                });
    }

    /**
     * Attach adapters to details list
     *
     * @param recyclerView you want to fill with data.
     * @param listType     name or type of the list.
     * @param detailsItems list of items to bind
     */
    private void bindDetailsRecyclers(RecyclerView recyclerView, String listType, List<DetailsItem> detailsItems) {
        recyclerView.setAdapter(new DetailsAdapter(DetailsActivity.this, listType, detailsItems));
    }

    private void bindLinksRecycler(List<CharLinks> charLinks) {
        mLinksRecycler.setAdapter(new LinksAdapter(DetailsActivity.this, charLinks));
    }

    @OnClick({R.id.details_back_btn})
    public void onClick(View v) {
        if (v.getId() == R.id.details_back_btn)
            onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_home_enter, R.anim.activity_details_exit);
    }
}
