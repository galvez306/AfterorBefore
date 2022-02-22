package com.creamcode.afterorbefore.Presenters;



import com.creamcode.afterorbefore.Interactors.PictureInteractorImp;
import com.creamcode.afterorbefore.Interfaces.PictureInteractor;
import com.creamcode.afterorbefore.Interfaces.PicturePresenter;
import com.creamcode.afterorbefore.Interfaces.PictureView;
import java.util.ArrayList;


public class PicturePresenterImp implements PicturePresenter {

    private PictureView view;
    private PictureInteractor interactor;

    private String pictureId, country;
    private String nameFlagOne, nameFlagTwo, nameFlagThree;
    private int year;
    private boolean lock= false;

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
    public void checkPictureYear() {
        if(!lock){
           view.sendPictureYearAnswer(pictureId);
        }
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
    public void lockingPicture(boolean lock) {
        this.lock = lock;
    }

    @Override
    public void loadFlags(ArrayList<String[]> flags) {
        nameFlagOne = flags.get(0)[0];
        nameFlagTwo =flags.get(1)[0];
        nameFlagThree = flags.get(2)[0];
        view.loadFlagsonView(flags);

    }

    @Override
    public void checkFlagName(int flagNumber) {

        switch (flagNumber){
            case 1:
                if(nameFlagOne.equals(country)){
                    view.sendFlagNameAnswer(true, pictureId);
                }else{
                    view.sendFlagNameAnswer(false, pictureId);
                }
                break;
            case 2:
                if(nameFlagTwo.equals(country)){
                    view.sendFlagNameAnswer(true, pictureId);
                }else{
                    view.sendFlagNameAnswer(false, pictureId);
                }
                break;
            case 3:
                if(nameFlagThree.equals(country)){
                    view.sendFlagNameAnswer(true, pictureId);
                }else{
                    view.sendFlagNameAnswer(false, pictureId);
                }
                break;
        }
    }

}
