package com.creamcode.afterorbefore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.protobuf.StringValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreAdapter extends BaseAdapter {
    Context context;
    ArrayList<ResultAcivity.Score> scores;

    public ScoreAdapter(Context context, ArrayList<ResultAcivity.Score> scores) {
        this.context = context;
        this.scores = scores;

    }

    @Override
    public int getCount() {
        int size=0;
        for(int i = 0; i<scores.size(); i++){
            if(scores.get(i).name!=null){
                size++;
            }
        }
        return size;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView tvName, tvScore;

        if(view==null){
            view = LayoutInflater.from(context).inflate(R.layout.item_score_result,null);
        }
        tvName = view.findViewById(R.id.tv_name);
        tvScore = view.findViewById(R.id.tv_score);
        if(scores.get(i).name!=null){
            tvName.setText(scores.get(i).name);
            tvScore.setText(String.valueOf(scores.get(i).points));
        }
        return view;
    }
}
