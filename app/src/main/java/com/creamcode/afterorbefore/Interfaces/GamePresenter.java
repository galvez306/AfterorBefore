package com.creamcode.afterorbefore.Interfaces;

import com.creamcode.afterorbefore.Views.PictureFragment;

import java.util.ArrayList;

public interface GamePresenter {
    void getIds();
    void setIds(ArrayList ids, String turnType);
    void checkAnswerPresenter(PictureFragment pictureFragmentA, PictureFragment pictureFragmentB, String id);
    void checkAnswerFlagPresenter(PictureFragment pictureFragmentA, PictureFragment pictureFragmentB, String flagName);
    void nextPicture(PictureFragment pictureFragment);
}
