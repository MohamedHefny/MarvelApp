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

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int CHARACTER_TYPE = 1;
    private static final int LOADING_TYPE = 2;

    private List<Character> mCharacterList;
    private ClickCallback mCharacterCallback;


    public HomeAdapter(List<Character> characterList, ClickCallback characterCallback) {
        this.mCharacterList = characterList;
        mCharacterCallback = characterCallback;
    }

    /**
     * Check for view type to support loading view on fetching new characters page
     */
    @Override
    public int getItemViewType(int position) {
        if (position == mCharacterList.size() - 1)
            return LOADING_TYPE;
        else
            return CHARACTER_TYPE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View homeView;

        switch (viewType) {
            case CHARACTER_TYPE:
                homeView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_char, parent, false);
                return new CharacterViewHolder(homeView);
            case LOADING_TYPE:
                homeView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_home_loading, parent, false);
                return new LoadingViewHolder(homeView);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        //If the list is loading then return and wait until calling of addNewCharacters
        if (holder.getItemViewType() == LOADING_TYPE)
            return;

        CharacterViewHolder characterHolder = (CharacterViewHolder) holder;
        Character character = mCharacterList.get(position);

        Picasso.get()
                .load(character.getThumbnail().getPath().concat("/landscape_xlarge.").concat(character.getThumbnail().getExtension()))
                .placeholder(R.drawable.marvel_logo)
                .into(characterHolder.charPic);

        characterHolder.charName.setText(character.getName());
    }

    @Override
    public int getItemCount() {
        return mCharacterList.size();
    }

    /**
     * Update characters list to support pagination
     *
     * @param newCharacterList the new list of characters
     */
    public void addNewCharacters(List<Character> newCharacterList) {
        for (int i = mCharacterList.size(); i < newCharacterList.size(); i++)
            mCharacterList.add(newCharacterList.get(i));

        //Notifying the recyclerView will add new characters in the correct positions and will remove the loading view
        notifyDataSetChanged();
    }

    class CharacterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.item_char_img)
        ImageView charPic;
        @BindView(R.id.item_char_name)
        TextView charName;

        CharacterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mCharacterCallback.onCharacterSelected(mCharacterList.get(getAdapterPosition()));
        }
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {

        LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
