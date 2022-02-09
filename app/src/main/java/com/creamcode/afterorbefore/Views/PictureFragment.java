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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.creamcode.afterorbefore.Interfaces.PicturePresenter;
import com.creamcode.afterorbefore.Interfaces.PictureView;
import com.creamcode.afterorbefore.Presenters.PicturePresenterImp;
import com.creamcode.afterorbefore.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class PictureFragment extends Fragment implements PictureView {

    private PictureFragmentInterface pfInterface;

    private ConstraintLayout mainLyt;
    private LinearLayout  flagsLyt;

    public TextView tvID, tvCountry, tvYear;
    private ImageView imvPicture;
    private ImageView btnFlagOne, btnFlagTwo, btnFlagThree;

    private String flagOne, flagTwo, flagThree;


    private PicturePresenter presenter;


    public interface PictureFragmentInterface{
        void checkAnswerCountry(String id);
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
                pfInterface.checkAnswerCountry(presenter.getPictureId());
            }
        });
        btnFlagOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"esta es la bandera "+flagOne,Toast.LENGTH_SHORT).show();
            }
        });
        btnFlagTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"esta es la bandera "+flagTwo,Toast.LENGTH_SHORT).show();
            }
        });
        btnFlagThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"esta es la bandera "+flagThree,Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public String getPictureId() {
        return presenter.getPictureId();
    }

    public int getYear() {
        return presenter.getYear();
    }

    public String getCountry() {
        return presenter.getCountry();
    }

    public void cargarFoto(String id){
        presenter.getPhotoData(id);
    }

    public void cargarFlags(){
        flagsLyt.setVisibility(View.VISIBLE);
        presenter.getFlags();
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
        flagOne = flags.get(0)[0];
        flagTwo =flags.get(1)[0];
        flagThree = flags.get(2)[0];
        Glide.with(getContext()).load(flags.get(0)[1]).into(btnFlagOne);
        Glide.with(getContext()).load(flags.get(1)[1]).into(btnFlagTwo);
        Glide.with(getContext()).load(flags.get(2)[1]).into(btnFlagThree);


        //esto no desordena el map, solo lo lee en desorden
        /*List keys = new ArrayList(flagsMap.keySet());
        Collections.shuffle(keys);
        for (Object o : keys) {
            flagsMap.get(o);
        }*/


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