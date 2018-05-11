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
    FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_pokemon);

        mCustomFont = Typeface.createFromAsset(getAssets(), "fonts/roboli.ttf");

        //OBTENER ID, IMAGEN Y NOMBRE DEL POKEMON CLICKEADO PARA AHORRAR DATOS Y NO VOLVER A LLAMAR API
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

        FragmentManager fragmentManager = getSupportFragmentManager();
        mFragmentTransaction = fragmentManager.beginTransaction();

        PokemonStatsFragment fragment = PokemonStatsFragment.newInstance(mClickedPokemonID, mClickedPokemonName, mClickedPokemonImage);
        mFragmentTransaction.add(R.id.layoutEstado, fragment);
        mFragmentTransaction.commit();

        //LISTENER BOTONES DEL NAVIGATION
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_add:
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        mFragmentTransaction = fragmentManager.beginTransaction();

                        PokemonStatsFragment fragment = PokemonStatsFragment.newInstance(mClickedPokemonID, mClickedPokemonName, mClickedPokemonImage);
                        mFragmentTransaction.add(R.id.layoutEstado, fragment);
                        mFragmentTransaction.commit();
                        break;
                    case R.id.action_edit:
                        getSupportFragmentManager().beginTransaction().replace(R.id.layoutEstado, new PokemonDetailsFragment()).commit();
                        break;
                    case R.id.action_del:
                        getSupportFragmentManager().beginTransaction().replace(R.id.layoutEstado, new PokemonEvolutionFragment()).commit();
                        break;
                    default:
                        getSupportFragmentManager().beginTransaction().replace(R.id.layoutEstado, new PokemonStatsFragment()).commit();
                        break;
                }
                return true;
            }
        });
    }
}
