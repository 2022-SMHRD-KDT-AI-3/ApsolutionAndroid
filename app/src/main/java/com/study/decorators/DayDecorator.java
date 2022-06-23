package com.study.decorators;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.study.materialcalendarview.R;

//선택한 요일의 배경을 설정하는 클래스
//활용: 선택한 요일을 표현할 때
public class DayDecorator implements DayViewDecorator {

    private final Drawable drawable;

    public DayDecorator(Context context) {
        drawable = context.getResources().getDrawable(R.drawable.calendar_selector);
    }

    // true를 리턴 시 모든 요일에 내가 설정한 드로어블이 적용된다
    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return true;
    }

    // 일자 선택 시 내가 정의한 드로어블이 적용되도록 한다
    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(drawable);
    }
}
