package com.taraspelypets.clinics;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener, FragmentRequestListener {

    private FragmentManager fragmentManager;
    private NavigationFragment navigationFragment;
    private SearchFragment searchFragment;
    PatientAppointmentsFragment patientAppointmentsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        navigationFragment = new NavigationFragment();
        searchFragment = new SearchFragment();
        patientAppointmentsFragment = new PatientAppointmentsFragment();

        setContentView(R.layout.activity_main);
        fragmentManager = getFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.left_drawer, navigationFragment)
                .commit();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, searchFragment)
                .commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_search:
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, searchFragment)
                        .commit();
                break;
            case R.id.button_appointments:
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, patientAppointmentsFragment)
                        .commit();
                break;
        }
    }

    @Override
    public void onFragmentRequest(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .addToBackStack(null)
                .commit();
    }
}
