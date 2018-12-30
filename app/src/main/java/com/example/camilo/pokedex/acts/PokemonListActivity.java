package com.example.camilo.pokedex.acts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.camilo.pokedex.MyApplication;
import com.example.camilo.pokedex.R;
import com.example.camilo.pokedex.adapters.PokemonListAdapter;
import com.example.camilo.pokedex.models.PokeResponse;
import com.example.camilo.pokedex.models.PokemonDto;
import com.example.camilo.pokedex.services.PokemonService;
import com.example.camilo.pokedex.utils.PokemonListFetcher;
import com.example.camilo.pokedex.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PokemonListActivity extends AppCompatActivity implements PokemonService {

    @BindView(R.id.pokemon_list_recyclerview)
    RecyclerView mPokemonListRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private PokemonListAdapter mPokemonListAdapter;
    private int mOffset;
    public static boolean mMustCharge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setIcon(R.drawable.img_pokedex_toolbar_logo);

        setupRecyclerView();

        //Bundle pokemonExtras = getIntent().getBundleExtra(Utils.EXTRA_POKEDEX_BUNDLE);
        //List<PokeResponse> pokemonList = (List<PokeResponse>) pokemonExtras.getParcelable(Utils.EXTRA_POKEDEX_LIST);

        List<PokeResponse> pokemonList = MyApplication.getmResponseList();

        if (pokemonList != null) {
            mPokemonListAdapter.addNewPokemonList(pokemonList);
        } else {
            Toast.makeText(this, "Roompió", Toast.LENGTH_SHORT).show();
        }
        mMustCharge = true; // This var avoids to make api calls continuously
    }

    // Setup the RecyclerView, GridLayoutManager and Adapter
    private void setupRecyclerView() {
        mPokemonListRecyclerView.setHasFixedSize(true); // For better performance
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 3); // Grid layout 3 items
        mPokemonListRecyclerView.setLayoutManager(mLayoutManager); // Grid horizontal items per view

        mOffset = mOffset + 20;
        PokemonListFetcher mPokemonListFetcher = new PokemonListFetcher(this, mOffset, this); // Instantiate this class to make the calls
        mPokemonListFetcher.setScrollListener(mPokemonListRecyclerView, mLayoutManager);

        mPokemonListAdapter = new PokemonListAdapter(this, this); // Adapter that will populate the data
        mPokemonListRecyclerView.setAdapter(mPokemonListAdapter);
    }

    @Override
    public void renderPokemonList(List<PokeResponse> pokemonList) {
        mPokemonListAdapter.addNewPokemonList(pokemonList);
    }

    @Override
    public void onPokemonItemClick(PokeResponse pokemon, int pos, ImageView img) {
        Intent intent = new Intent(PokemonListActivity.this, PokemonViewActivity.class)
                .putExtra(Utils.EXTRA_POKEMON_NAME, pokemon.getName())
                .putExtra(Utils.EXTRA_POKEMON_ID, pos + 1); // +1 cause RecyclerView starts on 0

        if (img.getDrawable() != null)  // Check if the image loaded successfully to be passed on intent
        {
            Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
            intent.putExtra(Utils.EXTRA_POKEMON_IMAGE, bitmap);
            startActivity(intent);
        } else {
            Toast.makeText(this, getString(R.string.pokemon_list_image_msg), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void renderPokemon(PokemonDto pokemon) {
        // Do nothing here
    }
}
