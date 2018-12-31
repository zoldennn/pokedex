package com.example.camilo.pokedex.models;

import android.support.annotation.VisibleForTesting;

class PokemonAbilityBasicDataDto {

    private String name;
    private String url;

    // GETTERS
    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    // SETERS
    @VisibleForTesting
    public void setName(String name) {
        this.name = name;
    }

    @VisibleForTesting
    public void setUrl(String url) {
        this.url = url;
    }
}
