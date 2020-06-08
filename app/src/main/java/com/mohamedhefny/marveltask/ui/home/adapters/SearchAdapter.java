package com.mohamedhefny.marveltask.ui.home.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mohamedhefny.marveltask.R;
import com.mohamedhefny.marveltask.data.entities.Character;
import com.mohamedhefny.marveltask.ui.home.ClickCallback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {


    private List<Character> mCharacterList;
    private ClickCallback mCharacterCallback;

    public SearchAdapter(List<Character> characterList, ClickCallback characterCallback) {
        mCharacterList = characterList;
        mCharacterCallback = characterCallback;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View searchView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_character_search, parent, false);
        return new SearchViewHolder(searchView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Character character = mCharacterList.get(position);

        Picasso.get()
                .load(character.getThumbnail().getPath().concat("/standard_medium.").concat(character.getThumbnail().getExtension()))
                .placeholder(R.drawable.logo)
                .into(holder.characterImg);

        holder.characterTitle.setText(character.getName());
    }

    @Override
    public int getItemCount() {
        return mCharacterList.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.item_character_search_img)
        ImageView characterImg;
        @BindView(R.id.item_character_search_title)
        TextView characterTitle;

        SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mCharacterCallback.onCharacterSelected(mCharacterList.get(getAdapterPosition()));
        }
    }
}
