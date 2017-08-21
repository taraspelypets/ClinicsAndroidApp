package com.taraspelypets.awsomecalendar.monthcalendar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.taraspelypets.awsomecalendar.adapters.CalendarAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Taras on 09.08.2017.
 */

/**
 * View witch contains one month relating to given @{@link Calendar}. Pass @{@link Calendar} to the constructor
 * Can not be added via xml. Use @{@link MonthI} to form calendar.
 */
public class MonthItemView extends LinearLayout {
    private static final String TAG = "MonthItemView";

    private Calendar calendar;

    private CalendarAdapter daysOfMonthAdapter;
    private List<CalendarAdapter.ViewHolder> dayOfMonthHolders = new ArrayList<>();

    private LinearLayout[] rows;

    public MonthItemView(Calendar calendar, @NonNull Context context) {
        super(context);
        this.calendar = calendar;
        setOrientation(VERTICAL);
    }

    private void createRows() {
        rows = new LinearLayout[calendar.getActualMaximum(Calendar.WEEK_OF_MONTH)];
        for (int i = 0; i < rows.length; i++) {
            rows[i] = new LinearLayout(getContext());
            rows[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            rows[i].setOrientation(HORIZONTAL);
            rows[i].setGravity(Gravity.RIGHT);
            addView(rows[i]);
        }
    }

    private void addDayItem(int row, View view) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.weight = 1;
        view.setLayoutParams(lp);

        rows[row].addView(view);
    }

    private void addEmpty(int before, int after) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.weight = 1;

        int beforeCount = 7 - rows[before].getChildCount();

        for (int i = 0; i < beforeCount; i++) {
            FrameLayout v = new FrameLayout(getContext());
            v.setLayoutParams(lp);
            rows[before].addView(v, 0);
        }

        int afterCount = 7 - rows[after].getChildCount();

        for (int i = 0; i < afterCount; i++) {
            FrameLayout v = new FrameLayout(getContext());
            v.setLayoutParams(lp);
            rows[after].addView(v);
        }
    }

    private void createCalendar() {

        Calendar temCal = new GregorianCalendar();
        temCal.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        temCal.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        calendar.set(Calendar.DATE, 1);

        for (int j = 1; j <= temCal.getActualMaximum(Calendar.DATE); j++) {
            temCal.set(Calendar.DATE, j);

            CalendarAdapter.ViewHolder vh = daysOfMonthAdapter.onCreateViewHolder(new FrameLayout(getContext()));
            View v = vh.getView();
            dayOfMonthHolders.add(vh);
            addDayItem(temCal.get(Calendar.WEEK_OF_MONTH) - 1, v);
        }

        addEmpty(0, temCal.getActualMaximum(Calendar.WEEK_OF_MONTH) - 1);
        dataSetChanged();
    }

    public void dataSetChanged() {
        Calendar temCal = new GregorianCalendar();
        temCal.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        temCal.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        calendar.set(Calendar.DATE, 1);

        for (int j = 1; j <= temCal.getActualMaximum(Calendar.DATE); j++) {
            temCal.set(Calendar.DATE, j);

            CalendarAdapter.ViewHolder vh = dayOfMonthHolders.get(j - 1);
            daysOfMonthAdapter.onBindViewHolder(vh, temCal);
        }
    }

    public CalendarAdapter getDaysOfMonthAdapter() {
        return daysOfMonthAdapter;
    }

    public void setDaysOfMonthAdapter(CalendarAdapter daysOfMonthAdapter) {
        this.daysOfMonthAdapter = daysOfMonthAdapter;
        createRows();
        createCalendar();
    }

}
