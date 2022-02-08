package com.creamcode.afterorbefore.Presenters;

import com.creamcode.afterorbefore.Interactors.PictureInteractorImp;
import com.creamcode.afterorbefore.Interfaces.PictureInteractor;
import com.creamcode.afterorbefore.Interfaces.PicturePresenter;
import com.creamcode.afterorbefore.Interfaces.PictureView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class PicturePresenterImp implements PicturePresenter {

    private PictureView view;
    private PictureInteractor interactor;

    private String pictureId, country;
    private int year;

    public PicturePresenterImp(PictureView view) {
        this.view = view;
        this.interactor = new PictureInteractorImp(this);
    }

    @Override
    public String getPictureId() {
        return pictureId;
    }

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public String getCountry() {
        return country;
    }

    @Override
    public void getPhotoData(String id) {
        interactor.getPhoto(id);
    }

    @Override
    public void loadPhotoData(String id, String year, String country, String url) {
        this.pictureId = id;
        view.loadID(id);
        this.year = Integer.parseInt(year);
        view.loadYear(year);
        this.country = country;
        view.loadCountry(country);
        view.loadUrl(url);
    }

    @Override
    public void getFlags() {
        interactor.getRandomFlags(country);
    }

    @Override
    public void loadFlags(ArrayList<String[]> flags) {
        view.loadFlagsonView(flags);

    }
}
