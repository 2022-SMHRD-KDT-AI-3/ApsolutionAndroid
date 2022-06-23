package com.study.materialcalendarview;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainPageActivity extends AppCompatActivity {

    BottomNavigationView bnv;
    com.study.materialcalendarview.Ins_Before_Fragment1 Ins_Before_Fragment1;
    Diary_Cal_Fragment2 Diary_Cal_Fragment2;
    MyPage_Main_Fragment3 MyPage_Main_Fragment3;
    ProList_Fragment4 ProList_Fragment4;
    MainPage_Fragment5 MainPage_Fragment5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        bnv = findViewById(R.id.bnv);
        Ins_Before_Fragment1 = new Ins_Before_Fragment1();
        Diary_Cal_Fragment2 = new Diary_Cal_Fragment2();
        MyPage_Main_Fragment3 = new MyPage_Main_Fragment3();
        ProList_Fragment4 = new ProList_Fragment4();
        MainPage_Fragment5 = new MainPage_Fragment5();

        if(getIntent().getStringExtra("page")!=null){
            if(getIntent().getStringExtra("page").equals("ProList_Fragment4")){
                getSupportFragmentManager().beginTransaction().replace(R.id.container, ProList_Fragment4).commit(); //

            }
        }else{
            //처음 띄울 프래그먼트 화면 설정
            //replace(프래그먼트를 보여줄 FrameLayout id, 화면에 출력한 프래그먼트 객체)
            getSupportFragmentManager().beginTransaction().replace(R.id.container, MainPage_Fragment5).commit(); //

        }

        //메뉴아이템을 클릭했을 때, 프레그먼트 화면을 전환하는 기능구현
        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //프래그먼트 전환하는 로직

                //메뉴아이템의 id값을 반환하는 메소드 사용
                int itemId = item.getItemId();

                if(itemId == R.id.iteminspect){
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, Ins_Before_Fragment1)
                            .commit();

                }else if(itemId  == R.id.itemdiary){
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, Diary_Cal_Fragment2)
                            .commit();
//                    Intent intent = new Intent(MainPageActivity.this, Cal_Activity.class);
//                    startActivity(intent);

                }else if(itemId  == R.id.itemmypage){
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, MyPage_Main_Fragment3)
                            .commit();
                }else if(itemId  == R.id.itemprolist){
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, ProList_Fragment4)
                            .commit();

                }else {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, MainPage_Fragment5)
                            .commit();


                }

                return true;
            }
        });

    }
}