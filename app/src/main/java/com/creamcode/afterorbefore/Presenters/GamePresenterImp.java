package com.creamcode.afterorbefore.Presenters;

import android.widget.Toast;

import com.creamcode.afterorbefore.GameActivity;
import com.creamcode.afterorbefore.Interactors.GameInteractorImp;
import com.creamcode.afterorbefore.Interfaces.GameInteractor;
import com.creamcode.afterorbefore.Interfaces.GamePresenter;
import com.creamcode.afterorbefore.Interfaces.GameView;
import com.creamcode.afterorbefore.Views.PictureFragment;

import java.util.ArrayList;

public class GamePresenterImp implements GamePresenter {

    private GameView gameView;
    private GameInteractor gameInteractor;

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
    public void setIds(ArrayList ids) {
        arrayPictures = ids;
        for (int i = 0; i< arrayPictures.size(); i++){
            System.out.print("elemento "+i+"--->");
            System.out.println(arrayPictures.get(i));
        }
        //mandandole los dos primeros ids random a la vista activity y removiendolos
        gameView.initializePicturesAB(arrayPictures.get(0).toString(),arrayPictures.get(1).toString());
        arrayPictures.remove(0);
        arrayPictures.remove(0);

        for (int i = 0; i< arrayPictures.size(); i++){
            System.out.print("elemento "+i+"--->");
            System.out.println(arrayPictures.get(i));
        }
    }

    //creo que se necesitara otro metodo en presentes(interfacePresenter para obtener el siguiente indice)
    //ya no es necesario ir hasta el modelo, luego luego regresar a la vista
    @Override
    public void nextPicture(PictureFragment pictureFragment) {
        if(arrayPictures.isEmpty()){
            System.out.println("ya no hay mas imagenes");
        }else {
            gameView.loadNextPictur(pictureFragment, arrayPictures.get(0).toString());
            arrayPictures.remove(0);
        }
    }


}
