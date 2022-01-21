package com.crazy4dev.ag_037.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.crazy4dev.ag_037.R;
import com.crazy4dev.ag_037.adapter.VaccinationInfoAdapter;
import com.crazy4dev.ag_037.model.VaccineModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class VaccineFinderActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    String baseURL = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByPin";
    private EditText areaPinCode;
    private Button forwardbtn;
    ProgressBar holdOnProgress;
    private ArrayList<VaccineModel> vaccination_centers;
    private RecyclerView resultRecyclerView;
    String areaPIN, avlDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_vaccine_finder);
        mapViews();
        onClickSetup();
    }

    private void onClickSetup() {
        forwardbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holdOnProgress.setVisibility(View.VISIBLE);
                DialogFragment dp = new PickDate();
                dp.show(getSupportFragmentManager(), "pick a date");
            }
        });
    }

    private void mapViews() {
        forwardbtn = findViewById(R.id.getResult);
        holdOnProgress = findViewById(R.id.progressBar);
        areaPinCode = findViewById(R.id.enterPinCode);
        resultRecyclerView = findViewById(R.id.recyclerView);
        vaccination_centers = new ArrayList<VaccineModel>();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar k = Calendar.getInstance();
        k.set(Calendar.YEAR, year);
        k.set(Calendar.MONTH, month);
        k.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
        dateFormat.setTimeZone(k.getTimeZone());
        String d = dateFormat.format(k.getTime());

        setup(d);
    }

    private void setup(String d) {
        avlDate = d;
        fetchDataNow();
    }

    private void fetchDataNow() {
        vaccination_centers.clear();
        areaPIN = areaPinCode.getText().toString();
        String url_api = baseURL + "pincode="+ areaPIN + "&date=" + avlDate;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_api, response -> {
            try {
                JSONObject object = new JSONObject(response);
                JSONArray sessionArray = object.getJSONArray("sessions");

                for (int i = 0; i < sessionArray.length(); i++) {
                    JSONObject sesObject = sessionArray.getJSONObject(i);
                    VaccineModel vaccineModel = new VaccineModel();
                    vaccineModel.setVaccineCenter(sesObject.getString("name"));
                    vaccineModel.setVaccinationCenterAddress(sesObject.getString("address"));
                    vaccineModel.setVaccinationTimings(sesObject.getString("from"));
                    vaccineModel.setVaccineCenterTime(sesObject.getString("to"));
                    vaccineModel.setVaccineName(sesObject.getString("vaccine"));
                    vaccineModel.setVaccinationCharges(sesObject.getString("fee_type"));
                    vaccineModel.setVaccinationAge(sesObject.getString("min_age_limit"));
                    vaccineModel.setVaccineAvailable(sesObject.getString("available"));
                    vaccination_centers.add(vaccineModel);
                }
                VaccinationInfoAdapter vaccinationInfoAdapter = new VaccinationInfoAdapter(getApplicationContext(), vaccination_centers);
                resultRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                holdOnProgress.setVisibility(View.INVISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                holdOnProgress.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplication(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}