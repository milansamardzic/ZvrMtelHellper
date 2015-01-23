package com.milansamardzic.zvrrmtel;

import android.content.Context;
import android.net.Uri;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.milansamardzic.pozovime.R;
import com.pkmmte.view.CircularImageView;

import java.util.ArrayList;

/**
 * Created by ms on 12/24/14.
 */
public class GridAdapter  extends ArrayAdapter<People> {
    public SparseBooleanArray mCheckStates;
    LayoutInflater mInflater;
    TextView tv1;
    TextView tv;
    TextView tvNameInitialFav;
    ArrayList<People> humans;

    public GridAdapter(Context context, ArrayList<People> objects) {
        super(context, 0, objects);
        humans = objects;
        mCheckStates = new SparseBooleanArray(objects.size());
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return humans.size();
    }

    @Override
    public People getItem(int position) {
        return humans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View vi=convertView;
        if(convertView==null)
            vi = mInflater.inflate(R.layout.single_item, null);

        tv= (TextView) vi.findViewById(R.id.textView1GV);
        tvNameInitialFav = (TextView) vi.findViewById(R.id.tvNameInitialFav);
        CircularImageView circularImageView = (CircularImageView)vi.findViewById(R.id.civImage);

        if(humans.get(position).getImg()!=null){
            tvNameInitialFav.setVisibility(View.INVISIBLE);
            circularImageView.setVisibility(View.VISIBLE);
            Uri imgUri=Uri.parse(humans.get(position).getImg());
            circularImageView.setImageURI(imgUri);
            circularImageView.setSelectorStrokeWidth(10);
            circularImageView.addShadow();

        }
        else {
            circularImageView.setVisibility(View.INVISIBLE);

            String initial = "";
            String[] split = humans.get(position).getName().split(" ");
            for(String value : split){
                initial += value.substring(0,1);
            }

            tvNameInitialFav.setText( initial );
            tvNameInitialFav.setVisibility(View.VISIBLE);
        }

        tv.setText(humans.get(position).getName());


        return vi;
    }


}