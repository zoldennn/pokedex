package com.example.camilo.pokedex.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.camilo.pokedex.R;
import com.example.camilo.pokedex.deserializers.PokemonDetailsDeserializer;
import com.example.camilo.pokedex.models.Pokemon;
import com.example.camilo.pokedex.models.PokemonDetails;
import com.example.camilo.pokedex.services.ApiCallService;
import com.example.camilo.pokedex.utils.Utils;
import com.google.gson.GsonBuilder;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonDetailsFragment extends Fragment {

    public static PokemonDetailsFragment newInstance(int id, String name, Bitmap bitmap) {
        PokemonDetailsFragment fragment = new PokemonDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(Utils.EXTRA_POKEMON_ID, id);
        args.putString(Utils.EXTRA_POKEMON_NAME, name);
        args.putParcelable(Utils.EXTRA_POKEMON_IMAGE, bitmap);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.pokemon_details_fragment, container, false);
        ButterKnife.bind(this, view);

        callApi();

        return view;
    }

    private void callApi() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(PokemonDetails.class, new PokemonDetailsDeserializer());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create(builder.create()))
                .build();

        ApiCallService apiCallService = retrofit.create(ApiCallService.class);
        final Call<PokemonDetails> pokemonCall = apiCallService.getPokemonDetails(6);

        pokemonCall.enqueue(new Callback<PokemonDetails>() {
            @Override
            public void onResponse(Call<PokemonDetails> call, Response<PokemonDetails> response) {
                if(response.isSuccessful()){
                    PokemonDetails pokemonDetails = response.body();
                    Toast.makeText(getContext(), ""+pokemonDetails.getDescription(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<PokemonDetails> call, Throwable t) {
                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
