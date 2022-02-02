package com.creamcode.afterorbefore.Interactors;


import com.creamcode.afterorbefore.Interfaces.PictureInteractor;
import com.creamcode.afterorbefore.Interfaces.PicturePresenter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PictureInteractorImp implements PictureInteractor {

    private PicturePresenter presenter;

    private String id, year, country, url;

    public PictureInteractorImp(PicturePresenter presenter){
        this.presenter = presenter;

        id="null";
        year = "null";
        country = "null";
        url = "null";
    }


    @Override
    public void getPhoto(String id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Pictures").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                year = documentSnapshot.getLong("year").toString();
                country = documentSnapshot.getString("country");
                url = documentSnapshot.getString("url");
                presenter.loadPhotoData(id,year,country,url);
            }
        });

    }
}
