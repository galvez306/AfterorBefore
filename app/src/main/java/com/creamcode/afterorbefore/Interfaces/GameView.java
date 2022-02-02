package com.creamcode.afterorbefore.Interfaces;

import com.creamcode.afterorbefore.Views.PictureFragment;

import java.util.ArrayList;

public interface GameView {
    void initializePicturesAB(String idA, String idB);
    void loadNextPictur(PictureFragment pictureFragment, String id);
}
