package com.example.camilo.pokedex.acts;


import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.camilo.pokedex.R;
import com.example.camilo.pokedex.services.Service;
import com.example.camilo.pokedex.deserializers.Deserializer;
import com.example.camilo.pokedex.models.Pokemon;
import com.example.camilo.pokedex.utils.Dialog;
import com.google.gson.GsonBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.camilo.pokedex.acts.EstadoPokemon.t2;


public class firstFragment extends Fragment{

    private Retrofit retrofit;
    private TextView cargando, idPoke,name,hp,atk,def,spd,sdf,satk;
    public ImageView img,type,type2;
    public static String namePoke, tipo1, tipo2;
    public ProgressBar barHP,barATK,barDEF,barSATK,barSDEF,barSPD;
    public Bitmap bitmapp;
    public static int idComp, id2, valorHP,valorATK, valorDEF, valorSATK, valorSDEF, valorSPD, control=1;
    public Dialog dialog;
    public ImageButton mas;

    public firstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        idComp = id2;
        id2 = EstadoPokemon.id;
        namePoke = EstadoPokemon.nameP;
        bitmapp = EstadoPokemon.bitmap;

        idPoke = view.findViewById(R.id.viewID);
        name = view.findViewById(R.id.viewNAME);
        type = view.findViewById(R.id.viewTYPE1);
        type2 = view.findViewById(R.id.viewTYPE2);
        img = view.findViewById(R.id.viewIMG);
        //desc = view.findViewById(R.id.viewDESC);

        hp = view.findViewById(R.id.hp);
        atk = view.findViewById(R.id.atk);
        def = view.findViewById(R.id.def);
        satk = view.findViewById(R.id.ht);
        sdf = view.findViewById(R.id.wt);
        spd = view.findViewById(R.id.xp);

        barHP = view.findViewById(R.id.progressHP);
        barATK = view.findViewById(R.id.progressATK);
        barDEF = view.findViewById(R.id.progressDEF);
        barSATK = view.findViewById(R.id.progressXP);
        barSDEF = view.findViewById(R.id.progressWT);
        barSPD = view.findViewById(R.id.progressHT);
        mas = view.findViewById(R.id.mas);

