package com.example.camilo.pokedex.models;

import android.support.annotation.VisibleForTesting;

import java.util.List;

public class PokemonDto {

    private List<PokemonAbilityDto> abilities;
    private int baseExperience;
    private int height;
    private int id;
    private List<PokemonMoveDto> moves;
    private PokemonSpeciesDto species;
    private PokemonSpritesDto sprites;
    private List<PokemonStatDto> stats;
    private int weight;

    // GETTERS
    public List<PokemonAbilityDto> getAbilities() {
        return abilities;
    }

    public int getBaseExperience() {
        return baseExperience;
    }

    public int getHeight() {
        return height;
    }

    public int getId() {
        return id;
    }

    public List<PokemonMoveDto> getMoves() {
        return moves;
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
    public void setWeight(int weight) {
        this.weight = weight;
    }
}
