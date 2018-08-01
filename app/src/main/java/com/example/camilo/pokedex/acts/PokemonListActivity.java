package com.example.camilo.pokedex.acts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.camilo.pokedex.R;
import com.example.camilo.pokedex.adapters.PokemonListAdapter;
import com.example.camilo.pokedex.dialogs.LoadingDialog;
import com.example.camilo.pokedex.dialogs.PokemonPreviewDialog;
import com.example.camilo.pokedex.models.Pokemon;
import com.example.camilo.pokedex.services.PokemonService;
import com.example.camilo.pokedex.utils.PokemonListFetcher;
import com.example.camilo.pokedex.utils.Utils;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.camilo.pokedex.acts.PokemonViewActivity.mCustomFont;

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
        setContentView(R.layout.activity_pokemon_list);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setIcon(R.drawable.img_pokedex_toolbar_logo);

        setupRecyclerView();

        Bundle pokemonExtras = getIntent().getBundleExtra(Utils.EXTRA_POKEDEX_BUNDLE);
        List<Pokemon> pokemonList = (List<Pokemon>) pokemonExtras.getSerializable(Utils.EXTRA_POKEDEX_LIST);
        if (pokemonList != null) {
            mPokemonListAdapter.addNewPokemonList(pokemonList);
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
    public void renderPokemonList(List<Pokemon> pokemonList) {
        mPokemonListAdapter.addNewPokemonList(pokemonList);
    }

    @Override
    public void onPokemonItemClick(Pokemon pokemon, int pos, ImageView img) {
        PokemonPreviewDialog pokemonDialog = new PokemonPreviewDialog(this);
        pokemonDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pokemonDialog.setContentView(R.layout.pokemon_preview_dialog);
        Objects.requireNonNull(pokemonDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pokemonDialog.show();

/*        Intent intent = new Intent(PokemonListActivity.this, PokemonViewActivity.class)
                .putExtra(Utils.EXTRA_POKEMON_NAME, pokemon.getName())
                .putExtra(Utils.EXTRA_POKEMON_ID, pos + 1); // +1 cause RecyclerView starts on 0

        if (img.getDrawable() != null)  // Check if the image loaded successfully to be passed on intent
        {
            Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
            intent.putExtra(Utils.EXTRA_POKEMON_IMAGE, bitmap);
            startActivity(intent);
        } else {
            Toast.makeText(this, getString(R.string.pokemon_list_image_msg), Toast.LENGTH_SHORT).show();
        }*/
    }

    @Override
    public void renderPokemon(Pokemon pokemon) {
        // Do nothing here
    }
}
