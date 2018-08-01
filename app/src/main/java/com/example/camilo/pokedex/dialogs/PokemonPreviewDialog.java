package com.example.camilo.pokedex.dialogs;

import android.app.Activity;
import android.os.Bundle;

import com.example.camilo.pokedex.R;

public class PokemonPreviewDialog extends android.app.Dialog{

    public Activity c;

    public PokemonPreviewDialog(Activity a) {
        super(a);
        this.c=a;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemon_preview_dialog);
    }
}
