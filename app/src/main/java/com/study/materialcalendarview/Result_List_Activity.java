package com.study.materialcalendarview;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Result_List_Activity extends AppCompatActivity {
    ListView disList;
    disAdapter adapter;
    ArrayList<disVO> list;
    ImageButton resultlist_back;



    int[] dis_img={R.drawable.apple_tree, R.drawable.apple_tree, R.drawable.apple_tree,
            R.drawable.apple_tree, R.drawable.apple_tree, R.drawable.apple_tree,
            R.drawable.apple_tree, R.drawable.apple_tree, R.drawable.apple_tree};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultlist);

        resultlist_back = findViewById(R.id.resultlist_back);



        //현재 로그인한 정보
        SharedPreferences spf = getSharedPreferences("test", Context.MODE_PRIVATE);
        String Login_id = spf.getString("id","0");

        //리스트뷰 보여주기
        result_list(Login_id);



        resultlist_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        return;
    }
    public void result_list(String ID){

        String url = "http://172.30.1.23:3000/member/result_list";


        //JSON형식으로 데이터 통신을 진행합니다!
        JSONObject testjson = new JSONObject();


        try {
            testjson.put("id", ID);
            String jsonString = testjson.toString(); //완성된 json 포맷

            //이제 전송해볼까요?
            final RequestQueue requestQueue = Volley.newRequestQueue(Result_List_Activity.this);
            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,testjson, new Response.Listener<JSONObject>() {

                //데이터 전달을 끝내고 이제 그 응답을 받을 차례입니다.
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        //받은 json형식의 응답을 받아
                        JSONObject jsonObject = new JSONObject(response.toString());

                        JSONArray result = jsonObject.getJSONArray("all");//키값

                        String[] dis_name1=new String[result.length()];

                        String[] dis_date2=new String[result.length()];
                        int k = result.length();
                        for(int i=0; i<result.length(); i++){
                            JSONObject result_all = result.getJSONObject(i);
                            String dis = result_all.getString("dis_name");
                            String time = result_all.getString("ins_time");
                            dis_name1[i] = dis;
                            dis_date2[i] = time;
                        }
                        int[] dis_img1=new int[dis_name1.length];
                        for (int i=0; i<dis_name1.length; i++){
                            if (dis_name1[i].equals("탄저병")){
                                dis_img1[i] = R.drawable.anth;
                            }else if (dis_name1[i].equals("과수화상병")){
                                dis_img1[i]= R.drawable.disease;
                            }else if (dis_name1[i].equals("부란병")){
                                dis_img1[i]= R.drawable.vals;
                            }else if (dis_name1[i].equals("점무늬낙엽병")){
                                dis_img1[i] = R.drawable.blot;
                            }else if (dis_name1[i].equals("갈색무늬병")){
                                dis_img1[i] = R.drawable.leaf;
                            }else{
                                dis_img1[i] = R.drawable.nor;
                            }
                        }
                        //JSONObject result_all = result.getJSONObject(1);
                        //String a = result_all.getString("dis_name");
                        //Log.v("제발....",a);


                        disList = findViewById(R.id.disList);
                        list = new ArrayList<>();
//                        for(int i=0;i<dis_name1.length;i++){
//                            list.add(new disVO(dis_img1[i], dis_name1[i], dis_date2[i]));
//                        }
                        for(int i=k-1;i>=0;i--){
                            list.add(new disVO(dis_img1[i], dis_name1[i], dis_date2[i]));
                        }
                        //R.layout 다음에 layout 이름 적기
                        adapter = new disAdapter(Result_List_Activity.this, R.layout.activity_resultlist_contents,list);
                        disList.setAdapter(adapter);
                        disList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent intent = new Intent(
                                        getApplicationContext(), Ins_Result.class
                                );
                                intent.putExtra("dis_name",list.get(i).getDis_name());
                                intent.putExtra("dis_data",list.get(i).getDis_data());
                                intent.putExtra("dis_date",list.get(i).getDis_date());
                                intent.putExtra("dis_img1",list.get(i).getDis_img());
                                startActivity(intent);
                            }
                        });




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
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(jsonObjectRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}