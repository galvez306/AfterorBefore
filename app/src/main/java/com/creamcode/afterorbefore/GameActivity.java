package com.creamcode.afterorbefore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.creamcode.afterorbefore.Interfaces.GamePresenter;
import com.creamcode.afterorbefore.Interfaces.GameView;
import com.creamcode.afterorbefore.Presenters.GamePresenterImp;
import com.creamcode.afterorbefore.Views.PictureFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity implements GameView, PictureFragment.PictureFragmentInterface {

    GamePresenter gamePresenter;
    PictureFragment pictureFragmentA, pictureFragmentB;

    //
    Button btnCambiar;
    TextView tv_prueba;
    //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gamePresenter = new GamePresenterImp(this);
        gamePresenter.getIds();

        //
        btnCambiar = findViewById(R.id.btn_cambiar);
        btnCambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pictureFragmentA.cargarFoto("5");
            }
        });

        tv_prueba = findViewById(R.id.tv_prueba);

        //


    }

    @Override
    public void initializePicturesAB(String idA, String idB) {

        pictureFragmentA = new PictureFragment();
        Bundle bundleA = new Bundle();
        bundleA.putString("id",idA);
        pictureFragmentA.setArguments(bundleA);

        pictureFragmentB = new PictureFragment();
        Bundle bundleB = new Bundle();
        bundleB.putString("id",idB);
        pictureFragmentB.setArguments(bundleB);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_picture_a,pictureFragmentA).replace(R.id.fl_picture_b,pictureFragmentB).commit();

    }


    @Override
    public void checkAnswer(String id) {
        PictureFragment fragment = (PictureFragment) identifyFragment(id); //checar cual de los dos fragments esta llamando
        fragment.getYear();

        if(fragment==pictureFragmentA){
            //se trabaja con A
                /*if(fragment.getYear()>pictureFragmentB.getYear()){
                    Toast.makeText(this,"este es mayor",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,"este es menor",Toast.LENGTH_SHORT).show();
                }*/
            //ejemplo si solo nos interesara cual es mayor
            if(fragment.getYear()>pictureFragmentB.getYear()){
                Toast.makeText(this,"este es mayor",Toast.LENGTH_SHORT).show();
                gamePresenter.nextPicture(pictureFragmentA);
                //presenter-> cambiar este fragment, de todos modos en este punto sabemos que aqui
                //estamos hablando de pictureFragmentA
                //presenter.void next picture
            }
        }else{
            //se trabaja con B
            /*if(fragment.getYear()>pictureFragmentA.getYear()){
                Toast.makeText(this,"este es mayor",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"este es menor",Toast.LENGTH_SHORT).show();
            }*/
            //ejemplo si solo nos interesara cual es mayor
            if(fragment.getYear()>pictureFragmentA.getYear()){
                Toast.makeText(this,"este es mayor",Toast.LENGTH_SHORT).show();
                gamePresenter.nextPicture(pictureFragmentB);
            }
        }

    }

    @Override
    public void loadNextPictur(PictureFragment pictureFragment, String id) {
        pictureFragment.cargarFoto(id);
    }

    //metodo para recuperar el fragment que esta utilizando la interfaz
    public Fragment identifyFragment(String id){
        if(pictureFragmentA.getPictureId().equals(id)){
            return pictureFragmentA;
        }else{
            return pictureFragmentB;
        }
    }

}