package com.creamcode.afterorbefore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

import com.creamcode.afterorbefore.Interfaces.GamePresenter;
import com.creamcode.afterorbefore.Interfaces.GameView;
import com.creamcode.afterorbefore.Presenters.GamePresenterImp;
import com.creamcode.afterorbefore.Views.GlockFragment;
import com.creamcode.afterorbefore.Views.PictureFragment;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class GameActivity extends AppCompatActivity implements GameView, PictureFragment.PictureFragmentInterface, GlockFragment.GlockInterface {

    GamePresenter gamePresenter;
    PictureFragment pictureFragmentA, pictureFragmentB;
    GlockFragment glockFragment;

    TextView tv_question;

    private MediaPlayer sound;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        FullScreencall();

        tv_question = findViewById(R.id.tv_question);

        gamePresenter = new GamePresenterImp(this);
        gamePresenter.getIds();

        //Initializing ad
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load((Context) this,"ca-app-pub-6105806325055299/6822608819", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;
                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                gamePresenter.finishGame();
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                mInterstitialAd = null;
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                mInterstitialAd = null;
                            }
                        });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        mInterstitialAd = null;
                    }
                });

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

    @Override
    public void playSound(String sound) {
        if(sound.equals("correct")){
            this.sound = MediaPlayer.create(this,R.raw.correcto);
        }else{
            this.sound = MediaPlayer.create(this,R.raw.incorrecto);
        }
        this.sound.start();
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
        if(question.equals("AFTER")){
            tv_question.setText(R.string.despues);
        }else{
            tv_question.setText(R.string.antes);
        }

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

    @Override
    public void loadAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd.show(GameActivity.this);
        } else {
            gamePresenter.finishGame();
        }
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
        playSound("incorrect");
        gamePresenter.finishGame();
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
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);// avisar reinicio
        } else {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
        Toast.makeText(this,R.string.reinicio,Toast.LENGTH_SHORT).show();
    }
}