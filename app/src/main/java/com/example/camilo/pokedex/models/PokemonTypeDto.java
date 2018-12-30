package com.example.camilo.pokedex.models;

import android.support.annotation.VisibleForTesting;

public class PokemonTypeDto {

    //TODO Property @slot

    private PokemonTypeBasicDataDto type;

    public PokemonTypeBasicDataDto getTypeBasicData() {
        return type;
    }

    @VisibleForTesting
    public void setType(PokemonTypeBasicDataDto type) {
        this.type = type;
    }
}
