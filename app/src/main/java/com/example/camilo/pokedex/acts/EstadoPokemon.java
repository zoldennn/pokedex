package com.example.camilo.pokedex.acts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.camilo.pokedex.R;

public class EstadoPokemon extends AppCompatActivity  {

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
        bitmap = intent.getParcelableExtra("img");
        try {
            id = intent.getExtras().getInt("pos");
        }catch (Exception e){
            Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
        }
        nameP = intent.getExtras().getString("name");

        BottomNavigationView bottomNavigationView;

        getSupportFragmentManager().beginTransaction().add(R.id.layoutEstado, new firstFragment()).commit();

        //LISTENER BOTONES DEL NAVIGATION
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.action_add:
                        getSupportFragmentManager().beginTransaction().replace(R.id.layoutEstado, new firstFragment()).commit();
                        return true;
                    case R.id.action_edit:
                        getSupportFragmentManager().beginTransaction().replace(R.id.layoutEstado, new secondFragment()).commit();
                        return true;
                    case R.id.action_del:
                        getSupportFragmentManager().beginTransaction().replace(R.id.layoutEstado, new thirdFragment()).commit();
                        return true;
                }
                return true;
            }
        });
    }
}
