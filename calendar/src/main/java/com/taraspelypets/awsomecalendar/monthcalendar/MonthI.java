package com.taraspelypets.awsomecalendar.monthcalendar;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.taraspelypets.awsomecalendar.ICalendar;
import com.taraspelypets.awsomecalendar.adapters.CalendarAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Taras on 09.08.2017.
 */

public class MonthI extends ViewPager implements ICalendar {
    private final int MONTH_COUNT = 12;

    public MonthI(Context context) {
        super(context);
    }

    public MonthI(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    private List<MonthItemView> monthItemViews = new ArrayList<>();

    private CalendarAdapter daysOfMonthAdapter;

    public void init(CalendarAdapter daysOfMonthAdapter){

        this.daysOfMonthAdapter = daysOfMonthAdapter;
        this.daysOfMonthAdapter.setICalendar(this);
        setAdapter(new MonthAdapter());
        setCurrentItem(0);
    }


    /** ATTENTION!
     * Don't use this method to set page. Use @{@link MonthI#goTo(Calendar)} to set page with given @{@link Calendar} item.
     * Sets current page of calendar. Page represents one month.
     * If @param item is 0 it will set page with current month, -1 will set one month before current, 2, two mont after.
     * @param item
     */
    @Override
    public void setCurrentItem(int item) {
        Log.d("setCurrentItem", "" + item + Integer.MAX_VALUE/2);
        super.setCurrentItem((int)(item + Integer.MAX_VALUE/2));
    }


    private class MonthAdapter extends PagerAdapter {

        MonthAdapter(){
            super();
        }

        @Override
        public Object instantiateItem(ViewGroup collection, int position){
            int relativePosition = position - Integer.MAX_VALUE/2;
            Calendar calendar = Calendar.getInstance();

            calendar.add(Calendar.MONTH, relativePosition);

            MonthItemView v = new MonthItemView(calendar, getContext());


            if(daysOfMonthAdapter !=null){
                v.setDaysOfMonthAdapter(daysOfMonthAdapter);
            }

            monthItemViews.add(v);
            collection.addView(v);
            Log.d("TAGG", String.valueOf(calendar.get(Calendar.MONTH)));
            return v;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view){
            monthItemViews.remove(view);
            collection.removeView((View) view);
        }

        @Override
        public int getCount(){
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object){
            return view.equals(object);
        }


    }

    public CalendarAdapter getDaysOfMonthAdapter() {
        return daysOfMonthAdapter;
    }

    public void setDaysOfMonthAdapter(CalendarAdapter daysOfMonthAdapter) {
        this.daysOfMonthAdapter = daysOfMonthAdapter;

        for(MonthItemView mv: monthItemViews){
            mv.setDaysOfMonthAdapter(daysOfMonthAdapter);
        }
    }

    @Override
    public void dataSetChanged(){
        for(MonthItemView mv: monthItemViews){
            if(mv != null){
                mv.dataSetChanged();
            }
        }
    }


    /**
     *  Navigates pager to given @{@link Calendar}
     * @param calendar
     */
    @Override
    public void goTo(Calendar calendar){
        Calendar startCalendar = Calendar.getInstance();

        int diffYear = calendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        int diffMonth = diffYear * 12 + calendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
        setCurrentItem(diffMonth);
    }

    @Override
    public Calendar getCurrentPageDate() {
        int relativePosition = getCurrentItem() - Integer.MAX_VALUE/2;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, relativePosition);
        calendar.set(Calendar.DATE, 1);
        return calendar;
    }


//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//
//        int height = 0;
//        for(int i = 0; i < getChildCount(); i++) {
//            View child = getChildAt(i);
//            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//            int h = child.getMeasuredHeight();
//            if(h > height) height = h;
//        }
//
//        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
//
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }
}
