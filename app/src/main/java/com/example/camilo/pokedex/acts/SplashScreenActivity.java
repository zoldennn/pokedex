package com.example.camilo.pokedex.acts;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.camilo.pokedex.R;
import com.example.camilo.pokedex.models.Pokemon;
import com.example.camilo.pokedex.services.PokemonService;
import com.example.camilo.pokedex.utils.PokemonListFetcher;
import com.example.camilo.pokedex.utils.Utils;

import java.io.Serializable;
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
    public void renderPokemonList(List<Pokemon> pokemonList) {
        Intent intent = new Intent(this, PokemonListActivity.class);
        Bundle extras = new Bundle();
        extras.putSerializable(Utils.EXTRA_POKEDEX_LIST, (Serializable) pokemonList);
        intent.putExtra(Utils.EXTRA_POKEDEX_BUNDLE, extras);
        startActivity(intent);
    }

    @Override
    public void onPokemonItemClick(Pokemon pokemon, int pos, ImageView img) {
        // Do nothing here
    }

    @Override
    public void renderPokemon(Pokemon pokemon) {
        // Do nothing here
    }
}
