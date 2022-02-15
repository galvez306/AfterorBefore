package com.creamcode.afterorbefore.Views;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.creamcode.afterorbefore.R;

import java.util.Timer;
import java.util.TimerTask;


public class GlockFragment extends Fragment {

    private ProgressBar pgbGlock;
    private Timer timer = new Timer();
    TimerTask tarea;

    private final static int RESET_PHOTO = 20;
    private final static int RESET_FLAGS = 10;

    private int time = RESET_PHOTO;
    private int timeLeft;

    private GlockInterface glockInterface;

    public interface GlockInterface {
        void tiempoTermino();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_glock, container, false);

        pgbGlock = v.findViewById(R.id.pgb_glock);

        timeLeft = time;
        cuentaRegresiva();

        return v;
    }

    public void cuentaRegresiva() {
        tarea = new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new TimerTask() {
                    @Override
                    public void run() {
                        if(time ==0){
                            pgbGlock.setProgress(0);
                            glockInterface.tiempoTermino();
                            timer.cancel();
                        }else{
                            pgbGlock.setProgress(time *100/ timeLeft);
                            time--;
                        }
                    }
                });
            }
        };
        timer.schedule(tarea,0,1000);
    }

    public void resetTimePhoto(){
        time = RESET_PHOTO;
        timeLeft = time;
    }
    public void resetTimeflags(){
        time = RESET_FLAGS;
        timeLeft = time;
    }
    public void cancelTimer(){
        timer.cancel();
    }
    //Methods to match the fragment with the interface in the activity
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof GlockFragment.GlockInterface){
            glockInterface = (GlockFragment.GlockInterface) context;
        }else{
            throw new RuntimeException(context.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        glockInterface = null;
    }
}