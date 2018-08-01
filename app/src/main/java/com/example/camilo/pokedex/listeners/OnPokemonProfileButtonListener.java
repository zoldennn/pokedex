package com.example.camilo.pokedex.listeners;

import android.widget.ImageView;

import com.example.camilo.pokedex.models.Pokemon;

public interface OnPokemonProfileButtonListener {
    void OnPokemonProfileClickListener(final Pokemon pokemon, final int pos, final ImageView img);
}
