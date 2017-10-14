package com.example.android.mytodo;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by shruti on 09-09-2017.
 */

public class customAdapter extends ArrayAdapter<schedules> {
    Context mContext;
    ArrayList<schedules> mItems;
    boolean[] checkBoxState;

     public customAdapter(@NonNull Context context, ArrayList<schedules> schedule){
         super(context,0);
         mContext = context;
         mItems = schedule;
         checkBoxState = new boolean[mItems.size()*10];

     }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
             convertView = LayoutInflater.from(mContext).inflate(R.layout.row_layout,null);
            viewHolder vHolder = new viewHolder();
         vHolder.title =   (TextView)convertView.findViewById(R.id.title);
vHolder.description =   (TextView)convertView.findViewById(R.id.rowTextView);
            vHolder.checkBox = (CheckBox)convertView.findViewById(R.id.checkBox);
convertView.setTag(vHolder);
        }

        viewHolder vholder  = (viewHolder) convertView.getTag();




        schedules item = mItems.get(position);
       vholder.title.setText(item.getTitle());
        vholder.description.setText(item.getDescription());
     //   vholder.checkBox.setChecked(checkBoxState[position]);
//        if(vholder.checkBox.isChecked()){
//            vholder.title.setPaintFlags( vholder.title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//
//        }


        //for managing the state of the boolean
        //array according to the state of the
        //CheckBox

    /*    vholder.checkBox.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(((CheckBox)v).isChecked())
                    checkBoxState[position]=true;
                else
                    checkBoxState[position]=false;

            }
        });*/


return convertView;
    }

static class viewHolder {
    TextView title;
    TextView description;
    CheckBox checkBox;
}}