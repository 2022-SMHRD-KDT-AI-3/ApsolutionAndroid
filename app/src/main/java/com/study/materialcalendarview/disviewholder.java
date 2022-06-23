package com.study.materialcalendarview;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class disviewholder {
    private ImageView dis_img;
    private TextView dis_name;
    private TextView dis_data;
    private  TextView dis_date;

    public disviewholder(View itemView){
        dis_img = itemView.findViewById(R.id.disimg);
        dis_name = itemView.findViewById(R.id.disname);
        dis_data = itemView.findViewById(R.id.disdata);
        dis_date = itemView.findViewById(R.id.disdate);
    }
    public ImageView getImg(){
        return dis_img;
    }
    public TextView getDis_name(){
        return dis_name;
    }
    public TextView getDis_data(){
        return dis_data;
    }
    public TextView getDis_date(){
        return dis_date;
    }




}
