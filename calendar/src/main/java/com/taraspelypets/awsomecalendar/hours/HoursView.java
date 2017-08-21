package com.taraspelypets.awsomecalendar.hours;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.taraspelypets.awsomecalendar.ICalendar;
import com.taraspelypets.awsomecalendar.adapters.CalendarAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Taras on 13.08.2017.
 */

public class HoursView extends LinearLayout implements ICalendar{

    private List<Calendar> slots = new ArrayList<>();
    private List<LinearLayout> rows = new ArrayList<>();
    private int from;
    private int to;
    private int intervalMin;
    private Calendar calendarDay;


    CalendarAdapter calendarAdapter;

    public HoursView(Context context) {
        super(context);
    }

    public HoursView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HoursView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(int from, int to, int intervalMin, Calendar calendarDay, CalendarAdapter calendarAdapter){
        this.calendarAdapter = calendarAdapter;
        this.from = from;
        this.to = to;
        this.intervalMin = intervalMin;
        this.calendarDay = calendarDay;

        calendarAdapter.setICalendar(this);
        setOrientation(VERTICAL);
        createRows();

        Calendar calendarFrom = new GregorianCalendar();
        calendarFrom.set(Calendar.HOUR_OF_DAY, from);
        calendarFrom.set(Calendar.MINUTE, 0);

        Calendar calendarTo = new GregorianCalendar();
        calendarTo.set(Calendar.HOUR_OF_DAY, to);
        calendarTo.set(Calendar.MINUTE, 59);


        while (calendarFrom.before(calendarTo)){
            int row = calendarFrom.get(Calendar.HOUR_OF_DAY);

            CalendarAdapter.ViewHolder vh = calendarAdapter.onCreateViewHolder(new FrameLayout(getContext()));

            holders.add(vh);
            View view = vh.getView();

            rows.get(row).addView(view);

            calendarFrom.add(Calendar.MINUTE, intervalMin);
        }
        dataSetChanged();

    }


    private void createRows(){
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
//        lp.weight = 1;
        for(int i = 0; i < 24; i++){
            LinearLayout row = new LinearLayout(getContext());
            row.setLayoutParams(lp);
            row.setOrientation(HORIZONTAL);
            rows.add(row);
            addView(row);
        }
    }

    @Override
    public void goTo(Calendar calendar) {

    }

    private List<CalendarAdapter.ViewHolder> holders = new ArrayList<>();

    @Override
    public void dataSetChanged() {

        Calendar calendarFrom = new GregorianCalendar();
        calendarFrom.set(Calendar.YEAR, calendarDay.get(Calendar.YEAR));
        calendarFrom.set(Calendar.MONTH, calendarDay.get(Calendar.MONTH));
        calendarFrom.set(Calendar.DAY_OF_MONTH, calendarDay.get(Calendar.DAY_OF_MONTH));
        calendarFrom.set(Calendar.HOUR_OF_DAY, from);
        calendarFrom.set(Calendar.MINUTE, 0);


        Calendar calendarTo = new GregorianCalendar();
        calendarTo.set(Calendar.YEAR, calendarDay.get(Calendar.YEAR));
        calendarTo.set(Calendar.MONTH, calendarDay.get(Calendar.MONTH));
        calendarTo.set(Calendar.DAY_OF_MONTH, calendarDay.get(Calendar.DAY_OF_MONTH));
        calendarTo.set(Calendar.HOUR_OF_DAY, to);
        calendarTo.set(Calendar.MINUTE, 59);



        for (int i = 0; i < holders.size() && calendarFrom.before(calendarTo); i++){
            int row = calendarFrom.get(Calendar.HOUR_OF_DAY);

            CalendarAdapter.ViewHolder vh = holders.get(i);

            calendarAdapter.onBindViewHolder(vh, calendarFrom);

            calendarFrom.add(Calendar.MINUTE, intervalMin);
        }
    }

    @Override
    public Calendar getCurrentPageDate() {
        return null;
    }
}
