package com.example.camilo.pokedex.deserializers;

import com.example.camilo.pokedex.models.PokemonDetails;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class PokemonDetailsDeserializer implements JsonDeserializer<PokemonDetails> {
    @Override
    public PokemonDetails deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject speciesObject = json.getAsJsonObject().get("species").getAsJsonObject();
        String urlSpecie = speciesObject.get("url").getAsString();

        return new PokemonDetails(urlSpecie);
    }
}
