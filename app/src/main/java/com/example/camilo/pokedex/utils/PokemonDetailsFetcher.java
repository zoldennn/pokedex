package com.example.camilo.pokedex.utils;

import com.example.camilo.pokedex.R;

public class PokemonDetailsFetcher {


    public int checkPokemonType(String typeToCheck) {
        int drawableID = R.drawable.bug;
        switch (typeToCheck) {
            case "bug":
                drawableID = R.drawable.bug;
                break;
            case "dark":
                drawableID = R.drawable.dark;
                break;
            case "dragon":
                drawableID = R.drawable.dragon;
                break;
            case "electric":
                drawableID = R.drawable.electric;
                break;
            case "fairy":
                drawableID = R.drawable.fairy;
                break;
            case "fighting":
                drawableID = R.drawable.fighting;
                break;
            case "fire":
                drawableID = R.drawable.fire;
                break;
            case "ghost":
                drawableID = R.drawable.ghost;
                break;
            case "grass":
                drawableID = R.drawable.grass;
                break;
            case "ground":
                drawableID = R.drawable.ground;
                break;
            case "ice":
                drawableID = R.drawable.ice;
                break;
            case "normal":
                drawableID = R.drawable.normal;
                break;
            case "poison":
                drawableID = R.drawable.poison;
                break;
            case "psychic":
                drawableID = R.drawable.psychic;
                break;
            case "steel":
                drawableID = R.drawable.steel;
                break;
            case "water":
                drawableID = R.drawable.water;
                break;
            case "rock":
                drawableID = R.drawable.rock;
                break;
            case "flying":
                drawableID = R.drawable.flying;
                break;
            default:
                drawableID = 0;
                break;
        }
        return drawableID;
    }
}
