package com.example.camilo.pokedex.deserializers;

import com.example.camilo.pokedex.models.Pokemon;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class Deserializer implements JsonDeserializer<Pokemon> {
    @Override
    public Pokemon deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        // NOTA IMPORTANTE: ESTA API TIENE NO SOLO JSON OBJECTS SINO QUE TAMBIÉN JSON ARRAYS

        int number = Pokemon.number;
        JsonArray forms = json.getAsJsonObject().get("forms").getAsJsonArray();
        JsonElement urll = forms.get(0);
        String url = urll.getAsJsonObject().get("url").getAsString();

        String name = json.getAsJsonObject().get("name").getAsString();
        JsonArray jsonArray2 = json.getAsJsonObject().get("types").getAsJsonArray();
        JsonElement types = jsonArray2.get(0);
        String type = types.getAsJsonObject().get("type").getAsJsonObject().get("name").getAsString();

        //REVISAR QUE EL POKEMON TENGA 2DO TIPO
        String type2= "asd";
        try {
            if(jsonArray2.size()>0)
            {
                JsonElement types2 = jsonArray2.get(1);
                type2 = types2.getAsJsonObject().get("type").getAsJsonObject().get("name").getAsString();
            }
        }catch (Exception e){type2="null";}

        //OBTENER ARRAY DE STATS
        JsonArray stats = json.getAsJsonObject().get("stats").getAsJsonArray();

        //OBTENER DIFERENTES ELEMENTOS DE STATS
        JsonElement speed = stats.get(0);
        int spd = speed.getAsJsonObject().get("base_stat").getAsInt();

        JsonElement special_defense = stats.get(1);
        int sdf = special_defense.getAsJsonObject().get("base_stat").getAsInt();

        JsonElement special_attack = stats.get(2);
        int satk = special_attack.getAsJsonObject().get("base_stat").getAsInt();

        JsonElement defense = stats.get(3);
        int def = defense.getAsJsonObject().get("base_stat").getAsInt();

        JsonElement attack = stats.get(4);
        int atk = attack.getAsJsonObject().get("base_stat").getAsInt();

        JsonElement hps = stats.get(5);
        int hp = hps.getAsJsonObject().get("base_stat").getAsInt();

        int weight = json.getAsJsonObject().get("weight").getAsInt();

        int height = json.getAsJsonObject().get("height").getAsInt();

        int exp = json.getAsJsonObject().get("base_experience").getAsInt();

        return new Pokemon(number,name,url,type,type2,hp,atk,def,height,weight,spd,exp,sdf,satk);

    }
}