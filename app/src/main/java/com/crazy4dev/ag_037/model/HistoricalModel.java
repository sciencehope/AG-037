package com.crazy4dev.ag_037.model;

import org.json.JSONObject;

import java.util.List;

public class HistoricalModel {
    private String country;
    private JSONObject timeline;
    private String cases;
    private List<Integer> deaths;
    private List<Integer> recovered;

    public HistoricalModel() {
    }

    public HistoricalModel(String country, String cases, List<Integer> deaths,
                           List<Integer> recovered, JSONObject timeline) {
        this.country = country;
        this.cases = cases;
        this.deaths = deaths;
        this.recovered = recovered;
        this.timeline = timeline;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }

    public List<Integer> getDeaths() {
        return deaths;
    }

    public void setDeaths(List<Integer> deaths) {
        this.deaths = deaths;
    }

    public List<Integer> getRecovered() {
        return recovered;
    }

    public void setRecovered(List<Integer> recovered) {
        this.recovered = recovered;
    }

    public JSONObject getTimeline() {
        return timeline;
    }

    public void setTimeline(JSONObject timeline) {
        this.timeline = timeline;
    }
}
