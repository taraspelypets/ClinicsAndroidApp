package com.taraspelypets.clinics.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.taraspelypets.cliniclib.ClinicLib;
import com.taraspelypets.clinics.R;

/**
 * Created by Taras on 07.08.2017.
 */

public class UserInfoFragment extends Fragment implements View.OnClickListener {

    private ImageButton mButtonBack;

    private Button mButtonLogOut;

    private TextView mTextViewLastName;
    private TextView mTextViewFirstName;
    private TextView mTextViewMiddleName;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_info, container, false);

        mButtonBack = v.findViewById(R.id.button_back);
        mButtonBack.setOnClickListener(this);

        mButtonLogOut = v.findViewById(R.id.button_log_out);
        mButtonLogOut.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_back:
                getActivity().onBackPressed();
                break;
            case R.id.button_log_out:
                ClinicLib.getInstance().setCurrentUser(null);
                getActivity().onBackPressed();
                break;
        }
    }
}
