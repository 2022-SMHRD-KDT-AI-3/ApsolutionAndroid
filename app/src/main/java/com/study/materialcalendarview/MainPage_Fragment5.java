package com.study.materialcalendarview;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;


public class MainPage_Fragment5 extends Fragment {

    ViewFlipper main;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@NonNull ViewGroup container,
                             @NonNull Bundle savedInstanceState) {

        int images[]  = {R.drawable.img1,R.drawable.img2,R.drawable.img3,R.drawable.img4};
        View v = inflater.inflate(R.layout.fragment_main_page_5,container,false);
        main = v.findViewById(R.id.main);
        //for loop
//        for(int i = 0; i< images.length;i++){
//            flipperImages(images[i]);
//        }
        // foreach
        for(int image:images){
            flipperImages(image);
        }

        return v;



    }



    public void flipperImages(int image){

        Log.d("슬라이드", "실행 "+image);

        ImageView imageView = new ImageView(getActivity());
        imageView.setBackgroundResource(image);

        main.addView(imageView);
        main.setFlipInterval(3000); //4sec
        main.setAutoStart(true);

        //animation
//        main.setInAnimation(getActivity(),android.R.anim.slide_in_left);
//        main.setInAnimation(getActivity(),android.R.anim.slide_out_right);


    }

}
