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

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class ResultAcivity extends AppCompatActivity {

    private ImageView btnAgain;
    private ListView listViewScores;

    private SharedPreferences sharedPreferences;

    private TextView tv_prueba;
    private int score = 0;

    ArrayList<Score> scores = new ArrayList<>();

    class Score {
        String name;
        int points;

        public Score(String name, int points) {
            this.name = name;
            this.points = points;
        }

        @Override
        public String toString() {
            return "NOMBREE "+name+"PUNTOS "+points;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_acivity);

        btnAgain = findViewById(R.id.btn_again);
        listViewScores = findViewById(R.id.lstv_scores);

        tv_prueba = findViewById(R.id.tv_result);
        score = getIntent().getExtras().getInt("SCORE");
        tv_prueba.setText("tu puntaje es de: "+score);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Gson gson = new Gson();

        Score userOne = gson.fromJson(sharedPreferences.getString("user1",""),Score.class);
        Score userTwo = gson.fromJson(sharedPreferences.getString("user2",""),Score.class);
        Score userThree = gson.fromJson(sharedPreferences.getString("user3",""),Score.class);
        Score userFour = gson.fromJson(sharedPreferences.getString("user4",""),Score.class);
        Score userFive = gson.fromJson(sharedPreferences.getString("user5",""),Score.class);

        /*scores.add(userOne);
        scores.add(userTwo);
        scores.add(userThree);
        scores.add(userFour);
        scores.add(userFive);*/

        Score edy = new Score("Eduardo",7);
        Score gaby = new Score("Gabriela",5);
        Score ramona = new Score("Ramona",10);
        Score calcetas = new Score("Calcetungas",2);
        Score chispa = new Score("Chispa",12);

        scores.add(edy);
        scores.add(gaby);
        scores.add(ramona);
        scores.add(calcetas);
        scores.add(chispa);
        scores();

        //ordenar el arreglo scores y mandarlos al adapter ya en orden, o talvez en el adapter tener la logica para acomodarlos


        /*Score edy = new Score("Eduardo",7);
        Score gaby = new Score("Gabriela",5);
        Score ramona = new Score("Ramona",10);
        Score calcetas = new Score("Calcetungas",2);
        Score chispa = new Score("Chispa",12);

        Gson gson = new Gson();

        String json1 = gson.toJson(edy);
        String json2 = gson.toJson(gaby);
        String json3 = gson.toJson(ramona);
        String json4 = gson.toJson(calcetas);
        String json5 = gson.toJson(chispa);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user1",json1);
        editor.putString("user2",json2);
        editor.putString("user3",json3);
        editor.putString("user4",json4);
        editor.putString("user5",json5);
        editor.apply();

        String impresion="";
        impresion +=sharedPreferences.getString("user1","");
        impresion +=sharedPreferences.getString("user2","");
        impresion +=sharedPreferences.getString("user3","");
        impresion +=sharedPreferences.getString("user4","");
        impresion +=sharedPreferences.getString("user5","");

        System.out.println(impresion);

        Score vuletodelfuturo = gson.fromJson(json3,Score.class);
        System.out.println(vuletodelfuturo.toString());*/

       /* namesAndScores = new ArrayList<String[]>();
        namesAndScores.add(nameScore);
        namesAndScores.add(nameScore);
        namesAndScores.add(nameScore);
        namesAndScores.add(nameScore);
        namesAndScores.add(nameScore);
        namesAndScores.add(nameScore);

        scores();*/


        btnAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultAcivity.this,GameActivity.class);
                startActivity(intent);
            }
        });

    }

    private void scores(){
        ScoreAdapter adapter = new ScoreAdapter(this,scores);
        listViewScores.setAdapter(adapter);
    }
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}