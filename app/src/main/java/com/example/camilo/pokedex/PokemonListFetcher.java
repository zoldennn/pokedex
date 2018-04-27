package com.example.camilo.pokedex;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.camilo.pokedex.acts.PokemonListActivity;
import com.example.camilo.pokedex.models.Pokemon;
import com.example.camilo.pokedex.models.PokemonResponse;
import com.example.camilo.pokedex.services.Service;
import com.example.camilo.pokedex.utils.LoadingDialog;
import com.example.camilo.pokedex.utils.PokemonListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.camilo.pokedex.acts.EstadoPokemon.t2;

public class PokemonListFetcher {

    private List<Pokemon> mPokemonList = new ArrayList<>();
    private PokemonService mPokemonService;

    public PokemonListFetcher(PokemonService listener) {
        mPokemonService = listener;
    }

    public void callPokemonApi(final Activity activity, final int offset, final PokemonListAdapter adapter) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // Get 20 pokemon list to adapter
        Service service = retrofit.create(Service.class);
        Call<PokemonResponse> pokemonResponseCall = service.obtenerListaPokemon(20, offset);

        // Create loading Dialog
        final LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadingDialog.setContentView(R.layout.dialog);
        TextView loadingMsg = loadingDialog.findViewById(R.id.cargando);
        loadingMsg.setTypeface(t2);
        Objects.requireNonNull(loadingDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        loadingDialog.show();

        pokemonResponseCall.enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                PokemonListActivity.mMustCharge = true;
                if (response.isSuccessful()) {
                    PokemonResponse pokemonResponse = response.body();
                    mPokemonList = pokemonResponse.getResults();
                    mPokemonService.renderPokemonList(mPokemonList);
                    loadingDialog.dismiss();
                } else {
                    Toast.makeText(activity, "Se detectó un problema de conexión", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                // If error, call API again
                loadingDialog.dismiss();
                PokemonListActivity.mMustCharge = true;
                callPokemonApi(activity, offset, adapter);
            }
        });
    }
}
