package com.creamcode.afterorbefore.Interfaces;

import java.util.ArrayList;

public interface PicturePresenter {

    void getPhotoData(String id);
    void loadPhotoData(String id, String year, String country, String url);
    String getPictureId();
    int getYear();
    String getCountry();
    void getFlags();
    void loadFlags(ArrayList <String> names, ArrayList <String> urls);




}
