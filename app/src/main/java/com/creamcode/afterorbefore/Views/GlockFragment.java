package com.creamcode.afterorbefore.Views;

import android.os.Bundle;

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

    private int tiempoActual, tiempoRestante;

    private GlockInterface glockInterface;

    public interface GlockInterface {
        void tiempoTermino();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_glock, container, false);

        pgbGlock = v.findViewById(R.id.pgb_glock);

        return v;
    }

    public void cuentaRegresiva() {
        tarea = new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new TimerTask() {
                    @Override
                    public void run() {
                        if(tiempoActual ==0){
                            pgbGlock.setProgress(0);
                            glockInterface.tiempoTermino();
                            timer.cancel();
                        }else{
                            pgbGlock.setProgress(tiempoActual *100/ tiempoRestante);
                            tiempoActual--;
                        }
                    }
                });
            }
        };
        timer.schedule(tarea,0,1000);
    }
    public void cambiarTiempo(int newTime){
        tiempoActual = newTime/1000;
        tiempoRestante = tiempoActual;
    }
    public void cancelTimer(){
        timer.cancel();
    }
}