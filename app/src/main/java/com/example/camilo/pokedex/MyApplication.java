package com.example.camilo.pokedex;

import android.app.Application;

import com.example.camilo.pokedex.models.PokemonDto;

public class MyApplication extends Application {

    private static PokemonDto mPokemon;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static PokemonDto getLastPokemon(){
        return mPokemon;
    }

    public static void setLastPokemon(PokemonDto pokemon){
        mPokemon = pokemon;
    }
}
