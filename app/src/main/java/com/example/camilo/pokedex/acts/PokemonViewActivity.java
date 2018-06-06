package com.example.camilo.pokedex.acts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.camilo.pokedex.R;
import com.example.camilo.pokedex.fragments.PokemonDetailsFragment;
import com.example.camilo.pokedex.fragments.PokemonEvolutionFragment;
import com.example.camilo.pokedex.fragments.PokemonStatsFragment;
import com.example.camilo.pokedex.utils.Utils;

public class PokemonViewActivity extends AppCompatActivity {

    private int mClickedPokemonID;
    private String mClickedPokemonName;
    private Bitmap mClickedPokemonImage;
    public static Typeface mCustomFont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_pokemon);

        mCustomFont = Typeface.createFromAsset(getAssets(), "fonts/roboli.ttf");

        // Get ID, name and image from bundle to avoid excessive data usage
        Intent intent = getIntent();
        mClickedPokemonImage = intent.getParcelableExtra(Utils.EXTRA_POKEMON_IMAGE);
        try {
            mClickedPokemonID = intent.getExtras().getInt(Utils.EXTRA_POKEMON_ID);
        } catch (Exception e) {
            Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
        }
        mClickedPokemonName = intent.getExtras().getString(Utils.EXTRA_POKEMON_NAME);

        initFragmentTransactions();
    }

    private void initFragmentTransactions() {
        BottomNavigationView bottomNavigationView;

        // TODO: CLEAN THIS UGLY TRANSACTIONS

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        PokemonStatsFragment fragment = PokemonStatsFragment.newInstance(mClickedPokemonID, mClickedPokemonName, mClickedPokemonImage);
        fragmentTransaction.add(R.id.container_layout, fragment);
        fragmentTransaction.commit();

        // BottomNavigationView item listeners
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_add:
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        PokemonStatsFragment fragment = PokemonStatsFragment.newInstance(mClickedPokemonID, mClickedPokemonName, mClickedPokemonImage);
                        fragmentTransaction.add(R.id.container_layout, fragment);
                        fragmentTransaction.commit();
                        break;
                    case R.id.action_edit:
                        FragmentManager fragmentManager2 = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();

                        PokemonDetailsFragment pokemonDetailsFragment = PokemonDetailsFragment.newInstance(mClickedPokemonID, mClickedPokemonName, mClickedPokemonImage);
                        fragmentTransaction2.add(R.id.container_layout, pokemonDetailsFragment);
                        fragmentTransaction2.commit();
                        break;
                    case R.id.action_del:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_layout, new PokemonEvolutionFragment()).commit();
                        break;
                    default:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_layout, new PokemonStatsFragment()).commit();
                        break;
                }
                return true;
            }
        });
    }
}
