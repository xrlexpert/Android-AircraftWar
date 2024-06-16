package com.example.aircraftwar2024.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.aircraftwar2024.R;

public class OfflineGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_offline_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //获取是否开启音乐
        boolean musicOn = getIntent().getBooleanExtra("music",false);

        Button easyMode = findViewById(R.id.easyMode);
        Button normalMode = findViewById(R.id.normalMode);
        Button hardMode = findViewById(R.id.hardMode);
        easyMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OfflineGameActivity.this,GameActivity.class);
                intent.putExtra("gameType",0);
                intent.putExtra("musicOn",musicOn);
                startActivity(intent);
            }
        });
        normalMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OfflineGameActivity.this,GameActivity.class);
                intent.putExtra("gameType",1);
                intent.putExtra("musicOn",musicOn);
                startActivity(intent);
            }
        });
        hardMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OfflineGameActivity.this,GameActivity.class);
                intent.putExtra("gameType",2);
                intent.putExtra("musicOn",musicOn);
                startActivity(intent);

            }
        });

        Button backHome = findViewById(R.id.backHome);
        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OfflineGameActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });


    }
}