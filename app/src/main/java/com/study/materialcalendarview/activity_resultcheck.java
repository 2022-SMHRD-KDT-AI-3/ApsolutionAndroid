package com.study.materialcalendarview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;


import org.w3c.dom.Text;

public class activity_resultcheck extends AppCompatActivity {
    Button btnstart;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultcheck);


        btnstart = (Button)findViewById(R.id.btn_start);
        SharedPreferences spf = getSharedPreferences("test", Context.MODE_PRIVATE);
        String Login_id = spf.getString("id","0");

        //시간
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");




        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_resultcheck.this,Result_List_Activity.class);

                startActivity(intent);
            }
        });



    }



}