package com.creamcode.afterorbefore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.creamcode.afterorbefore.Interfaces.GamePresenter;
import com.creamcode.afterorbefore.Interfaces.GameView;
import com.creamcode.afterorbefore.Presenters.GamePresenterImp;
import com.creamcode.afterorbefore.Views.GlockFragment;
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

public class GameActivity extends AppCompatActivity implements GameView, PictureFragment.PictureFragmentInterface, GlockFragment.GlockInterface {

    GamePresenter gamePresenter;
    PictureFragment pictureFragmentA, pictureFragmentB;
    GlockFragment glockFragment;

    TextView tv_question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        FullScreencall();

        tv_question = findViewById(R.id.tv_question);

        gamePresenter = new GamePresenterImp(this);
        gamePresenter.getIds();


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

        glockFragment = new GlockFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_picture_a,pictureFragmentA).replace(R.id.fl_picture_b,pictureFragmentB).replace(R.id.fl_glock,glockFragment).commit();

    }

    //this method is called by each fragment by a interface and here its implemented
    @Override
    public void checkAnswerCountry(String id) {
        //llevando la logica al presenter
        gamePresenter.checkAnswerPresenter(pictureFragmentA, pictureFragmentB, id);

    }


    @Override
    public void checkNameFlagAnswer(boolean answer, String id) {
        gamePresenter.flagNameAnswer(answer,id,pictureFragmentA,pictureFragmentB);
    }

    @Override
    public void loadNextPicture(PictureFragment pictureFragment, String id) {
        pictureFragment.cargarFoto(id);
    }

    @Override
    public void loadQuestion(String question) {
        tv_question.setText(question);
    }

    @Override
    public void changeTime(String timeType) {
        if(timeType.equals("Photo")){
           glockFragment.resetTimePhoto();
        }
        if(timeType.equals("Flag")){
            glockFragment.resetTimeflags();
        }
    }

    @Override
    public void killTime() {
        glockFragment.cancelTimer();
    }


    public void FullScreencall() {
        if(Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if(Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    @Override
    public void tiempoTermino() {
        gamePresenter.finishGame(0);
    }
    //ActivityLifeCycle
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        killTime();
    }

    @Override
    protected void onStop() {
        super.onStop();
        killTime();
        finish();
    }
}