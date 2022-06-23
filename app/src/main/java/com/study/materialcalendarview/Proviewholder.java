package com.study.materialcalendarview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Proviewholder {
    private ImageView pro_img;
    private TextView pro_name;
    private TextView pro_phone;
    private TextView pro_info;


    public Proviewholder(View itemView){
        pro_img = itemView.findViewById(R.id.proimg);
        pro_name = itemView.findViewById(R.id.proname);
        pro_phone = itemView.findViewById(R.id.prophone);
        pro_info = itemView.findViewById(R.id.proinfo);

    }

    public ImageView getPro_img() {
        return pro_img;
    }

    public TextView getPro_name() {
        return pro_name;
    }

    public TextView getPro_phone() {
        return pro_phone;
    }

    public TextView getPro_info() {
        return pro_info;
    }
}
