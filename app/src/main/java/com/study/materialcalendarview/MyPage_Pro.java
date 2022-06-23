package com.study.materialcalendarview;

import static com.study.materialcalendarview.CameraActivity.GET_GALLERY_IMAGE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
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

public class MyPage_Pro extends AppCompatActivity {

    Button pro_button, photo_button;
    ImageButton mypage_pro_back;
    TextView proregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_pro);

        pro_button = findViewById(R.id.pro_button);
        photo_button = findViewById(R.id.photo_button);
        proregister = findViewById(R.id.proregister);
        mypage_pro_back = findViewById(R.id.mypage_pro_back);



        pro_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String detail = proregister.getText().toString();


                proregister.setText(detail);

                proregister.clearFocus();


                SharedPreferences spf = getSharedPreferences("test", Context.MODE_PRIVATE);

                String Login_id = spf.getString("id","0");

                proWrite(Login_id, detail);


                //토스트 전문가 등록이 완료되었습니다.
                easyToast("전문가 등록이 완료되었습니다.");




//                Intent intent = new Intent(MyPage_Pro.this, MyPage_Main.class);
//                startActivity(intent);

                pro_button.setText("수정하기");
                finish();

            }
        });
        photo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gallery_open_intent();
            }
        });

        mypage_pro_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        return;

    }

    public void proWrite(String Login_id, String detail){
        String url = "http://172.30.1.23:3000/member/proregister";


        //JSON형식으로 데이터 통신을 진행합니다!
        JSONObject testjson = new JSONObject();
        try {
            testjson.put("id", Login_id);
            //사진
            //testjson.put("poto", pro_poto);
            testjson.put("detail", detail);

            String jsonString = testjson.toString(); //완성된 json 포맷

            //이제 전송해볼까요?
            final RequestQueue requestQueue = Volley.newRequestQueue(MyPage_Pro.this);
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



    void easyToast(String str){
        Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();
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
    private void gallery_open_intent() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GET_GALLERY_IMAGE);
        photo_button.setText("다시선택");

    }

}