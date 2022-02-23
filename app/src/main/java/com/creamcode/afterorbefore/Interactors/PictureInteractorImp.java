package com.creamcode.afterorbefore.Interactors;


import android.os.CountDownTimer;

import androidx.annotation.NonNull;

import com.creamcode.afterorbefore.Interfaces.PictureInteractor;
import com.creamcode.afterorbefore.Interfaces.PicturePresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class PictureInteractorImp implements PictureInteractor {

    private final static int NUM_FLAGS = 78; // cantidad de fotos en la coleccion
    private final static int FLAGS = 3; // cantidad de fotos en el fragment

    private PicturePresenter presenter;

    private String  idFlagCountryActual;

    public PictureInteractorImp(PicturePresenter presenter){
        this.presenter = presenter;
    }

    @Override
    public void getPhoto(String id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Pictures").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                presenter.loadPhotoData(id,documentSnapshot.getLong("year").toString(),
                        documentSnapshot.getString("country"),documentSnapshot.getString("url"));
            }
        });

    }

    @Override
    public void getRandomFlags(String countryActual) {
        ArrayList<String[]> flags = new ArrayList<String[]>();
        ArrayList<String > idsFlags = new ArrayList<String>();

        //get the data flag of the actual country displayed in the fragment
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Flags").whereEqualTo("country", countryActual).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                idFlagCountryActual = document.getId();
                                String [] flag = {document.getString("country"),document.getString("url")};
                                flags.add(flag);
                            }
                        }
                    }
                });

        //get other two ids of flags
        for(int i = 0; i<FLAGS-1; i++){
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
                                    String [] flag = {document.getString("country"),document.getString("url")};
                                    flags.add(flag);
                                }
                            }
                        }
                    });
        }

        //waiting for the data
        new CountDownTimer(800, 1000) {
            public void onFinish()
            {
                //disording the flags to implement randomity
                Collections.shuffle(flags);
                presenter.loadFlags(flags);
            }
            public void onTick(long millisUntilFinished) {
            }
        }.start();
    }
}
