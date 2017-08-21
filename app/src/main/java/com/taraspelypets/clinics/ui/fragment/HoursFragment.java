package com.taraspelypets.clinics.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taraspelypets.awsomecalendar.adapters.HoursAdapter;
import com.taraspelypets.awsomecalendar.hours.HoursView;
import com.taraspelypets.cliniclib.DataObject;
import com.taraspelypets.clinics.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Taras on 13.08.2017.
 */

public class HoursFragment extends Fragment{
    public static final String DATE = "DATE";
    private HoursView hoursView;

    HoursAdapter.OnTimePickedListener onTimePickedListener;
    Calendar calendar;

    public HoursFragment() {
    }

    public HoursAdapter.OnTimePickedListener getOnTimePickedListener() {
        return onTimePickedListener;
    }

    public void setOnTimePickedListener(HoursAdapter.OnTimePickedListener onTimePickedListener) {
        this.onTimePickedListener = onTimePickedListener;
    }

    public static HoursFragment newInstance(long date) {
        HoursFragment fragment = new HoursFragment();
        Bundle args = new Bundle();
        args.putLong(DATE, date);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            calendar = new GregorianCalendar();
            calendar.setTime(new Date(getArguments().getLong(DATE)));
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hours, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hoursView = view.findViewById(R.id.hours);
        HoursAdapter hoursAdapter = new HoursAdapter(getActivity());
        hoursAdapter.setOnTimePickedListener(new HoursAdapter.OnTimePickedListener() {
            @Override
            public void onTimePicked(Calendar calendar) {
                if(onTimePickedListener!=null){
                    onTimePickedListener.onTimePicked(calendar);
                }
            }
        });
        hoursView.init(7, 18, 15, calendar, hoursAdapter);
    }
}
