package com.example.camilo.pokedex.models;



public class PokemonDatos {

    private String tipo;

    public PokemonDatos(String tipo) {
        this.tipo = tipo;
    }

    String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
