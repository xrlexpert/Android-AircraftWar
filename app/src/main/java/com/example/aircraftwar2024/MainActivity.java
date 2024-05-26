package com.example.aircraftwar2024;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RadioGroup musicSelect = findViewById(R.id.musicSelect);
        RadioButton musicOn = findViewById(R.id.musicOn);
        RadioButton musicOff = findViewById(R.id.musicOff);
        musicOff.setChecked(true);


    }

}