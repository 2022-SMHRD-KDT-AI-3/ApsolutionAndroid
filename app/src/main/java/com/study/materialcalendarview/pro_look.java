package com.study.materialcalendarview;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class pro_look extends AppCompatActivity implements View.OnClickListener {

    TextView pro_name, pro_phone, pro_info, pro_home;
    Button btnCall;
    ImageButton pro_look_back;
    ImageView pro_img;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_look);

        pro_name = (TextView)findViewById(R.id.proname);
        pro_phone = (TextView)findViewById(R.id.prophone);
        pro_info = (TextView)findViewById(R.id.proinfo);
        pro_img = (ImageView)findViewById(R.id.proimg);
        pro_look_back = findViewById(R.id.pro_look_back);
        pro_home = findViewById(R.id.prohome);

//        if(getIntent().getStringExtra("page")!=null){
//            if(getIntent().getStringExtra("page").equals("ProList_Fragment4")){
//                getSupportFragmentManager().beginTransaction().replace(R.id.container, ProList_Fragment4).commit(); //
//
//            }
//        }else{
//            //처음 띄울 프래그먼트 화면 설정
//            //replace(프래그먼트를 보여줄 FrameLayout id, 화면에 출력한 프래그먼트 객체)
//            getSupportFragmentManager().beginTransaction().replace(R.id.container, MainPage_Fragment5).commit(); //
//
//        }

        Intent intent = getIntent();

        String pro = intent.getStringExtra("pro_name");
        String tel_num = intent.getStringExtra("pro_phone");
//        pro_name.setText(intent.getStringExtra("pro_name"));
//        pro_phone.setText(intent.getStringExtra("pro_phone"));
//        pro_info.setText(intent.getStringExtra("pro_info"));
        pro_img.setImageResource(intent.getIntExtra("pro_img",0));



        btnCall=findViewById(R.id.btnCall);

        prolistShow(pro);



        btnCall.setOnClickListener(this);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(
                        Intent.ACTION_CALL,
                        Uri.parse("tel:"+tel_num)
                );

                //액티비치에서 실행하는 경우 -> 권한 체크
                if ( ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) !=
                        PackageManager.PERMISSION_GRANTED ) {

                    //권한 요청
                    ActivityCompat.requestPermissions(
                            pro_look.this,
                            new String[]{Manifest.permission.CALL_PHONE}, 0);
                    //return : 이 구문을 만나면 여기서 끝내겠다 !!
                    return;
                }

                startActivity(callIntent);
            }
        });
        pro_look_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    @Override
    public void onClick(View view) {
    }


    public void prolistShow(String pro) {
        String url = "http://172.30.1.23:3000/member/prodetail";

        //JSON형식으로 데이터 통신을 진행합니다!
        JSONObject testjson = new JSONObject();


        try {

            //입력해둔 edittext의 id와 pw값을 받아와 put해줍니다 : 데이터를 json형식으로 바꿔 넣어주었습니다.
            testjson.put("pro_name", pro);

            //String jsonString = testjson.toString(); //완성된 json 포맷

            //이제 전송해볼까요?
            final RequestQueue requestQueue = Volley.newRequestQueue(pro_look.this);
            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, testjson, new Response.Listener<JSONObject>() {

                //데이터 전달을 끝내고 이제 그 응답을 받을 차례입니다.
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        //받은 json형식의 응답을 받아
                        JSONObject jsonObject = new JSONObject(response.toString());

                        //key값에 따라 value값을 쪼개 받아옵니다.
                        //String resultId = jsonObject.getString("approve_id");
                        String resultName = jsonObject.getString("approve_name");
                        String resultTel = jsonObject.getString("approve_tel");
                        String resultadd = jsonObject.getString("approve_add");
                        String resultDetail = jsonObject.getString("approve_detail");

//                        if(resultName.equals("OK")&resultTel.equals("OK")&resultAdd.equals("OK")){
//                           Toast.makeText(getApplicationContext(),"로그인 성공",Toast.LENGTH_SHORT).show();
//                           Intent intent = new Intent(getApplicationContext(), MainPageActivity.class);
//                           startActivity(intent);
//                           finish();
//
//
//                        }else if(resultId.equals("not_ex_id")){
//                            //easyToast("존재하지 않는 아이디입니다.");
//                        }else{
//                            //easyToast("비밀번호를 확인해주세요!");
//                        }

                        pro_name.setText(resultName);
                        pro_phone.setText(resultTel);
                        pro_home.setText(resultadd);
                        pro_info.setText(resultDetail);


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

        } finally {
        }

    }
}