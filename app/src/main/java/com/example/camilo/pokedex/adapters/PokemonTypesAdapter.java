package com.example.camilo.pokedex.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.camilo.pokedex.R;
import com.example.camilo.pokedex.models.PokemonTypeDto;
import com.example.camilo.pokedex.utils.PokemonTypesDrawableRepository;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PokemonTypesAdapter extends RecyclerView.Adapter<PokemonTypesAdapter.AdapterViewHolder> {

    private List<PokemonTypeDto> mPokemonTypes;

    public PokemonTypesAdapter(List<PokemonTypeDto> typesList) {
        mPokemonTypes = typesList;
    }

    @Override
    public AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_type_list_view, parent, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterViewHolder holder, int position) {
        String pokemonType = mPokemonTypes.get(position).getTypeBasicData().getName();
        holder.getIcon().setImageResource(PokemonTypesDrawableRepository.getTypeResourceId(pokemonType));
    }

    @Override
    public int getItemCount() {
        return mPokemonTypes.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_pokemon_type)
        ImageView vPokemonTypeIcon;

        private AdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public ImageView getIcon() {
            return vPokemonTypeIcon;
        }
    }
}

