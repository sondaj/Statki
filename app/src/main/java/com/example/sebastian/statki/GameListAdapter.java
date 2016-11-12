package com.example.sebastian.statki;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GameListAdapter extends ArrayAdapter<RowOneGame> {
    private Context context;
    private ArrayList<RowOneGame> values;

    public GameListAdapter(Context context, ArrayList<RowOneGame> values) {
        super(context, 0, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.one_game_row, parent, false);
        TextView textView1 = (TextView) rowView.findViewById(R.id.one_game_title);
        TextView textView2 = (TextView) rowView.findViewById(R.id.one_game_occupied);
        textView1.setText(values.get(position).getTitle());

        if(values.get(position).getOccupied()) {
            textView2.setText("zajeta");
        }else{
            textView2.setText("wolna");
        }

        return rowView;
    }
}