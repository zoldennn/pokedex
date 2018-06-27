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

        JsonArray textEntries = json.getAsJsonObject().get("flavor_text_entries").getAsJsonArray();
        JsonObject textEntriesPosition = textEntries.get(3).getAsJsonObject();
        String pokemonDescription = textEntriesPosition.getAsJsonObject().get("flavor_text").getAsString();

        JsonObject habitat = json.getAsJsonObject().get("habitat").getAsJsonObject();
        String habitatName = habitat.get("name").getAsString();

        String captureRate = json.getAsJsonObject().get("capture_rate").getAsString();

        return new PokemonDetails(pokemonDescription, habitatName, captureRate);
    }
}
