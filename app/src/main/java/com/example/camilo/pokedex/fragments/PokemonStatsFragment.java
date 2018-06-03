package com.example.camilo.pokedex.fragments;

import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.camilo.pokedex.MyApplication;
import com.example.camilo.pokedex.R;
import com.example.camilo.pokedex.dialogs.LoadingDialog;
import com.example.camilo.pokedex.models.Pokemon;
import com.example.camilo.pokedex.services.PokemonService;
import com.example.camilo.pokedex.utils.PokemonDetailsFetcher;
import com.example.camilo.pokedex.utils.Utils;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.camilo.pokedex.acts.PokemonViewActivity.mCustomFont;

public class PokemonStatsFragment extends Fragment implements PokemonService {

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

    private int mCurrentPokemonID;
    private int mLastClickedPokemonID;
    private String vClickedPokemonName;
    private LoadingDialog mLoadingDialog;
    private Bitmap mClickedPokemonPhoto;
    private PokemonDetailsFetcher mPokemonDetailsFetcher;

    public static PokemonStatsFragment newInstance(int id, String name, Bitmap bitmap) {
        PokemonStatsFragment fragment = new PokemonStatsFragment();
        Bundle args = new Bundle();
        args.putInt(Utils.EXTRA_POKEMON_ID, id);
        args.putString(Utils.EXTRA_POKEMON_NAME, name);
        args.putParcelable(Utils.EXTRA_POKEMON_IMAGE, bitmap);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mLastClickedPokemonID = getArguments().getInt(Utils.EXTRA_POKEMON_ID);
        if (Utils.EXTRA_POKEMON_OLD_ID == mLastClickedPokemonID) {
            mCurrentPokemonID = mLastClickedPokemonID;
        } else {
            Utils.EXTRA_POKEMON_OLD_ID = mLastClickedPokemonID;
        }
        mClickedPokemonPhoto = getArguments().getParcelable(Utils.EXTRA_POKEMON_IMAGE);
        vClickedPokemonName = getArguments().getString(Utils.EXTRA_POKEMON_NAME);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
        Utils.EXTRA_POKEMON_OLD_ID = mLastClickedPokemonID;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pokemon_stats, container, false);
        ButterKnife.bind(this, view);

        mPokemonDetailsFetcher = new PokemonDetailsFetcher();

        init();

        return view;
    }

    private void init() {
        applyFonts();

        // Check if the user already see that pokemon
        if (mCurrentPokemonID == mLastClickedPokemonID) {
            Pokemon pokemon = MyApplication.getLastPokemon();
            vPokemonID.setText(mPokemonDetailsFetcher.transformPokemonID(getContext(), mLastClickedPokemonID));

            vPokemonImageType1.setImageResource(mPokemonDetailsFetcher.checkPokemonTypes(pokemon.getType()));

            vPokemonName.setText(vClickedPokemonName);
            vPokemonPhoto.setImageBitmap(mClickedPokemonPhoto);

            setBarsProgress(pokemon);

            // Checks if pokemon have second type
            if (mPokemonDetailsFetcher.checkPokemonTypes(pokemon.getType2()) == 0) {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                lp.setMargins(100, 0, 0, 0);
                vPokemonImageType1.setLayoutParams(lp);
            } else {
                vPokemonImageType2.setImageResource(mPokemonDetailsFetcher.checkPokemonTypes(pokemon.getType2()));
            }
        } else {
            showBasicPokemonData();
        }
    }

    // Apply custom font to texts
    private void applyFonts() {
        vPokemonName.setTypeface(mCustomFont);
        vPokemonID.setTypeface(mCustomFont);
        vTitleHP.setTypeface(mCustomFont);
        vTitleATK.setTypeface(mCustomFont);
        vTitleDEF.setTypeface(mCustomFont);
        vTitleSPD.setTypeface(mCustomFont);
        vTitleSDEF.setTypeface(mCustomFont);
        vTitleSATK.setTypeface(mCustomFont);
    }

    private void setBarsProgress(Pokemon pokemon) {
        vBarHP.setProgress(pokemon.getHp());
        vBarATK.setProgress(pokemon.getAtk());
        vBarDEF.setProgress(pokemon.getDef());
        vBarSPD.setProgress(pokemon.getSpd());
        vBarSATK.setProgress(pokemon.getSatk());
        vBarSDEF.setProgress(pokemon.getSdf());
    }

    // Show id, name and image while loading the rest of data
    private void showBasicPokemonData() {
        vPokemonID.setText(mPokemonDetailsFetcher.transformPokemonID(getContext(), mLastClickedPokemonID));
        vPokemonName.setText(vClickedPokemonName);
        vPokemonPhoto.setImageBitmap(mClickedPokemonPhoto);
        showLoadingDialog();

        mPokemonDetailsFetcher.callPokemon(mLastClickedPokemonID, this);
    }

    @Override
    public void renderPokemon(Pokemon pokemon) {
        String pokemonType1 = pokemon.getType();
        vPokemonImageType1.setImageResource(mPokemonDetailsFetcher.checkPokemonTypes(pokemonType1));

        // TODO: GET STATS AND SHOW ON PROGRESS BAR'S RIGHT

        setBarsProgress(pokemon);

        // Check if Pokemon has 2nd type
        String pokemonType2 = pokemon.getType2();
        if (mPokemonDetailsFetcher.checkPokemonTypes(pokemonType2) == 0) {
            vPokemonImageType2.setVisibility(View.INVISIBLE);

            // Move type 1 to center
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            lp.setMargins(70, 0, 0, 0);
            vPokemonImageType1.setLayoutParams(lp);
        } else {
            vPokemonImageType2.setVisibility(View.VISIBLE);
            vPokemonImageType2.setImageResource(mPokemonDetailsFetcher.checkPokemonTypes(pokemonType2));
        }
        mLoadingDialog.dismiss();
    }

    // Show loading dialog while charging
    private void showLoadingDialog() {
        mLoadingDialog = new LoadingDialog(getActivity());
        mLoadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mLoadingDialog.setContentView(R.layout.dialog);
        TextView loadingMsg = mLoadingDialog.findViewById(R.id.cargando); // Need this TextView to set TypeFace
        loadingMsg.setTypeface(mCustomFont);
        Objects.requireNonNull(mLoadingDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mLoadingDialog.show();
    }

    @Override
    public void renderPokemonList(List<Pokemon> pokemonList) {
        // Do nothing here
    }

    @Override
    public void onPokemonItemClick(Pokemon pokemon, int pos, ImageView img) {
        // Do nothing here
    }
}
