package com.example.camilo.pokedex.acts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.camilo.pokedex.R;
import com.example.camilo.pokedex.services.Service;
import com.example.camilo.pokedex.models.Pokemon;
import com.example.camilo.pokedex.models.PokemonRespuesta;
import com.example.camilo.pokedex.utils.Dialog;
import com.example.camilo.pokedex.utils.ListaPokemonAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.camilo.pokedex.acts.EstadoPokemon.t2;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    public ArrayList<Pokemon> listaPokemon;
    private RecyclerView recyclerView;
    private ListaPokemonAdapter listaPokemonAdapter;
    private int offset;
    private boolean aptoParaCargar;
    private GridLayoutManager layoutManager;
    public TextView cargando,title;
    public Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recyclerView);
        listaPokemonAdapter = new ListaPokemonAdapter(this, R.layout.list_view, new ListaPokemonAdapter.OnItemClickListener() {

            //AL CLICKEAR EN UN ELEMENTO DEL RECYCLERVIEW SE GUARDAN DATOS BASICOS PARA AHORRAR DATOS
            @Override
            public void onItemClick(String name, int position, ImageView img) {
                int pos = position+1;  //SUMAR UNO PORQUE EL ADAPTER ARRANCA EN 0
                Intent intent = new Intent(MainActivity.this,EstadoPokemon.class);
                intent.putExtra("name",name);
                intent.putExtra("pos",pos);
                if(img.getDrawable()!=null)  //CONTROLAR QUE AL CLICKEAR SE HAYA CARGADO LA IMAGEN
                {
                    Bitmap bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();
                    intent.putExtra("img",bitmap);
                    startActivity(intent);
                }
                else
                {
                    //intent.putExtra("img","null");
                }

            }
        });

        //APLICO EL ADAPTER Y CONTROLO CUÁNDO SE LLEGÓ AL FINAL DEL RECYCLERVIEW
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listaPokemonAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy>0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if(aptoParaCargar) {
                        if(visibleItemCount + pastVisibleItems >= totalItemCount) {
                            aptoParaCargar = false;
                            offset += 20;
                            obtenerDatos(offset);
                        }
                    }
                }
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        aptoParaCargar = true;
        offset = 0;
        obtenerDatos(offset);
    }



    private void obtenerDatos(final int offset) {
        //OBTENER POKEMONES DE A 20 PARA EL ADAPTER
        Service service = retrofit.create(Service.class);
        Call<PokemonRespuesta> pokemonRespuestaCall = service.obtenerListaPokemon(20,offset);

        //CREAR DIALOG DE CARGA
        dialog = new  Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog);
        cargando = dialog.findViewById(R.id.cargando);
        cargando.setTypeface(t2);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();

        pokemonRespuestaCall.enqueue(new Callback<PokemonRespuesta>() {
            @Override
            public void onResponse(Call<PokemonRespuesta> call, Response<PokemonRespuesta> response) {
                aptoParaCargar = true;
                if(response.isSuccessful()) {
                    PokemonRespuesta pokemonRespuesta = response.body();
                    listaPokemon = pokemonRespuesta.getResults();
                    listaPokemonAdapter.adicionarPokemon(listaPokemon);
                    dialog.dismiss();
                }
                else {
                    Toast.makeText(MainActivity.this, "Se detectó un problema de conexión", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PokemonRespuesta> call, Throwable t) {
                //VUELVO A LLAMAR A LA API Y CIERRO EL DIALOGO PARA QUE NO SE SUPERPONGA
                aptoParaCargar = true;
                dialog.dismiss();
                obtenerDatos(offset);

            }
        });
    }


}
