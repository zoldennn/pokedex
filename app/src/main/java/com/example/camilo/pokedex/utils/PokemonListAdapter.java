package com.example.camilo.pokedex.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.camilo.pokedex.PokemonService;
import com.example.camilo.pokedex.R;
import com.example.camilo.pokedex.models.Pokemon;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class PokemonListAdapter extends RecyclerView.Adapter<PokemonListAdapter.ViewHolder> {

    private Context mContext;
    private List<Pokemon> mDataSet;
    private PokemonService mPokemonService;

    public PokemonListAdapter(Context context, PokemonService listener) {
        this.mContext = context;
        this.mPokemonService = listener;
        mDataSet = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pokemon pokemon = mDataSet.get(position);

        Picasso.with(mContext)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokemon.getNumber() + ".png")
                .into(holder.img);

        holder.bind(pokemon.getName());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    // Add the new 20 incoming pokemons
    public void addNewPokemonList(List<Pokemon> listaPokemon) {
        mDataSet.addAll(listaPokemon);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView idPoke, namePoke;

        public ViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.viewImg);
            idPoke = itemView.findViewById(R.id.viewPid);
            namePoke = itemView.findViewById(R.id.viewName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPokemonService.onPokemonItemClick(mDataSet.get(getAdapterPosition()), getAdapterPosition(), img);
                }
            });

        }

        public void bind(final String name) {
            int cont = getAdapterPosition() + 1;
            if (cont < 10) {
                this.idPoke.setText(String.format("%s%s", mContext.getString(R.string.pokemon_id_plus_2), cont));
            } else {
                if (cont < 100) {
                    this.idPoke.setText(String.format("%s%s", mContext.getString(R.string.pokemon_id_plus_1), cont));
                } else {
                    this.idPoke.setText(String.format("%s%s", mContext.getString(R.string.pokemon_id_plus_0), cont));
                }
            }
            this.namePoke.setText(name);

            final AssetManager assets = mContext.getAssets();
            final Typeface tvFont = Typeface.createFromAsset(assets, "fonts/roboli.ttf");
            idPoke.setTypeface(tvFont);
            namePoke.setTypeface(tvFont);
        }

    }
}
