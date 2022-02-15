package com.creamcode.afterorbefore.Interfaces;

import com.creamcode.afterorbefore.Views.PictureFragment;

import java.util.ArrayList;

public interface GamePresenter {
    void getIds();
    void setIds(ArrayList ids, String turnType);
    void checkAnswerPresenter(PictureFragment pictureFragmentA, PictureFragment pictureFragmentB, String id);
    void flagNameAnswer(boolean answer, String id, PictureFragment pictureFragmentA, PictureFragment pictureFragmentB);
    void nextPicture(PictureFragment pictureFragment);
    void finishGame(int aciertos);
}
