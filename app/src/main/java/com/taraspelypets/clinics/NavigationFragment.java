package com.taraspelypets.clinics;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Taras on 13.07.2017.
 */

public class NavigationFragment extends Fragment {

    private View.OnClickListener mListener;

    private Button mButtonSearch;
    private Button mButtonAppointments;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_navigation, container, false);
        mButtonSearch = (Button)v.findViewById(R.id.button_search);
        mButtonSearch.setOnClickListener(mListener);

        mButtonAppointments = (Button)v.findViewById(R.id.button_appointments);
        mButtonAppointments.setOnClickListener(mListener);

        return v;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof View.OnClickListener) {
            mListener = (View.OnClickListener) context;
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
