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
        /*for (int i = 0; i< arrayPictures.size(); i++){
            System.out.print("elemento "+i+"--->");
            System.out.println(arrayPictures.get(i));
        }*/
        //mandandole los dos primeros ids random a la vista activity y removiendolos
        gameView.loadQuestion(turnType);
        gameView.initializePicturesAB(arrayPictures.get(0).toString(),arrayPictures.get(1).toString());
        arrayPictures.remove(0);
        arrayPictures.remove(0);

       /* for (int i = 0; i< arrayPictures.size(); i++){
            System.out.print("elemento "+i+"--->");
            System.out.println(arrayPictures.get(i));
        }*/
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
                if(fragmentActual.getYear()<pictureFragmentB.getYear()){
                    //correcto
                    nextPicture(pictureFragmentA);
                }else{
                    //incorrecto
                }
            }else{
                if(fragmentActual.getYear()>pictureFragmentB.getYear()){
                    //correcto
                    nextPicture(pictureFragmentA);
                }else{
                    //incorrecto

                }
            }
        }else{//ist fragmentB
            if(turnType.equals("AFTER")){
                if(fragmentActual.getYear()<pictureFragmentA.getYear()){
                    //correcto
                    nextPicture(pictureFragmentB);
                }else{
                    //incorrecto
                }
            }else{
                if(fragmentActual.getYear()>pictureFragmentA.getYear()){
                    //correcto
                    nextPicture(pictureFragmentB);
                }else{
                    //incorrecto
                }
            }
        }

    }

    @Override
    public void nextPicture(PictureFragment pictureFragment) {
        if(arrayPictures.isEmpty()){
            System.out.println("ya no hay mas imagenes");
            Toast.makeText(pictureFragment.getContext(), "Ganaste", Toast.LENGTH_SHORT).show();
        }else {
            gameView.loadNextPicture(pictureFragment, arrayPictures.get(0).toString());
            arrayPictures.remove(0);
            this.turnType = gameInteractor.newQuestion();
            gameView.loadQuestion(this.turnType);
        }
    }

}
