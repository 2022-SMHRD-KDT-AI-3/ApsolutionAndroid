package com.study.materialcalendarview;

import static android.os.Build.ID;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class MyPage_Main_Fragment3 extends Fragment {

    Button editprofile, registerpro, lastins;
    ImageButton mypage_back;
    TextView myname;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.activity_mypage_main, container, false);
        editprofile =  view.findViewById(R.id.editprofile);
        registerpro = view.findViewById(R.id.registerpro);
        lastins = view.findViewById(R.id.lastins);
        mypage_back = view.findViewById(R.id.mypage_main_back);
        myname = view.findViewById(R.id.myname);

        SharedPreferences spf = getActivity().getSharedPreferences("test", Context.MODE_PRIVATE);

        String Login_id = spf.getString("id","0");

        myname.setText(Login_id);

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyPage_Edit.class);
                getActivity().startActivity(intent);


            }
        });




        registerpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), MyPage_Pro.class);
                getActivity().startActivity(intent);
            }
        });



        lastins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Result_List_Activity.class);
                getActivity().startActivity(intent);
            }
        });

        mypage_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new MainPage_Fragment5()).commit();
            }
        });


        return view;
    }

    }




