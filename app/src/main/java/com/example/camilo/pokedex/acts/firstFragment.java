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
import com.example.camilo.pokedex.deserializers.Deserializer;
import com.example.camilo.pokedex.dialogs.LoadingDialog;
import com.example.camilo.pokedex.models.Pokemon;
import com.example.camilo.pokedex.services.ApiCallService;
import com.example.camilo.pokedex.services.PokemonService;
import com.example.camilo.pokedex.utils.PokemonDetailsFetcher;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.camilo.pokedex.acts.EstadoPokemon.t2;

public class firstFragment extends Fragment implements PokemonService {

    @BindView(R.id.tv_pokemon_details_id)
    TextView vPokemonID;
    @BindView(R.id.tv_pokemon_details_name)
    TextView vPokemonName;
    @BindView(R.id.tv_pokemon_details_type1)
    ImageView vPokemonImageType1;
    @BindView(R.id.tv_pokemon_details_type2)
    ImageView vPokemonImageType2;
    @BindView(R.id.img_pokemon_details_photo)
    ImageView vPokemonPhoto;
    @BindView(R.id.tv_pokemon_details_hp_title)
    TextView vTitleHP;
    @BindView(R.id.tv_pokemon_details_atk_title)
    TextView vTitleATK;
    @BindView(R.id.tv_pokemon_details_def_title)
    TextView vTitleDEF;
    @BindView(R.id.tv_pokemon_details_spd_title)
    TextView vTitleSPD;
    @BindView(R.id.tv_pokemon_details_sd_title)
    TextView vTitleSDEF;
    @BindView(R.id.tv_pokemon_details_sa_title)
    TextView vTitleSATK;
    @BindView(R.id.pb_pokemon_details_hp)
    ProgressBar vBarHP;
    @BindView(R.id.pb_pokemon_details_atk)
    ProgressBar vBarATK;
    @BindView(R.id.pb_pokemon_details_def)
    ProgressBar vBarDEF;
    @BindView(R.id.pb_pokemon_details_sa)
    ProgressBar vBarSATK;
    @BindView(R.id.pb_pokemon_details_sd)
    ProgressBar vBarSDEF;
    @BindView(R.id.pb_pokemon_details_spd)
    ProgressBar vBarSPD;
    @BindView(R.id.mas)
    ImageButton vInvertDataButton;

    private int vCurrentPokemonID;
    private int vClickedPokemonID;
    private int mPokemonHP;
    private int mPokemonATK;
    private int mPokemonDEF;
    private int mPokemonSATK;
    private int mPokemonSDEF;
    private int mPokemonSPD;
    private int mShowNumbers = 1;

    private String vClickedPokemonName;
    private String mType1;
    private String mType2;

    private LoadingDialog mLoadingDialog;
    private Bitmap vClickedPokemonPhoto;
    private PokemonDetailsFetcher mPokemonDetailsFetcher;

