package com.creamcode.afterorbefore.Interfaces;

import android.os.Bundle;

import java.util.ArrayList;


public interface PictureView {

    void loadID(String id);
    void loadYear(String year);
    void loadCountry(String country);
    void loadUrl(String url);
    void loadFlagsonView(ArrayList<String[]> flags);
    void sendPictureYearAnswer(String pictureFragmentId);
    void sendFlagNameAnswer(boolean answer, String pictureFragmentId);

}
