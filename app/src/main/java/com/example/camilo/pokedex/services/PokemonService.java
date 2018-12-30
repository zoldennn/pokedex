package com.example.camilo.pokedex.services;

import android.widget.ImageView;

import com.example.camilo.pokedex.models.Pokemon;
import com.example.camilo.pokedex.models.PokemonDto;

import java.util.List;

public interface PokemonService {
    void renderPokemonList(List<Pokemon> pokemonList);

    void onPokemonItemClick(Pokemon pokemon, int pos, ImageView img);

    void renderPokemon(PokemonDto pokemon);
}
