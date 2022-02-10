package com.creamcode.afterorbefore.Presenters;

import android.widget.Toast;

import com.creamcode.afterorbefore.Interactors.GameInteractorImp;
import com.creamcode.afterorbefore.Interfaces.GameInteractor;
import com.creamcode.afterorbefore.Interfaces.GamePresenter;
import com.creamcode.afterorbefore.Interfaces.GameView;
import com.creamcode.afterorbefore.Views.PictureFragment;

import java.util.ArrayList;

public class GamePresenterImp implements GamePresenter {

    private GameView gameView;
    private GameInteractor gameInteractor;

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
        //mandandole los dos primeros ids random a la vista activity y removiendolos
        gameView.loadQuestion(turnType);
        gameView.initializePicturesAB(arrayPictures.get(0).toString(),arrayPictures.get(1).toString());
        arrayPictures.remove(0);
        arrayPictures.remove(0);
    }

    @Override
    public void checkAnswerPresenter(PictureFragment pictureFragmentA, PictureFragment pictureFragmentB, String id) {
        PictureFragment fragmentActual;
        //identify wich fragment is calling
        if(pictureFragmentA.getPictureId().equals(id)){
            fragmentActual =pictureFragmentA;
        }else{
            fragmentActual = pictureFragmentB;
        }
        if(fragmentActual==pictureFragmentA){//ist fragmentA
            if(turnType.equals("AFTER")){
                if(fragmentActual.getYear()>pictureFragmentB.getYear()){
                    //correcto
                    nextPicture(pictureFragmentA);
                }else{
                    //incorrecto
                    //generar flags con picture A y ocultar a B
                    if(!second_opportunity){
                        pictureFragmentA.cargarFlags();
                    }else{
                        Toast.makeText(pictureFragmentA.getContext(), "Perdiste", Toast.LENGTH_SHORT).show();
                    }
                }
            }else{//its BEFORE
                if(fragmentActual.getYear()<pictureFragmentB.getYear()){
                    //correcto
                    nextPicture(pictureFragmentA);
                }else{
                    //incorrecto
                    //generar flags con picture A y ocultar a B
                    if(!second_opportunity){
                        pictureFragmentA.cargarFlags();
                    }else{
                        Toast.makeText(pictureFragmentB.getContext(), "Perdiste", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }else{//ist fragmentB
            if(turnType.equals("AFTER")){
                if(fragmentActual.getYear()>pictureFragmentA.getYear()){
                    //correcto
                    nextPicture(pictureFragmentB);
                }else{
                    //incorrecto
                    //generar flags con picture B y ocultar a A
                    if(!second_opportunity){
                        pictureFragmentB.cargarFlags();
                    }else{
                        Toast.makeText(pictureFragmentB.getContext(), "Perdiste", Toast.LENGTH_SHORT).show();
                    }
                }
            }else{
                if(fragmentActual.getYear()<pictureFragmentA.getYear()){
                    //correcto
                    nextPicture(pictureFragmentB);
                }else{
                    //incorrecto
                    //generar flags con picture y y ocultar a A
                    if(!second_opportunity){
                        pictureFragmentB.cargarFlags();
                    }else{
                        Toast.makeText(pictureFragmentB.getContext(), "Perdiste", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }

    }

    @Override
    public void checkAnswerFlagPresenter(PictureFragment pictureFragmentA, PictureFragment pictureFragmentB, String flagName) {
        PictureFragment fragmentActual;
        //identify wich fragment is calling
        if(pictureFragmentA.getCountry().equals(flagName)){
            fragmentActual =pictureFragmentA;
        }else{
            fragmentActual = pictureFragmentB;
        }
        if(fragmentActual==pictureFragmentA){//ist fragmentA
            if(pictureFragmentA.getCountry().equals(flagName)){
                //correct, hide flags and next picture, and secondtry==true
                second_opportunity = true;
                pictureFragmentA.flagsLayoutVisibility(false);
                pictureFragmentA.pictureOpacity(false);
                nextPicture(pictureFragmentA);
            }else{
                //incorrect, quite the game
            }
        }else{//ist fragmentB
            if(pictureFragmentB.getCountry().equals(flagName)){
                //correct, hide flags and next picture, and secondtry==true
                second_opportunity = true;
                pictureFragmentB.flagsLayoutVisibility(false);
                pictureFragmentB.pictureOpacity(false);
                nextPicture(pictureFragmentB);
            }else{
                //incorrect, quite the game
            }
        }
    }

    @Override
    public void nextPicture(PictureFragment pictureFragment) {
        if(arrayPictures.isEmpty()){
            Toast.makeText(pictureFragment.getContext(), "Ganaste", Toast.LENGTH_SHORT).show();
        }else {
            gameView.loadNextPicture(pictureFragment, arrayPictures.get(0).toString());
            arrayPictures.remove(0);
            this.turnType = gameInteractor.newQuestion();
            gameView.loadQuestion(this.turnType);
        }
    }

}
