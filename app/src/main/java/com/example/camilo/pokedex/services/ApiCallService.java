package com.example.camilo.pokedex.services;

import com.example.camilo.pokedex.models.PokemonDto;
import com.example.camilo.pokedex.models.PokemonResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiCallService {

    @GET("pokemon")
    Call<PokemonResponse> getPokemonList(@Query("limit") int limit, @Query("offset") int offset);

    @GET("pokemon/{id}")
    Call<PokemonDto> getPokemon(@Path("id") int id);
}
