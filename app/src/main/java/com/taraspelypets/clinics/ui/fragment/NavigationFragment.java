package com.taraspelypets.clinics.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taraspelypets.cliniclib.ClinicLib;
import com.taraspelypets.clinics.R;
import com.taraspelypets.clinics.ui.activity.FragmentRequestListener;

/**
 * Created by Taras on 13.07.2017.
 */

public class NavigationFragment extends Fragment implements View.OnClickListener{

    private FragmentRequestListener mListener;

    private Button mButtonSearch;
    private Button mButtonMap;
    private Button mButtonAppointments;
    private Button mButtonMedicalCard;
    private Button mButtonSignIn;
    private Button mButtonSettings;

    private LinearLayout mLayoutDoctorMenu;
    private LinearLayout mLayoutUserInfo;

    private TextView mTextViewUsername;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_navigation, container, false);

        mTextViewUsername = v.findViewById(R.id.textView_username);
        String username = ClinicLib.getInstance().getCurrentUser().username;
        if(username != null){
            mTextViewUsername.setText(username);
        }else {
            mTextViewUsername.setText("");

        }

        mLayoutUserInfo = v.findViewById(R.id.layout_userInfo);
        mLayoutUserInfo.setOnClickListener(this);

        mButtonSearch = (Button)v.findViewById(R.id.button_search);
        mButtonSearch.setOnClickListener(this);

        mButtonAppointments = (Button)v.findViewById(R.id.button_appointments);
        mButtonAppointments.setOnClickListener(this);

        mButtonMedicalCard = (Button)v.findViewById(R.id.button_medical_card);
        mButtonMedicalCard.setOnClickListener(this);

        mButtonSignIn = (Button)v.findViewById(R.id.button_sign_in);
        mButtonSignIn.setOnClickListener(this);

        mButtonSettings = (Button)v.findViewById(R.id.button_settings);
        mButtonSettings.setOnClickListener(this);

        mButtonMap = (Button)v.findViewById(R.id.button_map_all_clinics);
        mButtonMap.setOnClickListener(this);

        mLayoutDoctorMenu = v.findViewById(R.id.layout_doctor_menu);
        if(ClinicLib.getInstance().hasAuthorityDoctor()){
            mLayoutDoctorMenu.setVisibility(View.VISIBLE);
        } else {
            mLayoutDoctorMenu.setVisibility(View.GONE);
        }

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_search:

                mListener.onCloseMenu();
                break;

            case R.id.button_appointments:

                mListener.onCloseMenu();
                if(ClinicLib.getInstance().hasAuthorityUser()){
                    mListener.onFragmentRequest(new PatientAppointmentsFragment());
                }else {
                    mListener.onFragmentRequest(new LogInFragment());
                }
                break;

            case R.id.button_medical_card:

                mListener.onCloseMenu();
                if(ClinicLib.getInstance().hasAuthorityUser()){
                    mListener.onFragmentRequest(new MedicalInfoFragment());
                }else {
                    mListener.onFragmentRequest(new LogInFragment());
                }
                break;

            case R.id.button_map_all_clinics:
                mListener.onCloseMenu();
                mListener.onFragmentRequest(new MapFragment());
                break;

            case R.id.button_settings:
                mListener.onCloseMenu();
                mListener.onFragmentRequest(new SettingsFragment());
                break;

            case R.id.button_sign_in:
                mListener.onCloseMenu();
                mListener.onFragmentRequest(new LogInFragment());
                break;

            case R.id.layout_userInfo:

                mListener.onCloseMenu();
                if(ClinicLib.getInstance().hasAuthorityUser()){
                    mListener.onFragmentRequest(new UserInfoFragment());
                }else {
                    mListener.onFragmentRequest(new LogInFragment());
                }
                break;

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof FragmentRequestListener) {
            mListener = (FragmentRequestListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mListener = null;
    }


}
