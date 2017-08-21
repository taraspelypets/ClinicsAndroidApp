package com.taraspelypets.clinics.ui.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taraspelypets.awsomecalendar.adapters.CalendarAdapter;
import com.taraspelypets.clinics.R;
import com.taraspelypets.clinics.ui.calendar.holder.DayOfMonthHolder;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Taras on 13.08.2017.
 */

public class NewAppointmentCalendarAdapter extends CalendarAdapter<DayOfMonthHolder> {
    private static final String TAG = "DayOfMonthAdapter";

    private Calendar currentCalendar;
    private OnDatePickedListener onDatePickedListener;


    private Context context;

    public NewAppointmentCalendarAdapter(Context context) {
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

        Calendar c = Calendar.getInstance();
        Calendar today = new GregorianCalendar();
        today.set(Calendar.YEAR, c.get(Calendar.YEAR));
        today.set(Calendar.MONTH, c.get(Calendar.MONTH));
        today.set(Calendar.DATE, c.get(Calendar.DATE));

        myViewHolder.mTextView.setText("" + calendar.get(Calendar.DATE));


        if (currentCalendar != null) {
            if(tempCal.before(today)){
                setDisabled(myViewHolder);
                myViewHolder.mTextView.setOnClickListener(null);
            }

            else if (currentCalendar.get(Calendar.YEAR) == tempCal.get(Calendar.YEAR)
                    && currentCalendar.get(Calendar.MONTH) == tempCal.get(Calendar.MONTH)
                    && currentCalendar.get(Calendar.DATE) == tempCal.get(Calendar.DATE)) {
                setPicked(myViewHolder);
                myViewHolder.mTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setCurrentDate(tempCal);
                    }
                });
            } else {
                setPlain(myViewHolder);
                myViewHolder.mTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setCurrentDate(tempCal);
                    }
                });
            }
        }


    }


    private void setPicked(DayOfMonthHolder holder) {
        holder.mImageViewBackground.setClickable(false);
        holder.mImageViewBackground.setImageResource(R.drawable.circle_calendar_item);
        holder.mTextView.setTextColor(context.getResources().getColor(R.color.colorPrimary));
    }

    private void setPlain(DayOfMonthHolder holder) {
        holder.mImageViewBackground.setClickable(false);
        holder.mImageViewBackground.setImageResource(R.drawable.empty);
        holder.mTextView.setTextColor(context.getResources().getColor(R.color.white));
    }

    private void setDisabled(DayOfMonthHolder holder) {
        holder.mImageViewBackground.setClickable(false);

        holder.mImageViewBackground.setImageResource(R.drawable.empty);
        holder.mTextView.setTextColor(context.getResources().getColor(R.color.disabled_date));
        holder.mTextView.setOnClickListener(null);
    }

    public Calendar getCurrentDate() {
        return currentCalendar;
    }

    public void setCurrentDate(Calendar currentDate) {
        this.currentCalendar = currentDate;

        if (getICalendar() != null) {
            getICalendar().dataSetChanged();
        }
        if (onDatePickedListener != null) {
            onDatePickedListener.onDatePicked(currentDate);
        }
    }

    public OnDatePickedListener getOnDatePickedListener() {
        return onDatePickedListener;
    }

    public void setOnDatePickedListener(OnDatePickedListener onDatePickedListener) {
        this.onDatePickedListener = onDatePickedListener;
    }

    public interface OnDatePickedListener {
        void onDatePicked(Calendar calendar);
    }

}
