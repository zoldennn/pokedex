package com.example.camilo.pokedex.acts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.camilo.pokedex.PokemonListFetcher;
import com.example.camilo.pokedex.PokemonService;
import com.example.camilo.pokedex.R;
import com.example.camilo.pokedex.models.Pokemon;
import com.example.camilo.pokedex.utils.LoadingDialog;
import com.example.camilo.pokedex.utils.PokemonListAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;

public class PokemonListActivity extends AppCompatActivity implements PokemonService{

    private Retrofit retrofit;
    public ArrayList<Pokemon> listaPokemon;
    private RecyclerView recyclerView;
    private PokemonListAdapter listaPokemonAdapter;
    private int offset;
    public static boolean aptoParaCargar;
    private GridLayoutManager layoutManager;
    public TextView cargando, title;
    public LoadingDialog dialog;
    private PokemonListFetcher listFetcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.pokemon_list_recyclerview);

        listFetcher = new PokemonListFetcher(this);
        listaPokemonAdapter = new PokemonListAdapter(this, R.layout.list_view, new PokemonListAdapter.OnItemClickListener() {

            //AL CLICKEAR EN UN ELEMENTO DEL RECYCLERVIEW SE GUARDAN DATOS BASICOS PARA AHORRAR DATOS
            @Override
            public void onItemClick(String name, int position, ImageView img) {
                int pos = position + 1;  //SUMAR UNO PORQUE EL ADAPTER ARRANCA EN 0
                Intent intent = new Intent(PokemonListActivity.this, EstadoPokemon.class);
                intent.putExtra("name", name);
                intent.putExtra("pos", pos);
                if (img.getDrawable() != null)  //CONTROLAR QUE AL CLICKEAR SE HAYA CARGADO LA IMAGEN
                {
                    Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
                    intent.putExtra("img", bitmap);
                    startActivity(intent);
                } else {
                    //intent.putExtra("img","null");
                }
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (aptoParaCargar) {
                        if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                            aptoParaCargar = false;
                            offset += 20;
                            listFetcher.callPokemonApi(PokemonListActivity.this, offset, listaPokemonAdapter);
                        }
                    }
                }
            }
        });
        //APLICO EL ADAPTER Y CONTROLO CUÁNDO SE LLEGÓ AL FINAL DEL RECYCLERVIEW
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listaPokemonAdapter);

        aptoParaCargar = true;
        offset = 0;
        listFetcher.callPokemonApi(this, offset, listaPokemonAdapter);
    }

    @Override
    public void renderPokemons(List<Pokemon> pokemonList) {
        setupRecyclerView(pokemonList);
    }

    private void setupRecyclerView(List<Pokemon> list) {
        //listaPokemonAdapter.adicionarPokemon(list);


        listaPokemonAdapter.adicionarPokemon(list);

    }
}
