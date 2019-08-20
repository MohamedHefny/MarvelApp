package com.mohamedhefny.marveltask.ui.detailsPager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.mohamedhefny.marveltask.R;
import com.mohamedhefny.marveltask.data.source.remote.responseMapping.details.DetailsItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailsPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<DetailsItem> mDetailsItems;

    public DetailsPagerAdapter(Context context, List<DetailsItem> detailsItems) {
        mContext = context;
        mDetailsItems = detailsItems;
    }

    @Override
    public int getCount() {
        return mDetailsItems.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View pagerLayout = LayoutInflater.from(mContext)
                .inflate(R.layout.item_details_pager, container, false);

        ImageView pagerImg = pagerLayout.findViewById(R.id.item_details_pager_img);
        TextView pagerTitle = pagerLayout.findViewById(R.id.item_details_pager_text);

        if (mDetailsItems.get(position).getThumbnail() == null)
            pagerImg.setImageResource(R.drawable.ic_broken_image);
        else
            Picasso.get()
                    .load(mDetailsItems.get(position).getThumbnail().getPath().concat("/portrait_uncanny.").concat(mDetailsItems.get(position).getThumbnail().getExtension()))
                    .into(pagerImg);

        pagerTitle.setText(mDetailsItems.get(position).getTitle());

        container.addView(pagerLayout, 0);

        return pagerLayout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
