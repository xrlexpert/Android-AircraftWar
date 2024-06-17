package com.example.aircraftwar2024.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.aircraftwar2024.R;

public class OnlineResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_online_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        int score = 0,opscore = 0;
        if(getIntent() != null){
            score = getIntent().getIntExtra("score",0);
            opscore = getIntent().getIntExtra("opscore",0);
        }
        TextView score_text = findViewById(R.id.online_myscore);
        TextView opscore_text = findViewById(R.id.online_opscore);
        score_text.setText(Integer.toString(score));
        opscore_text.setText(Integer.toString(opscore));
        Button backHome = findViewById(R.id.online_backHome);
        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnlineResultActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}