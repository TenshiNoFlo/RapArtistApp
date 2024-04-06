package com.example.opendata.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RapArtist implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("categories")
    @Expose
    private List<String> categories;
    @SerializedName("bio_url")
    @Expose
    private String bioUrl;
    @SerializedName("bio_yearsactivestart")
    @Expose
    private String bioYearsactivestart;
    @SerializedName("bio_birthdate")
    @Expose
    private String bioBirthdate;
    @SerializedName("bio_summary")
    @Expose
    private String bioSummary;
    @SerializedName("bio_yearsactiveend")
    @Expose
    private String bioYearsactiveend;
    @SerializedName("bio_deathdate")
    @Expose
    private String bioDeathdate;
    @SerializedName("youtube_clipexampleurl")
    @Expose
    private String youtubeClipexampleurl;
    @SerializedName("location_city")
    @Expose
    private String locationCity;
    @SerializedName("location_neighborhood")
    @Expose
    private Object locationNeighborhood;
    @SerializedName("location_coordinates")
    @Expose
    private LocationCoordinates locationCoordinates;

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    private boolean isFavorite;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getBioUrl() {
        return bioUrl;
    }

    public void setBioUrl(String bioUrl) {
        this.bioUrl = bioUrl;
    }

    public String getBioYearsactivestart() {
        return bioYearsactivestart;
    }

    public void setBioYearsactivestart(String bioYearsactivestart) {
        this.bioYearsactivestart = bioYearsactivestart;
    }

    public String getBioBirthdate() {
        return bioBirthdate;
    }

    public void setBioBirthdate(String bioBirthdate) {
        this.bioBirthdate = bioBirthdate;
    }

    public String getBioSummary() {
        return bioSummary;
    }

    public void setBioSummary(String bioSummary) {
        this.bioSummary = bioSummary;
    }

    public Object getBioYearsactiveend() {
        return bioYearsactiveend;
    }

    public void setBioYearsactiveend(String bioYearsactiveend) {
        this.bioYearsactiveend = bioYearsactiveend;
    }

    public Object getBioDeathdate() {
        return bioDeathdate;
    }

    public void setBioDeathdate(String bioDeathdate) {
        this.bioDeathdate = bioDeathdate;
    }

    public String getYoutubeClipexampleurl() {
        return youtubeClipexampleurl;
    }

    public void setYoutubeClipexampleurl(String youtubeClipexampleurl) {
        this.youtubeClipexampleurl = youtubeClipexampleurl;
    }

    public String getLocationCity() {
        return locationCity;
    }

    public void setLocationCity(String locationCity) {
        this.locationCity = locationCity;
    }

    public Object getLocationNeighborhood() {
        return locationNeighborhood;
    }

    public void setLocationNeighborhood(Object locationNeighborhood) {
        this.locationNeighborhood = locationNeighborhood;
    }

    public LocationCoordinates getLocationCoordinates() {
        return locationCoordinates;
    }

    public void setLocationCoordinates(LocationCoordinates locationCoordinates) {
        this.locationCoordinates = locationCoordinates;
    }

}