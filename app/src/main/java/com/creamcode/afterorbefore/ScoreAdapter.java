package com.creamcode.afterorbefore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.protobuf.StringValue;

import java.util.ArrayList;
import java.util.List;

public class ScoreAdapter extends BaseAdapter {
    Context context;
    ArrayList<String[]> arrayListst;

    public ScoreAdapter(Context context, ArrayList<String[]> arrayListst) {
        this.context = context;
        this.arrayListst = arrayListst;
    }

    @Override
    public int getCount() {
        return arrayListst.size();
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

        tvName.setText(arrayListst.get(i)[0]);
        tvScore.setText(arrayListst.get(i)[1]);

        return view;
    }
}
