package com.study.materialcalendarview;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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
import org.w3c.dom.Text;

public class Ins_Result extends AppCompatActivity {


    TextView dis_name, dis_data,dis_persent;
    ImageButton result_back;
    Button btnpro;
    ImageView dis_img;

    int[] all_img ={R.drawable.anth, R.drawable.disease, R.drawable.vals,
            R.drawable.leaf, R.drawable.nor, R.drawable.blot};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        result_back = findViewById(R.id.result_back);

        dis_name = findViewById(R.id.disname);
        dis_data = findViewById(R.id.disdata);
        dis_persent = findViewById(R.id.dispercent);
        //dis_date = findViewById(R.id.disdate);
        dis_img = findViewById(R.id.disimg);
        btnpro = findViewById(R.id.btnpro);



        Intent intent = getIntent();
        String DName = intent.getStringExtra("dis_name");
        String DDate = intent.getStringExtra("dis_date");

        dis_name.setText(intent.getStringExtra("dis_name"));
        dis_data.setText(intent.getStringExtra("dis_data"));
        dis_persent.setText(intent.getStringExtra("ins_percent"));
//        dis_date.setText(intent.getStringExtra("dis_date"));
        //dis_img.setImageResource(intent.getIntExtra("dis_img", 0));
        SharedPreferences spf = getSharedPreferences("test", Context.MODE_PRIVATE);

        String Login_id = spf.getString("id","0");
        insresult(Login_id, DName, DDate);


        result_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Ins_Result.this, MainPageActivity.class);
                intent.putExtra("page", "ProList_Fragment4");
                startActivity(intent);

            }
        });



    }
    public void insresult(String ID, String DName, String DDate) {
        String url = "http://172.30.1.30:3000/member/resultShow";

        //JSON형식으로 데이터 통신을 진행합니다!
        JSONObject testjson = new JSONObject();
        try {
            //입력해둔 edittext의 id와 pw값을 받아와 put해줍니다 : 데이터를 json형식으로 바꿔 넣어주었습니다.
            testjson.put("id", ID);
            testjson.put("Dname", DName);
            testjson.put("Date",DDate);

            String jsonString = testjson.toString(); //완성된 json 포맷

            //이제 전송해볼까요?
            final RequestQueue requestQueue = Volley.newRequestQueue(Ins_Result.this);
            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, testjson, new Response.Listener<JSONObject>() {

                //데이터 전달을 끝내고 이제 그 응답을 받을 차례입니다.
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        //받은 json형식의 응답을 받아
                        JSONObject jsonObject = new JSONObject(response.toString());

                        //key값에 따라 value값을 쪼개 받아옵니다.
                        //String resultId = jsonObject.getString("approve_id");
                        //String resultName = jsonObject.getString("approve_disname");
                        // String resultpoto = jsonObject.getString("ins_poto");
                        String resultpercent = jsonObject.getString("approve_inspercent");
                        String resultdata = jsonObject.getString("approve_disdata");

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

                        dis_name.setText(DName);
                        dis_persent.setText(resultpercent+'%');
                        dis_data.setText(resultdata);

                        if (DName.equals("탄저병")){
                            dis_img.setImageResource(R.drawable.anth);
                        }else if (DName.equals("과수화상병")){
                            dis_img.setImageResource(R.drawable.disease);
                        }else if (DName.equals("부란병")){
                            dis_img.setImageResource(R.drawable.vals);
                        }else if (DName.equals("점무늬낙엽병")){
                            dis_img.setImageResource(R.drawable.blot);
                        }else if (DName.equals("갈색무늬병")){
                            dis_img.setImageResource(R.drawable.leaf);
                        }else{
                            dis_img.setImageResource(R.drawable.nor);
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



}
