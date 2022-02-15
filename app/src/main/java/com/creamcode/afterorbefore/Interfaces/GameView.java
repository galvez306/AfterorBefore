package com.creamcode.afterorbefore.Interfaces;

import com.creamcode.afterorbefore.Views.PictureFragment;

import java.util.ArrayList;

public interface GameView {
    void initializePicturesAB(String idA, String idB);
    void loadNextPicture(PictureFragment pictureFragment, String id);
    void loadQuestion(String question);
    void changeTime(String timeType);
    void killTime();
}
