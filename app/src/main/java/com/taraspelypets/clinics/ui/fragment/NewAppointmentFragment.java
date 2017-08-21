package com.taraspelypets.clinics.ui.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.taraspelypets.awsomecalendar.DaysOfWeakView;
import com.taraspelypets.awsomecalendar.adapters.DayOfWeekMaterialAdapter;
import com.taraspelypets.awsomecalendar.adapters.HoursAdapter;
import com.taraspelypets.awsomecalendar.hours.HoursView;
import com.taraspelypets.awsomecalendar.monthcalendar.MonthI;
import com.taraspelypets.cliniclib.DataObject;
import com.taraspelypets.clinics.R;
import com.taraspelypets.clinics.ui.calendar.NewAppointmentCalendarAdapter;

import java.util.Calendar;

/**
 * Created by Taras on 07.08.2017.
 */

public class NewAppointmentFragment extends Fragment implements View.OnClickListener,
        NewAppointmentCalendarAdapter.OnDatePickedListener, HoursAdapter.OnTimePickedListener{
    private static final String DOCTOR = "param1";

    private DataObject.Doctor doctor;

    private ImageButton mButtonBack;

    private DaysOfWeakView mDaysOfWeakView;
    private MonthI mMonthPager;

    private Calendar pickedDateAndHour;


    public NewAppointmentFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param doctor doctor entity.
     * @return A new instance of fragment NewAppointmentFragment.
     */
    public static NewAppointmentFragment newInstance(DataObject.Doctor doctor) {
        NewAppointmentFragment fragment = new NewAppointmentFragment();
        Bundle args = new Bundle();
        args.putParcelable(DOCTOR, doctor);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            doctor = getArguments().getParcelable(DOCTOR);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_appointment, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//
        mDaysOfWeakView = view.findViewById(R.id.daysOfWeakView);
        mDaysOfWeakView.setDaysOfWeekAdapter(new DayOfWeekMaterialAdapter(getActivity()));

        mMonthPager = (MonthI) view.findViewById(R.id.calendar);
        NewAppointmentCalendarAdapter calendarAdapter = new NewAppointmentCalendarAdapter(getActivity());
        calendarAdapter.setOnDatePickedListener(this);
        calendarAdapter.setCurrentDate(Calendar.getInstance());
        mMonthPager.init(calendarAdapter);

        mButtonBack = view.findViewById(R.id.button_back);
        mButtonBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_back:
                getActivity().onBackPressed();
                break;

        }
    }

    @Override
    public void onDatePicked(Calendar calendar) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.hours_container, HoursFragment.newInstance(calendar.getTime().getTime()));
        transaction.commit();
        pickedDateAndHour = null;
    }

    @Override
    public void onTimePicked(Calendar calendar) {
        pickedDateAndHour = calendar;
    }

}
