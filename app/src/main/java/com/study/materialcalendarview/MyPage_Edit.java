package com.study.materialcalendarview;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.jar.Attributes;

public class MyPage_Edit extends AppCompatActivity {

    Button btnrevise;
    ImageButton mypage_edit_back;
    EditText etdname, ettel, etadd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_edit);

        btnrevise = findViewById(R.id.btnrevise);
        etdname = findViewById(R.id.etname);
        ettel = findViewById(R.id.ettel);
        etadd = findViewById(R.id.etadd);
        mypage_edit_back = findViewById(R.id.mypage_edit_back);

        SharedPreferences spf = getSharedPreferences("test", Context.MODE_PRIVATE);

        String Login_id = spf.getString("id","0");

        reqProfile(Login_id);



        btnrevise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = etdname.getText().toString();
                String tel = ettel.getText().toString();
                String add = etadd.getText().toString();

                etdname.setText(name);
                ettel.setText(tel);
                etadd.setText(add);


                etdname.clearFocus();
                ettel.clearFocus();
                etadd.clearFocus();
//                SharedPreferences spf = getSharedPreferences("test", Context.MODE_PRIVATE);
//
//                String Login_id = spf.getString("id","0");

                editProfile(Login_id,name,tel,add);
                finish();
                //Toast.makeText(MyPage_Edit.this ,"", Toast.LENGTH_SHORT).show();


//                Intent intent = new Intent(MyPage_Edit.this, MyPage_Main.class);
//                startActivity(intent);


            }

        });
        mypage_edit_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        return;

    }

    // ?????? ?????? ??????
    public void reqProfile(String ID){
        String url = "http://172.30.1.23:3000/member/profileshow";

        //JSON???????????? ????????? ????????? ???????????????!
        JSONObject testjson = new JSONObject();
        try {
            //???????????? edittext??? id??? pw?????? ????????? put???????????? : ???????????? json???????????? ?????? ?????????????????????.
            testjson.put("id", ID);

            String jsonString = testjson.toString(); //????????? json ??????

            //?????? ???????????????????
            final RequestQueue requestQueue = Volley.newRequestQueue(MyPage_Edit.this);
            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,testjson, new Response.Listener<JSONObject>() {

                //????????? ????????? ????????? ?????? ??? ????????? ?????? ???????????????.
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        //?????? json????????? ????????? ??????
                        JSONObject jsonObject = new JSONObject(response.toString());

                        //key?????? ?????? value?????? ?????? ???????????????.
                        //String resultId = jsonObject.getString("approve_id");
                        String resultName = jsonObject.getString("approve_name");
                        String resultTel = jsonObject.getString("approve_tel");
                        String resultAdd = jsonObject.getString("approve_add");

//                        if(resultName.equals("OK")&resultTel.equals("OK")&resultAdd.equals("OK")){
////                           Toast.makeText(getApplicationContext(),"????????? ??????",Toast.LENGTH_SHORT).show();
////                           Intent intent = new Intent(getApplicationContext(), MainPageActivity.class);
////                           startActivity(intent);
////                           finish();
//
//
//                        }else if(resultId.equals("not_ex_id")){
//                            //easyToast("???????????? ?????? ??????????????????.");
//                        }else{
//                            //easyToast("??????????????? ??????????????????!");
//                        }

                        etdname.setText(resultName);
                        ettel.setText(resultTel);
                        etadd.setText(resultAdd);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //????????? ????????? ?????? ??? ?????? ????????? ????????? ?????? ?????? ????????? ???????????????.
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
    }

    public void editProfile(String Login_id, String name, String tel, String add){
        String url = "http://172.30.1.23:3000/member/profileedit";

        JSONObject testjson = new JSONObject();
        try {
            testjson.put("id", Login_id);
            testjson.put("name", name);
            testjson.put("tel", tel);
            testjson.put("add", add);



            String jsonString = testjson.toString(); //????????? json ??????
            //?????? ???????????????????
            final RequestQueue requestQueue = Volley.newRequestQueue(MyPage_Edit.this);
            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,testjson, new Response.Listener<JSONObject>() {

                //????????? ????????? ????????? ?????? ??? ????????? ?????? ???????????????.
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        //?????? json????????? ????????? ??????
                        JSONObject jsonObject = new JSONObject(response.toString());

                        //key?????? ?????? value?????? ?????? ???????????????.
                        //String resultId = jsonObject.getString("approve_id");
                        String resultName = jsonObject.getString("approve_name");
                        String resultTel = jsonObject.getString("approve_tel");
                        String resultAdd = jsonObject.getString("approve_add");

//                        if(resultName.equals("OK")&resultTel.equals("OK")&resultAdd.equals("OK")){
////                           Toast.makeText(getApplicationContext(),"????????? ??????",Toast.LENGTH_SHORT).show();
////                           Intent intent = new Intent(getApplicationContext(), MainPageActivity.class);
////                           startActivity(intent);
////                           finish();
//
//
//                        }else if(resultId.equals("not_ex_id")){
//                            //easyToast("???????????? ?????? ??????????????????.");
//                        }else{
//                            //easyToast("??????????????? ??????????????????!");
//                        }

                        etdname.setText(resultName);
                        ettel.setText(resultTel);
                        etadd.setText(resultAdd);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //????????? ????????? ?????? ??? ?????? ????????? ????????? ?????? ?????? ????????? ???????????????.
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