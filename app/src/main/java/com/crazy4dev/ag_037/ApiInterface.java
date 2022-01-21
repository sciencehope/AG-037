package com.crazy4dev.ag_037;

import com.crazy4dev.ag_037.model.CountriesUpdateModel;
import com.crazy4dev.ag_037.model.HistoricalModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    String BASE_URL = "https://disease.sh/v3/";

    @GET("covid-19/countries/{country}")
    Call<CountriesUpdateModel> getCountryData(@Path("country") String country);

    @GET("covid-19/historical/{country}")
    Call<HistoricalModel> getHistoricalData(@Path("country") String country);

}
