package com.study.materialcalendarview;

import android.content.Intent;
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

public class JoinActivity extends AppCompatActivity {

    EditText join_id;
    EditText join_pw;
    EditText join_pwcheck;
    EditText join_name;
    EditText join_tel;
    EditText join_add;
    Button btnjoin;
    ImageButton join_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        join_id = findViewById(R.id.join_id);
        join_pw = findViewById(R.id.join_pw);
        join_pwcheck = findViewById(R.id.join_pwcheck);
        join_name = findViewById(R.id.join_name);
        join_tel = findViewById(R.id.join_tel);
        join_add = findViewById(R.id.join_add);
        btnjoin = findViewById(R.id.btnjoin);
        join_back = findViewById(R.id.join_back);



        join_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });




        btnjoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //사용자가 입력한 데이터를 변수에 저장
                String user_id = join_id.getText().toString();
                String user_pw = join_pw.getText().toString();
                String user_pwc = join_pwcheck.getText().toString();
                String user_name = join_name.getText().toString();
                String user_phone = join_tel.getText().toString();
                String user_add = join_add.getText().toString();

                requestJoin(user_id,user_pw,user_name,user_phone,user_add);
                //LoginActivity로 이동하는 기능 구현
                Intent intent = new Intent(JoinActivity.this, LoginActivity.class);

                //Intent 객체에 전달할 데이터를 저장
//                intent.putExtra("user_id",user_id);
//                intent.putExtra("user_pw",user_pw);
//                intent.putExtra("user_pwc",user_pwc);
//                intent.putExtra("user_name",user_name);
//                intent.putExtra("user_phone",user_phone);
//                intent.putExtra("user_add",user_add);

                //Intent 객체를 실행시키는 메소드(이동)
                startActivity(intent);

                //현재 엑티비티를 종류하는 메소드
                //finish();
            }
        });

    }
    // 전체 코드 추가
    public void requestJoin(String ID, String PW, String Name, String Phone, String ADD){
        String url = "http://172.30.1.30:3000/member/join";

        //JSON형식으로 데이터 통신을 진행합니다!
        JSONObject testjson = new JSONObject();
        try {
            testjson.put("id", ID);
            testjson.put("pw", PW);
            testjson.put("name", Name);
            testjson.put("tel", Phone);
            testjson.put("add", ADD);
            String jsonString = testjson.toString(); //완성된 json 포맷

            //이제 전송해볼까요?
            final RequestQueue requestQueue = Volley.newRequestQueue(JoinActivity.this);
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
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
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



}