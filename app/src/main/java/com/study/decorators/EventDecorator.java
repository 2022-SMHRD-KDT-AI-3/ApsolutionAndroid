package com.study.decorators;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;
import com.study.materialcalendarview.R;

import java.util.Collection;
import java.util.HashSet;

//여러 요일들에 대한 배경을 설정하는 클래스
//활용도: 저장한 날짜 표현할 때
public class EventDecorator implements DayViewDecorator {

    private final Drawable drawable;
    private int color;
    private HashSet<CalendarDay> dates;

    public EventDecorator(int color, Collection<CalendarDay> dates, Context context) {
        drawable = context.getResources().getDrawable(R.drawable.transparent_calendar_element);
        this.color = color;
        this.dates = new HashSet<>(dates);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {

        //view.setSelectionDrawable(drawable); //배경이미지
        view.addSpan(new DotSpan(5, color)); // 날자밑에 점
    }
}
