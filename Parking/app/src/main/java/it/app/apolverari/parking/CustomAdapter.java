package it.app.apolverari.parking;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by a.polverari on 20/04/2016.
 */
public class CustomAdapter extends BaseAdapter{

    private ArrayList<Parking> parkings = null;
    private Context ctx = null;

    public CustomAdapter(Context context, ArrayList<Parking> parkings) {
        this.parkings = parkings;
        this.ctx = context;
    }

    @Override
    public int getCount() {
        return parkings.size();
    }

    @Override
    public Object getItem(int position) {
        return parkings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if (v == null) {
            v = LayoutInflater.from(ctx).inflate(R.layout.parks_adapter, null);
        }
        Parking p = (Parking) getItem(position);
        TextView park_date = (TextView) v.findViewById(R.id.park_date);
        park_date.setText(p.getDate());
        TextView park_address = (TextView) v.findViewById(R.id.park_address);
        String address = p.getAddress();
        if (address.length() >= 40){
            address = address.substring(0,40) + "...";
        }
        park_address.setText(address);
        ImageView pic = (ImageView) v.findViewById(R.id.park_pic);
        try {
            pic.setImageBitmap(MediaStore.Images.Media.getBitmap(ctx.getContentResolver(), Uri.parse(p.getPic())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return v;
    }
}
