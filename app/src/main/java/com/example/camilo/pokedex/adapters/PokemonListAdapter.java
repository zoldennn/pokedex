package com.example.camilo.pokedex.adapters;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.camilo.pokedex.services.PokemonService;
import com.example.camilo.pokedex.R;
import com.example.camilo.pokedex.models.Pokemon;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PokemonListAdapter extends RecyclerView.Adapter<PokemonListAdapter.AdapterViewHolder> {

    private Context mContext;
    private List<Pokemon> mDataSet;
    private PokemonService mPokemonService;

    public PokemonListAdapter(Context context, PokemonService listener) {
        this.mContext = context;
        this.mPokemonService = listener;
        mDataSet = new ArrayList<>();
    }

    @Override
    public AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterViewHolder holder, int position) {
        Pokemon pokemon = mDataSet.get(position);
        Picasso.get().load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokemon.getNumber() + ".png").into(holder.vPokemonPhoto);

        holder.bind(pokemon.getName());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    // Add the new 20 incoming pokemon
    public void addNewPokemonList(List<Pokemon> pokemonList) {
        mDataSet.addAll(pokemonList);
        notifyDataSetChanged();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.pokemon_list_image)
        ImageView vPokemonPhoto;
        @BindView(R.id.viewPid)
        TextView vPokemonID;
        @BindView(R.id.viewName)
        TextView vPokemonName;

        private AdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPokemonService.onPokemonItemClick(mDataSet.get(getAdapterPosition()), getAdapterPosition(), vPokemonPhoto);
                }
            });
        }

        private void bind(final String name) {
            int cont = getAdapterPosition() + 1;

            if (cont < 10) {
                this.vPokemonID.setText(String.format("%s%s", mContext.getString(R.string.pokemon_id_plus_2), cont));
            } else {
                if (cont < 100) {
                    this.vPokemonID.setText(String.format("%s%s", mContext.getString(R.string.pokemon_id_plus_1), cont));
                } else {
                    this.vPokemonID.setText(String.format("%s%s", mContext.getString(R.string.pokemon_id_plus_0), cont));
                }
            }
            this.vPokemonName.setText(name);
            final AssetManager assets = mContext.getAssets();
            final Typeface tvFont = Typeface.createFromAsset(assets, "fonts/roboli.ttf");

            vPokemonID.setTypeface(tvFont);
            vPokemonName.setTypeface(tvFont);
        }
    }
}
