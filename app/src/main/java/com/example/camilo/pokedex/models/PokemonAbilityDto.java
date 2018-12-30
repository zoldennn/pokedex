package com.example.camilo.pokedex.models;

import android.support.annotation.VisibleForTesting;

class PokemonAbilityDto {

    // TODO: Properties @is_hidden && @slot

    private PokemonAbilityBasicDataDto ability;

    public PokemonAbilityBasicDataDto getAbility() {
        return ability;
    }

    @VisibleForTesting
    public void setAbility(PokemonAbilityBasicDataDto ability) {
        this.ability = ability;
    }
}
