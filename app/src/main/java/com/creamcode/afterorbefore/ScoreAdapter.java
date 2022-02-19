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
        try {
            Collections.sort(scores, (s1, s2) ->
                    Integer.compare(s2.points, s1.points));
        }catch (Exception e){

        }

    }

    @Override
    public int getCount() {
        return scores.size();
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

        tvName.setText(scores.get(i).toString());
        /*tvScore.setText(arrayListst.get(i)[1]);*/

        return view;
    }
}
