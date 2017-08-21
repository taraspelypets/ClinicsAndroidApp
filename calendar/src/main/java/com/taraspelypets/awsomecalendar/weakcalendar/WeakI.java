package com.taraspelypets.awsomecalendar.weakcalendar;

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
 * Created by Taras on 10.08.2017.
 */

public class WeakI extends ViewPager implements ICalendar {

    private List<WeakItemView> weakItemViews = new ArrayList<>();

    private CalendarAdapter daysOfMonthAdapter;

    public WeakI(Context context) {
        super(context);
    }

    public WeakI(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void goTo(Calendar calendar) {

    }

    @Override
    public void dataSetChanged() {
        for(WeakItemView mv: weakItemViews){
            if(mv != null){
                mv.dataSetChanged();
            }
        }

    }

    @Override
    public Calendar getCurrentPageDate() {
        return null;
    }

    public void init(CalendarAdapter daysOfMonthAdapter){
        this.daysOfMonthAdapter = daysOfMonthAdapter;
        setAdapter(new WeakPagerAdapter());
        this.daysOfMonthAdapter.setICalendar(this);

        setCurrentItem(0);
    }



    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem((int)(item + Integer.MAX_VALUE/2));
    }


    private class WeakPagerAdapter extends PagerAdapter{

        @Override
        public Object instantiateItem(ViewGroup collection, int position){
            int relativePosition = position - Integer.MAX_VALUE/2;
            Calendar calendar = Calendar.getInstance();
            Log.d("WeakPagerAdapter", ""+ relativePosition);
            calendar.add(Calendar.WEEK_OF_YEAR, relativePosition);

            WeakItemView v = new WeakItemView(calendar, getContext());

            if(daysOfMonthAdapter !=null){
                v.setDaysOfMonthAdapter(daysOfMonthAdapter);
            }

            weakItemViews.add(v);

            collection.addView(v);

            Log.d("TAGG", String.valueOf(calendar.get(Calendar.MONTH)));
            return v;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view){
            weakItemViews.remove(view);
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

        for(WeakItemView mv: weakItemViews){
            mv.setDaysOfMonthAdapter(daysOfMonthAdapter);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int height = 0;
        for(int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if(h > height) height = h;
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }



}
