package com.example.aircraftwar2024.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.aircraftwar2024.R;
import com.example.aircraftwar2024.scores.ScoreDaoImpl;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button singlePlayMode = findViewById(R.id.Single_Player_Mode);
        RadioGroup musicSelect = findViewById(R.id.musicSelect);
        RadioButton musicOn = findViewById(R.id.musicOn);
        RadioButton musicOff = findViewById(R.id.musicOff);
        musicOff.setChecked(true);
        singlePlayMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean music = false;
                if(musicOn.isChecked()){
                    music = true;
                }
                Intent intent = new Intent(MainActivity.this, OfflineActivity.class);
                intent.putExtra("music", music);
                startActivity(intent);
            }
        });


    }

}