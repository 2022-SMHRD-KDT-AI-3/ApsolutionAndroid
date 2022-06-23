package com.study.materialcalendarview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class ProAdapter extends BaseAdapter {
    Context context;
    //레이아웃
    int layout;
    //담을 배열 생성
    ArrayList<ProVO> list;
    //객체 배열
    LayoutInflater inflater;
    Proviewholder holder;

    public ProAdapter(Context context, int layout, ArrayList<ProVO> list) {
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

        if (view == null) {
            view = inflater.inflate(layout, viewGroup, false);

            holder = new Proviewholder(view);

            view.setTag(holder);
        } else {
            holder = (Proviewholder) view.getTag();
        }

        ProVO vo = list.get(i);
        holder.getPro_img().setImageResource(vo.getPro_img());
        holder.getPro_name().setText(vo.getPro_name());
        holder.getPro_phone().setText(vo.getPro_phone());
        holder.getPro_info().setText(vo.getPro_info());
        return view;


    }
}