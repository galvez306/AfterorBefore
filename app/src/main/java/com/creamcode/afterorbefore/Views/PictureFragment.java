package com.creamcode.afterorbefore.Views;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.creamcode.afterorbefore.Interfaces.PicturePresenter;
import com.creamcode.afterorbefore.Interfaces.PictureView;
import com.creamcode.afterorbefore.Presenters.PicturePresenterImp;
import com.creamcode.afterorbefore.R;

import java.util.ArrayList;


public class PictureFragment extends Fragment implements PictureView {

    private PictureFragmentInterface pfInterface;

    private ConstraintLayout mainLyt;
    private LinearLayout  flagsLyt;

    public TextView tvID, tvCountry, tvYear;
    private ImageView imvPicture;
    public ImageView btnFlagOne, btnFlagTwo, btnFlagThree;



    private PicturePresenter presenter;


    public interface PictureFragmentInterface{
        void checkAnswerCountry(String id);
        void checkNameFlagAnswer(boolean answer, String id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picture, container, false);

        mainLyt = view.findViewById(R.id.main_layout);
        flagsLyt = view.findViewById(R.id.flags_layout);

        tvID = view.findViewById(R.id.tv_id_picture);
        tvYear = view.findViewById(R.id.tv_year_picture);
        tvCountry = view.findViewById(R.id.tv_country_picture);

        imvPicture = view.findViewById(R.id.imv_picture);

        btnFlagOne = view.findViewById(R.id.btn_flag_one);
        btnFlagTwo = view.findViewById(R.id.btn_flag_two);
        btnFlagThree = view.findViewById(R.id.btn_flag_three);


        Bundle bundle = getArguments();
        String id = bundle.getString("id");

        presenter = new PicturePresenterImp(this);
        presenter.getPhotoData(id);

        mainLyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.checkPictureYear();
            }
        });
        btnFlagOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                presenter.checkFlagName(1);
            }
        });
        btnFlagTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.checkFlagName(2);
            }
        });
        btnFlagThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.checkFlagName(3);
            }
        });

        return view;
    }

    public void lockPictureFuncionality(boolean lock){
        presenter.lockingPicture(lock);
    }
    public void infoPictureVisibility(){
        tvCountry.setVisibility(View.VISIBLE);
        tvYear.setVisibility(View.VISIBLE);
    }

    public String getPictureId() {
        return presenter.getPictureId();
    }

    public int getYear() {
        return presenter.getYear();
    }


    public void cargarFoto(String id){
        presenter.getPhotoData(id);
    }

    public void cargarFlags(){
        pictureOpacity(true);
        flagsLayoutVisibility(true);
        presenter.getFlags();
    }
    public void flagsLayoutVisibility(Boolean visivility){
        if(visivility){
            flagsLyt.setVisibility(View.VISIBLE);
        }else{
            flagsLyt.setVisibility(View.INVISIBLE);
        }
    }
    public void pictureOpacity(Boolean visivility){
        if(visivility){
            imvPicture.setAlpha(0.6f);
        }else{
            imvPicture.setAlpha(1.0f);
        }
    }

    //metodos de la interface del mvp del fragment
    @Override
    public void loadID(String id) {
        tvID.setText(id);
    }

    @Override
    public void loadYear(String year) {
        tvYear.setText(year);
    }

    @Override
    public void loadCountry(String country) {
        tvCountry.setText(country);
    }

    @Override
    public void loadUrl(String url) {
        Glide.with(getContext()).load(url).into(imvPicture);
    }

    @Override
    public void loadFlagsonView(ArrayList<String[]> flags) {
        Glide.with(getContext()).load(flags.get(0)[1]).into(btnFlagOne);
        Glide.with(getContext()).load(flags.get(1)[1]).into(btnFlagTwo);
        Glide.with(getContext()).load(flags.get(2)[1]).into(btnFlagThree);

    }

    @Override
    public void sendPictureYearAnswer(String pictureFragmentId) {
        pfInterface.checkAnswerCountry(pictureFragmentId);
    }

    @Override
    public void sendFlagNameAnswer(boolean answer, String pictureFragmentId) {
        pfInterface.checkNameFlagAnswer(answer,pictureFragmentId);
    }

    //Methods to match the fragment with the interface in the activity
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof PictureFragmentInterface){
            pfInterface = (PictureFragmentInterface) context;
        }else{
            throw new RuntimeException(context.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        pfInterface = null;
    }
}