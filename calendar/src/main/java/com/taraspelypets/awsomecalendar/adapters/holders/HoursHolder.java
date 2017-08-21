package com.taraspelypets.awsomecalendar.adapters.holders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.taraspelypets.awsomecalendar.R;
import com.taraspelypets.awsomecalendar.adapters.CalendarAdapter;

/**
 * Created by Taras on 13.08.2017.
 */

public class HoursHolder extends CalendarAdapter.ViewHolder {
    public TextView mTextView;
    public ImageView mImageViewBackground;


    public HoursHolder(View view) {
        super(view);
        mTextView = view.findViewById(R.id.textView);
        mImageViewBackground = view.findViewById(R.id.imageView_background);

    }
}
