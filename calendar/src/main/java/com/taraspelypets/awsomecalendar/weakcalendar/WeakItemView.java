package com.taraspelypets.awsomecalendar.weakcalendar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.taraspelypets.awsomecalendar.R;
import com.taraspelypets.awsomecalendar.adapters.CalendarAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Taras on 10.08.2017.
 */

/**
 * View witch contains one weak relating to given @{@link Calendar}
 */
public class WeakItemView extends FrameLayout{

    private Calendar calendar;

    private LinearLayout mLinearLayout;
    private CalendarAdapter daysOfMonthAdapter;
    private List<CalendarAdapter.ViewHolder> dayOfMonthHolders = new ArrayList<>();


    public WeakItemView(Calendar calendar, Context context) {
        super(context);
        this.calendar = calendar;
        View v = LayoutInflater.from(getContext()).inflate(R.layout.view_weak, null, false);
        mLinearLayout = v.findViewById(R.id.holder);

        FrameLayout.LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);

        v.setLayoutParams(layoutParams);
        addView(v);
    }

    private void create(){

        for(int j = 1; j <= 7; j++){

            CalendarAdapter.ViewHolder vh = daysOfMonthAdapter.onCreateViewHolder(new FrameLayout(getContext()));
            View v = vh.getView();
            dayOfMonthHolders.add(vh);
            addDayItem(v);
        }

        dataSetChanged();
    }

    public void dataSetChanged(){

        Calendar temCal = new GregorianCalendar();
        temCal.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        temCal.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        temCal.set(Calendar.DATE, calendar.get(Calendar.DATE));
        Log.d("eee"+calendar.get(Calendar.WEEK_OF_YEAR), "b " + temCal.get(Calendar.DATE));

        temCal.add(Calendar.DATE, -calendar.get(Calendar.DAY_OF_WEEK)+1);
        Log.d("eee"+calendar.get(Calendar.WEEK_OF_YEAR), "a " + temCal.get(Calendar.DATE));


//        for(int i = -calendar.get(Calendar.DAY_OF_WEEK), j = 0; i < -calendar.get(Calendar.DAY_OF_WEEK) + 7; i++, j++){
        for(int j = 1; j <= 7; j++){

//            Log.d("iii"+calendar.get(Calendar.WEEK_OF_YEAR), "" + i);

            temCal.add(Calendar.DATE, 1);

//            Log.d("eee"+calendar.get(Calendar.WEEK_OF_YEAR), "" + temCal.get(Calendar.DATE));


            CalendarAdapter.ViewHolder vh = dayOfMonthHolders.get(j-1);
            daysOfMonthAdapter.onBindViewHolder(vh, temCal);
        }
    }

    private void addDayItem(View view) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.weight = 1;
        view.setLayoutParams(lp);

        mLinearLayout.addView(view);
    }

    public CalendarAdapter getDaysOfMonthAdapter() {
        return daysOfMonthAdapter;
    }

    public void setDaysOfMonthAdapter(CalendarAdapter daysOfMonthAdapter) {
        this.daysOfMonthAdapter = daysOfMonthAdapter;
        create();
    }


}
