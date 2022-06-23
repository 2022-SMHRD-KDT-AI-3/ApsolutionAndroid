package com.study.materialcalendarview;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

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


public class ProList_Fragment4 extends Fragment {


    ListView proList;
    ProAdapter adapter;
    ArrayList<ProVO> list;
    ImageButton prolist_back;

//    String[] pro_name={"하성진","김하빈","공지연","유도영","김다솔"};
//    String[] pro_phone={"010-0000-0000","010-1111-1111","010-2222-2222","010-3333-3333","010-4444-4444"};
//    String[] pro_info={"#백엔드 인재 #빛나는 #사과나무 심어보자","#안드인재 #크롤링인재 ","#안드인재 #데베인재"
//            ,"#딥러닝인재 #잔소리인재","#불쌍한 노예"};

    int[] pro_img={R.drawable.mypage_profile,R.drawable.mypage_profile,R.drawable.mypage_profile,R.drawable.mypage_profile,
            R.drawable.mypage_profile,R.drawable.mypage_profile,R.drawable.mypage_profile,R.drawable.mypage_profile,R.drawable.mypage_profile,R.drawable.mypage_profile,R.drawable.mypage_profile
            ,R.drawable.mypage_profile,R.drawable.mypage_profile,R.drawable.mypage_profile,R.drawable.mypage_profile,R.drawable.mypage_profile,R.drawable.mypage_profile,R.drawable.mypage_profile};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_prolist, container, false);


        prolist_back = view.findViewById(R.id.prolist_back);

        //현재 로그인한 정보
        SharedPreferences spf = getActivity().getSharedPreferences("test", Context.MODE_PRIVATE);
        String Login_id = spf.getString("id","0");

        pro_list(Login_id);

        prolist_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new MainPage_Fragment5()).commit();
            }


        });



        return view;
    }

    public void pro_list(String ID){
        String url = "http://172.30.1.23:3000/member/pro_list";

        //JSON형식으로 데이터 통신을 진행합니다!
        JSONObject testjson = new JSONObject();

        try {
            testjson.put("id", ID);
            String jsonString = testjson.toString(); //완성된 json 포맷

            //이제 전송해볼까요?
            final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,testjson, new Response.Listener<JSONObject>() {

                //데이터 전달을 끝내고 이제 그 응답을 받을 차례입니다.
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        //받은 json형식의 응답을 받아
                        JSONObject jsonObject = new JSONObject(response.toString());

                        JSONArray result = jsonObject.getJSONArray("all");//키값
//                        JSONObject result_all = result.getJSONObject(1);
//                        String a = result_all.getString("pro_detail");
//                        Log.v("나올거...지",a);
                        String[] user_name=new String[result.length()];
                        String[] user_tel=new String[result.length()];
                        String[] pro_detail=new String[result.length()];
                        for(int i=0; i<result.length(); i++){
                            JSONObject result_all = result.getJSONObject(i);
                            String user = result_all.getString("user_name");
                            String tel = result_all.getString("user_tel");
                            String detail = result_all.getString("pro_detail");
                            user_name[i] = user;
                            user_tel[i] = tel;
                            pro_detail[i] = detail;
                        }
                        proList = getView().findViewById(R.id.proList);
                        list = new ArrayList<>();
                        for (int i = 0; i < user_name.length; i++) {
                            list.add(new ProVO(pro_img[i], user_name[i], user_tel[i], pro_detail[i]));
                        }
                        //R.layout 다음에 layout 이름 적기
                        adapter = new ProAdapter(getActivity(), R.layout.activity_prolist_contents, list);
                        proList.setAdapter(adapter);
                        proList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent intent = new Intent(
                                        getActivity().getApplicationContext(),pro_look.class
                                );
                                intent.putExtra("pro_name",list.get(i).getPro_name());
                                intent.putExtra("pro_img",list.get(i).getPro_img());
                                intent.putExtra("pro_phone",list.get(i).getPro_phone());
                                intent.putExtra("pro_info",list.get(i).getPro_info());
                                Log.v(".......", list.get(i).getPro_name());
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