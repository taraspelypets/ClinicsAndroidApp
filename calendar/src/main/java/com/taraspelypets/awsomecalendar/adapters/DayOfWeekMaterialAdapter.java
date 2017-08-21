package com.taraspelypets.awsomecalendar.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taraspelypets.awsomecalendar.R;
import com.taraspelypets.awsomecalendar.adapters.holders.DayOfWeekHolder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Taras on 10.08.2017.
 */

public class DayOfWeekMaterialAdapter extends CalendarAdapter<DayOfWeekHolder> {

    private Context context;

    public DayOfWeekMaterialAdapter(Context context) {
        this.context = context;
    }

    @Override
    public DayOfWeekHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.day_of_week_item, parent, false);
        return new DayOfWeekHolder(v);
    }

    @Override
    public void onBindViewHolder(DayOfWeekHolder dayOfWeekHolder, Calendar calendar) {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = context.getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = context.getResources().getConfiguration().locale;
        }

        SimpleDateFormat dayFormat = new SimpleDateFormat("E", locale);

        dayOfWeekHolder.mTextView.setText(dayFormat.format(calendar.getTime()));
    }
}
