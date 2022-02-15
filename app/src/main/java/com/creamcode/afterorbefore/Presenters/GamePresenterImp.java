package com.creamcode.afterorbefore.Presenters;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.creamcode.afterorbefore.Interactors.GameInteractorImp;
import com.creamcode.afterorbefore.Interfaces.GameInteractor;
import com.creamcode.afterorbefore.Interfaces.GamePresenter;
import com.creamcode.afterorbefore.Interfaces.GameView;
import com.creamcode.afterorbefore.ResultAcivity;
import com.creamcode.afterorbefore.Views.PictureFragment;

import java.util.ArrayList;

public class GamePresenterImp implements GamePresenter {

    private GameView gameView;
    private GameInteractor gameInteractor;

    private int points = 0;
    private String turnType;
    private boolean second_opportunity = false;

    private ArrayList arrayPictures = new ArrayList<String>();

    public GamePresenterImp(GameView view){
        gameView = view;
        gameInteractor = new GameInteractorImp(this);

    }
    @Override
    public void getIds() {
        gameInteractor.getIds();
    }

    @Override
    public void setIds(ArrayList ids, String turnType) {
        arrayPictures = ids;
        this.turnType = turnType;
        //Sending the first two random idas to the view(Activity) and removing them
        gameView.loadQuestion(turnType);
        gameView.initializePicturesAB(arrayPictures.get(0).toString(),arrayPictures.get(1).toString());
        arrayPictures.remove(0);
        arrayPictures.remove(0);
    }

    @Override
    public void checkAnswerPresenter(PictureFragment pictureFragmentA, PictureFragment pictureFragmentB, String id) {
        PictureFragment fragmentActual;
        //Identifying wich fragment is calling through its id
        if(pictureFragmentA.getPictureId().equals(id)){
            fragmentActual =pictureFragmentA;
        }else{
            fragmentActual = pictureFragmentB;
        }
        if(fragmentActual==pictureFragmentA){//Ist fragmentA calling to this method
            if(turnType.equals("AFTER")){//Its After. The question is which picture is recenter
                if(fragmentActual.getYear()>pictureFragmentB.getYear()){
                    //Correct, Picture A year is bigger than picture B year
                    nextPicture(pictureFragmentA);
                }else{
                    //Incorrect
                    //Producing flags and blocking the other functions
                    if(!second_opportunity){
                        gameView.changeTime("Flag");
                        pictureFragmentA.lockPictureFuncionality(true);
                        pictureFragmentB.lockPictureFuncionality(true);
                        pictureFragmentA.cargarFlags();
                    }else{
                        Toast.makeText(pictureFragmentA.getContext(), "Perdiste", Toast.LENGTH_SHORT).show();
                    }
                }
            }else{//Its BEFORE. The question is which picture is older
                if(fragmentActual.getYear()<pictureFragmentB.getYear()){
                    //Correct, Picture A year is lower than picture B year
                    nextPicture(pictureFragmentA);
                }else{
                    //Incorrect
                    //Producing flags and blocking the other functions
                    if(!second_opportunity){
                        gameView.changeTime("Flag");
                        pictureFragmentA.lockPictureFuncionality(true);
                        pictureFragmentB.lockPictureFuncionality(true);
                        pictureFragmentA.cargarFlags();
                    }else{
                        Toast.makeText(pictureFragmentB.getContext(), "Perdiste", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }else{//Ist fragmentB calling to this method
            if(turnType.equals("AFTER")){//Its After. The question is which picture is recenter
                if(fragmentActual.getYear()>pictureFragmentA.getYear()){
                    //Correct, Picture B year is bigger than picture A year
                    nextPicture(pictureFragmentB);
                }else{
                    //Incorrect
                    //Producing flags and blocking the other functions
                    if(!second_opportunity){
                        gameView.changeTime("Flag");
                        pictureFragmentA.lockPictureFuncionality(true);
                        pictureFragmentB.lockPictureFuncionality(true);
                        pictureFragmentB.cargarFlags();
                    }else{
                        Toast.makeText(pictureFragmentB.getContext(), "Perdiste", Toast.LENGTH_SHORT).show();
                        finishGame(points);
                    }
                }
            }else{//Its BEFORE. The question is which picture is older
                if(fragmentActual.getYear()<pictureFragmentA.getYear()){
                    //Correct, Picture B year is lower than picture A year
                    nextPicture(pictureFragmentB);
                }else{
                    //Incorrect
                    //Producing flags and blocking the other functions
                    if(!second_opportunity){
                        gameView.changeTime("Flag");
                        pictureFragmentA.lockPictureFuncionality(true);
                        pictureFragmentB.lockPictureFuncionality(true);
                        pictureFragmentB.cargarFlags();
                    }else{
                        Toast.makeText(pictureFragmentB.getContext(), "Perdiste", Toast.LENGTH_SHORT).show();
                        finishGame(points);
                    }

                }
            }
        }

    }



    @Override
    public void flagNameAnswer(boolean answer, String id, PictureFragment pictureFragmentA, PictureFragment pictureFragmentB) {
        PictureFragment fragmentActual;
        //Identify which fragment is calling this method through its id
        if(pictureFragmentA.getPictureId().equals(id)){
            fragmentActual =pictureFragmentA;
        }else{
            fragmentActual = pictureFragmentB;
        }
        if(fragmentActual==pictureFragmentA){//Ist fragmentA calling to this method
            if(answer==true){
                //The answer given is correct, continue with the pictures left, and unlocking all the functionalities
                second_opportunity=true; //Advicing that the user has already failed before
                pictureFragmentA.flagsLayoutVisibility(false);
                pictureFragmentA.pictureOpacity(false);
                nextPicture(pictureFragmentA);
                pictureFragmentA.lockPictureFuncionality(false);
                pictureFragmentB.lockPictureFuncionality(false);
            }else{
                //Incorrect
                finishGame(points);
            }
        }else{//Ist fragmentB calling to this method
            if(answer==true){
                //The answer given is correct, continue with the pictures left, and unlocking all the functionalities
                second_opportunity=true;//Advicing that the user has already failed before
                pictureFragmentB.flagsLayoutVisibility(false);
                pictureFragmentB.pictureOpacity(false);
                nextPicture(pictureFragmentB);
                pictureFragmentA.lockPictureFuncionality(false);
                pictureFragmentB.lockPictureFuncionality(false);
            }else{
                //Incorrect
                finishGame(points);
            }

        }

    }

    @Override
    public void nextPicture(PictureFragment pictureFragment) {
        points++;
        if(arrayPictures.isEmpty()){

            Toast.makeText(pictureFragment.getContext(), "Ganaste", Toast.LENGTH_SHORT).show();
            finishGame(points);
        }else {
            gameView.changeTime("Photo");
            gameView.loadNextPicture(pictureFragment, arrayPictures.get(0).toString());
            arrayPictures.remove(0);
            this.turnType = gameInteractor.newQuestion();
            gameView.loadQuestion(this.turnType);
        }
    }

    @Override
    public void finishGame(int aciertos) {
        Intent intent = new Intent((Context) gameView, ResultAcivity.class);
        ((Context) gameView).startActivity(intent);
    }

}
