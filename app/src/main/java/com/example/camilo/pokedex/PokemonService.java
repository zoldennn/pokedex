package com.example.camilo.pokedex;

import com.example.camilo.pokedex.models.Pokemon;

import java.util.List;

public interface PokemonService {
    void renderPokemons(List<Pokemon> pokemonList);
}
