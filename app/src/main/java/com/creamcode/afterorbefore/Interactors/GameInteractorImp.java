package com.creamcode.afterorbefore.Interactors;

import com.creamcode.afterorbefore.Interfaces.GameInteractor;
import com.creamcode.afterorbefore.Interfaces.GamePresenter;


import java.util.ArrayList;
import java.util.Random;


public class GameInteractorImp implements GameInteractor {

    private GamePresenter gamePresenter;

    private final static int META = 5; //meta del juego, DEBE SER +2
    private final static int NUM_PHOTOS = 7; // cantidad de fotos en la coleccion

    //tambien falta devolver un conjunto de paises

    public GameInteractorImp(GamePresenter presenter){
        gamePresenter=presenter;
    }

    @Override
    public void getIds() {
        ArrayList<String > ids = new ArrayList<String>();
        for(int i = 0; i<META; i++){
            String random = String.valueOf((int) ((Math.random() * (NUM_PHOTOS)) ));
            while(ids.contains(random)){
                random = String.valueOf((int) ((Math.random() * (NUM_PHOTOS)) ));
            }
            ids.add(random);
        }

        gamePresenter.setIds(ids, newQuestion());
    }

    @Override
    public String newQuestion() {
        Random random = new Random();
        if(random.nextBoolean()){
            return "AFTER";
        }else{
            return "BEFORE";
        }
    }
}
