package com.example.apolverari.myapplication;

import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by a.polverari on 14/04/2016.
 */
public class NoteAdapter extends ArrayAdapter<Nota>{

    public NoteAdapter(Context context, int textViewResourceId,
                       Nota [] objects) {
        super(context, textViewResourceId, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.custom_row, null);
        TextView title = (TextView)convertView.findViewById(R.id.textViewTitle);
        TextView content = (TextView)convertView.findViewById(R.id.textViewContent);
        Nota n = getItem(position);
        title.setText(n.getTitolo());
        content.setText(n.getContenuto());
        return convertView;
    }


}
