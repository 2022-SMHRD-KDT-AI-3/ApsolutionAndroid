package com.study.materialcalendarview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class disAdapter extends BaseAdapter {

    Context context;
    //레이아웃
    int layout;
    //담을 배열 생성
    ArrayList<disVO> list;
    //객체 배열
    LayoutInflater inflater;
    disviewholder holder;

    public disAdapter(Context context, int layout, ArrayList<disVO> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }






    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        if(view==null){
            //list_item.xml -> view 객체로 변환
            view = inflater.inflate(layout, viewGroup, false);

            //list_item.xml에 정의된 View객체들을 초기화
            holder = new disviewholder(view);

            //view의 상태값을 저장하는 메소드
            view.setTag(holder);
        }else {
            holder=(disviewholder) view.getTag();
        }

        disVO vo = list.get(i);

        holder.getImg().setImageResource(vo.getDis_img());
        holder.getDis_name().setText(vo.getDis_name());
        holder.getDis_data().setText(vo.getDis_data());
        holder.getDis_date().setText(vo.getDis_date());

        return view;
    }

}
