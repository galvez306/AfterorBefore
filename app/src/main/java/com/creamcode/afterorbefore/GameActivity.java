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

    TextView tv_question;

    //
    Button btnCambiar;
    //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        tv_question = findViewById(R.id.tv_question);

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

    //this method is called by each fragment by a interface and here its implemented
    @Override
    public void checkAnswer(String id) {
        //llevando la logica al presenter
        gamePresenter.checkAnswerPresenter(pictureFragmentA, pictureFragmentB, id);

    }

    @Override
    public void loadNextPicture(PictureFragment pictureFragment, String id) {
        pictureFragment.cargarFoto(id);
    }

    @Override
    public void loadQuestion(String question) {
        tv_question.setText(question);
    }

}