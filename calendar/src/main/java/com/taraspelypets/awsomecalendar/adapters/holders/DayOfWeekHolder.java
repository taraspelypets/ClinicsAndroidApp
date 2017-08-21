package com.taraspelypets.awsomecalendar.adapters.holders;

import android.view.View;
import android.widget.TextView;

import com.taraspelypets.awsomecalendar.R;
import com.taraspelypets.awsomecalendar.adapters.CalendarAdapter;

/**
 * Created by Taras on 10.08.2017.
 */

public class DayOfWeekHolder extends CalendarAdapter.ViewHolder {

    public TextView mTextView;

    public DayOfWeekHolder(View view) {
        super(view);
        mTextView = view.findViewById(R.id.textView);
    }


}
