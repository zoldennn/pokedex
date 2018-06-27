package com.example.camilo.pokedex.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.camilo.pokedex.MyApplication;
import com.example.camilo.pokedex.R;
import com.example.camilo.pokedex.deserializers.PokemonDetailsDeserializer;
import com.example.camilo.pokedex.models.PokemonDetails;
import com.example.camilo.pokedex.services.ApiCallService;
import com.example.camilo.pokedex.utils.Utils;
import com.google.gson.GsonBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonDetailsFragment extends Fragment {

    @BindView(R.id.tv_pokemon_details_description)
    TextView vPokemonDescription;
    @BindView(R.id.tv_pokemon_details_habitat)
    TextView vPokemonHabitat;
    @BindView(R.id.tv_pokemon_details_capture_rate)
    TextView vPokemonCaptureRate;
    @BindView(R.id.tv_pokemon_details_id)
    TextView vPokemonId;
    @BindView(R.id.img_pokemon_details_photo)
    ImageView vPokemonPhoto;
    @BindView(R.id.tv_pokemon_details_name2)
    TextView vPokemonName;

    private static Bundle pokemonDetailsBundle;

    public static PokemonDetailsFragment newInstance(int id, String name, Bitmap bitmap) {
        PokemonDetailsFragment fragment = new PokemonDetailsFragment();
        pokemonDetailsBundle = new Bundle();
        pokemonDetailsBundle.putInt(Utils.EXTRA_POKEMON_ID, id);
        pokemonDetailsBundle.putString(Utils.EXTRA_POKEMON_NAME, name);
        pokemonDetailsBundle.putParcelable(Utils.EXTRA_POKEMON_IMAGE, bitmap);
        fragment.setArguments(pokemonDetailsBundle);
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
        final Call<PokemonDetails> pokemonCall = apiCallService.getPokemonDetails(MyApplication.getLastPokemon().getNumber());

        pokemonCall.enqueue(new Callback<PokemonDetails>() {
            @Override
            public void onResponse(Call<PokemonDetails> call, Response<PokemonDetails> response) {
                if(response.isSuccessful()){
                    PokemonDetails pokemonDetails = response.body();
                    vPokemonDescription.setText(removeUselessDescriptionSpaces(pokemonDetails.getDescription()));

                    vPokemonCaptureRate.setText(pokemonDetails.getCaptureRate());
                    vPokemonHabitat.setText(pokemonDetails.getHabitat());
                    vPokemonName.setText(pokemonDetailsBundle.getString(Utils.EXTRA_POKEMON_NAME));
                    vPokemonId.setText(""+pokemonDetailsBundle.getInt(Utils.EXTRA_POKEMON_ID));
                    vPokemonPhoto.setImageBitmap((Bitmap) pokemonDetailsBundle.getParcelable(Utils.EXTRA_POKEMON_IMAGE));
                }
            }

            @Override
            public void onFailure(Call<PokemonDetails> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // The poke descriptions comes with weird parraph spaces, this method will return a clean description
    private String removeUselessDescriptionSpaces(String description){
        return description.replaceAll("\n","");
    }
}
