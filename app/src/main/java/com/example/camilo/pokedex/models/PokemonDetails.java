package com.example.camilo.pokedex.models;

public class PokemonDetails {
    private String description;
    private String habitat;
    private String captureRate;

    public PokemonDetails(String description, String habitat, String captureRate) {
        this.description = description;
        this.habitat = habitat;
        this.captureRate = captureRate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHabitat() {
        return habitat;
    }

    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }

    public String getCaptureRate() {
        return captureRate;
    }

    public void setCaptureRate(String captureRate) {
        this.captureRate = captureRate;
    }
}
