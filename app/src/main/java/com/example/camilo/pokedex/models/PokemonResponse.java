package com.example.camilo.pokedex.models;

import java.util.List;

public class PokemonResponse {

    private List<PokeResponse> results;

    public List<PokeResponse> getResults() {
        return results;
    }

    public void setResults(List<PokeResponse> results) {
        this.results = results;
    }
}
