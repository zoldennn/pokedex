package com.example.camilo.pokedex;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.camilo.pokedex.acts.PokemonListActivity;
import com.example.camilo.pokedex.models.Pokemon;
import com.example.camilo.pokedex.models.PokemonResponse;
import com.example.camilo.pokedex.models.PokemonRespuesta;
import com.example.camilo.pokedex.services.Service;
import com.example.camilo.pokedex.utils.LoadingDialog;
import com.example.camilo.pokedex.utils.PokemonListAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.camilo.pokedex.acts.EstadoPokemon.t2;

public class PokemonListFetcher {

    private List<PokemonResponse> mPokemonResponseList = new ArrayList<>();
    private static List<Pokemon> mPokemonList = new ArrayList<>();
    private static PokemonService mPokemonService;

    public PokemonListFetcher(PokemonService listener) {
        mPokemonService = listener;
    }

    public void callPokemonApi(final Activity activity, final int offset, final PokemonListAdapter adapter) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //OBTENER POKEMONES DE A 20 PARA EL ADAPTER
        Service service = retrofit.create(Service.class);
        Call<PokemonRespuesta> pokemonRespuestaCall = service.obtenerListaPokemon(20, offset);

        //CREAR DIALOG DE CARGA
        final LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadingDialog.setContentView(R.layout.dialog);
        TextView loadingMsg = loadingDialog.findViewById(R.id.cargando);
        loadingMsg.setTypeface(t2);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //loadingDialog.show();

        pokemonRespuestaCall.enqueue(new Callback<PokemonRespuesta>() {
            @Override
            public void onResponse(Call<PokemonRespuesta> call, Response<PokemonRespuesta> response) {
                PokemonListActivity.aptoParaCargar = true;
                if (response.isSuccessful()) {
                    PokemonRespuesta pokemonRespuesta = response.body();
                    mPokemonList = pokemonRespuesta.getResults();
                    //adapter.adicionarPokemon(mPokemonList);
                    mPokemonService.renderPokemons(mPokemonList);
                    Log.d("TAG", "SUCCESS API");
                    //loadingDialog.dismiss();
                } else {
                    Toast.makeText(activity, "Se detectó un problema de conexión", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PokemonRespuesta> call, Throwable t) {
                //VUELVO A LLAMAR A LA API Y CIERRO EL DIALOGO PARA QUE NO SE SUPERPONGA
                //loadingDialog.dismiss();
                PokemonListActivity.aptoParaCargar = true;
                Log.d("TAG", "FAILED API");
                callPokemonApi(activity, offset, adapter);
            }
        });
    }
}
