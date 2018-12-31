package com.example.camilo.pokedex.fragments;

import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.camilo.pokedex.MyApplication;
import com.example.camilo.pokedex.R;
import com.example.camilo.pokedex.adapters.PokemonTypesAdapter;
import com.example.camilo.pokedex.dialogs.LoadingDialog;
import com.example.camilo.pokedex.models.PokeResponse;
import com.example.camilo.pokedex.models.PokemonDto;
import com.example.camilo.pokedex.models.PokemonStatDto;
import com.example.camilo.pokedex.models.PokemonTypeDto;
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
    @BindView(R.id.rv_pokemon_type)
    RecyclerView vPokemonTypesRecyclerView;

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
        View view = inflater.inflate(R.layout.pokemon_details_fragment, container, false);
        ButterKnife.bind(this, view);

        mPokemonDetailsFetcher = new PokemonDetailsFetcher();

        init();

        return view;
    }

    private void init() {
        applyFonts();

        // Check if the user already see that pokemon
        if (mCurrentPokemonID == mLastClickedPokemonID) {
            PokemonDto pokemon = MyApplication.getLastPokemon();
            vPokemonID.setText(mPokemonDetailsFetcher.transformPokemonID(getContext(), mLastClickedPokemonID));

            setupPokemonTypesRecyclerView(pokemon.getTypes());
            vPokemonName.setText(vClickedPokemonName);
            vPokemonPhoto.setImageBitmap(mClickedPokemonPhoto);

            setBarsProgress(pokemon.getStats());

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

    private void setBarsProgress(List<PokemonStatDto> pokemonStats) {
        setStatForBar(vBarHP, pokemonStats.get(0));
        setStatForBar(vBarATK, pokemonStats.get(1));
        setStatForBar(vBarDEF, pokemonStats.get(2));
        setStatForBar(vBarSPD, pokemonStats.get(3));
        setStatForBar(vBarSATK, pokemonStats.get(4));
        setStatForBar(vBarSDEF, pokemonStats.get(5));
    }

    private void setStatForBar(ProgressBar statBar, PokemonStatDto pokemonStatDto) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            statBar.setProgress(pokemonStatDto.getBaseStat(), true);
        } else {
            statBar.setProgress(pokemonStatDto.getBaseStat());
        }
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
    public void renderPokemon(PokemonDto pokemon) {
        // TODO: GET STATS AND SHOWS THEM AT PROGRESS BAR'S RIGHT

        setBarsProgress(pokemon.getStats());

        setupPokemonTypesRecyclerView(pokemon.getTypes());

        mLoadingDialog.dismiss();
    }

    private void setupPokemonTypesRecyclerView(List<PokemonTypeDto> pokemonTypes) {
        vPokemonTypesRecyclerView.setHasFixedSize(true); // For better performance
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);// Horizontal scroll
        vPokemonTypesRecyclerView.setLayoutManager(mLayoutManager);

        PokemonTypesAdapter pokemonTypesAdapter= new PokemonTypesAdapter(pokemonTypes);
        vPokemonTypesRecyclerView.setAdapter(pokemonTypesAdapter);
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
    public void renderPokemonList(List<PokeResponse> pokemonList) {
        // Do nothing here
    }

    @Override
    public void onPokemonItemClick(PokeResponse pokemon, int pos, ImageView img) {
        // Do nothing here
    }
}
