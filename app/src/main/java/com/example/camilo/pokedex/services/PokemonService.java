package com.example.camilo.pokedex.services;

import android.widget.ImageView;

import com.example.camilo.pokedex.models.PokeResponse;
import com.example.camilo.pokedex.models.PokemonDto;

import java.util.List;

public interface PokemonService {
    void renderPokemonList(List<PokeResponse> pokemonList);

    void onPokemonItemClick(PokeResponse pokemon, int pos, ImageView img);

    void renderPokemon(PokemonDto pokemon);
}
