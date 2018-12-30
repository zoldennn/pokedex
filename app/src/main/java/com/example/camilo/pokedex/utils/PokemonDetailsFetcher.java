package com.example.camilo.pokedex.utils;

import android.content.Context;

import com.example.camilo.pokedex.MyApplication;
import com.example.camilo.pokedex.R;
import com.example.camilo.pokedex.deserializers.Deserializer;
import com.example.camilo.pokedex.models.Pokemon;
import com.example.camilo.pokedex.models.PokemonDto;
import com.example.camilo.pokedex.services.ApiCallService;
import com.example.camilo.pokedex.services.PokemonService;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonDetailsFetcher {

    public void callPokemon(final int pokemonID, final PokemonService service) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Pokemon.class, new Deserializer());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create(builder.create()))
                .build();

        ApiCallService apiCallService = retrofit.create(ApiCallService.class);

        // Api call
        Call<PokemonDto> pokemonCall = apiCallService.getPokemon(pokemonID);
        pokemonCall.enqueue(new Callback<PokemonDto>() {
            @Override
            public void onResponse(Call<PokemonDto> call, Response<PokemonDto> response) {
                if (response.isSuccessful()) {
                    PokemonDto pokemon = response.body();
                    MyApplication.setLastPokemon(pokemon);
                    service.renderPokemon(pokemon);
                }
            }

            @Override
            public void onFailure(Call<PokemonDto> call, Throwable t) {
                callPokemon(pokemonID, service);
            }
        });
    }

    public String transformPokemonID(Context context, int clickedPokemonID) {
        if (clickedPokemonID < 10) {
            return String.format("%s%s", context.getString(R.string.pokemon_id_plus_2), clickedPokemonID);
        } else {
            if (clickedPokemonID < 100) {
                return String.format("%s%s", context.getString(R.string.pokemon_id_plus_1), clickedPokemonID);
            } else {
                return String.format("%s%s", context.getString(R.string.pokemon_id_plus_0), clickedPokemonID);
            }
        }
    }

    public int checkPokemonTypes(String typeToCheck) {
        int drawableID;
        switch (typeToCheck) {
            case "bug":
                drawableID = R.drawable.bug;
                break;
            case "dark":
                drawableID = R.drawable.dark;
                break;
            case "dragon":
                drawableID = R.drawable.dragon;
                break;
            case "electric":
                drawableID = R.drawable.electric;
                break;
            case "fairy":
                drawableID = R.drawable.fairy;
                break;
            case "fighting":
                drawableID = R.drawable.fighting;
                break;
            case "fire":
                drawableID = R.drawable.fire;
                break;
            case "ghost":
                drawableID = R.drawable.ghost;
                break;
            case "grass":
                drawableID = R.drawable.grass;
                break;
            case "ground":
                drawableID = R.drawable.ground;
                break;
            case "ice":
                drawableID = R.drawable.ice;
                break;
            case "normal":
                drawableID = R.drawable.normal;
                break;
            case "poison":
                drawableID = R.drawable.poison;
                break;
            case "psychic":
                drawableID = R.drawable.psychic;
                break;
            case "steel":
                drawableID = R.drawable.steel;
                break;
            case "water":
                drawableID = R.drawable.water;
                break;
            case "rock":
                drawableID = R.drawable.rock;
                break;
            case "flying":
                drawableID = R.drawable.flying;
                break;
            default:
                drawableID = 0;
                break;
        }
        return drawableID;
    }
}
