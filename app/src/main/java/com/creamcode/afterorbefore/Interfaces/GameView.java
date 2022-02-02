package com.creamcode.afterorbefore.Interfaces;

import com.creamcode.afterorbefore.Views.PictureFragment;

import java.util.ArrayList;

public interface GameView {
    void initializePicturesAB(String idA, String idB, String questionType);
    void loadNextPictur(PictureFragment pictureFragment, String id);
}
