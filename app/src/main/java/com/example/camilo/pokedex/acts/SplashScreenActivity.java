package com.example.camilo.pokedex.acts;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.camilo.pokedex.R;
import com.example.camilo.pokedex.models.PokeResponse;
import com.example.camilo.pokedex.models.PokemonDto;
import com.example.camilo.pokedex.services.PokemonService;
import com.example.camilo.pokedex.utils.PokemonListFetcher;
import com.example.camilo.pokedex.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SplashScreenActivity extends AppCompatActivity implements PokemonService {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // To fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);

        PokemonListFetcher pokemonListFetcher = new PokemonListFetcher(this, 0, this); // Instantiate this class to make the calls
        pokemonListFetcher.callPokemonApi(); // Make API call
    }

    @Override
    public void renderPokemonList(List<PokeResponse> pokemonList) {
        Bundle extras = new Bundle();
        extras.putParcelableArrayList(Utils.EXTRA_POKEDEX_LIST, (ArrayList<? extends Parcelable>) pokemonList);

        Intent intent = new Intent(this, PokemonListActivity.class);
        intent.putExtras(extras);

        startActivity(intent);
    }

    @Override
    public void onPokemonItemClick(PokeResponse pokemon, int pos, ImageView img) {
        // Do nothing here
    }

    @Override
    public void renderPokemon(PokemonDto pokemon) {
        // Do nothing here
    }
}
