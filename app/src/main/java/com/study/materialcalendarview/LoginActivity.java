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

public class LoginActivity extends AppCompatActivity {
    //변수선언
    EditText ID;
    EditText PW;
    Button loginbtn;
    Button joinbtn;

    //로그인 데이터 받기
    //String inputID, InputPW;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //초기화
        ID = findViewById(R.id.id);
        PW = findViewById(R.id.pw);
        loginbtn = findViewById(R.id.loginbtn);
        joinbtn = findViewById(R.id.joinbtn);


        //Intent객체생성
        Intent intent = getIntent();
        String joinid = intent.getStringExtra("user_id");
        String joinpw = intent.getStringExtra("user_pw");

        SharedPreferences spf = getSharedPreferences("test", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();

        joinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);

            }
        });


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id1 = ID.getText().toString();
                String pw2 = PW.getText().toString();

//              if(id1.equals(joinid) && pw2.equals(joinpw)){
              /*  if(id1.equals("apple") && pw2.equals("1234")){
                    Toast.makeText(getApplicationContext(), String.format("%s님 환영합니다.", id1),  Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(getApplicationContext(), String.format("회원정보를 확인해주세요"), Toast.LENGTH_SHORT).show();
                }*/
                requestLogin(id1, pw2);

                editor.putString("id",id1);
                editor.putString("pw","");
                editor.commit();
            }
        });


    }

    // 전체 코드 추가
    public void requestLogin(String ID, String PW){
        String url = "http://172.30.1.30:3000/member/login";

        //JSON형식으로 데이터 통신을 진행합니다!
        JSONObject testjson = new JSONObject();
        try {
            //입력해둔 edittext의 id와 pw값을 받아와 put해줍니다 : 데이터를 json형식으로 바꿔 넣어주었습니다.
            testjson.put("id", ID);
            testjson.put("password", PW);
            String jsonString = testjson.toString(); //완성된 json 포맷

            //이제 전송해볼까요?
            final RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,testjson, new Response.Listener<JSONObject>() {

                //데이터 전달을 끝내고 이제 그 응답을 받을 차례입니다.
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        //받은 json형식의 응답을 받아
                        JSONObject jsonObject = new JSONObject(response.toString());

                        //key값에 따라 value값을 쪼개 받아옵니다.
                        String resultId = jsonObject.getString("approve_id");
                        String resultPassword = jsonObject.getString("approve_pw");

                        if(resultId.equals("OK") & resultPassword.equals("OK")){
                            Toast.makeText(getApplicationContext(),"로그인 성공",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainPageActivity.class);
                            startActivity(intent);
                            finish();
                        }else if(resultId.equals("not_ex_id")){
                            easyToast("존재하지 않는 아이디입니다.");
                        }else{
                            easyToast("비밀번호를 확인해주세요!");
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


