package com.example.camilo.pokedex.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.camilo.pokedex.R;
import com.example.camilo.pokedex.models.Pokemon;
import com.example.camilo.pokedex.models.PokemonResponse;
import com.example.camilo.pokedex.services.ApiCallService;
import com.example.camilo.pokedex.services.PokemonService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.camilo.pokedex.acts.PokemonListActivity.mMustCharge;

public class PokemonListFetcher {

    private List<Pokemon> mPokemonList = new ArrayList<>();
    private PokemonService mPokemonService;
    private static int mOffset;
    private Context mContext;

    public PokemonListFetcher(Context context, int offset, PokemonService listener) {
        mContext = context;
        mOffset = offset;
        mPokemonService = listener;
    }

    public void callPokemonApi() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // Get 20 pokemon list to adapter
        ApiCallService apiCallService = retrofit.create(ApiCallService.class);
        Call<PokemonResponse> pokemonResponseCall = apiCallService.getPokemonList(20, mOffset);

        pokemonResponseCall.enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                mMustCharge = true;
                if (response.isSuccessful()) {
                    PokemonResponse pokemonResponse = response.body();
                    mPokemonList = pokemonResponse.getResults();
                    mPokemonService.renderPokemonList(mPokemonList);
                } else {
                    Toast.makeText(mContext, mContext.getString(R.string.api_call_error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                // If error, call API again
                mMustCharge = true;
                showAlert();
            }
        });
    }

    public void setScrollListener(RecyclerView recyclerView, final GridLayoutManager manager) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    int visibleItemCount = manager.getChildCount();
                    int totalItemCount = manager.getItemCount();
                    int pastVisibleItems = manager.findFirstVisibleItemPosition();

                    // Checks if must charge Pokemon and if the recyclerView has reached the bottom
                    if (mMustCharge && visibleItemCount + pastVisibleItems >= totalItemCount) {
                        mMustCharge = false;
                        mOffset += 20;
                        callPokemonApi();
                    }
                }
            }
        });
    }

    private void showAlert(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
        builder1.setMessage(mContext.getString(R.string.api_call_error));
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Salir de la app",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Activity myActivity = (Activity) mContext;
                        myActivity.finish();
                    }
                });

        builder1.setNegativeButton(
                "Reintentar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        callPokemonApi();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
