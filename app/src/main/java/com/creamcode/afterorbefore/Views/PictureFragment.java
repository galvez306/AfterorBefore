package com.creamcode.afterorbefore.Views;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.creamcode.afterorbefore.Interfaces.PicturePresenter;
import com.creamcode.afterorbefore.Interfaces.PictureView;
import com.creamcode.afterorbefore.Presenters.PicturePresenterImp;
import com.creamcode.afterorbefore.R;


public class PictureFragment extends Fragment implements PictureView {

    private PictureFragmentInterface pfInterface;

    private LinearLayout mainLyt;

    public TextView tvID, tvCountry, tvYear;
    private ImageView imvPicture;
    private String pictureId, country;

    private int year;
    private PicturePresenter presenter;


    public interface PictureFragmentInterface{
        void checkAnswer(String id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picture, container, false);

        tvID = view.findViewById(R.id.tv_id_picture);
        tvYear = view.findViewById(R.id.tv_year_picture);
        tvCountry = view.findViewById(R.id.tv_country_picture);
        imvPicture = view.findViewById(R.id.imv_picture);

        mainLyt = view.findViewById(R.id.main_layout);

        Bundle bundle = getArguments();
        String id = bundle.getString("id");

        presenter = new PicturePresenterImp(this);
        presenter.getPhotoData(id);

        mainLyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pfInterface.checkAnswer(pictureId);
            }
        });

        return view;
    }

    public String getPictureId() {
        return pictureId;
    }

    public int getYear() {
        return year;
    }

    public String getCountry() {
        return country;
    }

    public void cargarFoto(String id){
        pictureId = id;
        presenter.getPhotoData(id);
    }

    //metodos de la interface del mvp del fragment
    @Override
    public void loadID(String id) {
        this.pictureId = id;
        tvID.setText(id);
    }

    @Override
    public void loadYear(String year) {
        this.year = Integer.parseInt(year);
        tvYear.setText(year);
    }

    @Override
    public void loadCountry(String country) {
        this.country = country;
        tvCountry.setText(country);
    }

    @Override
    public void loadUrl(String url) {
        Glide.with(getContext()).load(url).into(imvPicture);
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