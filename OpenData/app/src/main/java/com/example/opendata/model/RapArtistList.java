package com.example.opendata.model;

import java.util.ArrayList;
import java.util.List;

public class RapArtistList {
    private List<RapArtist> rapArtists;

    public RapArtistList() {
        rapArtists = new ArrayList<>();
    }

    public List<RapArtist> getRapArtists() {
        return rapArtists;
    }

    public void setRapArtists(List<RapArtist> rapArtists) {
        this.rapArtists = rapArtists;
    }

    public void addRapArtist(RapArtist rapArtist) {
        rapArtists.add(rapArtist);
    }

    public void removeRapArtist(RapArtist rapArtist) {
        rapArtists.remove(rapArtist);
    }

    public RapArtist getRapArtistByName(String name) {
        for (RapArtist rapArtist : rapArtists) {
            if (rapArtist.getName().equals(name)) {
                return rapArtist;
            }
        }
        return null;
    }
}

