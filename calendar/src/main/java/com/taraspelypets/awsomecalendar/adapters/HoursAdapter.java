package com.taraspelypets.awsomecalendar.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taraspelypets.awsomecalendar.R;
import com.taraspelypets.awsomecalendar.adapters.holders.HoursHolder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Taras on 13.08.2017.
 */

public class HoursAdapter extends CalendarAdapter<HoursHolder>{

    private Context context;
    private Calendar currentCalendar;

    private OnTimePickedListener onTimePickedListener;

    public HoursAdapter(Context context) {
        this.context = context;
        currentCalendar = new GregorianCalendar();

    }

    @Override
    public HoursHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.appointment_slot, parent, false);
        return new HoursHolder(v);
    }

    @Override
    public void onBindViewHolder(HoursHolder hoursHolder, final Calendar calendar) {
        final Calendar tmpCal = new GregorianCalendar();
        tmpCal.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        tmpCal.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        tmpCal.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
        tmpCal.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
        tmpCal.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        String formattedDate = formatter.format(tmpCal.getTime());
        hoursHolder.mTextView.setText(formattedDate);

        if (currentCalendar != null) {
            if(calendar.before(Calendar.getInstance())){
                setDisabled(hoursHolder);
                hoursHolder.mTextView.setOnClickListener(null);
            } else if (currentCalendar.get(Calendar.HOUR_OF_DAY) == calendar.get(Calendar.HOUR_OF_DAY)
                    && currentCalendar.get(Calendar.MINUTE) == calendar.get(Calendar.MINUTE)) {
                setPicked(hoursHolder);
                hoursHolder.mTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("click", "ccc");
                        setCurrentDate(tmpCal);
                    }
                });
            } else {
                setPlain(hoursHolder);
                hoursHolder.mTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("click", "ccc");

                        setCurrentDate(tmpCal);
                    }
                });
            }
        }
    }



    public Calendar getCurrentDate() {
        return currentCalendar;
    }

    public void setCurrentDate(Calendar currentDate) {
        this.currentCalendar = currentDate;

        if (getICalendar() != null) {
            getICalendar().dataSetChanged();
        }
        if (onTimePickedListener != null) {
            onTimePickedListener.onTimePicked(currentDate);
        }
    }

    private void setPicked(HoursHolder holder) {
        holder.mImageViewBackground.setClickable(false);
        holder.mImageViewBackground.setImageResource(com.taraspelypets.awsomecalendar.R.drawable.circle_picked);
//        holder.mTextView.setTextColor(Color.WHITE);
    }

    private void setPlain(HoursHolder holder) {
        holder.mImageViewBackground.setClickable(false);
        holder.mImageViewBackground.setImageResource(R.drawable.empty);
        holder.mTextView.setTextColor(Color.BLACK);
    }

    private void setDisabled(HoursHolder holder) {
        holder.mImageViewBackground.setClickable(false);

        holder.mImageViewBackground.setImageResource(R.drawable.empty);
        holder.mTextView.setTextColor(Color.GRAY);
        holder.mTextView.setOnClickListener(null);
    }

    public OnTimePickedListener getOnTimePickedListener() {
        return onTimePickedListener;
    }

    public void setOnTimePickedListener(OnTimePickedListener onTimePickedListener) {
        this.onTimePickedListener = onTimePickedListener;
    }

    public interface OnTimePickedListener{
        void onTimePicked(Calendar calendar);
    }

}
