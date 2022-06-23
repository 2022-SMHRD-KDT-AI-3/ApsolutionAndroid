package com.study.materialcalendarview;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.List;

public class Pro_List extends AppCompatActivity {


    ListView proList;
    ProAdapter adapter;
    ArrayList<ProVO> list;
    ImageButton prolist_back;

//    String[] pro_name={"하성진","김하빈","공지연","유도영","김다솔"};
//    String[] pro_phone={"010-0000-0000","010-1111-1111","010-2222-2222","010-3333-3333","010-4444-4444"};
//    String[] pro_info={"#백엔드 인재 #빛나는 하성진 #사과나무 심어보자","#안드인재 #크롤링인재 ","#안드인재 #데베인재"
//            ,"#딥러닝인재 #잔소리인재","#불쌍한 노예"};

    int[] pro_img={R.drawable.mypage_profile,R.drawable.mypage_profile,R.drawable.mypage_profile,
            R.drawable.mypage_profile,R.drawable.mypage_profile};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prolist);
        proList = findViewById(R.id.proList);
        prolist_back = findViewById(R.id.prolist_back);
        list = new ArrayList<>();
//        for(int i=0;i<pro_name.length;i++){
//            list.add(new ProVO(pro_img[i],pro_name[i],pro_phone[i],pro_info[i]));
//        }
        //R.layout 다음에 layout 이름 적기
        adapter = new ProAdapter(Pro_List.this,R.layout.activity_prolist_contents,list);
        proList.setAdapter(adapter);



//        proList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(
//                        getApplicationContext(),pro_look.class
//                );
//                intent.putExtra("pro_name",list.get(i).getPro_name());
//                intent.putExtra("pro_img",list.get(i).getPro_img());
//                intent.putExtra("pro_phone",list.get(i).getPro_phone());
//                intent.putExtra("pro_info",list.get(i).getPro_info());
//                startActivity(intent);
//
//            }
//        });
        prolist_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }



    public void reqProlist(String ID,String name,String tel,String detail){

        String url = "http://172.30.1.23:3000/member/prolist";


        //JSON형식으로 데이터 통신을 진행합니다!
        JSONObject testjson = new JSONObject();


        try {
            String jsonString = testjson.toString(); //완성된 json 포맷
            JSONArray jsonArray = new JSONArray(list);
            for(int i =0;i<jsonArray.length();i++){
                testjson.put("name",name);
            }
            testjson.put("id", ID);
            testjson.put("name", name);
            testjson.put("tel", tel);
            testjson.put("detail",detail);


            //이제 전송해볼까요?
            final RequestQueue requestQueue = Volley.newRequestQueue(Pro_List.this);
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

}