package com.example.camilo.pokedex.models;

import android.support.annotation.VisibleForTesting;

class PokemonFormDto {

    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @VisibleForTesting
    public void setName(String name) {
        this.name = name;
    }

    @VisibleForTesting
    public void setUrl(String url) {
        this.url = url;
    }
}
