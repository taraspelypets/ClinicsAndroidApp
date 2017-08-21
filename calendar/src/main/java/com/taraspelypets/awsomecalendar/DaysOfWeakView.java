package com.taraspelypets.awsomecalendar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.taraspelypets.awsomecalendar.adapters.CalendarAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Taras on 11.08.2017.
 */

public class DaysOfWeakView extends LinearLayout {
    private CalendarAdapter daysOfWeekAdapter;
    private List<CalendarAdapter.ViewHolder> dayOfWeekHolders = new ArrayList<>();

    public DaysOfWeakView(Context context) {
        super(context);
    }

    public DaysOfWeakView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DaysOfWeakView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void addDaysOfWeek() {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.weight = 1;

        for (int i = 0; i < 7; i++) {

            CalendarAdapter.ViewHolder vh = daysOfWeekAdapter.onCreateViewHolder(new FrameLayout(getContext()));
            dayOfWeekHolders.add(vh);

            View v = vh.getView();
            v.setLayoutParams(lp);
            addView(v);
        }
    }

    private void updateDaysOfWeak() {
        for (int i = 0; i < 7; i++) {
            CalendarAdapter.ViewHolder vh = dayOfWeekHolders.get(i);
            Calendar calendar = new GregorianCalendar();
            calendar.set(Calendar.DAY_OF_WEEK, i + 1);
            daysOfWeekAdapter.onBindViewHolder(vh, calendar);
        }
    }

    public CalendarAdapter getDaysOfWeekAdapter() {
        return daysOfWeekAdapter;
    }

    public void setDaysOfWeekAdapter(CalendarAdapter daysOfWeekAdapter) {
        this.daysOfWeekAdapter = daysOfWeekAdapter;
        addDaysOfWeek();
        updateDaysOfWeak();
    }

    public void update() {
        if (daysOfWeekAdapter != null){
            updateDaysOfWeak();
        } else {
            throw new IllegalStateException("Adapter is not set. Please use setDaysOfWeekAdapter()");
        }
    }
}
