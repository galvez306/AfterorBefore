package com.creamcode.afterorbefore.Interfaces;

import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface PicturePresenter {

    void getPhotoData(String id);
    void loadPhotoData(String id, String year, String country, String url);
    String getPictureId();
    int getYear();
    void checkPictureYear();
    void getFlags();
    void lockingPicture(boolean lock);
    void loadFlags(ArrayList<String[]> flags);
    void checkFlagName(int flagNumber);




}
