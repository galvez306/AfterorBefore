package com.creamcode.afterorbefore.Interfaces;

import com.creamcode.afterorbefore.Views.PictureFragment;

import java.util.ArrayList;

public interface GamePresenter {
    void getIds();
    void setIds(ArrayList ids, String turnType);
    void nextPicture(PictureFragment pictureFragment);
    String getTurnType();
}
