package com.example.camilo.pokedex.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.VisibleForTesting;

public class PokeResponse implements Parcelable {

    private String name;
    private String url;

    protected PokeResponse(Parcel in) {
        name = in.readString();
        url = in.readString();
    }

    // GETTERS
    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public int getId() {
        String[] urlParts = url.split("/");
        return Integer.parseInt(urlParts[urlParts.length - 1]);
    }

    //SETTERS
    @VisibleForTesting
    public void setName(String name) {
        this.name = name;
    }

    @VisibleForTesting
    public void setUrl(String url) {
        this.url = url;
    }

    public static final Creator<PokeResponse> CREATOR = new Creator<PokeResponse>() {
        @Override
        public PokeResponse createFromParcel(Parcel in) {
            return new PokeResponse(in);
        }

        @Override
        public PokeResponse[] newArray(int size) {
            return new PokeResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(url);
    }
}
