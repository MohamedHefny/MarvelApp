package com.mohamedhefny.marveltask.ui.detailsPager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.mohamedhefny.marveltask.R;
import com.mohamedhefny.marveltask.data.source.remote.responseMapping.details.DetailsItem;
import com.mohamedhefny.marveltask.ui.BaseActivity;
import com.mohamedhefny.marveltask.ui.detailsPager.adapters.DetailsPagerAdapter;
import com.mohamedhefny.marveltask.ui.viewModels.DetailsViewModel;
import com.mohamedhefny.marveltask.util.AppConstants;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DetailsPagerActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.details_pager_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.details_pager_pages_count_tv)
    TextView mPageCount;

    //Initialize pages assuming at least 1 page if this activity opened
    private int mCurrentPage = 1;
    private int mTotalPages = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        enableFullScreen(TRANSPARENT_STATUS_BAR, TRANSPARENT_STATUS_BAR);

        setContentView(R.layout.activity_details_pager);

        String mListType = getListType();

        DetailsViewModel detailsViewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);

        List<DetailsItem> detailsItems = detailsViewModel.getPagerList(mListType).getValue();

        mTotalPages = detailsItems.size();
        mCurrentPage = getClickedPosition() + 1;
        setupViewpager(detailsItems);
        setCurrentPage(mCurrentPage);
    }

    private void setupViewpager(List<DetailsItem> detailsItems) {
        mViewPager.setAdapter(new DetailsPagerAdapter(DetailsPagerActivity.this, detailsItems));
        mViewPager.setCurrentItem(getClickedPosition(), true);
        mViewPager.addOnPageChangeListener(this);

        //TODO Support pagination to load more items if existing when scrolling reaches the end.
    }

    private void setCurrentPage(int currentPage) {
        mPageCount.setText(String.valueOf(currentPage).concat("/").concat(String.valueOf(mTotalPages)));
        mCurrentPage = currentPage;
    }

    private String getListType() {
        return getIntent().getStringExtra(AppConstants.DETAILS_LIST_TYPE);
    }

    private int getClickedPosition() {
        return getIntent().getIntExtra(AppConstants.DETAILS_ITEM_POSITION, 0);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        setCurrentPage(position + 1);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnClick({R.id.details_pager_close_btn})
    public void onClick(View v) {
        if (v.getId() == R.id.details_pager_close_btn)
            onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.normal_static, R.anim.fade_out);
    }
}
