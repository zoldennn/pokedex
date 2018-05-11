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
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.camilo.pokedex.R;
import com.example.camilo.pokedex.adapters.PokemonListAdapter;
import com.example.camilo.pokedex.dialogs.LoadingDialog;
import com.example.camilo.pokedex.models.Pokemon;
import com.example.camilo.pokedex.services.PokemonService;
import com.example.camilo.pokedex.utils.PokemonListFetcher;
import com.example.camilo.pokedex.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.camilo.pokedex.acts.EstadoPokemon.t2;


public class PokemonListActivity extends AppCompatActivity implements PokemonService {

    @BindView(R.id.pokemon_list_recyclerview)
    RecyclerView mPokemonListRecyclerView;
    private PokemonListAdapter mPokemonListAdapter;
    private int mOffset;
    public static boolean mMustCharge;
    private GridLayoutManager mLayoutManager;
    private PokemonListFetcher mPokemonListFetcher;
    private LoadingDialog mLoadingDialog;
    private Toolbar mToolbar;
    private List<Pokemon> lista = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setIcon(R.drawable.img_pokedex_toolbar_logo);
        setupRecyclerView();

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        lista = (List<Pokemon>) bundle.getSerializable("lista");
        if(lista!=null){
            mPokemonListAdapter.addNewPokemonList(lista);
        }
        Log.d("TAG", lista.get(2).getName());
        //showLoadingDialog();

        mMustCharge = true; // This var avoids to make api calls continuously
        //mOffset = 0; // To call pokemon from 0 to 20
        //mPokemonListFetcher.callPokemonApi(); // Make API call
    }

    @Override
    public void renderPokemonList(List<Pokemon> pokemonList) {
        mPokemonListAdapter.addNewPokemonList(pokemonList);
    }

    @Override
    public void onPokemonItemClick(Pokemon pokemon, int pos, ImageView img) {
        Intent intent = new Intent(PokemonListActivity.this, EstadoPokemon.class)
                .putExtra(Utils.EXTRA_POKEMON_NAME, pokemon.getName())
                .putExtra(Utils.EXTRA_POKEMON_ID, pos+1);

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
    public void renderPokemon(Pokemon pokemon) {
        // Do nothing here
    }

    // Setup the RecyclerView, GridLayoutManager and Adapter
    private void setupRecyclerView() {
        mPokemonListRecyclerView.setHasFixedSize(true); // For better performance
        mLayoutManager = new GridLayoutManager(this, 3); // Grid layout 3 items
        mPokemonListRecyclerView.setLayoutManager(mLayoutManager); // Grid horizontal items per view

        mPokemonListFetcher = new PokemonListFetcher(this, mOffset, this); // Instantiate this class to make the calls
        mPokemonListAdapter = new PokemonListAdapter(this, this); // Adapter that will populate the data

        mPokemonListFetcher.setScrollListener(mPokemonListRecyclerView, mLayoutManager);
        mPokemonListRecyclerView.setAdapter(mPokemonListAdapter);
    }

    // Create loading Dialog
    private void showLoadingDialog() {
        mLoadingDialog = new LoadingDialog(this);
        mLoadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mLoadingDialog.setContentView(R.layout.dialog);
        TextView loadingMsg = mLoadingDialog.findViewById(R.id.cargando); // Need this TextView to set TypeFace
        loadingMsg.setTypeface(t2);
        Objects.requireNonNull(mLoadingDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mLoadingDialog.show();
    }
}
