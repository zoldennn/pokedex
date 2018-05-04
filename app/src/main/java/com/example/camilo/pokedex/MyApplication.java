package com.example.camilo.pokedex;

import android.app.Application;

import com.example.camilo.pokedex.models.Pokemon;

public class MyApplication extends Application {

    int mPokemonID;
    private static Pokemon mPokemon;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static Pokemon getLastPokemon(){
        return mPokemon;
    }

    public static void setLastPokemon(Pokemon pokemon){
        mPokemon = pokemon;
    }
}
