package com.example.camilo.pokedex.models;

class PokemonSpritesDto {

    // TODO: Property @back_female, @front_female, @back_shiny_female, @front_shiny_female

    private String back_default;
    private String back_shiny;
    private String front_default;
    private String front_shiny;

    // GETTERS

    public String getBack_default() {
        return back_default;
    }

    public String getBack_shiny() {
        return back_shiny;
    }

    public String getFront_default() {
        return front_default;
    }

    public String getFront_shiny() {
        return front_shiny;
    }

    // SETTERS

    public void setBack_default(String back_default) {
        this.back_default = back_default;
    }

    public void setBack_shiny(String back_shiny) {
        this.back_shiny = back_shiny;
    }

    public void setFront_default(String front_default) {
        this.front_default = front_default;
    }

    public void setFront_shiny(String front_shiny) {
        this.front_shiny = front_shiny;
    }
}
