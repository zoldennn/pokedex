package com.example.camilo.pokedex.models;

import android.support.annotation.VisibleForTesting;

import java.io.Serializable;
import java.util.List;

public class PokemonDto implements Serializable {

    private List<PokemonAbilityDto> abilities;
    private int baseExperience;
    private List<PokemonFormDto> forms;
    private int height;
    private int id;
    private List<PokemonMoveDto> moves;
    private String name;
    private PokemonSpeciesDto species;
    private PokemonSpritesDto sprites;
    private List<PokemonStatDto> stats;
    private List<PokemonTypeDto> types;
    private int weight;

    // GETTERS
    public List<PokemonAbilityDto> getAbilities() {
        return abilities;
    }

    public int getBaseExperience() {
        return baseExperience;
    }

    public List<PokemonFormDto> getForms() {
        return forms;
    }

    public int getHeight() {
        return height;
    }

    public int getId() {
        String[] urlParts = getForms().get(0).getName().split("/");
        return Integer.parseInt(urlParts[urlParts.length - 1]);
    }

    public List<PokemonMoveDto> getMoves() {
        return moves;
    }

    public String getName() {
        return name;
    }

    public PokemonSpeciesDto getSpecies() {
        return species;
    }

    public PokemonSpritesDto getSprites() {
        return sprites;
    }

    public List<PokemonStatDto> getStats() {
        return stats;
    }

    public List<PokemonTypeDto> getTypes() {
        return types;
    }

    public int getWeight() {
        return weight;
    }

    // SETTERS
    @VisibleForTesting
    public void setAbilities(List<PokemonAbilityDto> abilities) {
        this.abilities = abilities;
    }

    @VisibleForTesting
    public void setBaseExperience(int baseExperience) {
        this.baseExperience = baseExperience;
    }

    @VisibleForTesting
    public void setForms(List<PokemonFormDto> pokemonForms) {
        this.forms = pokemonForms;
    }

    @VisibleForTesting
    public void setHeight(int height) {
        this.height = height;
    }

    @VisibleForTesting
    public void setId(int id) {
        this.id = id;
    }

    @VisibleForTesting
    public void setMoves(List<PokemonMoveDto> moves) {
        this.moves = moves;
    }

    @VisibleForTesting
    public void setName(String name){
        this.name = name;
    }

    @VisibleForTesting
    public void setSpecies(PokemonSpeciesDto species) {
        this.species = species;
    }

    @VisibleForTesting
    public void setSprites(PokemonSpritesDto sprites) {
        this.sprites = sprites;
    }

    @VisibleForTesting
    public void setStats(List<PokemonStatDto> stats) {
        this.stats = stats;
    }

    @VisibleForTesting
    public void setTypes(List<PokemonTypeDto> types) {
        this.types = types;
    }

    @VisibleForTesting
    public void setWeight(int weight) {
        this.weight = weight;
    }
}
