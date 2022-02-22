package com.creamcode.afterorbefore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class ResultAcivity extends AppCompatActivity {

    private ImageView btnAgain;
    private ListView listViewScores;

    private SharedPreferences sharedPreferences;
    private Gson gson;

    private boolean hasNull = false, newRecord = false;
    private int gameScore = 0;

    ArrayList<Score> scores = new ArrayList<>();

    class Score {
        String name;
        int points;

        public Score(String name, int points) {
            this.name = name;
            this.points = points;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_acivity);

        btnAgain = findViewById(R.id.btn_again);
        listViewScores = findViewById(R.id.lstv_scores);

        gameScore = getIntent().getExtras().getInt("SCORE");

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        gson = new Gson();
        Score plyr = new Score(null,-1);
        String playDefault = gson.toJson(plyr);

        //Getting the leaderboard
        Score userOne = gson.fromJson(sharedPreferences.getString("user1",playDefault),Score.class);
        Score userTwo = gson.fromJson(sharedPreferences.getString("user2",playDefault),Score.class);
        Score userThree = gson.fromJson(sharedPreferences.getString("user3",playDefault),Score.class);
        Score userFour = gson.fromJson(sharedPreferences.getString("user4",playDefault),Score.class);
        Score userFive = gson.fromJson(sharedPreferences.getString("user5",playDefault),Score.class);

        scores = new ArrayList<>(Arrays.asList(userOne,userTwo,userThree,userFour,userFive));

        //Reading the array to see if this is one o the 5 first times the user play the game
        for(int i=0; i<scores.size(); i++){
            if(scores.get(i).name==null){
                hasNull = true;
            }else{
                hasNull = false;
            }
        }
        if(hasNull){
            //At least, there ir a null score in the array
            newScore();
        }else{
            //There is no one null score in the array
            //If the game score is higher than one of the array, show the dialog
            for(int i=0; i<scores.size(); i++){
                if(gameScore >=scores.get(i).points){
                    newRecord = true;
                    newScore();
                    break;
                }
            }
            if(!newRecord){
                getScores();
            }
        }

        btnAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultAcivity.this,GameActivity.class);
                startActivity(intent);
            }
        });

    }
    public void newScore(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ResultAcivity.this);
        View customLayout = getLayoutInflater().inflate(R.layout.dialog_new_score, null);
        alertDialog.setView(customLayout);
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(true);
        alert.show();
        alert.setOnCancelListener(
                new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        getScores();
                    }
                }
        );
        EditText edtName = customLayout.findViewById(R.id.edt_name);
        Button submit = customLayout.findViewById(R.id.btn_new_score);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString().trim();
                if(name.equals("")){
                    Toast.makeText(ResultAcivity.this, "Name can not be empty", Toast.LENGTH_SHORT).show();
                }else{
                    if(name.length()>14){
                        Toast.makeText(ResultAcivity.this, "Name too large", Toast.LENGTH_SHORT).show();
                    }else{
                        Score sc = new Score(name, gameScore);
                        scores.add(sc);
                        getScores();
                        alert.dismiss();
                    }
                }
            }
        });

    }
    private void getScores(){
        sortScores(scores);

        String json1 = gson.toJson(scores.get(0));
        String json2 = gson.toJson(scores.get(1));
        String json3 = gson.toJson(scores.get(2));
        String json4 = gson.toJson(scores.get(3));
        String json5 = gson.toJson(scores.get(4));

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user1",json1);
        editor.putString("user2",json2);
        editor.putString("user3",json3);
        editor.putString("user4",json4);
        editor.putString("user5",json5);
        editor.apply();

        ScoreAdapter adapter = new ScoreAdapter(this,scores);
        listViewScores.setAdapter(adapter);
    }

    public void sortScores(ArrayList<Score> arrayList){
        try {
            Collections.sort(arrayList, (s1, s2) ->
                    Integer.compare(s2.points, s1.points));
            arrayList.remove(5);
        }catch (Exception e){

        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}