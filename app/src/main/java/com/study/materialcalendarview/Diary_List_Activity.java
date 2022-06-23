package com.study.materialcalendarview;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.study.decorators.EventDecorator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Diary_List_Activity extends AppCompatActivity {

    EditText field_c, pest_c, pest_use_c, water_c, ph_c, sects_c, detail_c;
    Button btnwrite;
    ImageView diarywrite_back;
    RequestQueue queue;
    StringRequest String_request_diary;
    MaterialCalendarView materialCalendarView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_write);




        // id와 날짜 가져오기
        SharedPreferences spf = getSharedPreferences("test", Context.MODE_PRIVATE);
        String Login_id = spf.getString("id","0");
        Intent i =getIntent();
        String today = i.getStringExtra("today");


        Log.d(Login_id, "onCreate: ");

        // Intent i = getIntent();
        //String Year = i.getStringExtra("Year");
        //Log.d(Year, "onCreate: ");


        field_c = findViewById(R.id.field_c);
        pest_c = findViewById(R.id.pest_c);
        pest_use_c = findViewById(R.id.pest_use_c);
        water_c = findViewById(R.id.water_c);
        ph_c = findViewById(R.id.ph_c);
        sects_c = findViewById(R.id.sects_c);
        detail_c = findViewById(R.id.detail_c);

        btnwrite = findViewById(R.id.btnwrite);
        diarywrite_back = findViewById(R.id.diary_write_back);
        queue = Volley.newRequestQueue(Diary_List_Activity.this);




        diaryShow(Login_id, today);
        //diaryShow2("kong", "2022-06-18");





        btnwrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i =getIntent();
                String today = i.getStringExtra("today");

                String field = field_c.getText().toString();
                String pest = pest_c.getText().toString();
                String pest_use = pest_use_c.getText().toString();
                String water = water_c.getText().toString();
                String ph = ph_c.getText().toString();
                String sects = sects_c.getText().toString();
                String detail = detail_c.getText().toString();

                field_c.setText(field);
                pest_c.setText(pest);
                pest_use_c.setText(pest_use);
                water_c.setText(water);
                ph_c.setText(ph);
                sects_c.setText(sects);
                detail_c.setText(detail);

                field_c.clearFocus();
                pest_c.clearFocus();
                pest_use_c.clearFocus();
                water_c.clearFocus();
                ph_c.clearFocus();
                sects_c.clearFocus();
                detail_c.clearFocus();

                SharedPreferences spf = getSharedPreferences("test", Context.MODE_PRIVATE);
                String Login_id = spf.getString("id","0");

                diaryWrite(Login_id, today, field, pest, pest_use, water, ph, sects, detail);


//                Intent intent = new Intent(Diary_List_Activity.this, Diary_Cal_Fragment2.class);
//                startActivity(intent);


                btnwrite.setText("수정하기");
                finish();


            }
        });

        diarywrite_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }




    public void diaryWrite(String Login_id, String diary_date, String field, String pest, String pest_use, String water, String ph, String sects, String detail){
        String url = "http://172.30.1.30:3000/member/diarywrite";


        //JSON형식으로 데이터 통신을 진행합니다!
        JSONObject testjson = new JSONObject();
        try {
            testjson.put("id", Login_id);
            testjson.put("diary_date", diary_date);
            testjson.put("field", field);
            testjson.put("pest", pest);
            testjson.put("pest_use", pest_use);
            testjson.put("water", water);
            testjson.put("ph", ph);
            testjson.put("sects", sects);
            testjson.put("detail", detail);

            String jsonString = testjson.toString(); //완성된 json 포맷

            //이제 전송해볼까요?
            final RequestQueue requestQueue = Volley.newRequestQueue(Diary_List_Activity.this);
            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,testjson, new Response.Listener<JSONObject>() {

                //데이터 전달을 끝내고 이제 그 응답을 받을 차례입니다.
                @Override
                public void onResponse(JSONObject response) {

                }
                //서버로 데이터 전달 및 응답 받기에 실패한 경우 아래 코드가 실행됩니다.
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(jsonObjectRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }



    public void diaryShow(String ID, String diary_date) {
        String url = "http://172.30.1.23:3000/member/diaryShow";

        //JSON형식으로 데이터 통신을 진행합니다!
        JSONObject testjson = new JSONObject();
        try {
            //입력해둔 edittext의 id와 pw값을 받아와 put해줍니다 : 데이터를 json형식으로 바꿔 넣어주었습니다.
            testjson.put("id", ID);
            testjson.put("diary_date", diary_date);



            String jsonString = testjson.toString(); //완성된 json 포맷

            //이제 전송해볼까요?
            final RequestQueue requestQueue = Volley.newRequestQueue(Diary_List_Activity.this);
            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, testjson, new Response.Listener<JSONObject>() {

                //데이터 전달을 끝내고 이제 그 응답을 받을 차례입니다.
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        //받은 json형식의 응답을 받아
                        JSONObject jsonObject = new JSONObject(response.toString());

                        //key값에 따라 value값을 쪼개 받아옵니다.
                        //String resultId = jsonObject.getString("approve_id");
                        String resultfield = jsonObject.getString("approve_field");
                        String resultpest = jsonObject.getString("approve_pest");
                        String resultpest_use = jsonObject.getString("approve_pest_use");
                        String resultwater = jsonObject.getString("approve_water");
                        String resultph = jsonObject.getString("approve_ph");
                        String resultsects = jsonObject.getString("approve_sects");
                        String resultdetail = jsonObject.getString("approve_detail");
                        String resultdate = jsonObject.getString("approve_diary_date");


                        Intent i =getIntent();
                        String today = i.getStringExtra("today");
                        if(resultdate.equals(today)){

                            field_c.setText(resultfield);
                            pest_c.setText(resultpest);
                            pest_use_c.setText(resultpest_use);
                            water_c.setText(resultwater);
                            ph_c.setText(resultph);
                            sects_c.setText(resultsects);
                            detail_c.setText(resultdetail);

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //서버로 데이터 전달 및 응답 받기에 실패한 경우 아래 코드가 실행됩니다.
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(jsonObjectRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    };






    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View focusView = getCurrentFocus();
        if (focusView != null) {
            Rect rect = new Rect();
            focusView.getGlobalVisibleRect(rect);
            int x = (int) ev.getX(), y = (int) ev.getY();
            if (!rect.contains(x, y)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null)
                    imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
                focusView.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

}