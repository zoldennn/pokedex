package com.example.camilo.pokedex.acts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.camilo.pokedex.R;
import com.example.camilo.pokedex.fragments.PokemonDetailsFragment;
import com.example.camilo.pokedex.fragments.PokemonEvolutionFragment;
import com.example.camilo.pokedex.fragments.PokemonStatsFragment;
import com.example.camilo.pokedex.utils.Utils;

public class PokemonViewActivity extends AppCompatActivity  {

    public static int id;
    public static String nameP;
    public LinearLayout layout;
    public static Bitmap bitmap;
    public static Typeface t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_pokemon);

        t2 = Typeface.createFromAsset(getAssets(), "fonts/roboli.ttf");

        //OBTENER ID, IMAGEN Y NOMBRE DEL POKEMON CLICKEADO PARA AHORRAR DATOS Y NO VOLVER A LLAMAR API
        Intent intent = getIntent();
        bitmap = intent.getParcelableExtra(Utils.EXTRA_POKEMON_IMAGE);
        try {
            id = intent.getExtras().getInt(Utils.EXTRA_POKEMON_ID);
        }catch (Exception e){
            Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
        }
        nameP = intent.getExtras().getString(Utils.EXTRA_POKEMON_NAME);

        BottomNavigationView bottomNavigationView;

        //getSupportFragmentManager().beginTransaction().add(R.id.layoutEstado, new PokemonStatsFragment()).commit();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        PokemonStatsFragment fragment = PokemonStatsFragment.newInstance(id, nameP, bitmap);
        fragmentTransaction.add(R.id.layoutEstado, fragment);
        fragmentTransaction.commit();

        //LISTENER BOTONES DEL NAVIGATION
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.action_add:
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        PokemonStatsFragment fragment = PokemonStatsFragment.newInstance(id, nameP, bitmap);
                        fragmentTransaction.add(R.id.layoutEstado, fragment);
                        fragmentTransaction.commit();

                        //getSupportFragmentManager().beginTransaction().replace(R.id.layoutEstado, new PokemonStatsFragment()).commit();
                        return true;
                    case R.id.action_edit:
                        getSupportFragmentManager().beginTransaction().replace(R.id.layoutEstado, new PokemonDetailsFragment()).commit();
                        return true;
                    case R.id.action_del:
                        getSupportFragmentManager().beginTransaction().replace(R.id.layoutEstado, new PokemonEvolutionFragment()).commit();
                        return true;
                }
                return true;
            }
        });
    }
}
