package com.example.camilo.pokedex.services;

import com.example.camilo.pokedex.models.Pokemon;
import com.example.camilo.pokedex.models.PokemonResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {

    @GET("pokemon")
    Call<PokemonResponse> obtenerListaPokemon(@Query("limit") int limit, @Query("offset") int offset);

    @GET("pokemon/{id}")
    Call<Pokemon> getPokemon(@Path("id") int id);

}
