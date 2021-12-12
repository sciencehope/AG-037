package com.crazy4dev.ag_037;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.crazy4dev.ag_037.databinding.ActivityMainBinding;
import com.github.mikephil.charting.charts.PieChart;
import com.hbb20.CountryCodePicker;

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
    private List<ModelUpdate> modelUpdateList;
    private List<ModelUpdate> modelUpdateList2;
    PieChart mPieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bindView();

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
                fetchData();
            }
        });

        fetchData();
    }

    private void fetchData() {
        ApiUtilities.getAPIInterface().getCountryData().enqueue(new Callback<List<ModelUpdate>>() {
            @Override
            public void onResponse(Call<List<ModelUpdate>> call, Response<List<ModelUpdate>> response) {
                modelUpdateList.addAll(response.body());
                for (int i=0; i<modelUpdateList.size(); i++){
                    if(modelUpdateList.get(i).getCountry().equals(country)){
                        mActive.setText(modelUpdateList.get(i).getActive());
                        mDeaths.setText(modelUpdateList.get(i).getDeaths());
                        mRecovered.setText(modelUpdateList.get(i).getRecovered());
                        mTodayCases.setText(modelUpdateList.get(i).getTodayCases());

                        updategraph(modelUpdateList.get(i).getCases(),
                                modelUpdateList.get(i).getActive(),
                                modelUpdateList.get(i).getRecovered(),
                                modelUpdateList.get(i).getDeaths());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ModelUpdate>> call, Throwable t) {

            }
        });
    }

    private void updategraph(String cases, String active, String recovered, String deaths) {

    }

    private void bindView() {
        confirmedBtn = binding.confirm;
        mActive = binding.active;
        mDeaths = binding.deaths;
        mRecovered = binding.recovered;
        mTodayCases = binding.todayCases;
//        mPieChart = binding.confirmedCasesChart;
        countryCodePicker = binding.countryList;
        modelUpdateList = new ArrayList<>();
        modelUpdateList2 = new ArrayList<>();
    }


}