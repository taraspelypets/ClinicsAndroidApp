package com.taraspelypets.awsomecalendar.adapters.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.taraspelypets.awsomecalendar.R;
import com.taraspelypets.awsomecalendar.adapters.CalendarAdapter;

/**
 * Created by Taras on 10.08.2017.
 */


public class DayOfMonthHolder extends CalendarAdapter.ViewHolder {

    public TextView mTextView;
    public ImageView mImageViewBackground;

    public DayOfMonthHolder(View view) {
        super(view);
        mTextView = view.findViewById(R.id.textView);
        mImageViewBackground = view.findViewById(R.id.imageView_background);

    }


}