package com.example.camilo.pokedex.deserializers;

import com.example.camilo.pokedex.models.PokemonDetails;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class PokemonDetailsDeserializer implements JsonDeserializer<PokemonDetails> {
    @Override
    public PokemonDetails deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonArray forms = json.getAsJsonObject().get("flavor_text_entries").getAsJsonArray();
        JsonObject posicion = forms.get(3).getAsJsonObject();
        //JsonElement urll = forms.get(0);
        String description = posicion.getAsJsonObject().get("flavor_text").getAsString();

        //JsonObject speciesObject = json.getAsJsonObject().get("flavor_text").getAsJsonObject();
        //String urlSpecie = speciesObject.get("name").getAsString();

        return new PokemonDetails(description);
    }
}
