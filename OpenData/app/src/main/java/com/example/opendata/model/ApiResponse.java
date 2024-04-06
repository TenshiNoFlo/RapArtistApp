package com.example.opendata.model;

import java.util.List;

public class ApiResponse {
    private int totalCount;
    private List<RapArtist> results;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<RapArtist> getResults() {
        return results;
    }

    public void setResults(List<RapArtist> results) {
        this.results = results;
    }
}