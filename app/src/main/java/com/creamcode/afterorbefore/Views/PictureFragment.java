package com.creamcode.afterorbefore.Views;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
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


public class PictureFragment extends Fragment implements PictureView {

    private PictureFragmentInterface pfInterface;

    private LinearLayout mainLyt;

    public TextView tvID, tvCountry, tvYear;
    private ImageView imvPicture;
    private ImageView btnFlagOne, btnFlagTwo, btnFlagThree;



    private PicturePresenter presenter;


    public interface PictureFragmentInterface{
        void checkAnswer(String id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picture, container, false);

        mainLyt = view.findViewById(R.id.main_layout);
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
                pfInterface.checkAnswer(presenter.getPictureId());
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
    public void loadFlagsonView(String flagone, String flagtwo, String flagThree) {
        Glide.with(getContext()).load(flagone).into(btnFlagOne);
        Glide.with(getContext()).load(flagtwo).into(btnFlagTwo);
        Glide.with(getContext()).load(flagThree).into(btnFlagThree);
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