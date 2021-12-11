package com.example.ag_037;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ag_037.databinding.ActivityMainBinding;


public class Details extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }


}