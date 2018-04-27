package com.example.camilo.pokedex.models;

import com.google.gson.annotations.SerializedName;

public class PokemonResponse {
    @SerializedName("url")
    private String urlPokemon;
    @SerializedName("name")
    private String name;
}
