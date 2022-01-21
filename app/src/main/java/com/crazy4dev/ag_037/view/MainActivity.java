package com.crazy4dev.ag_037.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.crazy4dev.ag_037.ApiInterface;
import com.crazy4dev.ag_037.ApiUtilities;
import com.crazy4dev.ag_037.DetailsActivity;
import com.crazy4dev.ag_037.model.CountriesUpdateModel;
import com.crazy4dev.ag_037.databinding.ActivityMainBinding;
import com.crazy4dev.ag_037.model.HistoricalModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.hbb20.CountryCodePicker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    ConstraintLayout confirmedBtn;

    CountryCodePicker countryCodePicker;
    TextView mCountry, mCases, mTodayCases, mDeaths, mTodayDeaths, mRecovered, mTodayRecovered, mActive,
            mCritical, mTests, mPopulation;
    String country;
    String[] types = {"cases", "deaths", "recovered", "active"};
    ArrayList<Integer> cases;
    ArrayList<Integer> deaths;
    ArrayList<Integer> recovered;
    LineChart mPieChart;
    Button findVaccine;
    JSONObject timelines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bindView();

        findVaccine.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, VaccineFinderActivity.class);
            startActivity(intent);
        });

        confirmedBtn.setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
            startActivity(intent);

        });

        countryCodePicker.setAutoDetectedCountry(true);
        country = countryCodePicker.getSelectedCountryName();

        countryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                country = countryCodePicker.getSelectedCountryName();
                fetchHistoricalData(country);
                fetchCountryData(country);
            }
        });

        fetchHistoricalData(country);
        fetchCountryData(country);
    }

    private void fetchHistoricalData(String country) {
        ApiUtilities.getAPIInterface().getHistoricalData(country).enqueue(new Callback<HistoricalModel>() {
            @Override
            public void onResponse(Call<HistoricalModel> call, Response<HistoricalModel> response) {
//                cases.addAll(response.body().getCases());

//                timelines = response.body().getTimeline();
                Log.i("Tag", "Request data country= "+ country + " Body =" +
                        response.body().getCases().toString());
//                System.out.println("Historic " + historicalModel);
//                LineDataSet lineCasesDataSet = new LineDataSet(cases, "Cases historic");
//                ArrayList<ILineDataSet> caseDataSet = new ArrayList<>();
//                caseDataSet.add(lineCasesDataSet);
//                LineData data = new LineData(caseDataSet);
//                mPieChart.setData(data);
//                mPieChart.invalidate();

//                deaths.addAll(response.body().getDeaths());
//                LineDataSet lineDeathsDataSet = new LineDataSet(deaths, "Deaths historic");
//                recovered.addAll(response.body().getRecovered());
//                LineDataSet linerecoveredDataSet = new LineDataSet(recovered, "Recovered historic");
            }

            @Override
            public void onFailure(Call<HistoricalModel> call, Throwable t) {

            }
        });
    }


    private void fetchCountryData(String country) {
        ApiUtilities.getAPIInterface().getCountryData(country).enqueue(new Callback<CountriesUpdateModel>() {
            @Override
            public void onResponse(Call<CountriesUpdateModel> call, Response<CountriesUpdateModel> response) {
                mActive.setText(response.body().getActive());
                mDeaths.setText(response.body().getDeaths());
                mRecovered.setText(response.body().getRecovered());
                mTodayCases.setText(response.body().getTodayCases());
//                Log.i("Tag", "Request data country= "+ country + " Body =" + response.body());
            }

            @Override
            public void onFailure(Call<CountriesUpdateModel> call, Throwable t) {

            }
        });
    }


    private void updategraph(String cases, String active, String recovered, String deaths) {

    }

    private void bindView() {
        findVaccine = binding.findVaccine;
        confirmedBtn = binding.confirm;
        mActive = binding.active;
        mDeaths = binding.deaths;
        mRecovered = binding.recovered;
        mTodayCases = binding.todayCases;
        mPieChart = binding.confirmedCasesChart;
        countryCodePicker = binding.countryList;
        cases = new ArrayList<>();
        deaths = new ArrayList<>();
        recovered = new ArrayList<>();
    }


}