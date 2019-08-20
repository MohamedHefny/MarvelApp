package com.mohamedhefny.marveltask.ui.details;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mohamedhefny.marveltask.R;
import com.mohamedhefny.marveltask.data.entities.CharLinks;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LinksAdapter extends RecyclerView.Adapter<LinksAdapter.LinkViewHolder> {

    private Activity mActivity;
    private List<CharLinks> mCharLinks;

    LinksAdapter(Activity activity, List<CharLinks> charLinks) {
        mActivity = activity;
        mCharLinks = charLinks;
    }

    @NonNull
    @Override
    public LinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View linkView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_links, parent, false);
        return new LinkViewHolder(linkView);
    }

    @Override
    public void onBindViewHolder(@NonNull LinkViewHolder holder, int position) {
        String linkType = mCharLinks.get(position).getType();
        holder.linkType.setText(linkType.substring(0, 1).toUpperCase().concat(linkType.substring(1)));
    }

    @Override
    public int getItemCount() {
        return mCharLinks.size();
    }

    class LinkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.item_link_type_tv)
        TextView linkType;

        LinkViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent linkIntent = new Intent(Intent.ACTION_VIEW);
            linkIntent.setData(Uri.parse(mCharLinks.get(getAdapterPosition()).getUrl()));
            mActivity.startActivity(linkIntent);
        }
    }
}
