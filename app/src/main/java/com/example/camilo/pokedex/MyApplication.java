package com.example.camilo.pokedex;

import android.app.Application;

import com.example.camilo.pokedex.models.PokeResponse;
import com.example.camilo.pokedex.models.PokemonDto;
import com.example.camilo.pokedex.models.PokemonResponse;

import java.util.List;

public class MyApplication extends Application {

    private static PokemonDto mPokemon;
    private static List<PokeResponse> mResponseList;

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

    public static List<PokeResponse> getmResponseList() {
        return mResponseList;
    }

    public static void setmResponseList(List<PokeResponse> responseList) {
        mResponseList = responseList;
    }
}
