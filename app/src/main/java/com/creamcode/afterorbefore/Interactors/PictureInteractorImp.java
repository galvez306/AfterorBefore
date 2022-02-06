package com.creamcode.afterorbefore.Interactors;


import android.os.CountDownTimer;

import androidx.annotation.NonNull;

import com.creamcode.afterorbefore.Interfaces.PictureInteractor;
import com.creamcode.afterorbefore.Interfaces.PicturePresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class PictureInteractorImp implements PictureInteractor {

    private final static int NUM_FLAGS = 78; // cantidad de fotos en la coleccion
    private final static int FLAGS = 2; // cantidad de fotos en el fragment

    private PicturePresenter presenter;

    private String id, year, country, url, idFlagCountryActual;


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

    @Override
    public void getRandomFlags(String countryActual) {
        ArrayList<String > nameFlags = new ArrayList<String>();
        ArrayList<String > urlFlags = new ArrayList<String>();
        ArrayList<String > idsFlags = new ArrayList<String>();

        //get the data flag of the actual country displayed in the fragment
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Flags").whereEqualTo("country", countryActual).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                idFlagCountryActual = document.getId();
                                nameFlags.add(document.getString("country"));
                                urlFlags.add(document.getString("url"));
                            }
                        }
                    }
                });

        //get other two ids of flags
        for(int i = 0; i<FLAGS; i++){
            String random = String.valueOf((int) ((Math.random() * (NUM_FLAGS)) ));
            while(idsFlags.contains(random) || random.equals(idFlagCountryActual)){
                random = String.valueOf((int) ((Math.random() * (NUM_FLAGS)) ));
            }
            idsFlags.add(random);
        }
        //getting data of the 2 left flags, we are starting with
        for(int j = 0; j<idsFlags.size(); j ++){
            db.collection("Flags")
                    .whereEqualTo("__name__", idsFlags.get(j))
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    nameFlags.add(document.getString("country"));
                                    urlFlags.add(document.getString("url"));
                                }
                            }
                        }
                    });
        }
       /* //para hacer esto debemos convertir los arraylist en HAshmap
        Collections.shuffle(mylist);*/

        //waiting for the data
        new CountDownTimer(400, 1000) {
            public void onFinish() {
                //meter accion aqui
                presenter.loadFlags(nameFlags, urlFlags);
            }
            public void onTick(long millisUntilFinished) {
            }
        }.start();
    }
}
