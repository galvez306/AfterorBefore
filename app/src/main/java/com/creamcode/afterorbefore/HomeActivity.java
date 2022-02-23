package com.creamcode.afterorbefore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class HomeActivity extends AppCompatActivity {

    private Button btnStart;
    private ImageView btnScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnStart = findViewById(R.id.btn_start);
        btnScores = findViewById(R.id.btn_scores);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,GameActivity.class);
                startActivity(intent);
            }
        });
        btnScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,ResultAcivity.class);
                intent.putExtra("SCORE",-1);
                startActivity(intent);
            }
        });
    }
}