package com.study.materialcalendarview;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;
import com.study.decorators.DayDecorator;
import com.study.decorators.EventDecorator;
import com.study.decorators.OneDayDecorator;
import com.study.decorators.SaturdayDecorator;
import com.study.decorators.SundayDecorator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

public class Diary_Cal_Fragment2 extends Fragment {
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();
    MaterialCalendarView materialCalendarView;
    ImageButton diary_back;


    //날짜데이터셋
    //String[] result = {"2022-06-01","2022-06-02","2022-06-03","2022-06-04"};
    //String[] result = {"2022-06-02","2022-06-04","2022-06-16"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_diary, container, false);

        materialCalendarView = view.findViewById(R.id.materialCalendar);
        diary_back = view.findViewById(R.id.diary_back);

        calendarInit();

        //AsyncTask 실행
        //new ApiSimulator(result).executeOnExecutor(Executors.newSingleThreadExecutor());

        //날짜 클릭시 이벤트
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int Year = date.getYear();
                int Month = date.getMonth() + 1;
                int Day = date.getDay();

                Log.i("Year test", Year + "");
                Log.i("Month test", Month + "");
                Log.i("Day test", Day + "");


                String shot_Day = Year + "년" + Month + "월" + Day + "일";
                String today =  Year + "-" +Month + "-" + Day;





                Log.i("shot_Day test", "선택된 날짜:"+shot_Day + "");

                //선택한 날짜의 배경색 제거
//                materialCalendarView.clearSelection();

                Toast.makeText(getActivity(), shot_Day , Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), Diary_List_Activity.class);
                intent.putExtra("today",today);

                getActivity().startActivity(intent);






            }
        });

        diary_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new MainPage_Fragment5()).commit();
            }
        });

        return view;


    }


    //달력 실행 시 초기화
    public void calendarInit() {
        //달력 시작/끝 셋팅
        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2017, 0, 1)) // 달력의 시작
                .setMaximumDate(CalendarDay.from(2025, 11, 31)) // 달력의 끝
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        //월, 요일을 한글로 보이게 설정
        materialCalendarView.setTitleFormatter(new MonthArrayTitleFormatter(getResources().getStringArray(R.array.custom_months)));
        materialCalendarView.setWeekDayFormatter(new ArrayWeekDayFormatter(getResources().getStringArray(R.array.custom_weekdays)));

        // 좌우 화살표 사이 연, 월의 폰트 스타일 설정
        materialCalendarView.setHeaderTextAppearance(R.style.CalendarWidgetHeader);

        // 월/연 -> 연/월이 보이는 방식 커스텀
        materialCalendarView.setTitleFormatter(new TitleFormatter() {
            @Override
            public CharSequence format(CalendarDay day) {
                Date inputText = day.getDate();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String getTime = sdf.format(inputText);

                String[] calendarHeaderElements = getTime.split("-");
                StringBuilder calendarHeaderBuilder = new StringBuilder();
                calendarHeaderBuilder.append(calendarHeaderElements[0])
                        .append("년")
                        .append(" ")
                        .append(calendarHeaderElements[1])
                        .append("월");
                return calendarHeaderBuilder.toString();
            }
        });


        //달력에 휴일 글자색 셋팅 관련 메소드
        //토요일(파란색), 일요일(빨간색), 당일(파란색
        materialCalendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator(),
                new DayDecorator(getActivity()),
                oneDayDecorator);
    }

    //날짜표기 아래 빨간색 점 찍어주는 기능
    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        String[] Time_Result;

        ApiSimulator(String[] Time_Result){
            this.Time_Result = Time_Result;
        }

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            ArrayList<CalendarDay> dates = new ArrayList<>();

            /*특정날짜 달력에 점표시해주는곳*/
            /*월은 0이 1월 년,일은 그대로*/
            //string 문자열인 Time_Result 을 받아와서 ,를 기준으로짜르고 string을 int 로 변환
            for(int i = 0 ; i < Time_Result.length ; i ++){

                String[] time = Time_Result[i].split("-");
                int year = Integer.parseInt(time[0]);
                int month = Integer.parseInt(time[1]);
                int dayy = Integer.parseInt(time[2]);

                calendar.set(year,month-1,dayy);
                CalendarDay day = CalendarDay.from(calendar);

                dates.add(day);
            }

            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if (getActivity().isFinishing()) { return; }

            materialCalendarView.addDecorator(new EventDecorator(Color.RED, calendarDays, getActivity()));
        }
    }

    //Date객체형태의 날짜정보 반환 기능
    public Date getDate(int year, int month, int date){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month-1, date);
        return new Date(calendar.getTimeInMillis());
    }
}