        //CAMBIAR ENTRE NUMEROS Y LETRAS
        mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(control==1) {
                    hp.setText(""+valorHP);
                    atk.setText(""+valorATK);
                    def.setText(""+valorDEF);
                    satk.setText(""+valorSATK);
                    sdf.setText(""+valorSDEF);
                    spd.setText(""+valorSPD);
                    control = 0;
                }
                else {
                    hp.setText("HP");
                    atk.setText("ATK");
                    def.setText("DEF");
                    satk.setText("SPD");
                    sdf.setText("SD");
                    spd.setText("SA");
                    control = 1;
                }

            }
        });

        //APLICAR FUENTE A LOS TEXTS
        name.setTypeface(t2);
        idPoke.setTypeface(t2);
        hp.setTypeface(t2);
        atk.setTypeface(t2);
        def.setTypeface(t2);
        satk.setTypeface(t2);
        sdf.setTypeface(t2);
        spd.setTypeface(t2);

        //REVISAR SI YA SE VIO ESE POKEMON PARA AHORRAR DATOS
        if(idComp == id2) {

            if(id2<10) {
                idPoke.setText("#00"+id2);
            }
            else {
                if(id2<100) {
                    idPoke.setText("#0"+id2);
                }
                else {
                    idPoke.setText("#"+id2);
                }
            }
            revisarTIPO(tipo1,type);
            revisarTIPO(tipo2,type2);
            name.setText(namePoke);
            img.setImageBitmap(bitmapp);
            barHP.setProgress(valorHP);
            barATK.setProgress(valorATK);
            barDEF.setProgress(valorDEF);
            barSPD.setProgress(valorSPD);
            barSATK.setProgress(valorSATK);
            barSDEF.setProgress(valorSDEF);
            if(tipo2.equals("null")) {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                lp.setMargins(100, 0, 0, 0);
                type.setLayoutParams(lp);
            }
        }
        else {
            begin();
        }
        return view;
    }

    private void begin() {


        revisarID();

        //MOSTRAR NOMBRE E IMAGEN
        name.setText(namePoke);
        img.setImageBitmap(bitmapp);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Pokemon.class, new Deserializer());

        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create(builder.create()))
                .build();

        Service service = retrofit.create(Service.class);

        //CREAR Y MOSTRAR DIÁLOGO DE CARGA
        dialog = new  Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog);
        cargando = dialog.findViewById(R.id.cargando);
        cargando.setTypeface(t2);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();

        //LLAMADA A LA API
        Call<Pokemon> pokemonCall = service.getPokemon(id2);
        pokemonCall.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                if(response.isSuccessful())
                {
                    Pokemon pokemon = response.body();
                    tipo1 = pokemon.getType();
                    revisarTIPO(tipo1,type);

                    valorHP = pokemon.getHp();
                    barHP.setProgress(valorHP);

                    valorATK = pokemon.getAtk();
                    barATK.setProgress(valorATK);

                    valorDEF = pokemon.getDef();
                    barDEF.setProgress(valorDEF);

                    valorSPD = pokemon.getSpd();
                    barSPD.setProgress(valorSPD);

                    valorSATK = pokemon.getSatk();
                    barSATK.setProgress(valorSATK);

                    valorSDEF = pokemon.getSdf();
                    barSDEF.setProgress(valorSDEF);


                    //----------------------GUARDAR PARA HT Y WT DOUBLES--------------------

                    /*String wt = Integer.toString(pokemon.getWid());
                    if(wt.length()==4)
                    {
                        wt = wt.substring(0, 3) + "." + wt.substring(3, wt.length());
                    }
                    else
                    {
                        if(wt.length()==3)
                        {
                            wt = wt.substring(0, 2) + "." + wt.substring(2, wt.length());
                        }
                        else
                        {
                            if(wt.length()==2)
                            {
                                wt = wt.substring(0, 1) + "." + wt.substring(1, wt.length());
                            }
                        }
                    }

                    double redondearW = Double.parseDouble(wt);
                    long wttt = Math.round(redondearW);*/


                    /*String ht = Integer.toString(pokemon.getHei());
                    if(ht.length()==2)
                    {
                        ht = ht.substring(0, 1) + "." + ht.substring(1, ht.length());
                    }
                    else
                    {
                        ht = "0."+ht;
                    }
                    double redondearH = Double.parseDouble(ht);
                    long htt = Math.round(redondearH);

                    ------------------------------------------------------------------------
                    */



                    //REVISAR SI EL POKEMON TIENE 2DO TIPO
                    tipo2 = pokemon.getType2();
                    if ("null".equals(tipo2)) {
                        type2.setVisibility(View.INVISIBLE);

                        //MOVER EL TIPO PRINCIPAL A LA DERECHA
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        lp.setMargins(70, 0, 0, 0);
                        type.setLayoutParams(lp);

                    }
                    else {
                        type2.setVisibility(View.VISIBLE);
                        revisarTIPO(tipo2,type2);
                    }

                }
                //TERMINÓ LA CARGA, CERRAR DIALOG
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                dialog.dismiss();
                begin();
                cargando.setText("Reintentando conexión..");
            }
        });
    }

    //FUNCIÓN QUE CONCATENA BASADO EN EL NÚMERO DE POKEMON
    private void revisarID() {
        if(id2<10)
        {
            idPoke.setText("#00"+id2);
        }
        else
        {
            if(id2<100)
            {
                idPoke.setText("#0"+id2);
            }
            else
            {
                idPoke.setText("#"+id2);
            }
        }
    }

    //FUNCIÓN QUE REVISA LOS TIPOS DEL POKEMON
    private void revisarTIPO(String tipo, ImageView imgV) {
        switch (tipo){
            case "bug":
                imgV.setImageResource(R.drawable.bug);
                break;
            case "dark":
                imgV.setImageResource(R.drawable.dark);
                break;
            case "dragon":
                imgV.setImageResource(R.drawable.dragon);
                break;
            case "electric":
                imgV.setImageResource(R.drawable.electric);
                break;
            case "fairy":
                imgV.setImageResource(R.drawable.fairy);
                break;
            case "fighting":
                imgV.setImageResource(R.drawable.fighting);
                break;
            case "fire":
                imgV.setImageResource(R.drawable.fire);
                break;
            case "ghost":
                imgV.setImageResource(R.drawable.ghost);
                break;
            case "grass":
                imgV.setImageResource(R.drawable.grass);
                break;
            case "ground":
                imgV.setImageResource(R.drawable.ground);
                break;
            case "ice":
                imgV.setImageResource(R.drawable.ice);
                break;
            case "normal":
                imgV.setImageResource(R.drawable.normal);
                break;
            case "poison":
                imgV.setImageResource(R.drawable.poison);
                break;
            case "psychic":
                imgV.setImageResource(R.drawable.psychic);
                break;
            case "steel":
                imgV.setImageResource(R.drawable.steel);
                break;
            case "water":
                imgV.setImageResource(R.drawable.water);
                break;
            case "rock":
                imgV.setImageResource(R.drawable.rock);
                break;
            case "flying":
                imgV.setImageResource(R.drawable.flying);
                break;
        }
    }


}
