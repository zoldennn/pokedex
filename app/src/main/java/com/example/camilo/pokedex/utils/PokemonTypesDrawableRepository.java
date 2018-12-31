package com.example.camilo.pokedex.utils;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;

import com.example.camilo.pokedex.R;

import java.util.HashMap;
import java.util.Map;

public class PokemonTypesDrawableRepository {

    private static Map<String, Integer> articleMapOne;
    static {
        articleMapOne = new HashMap<>();
        articleMapOne.put("bug", R.drawable.bug);
        articleMapOne.put("dark", R.drawable.dark);
        articleMapOne.put("dragon", R.drawable.dragon);
        articleMapOne.put("electric", R.drawable.electric);
        articleMapOne.put("fairy", R.drawable.fairy);
        articleMapOne.put("fighting", R.drawable.fighting);
        articleMapOne.put("fire", R.drawable.fire);
        articleMapOne.put("ghost", R.drawable.ghost);
        articleMapOne.put("ground", R.drawable.ground);
        articleMapOne.put("ice", R.drawable.ice);
        articleMapOne.put("normal", R.drawable.normal);
        articleMapOne.put("poison", R.drawable.poison);
        articleMapOne.put("psychic", R.drawable.psychic);
        articleMapOne.put("steel", R.drawable.steel);
        articleMapOne.put("water", R.drawable.water);
        articleMapOne.put("flying", R.drawable.flying);
    }

    @DrawableRes
    public static int getTypeResourceId(String typeName) {
        return articleMapOne.get(typeName);
    }
}
