package com.taraspelypets.clinics.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import com.taraspelypets.awsomecalendar.DaysOfWeakView;
import com.taraspelypets.awsomecalendar.adapters.DayOfWeekMaterialAdapter;
import com.taraspelypets.awsomecalendar.monthcalendar.MonthI;
import com.taraspelypets.awsomecalendar.weakcalendar.WeakI;
import com.taraspelypets.awsomecalendar.adapters.DayOfMonthMaterialAdapter;
import com.taraspelypets.clinics.R;

/**
 * Created by Taras on 13.08.2017.
 */

public class TestAct extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        MonthI monthPager = (MonthI) findViewById(R.id.pager);
        monthPager.init(new DayOfMonthMaterialAdapter(this));

        DaysOfWeakView daysOfWeakView = findViewById(R.id.daysOfWeak);
        daysOfWeakView.setDaysOfWeekAdapter(new DayOfWeekMaterialAdapter(this));
//
        WeakI weakPager = findViewById(R.id.pagerWeak);
        weakPager.init(new DayOfMonthMaterialAdapter(this));

    }
}
