package com.creamcode.afterorbefore.Presenters;

import com.creamcode.afterorbefore.Interactors.PictureInteractorImp;
import com.creamcode.afterorbefore.Interfaces.PictureInteractor;
import com.creamcode.afterorbefore.Interfaces.PicturePresenter;
import com.creamcode.afterorbefore.Interfaces.PictureView;

public class PicturePresenterImp implements PicturePresenter {

    private PictureView view;
    private PictureInteractor interactor;

    public PicturePresenterImp(PictureView view) {
        this.view = view;
        this.interactor = new PictureInteractorImp(this);
    }

    @Override
    public void getPhotoData(String id) {
        interactor.getPhoto(id);
    }

    @Override
    public void loadPhotoData(String id, String year, String country, String url) {
        view.loadID(id);
        view.loadYear(year);
        view.loadCountry(country);
        view.loadUrl(url);
    }
}
