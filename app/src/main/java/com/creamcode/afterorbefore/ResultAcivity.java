package com.creamcode.afterorbefore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ResultAcivity extends AppCompatActivity {

    private TextView tv_prueba;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_acivity);
        tv_prueba = findViewById(R.id.tv_result);
        score = getIntent().getExtras().getInt("SCORE");
        tv_prueba.setText("tu puntaje es de: "+score);


    }
}