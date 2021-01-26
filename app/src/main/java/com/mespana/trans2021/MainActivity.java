package com.mespana.trans2021;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.mespana.trans2021.databinding.ActivityMainBinding;
import com.mespana.trans2021.services.JsonParsingService;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        JsonParsingService.parseJson(this);
        setContentView(binding.getRoot());
    }
}