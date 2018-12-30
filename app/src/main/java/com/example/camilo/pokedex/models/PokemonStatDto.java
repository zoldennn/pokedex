package com.example.camilo.pokedex.models;

import android.support.annotation.VisibleForTesting;

public class PokemonStatDto {

    private int base_stat;
    private int effort;
    private PokemonStatBasicDataDto stat;

    public int getBaseStat() {
        return base_stat;
    }

    public int getEffort() {
        return effort;
    }

    public PokemonStatBasicDataDto getStat() {
        return stat;
    }

    @VisibleForTesting
    public void setBaseStat(int baseStat) {
        this.base_stat = baseStat;
    }

    @VisibleForTesting
    public void setEffort(int effort) {
        this.effort = effort;
    }

    @VisibleForTesting
    public void setStat(PokemonStatBasicDataDto stat) {
        this.stat = stat;
    }
}
