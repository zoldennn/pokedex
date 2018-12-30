package com.example.camilo.pokedex.models;

import android.support.annotation.VisibleForTesting;

class PokemonAbilityDto {
    private PokemonAbilityBasicDataDto ability;

    // TODO: Properties boolean @is_hidden && int @slot

    public PokemonAbilityBasicDataDto getAbility() {
        return ability;
    }

    @VisibleForTesting
    public void setAbility(PokemonAbilityBasicDataDto ability) {
        this.ability = ability;
    }
}
