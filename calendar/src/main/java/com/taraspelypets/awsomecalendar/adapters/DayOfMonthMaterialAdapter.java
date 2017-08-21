package com.taraspelypets.awsomecalendar.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taraspelypets.awsomecalendar.R;
import com.taraspelypets.awsomecalendar.adapters.holders.DayOfMonthHolder;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Taras on 10.08.2017.
 */

public class DayOfMonthMaterialAdapter extends CalendarAdapter<DayOfMonthHolder> {
    private static final String TAG = "DayOfMonthAdapter";

    private Calendar currentCalendar;
    private OnDatePickedListener onDatePickedListener;

    private Context context;

    public DayOfMonthMaterialAdapter(Context context) {
        this.context = context;
    }

    @Override
    public DayOfMonthHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.day_item, parent, false);
        return new DayOfMonthHolder(v);
    }

    @Override
    public void onBindViewHolder(DayOfMonthHolder myViewHolder, Calendar calendar) {
        final Calendar tempCal = new GregorianCalendar();
        tempCal.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        tempCal.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        tempCal.set(Calendar.DATE, calendar.get(Calendar.DATE));

        myViewHolder.mTextView.setText("" + calendar.get(Calendar.DATE));
        Log.d(TAG, "onBindViewHolder " + calendar.get(Calendar.DATE));

        if (currentCalendar != null){
            if (currentCalendar.get(Calendar.YEAR) == tempCal.get(Calendar.YEAR)
                    && currentCalendar.get(Calendar.MONTH) == tempCal.get(Calendar.MONTH)
                    && currentCalendar.get(Calendar.DATE) == tempCal.get(Calendar.DATE)) {
                setPicked(myViewHolder);
            } else{
                setPlain(myViewHolder);
            }
        }

        myViewHolder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick " + tempCal.get(Calendar.DATE));
                setCurrentDate(tempCal);
            }
        });
    }


    private void setPicked(DayOfMonthHolder holder){
        holder.mImageViewBackground.setImageResource(R.drawable.circle_picked);
    }

    private void setPlain(DayOfMonthHolder holder){
        holder.mImageViewBackground.setImageResource(R.drawable.empty);

    }

    public Calendar getCurrentDate() {
        return currentCalendar;
    }

    public void setCurrentDate(Calendar currentDate) {
        this.currentCalendar = currentDate;

        if(getICalendar()!=null){
            getICalendar().dataSetChanged();
        }
        if(onDatePickedListener != null){
            onDatePickedListener.onDatePicked(currentDate);
        }
    }

    public OnDatePickedListener getOnDatePickedListener() {
        return onDatePickedListener;
    }

    public void setOnDatePickedListener(OnDatePickedListener onDatePickedListener) {
        this.onDatePickedListener = onDatePickedListener;
    }

    public interface OnDatePickedListener{
        void onDatePicked(Calendar calendar);
    }

}
