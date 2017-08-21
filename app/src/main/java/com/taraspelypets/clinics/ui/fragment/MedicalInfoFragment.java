package com.taraspelypets.clinics.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.taraspelypets.clinics.R;

/**
 * Created by Taras on 07.08.2017.
 */

public class MedicalInfoFragment extends Fragment implements View.OnClickListener{

    private ImageButton mButtonBack;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmrnt_medical_info, container, false);

        mButtonBack =v.findViewById(R.id.button_back);
        mButtonBack.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_back:
                getActivity().onBackPressed();
                break;
        }
    }
}
