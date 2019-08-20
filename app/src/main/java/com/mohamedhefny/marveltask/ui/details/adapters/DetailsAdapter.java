package com.mohamedhefny.marveltask.ui.details.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mohamedhefny.marveltask.R;
import com.mohamedhefny.marveltask.data.entities.Thumbnail;
import com.mohamedhefny.marveltask.data.source.remote.responseMapping.details.DetailsItem;
import com.mohamedhefny.marveltask.ui.detailsPager.DetailsPagerActivity;
import com.mohamedhefny.marveltask.util.AppConstants;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder> {

    private Activity mActivity;
    private String mListType;
    private List<DetailsItem> mDetailsItems;

    public DetailsAdapter(Activity activity, String listType, List<DetailsItem> detailsItems) {
        mActivity = activity;
        mListType = listType;
        mDetailsItems = detailsItems;
    }

    @NonNull
    @Override
    public DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View detailsView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_details_recycler, parent, false);
        return new DetailsViewHolder(detailsView);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsViewHolder holder, int position) {
        DetailsItem item = mDetailsItems.get(position);
        Thumbnail itemThumb = item.getThumbnail();

        if (itemThumb == null)
            holder.image.setImageResource(R.drawable.ic_broken_image);

        else
            Picasso.get()
                    .load(itemThumb.getPath().concat("/portrait_medium.").concat(itemThumb.getExtension()))
                    .into(holder.image);

        holder.title.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return mDetailsItems.size();
    }

    class DetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.item_details_img)
        ImageView image;
        @BindView(R.id.item_details_text)
        TextView title;

        DetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent pagerIntent = new Intent(mActivity, DetailsPagerActivity.class);
            pagerIntent.putExtra(AppConstants.DETAILS_LIST_TYPE, mListType);
            pagerIntent.putExtra(AppConstants.DETAILS_ITEM_POSITION, getAdapterPosition());
            mActivity.startActivity(pagerIntent);
            mActivity.overridePendingTransition(R.anim.fade_in, R.anim.normal_static);
        }
    }
}
