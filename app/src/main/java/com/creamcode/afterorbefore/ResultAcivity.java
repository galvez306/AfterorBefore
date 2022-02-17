package com.creamcode.afterorbefore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ResultAcivity extends AppCompatActivity {

    private ImageView btnAgain;
    private ListView listViewScores;

    private SharedPreferences sharedPreferences;

    private TextView tv_prueba;
    private int score = 0;
    ArrayList<String[]> namesAndScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_acivity);

        btnAgain = findViewById(R.id.btn_again);
        listViewScores = findViewById(R.id.lstv_scores);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String [] nameScore = {sharedPreferences.getString("nameUno","no name"),"10"};
        String [] nameScoreDos = {sharedPreferences.getString("nameDos","no name"),"10"};

        tv_prueba = findViewById(R.id.tv_result);
        score = getIntent().getExtras().getInt("SCORE");
        tv_prueba.setText("tu puntaje es de: "+score);

        namesAndScores = new ArrayList<String[]>();
        namesAndScores.add(nameScore);
        namesAndScores.add(nameScore);
        namesAndScores.add(nameScore);
        namesAndScores.add(nameScore);
        namesAndScores.add(nameScore);
        namesAndScores.add(nameScore);

        scores();


        btnAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultAcivity.this,GameActivity.class);
                startActivity(intent);
            }
        });

    }

    private void scores(){
        ScoreAdapter adapter = new ScoreAdapter(this,namesAndScores);
        listViewScores.setAdapter(adapter);
    }
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}