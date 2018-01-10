package com.example.camilo.pokedex.models;


public class Pokemon {

    public static int number;
    private String name;
    private String url;
    private String type;
    private String type2;
    private int hp;
    private int atk;
    private int def;
    private int hei;
    private int wid;
    private int spd;
    private int exp;
    private int sdf;
    private int satk;

    public Pokemon(int number, String name, String url, String type, String type2, int hp, int atk, int def, int hei, int wid, int spd, int exp, int sdf, int satk) {
        Pokemon.number = number;
        this.name = name;
        this.url = url;
        this.type = type;
        this.type2 = type2;
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.hei = hei;
        this.wid = wid;
        this.spd = spd;
        this.exp = exp;
        this.sdf = sdf;
        this.satk = satk;
    }

    public int getNumber() {
        String[] urlPartes = url.split("/");
        return Integer.parseInt(urlPartes[urlPartes.length-1]);
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getHei() {
        return hei;
    }

    public void setHei(int hei) {
        this.hei = hei;
    }

    public int getWid() {
        return wid;
    }

    public void setWid(int wid) {
        this.wid = wid;
    }

    public int getSpd() {
        return spd;
    }

    public void setSpd(int spd) {
        this.spd = spd;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getSdf() {
        return sdf;
    }

    public void setSdf(int sdf) {
        this.sdf = sdf;
    }

    public int getSatk() {
        return satk;
    }

    public void setSatk(int satk) {
        this.satk = satk;
    }

    /*    String[] urlPartes = url.split("/");
    return Integer.parseInt(urlPartes[urlPartes.length-1]);*/
}