    public firstFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.pokemon_details_fragment, container, false);
        ButterKnife.bind(this, view);
        vCurrentPokemonID = vClickedPokemonID;
        vClickedPokemonID = EstadoPokemon.id;
        vClickedPokemonName = EstadoPokemon.nameP;
        vClickedPokemonPhoto = EstadoPokemon.bitmap;
        mPokemonDetailsFetcher = new PokemonDetailsFetcher();

        // Change between numbers and letters
        vInvertDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mShowNumbers == 1) {
                    vTitleHP.setText("" + mPokemonHP);
                    vTitleATK.setText("" + mPokemonATK);
                    vTitleDEF.setText("" + mPokemonDEF);
                    vTitleSPD.setText("" + mPokemonSATK);
                    vTitleSDEF.setText("" + mPokemonSDEF);
                    vTitleSATK.setText("" + mPokemonSPD);
                    mShowNumbers = 0;
                } else {
                    vTitleHP.setText("HP");
                    vTitleATK.setText("ATK");
                    vTitleDEF.setText("DEF");
                    vTitleSPD.setText("SPD");
                    vTitleSDEF.setText("SD");
                    vTitleSATK.setText("SA");
                    mShowNumbers = 1;
                }
            }
        });

        applyFonts();

        // Check if the user already see that pokemon
        if (vCurrentPokemonID == vClickedPokemonID) {
            vPokemonID.setText(mPokemonDetailsFetcher.getViewedPokemonID(vClickedPokemonID));

            vPokemonImageType1.setImageResource(mPokemonDetailsFetcher.checkPokemonType(mType1));

            vPokemonName.setText(vClickedPokemonName);
            vPokemonPhoto.setImageBitmap(vClickedPokemonPhoto);

            setBarsValues();

            if (mPokemonDetailsFetcher.checkPokemonType(mType2) == 0) {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                lp.setMargins(100, 0, 0, 0);
                vPokemonImageType1.setLayoutParams(lp);
            }
            else
            {
                vPokemonImageType2.setImageResource(mPokemonDetailsFetcher.checkPokemonType(mType2));
            }
        } else {
            begin();
        }
        return view;
    }

    // Apply custom font to texts
    private void applyFonts() {
        vPokemonName.setTypeface(t2);
        vPokemonID.setTypeface(t2);
        vTitleHP.setTypeface(t2);
        vTitleATK.setTypeface(t2);
        vTitleDEF.setTypeface(t2);
        vTitleSPD.setTypeface(t2);
        vTitleSDEF.setTypeface(t2);
        vTitleSATK.setTypeface(t2);
    }

    private void setBarsValues() {
        vBarHP.setProgress(mPokemonHP);
        vBarATK.setProgress(mPokemonATK);
        vBarDEF.setProgress(mPokemonDEF);
        vBarSPD.setProgress(mPokemonSPD);
        vBarSATK.setProgress(mPokemonSATK);
        vBarSDEF.setProgress(mPokemonSDEF);
    }

    private void begin() {
        vPokemonID.setText(mPokemonDetailsFetcher.getViewedPokemonID(vClickedPokemonID));

        // Show name and image while loading the rest of data
        vPokemonName.setText(vClickedPokemonName);
        vPokemonPhoto.setImageBitmap(vClickedPokemonPhoto);

        showLoadingDialog();

        mPokemonDetailsFetcher.callPokemon(vClickedPokemonID, this);
    }

    @Override
    public void renderPokemonList(List<Pokemon> pokemonList) {
        // Do nothing here
    }

    @Override
    public void onPokemonItemClick(Pokemon pokemon, int pos, ImageView img) {
        // Do nothing here
    }

    @Override
    public void renderPokemon(Pokemon pokemon) {
        mType1 = pokemon.getType();
        vPokemonImageType1.setImageResource(mPokemonDetailsFetcher.checkPokemonType(mType1));

        mPokemonHP = pokemon.getHp();
        mPokemonATK = pokemon.getAtk();
        mPokemonDEF = pokemon.getDef();
        mPokemonSPD = pokemon.getSpd();
        mPokemonSATK = pokemon.getSatk();
        mPokemonSDEF = pokemon.getSdf();

        setBarsValues();

        // Check if Pokemon has 2nd type
        mType2 = pokemon.getType2();
        if (mPokemonDetailsFetcher.checkPokemonType(mType2)==0) {
            vPokemonImageType2.setVisibility(View.INVISIBLE);

            // Move type 1 to center
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            lp.setMargins(70, 0, 0, 0);
            vPokemonImageType1.setLayoutParams(lp);

        } else {
            vPokemonImageType2.setVisibility(View.VISIBLE);
            vPokemonImageType2.setImageResource(mPokemonDetailsFetcher.checkPokemonType(mType2));
        }
        mLoadingDialog.dismiss();
    }

    // Show loading dialog while charging
    private void showLoadingDialog(){
        mLoadingDialog = new LoadingDialog(getActivity());
        mLoadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mLoadingDialog.setContentView(R.layout.dialog);
        TextView loadingMsg = mLoadingDialog.findViewById(R.id.cargando); // Need this TextView to set TypeFace
        loadingMsg.setTypeface(t2);
        Objects.requireNonNull(mLoadingDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mLoadingDialog.show();
    }
}
