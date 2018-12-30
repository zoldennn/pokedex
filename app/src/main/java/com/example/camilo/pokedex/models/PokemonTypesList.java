package com.example.camilo.pokedex.models;

import android.support.annotation.VisibleForTesting;

public class PokemonTypesList {
    private int slot;
    private PokemonTypeData type;

    public int getSlot() {
        return slot;
    }

    @VisibleForTesting
    public void setSlot(int slot) {
        this.slot = slot;
    }

    public PokemonTypeData getType() {
        return type;
    }

    @VisibleForTesting
    public void setType(PokemonTypeData type) {
        this.type = type;
    }
}
