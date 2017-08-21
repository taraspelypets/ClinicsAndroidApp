package com.taraspelypets.awsomecalendar;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import java.util.Calendar;

/**
 * Created by Taras on 11.08.2017.
 */

public interface ICalendar {


    public void goTo(Calendar calendar);

    public void dataSetChanged();

    Calendar getCurrentPageDate();

}
